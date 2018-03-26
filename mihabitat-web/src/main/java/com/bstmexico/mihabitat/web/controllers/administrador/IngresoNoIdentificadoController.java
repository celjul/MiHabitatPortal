package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.IngresoNoIdentificado;
import com.bstmexico.mihabitat.ingresosnoidentificados.service.IngresoNoIdentificadoService;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zoé Jonatan Tapia Hernández
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/ingresosnoidentificados")
public class IngresoNoIdentificadoController {

    private static final Logger LOG = LoggerFactory
            .getLogger(IngresoNoIdentificadoController.class);

    @Autowired
    private IngresoNoIdentificadoService ingresoNoIdentificadoService;

    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private PagoController pagoController;

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private CatalogoService catalogoService;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "nuevo")
    public String nuevo(Model model, HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));

        model.addAttribute("bancosCajas", mapper
                .writeValueAsString(cuentaService.getCuentas(
                        (Condominio) session.getAttribute("condominio"),
                        configurationServiceImpl.getCuentaCajasBancos())));
        model.addAttribute("cuentasIngresos", mapper.writeValueAsString(cuentaService
                .getCuentas((Condominio) session.getAttribute("condominio"),
                        configurationServiceImpl.getCuentaIngresos())));
        model.addAttribute("metodosPago", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoMetodoPago.class)));
        return "administrador/ingresosnoidentificados/nuevo";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public IngresoNoIdentificado guardar(@RequestBody IngresoNoIdentificado ingresoNoIdentificado, HttpSession session) {
        ingresoNoIdentificadoService.guardar(ingresoNoIdentificado);
        return ingresoNoIdentificado;
    }

    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String lista(Model model, HttpSession session) {
        model.addAttribute("ingresosNoIdentificados", mapper.writeValueAsString(ingresoNoIdentificadoService
                .listar((Condominio) session.getAttribute("condominio"))));
        return "administrador/ingresosnoidentificados/lista";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "editar/{ingresoNoIdentificadoId}")
    public String editar(@PathVariable Long ingresoNoIdentificadoId, Model model,
                         HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));

        model.addAttribute("bancosCajas", mapper
                .writeValueAsString(cuentaService.getCuentas(
                        (Condominio) session.getAttribute("condominio"),
                        configurationServiceImpl.getCuentaCajasBancos())));
        model.addAttribute("cuentasIngresos", mapper.writeValueAsString(cuentaService
                .getCuentas((Condominio) session.getAttribute("condominio"),
                        configurationServiceImpl.getCuentaIngresos())));
        model.addAttribute("metodosPago", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoMetodoPago.class)));
        model.addAttribute("ingresoNoIdentificado",
                mapper.writeValueAsString(ingresoNoIdentificadoService.get(ingresoNoIdentificadoId)));
        return "administrador/ingresosnoidentificados/editar";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "actualizar")
    public IngresoNoIdentificado actualizar(@RequestBody IngresoNoIdentificado ingresoNoIdentificado, HttpSession session) {
        ingresoNoIdentificadoService.actualizar(ingresoNoIdentificado);
        return ingresoNoIdentificado;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "cancelar/{ingresoNoIdentificadoId}")
    public IngresoNoIdentificado cancelar(@PathVariable Long ingresoNoIdentificadoId, HttpSession session) {
        IngresoNoIdentificado ingresoNoIdentificado = ingresoNoIdentificadoService.get(ingresoNoIdentificadoId);
        ingresoNoIdentificadoService.cancelar(ingresoNoIdentificado);
        return ingresoNoIdentificado;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "saldo")
    public BigDecimal getSaldo(@RequestParam Long idCuenta) {
        BigDecimal saldo = movimientoService.getHaber(new Date(),
                CuentaFactory.newInstance(idCuenta)).subtract(
                movimientoService.getDebe(new Date(),
                        CuentaFactory.newInstance(idCuenta)));
        return saldo;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "asignarIngresoNoIdentificados")
    public IngresoNoIdentificado asignarAbono(@RequestBody IngresoNoIdentificado ingresoNoIdentificado, HttpSession session) {

        session.setAttribute("ind", ingresoNoIdentificado);

        return ingresoNoIdentificado;
    }
}
