package com.bstmexico.mihabitat.web.controllers.contacto;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.CatalogoArrendamiento;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.service.ArrendatarioService;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author Julio Celio
 * @version 1.0
 * @since 2018
 * 
 * Controlador de Modulo Arrendamiento
 */
@Controller
@RequestMapping(value = "contacto/arrendamiento")
public class ArrendamientoContactoController {

	
    @Autowired
    private DepartamentoService departamentoService;
	
    
	@Autowired
	private ArrendatarioService arrendatarioService;
	
	 @Autowired
    private NotificationHelperService notificationHelperService;

    
	//Carga en la pagina de nuevos los departamentos y redirigue a pagin nuevos desde el menu

	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		 Condominio condominio = (Condominio) session.getAttribute("condominio");
		 Usuario usuario = (Usuario) session.getAttribute("usuario");
		 Long idcondominio = condominio.getId();
		List<Departamento> list = departamentoService.searchByPersona(usuario.getPersona().getId());
		List<Departamento> lista = new ArrayList<>();
		int contador =0;
		while(list.size()>contador) {
			Departamento depa = new Departamento();
			depa = departamentoService.searchByid(list.get(contador).getId());
			Long iddepaCondominio = depa.getCondominio().getId();
			if(iddepaCondominio==idcondominio) {
				lista.add(depa);
			}
			contador++;
		}
		model.addAttribute("departamentos", lista);
		return "contacto/arrendamiento/nuevo";
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
		catalogo.setNIdCatalogo(Long.valueOf(803));
		arrendatario.setIdStatus(catalogo);
		LocalDate localDate = LocalDate.now();
		arrendatario.setFechaRegistro(localDate.now().toString());
		//insertar datos a bdd
		arrendatarioService.save(arrendatario);
		notificationHelperService.enviarNotificacionNuevoArrendador(arrendatario);
			 Long idcondominio = condominio.getId();
		List<Departamento> list = departamentoService.searchByPersona(usuario.getPersona().getId());
		List<Departamento> lista = new ArrayList<>();
		int contador =0;
		while(list.size()>contador) {
			Departamento depa = new Departamento();
			depa = departamentoService.searchByid(list.get(contador).getId());
			Long iddepaCondominio = depa.getCondominio().getId();
			if(iddepaCondominio==idcondominio) {
				lista.add(depa);
			}
			contador++;
		}
 
		model.addAttribute("departamentos", lista);
		model.addAttribute("statusguardado",1);
		return "contacto/arrendamiento/nuevo";
	}
	
	//Trae los items de la tabla de arrendamientos para la lisa
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(WebRequest request, Model model, HttpSession session) {
		
		 Condominio condominio = (Condominio) session.getAttribute("condominio");
		 Usuario usuario = (Usuario) session.getAttribute("usuario");
		 List<Arrendatario> listaarrendatarios = new ArrayList();
		 Long idcondominio = condominio.getId();
			List<Departamento> list = departamentoService.searchByPersona(usuario.getPersona().getId());
			List<Departamento> lista = new ArrayList<>();
			int contador =0;
			while(list.size()>contador) {
				Departamento depa = new Departamento();
				depa = departamentoService.searchByid(list.get(contador).getId());
				Long iddepaCondominio = depa.getCondominio().getId();
				if(iddepaCondominio==idcondominio) {
					lista.add(depa);
				}
				contador++;
			}
			int cont = 0;
			while(lista.size()>cont) {
				List<Arrendatario> listaarrendadores = null; 
				listaarrendadores = arrendatarioService.getByContacto(lista.get(cont).getId());
				int contArrend = 0;
					while(contArrend<listaarrendadores.size()){
						Arrendatario arrendador = new Arrendatario();
						arrendador= listaarrendadores.get(contArrend);
						listaarrendatarios.add(arrendador);
						contArrend++;
					}
				 cont++;
			}
			
		model.addAttribute("items", listaarrendatarios);
		return "contacto/arrendamiento/lista";
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
		return "contacto/arrendamiento/editar";
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

		 List<Arrendatario> listaarrendatarios = new ArrayList();
		 Long idcondominio = condominio.getId();
			List<Departamento> list = departamentoService.searchByPersona(usuario.getPersona().getId());
			List<Departamento> lista = new ArrayList<>();
			int contador =0;
			while(list.size()>contador) {
				Departamento depa = new Departamento();
				depa = departamentoService.searchByid(list.get(contador).getId());
				Long iddepaCondominio = depa.getCondominio().getId();
				if(iddepaCondominio==idcondominio) {
					lista.add(depa);
				}
				contador++;
			}
			int cont = 0;
			while(lista.size()>cont) {
				List<Arrendatario> listaarrendadores = null; 
				listaarrendadores = arrendatarioService.getByContacto(lista.get(cont).getId());
				int contArrend = 0;
					while(contArrend<listaarrendadores.size()){
						Arrendatario arrendador = new Arrendatario();
						arrendador= listaarrendadores.get(contArrend);
						listaarrendatarios.add(arrendador);
						contArrend++;
					}
				 cont++;
			}
			
		model.addAttribute("items", listaarrendatarios);
		return "contacto/arrendamiento/lista";
	}
}