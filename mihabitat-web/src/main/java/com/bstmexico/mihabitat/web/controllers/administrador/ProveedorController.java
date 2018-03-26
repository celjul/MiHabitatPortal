package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.direcciones.service.PaisService;
import com.bstmexico.mihabitat.comunes.personas.model.CatalogoEmail;
import com.bstmexico.mihabitat.comunes.personas.model.CatalogoTelefono;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.proveedores.factory.ProveedorFactory;
import com.bstmexico.mihabitat.proveedores.model.CatalogoGiro;
import com.bstmexico.mihabitat.proveedores.model.Proveedor;
import com.bstmexico.mihabitat.proveedores.service.ProveedorService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/proveedores")
public class ProveedorController {

	protected static final Logger LOG = LoggerFactory
			.getLogger(Proveedor.class);

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ProveedorService proveedorService;	
	
	@Autowired
	private CatalogoService catalogoService;
	
	@Autowired
	private PaisService paisService;

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		Map map = new HashMap();
		
		map.put("condominio", (Condominio) session.getAttribute("condominio"));

		model.addAttribute("cuenta", mapper.writeValueAsString(cuentaService.getCuentas(
				(Condominio) session.getAttribute("condominio"), configurationServiceImpl.getCuentaEgresos())));
		model.addAttribute("giros", mapper.writeValueAsString(catalogoService.getList(CatalogoGiro.class)));
		model.addAttribute("catalogoEmail", mapper.writeValueAsString(catalogoService.getList(CatalogoEmail.class)));
		model.addAttribute("catalogoTelefono", mapper.writeValueAsString(catalogoService.getList(CatalogoTelefono.class)));
		model.addAttribute("paises", mapper.writeValueAsString(paisService.getList()));
		
		return "administrador/proveedores/nuevo";
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public @ResponseBody Proveedor guardar(
			@RequestBody Proveedor proveedor
			) {
		proveedorService.save(proveedor);

		return proveedor;
	}

	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public @ResponseBody
	Proveedor actualizar(
			@RequestBody Proveedor proveedor,
			@RequestParam(required = false) Collection<String> notificaciones) {

		proveedorService.update(proveedor);
		return proveedor;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("proveedores", mapper.writeValueAsString(proveedorService.search(map)));
		return "administrador/proveedores/lista";
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{proveedor}")
	public String editar(@PathVariable Long proveedor, Model model,
						 HttpSession session) {
		Map map = new HashMap();

        map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("cuenta", mapper.writeValueAsString(cuentaService.getCuentas(
				(Condominio) session.getAttribute("condominio"), configurationServiceImpl.getCuentaEgresos())));
        model.addAttribute("giros", mapper.writeValueAsString(catalogoService.getList(CatalogoGiro.class)));
        model.addAttribute("catalogoEmail", mapper.writeValueAsString(catalogoService.getList(CatalogoEmail.class)));
        model.addAttribute("catalogoTelefono", mapper.writeValueAsString(catalogoService.getList(CatalogoTelefono.class)));
        model.addAttribute("paises", mapper.writeValueAsString(paisService.getList()));

		model.addAttribute("proveedor", mapper.writeValueAsString(proveedorService.get(proveedor)));

		return "administrador/proveedores/editar";
	}


	@RequestMapping(method = RequestMethod.POST, value = "/existe")
	public @ResponseBody
	Proveedor existe(@RequestParam String rfc, HttpSession session) {

		Proveedor proveedor = ProveedorFactory.newInstance();
		proveedor.setCondominio((Condominio) session.getAttribute("condominio"));
		proveedor.setRfc(rfc);

		LOG.warn("RFC: " + proveedorService.existeProveedor(proveedor).getRfc());
		return proveedorService.existeProveedor(proveedor);
	}



	public Collection<Cuenta> getCuentas() {
		Collection<Cuenta> cuentas = new ArrayList<Cuenta>();
		Cuenta cuenta = new Cuenta();
		cuenta.setId(1l);
		cuenta.setNombre("Cuenta dummy");
		cuentas.add(cuenta);
		return cuentas;
	}
	
}
