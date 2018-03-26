package com.bstmexico.mihabitat.web.controllers.superadministrador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.direcciones.service.PaisService;
import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.service.UsuarioService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.web.service.AccountService;
//import com.bstmexico.mihabitat.usuarios.service.UsuarioService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "super-administrador")
public class CondominioController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private PaisService paisService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET, value = "/condominios/nuevo")
	public String nuevo(Model model) {
		getCatalogos(model);
		return "super-administrador/condominios/nuevo";
	}

	private void getCatalogos(Model model) {
		model.addAttribute(
				"administradores",
				mapper.writeValueAsString(usuarioService
						.getByRol(configurationServiceImpl.getRolAdministrador())));
		model.addAttribute("paises",
				mapper.writeValueAsString(paisService.getList()));
	}

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/condominios")
	public @ResponseBody Condominio guardar(@RequestBody Condominio condominio) {
		if (condominio.getId() == null) {
			accountService.guardarCondominio(condominio);
		} else {
			condominioService.update(condominio);
		}
		return condominio;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/condominios/lista")
	public String lista(Model model) {
		getCatalogos(model);
		model.addAttribute("condominios",
				mapper.writeValueAsString(condominioService.getList()));
		return "super-administrador/condominios/lista";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/condominios/actualizar/{idCondominio}")
	public String editar(@PathVariable Long idCondominio, Model model) {
		getCatalogos(model);
		model.addAttribute("condominioEditar",
				mapper.writeValueAsString(condominioService.get(idCondominio)));
		return "super-administrador/condominios/editar";
	}
}