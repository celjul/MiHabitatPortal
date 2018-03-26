package com.bstmexico.mihabitat.web.controllers.administrador;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.cargos.model.CatalogoTipoConsumo;
import com.bstmexico.mihabitat.cargos.model.CatalogoUnidad;
import com.bstmexico.mihabitat.cargos.model.TipoConsumo;
import com.bstmexico.mihabitat.cargos.service.TipoConsumoService;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

@Controller
@RequestMapping(value = "administrador/consumos")
public class ConsumoController {

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private TipoConsumoService consumoService;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaIngresos())));
		model.addAttribute("catalogoUnidad", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoUnidad.class)));
		model.addAttribute("catalogoTipoConsumo", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTipoConsumo.class)));
		return "administrador/consumos/nuevo";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaIngresos())));
		model.addAttribute("catalogoUnidad", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoUnidad.class)));
		model.addAttribute("catalogoTipoConsumo", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTipoConsumo.class)));
		model.addAttribute("consumos",
				mapper.writeValueAsString(consumoService.search(map)));
		return "administrador/consumos/lista";
	}

	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{consumo}")
	public String editar(@PathVariable Long consumo, Model model,
			HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaIngresos())));
		model.addAttribute("catalogoUnidad", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoUnidad.class)));
		model.addAttribute("consumo",
				mapper.writeValueAsString(consumoService.get(consumo)));
		model.addAttribute("catalogoTipoConsumo", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTipoConsumo.class)));
		return "administrador/consumos/editar";
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{tipoConsumoId}")
	public @ResponseBody TipoConsumo editar(@PathVariable Long tipoConsumoId, Model model,
									   HttpSession session) {
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaIngresos())));
		model.addAttribute("catalogoUnidad", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoUnidad.class)));
		model.addAttribute("catalogoTipoConsumo", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoTipoConsumo.class)));
		return consumoService.get(tipoConsumoId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public @ResponseBody TipoConsumo guardar(@RequestBody TipoConsumo consumo, HttpSession session) {
		consumo.setCondominio((Condominio) session.getAttribute("condominio"));
		consumoService.save(consumo);
		return consumo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public @ResponseBody TipoConsumo actualizar(@RequestBody TipoConsumo consumo, HttpSession session) {
		consumo.setCondominio((Condominio) session.getAttribute("condominio"));
		consumoService.update(consumo);
		return consumo;
	}
}
