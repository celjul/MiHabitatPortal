package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.CatalogoTipoCobroDepartamento;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;
import com.bstmexico.mihabitat.condominios.service.MantenimientoCondominioService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/mantenimientos")
public class MantenimientoController {

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private MantenimientoCondominioService mantenimientoCondominioService;

    @Autowired
    private CatalogoService catalogoService;

    @RequestMapping(method = RequestMethod.GET, value = "nuevo")
    public String nuevo() {
        return "administrador/mantenimientos/nuevo";
    }

    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public
    @ResponseBody
    MantenimientoCondominio guardar(
            @RequestBody MantenimientoCondominio mantenimiento,
            HttpSession session) {
        mantenimiento.setCondominio((Condominio) session
                .getAttribute("condominio"));
        mantenimientoCondominioService.save(mantenimiento);
        return mantenimiento;
    }

    @RequestMapping(method = RequestMethod.POST, value = "actualizar")
    public
    @ResponseBody
    MantenimientoCondominio actualizar(
            @RequestBody MantenimientoCondominio mantenimiento,
            HttpSession session) {
        mantenimiento.setCondominio((Condominio) session
                .getAttribute("condominio"));
        mantenimientoCondominioService.update(mantenimiento);
        return mantenimiento;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String lista(Model model, HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("mantenimientos", mapper.writeValueAsString(mantenimientoCondominioService.search(map)));
        model.addAttribute("tiposCobro", mapper.writeValueAsString(catalogoService.getList(CatalogoTipoCobroDepartamento.class)));
        return "administrador/mantenimientos/lista";
    }

	/*@RequestMapping(method = RequestMethod.GET, value = "actualizar/{mantenimiento}")
    public String editar(@PathVariable Long mantenimiento, Model model) {
		model.addAttribute("mantenimiento", mapper
				.writeValueAsString(mantenimientoCondominioService
						.get(mantenimiento)));
		return "administrador/mantenimientos/editar";
	}*/

    @RequestMapping(method = RequestMethod.GET, value = "actualizar/{mantenimiento}")
    public
    @ResponseBody
    MantenimientoCondominio editar(@PathVariable Long mantenimiento, Model model) {
        return mantenimientoCondominioService.get(mantenimiento);
    }
}
