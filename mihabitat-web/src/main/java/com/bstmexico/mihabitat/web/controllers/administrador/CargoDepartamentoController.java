package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.cargos.factory.CargoFactory;
import com.bstmexico.mihabitat.cargos.factory.TipoConsumoFactory;
import com.bstmexico.mihabitat.cargos.model.CargoAgrupador;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.model.CatalogoCargo;
import com.bstmexico.mihabitat.cargos.model.CatalogoInteres;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.cargos.service.TipoConsumoService;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.CatalogoTipoCobroDepartamento;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.web.exceptions.GenericController;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/cargos-departamentos")
public class CargoDepartamentoController extends GenericController {

    @Autowired
    @Qualifier("cargoserviceproxy")
    private CargoService cargoService;

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private TipoConsumoService tipoConsumoService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

	/*@Autowired
    private SimpMessagingTemplate template;*/

	/*@Autowired
    private NotificationHelperService notificationService;*/

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory
            .getLogger(CargoDepartamentoController.class);

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "nuevo")
    public String nuevo(Model model, HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("departamentos", mapper.writeValueAsString(departamentoService.search(map)));
        model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService.getCuentas((Condominio) session.getAttribute("condominio"), configurationServiceImpl.getCuentaIngresos())));
        model.addAttribute("catalogoCargo", mapper.writeValueAsString(catalogoService.getList(CatalogoCargo.class)));
        model.addAttribute("consumos", mapper.writeValueAsString(tipoConsumoService.search(map)));
        model.addAttribute("catalogoInteres", mapper.writeValueAsString(catalogoService.getList(CatalogoInteres.class)));
        model.addAttribute("catalogoTipoCobroDepartamento", mapper.writeValueAsString(catalogoService.getList(CatalogoTipoCobroDepartamento.class)));
        return "administrador/cargos/agrupador/nuevo";
    }

    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public
    @ResponseBody
    CargoDepartamento guardar(
            @RequestBody CargoDepartamento cargo) {
        cargoService.save(cargo);
        //notificationService.enviarNotificacionNuevosCargos(cargo);
        return cargo;
    }

    @RequestMapping(method = RequestMethod.POST, value = "agrupador/guardar")
    public
    @ResponseBody
    CargoAgrupador guardar(
            @RequestBody CargoAgrupador cargo) {
        cargoService.save(cargo);
        //notificationService.enviarNotificacionNuevosCargos(cargo);
        return cargo;
    }

    @RequestMapping(method = RequestMethod.POST, value = "agrupador/actualizar")
    public
    @ResponseBody
    CargoAgrupador actualizar(
            @RequestBody CargoAgrupador cargo) {
        cargoService.update(cargo);
        return cargo;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String lista(Model model, HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("cargos", mapper.writeValueAsString(cargoService.search(map, CargoDepartamento.class, Boolean.FALSE)));
        return "administrador/cargos/departamentos/lista";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(method = RequestMethod.GET, value = "listaConFechas")
    @ResponseBody
    public Collection<CargoDepartamento> listaConFechas(Model model, HttpSession session,
                                                        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
                                                        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin) {
        /*Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("cargos", mapper.writeValueAsString(cargoService
				.search(map, CargoDepartamento.class, Boolean.FALSE)));
		return "administrador/cargos/departamentos/lista";*/

        Collection<CargoDepartamento> cargos = cargoService.getList((Condominio) session.getAttribute("condominio"),
                inicio, fin);

        return cargos;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "agrupador/actualizar/{cargo}")
    public String editar(@PathVariable Long cargo, Model model,
                         HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("departamentos", mapper.writeValueAsString(departamentoService.search(map)));
        model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService.getCuentas((Condominio) session.getAttribute("condominio"), configurationServiceImpl.getCuentaIngresos())));
        model.addAttribute("catalogoCargo", mapper.writeValueAsString(catalogoService.getList(CatalogoCargo.class)));
        model.addAttribute("consumos", mapper.writeValueAsString(tipoConsumoService.search(map)));
        model.addAttribute("cargo", mapper.writeValueAsString(cargoService.get(cargo, CargoAgrupador.class, Boolean.FALSE)));
        model.addAttribute("catalogoInteres", mapper.writeValueAsString(catalogoService.getList(CatalogoInteres.class)));
        model.addAttribute("catalogoTipoCobroDepartamento", mapper.writeValueAsString(catalogoService.getList(CatalogoTipoCobroDepartamento.class)));
        return "administrador/cargos/agrupador/editar";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "actualizar/{cargo}")
    public String editarIndividual(@PathVariable Long cargo, Model model,
                                   HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService.getCuentas((Condominio) session.getAttribute("condominio"), configurationServiceImpl.getCuentaIngresos())));
        model.addAttribute("cargo", mapper.writeValueAsString(cargoService.get(cargo, CargoDepartamento.class, Boolean.FALSE)));
        model.addAttribute("consumos", mapper.writeValueAsString(tipoConsumoService.search(map)));
        model.addAttribute("catalogoInteres", mapper.writeValueAsString(catalogoService.getList(CatalogoInteres.class)));
        model.addAttribute("catalogoTipoCobroDepartamento", mapper.writeValueAsString(catalogoService.getList(CatalogoTipoCobroDepartamento.class)));

        return "administrador/cargos/departamentos/editar";
    }

    @RequestMapping(method = RequestMethod.POST, value = "actualizar")
    public
    @ResponseBody
    CargoDepartamento actualizarIndividual(
            @RequestBody CargoDepartamento cargo) {

        cargoService.update(cargo);
        return cargo;
    }

    @RequestMapping(method = RequestMethod.POST, value = "agrupador/anterior")
    public
    @ResponseBody
    CargoAgrupador getAnterior(@RequestParam Long tipo,
                               HttpSession session) {
        return cargoService.getAnterior(
                (Condominio) session.getAttribute("condominio"),
                TipoConsumoFactory.newInstance(tipo));
    }

    @RequestMapping(method = RequestMethod.POST, value = "descuento/nuevo")
    public
    @ResponseBody
    Boolean generarDescuento(
            @RequestBody MovimientoCargo descuento) {
        cargoService.generarDescuento(descuento);
        return Boolean.TRUE;
    }

    @RequestMapping(method = RequestMethod.POST, value = "recargo/nuevo")
    public
    @ResponseBody
    Boolean generarRecargo(
            @RequestBody MovimientoCargo recargo) {
        cargoService.generarRecargo(recargo);
        return Boolean.TRUE;
    }

    @RequestMapping(method = RequestMethod.POST, value = "descuento/cancelar")
    public
    @ResponseBody
    Boolean cancelarDescuento(
            @RequestBody MovimientoCargo descuento) {
        cargoService.cancelarDescuento(descuento);
        return Boolean.TRUE;
    }

    @RequestMapping(method = RequestMethod.POST, value = "recargo/cancelar")
    public
    @ResponseBody
    Boolean cancelarRecargo(
            @RequestBody MovimientoCargo recargo) {
        cargoService.cancelarRecargo(recargo);
        return Boolean.TRUE;
    }

    @RequestMapping(method = RequestMethod.POST, value = "cancelar")
    public
    @ResponseBody
    Boolean cancelarCargo(@RequestParam Long idCargo) {
        cargoService.cancelarCargo((CargoDepartamento) CargoFactory
                .newInstance(CargoDepartamento.class, idCargo));
        return Boolean.TRUE;
    }
}
