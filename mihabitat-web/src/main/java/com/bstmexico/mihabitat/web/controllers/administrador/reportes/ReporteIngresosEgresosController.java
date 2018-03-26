package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.condominios.service.CondominioService;
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

import com.bstmexico.mihabitat.cargos.model.Cargo;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import com.bstmexico.mihabitat.proveedores.model.DetalleFactura;
import com.bstmexico.mihabitat.proveedores.service.FacturaPorPagarService;
import com.bstmexico.mihabitat.web.dto.reportes.Cuenta;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteIngresosEgresos;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/ingresos-egresos")
public class ReporteIngresosEgresosController {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReporteIngresosEgresosController.class);

	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;
	
	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	private CondominioService condominioService;
	
	@Autowired
	private FacturaPorPagarService facturaPorPagarService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private ReportUtils reportUtils;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model) {
		return "administrador/reportes/ingresos-egresos";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteIngresosEgresos consultar(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			HttpSession session) {
		return getReporte((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
				DateUtils.getStartOfDay(inicio), DateUtils.getEndOfDay(fin));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam String formato, HttpSession session) {

		ReporteIngresosEgresos reporte = getReporte((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
				DateUtils.getStartOfDay(inicio), DateUtils.getEndOfDay(fin));
		reporte.setCondominio((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		
		reporte.getCuentasinicial().add(reporte.getBancosCorte());
		reporte.getCuentasinicial().add(reporte.getCajasCorte());
		reporte.getCuentas().add(reporte.getCobrar());
		reporte.getCuentas().add(reporte.getPagar());
		reporte.getCuentasingresos().add(reporte.getIngresos());
		reporte.getCuentasegresos().add(reporte.getEgresos());
		reporte.getCuentasfinal().add(reporte.getBancos());
		reporte.getCuentasfinal().add(reporte.getCajas());
		
		byte[] bytes = getBytes(reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "ingresos_egresos." + formato;
		
		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] getBytes(ReporteIngresosEgresos reporte, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator + "ingresos-egresos" + File.separator;

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
		
		String sourceFile = formato.equals(reportUtils.PDF) ? directorio + "ingresos-egresos.jasper" : directorio + "ingresos-egresos-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	private ReporteIngresosEgresos getReporte(Condominio condominio, Date inicio, Date fin) {

		com.bstmexico.mihabitat.cuentas.model.Cuenta bancos = cuentaService.getCuentas(condominio, configurationServiceImpl.getBancos()).iterator().next();
		com.bstmexico.mihabitat.cuentas.model.Cuenta cajas = cuentaService.getCuentas(condominio, configurationServiceImpl.getCajas()).iterator().next();
		com.bstmexico.mihabitat.cuentas.model.Cuenta ingresos = cuentaService.getCuentas(condominio, configurationServiceImpl.getIngresos()).iterator().next();
		com.bstmexico.mihabitat.cuentas.model.Cuenta egresos = cuentaService.getCuentas(condominio, configurationServiceImpl.getEgresos()).iterator().next();
		com.bstmexico.mihabitat.cuentas.model.Cuenta saldos = cuentaService.getCuentas(condominio, configurationServiceImpl.getSaldos()).iterator().next();
		
		ReporteIngresosEgresos reporte = new ReporteIngresosEgresos();
		reporte.setBancosCorte(new Cuenta(bancos));
		reporte.setCajasCorte(new Cuenta(cajas));
		reporte.setCobrar(new Cuenta());
		reporte.setPagar(new Cuenta());
		reporte.setIngresos(new Cuenta(ingresos));
		reporte.setEgresos(new Cuenta(egresos));
		reporte.setSaldos(new Cuenta(saldos));
		reporte.setBancos(new Cuenta(bancos));
		reporte.setCajas(new Cuenta(cajas));
		reporte.setTotal(new Cuenta());
		
		reporte.setInicio(inicio);
		reporte.setFin(fin);
		
		calcularHasta(reporte.getBancosCorte(), inicio, false);
		calcularHasta(reporte.getCajasCorte(), inicio, false);
		calcularCobrar(condominio, reporte.getCobrar(), fin);
		calcularPagar(condominio, reporte.getPagar(), fin);
		calcularEntre(reporte.getIngresos(), inicio, fin);
		calcularEntre(reporte.getEgresos(), inicio, fin);
		calcularHasta(reporte.getSaldos(), fin, true);
		calcularHasta(reporte.getBancos(), fin, true);
		calcularHasta(reporte.getCajas(), fin, true);
		calcularTotal(reporte);

		fixId(reporte.getBancosCorte());
		fixId(reporte.getCajasCorte());
		return reporte;
	}
	
	private void calcularTotal(ReporteIngresosEgresos reporte) {
		BigDecimal debe = BigDecimal.ZERO;
		BigDecimal haber = BigDecimal.ZERO;
		
		debe = debe.add(reporte.getBancosCorte().getDebe() != null ? reporte.getBancosCorte().getDebe() : BigDecimal.ZERO);
		debe = debe.add(reporte.getCajasCorte().getDebe() != null ? reporte.getCajasCorte().getDebe() : BigDecimal.ZERO);
		debe = debe.add(reporte.getCobrar().getDebe() != null ? reporte.getCobrar().getDebe() : BigDecimal.ZERO);
		debe = debe.add(reporte.getPagar().getDebe() != null ? reporte.getPagar().getDebe() : BigDecimal.ZERO);
		debe = debe.add(reporte.getIngresos().getDebe() != null ? reporte.getIngresos().getDebe() : BigDecimal.ZERO);
		debe = debe.add(reporte.getEgresos().getDebe() != null ? reporte.getEgresos().getDebe() : BigDecimal.ZERO);
		debe = debe.add(reporte.getSaldos().getDebe() != null ? reporte.getSaldos().getDebe() : BigDecimal.ZERO);
		debe = debe.add(reporte.getBancos().getDebe() != null ? reporte.getBancos().getDebe() : BigDecimal.ZERO);
		debe = debe.add(reporte.getCajas().getDebe() != null ? reporte.getCajas().getDebe() : BigDecimal.ZERO);
		
		haber = haber.add(reporte.getBancosCorte().getHaber() != null ? reporte.getBancosCorte().getHaber() : BigDecimal.ZERO);
		haber = haber.add(reporte.getCajasCorte().getHaber() != null ? reporte.getCajasCorte().getHaber() : BigDecimal.ZERO);
		haber = haber.add(reporte.getCobrar().getHaber() != null ? reporte.getCobrar().getHaber() : BigDecimal.ZERO);
		haber = haber.add(reporte.getPagar().getHaber() != null ? reporte.getPagar().getHaber() : BigDecimal.ZERO);
		haber = haber.add(reporte.getIngresos().getHaber() != null ? reporte.getIngresos().getHaber() : BigDecimal.ZERO);
		haber = haber.add(reporte.getEgresos().getHaber() != null ? reporte.getEgresos().getHaber() : BigDecimal.ZERO);
		haber = haber.add(reporte.getSaldos().getHaber() != null ? reporte.getSaldos().getHaber() : BigDecimal.ZERO);
		haber = haber.add(reporte.getBancos().getHaber() != null ? reporte.getBancos().getHaber() : BigDecimal.ZERO);
		haber = haber.add(reporte.getCajas().getHaber() != null ? reporte.getCajas().getHaber() : BigDecimal.ZERO);
	
		reporte.getTotal().setNombre("TOTAL");
		reporte.getTotal().setDebe(debe);
		reporte.getTotal().setHaber(haber);
	}
	
	@SuppressWarnings({ "rawtypes","unchecked" })
	private void calcularCobrar(Condominio condominio, Cuenta pagar, Date fin) {
		pagar.setNombre("CUENTAS POR COBRAR");
		
		BigDecimal debe = BigDecimal.ZERO;
		Map map = new HashMap();
		map.put("condominio", condominio);
		Collection<Cargo> cargos = cargoService.search(map, CargoDepartamento.class, Boolean.FALSE);
		for (Cargo cargo : cargos) {
			for (MovimientoCargo mov : ((CargoDepartamento) cargo).getMovimientos()) {
				if (mov.getFecha().compareTo(fin) < 0) {
					if (mov.getDebe() != null) {
						debe = debe.add(mov.getDebe());
					} else if (mov.getHaber() != null) {
						debe = debe.subtract(mov.getHaber());
					}
				}
				for (MovimientoCargoAplicado apl : mov.getAplicados()) {
					if (apl.getAplicado() && apl.getFecha().compareTo(fin) < 0) {
						if (apl.getHaber() != null) {
							debe = debe.subtract(apl.getHaber());
						} else if (apl.getDebe() != null) {
							debe = debe.add(apl.getDebe());
						}
					}
				}
			}
		}
		
		pagar.setDebe(debe);
		pagar.setHaber(BigDecimal.ZERO);
	}
	
	@SuppressWarnings({ "rawtypes","unchecked" })
	private void calcularPagar(Condominio condominio, Cuenta pagar, Date fin) {
		pagar.setNombre("CUENTAS POR PAGAR");
		
		BigDecimal debe = BigDecimal.ZERO;
		Map map = new HashMap();
		map.put("condominio", condominio);
		
		Collection<Cfdi> facturas = facturaPorPagarService.search(map);
		for (Cfdi cfdi : facturas) {
			for (DetalleFactura detalle : cfdi.getConceptos()) {
				if (detalle.getMovimientoCfdi().getFecha().compareTo(fin) < 0) {
					if (detalle.getMovimientoCfdi().getHaber() != null) {
						debe = debe.add(detalle.getMovimientoCfdi().getHaber());
					}
					for (MovimientoCfdiAplicado apl : detalle.getMovimientoCfdi().getAplicados()) {
						if (apl.getAplicado() && apl.getFecha().compareTo(fin) < 0) {
							if (apl.getHaber() != null) {
								debe = debe.add(apl.getHaber());
							} else if (apl.getDebe() != null) {
								debe = debe.subtract(apl.getDebe());
							}
						}
					}
				}
			}
		}
		
		pagar.setDebe(debe);
		pagar.setHaber(BigDecimal.ZERO);
	}
	
	private void calcularHasta(Cuenta primer, Date inicio, boolean incluyente) {
		setMontosHasta(primer, inicio, incluyente);
		
		if (!CollectionUtils.isEmpty(primer.getHijas())) {
			for (Cuenta segundo : primer.getHijas()) { //SEGUNDO
				setMontosHasta(segundo, inicio, incluyente);
				
				if (!CollectionUtils.isEmpty(segundo.getHijas())) {
					
					for (Cuenta tercer : segundo.getHijas()) { //TERCER
						setMontosHasta(tercer, inicio, incluyente);
						
						if (!CollectionUtils.isEmpty(tercer.getHijas())) {
							
							for (Cuenta cuarto : tercer.getHijas()) { //CUARTO
								setMontosHasta(cuarto, inicio, incluyente);
								setSaldo(cuarto);
							}
							if (crearOtra(tercer)) {
								tercer.getHijas().add(getOtra(tercer));
							}
						}
						setSaldo(tercer);
					}
					if (crearOtra(segundo)) {
						segundo.getHijas().add(getOtra(segundo));
					}
				}
				setSaldo(segundo);
			}
		}
		if (crearOtra(primer)) {
			primer.getHijas().add(getOtra(primer));
		}
		setSaldo(primer);
	}
	
	private void calcularEntre(Cuenta primer, Date inicio, Date fin) {
		setMontosEntre(primer, inicio, fin);
		
		if (!CollectionUtils.isEmpty(primer.getHijas())) {
			
			for (Cuenta segundo : primer.getHijas()) { //SEGUNDO
				setMontosEntre(segundo, inicio, fin);
				
				if (!CollectionUtils.isEmpty(segundo.getHijas())) {
					
					for (Cuenta tercer : segundo.getHijas()) { //TERCER
						setMontosEntre(tercer, inicio, fin);
						
						if (!CollectionUtils.isEmpty(tercer.getHijas())) {
							
							for (Cuenta cuarto : tercer.getHijas()) { //CUARTO
								setMontosEntre(cuarto, inicio, fin);
								setSaldo(cuarto);
							}
							if (crearOtra(tercer)) {
								tercer.getHijas().add(getOtra(tercer));
							}
						}
						setSaldo(tercer);
					}
					if (crearOtra(segundo)) {
						segundo.getHijas().add(getOtra(segundo));
					}
				}
				setSaldo(segundo);
			}
		}
		if (crearOtra(primer)) {
			primer.getHijas().add(getOtra(primer));
		}
		setSaldo(primer);
	}
	
	private void setMontosHasta(Cuenta cuenta, Date inicio, boolean incluyente) {
		if(incluyente) {
			cuenta.setDebe(movimientoService.getDebeLtEq(inicio, CuentaFactory.newInstance(cuenta.getId())));
			cuenta.setHaber(movimientoService.getHaberLtEq(inicio, CuentaFactory.newInstance(cuenta.getId())));
		} else {
			cuenta.setDebe(movimientoService.getDebe(inicio, CuentaFactory.newInstance(cuenta.getId())));
			cuenta.setHaber(movimientoService.getHaber(inicio, CuentaFactory.newInstance(cuenta.getId())));
		}
	}

	/*private void setMontosHastaLtEq(Cuenta cuenta, Date inicio) {
		cuenta.setDebe(movimientoService.getDebeLtEq(inicio, CuentaFactory.newInstance(cuenta.getId())));
		cuenta.setHaber(movimientoService.getHaberLtEq(inicio, CuentaFactory.newInstance(cuenta.getId())));
	}*/
	
	private void setMontosEntre(Cuenta cuenta, Date inicio, Date fin) {
		cuenta.setDebe(movimientoService.getDebe(inicio, fin, CuentaFactory.newInstance(cuenta.getId())));
		cuenta.setHaber(movimientoService.getHaber(inicio, fin, CuentaFactory.newInstance(cuenta.getId())));
	}
	
	private Boolean crearOtra(Cuenta cuenta) {
		return cuenta.getDebe().compareTo(BigDecimal.ZERO) > 0 || cuenta.getHaber().compareTo(BigDecimal.ZERO) > 0;
	}
	
	private Cuenta getOtra(Cuenta padre) {
		Cuenta cuenta = new Cuenta();
		cuenta.setId(padre.getId());
		cuenta.setDebe(padre.getDebe());
		cuenta.setHaber(padre.getHaber());
		cuenta.setInicial(padre.getInicial());
		cuenta.setNombre("Otros");
		cuenta.setFicticia(Boolean.TRUE);
		setSaldo(cuenta);
		return cuenta;
	}
	
	private void setSaldo(Cuenta cuenta) {
		
		if (!CollectionUtils.isEmpty(cuenta.getHijas())) {
			BigDecimal debe = BigDecimal.ZERO;
			BigDecimal haber = BigDecimal.ZERO;
			BigDecimal inicial = BigDecimal.ZERO;
			for (Cuenta c : cuenta.getHijas()) {
				debe = debe.add(c.getDebe());
				haber = haber.add(c.getHaber());
				inicial = inicial.add(c.getInicial()!=null?c.getInicial():BigDecimal.ZERO);
			}
			cuenta.setDebe(debe);
			cuenta.setHaber(haber);
			cuenta.setInicial(inicial);
		}
		cuenta.setSaldo((cuenta.getDebe() != null ? cuenta.getDebe()
					: BigDecimal.ZERO).subtract((cuenta.getHaber() != null ? cuenta.getHaber()
							: BigDecimal.ZERO)));
	}
	
	private void fixId(Cuenta cuenta) {
		if (!CollectionUtils.isEmpty(cuenta.getHijas())) {
			for (Cuenta c : cuenta.getHijas()) {
				fixId(c);
			}
		}
		cuenta.setId(cuenta.getId() * -1);
	}
}
