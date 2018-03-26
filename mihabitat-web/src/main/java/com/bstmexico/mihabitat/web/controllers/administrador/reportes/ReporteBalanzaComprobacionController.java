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
import com.bstmexico.mihabitat.ingresosnoidentificados.model.MovimientoIngresoNoIdentificado;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoConceptoOtroIngreso;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoOtroIngreso;
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

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoPago;
import com.bstmexico.mihabitat.movimientos.model.MovimientoPagoProveedor;
import com.bstmexico.mihabitat.movimientos.model.MovimientoSaldo;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoDetallle;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoGasto;
import com.bstmexico.mihabitat.web.dto.reportes.Cuenta;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteBalanzaComprobacion;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/balanza-comprobacion")
public class ReporteBalanzaComprobacionController {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReporteBalanzaComprobacionController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ServletContext context;

	@Autowired
	private ReportUtils reportUtils;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model,
			@RequestParam(required = false) String inicio,
			@RequestParam(required = false) String fin) {
		model.addAttribute("inicio", mapper.writeValueAsString(inicio));
		model.addAttribute("fin", mapper.writeValueAsString(fin));
		return "administrador/reportes/balanza-comprobacion";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteBalanzaComprobacion consultar(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			HttpSession session) {
		return getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), DateUtils.getStartOfDay(inicio),
				DateUtils.getEndOfDay(fin));
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(@RequestParam String formato,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			HttpSession session) {

		ReporteBalanzaComprobacion reporte = getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), DateUtils.getStartOfDay(inicio),
				DateUtils.getEndOfDay(fin));
		reporte.setCondominio((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "balanza-comprobacion." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private byte[] getBytes(ReporteBalanzaComprobacion reporte, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "balanza-comprobacion" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		/*map.put("IMAGEN", contexto + "recursos" + File.separator + "img"
				+ File.separator + configurationServiceImpl.getLogo());*/
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

		String sourceFile = formato.equals(reportUtils.PDF) ? directorio
				+ "balanza-comprobacion.jasper" : directorio
				+ "balanza-comprobacion-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	private ReporteBalanzaComprobacion getReporte(Condominio condominio,
			Date inicio, Date fin) {
		ReporteBalanzaComprobacion reporte = new ReporteBalanzaComprobacion();
		reporte.setInicio(inicio);
		reporte.setFin(fin);

		Collection<com.bstmexico.mihabitat.cuentas.model.Cuenta> cuentas = cuentaService
				.getCuentas(condominio,
						configurationServiceImpl.getCuentasPadre());

		if (!CollectionUtils.isEmpty(cuentas)) {
			for (com.bstmexico.mihabitat.cuentas.model.Cuenta cuenta : cuentas) {
				setCuentas(cuenta, reporte);
			}
		}
		return reporte;
	}

	private void setCuentas(
			com.bstmexico.mihabitat.cuentas.model.Cuenta primer,
			ReporteBalanzaComprobacion reporte) {
		Cuenta p = getCuenta(primer);
		setInicial(p, reporte.getInicio());
		p.setNumero(primer.getNumero());
		setRetirosDepositos(p, reporte.getInicio(), reporte.getFin());
		reporte.addCuenta(p);

		if (!CollectionUtils.isEmpty(primer.getCuentasHijas())) {
			for (com.bstmexico.mihabitat.cuentas.model.Cuenta segundo : primer
					.getCuentasHijas()) {
				Cuenta s = getCuenta(segundo);
				s.setNombre(" - - - " + s.getNombre());
				s.setTipo(p.getNombre());
				s.setNumero(segundo.getNumero() + "-" + segundo.getNumeroHija());
				setInicial(s, reporte.getInicio());
				setRetirosDepositos(s, reporte.getInicio(), reporte.getFin());
				reporte.addCuenta(s);

				if (!CollectionUtils.isEmpty(segundo.getCuentasHijas())) {
					for (com.bstmexico.mihabitat.cuentas.model.Cuenta tercer : segundo
							.getCuentasHijas()) {
						Cuenta t = getCuenta(tercer);
						t.setNombre(" - - - - - - " + t.getNombre());
						t.setTipo(p.getNombre());
						t.setNumero(tercer.getNumero() + "-"
								+ tercer.getNumeroHija() + "-"
								+ tercer.getNumeroNieto());
						setInicial(t, reporte.getInicio());
						setRetirosDepositos(t, reporte.getInicio(),
								reporte.getFin());
						reporte.addCuenta(t);

						if (!CollectionUtils.isEmpty(tercer.getCuentasHijas())) {
							for (com.bstmexico.mihabitat.cuentas.model.Cuenta cuarto : tercer
									.getCuentasHijas()) {
								Cuenta c = getCuenta(cuarto);
								c.setNombre(" - - - - - - - - - "
										+ c.getNombre());
								c.setTipo(p.getNombre());
								c.setNumero(cuarto.getNumero() + "-"
										+ cuarto.getNumeroHija() + "-"
										+ cuarto.getNumeroNieto() + "-"
										+ cuarto.getNumeroBis());
								setInicial(c, reporte.getInicio());
								setRetirosDepositos(c, reporte.getInicio(),
										reporte.getFin());
								reporte.addCuenta(c);
							}
						}
					}
				}
			}
		}
	}

	public Cuenta getCuenta(com.bstmexico.mihabitat.cuentas.model.Cuenta c) {
		Cuenta cuenta = new Cuenta();
		cuenta.setId(c.getId());
		cuenta.setNombre(c.getNombre());
		cuenta.setInicial(c.getInicial());
		return cuenta;
	}

	private void setInicial(Cuenta cuenta, Date fin) {
		Collection<Movimiento> movimientos = movimientoService.getMovimientos(
				CuentaFactory.newInstance(cuenta.getId()), fin);
		procesaMovimientosIniciales(cuenta, movimientos);
	}

	private void setRetirosDepositos(Cuenta cuenta, Date inicio, Date fin) {
		Collection<Movimiento> movimientos = movimientoService.getMovimientos(
				CuentaFactory.newInstance(cuenta.getId()), inicio, fin);
		procesaMovimientos(cuenta, movimientos);
	}

	private void procesaMovimientosIniciales(Cuenta cuenta,
									Collection<Movimiento> movimientos) {
		if (!CollectionUtils.isEmpty(movimientos)) {
			for (Movimiento movimiento : movimientos) {
				if (
						movimiento instanceof MovimientoCargoAplicado
						|| movimiento instanceof MovimientoSaldo
						|| movimiento instanceof MovimientoDetallle
						|| movimiento instanceof MovimientoCfdiAplicado
								|| movimiento instanceof MovimientoConceptoOtroIngreso
								|| (movimiento instanceof MovimientoIngresoNoIdentificado &&
								(((MovimientoIngresoNoIdentificado)movimiento).getTipo().getId().equals(configurationServiceImpl.getIngresonoidentificado()) ||
										((MovimientoIngresoNoIdentificado)movimiento).getTipo().getId().equals(configurationServiceImpl.getIngresonoidentificadocancelado())))
						) {
					if (movimiento.getDebe() != null) {
						cuenta.setInicial((cuenta.getInicial() != null ? cuenta.getInicial() : BigDecimal.ZERO).add(cuenta.getDebe() != null ? cuenta
								.getDebe() : BigDecimal.ZERO).add(movimiento
								.getDebe()));
					} else if (movimiento.getHaber() != null) {
						cuenta.setInicial((cuenta.getInicial() != null ? cuenta.getInicial() : BigDecimal.ZERO).add(cuenta.getHaber() != null ? cuenta
								.getHaber() : BigDecimal.ZERO).add(movimiento
								.getHaber()));
					}
				} else if (
						movimiento instanceof MovimientoPago
						|| movimiento instanceof MovimientoPagoProveedor
						|| movimiento instanceof MovimientoGasto
								|| movimiento instanceof MovimientoOtroIngreso
								|| (movimiento instanceof MovimientoIngresoNoIdentificado &&
								(((MovimientoIngresoNoIdentificado)movimiento).getTipo().getId().equals(configurationServiceImpl.getIngresonoidentificadobanco()) ||
										((MovimientoIngresoNoIdentificado)movimiento).getTipo().getId().equals(configurationServiceImpl.getIngresonoidentificadobancocancelado())))
						/*|| movimiento instanceof MovimientoCfdiAplicado*/
						/*|| movimiento instanceof MovimientoConceptoOtroIngreso*/
						) {
					if (movimiento.getDebe() != null) {
						cuenta.setInicial((cuenta.getInicial() != null ? cuenta.getInicial() : BigDecimal.ZERO).add(cuenta.getHaber() != null ? cuenta
								.getHaber() : BigDecimal.ZERO).add(movimiento
								.getDebe()));
					} else if (movimiento.getHaber() != null) {
						cuenta.setInicial((cuenta.getInicial() != null ? cuenta.getInicial() : BigDecimal.ZERO).add(cuenta.getDebe() != null ? cuenta
								.getDebe() : BigDecimal.ZERO).add(movimiento
								.getHaber()));
					}
				}
			}
		}
	}

	/*private void procesaMovimientos(Cuenta cuenta,
			Collection<Movimiento> movimientos) {
		if (!CollectionUtils.isEmpty(movimientos)) {
			for (Movimiento movimiento : movimientos) {
				if (movimiento instanceof MovimientoCargoAplicado
						|| movimiento instanceof MovimientoSaldo

						|| movimiento instanceof MovimientoConceptoOtroIngreso

						|| movimiento instanceof MovimientoCfdiAplicado

						) {
					if (movimiento.getDebe() != null) {
						cuenta.setDebe((cuenta.getDebe() != null ? cuenta
								.getDebe() : BigDecimal.ZERO).add(movimiento
								.getDebe()));
					} else if (movimiento.getHaber() != null) {
						cuenta.setHaber((cuenta.getHaber() != null ? cuenta
								.getHaber() : BigDecimal.ZERO).add(movimiento
								.getHaber()));
					}
				} else if (movimiento instanceof MovimientoPago
						|| movimiento instanceof MovimientoPagoProveedor
						*//*|| movimiento instanceof MovimientoCfdiAplicado*//*
						|| movimiento instanceof MovimientoOtroIngreso
						*//*|| movimiento instanceof MovimientoConceptoOtroIngreso*//*) {
					if (movimiento.getDebe() != null) {
						cuenta.setHaber((cuenta.getHaber() != null ? cuenta
								.getHaber() : BigDecimal.ZERO).add(movimiento
								.getDebe()));
					} else if (movimiento.getHaber() != null) {
						cuenta.setDebe((cuenta.getDebe() != null ? cuenta
								.getDebe() : BigDecimal.ZERO).add(movimiento
								.getHaber()));
					}
				}
			}
		}
	}*/

	private void procesaMovimientos(Cuenta cuenta,
			Collection<Movimiento> movimientos) {
		if (!CollectionUtils.isEmpty(movimientos)) {
			for (Movimiento movimiento : movimientos) {
				if (
						movimiento instanceof MovimientoCargoAplicado
						|| movimiento instanceof MovimientoSaldo
						|| movimiento instanceof MovimientoDetallle
						|| movimiento instanceof MovimientoCfdiAplicado
								|| movimiento instanceof MovimientoConceptoOtroIngreso
								|| (movimiento instanceof MovimientoIngresoNoIdentificado &&
								(((MovimientoIngresoNoIdentificado)movimiento).getTipo().getId().equals(configurationServiceImpl.getIngresonoidentificado()) ||
										((MovimientoIngresoNoIdentificado)movimiento).getTipo().getId().equals(configurationServiceImpl.getIngresonoidentificadocancelado())))


						) {
					if (movimiento.getDebe() != null) {
						cuenta.setHaber((cuenta.getHaber() != null ? cuenta
								.getHaber() : BigDecimal.ZERO).add(movimiento
								.getDebe()));
					} else if (movimiento.getHaber() != null) {
						cuenta.setDebe((cuenta.getDebe() != null ? cuenta
								.getDebe() : BigDecimal.ZERO).add(movimiento
								.getHaber()));
					}
				} else if (
						movimiento instanceof MovimientoPago
						|| movimiento instanceof MovimientoPagoProveedor
						|| movimiento instanceof MovimientoGasto
								|| movimiento instanceof MovimientoOtroIngreso
								|| (movimiento instanceof MovimientoIngresoNoIdentificado &&
								(((MovimientoIngresoNoIdentificado)movimiento).getTipo().getId().equals(configurationServiceImpl.getIngresonoidentificadobanco()) ||
										((MovimientoIngresoNoIdentificado)movimiento).getTipo().getId().equals(configurationServiceImpl.getIngresonoidentificadobancocancelado())))

						//|| movimiento instanceof MovimientoCfdiAplicado
						// || movimiento instanceof MovimientoDetallle
						) {
					if (movimiento.getDebe() != null) {
						cuenta.setDebe((cuenta.getDebe() != null ? cuenta
								.getDebe() : BigDecimal.ZERO).add(movimiento
								.getDebe()));
					} else if (movimiento.getHaber() != null) {
						cuenta.setHaber((cuenta.getHaber() != null ? cuenta
								.getHaber() : BigDecimal.ZERO).add(movimiento
								.getHaber()));
					}
				}
			}
		}
	}
}
