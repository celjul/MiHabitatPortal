package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.cargos.model.Cargo;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.web.dto.reportes.PeriodoCobranza;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCumplimientoEfectividadCobranza;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/cumplimiento-efectividad-cobranza")
public class ReporteCumplimientoEfectividadCobranzaController {

	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory
			.getLogger(ReporteCumplimientoEfectividadCobranzaController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	private ServletContext context;

	@Autowired
	private ReportUtils reportUtils;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar() {
		return "administrador/reportes/cumplimiento-efectividad-cobranza";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consultar")
	@ResponseBody
	public ReporteCumplimientoEfectividadCobranza consultar(
			@RequestParam Byte tipo,
			@RequestParam(required = false) Short anio,
			@RequestParam(required = false, value = "anios[]") Collection<Short> anios,
			HttpSession session) {
		switch (tipo) {
		case 1:
			return getReporteMeses(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), anio);
		case 2:
			return getReporteAnios(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), anios);
		default:
			throw new ServiceException();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(@RequestParam String formato,
			@RequestParam Byte tipo,
			@RequestParam(required = false) Short anio,
			@RequestParam(required = false) Collection<Short> anios,
			HttpSession session) {

		ReporteCumplimientoEfectividadCobranza reporte = null;
		switch (tipo) {
		case 1:
			reporte = getReporteMeses(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), anio);
			break;
		case 2:
			reporte = getReporteAnios(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), anios);
			break;
		}

		reporte.setCondominio((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "cumplimiento-efectividad-cobranza." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private byte[] getBytes(ReporteCumplimientoEfectividadCobranza reporte,
			String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "cumplimiento-efectividad-cobranza" + File.separator;

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
				+ "cumplimiento-efectividad-cobranza.jasper" : directorio
				+ "cumplimiento-efectividad-cobranza-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ReporteCumplimientoEfectividadCobranza getReporteMeses(
			Condominio condominio, Short anio) {

		ReporteCumplimientoEfectividadCobranza reporte = new ReporteCumplimientoEfectividadCobranza();
		reporte.setAnio(anio);

		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Cargo> cargos = cargoService.search(map,
				CargoDepartamento.class, Boolean.TRUE);
		for (Cargo cargo : cargos) {
			for (MovimientoCargo mov : ((CargoDepartamento) cargo)
					.getMovimientos()) {
				agregarPeriodoMeses(reporte, mov);
				for (MovimientoCargoAplicado apl : mov.getAplicados()) {
					if (apl.getAplicado()) {
						agregarPeriodoMeses(reporte, apl);
					}
				}
			}
		}

		for (byte i = 0; i <= 11; i++) {
			PeriodoCobranza periodo = new PeriodoCobranza();
			periodo.setId(i);
			periodo.setPeriodo((new DateFormatSymbols().getMonths()[i])
					.toUpperCase());
			reporte.addPeriodo(periodo);
		}
		Collections.sort(reporte.getPeriodos());
		return reporte;
	}

	private void agregarPeriodoMeses(
			ReporteCumplimientoEfectividadCobranza reporte,
			Movimiento movimiento) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(movimiento instanceof MovimientoCargo ? movimiento
				.getFecha()
				: movimiento instanceof MovimientoCargoAplicado ? ((MovimientoCargoAplicado) movimiento)
						.getMovimientoCargo().getFecha() : new Date());
		PeriodoCobranza periodo = new PeriodoCobranza();

		if (reporte.getAnio() == calendar.get(Calendar.YEAR)) {
			periodo.setId(Byte.valueOf(String.valueOf(calendar
					.get(Calendar.MONTH))));
			periodo.setPeriodo((new DateFormatSymbols().getMonths()[calendar
					.get(Calendar.MONTH)]).toUpperCase());

			if (movimiento instanceof MovimientoCargo) {
				periodo.setCobros(movimiento.getDebe() != null ? movimiento
						.getDebe() : movimiento.getHaber() != null ? movimiento
						.getHaber().negate() : BigDecimal.ZERO);
			} else if (movimiento instanceof MovimientoCargoAplicado) {
				if (DateUtils.getDays(movimiento.getFecha(),
						((MovimientoCargoAplicado) movimiento)
								.getMovimientoCargo().getFecha()) <= 30) {
					periodo.setPagosATiempo(movimiento.getHaber() != null ? movimiento
							.getHaber()
							: movimiento.getDebe() != null ? movimiento
									.getDebe().negate() : BigDecimal.ZERO);
				} else {
					periodo.setPagosExtemporaneos(movimiento.getHaber() != null ? movimiento
							.getHaber()
							: movimiento.getDebe() != null ? movimiento
									.getDebe().negate() : BigDecimal.ZERO);
				}
			}
			reporte.addPeriodo(periodo);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ReporteCumplimientoEfectividadCobranza getReporteAnios(
			Condominio condominio, Collection<Short> anios) {

		ReporteCumplimientoEfectividadCobranza reporte = new ReporteCumplimientoEfectividadCobranza();
		reporte.setAnios(anios);

		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Cargo> cargos = cargoService.search(map,
				CargoDepartamento.class, Boolean.TRUE);
		for (Cargo cargo : cargos) {
			for (MovimientoCargo mov : ((CargoDepartamento) cargo)
					.getMovimientos()) {
				agregarPeriodoAnios(reporte, mov);
				for (MovimientoCargoAplicado apl : mov.getAplicados()) {
					if (apl.getAplicado()) {
						agregarPeriodoAnios(reporte, apl);
					}
				}
			}
		}

		byte index = 0;
		for (Short anio : anios) {
			PeriodoCobranza periodo = new PeriodoCobranza();
			periodo.setId(index);
			periodo.setPeriodo(String.valueOf(anio));
			reporte.addPeriodo(periodo);
			index++;
		}
		Collections.sort(reporte.getPeriodos());

		return reporte;
	}

	private void agregarPeriodoAnios(
			ReporteCumplimientoEfectividadCobranza reporte,
			Movimiento movimiento) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(movimiento.getFecha());
		PeriodoCobranza periodo = new PeriodoCobranza();
		byte index = 0;

		for (Short anio : reporte.getAnios()) {
			if (anio == calendar.get(Calendar.YEAR)) {
				periodo.setId(index);
				periodo.setPeriodo(String.valueOf(anio));

				if (movimiento instanceof MovimientoCargo) {
					periodo.setCobros(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
				} else if (movimiento instanceof MovimientoCargoAplicado) {
					if (DateUtils.getDays(movimiento.getFecha(),
							((MovimientoCargoAplicado) movimiento)
									.getMovimientoCargo().getFecha()) <= 30) {
						periodo.setPagosATiempo(movimiento.getHaber() != null ? movimiento
								.getHaber()
								: movimiento.getDebe() != null ? movimiento
										.getDebe().negate() : BigDecimal.ZERO);
					} else {
						periodo.setPagosExtemporaneos(movimiento.getHaber() != null ? movimiento
								.getHaber()
								: movimiento.getDebe() != null ? movimiento
										.getDebe().negate() : BigDecimal.ZERO);
					}
				}
			}
			index++;
		}
		reporte.addPeriodo(periodo);
	}
}
