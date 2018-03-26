package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.condominios.factory.ConfiguracionCondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.ConfiguracionCondominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.condominios.service.ConfiguracionCondominioService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

//import com.bstmexico.mihabitat.usuarios.service.UsuarioService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/condominios")
public class ConfiguracionCondominioController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	/*@Autowired
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
	private AccountService accountService;*/

	@Autowired
	private ConfiguracionCondominioService configuracionCondominioService;

	@Autowired
	private CondominioService condominioService;

	/*@RequestMapping(method = RequestMethod.GET, value = "/condominios/nuevo")
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
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "configuracion")
	public String editar(Model model, HttpSession session) {

		ConfiguracionCondominio configuracionCondominio = condominioService.readConConfiguracion(((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue())).getId()).getConfiguracionCondominio();

		if(configuracionCondominio == null) {
			configuracionCondominio = ConfiguracionCondominioFactory.newInstance();
		}

		model.addAttribute("configuracionCondominio",
				mapper.writeValueAsString(configuracionCondominio));

		return "administrador/condominioconfiguracion/editar";
	}

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = "/configuracion/guardar")
	public @ResponseBody ConfiguracionCondominio guardar(@RequestBody ConfiguracionCondominio configuracionCondominio,
														 HttpSession session) {

		Condominio condominio = ((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		if (configuracionCondominio.getId() == null) {
			configuracionCondominioService.save(configuracionCondominio);
			condominio.setConfiguracionCondominio(configuracionCondominio);
			condominioService.update(condominio);
		} else {
			configuracionCondominioService.update(configuracionCondominio);
		}
		return configuracionCondominio;
	}

}