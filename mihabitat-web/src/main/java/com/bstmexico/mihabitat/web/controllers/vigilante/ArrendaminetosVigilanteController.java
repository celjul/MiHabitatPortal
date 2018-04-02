package com.bstmexico.mihabitat.web.controllers.vigilante;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.service.ArrendatarioService;

@Controller
@RequestMapping(value = "vigilante/arrendamiento")
public class ArrendaminetosVigilanteController {

	@Autowired
	private ArrendatarioService arrendatarioService;
	
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
		return "vigilante/arrendamiento/lista";
	}
}
