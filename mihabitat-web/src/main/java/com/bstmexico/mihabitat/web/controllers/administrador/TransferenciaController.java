package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.transferencias.model.Transferencia;
import com.bstmexico.mihabitat.transferencias.service.TransferenciaService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Eduardo Prieto Islas
 * @version 1.0
 * @created 2015
 */



@Controller
@RequestMapping(value = "administrador/transferencias")
public class TransferenciaController {
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;
    @Autowired
    private HibernateAwareObjectMapper mapper;
    @Autowired
    private CatalogoService catalogoService;
    @Autowired
    private TransferenciaService transferenciaService;

    //estatus de informacion
    private static final Logger LOG = LoggerFactory
            .getLogger(OtroIngresoController.class);


    //nueva transaccion
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(method = RequestMethod.GET, value = "nuevo")

    public String nuevo(Model model, HttpSession session) {

        model.addAttribute("bancosCajas", mapper
                .writeValueAsString(cuentaService.getCuentas(
                        (Condominio) session.getAttribute("condominio"),
                        configurationServiceImpl.getCuentaCajasBancos())));
            model.addAttribute("metodosTransferencia", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoMetodoPago.class)));
        return "administrador/transferencias/nuevo";
    }





    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public Transferencia guardar(@RequestBody Transferencia transferencia, HttpSession session) {
       transferenciaService.save(transferencia);
        return transferencia;
    }



    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String lista(Model model, HttpSession session) {
        model.addAttribute("transferencias", mapper.writeValueAsString(transferenciaService
                .search((Condominio) session.getAttribute("condominio"))));
        return "administrador/transferencias/lista";
    }


    @RequestMapping(method = RequestMethod.GET, value = "actualizar/{transferencia}")
    public String actualizar(@PathVariable Long transferencia,Model model,HttpSession session) {
        model.addAttribute("bancosCajas", mapper
                .writeValueAsString(cuentaService.getCuentas(
                        (Condominio) session.getAttribute("condominio"),
                        configurationServiceImpl.getCuentaCajasBancos())));
        model.addAttribute("metodosTransferencia", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoMetodoPago.class)));
        model.addAttribute("transferencia",  mapper.writeValueAsString(transferenciaService.get(transferencia)));
    return "administrador/transferencias/editar";
    }




}
