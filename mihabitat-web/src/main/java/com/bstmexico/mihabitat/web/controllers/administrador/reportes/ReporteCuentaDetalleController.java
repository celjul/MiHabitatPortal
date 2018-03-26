package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.MovimientoIngresoNoIdentificado;
import com.bstmexico.mihabitat.movimientos.model.*;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoConceptoOtroIngreso;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoOtroIngreso;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoDetallle;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoGasto;
import com.bstmexico.mihabitat.web.dto.reportes.Cuenta;
import com.bstmexico.mihabitat.web.dto.reportes.Movimiento;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentaDetalle;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/cuenta-detalle")
public class ReporteCuentaDetalleController {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReporteCuentaDetalleController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model, @RequestParam(required = false) Long idCuenta,
			@RequestParam(required = false) String inicio,
			@RequestParam(required = false) String fin,
			@RequestParam(required = false) Boolean ficticia, HttpSession session) {
		model.addAttribute("idCuenta", mapper.writeValueAsString(idCuenta));
		model.addAttribute("inicio", mapper.writeValueAsString(inicio));
		model.addAttribute("fin", mapper.writeValueAsString(fin));
		model.addAttribute("ficticia", mapper.writeValueAsString(ficticia));

		Collection<com.bstmexico.mihabitat.cuentas.model.Cuenta> cuentaList = new ArrayList<>();

		cuentaList.addAll(cuentaService
				.getCuentas((Condominio) session
								.getAttribute(SessionEnum.CONDOMINIO.getValue()),
						configurationServiceImpl.getCuentaCajasBancos()));

		cuentaList.addAll(cuentaService
				.getCuentas((Condominio) session
								.getAttribute(SessionEnum.CONDOMINIO.getValue()),
						configurationServiceImpl.getCuentaEgresos()));

		cuentaList.addAll(cuentaService
				.getCuentas((Condominio) session
								.getAttribute(SessionEnum.CONDOMINIO.getValue()),
						configurationServiceImpl.getCuentaIngresos()));

		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaList));
		return "administrador/reportes/cuenta-detalle";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteCuentaDetalle consultar(@RequestParam Long idCuenta,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam Boolean ficticia, HttpSession session) {
		return getReporte(idCuenta,
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()), 
				DateUtils.getStartOfDay(inicio),
				DateUtils.getEndOfDay(fin),
				ficticia);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(@RequestParam Long idCuenta,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam String formato, Boolean ficticia, HttpSession session) {

		ReporteCuentaDetalle reporte = getReporte(idCuenta, 
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
				DateUtils.getStartOfDay(inicio),
				DateUtils.getEndOfDay(fin),
				ficticia);
		reporte.setCondominio((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		
		byte[] bytes = getBytes(reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "cuenta_detalle." + formato;
		
		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] getBytes(ReporteCuentaDetalle reporte, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator + "cuenta-detalle" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		//map.put("IMAGEN", contexto + "recursos" + File.separator + "img" + File.separator + configurationServiceImpl.getLogo());
		map.put("FORMATO", formato);

		Condominio condominio = condominioService.readConImagen(reporte.getCondominio().getId());

		if(condominio.getLogoCondominio() != null) {
			InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
			try{
				BufferedImage image = ImageIO.read(is);
				map.put("IMAGEN", image);
			} catch (IOException ioe) {
				LOG.error("Error leyendo logo del Condominio, se colocar? el de MH");
				File initialFile = new File(contexto + "recursos" + File.separator + "img"
						+ File.separator + configurationServiceImpl.getLogo());
				try {
					is = new FileInputStream(initialFile);
					BufferedImage image = ImageIO.read(is);
					map.put("IMAGEN", image);
				}  catch (IOException ioedos) {
					LOG.error("No se encontr? el logo de MiHabitat");
				}
			}

		} else {
			LOG.warn("No se encontr? logo del Condominio, se colocar? el de MH");
			File initialFile = new File(contexto + "recursos" + File.separator + "img"
					+ File.separator + configurationServiceImpl.getLogo());
			try {
				InputStream is = new FileInputStream(initialFile);
				BufferedImage image = ImageIO.read(is);
				map.put("IMAGEN", image);
			}  catch (IOException ioedos) {
				LOG.error("No se encontr? el logo de MiHabitat");
			}
		}

		Collection collection = new ArrayList();
		collection.add(reporte);
		jrDataSource = new JRBeanCollectionDataSource(collection);
		
		String sourceFile = formato.equals(reportUtils.PDF) ? directorio + "cuenta-detalle.jasper" : directorio + "cuenta-detalle-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	private ReporteCuentaDetalle getReporte(Long idCuenta,
			Condominio condominio, Date inicio, Date fin, Boolean ficticia) {

		ReporteCuentaDetalle reporte = new ReporteCuentaDetalle();
		reporte.setCuenta(new Cuenta());
		reporte.setInicio(inicio);
		reporte.setFin(fin);
		reporte.setMovimientos(new ArrayList<Movimiento>());
		
		if (!ficticia) {
			com.bstmexico.mihabitat.cuentas.model.Cuenta cuenta = cuentaService.get(CuentaFactory.newInstance(idCuenta));
			reporte.setCuenta(getCuenta(cuenta));
			procesaMovimientos(reporte, getCuenta(cuenta),  movimientoService.getMovimientos(cuenta, inicio, fin));
			
			if (!CollectionUtils.isEmpty(cuenta.getCuentasHijas())) {
				for (com.bstmexico.mihabitat.cuentas.model.Cuenta segundo : cuenta.getCuentasHijas()) {
					procesaMovimientos(reporte, getCuenta(segundo),  movimientoService.getMovimientos(segundo, inicio, fin));
					if (!CollectionUtils.isEmpty(segundo.getCuentasHijas())) {
						for (com.bstmexico.mihabitat.cuentas.model.Cuenta tercer : segundo.getCuentasHijas()) {
							procesaMovimientos(reporte, getCuenta(tercer),  movimientoService.getMovimientos(tercer, inicio, fin));
							if (!CollectionUtils.isEmpty(tercer.getCuentasHijas())) {
								for (com.bstmexico.mihabitat.cuentas.model.Cuenta cuarto : tercer.getCuentasHijas()) {
									procesaMovimientos(reporte, getCuenta(cuarto),  movimientoService.getMovimientos(cuarto, inicio, fin));
								}
							}
						}
					}
				}
			}
		} else {
			com.bstmexico.mihabitat.cuentas.model.Cuenta cuenta = cuentaService.get(idCuenta);
			reporte.setCuenta(getCuenta(cuenta));
			reporte.getCuenta().setNombre("Otros - " + cuenta.getNombre());
			reporte.getCuenta().setFicticia(Boolean.TRUE);
			procesaMovimientos(reporte, getCuenta(cuenta),  movimientoService.getMovimientos(cuenta, inicio, fin));
		}
		return reporte;
	}
	
	public Cuenta getCuenta(com.bstmexico.mihabitat.cuentas.model.Cuenta c) {
		Cuenta cuenta = new Cuenta();
		cuenta.setId(c.getId());
		cuenta.setNombre(c.getNombre());
		return cuenta;
	} 
	
	public void procesaMovimientos(ReporteCuentaDetalle reporte, Cuenta cuenta,
			Collection<com.bstmexico.mihabitat.movimientos.model.Movimiento> movimientos) {
		if (!CollectionUtils.isEmpty(movimientos)) {
			for (com.bstmexico.mihabitat.movimientos.model.Movimiento m : movimientos) {
				Movimiento movimiento = new Movimiento();
				movimiento.setCuenta(cuenta);
				movimiento.setFecha(m.getFecha());
				if (m.getDebe() != null) {
					movimiento.setDebe(m.getDebe());
				} else if (m.getHaber() != null) {
					movimiento.setHaber(m.getHaber());
				}
				if (m instanceof MovimientoCargoAplicado) {
					movimiento.setDescripcion((((MovimientoCargoAplicado) m).getTipo().getDescripcion()
							+ ": "
							+ ((MovimientoCargoAplicado) m).getMovimientoCargo().getCargo().getConcepto()).toUpperCase()
							+ " - "
							+ ((MovimientoCargoAplicado) m).getMovimientoCargo().getCargo().getDepartamento().getNombre().toUpperCase()
							+ " - "
							+ DateUtils.getDate(((MovimientoCargoAplicado) m).getMovimientoCargo().getCargo().getFecha(), "dd/MM/yyyy"));
				} else if (m instanceof MovimientoPago) {
					if (m.getDebe() != null) {
						movimiento.setDescripcion("PAGO CANCELADO");
					} else if (m.getHaber() != null) {
						movimiento.setDescripcion("PAGO");
					}
				} else if (m instanceof MovimientoSaldo) {
					movimiento.setDescripcion("SALDO A FAVOR");
				} else if (m instanceof MovimientoCfdiAplicado) {
					movimiento.setDescripcion("PAGO A CDFI " + 
							((MovimientoCfdi) ((MovimientoCfdiAplicado) m).getMovimiento()).getDetalleCfdi().getDescripcion());
				} else if (m instanceof MovimientoPagoProveedor) {
					movimiento.setDescripcion("PAGO A CDFI");
				} else if (m instanceof MovimientoGasto) {
					movimiento.setDescripcion("PAGO A GASTO");
				} else if (m instanceof MovimientoDetallle) {
					movimiento.setDescripcion("PAGO A GASTO " + 
							((MovimientoDetallle) m).getDetalle().getConcepto());
				} else if (m instanceof MovimientoOtroIngreso) {
					movimiento.setDescripcion("OTRO INGRESO ");
				} else if (m instanceof MovimientoConceptoOtroIngreso) {
					movimiento.setDescripcion("OTRO INGRESO " +
							((MovimientoConceptoOtroIngreso) m).getConceptoIngreso().getConcepto());
				} else if (m instanceof MovimientoIngresoNoIdentificado) {
					if (m.getDebe() != null) {
						movimiento.setDescripcion("INGRESO NO IDENTIFICADO CANCELADO " + ((MovimientoIngresoNoIdentificado) m).getIngresoNoIdentificado().getComentario());
					} else if (m.getHaber() != null) {
						movimiento.setDescripcion("INGRESO NO IDENTIFICADO " + ((MovimientoIngresoNoIdentificado) m).getIngresoNoIdentificado().getComentario());
					}
				}
				reporte.getMovimientos().add(movimiento);
			}
		}
	}
	
	
}