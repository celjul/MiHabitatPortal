package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteResumenDeAbonos;
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
@RequestMapping(value = "administrador/reportes/resumen-abonos")
public class ReporteResumenDeAbonosController {

	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory
			.getLogger(ReporteResumenDeAbonosController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private ReportUtils reportUtils;

	@Autowired
	private ServletContext context;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	@Qualifier("pagoserviceproxy")
	private PagoService pagoService;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model) {
		/*model.addAttribute("periodos",
				mapper.writeValueAsString(DateUtils.getPeriodos()));*/
		return "administrador/reportes/resumen-abonos";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteResumenDeAbonos consultar(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam Boolean cancelados,
			@RequestParam Boolean conSaldoFavor,

			HttpSession session) {
		return getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), inicio, fin, cancelados, conSaldoFavor);
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam String formato, @RequestParam Boolean detalle,
			@RequestParam Boolean cancelados,
			@RequestParam Boolean conSaldoFavor,
			@RequestParam Boolean usuarioComentario,
			HttpSession session) {

		ReporteResumenDeAbonos reporte = getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), inicio, fin, cancelados, conSaldoFavor);
		reporte.setCondominio((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(reporte, formato, detalle,usuarioComentario);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "detalle_abonos." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] getBytes(ReporteResumenDeAbonos reporte, String formato,
			Boolean detalle,
	        Boolean usuarioComentario ) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "resumen-abonos" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		/*map.put("IMAGEN", contexto + "recursos" + File.separator + "img"
				+ File.separator + configurationServiceImpl.getLogo());*/
		map.put("FORMATO", formato);
		map.put("DETALLE", detalle);
		map.put("USUARIO",usuarioComentario);

		Condominio condominio = condominioService.readConImagen(reporte.getCondominio().getId());

		if(condominio.getLogoCondominio() != null) {
			InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
			try{
				BufferedImage image = ImageIO.read(is);
				map.put("IMAGEN", image);
			} catch (IOException ioe) {
				LOG.error("Error leyendo logo del Condominio, se colocar� el de MH");
				File initialFile = new File(contexto + "recursos" + File.separator + "img"
						+ File.separator + configurationServiceImpl.getLogo());
				try {
					is = new FileInputStream(initialFile);
					BufferedImage image = ImageIO.read(is);
					map.put("IMAGEN", image);
				}  catch (IOException ioedos) {
					LOG.error("No se encontr� el logo de MiHabitat");
				}
			}

		} else {
			LOG.warn("No se encontr� logo del Condominio, se colocar� el de MH");
			File initialFile = new File(contexto + "recursos" + File.separator + "img"
					+ File.separator + configurationServiceImpl.getLogo());
			try {
				InputStream is = new FileInputStream(initialFile);
				BufferedImage image = ImageIO.read(is);
				map.put("IMAGEN", image);
			}  catch (IOException ioedos) {
				LOG.error("No se encontr� el logo de MiHabitat");
			}
		}

		Collection collection = new ArrayList();
		collection.add(reporte);
		jrDataSource = new JRBeanCollectionDataSource(collection);

		String sourceFile = formato.equals(reportUtils.PDF) ? directorio
				+ "resumen-abonos.jasper" : directorio
				+ "resumen-abonos-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ReporteResumenDeAbonos getReporte(Condominio condominio,
			Date inicio, Date fin, Boolean cancelados, Boolean conSaldoFavor) {

		ReporteResumenDeAbonos reporte = new ReporteResumenDeAbonos();
		reporte.setInicio(inicio);
		reporte.setFin(fin);

		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Pago> abonos = pagoService.getList(condominio,
				inicio, fin);

		if(cancelados) {
			abonos = removerCancelados(abonos);
		}
		if(!conSaldoFavor) {
			abonos = removerConSaldoFavor(abonos);
		}

		reporte.setAbonos(abonos);
		return reporte;
	}

	private Collection<Pago> removerCancelados(Collection<Pago> abonos) {
		Collection<Pago> abonosAux = new ArrayList<>();

		for(Pago pg : abonos) {
			if ((pg.getUltimoEstatus().getEstatus().getId() == configurationServiceImpl.getPagoAprobado())) {
				abonosAux.add(pg);
			}
		}

		return abonosAux;
	}

	private Collection<Pago> removerConSaldoFavor(Collection<Pago> abonos) {
		Collection<Pago> abonosAux = new ArrayList<>();

		for(Pago pg : abonos) {
			if (!(pg.getMetodoPago().getId() == configurationServiceImpl.getSaldoFavor().getId())) {
				abonosAux.add(pg);
			}
		}

		return abonosAux;
	}
}
