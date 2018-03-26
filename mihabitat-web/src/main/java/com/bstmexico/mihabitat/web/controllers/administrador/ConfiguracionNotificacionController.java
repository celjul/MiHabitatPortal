package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.CatalogoTipoNotificacion;
import com.bstmexico.mihabitat.departamentos.model.ConfiguracionNotificacionContacto;
import com.bstmexico.mihabitat.departamentos.service.ConfiguracionNotificacionService;
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
import java.util.Collection;

/**
 * Created by Pegasus on 15/06/2015.
 */
@Controller
@RequestMapping(value = "administrador/confnotificaciones")
public class ConfiguracionNotificacionController {

    @Autowired
    private ConfiguracionNotificacionService configuracionNotificacionService;

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String listConfiguracionContactos(
            Model model, HttpSession session) {
        Condominio condominio = ((Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                .getValue()));

        model.addAttribute("listadoConfNots",
                mapper.writeValueAsString(configuracionNotificacionService.obtenerConfiguracionNotificacion(condominio)));
        model.addAttribute("tiposNotificacion",
                mapper.writeValueAsString(catalogoService.getList(CatalogoTipoNotificacion.class)));
        return "administrador/notificaciones/configuracionnotificacion";
    }

    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public @ResponseBody Collection<ConfiguracionNotificacionContacto> guardar(
            @RequestBody ConfiguracionNotificacionContacto configuracionNotificacionContacto,
            Model model, HttpSession session) {

        Condominio condominio = ((Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                .getValue()));

        if(configuracionNotificacionContacto.getId() == null) {
            configuracionNotificacionService.save(configuracionNotificacionContacto);
        } else {
            configuracionNotificacionService.update(configuracionNotificacionContacto);
        }
        return configuracionNotificacionService.obtenerConfiguracionNotificacion(condominio);
    }


    @RequestMapping(method = RequestMethod.POST, value = "guardar/multiple")
    public @ResponseBody Collection<ConfiguracionNotificacionContacto> guardarMultiple(
            @RequestBody Collection<ConfiguracionNotificacionContacto> listaConfiguraciones,
            Model model, HttpSession session) {

        Condominio condominio = ((Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                .getValue()));
        for(ConfiguracionNotificacionContacto cnf : listaConfiguraciones) {
            if (cnf.getId() == null) {
                configuracionNotificacionService.save(cnf);
            } else {
                configuracionNotificacionService.update(cnf);
            }
        }
        return configuracionNotificacionService.obtenerConfiguracionNotificacion(condominio);
    }
}
