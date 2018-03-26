package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.presupuestos.service.PresupuestoService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by Pegasus on 08/06/2015.
 */
@Controller
@RequestMapping(value = "administrador/presupuestos")
public class PresupuestoController {

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private PresupuestoService presupuestoService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.GET, value = "lista/{anio}")
    public String lista(@PathVariable Integer anio, Model model, HttpSession session) {
        //Map map = new HashMap();
        //map.put("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("presupuestos",
                mapper.writeValueAsString(
                        presupuestoService.getList((Condominio) session.getAttribute("condominio"), anio)));
        return "administrador/presupuestos/lista";
    }
}
