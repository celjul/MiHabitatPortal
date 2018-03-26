package com.bstmexico.mihabitat.web.controllers.comunes;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.bstmexico.mihabitat.comunes.personas.model.*;
import com.bstmexico.mihabitat.comunes.usuarios.service.UsuarioService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.bstmexico.mihabitat.activacion.dao.EnlaceDao;
import com.bstmexico.mihabitat.activacion.model.Enlace;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.personas.dao.PersonaDao;
import com.bstmexico.mihabitat.comunes.usuarios.dao.UsuarioDao;
import com.bstmexico.mihabitat.comunes.usuarios.factory.UsuarioFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "activacion")
public class ActivacionController {
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ActivacionController.class);
	
	@Autowired
	private EnlaceDao enlaceDao;
	
	@Autowired
	private ContactoService contactoService;
	
	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ConfigurationServiceImpl configuration;

	@RequestMapping(method = RequestMethod.GET, value = "nueva")
	public String nueva(@RequestParam Long id,
			Model model) {
		String view = "redirect:/login";
		Enlace enlace = enlaceDao.get(id);
		
		if (enlace != null) {
			Date fechaActual = getFechaActual();
			
			LOGGER.debug("Fecha de caducidad del link: " + enlace.getVigencia());
			LOGGER.debug("Fecha actual: " + fechaActual);
			LOGGER.debug("Fecha actual: " + enlace.getVigencia().compareTo(fechaActual));
			
			if (enlace.getVigencia().compareTo(fechaActual) >= 0) {
				contactoService.get(id, enlace.getEmail());
				
				Contacto contacto = contactoService.get(enlace.getContacto().getId());
				/*contacto.getCondominio().setAdministradores(null);
				contacto.getCondominio().setDireccion(null);*/
				contacto.setDepartamentos(null);
				
				if (contacto != null) {
					model.addAttribute("catalogoEmail", mapper
							.writeValueAsString(catalogoService
									.getList(CatalogoEmail.class)));
					model.addAttribute("catalogoTelefono", mapper
							.writeValueAsString(catalogoService
									.getList(CatalogoTelefono.class)));
					model.addAttribute("contacto", 
							mapper.writeValueAsString(contacto));
					model.addAttribute("enlace", 
							mapper.writeValueAsString(enlace));
					
					view = "contacto/activacion/activacion";
				} 
			} else {
				view = "contacto/activacion/caducado";
			}
		} else {
			view = "contacto/activacion/inexistente";
		}
		
		return view;
	}

	private Date getFechaActual() {
		Calendar fecha = GregorianCalendar.getInstance();
		fecha.set(Calendar.HOUR_OF_DAY, 0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.SECOND, 0);
		
		return fecha.getTime();
	}

	@RequestMapping(method = RequestMethod.POST, value = "activar/{enlace}")
	public @ResponseBody Boolean activar(@RequestBody Usuario usuario,
			@PathVariable Long enlace) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();


		Enlace link = enlaceDao.get(enlace);
		
		usuario.setPassword(encoder.encodePassword(usuario
				.getPassword(), null));
		fixPersona(usuario.getPersona());
		usuario.setActivo(Boolean.TRUE);
		usuario.addRol(configuration.getRolContacto());
		
		usuarioDao.save(usuario);

		Contacto contacto = contactoService.get(link.getContacto().getId());
		contacto.setUsuario(usuario);
		contactoService.update(contacto);

		enlaceDao.delete(link);
		return Boolean.TRUE;
	}

	private void fixPersona(Persona persona) {
		if (CollectionUtils.isNotEmpty(persona.getEmails())) {
			for (Email email : persona.getEmails()) {
				((EmailPersona) email).setPersona(persona);
			}
		}

		if (CollectionUtils.isNotEmpty(persona.getTelefonos())) {
			for (Telefono tel : persona.getTelefonos()) {
				((TelefonoPersona) tel).setPersona(persona);
			}
		}

//		if (contacto.getPersona() != null) {
//			fixPersona(contacto.getPersona());
//		}
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
