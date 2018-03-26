package com.bstmexico.mihabitat.web.controllers.comunes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.factory.UsuarioFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunes.usuarios.service.UsuarioService;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.service.ArrendatarioService;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
public class LoginController {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ArrendatarioService arrendatarioService;
	
	@Autowired
	private CondominioService condominioService;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	private DepartamentoService departamentoService;

	@RequestMapping(method = RequestMethod.GET, value = "login")
	public String login(@RequestParam(required = false) Boolean error,
			Model model) {
		if (error != null && error) {
			model.addAttribute("error", Boolean.TRUE);
		}
		return "ingreso";
	}

	@RequestMapping(method = RequestMethod.GET, value = "index")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping(method = RequestMethod.GET, value = "demo")
	public String demo(Model model) {
		return "demo";
	}

	@RequestMapping(method = RequestMethod.GET, value = "inicio")
	public String iniciar(Model model, HttpSession session) {
		User user = (User) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Usuario usuario = UsuarioFactory.newInstance();
		usuario.setUser(user.getUsername());
		
		session.setAttribute(SessionEnum.USUARIO.getValue(), 
				usuarioService.get(usuario));

		if (user.getAuthorities().size() == 1) {
			String rol = user.getAuthorities().iterator().next().getAuthority();
			session.setAttribute(SessionEnum.ROL.getValue(),
					rol);
			switch (rol) {
			case "Super Administrador":
				return "redirect:super-administrador/inicio";
			case "Administrador":
				return "redirect:administrador/inicio";
			case "Contacto":
				return "redirect:contacto/inicio";
			case "vigilante":
				return "redirect:vigilante/inicio";
			}

		} else {
			//TODO: Desarrollar pantalla para seleccionar el perfil
			return "inicio";
		}
		return "ingreso";
	}

	@RequestMapping(method = RequestMethod.GET, value = "logout")
	public String logout() {
		return "ingreso";
	}

	@RequestMapping(method = RequestMethod.GET, value = "super-administrador/inicio")
	public String iniciarSuperAdministrador() {
		return "super-administrador/inicio";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "vigilante/inicio")
	public String iniciarVigialante(
			@RequestParam(required = false) Long condominio,Model model, HttpSession session) {
		if (condominio != null) {
			for (Condominio c : (ArrayList<Condominio>) session
					.getAttribute("condominios")) {
				if (c.equals(CondominioFactory.newInstance(condominio))) {
					session.setAttribute("condominio", c);
					break;	
				}
			}
		} else {
//			Persona persona = (Persona) session.getAttribute("persona");
			Usuario usuario = (Usuario) session.getAttribute(SessionEnum.USUARIO.getValue());
			Collection<Condominio> condominios = condominioService
					.search(usuario);
			session.setAttribute(SessionEnum.CONDOMINIOS.getValue(), condominios);
			session.setAttribute(SessionEnum.CONDOMINIO.getValue(),
					condominios.size() > 0 ? condominios.iterator().next()
							: null);
		}
		Collection coll = arrendatarioService.getAll();
		List listaarrendatarios = (List)coll;
		model.addAttribute("items", listaarrendatarios);	
		return "vigilante/inicio";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "administrador/inicio")
	public String iniciarAdministrador(
			@RequestParam(required = false) Long condominio, HttpSession session) {
		if (condominio != null) {
			for (Condominio c : (ArrayList<Condominio>) session
					.getAttribute("condominios")) {
				if (c.equals(CondominioFactory.newInstance(condominio))) {
					session.setAttribute("condominio", c);
					break;	
				}
			}
		} else {
//			Persona persona = (Persona) session.getAttribute("persona");
			Usuario usuario = (Usuario) session.getAttribute(SessionEnum.USUARIO.getValue());
			Collection<Condominio> condominios = condominioService
					.search(usuario);
			session.setAttribute(SessionEnum.CONDOMINIOS.getValue(), condominios);
			session.setAttribute(SessionEnum.CONDOMINIO.getValue(),
					condominios.size() > 0 ? condominios.iterator().next()
							: null);
		}
		return "administrador/inicio";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "contacto/inicio")
	public String iniciarContacto(
			@RequestParam(required = false) Long condominio, HttpSession session) {
		if (condominio != null) {
			for (Condominio c : (ArrayList<Condominio>) session
					.getAttribute("condominios")) {
				if (c.equals(CondominioFactory.newInstance(condominio))) {
					session.setAttribute("condominio", c);
					break;
				}
			}
		} else {
			Usuario usuario = (Usuario) session.getAttribute(SessionEnum.USUARIO.getValue());
			Collection<Condominio> condominios = new ArrayList<>(departamentoService.search(usuario));
			session.setAttribute("condominios", condominios);
			session.setAttribute("condominio",
					condominios.size() > 0 ? condominios.iterator().next()
							: null);
		}
		return "contacto/inicio";
	}

	@RequestMapping(method = RequestMethod.GET, value = "recuperar-password")
	public String changePassword(@RequestParam(required = false) Boolean error,
			Model model) {
		if (error != null && error) {
			model.addAttribute("error", Boolean.TRUE);
		}
		return "recuperar-password";
	}
}
