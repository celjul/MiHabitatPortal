package com.bstmexico.mihabitat.web.controllers.comunes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.personas.model.CatalogoEmail;
import com.bstmexico.mihabitat.comunes.personas.model.CatalogoTelefono;
import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.factory.UsuarioFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

@Controller
public class PerfilController {
	
	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private PersonaService personaService;

	@RequestMapping(method = RequestMethod.GET, 
			value = {"super-administrador/perfil/{usuario}", 
					"administrador/perfil/{usuario}"})
	public String  miPerfil(@PathVariable String usuario, Model model) {
		model.addAttribute("roles", mapper.writeValueAsString(catalogoService
				.getList(CatalogoRol.class)));
		model.addAttribute("catalogoEmail", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoEmail.class)));
		model.addAttribute("catalogoTelefono", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTelefono.class)));
		
		Usuario usuarioObj = UsuarioFactory.newInstance();
		usuarioObj.setUser(usuario);
		
		model.addAttribute("personaJSON",  mapper.writeValueAsString(personaService
				.get(usuarioObj)));
		return "perfil/perfil";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/actualizar")
	public void actualiarPerfil() {
		
	}
}
