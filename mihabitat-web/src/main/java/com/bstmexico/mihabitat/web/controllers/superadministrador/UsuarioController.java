package com.bstmexico.mihabitat.web.controllers.superadministrador;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunes.usuarios.service.UsuarioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "super-administrador")
public class UsuarioController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/usuarios/nuevo")
	public String nuevo(Model model) {
		getCatalogosUsuario(model);
		return "super-administrador/usuarios/nuevo";
	}

	private void getCatalogosUsuario(Model model) {
		model.addAttribute("roles", mapper.writeValueAsString(catalogoService
				.getList(CatalogoRol.class)));
		model.addAttribute("catalogoEmail", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoEmail.class)));
		model.addAttribute("catalogoTelefono", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTelefono.class)));
	}

	@RequestMapping(method = RequestMethod.POST, value = "/usuarios")
	public @ResponseBody Usuario guardar(@RequestBody Usuario usuario) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		
		usuario.setPassword(encoder.encodePassword(usuario.getPassword(), null));
		usuarioService.save(usuario);
		return usuario;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/usuarios/lista")
	public String lista(Model model) {
		model.addAttribute("usuarios", mapper.writeValueAsString(usuarioService
				.getByRol(configurationServiceImpl.getRolSuperAministrador(), 
						configurationServiceImpl.getRolAdministrador())));
		
		getCatalogosUsuario(model);
		return "super-administrador/usuarios/lista";
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/usuarios")
	public @ResponseBody Usuario actualizar(@RequestBody Usuario usuario) {
		usuarioService.update(usuario);
		return usuario;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/usuarios/{usuario}")
	public String editar(@PathVariable Long usuario, Model model) {
		model.addAttribute("usuario",
				mapper.writeValueAsString(usuarioService.get(usuario)));
		getCatalogosUsuario(model);
		
		return "super-administrador/usuarios/editar";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/usuarios/existe")
	public @ResponseBody Usuario existe(
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String user) {
		
		if (StringUtils.isNotBlank(user)) {
			return usuarioService.getByUsername(user);
		} else {
			return usuarioService.getByEmail(email);
		}
	}
}
