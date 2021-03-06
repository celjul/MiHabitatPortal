package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
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

import com.bstmexico.mihabitat.cargos.model.Cargo;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.web.dto.reportes.Adeudo;
import com.bstmexico.mihabitat.web.dto.reportes.AdeudoAnios;
import com.bstmexico.mihabitat.web.dto.reportes.AdeudoDias;
import com.bstmexico.mihabitat.web.dto.reportes.AdeudoMeses;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteAntiguedadCuentasCobrar;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentasCobrar;
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
@RequestMapping(value = "administrador/reportes/morosidad")
public class ReporteMorosidadController {

	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory
			.getLogger(ReporteMorosidadController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private ServletContext context;

	@Autowired
	private ReportUtils reportUtils;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model,
			@RequestParam(required = false) String fin) {
		model.addAttribute("fin", mapper.writeValueAsString(fin));
		return "administrador/reportes/morosidad";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consultar")
	@ResponseBody
	public ReporteAntiguedadCuentasCobrar consultar(
			@RequestParam Byte tipo,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam(required = false) Integer anio,
			@RequestParam(required = false, value = "anios[]") Collection<Integer> anios,
			@RequestParam BigDecimal monto, @RequestParam Integer dias,
			HttpSession session) {
		ReporteAntiguedadCuentasCobrar reporte = null;
		switch (tipo) {
		case 1:
			reporte = getReporteDias(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), DateUtils.getEndOfDay(fin), monto,
					dias);
			for(Adeudo adeudo : reporte.getAdeudos()) {
				adeudo.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(DepartamentoFactory.newInstance(adeudo.getIdDepartamento()), null, fin));
			}
			return reporte;
		case 2:
			reporte = getReporteMeses(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), DateUtils.getEndOfDay(fin), anio, monto, dias);
			for(Adeudo adeudo : reporte.getAdeudos()) {
				adeudo.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(DepartamentoFactory.newInstance(adeudo.getIdDepartamento()), null, fin));
			}
			return reporte;
		case 3:
			reporte = getReporteAnios(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), DateUtils.getEndOfDay(fin), anios, monto, dias);
			for(Adeudo adeudo : reporte.getAdeudos()) {
				adeudo.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(DepartamentoFactory.newInstance(adeudo.getIdDepartamento()), null, fin));
			}
			return reporte;
		default:
			throw new ServiceException();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(
			@RequestParam String formato,
			@RequestParam Byte tipo,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam(required = false) Integer anio,
			@RequestParam(required = false) Collection<Integer> anios,
			@RequestParam Boolean contacto, @RequestParam BigDecimal monto,
			@RequestParam Integer dias, HttpSession session) {

		ReporteCuentasCobrar reporte = null;
		switch (tipo) {
		case 1:
			reporte = getReporteDias(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), DateUtils.getEndOfDay(fin), monto,
					dias);
			for(Adeudo adeudo : reporte.getAdeudos()) {
				adeudo.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(DepartamentoFactory.newInstance(adeudo.getIdDepartamento()), null, fin));
			}
			break;
		case 2:
			reporte = getReporteMeses(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), DateUtils.getEndOfDay(fin), anio, monto, dias);
			for(Adeudo adeudo : reporte.getAdeudos()) {
				adeudo.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(DepartamentoFactory.newInstance(adeudo.getIdDepartamento()), null, fin));
			}
			break;
		case 3:
			reporte = getReporteAnios(
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
							.getValue()), DateUtils.getEndOfDay(fin), anios, monto, dias);
			for(Adeudo adeudo : reporte.getAdeudos()) {
				adeudo.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(DepartamentoFactory.newInstance(adeudo.getIdDepartamento()), null, fin));
			}
			break;
		}

		reporte.setCondominio((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(tipo, contacto, reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "morosidad." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private byte[] getBytes(Byte tipo, Boolean contacto,
			ReporteCuentasCobrar reporte, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "antiguedad-cuentas-cobrar" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		/*map.put("IMAGEN", contexto + "recursos" + File.separator + "img"
				+ File.separator + configurationServiceImpl.getLogo());*/
		map.put("FORMATO", formato);
		map.put("TIPO", tipo);
		map.put("CONTACTO", contacto);
		map.put("MOROSIDAD", Boolean.TRUE);

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
				+ "antiguedad-cuentas-cobrar.jasper" : directorio
				+ "antiguedad-cuentas-cobrar-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	private ReporteAntiguedadCuentasCobrar getReporteDias(
			Condominio condominio, Date fin, BigDecimal monto, Integer dias) {

		ReporteAntiguedadCuentasCobrar reporte = new ReporteAntiguedadCuentasCobrar();
		reporte.setFin(fin);
		reporte.setMonto(monto);
		reporte.setDias(dias);

		agregarAdeudosDias(reporte, condominio);

		if (!CollectionUtils.isEmpty(reporte.getAdeudos())) {
			Iterator<Adeudo> iterator = reporte.getAdeudos().iterator();

			while (iterator.hasNext()) {
				AdeudoDias adeudo = (AdeudoDias) iterator.next();
				if (adeudo.getTotal().compareTo(reporte.getMonto()) < 0) {
					iterator.remove();
				}
			}
		}
		return reporte;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void agregarAdeudosDias(ReporteAntiguedadCuentasCobrar reporte,
			Condominio condominio) {
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Cargo> cargos = cargoService.search(map,
				CargoDepartamento.class, Boolean.FALSE);
		for (Cargo cargo : cargos) {
			/*if( (((CargoDepartamento) cargo).getFecha().before(reporte.getFin()) || ((CargoDepartamento) cargo).getFecha().equals(reporte.getFin()))
					&& ((CargoDepartamento) cargo).getSaldoPendiente(reporte.getFin()).compareTo(BigDecimal.ZERO) != 0) {
				Adeudo adeudo = new Adeudo();
				adeudo.setIdDepartamento(((CargoDepartamento) cargo).getDepartamento().getId());
				adeudo.setDepartamento(((CargoDepartamento) cargo).getDepartamento().getNombre());
				adeudo.setTorresEtiquetas(((CargoDepartamento) cargo).getDepartamento()
						.getStringGrupos());
				adeudo.setSaldo(((CargoDepartamento) cargo).getSaldoPendiente(reporte.getFin()));
				adeudo.addCargo((CargoDepartamento) cargo);
				reporte.addAdeudo(adeudo);
			}*/
			for (MovimientoCargo mov : ((CargoDepartamento) cargo)
					.getMovimientos()) {
				if (mov.getFecha().compareTo(reporte.getFin()) < 0) {
					agregarAdeudoDias(reporte, (CargoDepartamento) cargo, mov);
				}
				for (MovimientoCargoAplicado apl : mov.getAplicados()) {
					if (apl.getAplicado() && apl.getFecha().compareTo(reporte.getFin()) < 0) {
						agregarAdeudoDias(reporte, (CargoDepartamento) cargo,
								apl);
					}
				}
			}
		}
		setContacto(reporte);
	}

	private void agregarAdeudoDias(ReporteAntiguedadCuentasCobrar reporte,
			CargoDepartamento cargo, Movimiento movimiento) {
		if (DateUtils.getDays(reporte.getFin(), cargo.getFecha()) >= reporte
				.getDias()) {
			AdeudoDias adeudo = new AdeudoDias();
			adeudo.setIdDepartamento(((CargoDepartamento) cargo)
					.getDepartamento().getId());
			adeudo.setDepartamento(((CargoDepartamento) cargo)
					.getDepartamento().getNombre());
			adeudo.setTorresEtiquetas(((CargoDepartamento) cargo).getDepartamento()
					.getStringGrupos());
			Integer days = DateUtils.getDays(reporte.getFin(),
					cargo.getFecha());

			if (days < 31) {
				adeudo.set_1_30(movimiento.getDebe() != null ? movimiento
						.getDebe() : movimiento.getHaber() != null ? movimiento
						.getHaber().negate() : BigDecimal.ZERO);
			} else if (days < 61) {
				adeudo.set_31_60(movimiento.getDebe() != null ? movimiento
						.getDebe() : movimiento.getHaber() != null ? movimiento
						.getHaber().negate() : BigDecimal.ZERO);
			} else if (days < 91) {
				adeudo.set_61_90(movimiento.getDebe() != null ? movimiento
						.getDebe() : movimiento.getHaber() != null ? movimiento
						.getHaber().negate() : BigDecimal.ZERO);
			} else if (days < 181) {
				adeudo.set_91_180(movimiento.getDebe() != null ? movimiento
						.getDebe() : movimiento.getHaber() != null ? movimiento
						.getHaber().negate() : BigDecimal.ZERO);
			} else {
				adeudo.set_181(movimiento.getDebe() != null ? movimiento
						.getDebe() : movimiento.getHaber() != null ? movimiento
						.getHaber().negate() : BigDecimal.ZERO);
			}
			reporte.addAdeudoDias(adeudo);
		}
	}

	private ReporteAntiguedadCuentasCobrar getReporteMeses(
			Condominio condominio, Date fin,Integer anio, BigDecimal monto, Integer dias) {

		ReporteAntiguedadCuentasCobrar reporte = new ReporteAntiguedadCuentasCobrar();
		reporte.setAnio(anio);
		reporte.setMonto(monto);
		reporte.setDias(dias);
		reporte.setFin(fin);

		agregarAdeudosMeses(reporte, condominio, fin);

		if (!CollectionUtils.isEmpty(reporte.getAdeudos())) {
			Iterator<Adeudo> iterator = reporte.getAdeudos().iterator();

			while (iterator.hasNext()) {
				AdeudoMeses adeudo = (AdeudoMeses) iterator.next();
				if (adeudo.getTotal().compareTo(reporte.getMonto()) < 0) {
					iterator.remove();
				}
			}
		}
		return reporte;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void agregarAdeudosMeses(ReporteAntiguedadCuentasCobrar reporte,
			Condominio condominio, Date fin) {
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Cargo> cargos = cargoService.search(map,
				CargoDepartamento.class, Boolean.FALSE);
		for (Cargo cargo : cargos) {
			/*if( (((CargoDepartamento) cargo).getFecha().before(reporte.getFin()) || ((CargoDepartamento) cargo).getFecha().equals(reporte.getFin()))
					&& ((CargoDepartamento) cargo).getSaldoPendiente(reporte.getFin()).compareTo(BigDecimal.ZERO) != 0) {
				Adeudo adeudo = new Adeudo();
				adeudo.setIdDepartamento(((CargoDepartamento) cargo).getDepartamento().getId());
				adeudo.setDepartamento(((CargoDepartamento) cargo).getDepartamento().getNombre());
				adeudo.setTorresEtiquetas(((CargoDepartamento) cargo).getDepartamento()
						.getStringGrupos());
				adeudo.setSaldo(((CargoDepartamento) cargo).getSaldoPendiente(reporte.getFin()));
				adeudo.addCargo((CargoDepartamento) cargo);
				reporte.addAdeudo(adeudo);
			}*/
			for (MovimientoCargo mov : ((CargoDepartamento) cargo)
					.getMovimientos()) {
				if (mov.getFecha().compareTo(reporte.getFin()) < 0) {
					agregarAdeudoMeses(reporte, (CargoDepartamento) cargo, mov);
				}
				for (MovimientoCargoAplicado apl : mov.getAplicados()) {
					if (apl.getAplicado() && apl.getFecha().compareTo(reporte.getFin()) < 0) {
						agregarAdeudoMeses(reporte, (CargoDepartamento) cargo,
								apl);
					}
				}
			}
		}
		setContacto(reporte);
	}

	private void agregarAdeudoMeses(ReporteAntiguedadCuentasCobrar reporte,
			CargoDepartamento cargo, Movimiento movimiento) {

		if (DateUtils.getDays(reporte.getFin(),
				cargo.getFecha()) >= reporte.getDias()) {
			AdeudoMeses adeudo = new AdeudoMeses();
			adeudo.setIdDepartamento(((CargoDepartamento) cargo)
					.getDepartamento().getId());
			adeudo.setDepartamento(((CargoDepartamento) cargo)
					.getDepartamento().getNombre());
			adeudo.setTorresEtiquetas(((CargoDepartamento) cargo).getDepartamento()
					.getStringGrupos());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(cargo.getFecha());

			if (reporte.getAnio() == calendar.get(Calendar.YEAR)) {
				switch (calendar.get(Calendar.MONTH)) {
				case 0:
					adeudo.setEnero(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 1:
					adeudo.setFebrero(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 2:
					adeudo.setMarzo(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 3:
					adeudo.setAbril(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 4:
					adeudo.setMayo(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 5:
					adeudo.setJunio(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 6:
					adeudo.setJulio(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 7:
					adeudo.setAgosto(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 8:
					adeudo.setSeptiembre(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 9:
					adeudo.setOctubre(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 10:
					adeudo.setNoviembre(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				case 11:
					adeudo.setDiciembre(movimiento.getDebe() != null ? movimiento
							.getDebe()
							: movimiento.getHaber() != null ? movimiento
									.getHaber().negate() : BigDecimal.ZERO);
					break;
				}
				reporte.addAdeudoMeses(adeudo);
			}
		}
	}

	private ReporteAntiguedadCuentasCobrar getReporteAnios(
			Condominio condominio, Date fin, Collection<Integer> anios, BigDecimal monto,
			Integer dias) {
		ReporteAntiguedadCuentasCobrar reporte = new ReporteAntiguedadCuentasCobrar();
		reporte.setAnios(anios);
		reporte.setDias(dias);
		reporte.setMonto(monto);
		reporte.setFin(fin);
		
		agregarAdeudosAnios(reporte, condominio, fin, anios);

		if (!CollectionUtils.isEmpty(reporte.getAdeudos())) {
			Iterator<Adeudo> iterator = reporte.getAdeudos().iterator();

			while (iterator.hasNext()) {
				AdeudoAnios adeudo = (AdeudoAnios) iterator.next();
				if (adeudo.getTotal().compareTo(reporte.getMonto()) < 0) {
					iterator.remove();
				}
			}
		}
		return reporte;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void agregarAdeudosAnios(ReporteAntiguedadCuentasCobrar reporte,
			Condominio condominio, Date fin, Collection<Integer> anios) {
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Cargo> cargos = cargoService.search(map,
				CargoDepartamento.class, Boolean.FALSE);
		for (Cargo cargo : cargos) {
			/*if( (((CargoDepartamento) cargo).getFecha().before(reporte.getFin()) || ((CargoDepartamento) cargo).getFecha().equals(reporte.getFin()))
					&& ((CargoDepartamento) cargo).getSaldoPendiente(reporte.getFin()).compareTo(BigDecimal.ZERO) != 0) {
				Adeudo adeudo = new Adeudo();
				adeudo.setIdDepartamento(((CargoDepartamento) cargo).getDepartamento().getId());
				adeudo.setDepartamento(((CargoDepartamento) cargo).getDepartamento().getNombre());
				adeudo.setTorresEtiquetas(((CargoDepartamento) cargo).getDepartamento()
						.getStringGrupos());
				adeudo.setSaldo(((CargoDepartamento) cargo).getSaldoPendiente(reporte.getFin()));
				adeudo.addCargo((CargoDepartamento) cargo);
				reporte.addAdeudo(adeudo);
			}*/
			for (MovimientoCargo mov : ((CargoDepartamento) cargo)
					.getMovimientos()) {
				if (mov.getFecha().compareTo(reporte.getFin()) < 0) {
					agregarAdeudoAnios(reporte, (CargoDepartamento) cargo, mov,
							anios);
				}
				for (MovimientoCargoAplicado apl : mov.getAplicados()) {
					if (apl.getAplicado() && apl.getFecha().compareTo(reporte.getFin()) < 0) {
						agregarAdeudoAnios(reporte, (CargoDepartamento) cargo,
								apl, anios);
					}
				}
			}
		}
		setContacto(reporte);

	}

	private void agregarAdeudoAnios(ReporteAntiguedadCuentasCobrar reporte,
			CargoDepartamento cargo, Movimiento movimiento,
			Collection<Integer> anios) {
		if (DateUtils.getDays(reporte.getFin(), cargo.getFecha()) >= reporte
				.getDias()) {
			AdeudoAnios adeudo = new AdeudoAnios();
			adeudo.setIdDepartamento(((CargoDepartamento) cargo)
					.getDepartamento().getId());
			adeudo.setDepartamento(((CargoDepartamento) cargo)
					.getDepartamento().getNombre());
			adeudo.setTorresEtiquetas(((CargoDepartamento) cargo).getDepartamento()
					.getStringGrupos());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(cargo.getFecha());
			int index = 1;

			for (Integer anio : anios) {
				if (anio == calendar.get(Calendar.YEAR)) {
					switch (index) {
					case 1:
						adeudo.setAnio_1(movimiento.getDebe() != null ? movimiento
								.getDebe()
								: movimiento.getHaber() != null ? movimiento
										.getHaber().negate() : BigDecimal.ZERO);
						break;
					case 2:
						adeudo.setAnio_2(movimiento.getDebe() != null ? movimiento
								.getDebe()
								: movimiento.getHaber() != null ? movimiento
										.getHaber().negate() : BigDecimal.ZERO);
						break;
					case 3:
						adeudo.setAnio_3(movimiento.getDebe() != null ? movimiento
								.getDebe()
								: movimiento.getHaber() != null ? movimiento
										.getHaber().negate() : BigDecimal.ZERO);
						break;
					case 4:
						adeudo.setAnio_4(movimiento.getDebe() != null ? movimiento
								.getDebe()
								: movimiento.getHaber() != null ? movimiento
										.getHaber().negate() : BigDecimal.ZERO);
						break;
					case 5:
						adeudo.setAnio_5(movimiento.getDebe() != null ? movimiento
								.getDebe()
								: movimiento.getHaber() != null ? movimiento
										.getHaber().negate() : BigDecimal.ZERO);
						break;
					}
				}
				index++;
			}
			reporte.addAdeudoAnios(adeudo);
		}
	}

	private void setContacto(ReporteCuentasCobrar reporte) {
		if (!CollectionUtils.isEmpty(reporte.getAdeudos())) {
			for (Adeudo adeudo : reporte.getAdeudos()) {
				Departamento departamento = departamentoService.get(adeudo
						.getIdDepartamento());
				if (!CollectionUtils.isEmpty(departamento.getContactos())) {
					for (ContactoDepartamento cd : departamento.getContactos()) {
						if (cd.getPrincipal()) {
							adeudo.setContacto(cd.getContacto().getNombre()
									+ " "
									+ cd.getContacto().getApellidoPaterno());
							break;
						}
					}
				}
			}
		}
	}
}
