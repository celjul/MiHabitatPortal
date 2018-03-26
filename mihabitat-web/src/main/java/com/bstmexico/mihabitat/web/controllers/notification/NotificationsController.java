package com.bstmexico.mihabitat.web.controllers.notification;

import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.notificaciones.model.Notification;
import com.bstmexico.mihabitat.departamentos.service.ConfiguracionNotificacionService;
import com.bstmexico.mihabitat.notificaciones.service.NotificationService;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pegasus on 15/06/2015.
 */
@Controller
@RequestMapping(value = "notificaciones")
public class NotificationsController {

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private NotificationHelperService notificationHelperService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ConfiguracionNotificacionService configuracionNotificacionService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @RequestMapping(method = RequestMethod.GET, value = "todas")
    public @ResponseBody
    Collection<Notification> notificacionesTodas(HttpSession session) {

        Collection<Notification> notificaciones = new ArrayList<>();

        Usuario usuario = ((Usuario) session.getAttribute(SessionEnum.USUARIO
                .getValue()));

        if(((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue())).getAdministradores().contains(usuario)) {
            notificaciones.addAll(notificationHelperService.listPendientesAdmin((Condominio) session
                    .getAttribute(SessionEnum.CONDOMINIO.getValue())));
        }
        notificaciones.addAll(notificationHelperService.listarNotificacionesContacto(
                    (Condominio) session.getAttribute("condominio"), usuario));
        return notificaciones;
    }

    @RequestMapping(method = RequestMethod.GET, value = "canales")
    public @ResponseBody
    Collection<String> canales(Model model, HttpSession session) {

        //TODO Esto es una salvajada pero no encontramos otra forma de hacerlo, mejorarlo en cuanto se pueda.
        if(notificationHelperService.getTemplate() == null){
            notificationHelperService.setTemplate(template);
        }

        Collection<String> canales = new ArrayList<>();

        /*User user = (User) SecurityContextHolder.getContext()
                .getAuthentication().obtenerPrincipal();*/
        Usuario usuario = ((Usuario) session.getAttribute(SessionEnum.USUARIO
                .getValue()));

        //String rol = "contacto";

        session.setAttribute(SessionEnum.ES_ADMINISTRADOR.getValue(), false);
        session.setAttribute(SessionEnum.ES_CONTACTO.getValue(), false);

        canales.add(String.format("todos", usuario.getId()));

        if(usuario != null && usuario.getId() != null){
            canales.add(String.format("usuario/%s", usuario.getId()));
        }
        if(((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue())).getAdministradores().contains(usuario)) {
            canales.add(String.format("condominio/%s/administradores", ((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue())).getId()));
            session.setAttribute(SessionEnum.ES_ADMINISTRADOR.getValue(), true);
        //    rol = "administrador";
        }

        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
        map.put("usuario", usuario);

        Collection<Contacto> contactos = contactoService.search(map);

        //Collection<Contacto> contactos = contactoService.get((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()), usuario.getEmail());

        if(!contactos.isEmpty()) {
            for(Contacto contacto : contactos) {
                if (contacto != null && !contacto.getDepartamentos().isEmpty()) {

                    canales.add(String.format("condominio/%s/contactos", ((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue())).getId()));
                    for (ContactoDepartamento contactoDepartamento : contacto.getDepartamentos()) {

                        session.setAttribute(SessionEnum.ES_CONTACTO.getValue(), true);

                        canales.add(String.format("departamento/%s/todos", contactoDepartamento.getDepartamento().getId()));
                        if (contactoDepartamento.getPrincipal()) {
                            canales.add(String.format("departamento/%s/principal", contactoDepartamento.getDepartamento().getId()));
                        } else if (contactoDepartamento.getHabitante()) {
                            canales.add(String.format("departamento/%s/habitantes", contactoDepartamento.getDepartamento().getId()));
                        } else if (contactoDepartamento.getPropietario()) {
                            canales.add(String.format("departamento/%s/propietarios", contactoDepartamento.getDepartamento().getId()));
                        }
                    }
                }
            }
        }

        /*for(CatalogoRol catalogoRol : usuario.getRoles()){
            if(catalogoRol.getId() == configurationService.getRolSuperAministrador().getId()){
                rol = "superadministrador";
            }
        }

        model.addAttribute("rol", rol);*/

        return canales;
    }

    @RequestMapping(method = RequestMethod.POST, value = "eliminar/{idNotificacion}")
    public @ResponseBody Notification eliminar(@PathVariable Long idNotificacion, Model model, HttpSession session) {
        Notification notification = notificationService.get(idNotificacion);
        notificationService.delete(notification);
        return notification;
    }

    @RequestMapping(method = RequestMethod.POST, value = "visto/{idNotificacion}/{visto}")
    public @ResponseBody Notification visto(@PathVariable Long idNotificacion,
                                                   @PathVariable Boolean visto, Model model, HttpSession session) {
        Notification notification = notificationService.get(idNotificacion);
        notification.setVisto(!notification.getVisto());
        notificationService.update(notification);
        return notification;
    }
}
