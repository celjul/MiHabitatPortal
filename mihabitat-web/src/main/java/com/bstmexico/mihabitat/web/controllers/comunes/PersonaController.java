package com.bstmexico.mihabitat.web.controllers.comunes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.bstmexico.mihabitat.comunes.personas.service.EmailPersonaService;
import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.personas.service.TelefonoPersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.factory.UsuarioFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "personas")
public class PersonaController {

	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private TelefonoPersonaService telefonoPersonaService;
	
	@Autowired
	private EmailPersonaService emailPersonaService;

	@RequestMapping(method = RequestMethod.POST, value = "/existe")
	public @ResponseBody PersonaAbstract existe(
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String user) {
		Usuario usuario = UsuarioFactory.newInstance();
		usuario.setEmail(email);
		usuario.setUser(user);
		return personaService.get(usuario);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{persona}/emails/{email}")
	public @ResponseBody boolean borrarEmail(@PathVariable Long persona, @PathVariable Long email) {
		emailPersonaService.delete(email);
		
		return true;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{persona}/telefonos/{telefono}")
	public @ResponseBody boolean borrarTelefono(@PathVariable Long persona, @PathVariable Long telefono) {
		telefonoPersonaService.delete(telefono);
		
		return true;
	}
}
