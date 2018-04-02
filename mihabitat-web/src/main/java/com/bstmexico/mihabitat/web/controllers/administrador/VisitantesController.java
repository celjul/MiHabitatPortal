package com.bstmexico.mihabitat.web.controllers.administrador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.bstmexico.mihabitat.mihabitat_visitantes.model.CatalogoVisitas;
import com.bstmexico.mihabitat.mihabitat_visitantes.model.Visitantes;
import com.bstmexico.mihabitat.mihabitat_visitantes.service.VisitanteService;

@Controller
@RequestMapping(value = "administrador/visitantes")
public class VisitantesController {
	
	 @Autowired
	 private DepartamentoService departamentoService;
		
	 @Autowired
	 private VisitanteService visitanteService;
	
	 
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		 Condominio condominio = (Condominio) session.getAttribute("condominio");	
		 List<Departamento> list  = departamentoService.searchByCond(condominio.getId());	 
		 model.addAttribute("departamentos", list);
		 return "administrador/visitantes/nuevo";
	}
	
	@SuppressWarnings({"static-access" })
	@RequestMapping(method = RequestMethod.POST, value = "guardar" )
	public String guardar(WebRequest request,  Model model, HttpSession session) {
		
		//tomar datos de arrendatario
		Visitantes visitante = new Visitantes();
		Condominio condominio = (Condominio) session.getAttribute("condominio");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		CatalogoVisitas catalogo = new CatalogoVisitas();
		String apellidoPaterno =request.getParameter("apellidoPaterno");
		String apMaterno =request.getParameter("apellidoMaterno");
		String nombre =request.getParameter("nombre");
		String placas =request.getParameter("placas");
		String idDepartamento= request.getParameter("id_departamento");
		Departamento departamento = departamentoService.get(Long.valueOf(idDepartamento));
		visitante.setApPaterno(apellidoPaterno);
		visitante.setCondominio(condominio);
		visitante.setAdministrador(usuario);
		visitante.setApMaterno(apMaterno);
		visitante.setPlacas(placas);
		visitante.setNombre(nombre);
		visitante.setDepartamento(departamento);
		catalogo.setNIdCatalogo(Long.valueOf(809));
		visitante.setIdStatus(catalogo);
		String localDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		visitante.setFechaEntrada(localDate);
		visitante.setFechaSalida(null);
		//insertar datos a bdd
		visitanteService.save(visitante);
		 List<Departamento> list  = departamentoService.searchByCond(condominio.getId());	 
		model.addAttribute("departamentos", list);
		model.addAttribute("statusguardado",1);
		return "administrador/visitantes/nuevo";
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(WebRequest request, Model model, HttpSession session) {
		 Collection<Visitantes> coll = visitanteService.getAll();
		 Condominio condominio = (Condominio) session.getAttribute("condominio");
		 Long id_condomino = null;
		 id_condomino = condominio.getId();
		 List<Visitantes> listavisitantes = (List)coll;
		 List<Visitantes> listaVisitantesCondominio = new ArrayList();
		 
		 int contador = 0;
		 while(contador<listavisitantes.size()) {
			 Visitantes visitante = new Visitantes();
			 visitante = (Visitantes) listavisitantes.get(contador);
			 if(id_condomino == visitante.getCondominio().getId().longValue() ){
				 listaVisitantesCondominio.add(visitante);
			 }
			 contador++;
		 } 
		 
		model.addAttribute("items", listaVisitantesCondominio);
		return "administrador/visitantes/lista";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "actualizar" )
	public String actualizar(WebRequest request, Model model, HttpSession session) {
		String idVisitante =request.getParameter("idVisitante");
		
		Condominio condominio = (Condominio) session.getAttribute("condominio");	
		 List<Departamento> list  = departamentoService.searchByCond(condominio.getId());	 
		model.addAttribute("items", list);
		Visitantes visitante = new Visitantes();
		visitante = visitanteService.get(Long.valueOf(idVisitante));
		CatalogoVisitas catalogo = new CatalogoVisitas();
		catalogo.setNIdCatalogo(Long.valueOf(810));
		visitante.setIdStatus(catalogo);
		String localDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		visitante.setFechaSalida(localDate);
		visitanteService.update(visitante);

		 Collection coll = visitanteService.getAll();
		 List listavisitantes = (List)coll;
		 

		List<Visitantes> listaVisitantesCondominio = new ArrayList();
		 Long id_condomino = null;
		 id_condomino = condominio.getId();
		 int contador = 0;
		 while(contador<listavisitantes.size()) {
			 Visitantes visitante2 = new Visitantes();
			 visitante2 = (Visitantes) listavisitantes.get(contador);
			 if(id_condomino == visitante2.getCondominio().getId().longValue() ){
				 listaVisitantesCondominio.add(visitante2);
			 }
			 contador++;
		 } 
		 
		model.addAttribute("items", listaVisitantesCondominio);

		return "administrador/visitantes/lista";

	}
	
	@SuppressWarnings({ "rawtypes", "static-access" })
	@RequestMapping(method = RequestMethod.POST, value = "update" )
	public String update(WebRequest request, Model model, HttpSession session) {
		
		Visitantes visitante = new Visitantes();
		String idVisitante= request.getParameter("idVisitante");
		Condominio condominio = (Condominio) session.getAttribute("condominio");
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		CatalogoVisitas catalogo = new CatalogoVisitas();
		String apellidoPaterno =request.getParameter("apellidoPaterno");
		String apMaterno =request.getParameter("apellidoMaterno");
		String nombre =request.getParameter("nombre");
		String placas =request.getParameter("placas");
		String fechaEntrada =request.getParameter("fechaEntrada");
		String idDepartamento= request.getParameter("id_departamento");
		Departamento departamento = departamentoService.get(Long.valueOf(idDepartamento));
		visitante.setIdArrendador(Long.valueOf(idVisitante));
		visitante.setApPaterno(apellidoPaterno);
		visitante.setCondominio(condominio);
		visitante.setAdministrador(usuario);
		visitante.setApMaterno(apMaterno);
		visitante.setFechaEntrada(fechaEntrada);
		visitante.setPlacas(placas);
		visitante.setNombre(nombre);
		visitante.setDepartamento(departamento);
		catalogo.setNIdCatalogo(Long.valueOf(810));
		visitante.setIdStatus(catalogo);
		String localDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		visitante.setFechaSalida(localDate);
		visitanteService.update(visitante);
		
		 Collection coll = visitanteService.getAll();
		 List listavisitantes = (List)coll;
		model.addAttribute("items", listavisitantes);
		return "administrador/visitantes/lista";
	}
}