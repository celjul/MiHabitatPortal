package com.bstmexico.mihabitat.web.controllers.administrador;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;
import com.bstmexico.mihabitat.condominios.service.GrupoCondominioService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/grupos")
public class GrupoController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private GrupoCondominioService grupoCondominioService;

	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo() {
		return "administrador/grupos/nuevo";
	}

	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public @ResponseBody GrupoCondominio guardar(
			@RequestBody GrupoCondominio grupo, HttpSession session) {
		grupo.setCondominio((Condominio) session.getAttribute("condominio"));
		grupoCondominioService.save(grupo);
		return grupo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public @ResponseBody GrupoCondominio actualizar(
			@RequestBody GrupoCondominio grupo, HttpSession session) {
		grupo.setCondominio((Condominio) session.getAttribute("condominio"));
		grupoCondominioService.update(grupo);
		return grupo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("grupos",
				mapper.writeValueAsString(grupoCondominioService.search(map)));
		return "administrador/grupos/lista";
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "actualizar/{mantenimiento}")
	public String editar(@PathVariable Long mantenimiento, Model model) {
		model.addAttribute("grupo", mapper
				.writeValueAsString(grupoCondominioService.get(mantenimiento)));
		return "administrador/grupos/editar";
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{grupo}")
	public @ResponseBody GrupoCondominio editar(@PathVariable Long grupo, Model model) {
		return grupoCondominioService.get(grupo);
	}
}
