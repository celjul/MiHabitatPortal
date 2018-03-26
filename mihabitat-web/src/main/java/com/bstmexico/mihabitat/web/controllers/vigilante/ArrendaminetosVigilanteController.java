package com.bstmexico.mihabitat.web.controllers.vigilante;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.bstmexico.mihabitat.mihabitat_arrendamiento.service.ArrendatarioService;

@Controller
@RequestMapping(value = "vigilante/arrendamiento")
public class ArrendaminetosVigilanteController {

	@Autowired
	private ArrendatarioService arrendatarioService;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(WebRequest request, Model model, HttpSession session) {
		 Collection coll = arrendatarioService.getAll();
		 List listaarrendatarios = (List)coll;
		model.addAttribute("items", listaarrendatarios);
		return "vigilante/arrendamiento/lista";
	}
}
