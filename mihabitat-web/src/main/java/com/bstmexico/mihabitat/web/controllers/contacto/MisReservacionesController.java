package com.bstmexico.mihabitat.web.controllers.contacto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoEstatusReservacion;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.bstmexico.mihabitat.instalaciones.service.InstalacionService;
import com.bstmexico.mihabitat.instalaciones.service.ReservacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.web.controllers.administrador.PagoController;
import com.bstmexico.mihabitat.web.service.PaymentService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Zo Jonatan Tapia Hernndez
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "contacto/mis-reservaciones")
public class MisReservacionesController {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory
            .getLogger(PagoController.class);

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private InstalacionService instalacionService;

    @Autowired
    @Qualifier("reservacionserviceproxy")
    private ReservacionService reservacionService;

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String lista(Model model, HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        map.put("activo", true);
        model.addAttribute("instalaciones",
                mapper.writeValueAsString(instalacionService.search(map)));
        return "contacto/instalaciones/lista";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(method = RequestMethod.GET, value = "reservar/{instalacion}")
    public String nuevo(@PathVariable Long instalacion, Model model, HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
        map.put("usuario", (Usuario) session.getAttribute(SessionEnum.USUARIO
                .getValue()));
        model.addAttribute("contactos",
                mapper.writeValueAsString(contactoService.search(map)));
        model.addAttribute("estatus",
                mapper.writeValueAsString(catalogoService.getList(CatalogoEstatusReservacion.class)));

        Instalacion instalacionObject = instalacionService
                .getConReservaciones(instalacion);
        model.addAttribute("instalacion", mapper
                .writeValueAsString(instalacionObject));
        model.addAttribute("condominio", (Condominio) session.getAttribute("condominio"));

        session.setAttribute("instalacion", instalacionObject.getId());

        return "contacto/instalaciones/reservaciones/nuevo";
    }

    @RequestMapping(method = RequestMethod.POST, value = "reservar/guardar")
    public @ResponseBody Collection<Reservacion> reservarGuardar(@RequestBody Reservacion reservacion, HttpSession session) {

        String instalacionId = session.getAttribute("instalacion").toString();

        if (reservacion.getId() == null){
            reservacionService.save(reservacion);
        }
        else {
            reservacionService.update(reservacion);
        }

        Collection<Reservacion> res = instalacionService.getConReservaciones(Long.parseLong(instalacionId)).getReservaciones();
        return res;
    }

}
