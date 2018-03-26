package com.bstmexico.mihabitat.web.controllers.comunes;

import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zoé Jonatan Tapia Hernández
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "zonaabierta")
public class ZonaAbiertaController {
    private static final Logger LOG = LoggerFactory
            .getLogger(ZonaAbiertaController.class);

    @Autowired
    @Qualifier("javamailservice")
    private EmailingService emailingService;

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "contacto")
    public Boolean login(@RequestParam String inpNom, @RequestParam String inpEma,
                        @RequestParam String inpTel, @RequestParam String comen,
                        Model model) {
        final Map<String, String> emailsContacto = new HashMap<>();
        if(!StringUtils.isEmpty(inpEma)) {
            emailsContacto.put(inpEma, inpNom);
        }

        final Map<String, String> emailsHola = new HashMap<>();
        emailsHola.put(configurationService.getEmailContacto(), "Contacto MiHábitat");


        final Map mapVelocity = new HashMap();
        mapVelocity.put("host", configurationService.getHost());
        mapVelocity.put("nombre", inpNom);
        mapVelocity.put("email", inpEma);
        mapVelocity.put("telefono", inpTel);
        mapVelocity.put("comentario", comen.replaceAll("(\r\n|\n)", "<br />"));


        new Thread(new Runnable() {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                if(!CollectionUtils.isEmpty(emailsContacto)) {
                    emailingService.sendEmail(emailsContacto,
                            "¡Gracias por Contactarnos!",
                            "zl-gracias-contacto.html", mapVelocity, null, "");
                }
                emailingService.sendEmail(emailsHola,
                        "Solicitud de Información",
                        "zl-nuevo-contacto.html", mapVelocity, null, "");
            }
        }).start();
        /*emailingService.sendEmail(inpEma, "¡Gracias por Contactarnos!", "zl-gracias-contacto.html", map);

        emailingService.sendEmail(configurationService.getEmailContacto(),
                "Nueva Solicitud de Contacto","zl-nuevo-contacto.html", map);*/

        return Boolean.TRUE;
    }

    @RequestMapping(method = RequestMethod.GET, value = "noencontrado")
    public String noEncontrado(Model model) {
        return "noencontrado";
    }

    @RequestMapping(method = RequestMethod.GET, value = "proximamente")
    public String proximamente(Model model) {
        return "proximamente";
    }

    @RequestMapping(method = RequestMethod.GET, value = "avisodeprivacidad")
    public String avisoDePrivacidad(Model model) {
        return "avisodeprivacidad";
    }

}
