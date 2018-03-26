package com.bstmexico.mihabitat.web.controllers.administrador;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.CatalogoTipoCobroDepartamento;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteDirectorioDepartamento;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.personas.model.CatalogoEmail;
import com.bstmexico.mihabitat.comunes.personas.model.CatalogoTelefono;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.GrupoCondominioService;
import com.bstmexico.mihabitat.condominios.service.MantenimientoCondominioService;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.web.service.UserService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/departamentos")
public class DepartamentoController {

	private static final Logger LOG = LoggerFactory
			.getLogger(DepartamentoController.class);

	@Autowired
	private ServletContext context;

	@Autowired
	private ReportUtils reportUtils;

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private MantenimientoCondominioService mantenimientoCondominioService;

	@Autowired
	private GrupoCondominioService grupoCondominioService;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private UserService userService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		
		model.addAttribute("mantenimientos", mapper
				.writeValueAsString(mantenimientoCondominioService.search(map)));
		model.addAttribute("grupos",
				mapper.writeValueAsString(grupoCondominioService.search(map)));
		model.addAttribute("catalogoEmail", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoEmail.class)));
		model.addAttribute("catalogoTelefono", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTelefono.class)));
		model.addAttribute("contactos",
				mapper.writeValueAsString(contactoService.search(map)));
		model.addAttribute("catalogoTipoCobroDepartamento", 
				mapper.writeValueAsString(catalogoService.
						getList(CatalogoTipoCobroDepartamento.class)));
		return "administrador/departamentos/nuevo";
	}

	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public @ResponseBody Departamento guardar(
			@RequestBody Departamento departamento,
			@RequestParam(required = false) Collection<String> notificaciones,
			HttpSession session) {
		departamentoService.save(departamento);
		if(notificaciones != null) {
			userService.enviarLinkActivacion(notificaciones, departamento.getContactos(),
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		}
		
//		userService.sendEmail(notificaciones, (userService
//				.getContactoDepartamento(departamento))
//				.toArray(new Contacto[departamento.getContactos().size()]));
		return departamento;
	}

	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public @ResponseBody Departamento actualizar(
			@RequestBody Departamento departamento,
			@RequestParam(required = false) Collection<String> notificaciones,
			HttpSession session) {
		departamentoService.update(departamento);
		if(notificaciones != null) {
			userService.enviarLinkActivacion(notificaciones, departamento.getContactos(),
					(Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		}
//		userService.sendEmail(notificaciones, (userService
//				.getContactoDepartamento(departamento))
//				.toArray(new Contacto[departamento.getContactos().size()]));
		return departamento;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("departamentos",
				mapper.writeValueAsString(departamentoService.search(map)));
		return "administrador/departamentos/lista";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{departamento}")
	public String editar(@PathVariable Long departamento, Model model,
			HttpSession session) {

		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("mantenimientos", mapper
				.writeValueAsString(mantenimientoCondominioService.search(map)));
		model.addAttribute("grupos",
				mapper.writeValueAsString(grupoCondominioService.search(map)));
		model.addAttribute("catalogoEmail", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoEmail.class)));
		model.addAttribute("catalogoTelefono", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTelefono.class)));
		model.addAttribute("departamento", mapper
				.writeValueAsString(departamentoService.get(departamento)));
		model.addAttribute("contactos",
				mapper.writeValueAsString(contactoService.search(map)));
		model.addAttribute("catalogoTipoCobroDepartamento", 
				mapper.writeValueAsString(catalogoService.
						getList(CatalogoTipoCobroDepartamento.class)));

		return "administrador/departamentos/editar";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/existe")
	public @ResponseBody Departamento existe(@RequestParam Long condominio,
			@RequestParam String nombre) {
		Departamento departamento = DepartamentoFactory.newInstance();
		departamento.setCondominio(CondominioFactory.newInstance(condominio));
		departamento.setNombre(nombre);
		departamento = departamentoService.get(departamento);
		return departamento;
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(
			@RequestParam String formato,
			HttpSession session) {

		byte[] bytes = getReporte((Condominio) session.getAttribute("condominio"), formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "departamentos." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private byte[] getReporte(Condominio condominio,
							String formato) {
		ReporteDirectorioDepartamento reporte = new ReporteDirectorioDepartamento();
		reporte.setCondominio(condominio);

		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "directorio-departamentos" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		map.put("FORMATO", formato);

		Map mapParaBusqueda = new HashMap();
		mapParaBusqueda.put("condominio", condominio);
		Collection<Departamento> departamentos = departamentoService.search(mapParaBusqueda);
		condominio = condominioService.readConImagen(condominio.getId());

		if (condominio.getLogoCondominio() != null) {
			InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
			try {
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
				} catch (IOException ioedos) {
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
			} catch (IOException ioedos) {
				LOG.error("No se encontr? el logo de MiHabitat");
			}
		}

		reporte.setDepartamentos(departamentos);

		Collection collection = new ArrayList();
		collection.add(reporte);
		jrDataSource = new JRBeanCollectionDataSource(collection);

		String sourceFile = formato.equals(reportUtils.PDF) ? directorio
				+ "departamentos.jasper" : directorio
				+ "departamentos-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}
}
