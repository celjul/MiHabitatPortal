package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteArrendatarios;

import com.mysql.jdbc.log.Log;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.CatalogoArrendamiento;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.service.ArrendatarioService;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import com.bstmexico.mihabitat.web.util.ReportUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;


/**
 * @author Julio Celio
 * @version 1.0
 * @since 2018
 * 
 * Controlador de Modulo Arrendamiento
 */
@Controller
@RequestMapping(value = "administrador/arrendamiento")
public class ArrendamientoController {

	@Autowired
	private ReportUtils reportUtils;
	private static final Logger LOG = LoggerFactory
			.getLogger(DepartamentoController.class);

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private ServletContext context;


	@Autowired
	private CondominioService condominioService;

    @Autowired
    private DepartamentoService departamentoService;
	
    @Autowired
    private NotificationHelperService notificationHelperService;
    
	@Autowired
	private ArrendatarioService arrendatarioService;
	
    
	//Carga en la pagina de nuevos los departamentos y redirigue a pagin nuevos desde el menu

	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		 Condominio condominio = (Condominio) session.getAttribute("condominio");	
		 List<Departamento> list  = departamentoService.searchByCond(condominio.getId());	 
		model.addAttribute("departamentos", list);
		return "administrador/arrendamiento/nuevo";
	}

	//Inserta Registro en base de Datos
	@SuppressWarnings({"static-access" })
	@RequestMapping(method = RequestMethod.POST, value = "guardar" )
	public String guardar(WebRequest request,  Model model, HttpSession session) {
		
		//tomar datos de arrendatario
		Arrendatario arrendatario = new Arrendatario();
		Condominio condominio = (Condominio) session.getAttribute("condominio");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		CatalogoArrendamiento catalogo = new CatalogoArrendamiento();
		String apellidoPaterno =request.getParameter("apellidoPaterno");
		String apMaterno =request.getParameter("apellidoMaterno");
		String nombre =request.getParameter("nombre");
		String fechaEntrada =request.getParameter("fechaEntrada");
		String fechaSalida =request.getParameter("fechaSalida");
		String placas =request.getParameter("placas");
		String numAdultos =request.getParameter("numAdultos");
		String numNinos =request.getParameter("numNinos");
		String idDepartamento= request.getParameter("id_departamento");
		Departamento departamento = departamentoService.get(Long.valueOf(idDepartamento));
		arrendatario.setApPaterno(apellidoPaterno);
		arrendatario.setCondominio(condominio);
		arrendatario.setAdministrador(usuario);
		arrendatario.setApMaterno(apMaterno);
		arrendatario.setFechaEntrada(fechaEntrada);
		arrendatario.setFechaSalida(fechaSalida);
		arrendatario.setPlacas(placas);
		arrendatario.setNombre(nombre);
		arrendatario.setNumAdultos(Integer.valueOf(numAdultos));
		arrendatario.setNumNinos(Integer.valueOf(numNinos));
		arrendatario.setDepartamento(departamento);
		catalogo.setNIdCatalogo(Long.valueOf(807));
		arrendatario.setIdStatus(catalogo);
		LocalDate localDate = LocalDate.now();
		arrendatario.setFechaRegistro(localDate.now().toString());
		//insertar datos a bdd
		arrendatarioService.save(arrendatario);
		notificationHelperService.enviarNotificacionNuevoArrendatario(arrendatario);
		 List<Departamento> list  = departamentoService.searchByCond(condominio.getId());	 
		model.addAttribute("departamentos", list);
		model.addAttribute("statusguardado",1);
		return "administrador/arrendamiento/nuevo";
	}
	
	//Trae los items de la tabla de arrendamientos para la lisa
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(WebRequest request, Model model, HttpSession session) {
		 Collection<Arrendatario> coll = arrendatarioService.getAll();
		 Condominio condominio = (Condominio) session.getAttribute("condominio");
		 Long id_condomino = null;
		 id_condomino = condominio.getId();
		 
		List<Arrendatario> listaarrendatarios = (List)coll;

		 List<Arrendatario> listaarrendatariosCondominio = new ArrayList();
		 int contador = 0;
		 while(contador<listaarrendatarios.size()) {
			 Arrendatario arrendador = new Arrendatario();
			 arrendador = (Arrendatario) listaarrendatarios.get(contador);
			 if(id_condomino == arrendador.getCondominio().getId().longValue() ){
				 listaarrendatariosCondominio.add(arrendador);
			 }
			 contador++;
		 } 
		model.addAttribute("items", listaarrendatariosCondominio);
		return "administrador/arrendamiento/lista";
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "actualizar" )
	public String actualizar(WebRequest request, Model model, HttpSession session) {
		String idArrendatario =request.getParameter("idArrendatario");
		
		Condominio condominio = (Condominio) session.getAttribute("condominio");	
		 List<Departamento> list  = departamentoService.searchByCond(condominio.getId());	 
		model.addAttribute("items", list);
		Arrendatario arrendatario = new Arrendatario();
		arrendatario = arrendatarioService.get(Long.valueOf(idArrendatario));
		arrendatario.setFechaEntrada(arrendatario.getFechaEntrada().substring(0,10));
		arrendatario.setFechaSalida(arrendatario.getFechaSalida().substring(0,10));
		model.addAttribute("arrendatarioActual",arrendatario);
		return "administrador/arrendamiento/editar";
	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	@RequestMapping(method = RequestMethod.POST, value = "update" )
	public String update(WebRequest request, Model model, HttpSession session) {
		
		Arrendatario arrendatario = new Arrendatario();
		String idarrendador= request.getParameter("idArrendador");
		Condominio condominio = (Condominio) session.getAttribute("condominio");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		CatalogoArrendamiento catalogo = new CatalogoArrendamiento();
		String apellidoPaterno =request.getParameter("apellidoPaterno");
		String apMaterno =request.getParameter("apellidoMaterno");
		String nombre =request.getParameter("nombre");
		String fechaEntrada =request.getParameter("fechaEntrada");
		String fechaSalida =request.getParameter("fechaSalida");
		String placas =request.getParameter("placas");
		String numAdultos =request.getParameter("numAdultos");
		String numNinos =request.getParameter("numNinos");
		String idstatus = request.getParameter("id_status");
		String idDepartamento= request.getParameter("id_departamento");
		Departamento departamento = departamentoService.get(Long.valueOf(idDepartamento));
		arrendatario.setIdArrendador(Long.valueOf(idarrendador));
		arrendatario.setApPaterno(apellidoPaterno);
		arrendatario.setCondominio(condominio);
		arrendatario.setAdministrador(usuario);
		arrendatario.setApMaterno(apMaterno);
		arrendatario.setFechaEntrada(fechaEntrada);
		arrendatario.setFechaSalida(fechaSalida);
		arrendatario.setPlacas(placas);
		arrendatario.setNombre(nombre);
		arrendatario.setNumAdultos(Integer.valueOf(numAdultos));
		arrendatario.setNumNinos(Integer.valueOf(numNinos));
		arrendatario.setDepartamento(departamento);
		catalogo.setNIdCatalogo(Long.valueOf(idstatus));
		arrendatario.setIdStatus(catalogo);
		LocalDate localDate = LocalDate.now();
		arrendatario.setFechaRegistro(localDate.now().toString());
		arrendatarioService.update(arrendatario);
		
		 Collection coll = arrendatarioService.getAll();

		 
		 Long id_condomino = null;
		 id_condomino = condominio.getId();
		 
		List<Arrendatario> listaarrendatarios = (List)coll;

		 List<Arrendatario> listaarrendatariosCondominio = new ArrayList();
		 int contador = 0;
		 while(contador<listaarrendatarios.size()) {
			 Arrendatario arrendador = new Arrendatario();
			 arrendador = (Arrendatario) listaarrendatarios.get(contador);
			 if(id_condomino == arrendador.getCondominio().getId().longValue() ){
				 listaarrendatariosCondominio.add(arrendador);
			 }
			 contador++;
		 } 
		model.addAttribute("items", listaarrendatariosCondominio);

		return "administrador/arrendamiento/lista";
	}

	//////////////////////////////////////////////////////////




	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	//@RequestMapping(method = RequestMethod.GET, value = "arrendamiento/{idArrendatario}")
	public ResponseEntity<byte[]> imprimir(
            @RequestParam Long idArrendatarioImpresion,
			HttpSession session) {
	    byte[] bytes = getReporte((Condominio) session.getAttribute("condominio"), idArrendatarioImpresion);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "ReporteArrendatarios.pdf";

		reportUtils.setHttpHeaders(headers, "pdf");
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	private byte[] getReporte(Condominio condominio, Long idArrendatario) {
		ReporteArrendatarios reporte = new ReporteArrendatarios();

		JRDataSource  jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "arrendatarios"+ File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		map.put("FORMATO","pdf");

		Map mapParaBusqueda = new HashMap();
		mapParaBusqueda.put("condominio", condominio);

		Arrendatario arrendatario = arrendatarioService.get(idArrendatario);
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



        reporte.setAdministrador(arrendatario.getAdministrador().getPersona().getNombreCompleto());
        reporte.setNombre(arrendatario.getNombre());
        reporte.setApMaterno(arrendatario.getApMaterno());
        reporte.setApPaterno(arrendatario.getApPaterno());
        reporte.setCondominio(arrendatario.getCondominio());
        reporte.setDepartamento(arrendatario.getDepartamento());
        reporte.setFechaSalida(arrendatario.getFechaSalida());
        reporte.setFechaEntrada(arrendatario.getFechaEntrada());
        reporte.setIdStatus(arrendatario.getIdStatus().getVDescripcion());
        reporte.setNumNinos(arrendatario.getNumNinos());
        reporte.setNumAdultos(arrendatario.getNumAdultos());
        reporte.setPlacas(arrendatario.getPlacas());
        reporte.setTorre(arrendatario.getDepartamento().getStringGrupos());


		Collection collection = new ArrayList();
		collection.add(reporte);
		jrDataSource = new JRBeanCollectionDataSource(collection);

		String sourceFile = directorio + "ReporteArrendatarios.jasper";
		return reportUtils.export("pdf", sourceFile, map, jrDataSource);




	}


}
