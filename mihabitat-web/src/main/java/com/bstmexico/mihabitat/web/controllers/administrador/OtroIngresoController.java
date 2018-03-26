package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.otrosingresos.model.OtroIngreso;
import com.bstmexico.mihabitat.otrosingresos.service.OtroIngresoService;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.web.controllers.comunes.ComprobanteController;
import com.bstmexico.mihabitat.web.dto.FileMetaData;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zoé Jonatan Tapia Hernández
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/otrosingresos")
public class OtroIngresoController {

    private static final Logger LOG = LoggerFactory
            .getLogger(OtroIngresoController.class);

    @Autowired
    private OtroIngresoService otroIngresoService;

    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private CatalogoService catalogoService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
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
        return "administrador/otrosingresos/nuevo";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public OtroIngreso guardar(@RequestBody OtroIngreso otroIngreso, HttpSession session) {
        otroIngresoService.guardar(otroIngreso);
        return otroIngreso;
    }

    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String lista(Model model, HttpSession session) {
        model.addAttribute("otrosIngresos", mapper.writeValueAsString(otroIngresoService
                .listar((Condominio) session.getAttribute("condominio"))));
        return "administrador/otrosingresos/lista";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(method = RequestMethod.GET, value = "editar/{otroIngresoId}")
    public String editar(@PathVariable Long otroIngresoId, Model model,
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
        model.addAttribute("otroIngreso",
                mapper.writeValueAsString(otroIngresoService.editar(otroIngresoId)));
        return "administrador/otrosingresos/editar";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "actualizar")
    public OtroIngreso actualizar(@RequestBody OtroIngreso otroIngreso, HttpSession session) {
        otroIngresoService.actualizar(otroIngreso);
        return otroIngreso;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "cancelar")
    public OtroIngreso cancelar(@RequestBody OtroIngreso otroIngreso, HttpSession session) {
        otroIngresoService.cancelar(otroIngreso);
        return otroIngreso;
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
}
