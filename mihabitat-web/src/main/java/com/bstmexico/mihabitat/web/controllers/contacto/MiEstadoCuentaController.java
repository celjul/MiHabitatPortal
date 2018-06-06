package com.bstmexico.mihabitat.web.controllers.contacto;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.web.service.EstadoCuentaService;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "contacto/mis-estados-cuenta")
public class MiEstadoCuentaController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ContactoService contactoService;



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	public String consultar(Model model, HttpSession session) {
		model.addAttribute("periodos", mapper.writeValueAsString(DateUtils.getPeriodos()));
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		map.put("usuario", (Usuario) session.getAttribute(SessionEnum.USUARIO.getValue()));
		Contacto contacto = contactoService.search(map).iterator().next();
		model.addAttribute("contacto", mapper.writeValueAsString(contacto));
		return "contacto/estado-cuenta/consulta";
	}
}
