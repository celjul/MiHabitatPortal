package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
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

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.web.dto.EstadoCuenta;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteSaldoDepartamento;
import com.bstmexico.mihabitat.web.service.EstadoCuentaService;
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
@RequestMapping(value = "administrador/reportes/saldo-departamento")
public class ReporteSaldoDepartamentoController {

	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory
			.getLogger(ReporteSaldoDepartamentoController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private ReportUtils reportUtils;

	@Autowired
	private ServletContext context;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private EstadoCuentaService estadoCuentaService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model) {
		/*model.addAttribute("periodos",
				mapper.writeValueAsString(DateUtils.getPeriodos()));*/
		return "administrador/reportes/saldo-departamento";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteSaldoDepartamento consultar(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			HttpSession session) {
		return getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), inicio, fin);
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam String formato, @RequestParam Boolean detalle,
			HttpSession session) {

		ReporteSaldoDepartamento reporte = getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), inicio, fin);
		reporte.setCondominio((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(reporte, formato, detalle);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "saldo_departamento." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] getBytes(ReporteSaldoDepartamento reporte, String formato,
			Boolean detalle) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "saldo-departamento" + File.separator;

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
				+ "saldo-departamento.jasper" : directorio
				+ "saldo-departamento-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ReporteSaldoDepartamento getReporte(Condominio condominio,
			Date inicio, Date fin) {

		ReporteSaldoDepartamento reporte = new ReporteSaldoDepartamento();
		reporte.setInicio(inicio);
		reporte.setFin(fin);

		Map map = new HashMap();
		map.put("condominio", condominio);

		for (Departamento departamento : departamentoService.search(map)) {
			EstadoCuenta estado = estadoCuentaService.getEstadoCuenta(
					condominio, departamento, inicio, fin, null);
			
			reporte.addSaldo(estado);
		}
		return reporte;
	}
}
