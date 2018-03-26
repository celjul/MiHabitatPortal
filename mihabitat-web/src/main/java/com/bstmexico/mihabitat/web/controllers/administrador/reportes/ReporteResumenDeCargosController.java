package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteResumenDeCargos;
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
@RequestMapping(value = "administrador/reportes/resumen-cargos")
public class ReporteResumenDeCargosController {

	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory
			.getLogger(ReporteResumenDeCargosController.class);

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
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model) {
		/*model.addAttribute("periodos",
				mapper.writeValueAsString(DateUtils.getPeriodos()));*/
		return "administrador/reportes/resumen-cargos";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteResumenDeCargos consultar(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam Boolean cancelados,
			HttpSession session) {
		return getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), inicio, fin, cancelados);
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam String formato, @RequestParam Boolean detalle,
			@RequestParam Boolean cancelados,
			HttpSession session) {

		ReporteResumenDeCargos reporte = getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), inicio, fin, cancelados);
		reporte.setCondominio((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(reporte, formato, detalle);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "detalle_cargos." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] getBytes(ReporteResumenDeCargos reporte, String formato,
			Boolean detalle) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "resumen-cargos" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		/*map.put("IMAGEN", contexto + "recursos" + File.separator + "img"
				+ File.separator + configurationServiceImpl.getLogo());*/
		map.put("FORMATO", formato);
		map.put("DETALLE", detalle);

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
				+ "resumen-cargos.jasper" : directorio
				+ "resumen-cargos-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ReporteResumenDeCargos getReporte(Condominio condominio,
			Date inicio, Date fin, Boolean cancelados) {

		ReporteResumenDeCargos reporte = new ReporteResumenDeCargos();
		reporte.setInicio(inicio);
		reporte.setFin(fin);

		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<CargoDepartamento> cargos = cargoService.getList(condominio,
				inicio, fin);

		if(!cancelados) {
			cargos = removerCancelados(cargos);
		}

		reporte.setCargos(cargos);
		return reporte;
	}

	private Collection<CargoDepartamento> removerCancelados(Collection<CargoDepartamento> cargos) {
		Collection<CargoDepartamento> cargosAux = new ArrayList<>();

		for(CargoDepartamento cd : cargos) {
			if (!cd.getCargo().getCancelado()) {
				cargosAux.add(cd);
			}
		}

		return cargosAux;
	}
}
