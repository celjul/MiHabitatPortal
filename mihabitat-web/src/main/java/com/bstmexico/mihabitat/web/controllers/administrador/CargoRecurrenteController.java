package com.bstmexico.mihabitat.web.controllers.administrador;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.cargos.model.CargoRecurrenteMantenimiento;
import com.bstmexico.mihabitat.condominios.service.MantenimientoCondominioService;
import com.bstmexico.mihabitat.instalaciones.service.InstalacionService;
import com.bstmexico.mihabitat.web.dto.Item;

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

import com.bstmexico.mihabitat.cargos.model.CargoRecurrente;
import com.bstmexico.mihabitat.cargos.model.CatalogoCargo;
import com.bstmexico.mihabitat.cargos.model.CatalogoInteres;
//import com.bstmexico.mihabitat.cargos.model.CargoRecurrenteMes;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/cargos-recurrentes")
public class CargoRecurrenteController {

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
	private MantenimientoCondominioService mantenimientoCondominioService;

	@Autowired
	private InstalacionService instalacionService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CargoRecurrenteController.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("departamentos",
				mapper.writeValueAsString(departamentoService.search(map)));
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaIngresos())));
		String[] idMeses = configurationServiceImpl.getIdMeses().split("\\,");
		String[] descripcionMeses = configurationServiceImpl.getDescripcionMeses().split("\\,");
		Collection<Item> meses = new ArrayList<>();
		for(int i=0; i<idMeses.length; i++) {
			Item item = new Item();
			item.setId(idMeses[i]);
			item.setLabel(descripcionMeses[i]);
			meses.add(item);
		}
		model.addAttribute("catalogoMes", mapper
				.writeValueAsString(meses));
		model.addAttribute("catalogoCargo", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoCargo.class)));
		model.addAttribute("catalogoInteres", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoInteres.class)));
		model.addAttribute("mantenimientos", mapper
				.writeValueAsString(mantenimientoCondominioService.search(map)));
		model.addAttribute("instalaciones", mapper
				.writeValueAsString(instalacionService.search(map)));


		return "administrador/cargos/recurrentes/nuevo";
	}

	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public @ResponseBody CargoRecurrente guardar(
			@RequestBody CargoRecurrente cargo, HttpSession session) {
		cargo.setCondominio((Condominio) session.getAttribute("condominio"));
		cargoService.save(cargo);
		return cargo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public @ResponseBody CargoRecurrente actualizar(
			@RequestBody CargoRecurrente cargo, HttpSession session) {
		cargo.setCondominio((Condominio) session.getAttribute("condominio"));
		cargoService.update(cargo);
		return (CargoRecurrente) cargoService.get(cargo.getId(),CargoRecurrente.class, Boolean.FALSE);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("cargos", mapper.writeValueAsString(cargoService
				.search(map, CargoRecurrente.class, Boolean.FALSE)));
		return "administrador/cargos/recurrentes/lista";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{cargo}")
	public String editar(@PathVariable Long cargo, Model model,
			HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("departamentos",
				mapper.writeValueAsString(departamentoService.search(map)));
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaIngresos())));
		String[] idMeses = configurationServiceImpl.getIdMeses().split("\\,");
		String[] descripcionMeses = configurationServiceImpl.getDescripcionMeses().split("\\,");
		Collection<Item> meses = new ArrayList<>();
		for(int i=0; i<idMeses.length; i++) {
			Item item = new Item();
			item.setId(idMeses[i]);
			item.setLabel(descripcionMeses[i]);
			meses.add(item);
		}
		model.addAttribute("catalogoMes", mapper
				.writeValueAsString(meses));
		model.addAttribute("catalogoCargo", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoCargo.class)));
		model.addAttribute("cargo", mapper.writeValueAsString(cargoService.get(
				cargo, CargoRecurrente.class, Boolean.FALSE)));
		model.addAttribute("catalogoInteres", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoInteres.class)));
		model.addAttribute("mantenimientos", mapper
				.writeValueAsString(mantenimientoCondominioService.search(map)));
		model.addAttribute("instalaciones", mapper
				.writeValueAsString(instalacionService.search(map)));
		return "administrador/cargos/recurrentes/editar";
	}

	public Collection<Cuenta> getCuentas() {
		Collection<Cuenta> cuentas = new ArrayList<Cuenta>();
		Cuenta cuenta = new Cuenta();
		cuenta.setId(1l);
		cuenta.setNombre("Lala");
		cuentas.add(cuenta);
		return cuentas;
	}
}
