package com.bstmexico.mihabitat.web.controllers.administrador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.cargos.factory.TipoConsumoFactory;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.personas.factory.EmailFactory;
import com.bstmexico.mihabitat.comunes.personas.model.*;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.EmailContacto;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.factory.UsuarioFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.GrupoCondominioService;
import com.bstmexico.mihabitat.condominios.service.MantenimientoCondominioService;
import com.bstmexico.mihabitat.contactos.factory.ContactoFactory;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.web.service.UserService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/contactos")
public class ContactoController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private MantenimientoCondominioService mantenimientoCondominioService;

	@Autowired
	private GrupoCondominioService grupoCondominioService;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private UserService userService;

	@Autowired
	private ConfigurationServiceImpl configurationService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("mantenimientos", mapper
				.writeValueAsString(mantenimientoCondominioService.search(map)));
		model.addAttribute("grupos",
				mapper.writeValueAsString(grupoCondominioService.search(map)));
		model.addAttribute("catalogoEmail", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoEmail.class)));
		model.addAttribute("catalogoTelefono", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTelefono.class)));
		return "administrador/contactos/nuevo";
	}

	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public @ResponseBody Contacto guardar(@RequestBody Contacto contacto,
			@RequestParam(required = false) String notificaciones, HttpSession session) {
		contactoService.save(contacto);
		userService.enviarLinkActivacion(notificaciones, contacto, (Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		
		return contacto;
	}

	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public @ResponseBody Contacto actualizar(@RequestBody Contacto contacto) {
		contactoService.update(contacto);
		return contacto;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute(SessionEnum
				.CONDOMINIO.getValue()));
		model.addAttribute("contactos",
				mapper.writeValueAsString(contactoService.search(map)));
		return "administrador/contactos/lista";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{contacto}")
	public String editar(@PathVariable Long contacto, Model model,
			HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute(SessionEnum
				.CONDOMINIO.getValue()));
		model.addAttribute("mantenimientos", mapper
				.writeValueAsString(mantenimientoCondominioService.search(map)));
		model.addAttribute("grupos",
				mapper.writeValueAsString(grupoCondominioService.search(map)));
		model.addAttribute("catalogoEmail", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoEmail.class)));
		model.addAttribute("catalogoTelefono", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTelefono.class)));
		model.addAttribute("contacto",
				mapper.writeValueAsString(contactoService.get(contacto)));
		return "administrador/contactos/editar";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/existe")
	public @ResponseBody Contacto existe(@RequestParam Long condominio,
			@RequestParam String email) {
		Condominio c = CondominioFactory.newInstance(condominio);
		Contacto contacto = contactoService.get(c, email);
		if (contacto == null) {
			Usuario usuario = UsuarioFactory.newInstance();
			usuario.setEmail(email);
			PersonaAbstract persona = personaService.get(usuario);
			if (persona != null) {
				contacto = ContactoFactory.newInstance(persona);
				contacto.setCondominio(c);

				Email emailContacto = EmailFactory.newInstance(EmailContacto.class);
				emailContacto.setDireccion(usuario.getEmail());
				emailContacto.setTipo((CatalogoEmail)CatalogoFactory.newInstance(CatalogoEmail.class,configurationService.getEmailPersonal()));
				Collection<Email> emails = new ArrayList<>();
				emails.add(emailContacto);
				contacto.setEmails(emails);
				contacto.setUsuario(usuario);
			}
		}
		return contacto;
	}

	@RequestMapping(method = RequestMethod.GET, value = "departamentos")
	public @ResponseBody Collection<ContactoDepartamento> getDepartamentos(
			@RequestParam Long contacto) {
		return contactoService.getDepartamentos(contacto);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "test")
	public String test() {
		return "test";
	}
}
