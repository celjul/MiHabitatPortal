package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import com.bstmexico.mihabitat.proveedores.model.DetalleFactura;
import com.bstmexico.mihabitat.proveedores.service.FacturaPorPagarService;
import com.bstmexico.mihabitat.web.dto.reportes.AdeudoAniosProveedor;
import com.bstmexico.mihabitat.web.dto.reportes.AdeudoDiasProveedor;
import com.bstmexico.mihabitat.web.dto.reportes.AdeudoMesesProveedor;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteAntiguedadCuentasPagar;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentasPagar;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/antiguedad-cuentas-pagar")
public class ReporteAntiguedadCuentasPagarController {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReporteAntiguedadCuentasPagarController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private FacturaPorPagarService facturaPorPagarService;

	@Autowired
	private ServletContext context;

	@Autowired
	private ReportUtils reportUtils;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model, @RequestParam(required = false) String fin) {
		model.addAttribute("fin", mapper.writeValueAsString(fin));
		return "administrador/reportes/antiguedad-cuentas-pagar";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consultar")
	@ResponseBody
	public ReporteAntiguedadCuentasPagar consultar(@RequestParam Byte tipo,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam(required = false) Integer anio,
			@RequestParam(required = false, value = "anios[]") Collection<Integer> anios, HttpSession session) {
		switch (tipo) {
		case 1:
			return getReporteDias((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
					DateUtils.getEndOfDay(fin));
		case 2:
			return getReporteMeses((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()), anio);
		case 3:
			return getReporteAnios((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()), anios);
		default:
			throw new ServiceException();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(@RequestParam String formato, @RequestParam Byte tipo,
			@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam(required = false) Integer anio, @RequestParam(required = false) Collection<Integer> anios,
			HttpSession session) {

		ReporteCuentasPagar reporte = null;
		switch (tipo) {
		case 1:
			reporte = getReporteDias((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
					DateUtils.getEndOfDay(fin));
			break;
		case 2:
			reporte = getReporteMeses((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()), anio);
			break;
		case 3:
			reporte = getReporteAnios((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()), anios);
			break;
		}

		reporte.setCondominio((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(tipo, reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "antiguedad_cuentas_pagar." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private byte[] getBytes(Byte tipo, ReporteCuentasPagar reporte, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator + "antiguedad-cuentas-pagar" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		//map.put("IMAGEN", contexto + "recursos" + File.separator + "img" + File.separator + configurationServiceImpl.getLogo());
		map.put("FORMATO", formato);
		map.put("TIPO", tipo);

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

		String sourceFile = formato.equals(reportUtils.PDF) ? directorio + "antiguedad-cuentas-pagar.jasper"
				: directorio + "antiguedad-cuentas-pagar-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	private ReporteAntiguedadCuentasPagar getReporteDias(Condominio condominio, Date fin) {

		ReporteAntiguedadCuentasPagar reporte = new ReporteAntiguedadCuentasPagar();
		reporte.setFin(fin);
		agregarAdeudosDias(reporte, condominio);
		return reporte;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void agregarAdeudosDias(ReporteAntiguedadCuentasPagar reporte, Condominio condominio) {
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Cfdi> facturas = facturaPorPagarService.search(map);

		for (Cfdi cfdi : facturas) {
			for (DetalleFactura detalle : cfdi.getConceptos()) {
				if (detalle.getMovimientoCfdi().getFecha().compareTo(reporte.getFin()) < 0) {
					agregarAdeudoDias(reporte, cfdi, detalle.getMovimientoCfdi());
				}
				for (MovimientoCfdiAplicado apl : detalle.getMovimientoCfdi().getAplicados()) {
					if (apl.getAplicado() && apl.getFecha().compareTo(reporte.getFin()) < 0) {
						agregarAdeudoDias(reporte, cfdi, apl);
					}
				}
			}
		}
	}

	private void agregarAdeudoDias(ReporteAntiguedadCuentasPagar reporte, Cfdi cfdi, Movimiento movimiento) {
		AdeudoDiasProveedor adeudo = new AdeudoDiasProveedor();
		adeudo.setIdProveedor(cfdi.getProveedor().getId());
		adeudo.setProveedor(cfdi.getProveedor().getNombre());
		Integer days = DateUtils.getDays(reporte.getFin(), movimiento.getFecha());

		if (days < 31) {
			adeudo.set_1_30(movimiento.getDebe() != null ? movimiento.getDebe().negate()
					: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
		} else if (days < 61) {
			adeudo.set_31_60(movimiento.getDebe() != null ? movimiento.getDebe().negate()
					: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
		} else if (days < 91) {
			adeudo.set_61_90(movimiento.getDebe() != null ? movimiento.getDebe().negate()
					: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
		} else if (days < 181) {
			adeudo.set_91_180(movimiento.getDebe() != null ? movimiento.getDebe().negate()
					: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
		} else {
			adeudo.set_181(movimiento.getDebe() != null ? movimiento.getDebe().negate()
					: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
		}
		reporte.addAdeudoDias(adeudo);
	}

	private ReporteAntiguedadCuentasPagar getReporteMeses(Condominio condominio, Integer anio) {

		ReporteAntiguedadCuentasPagar reporte = new ReporteAntiguedadCuentasPagar();
		reporte.setAnio(anio);

		agregarAdeudosMeses(reporte, condominio);
		return reporte;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void agregarAdeudosMeses(ReporteAntiguedadCuentasPagar reporte, Condominio condominio) {
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Cfdi> facturas = facturaPorPagarService.search(map);
		for (Cfdi cfdi : facturas) {
			for (DetalleFactura detalle : cfdi.getConceptos()) {
				agregarAdeudoMeses(reporte, cfdi, detalle.getMovimientoCfdi());
				for (MovimientoCfdiAplicado apl : detalle.getMovimientoCfdi().getAplicados()) {
					if (apl.getAplicado()) {
						agregarAdeudoMeses(reporte, cfdi, detalle.getMovimientoCfdi());
					}
				}
			}
		}
	}

	private void agregarAdeudoMeses(ReporteAntiguedadCuentasPagar reporte, Cfdi cfdi, Movimiento movimiento) {
		AdeudoMesesProveedor adeudo = new AdeudoMesesProveedor();
		adeudo.setIdProveedor(cfdi.getProveedor().getId());
		adeudo.setProveedor(cfdi.getProveedor().getNombre());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(movimiento.getFecha());

		if (reporte.getAnio() == calendar.get(Calendar.YEAR)) {
			switch (calendar.get(Calendar.MONTH)) {
			case 0:
				adeudo.setEnero(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 1:
				adeudo.setFebrero(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 2:
				adeudo.setMarzo(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 3:
				adeudo.setAbril(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 4:
				adeudo.setMayo(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 5:
				adeudo.setJunio(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 6:
				adeudo.setJulio(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 7:
				adeudo.setAgosto(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 8:
				adeudo.setSeptiembre(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 9:
				adeudo.setOctubre(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 10:
				adeudo.setNoviembre(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			case 11:
				adeudo.setDiciembre(movimiento.getDebe() != null ? movimiento.getDebe().negate()
						: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
				break;
			}
			reporte.addAdeudoMeses(adeudo);
		}
	}

	private ReporteAntiguedadCuentasPagar getReporteAnios(Condominio condominio, Collection<Integer> anios) {
		ReporteAntiguedadCuentasPagar reporte = new ReporteAntiguedadCuentasPagar();
		reporte.setAnios(anios);
		agregarAdeudosAnios(reporte, condominio, anios);
		return reporte;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void agregarAdeudosAnios(ReporteAntiguedadCuentasPagar reporte, Condominio condominio,
			Collection<Integer> anios) {
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Cfdi> facturas = facturaPorPagarService.search(map);
		for (Cfdi cfdi : facturas) {
			for (DetalleFactura detalle : cfdi.getConceptos()) {
				agregarAdeudoAnios(reporte, cfdi, detalle.getMovimientoCfdi(), anios);
				for (MovimientoCfdiAplicado apl : detalle.getMovimientoCfdi().getAplicados()) {
					if (apl.getAplicado()) {
						agregarAdeudoAnios(reporte, cfdi, detalle.getMovimientoCfdi(), anios);
					}
				}
			}
		}
	}

	private void agregarAdeudoAnios(ReporteAntiguedadCuentasPagar reporte, Cfdi cfdi, Movimiento movimiento,
			Collection<Integer> anios) {
		AdeudoAniosProveedor adeudo = new AdeudoAniosProveedor();
		adeudo.setIdProveedor(cfdi.getProveedor().getId());
		adeudo.setProveedor(cfdi.getProveedor().getNombre());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(movimiento.getFecha());
		int index = 1;

		for (Integer anio : anios) {
			if (anio == calendar.get(Calendar.YEAR)) {
				switch (index) {
				case 1:
					adeudo.setAnio_1(movimiento.getDebe() != null ? movimiento.getDebe().negate()
							: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
					break;
				case 2:
					adeudo.setAnio_2(movimiento.getDebe() != null ? movimiento.getDebe().negate()
							: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
					break;
				case 3:
					adeudo.setAnio_3(movimiento.getDebe() != null ? movimiento.getDebe().negate()
							: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
					break;
				case 4:
					adeudo.setAnio_4(movimiento.getDebe() != null ? movimiento.getDebe().negate()
							: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
					break;
				case 5:
					adeudo.setAnio_5(movimiento.getDebe() != null ? movimiento.getDebe().negate()
							: movimiento.getHaber() != null ? movimiento.getHaber() : BigDecimal.ZERO);
					break;
				}
			}
			index++;
		}
		reporte.addAdeudoAnios(adeudo);
	}
}
