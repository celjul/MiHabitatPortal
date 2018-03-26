package com.bstmexico.mihabitat.web.controllers.administrador;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.proveedores.gastos.model.Gasto;
import com.bstmexico.mihabitat.proveedores.gastos.service.GastoService;
import com.bstmexico.mihabitat.proveedores.service.ProveedorService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
@Controller
@RequestMapping(value = "administrador/gastos")
public class GastoController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private ProveedorService proveedorService;

	@Autowired
	private GastoService gastoService;
	
	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));

		model.addAttribute("proveedores",
				mapper.writeValueAsString(proveedorService.search(map)));
		model.addAttribute("bancosCajas", mapper
				.writeValueAsString(cuentaService.getCuentas(
						(Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaCajasBancos())));
		model.addAttribute("egresos", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaEgresos())));
		model.addAttribute("metodosPago", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoMetodoPago.class)));
		return "administrador/gastos/nuevo";
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public Gasto guardar(@RequestBody Gasto gasto, HttpSession session) {
		gastoService.guardar(gasto);
		return gasto;
	}

	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		model.addAttribute("gastos", mapper.writeValueAsString(gastoService
				.listar((Condominio) session.getAttribute("condominio"))));
		return "administrador/gastos/lista";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "editar/{gasto}")
	public String editar(@PathVariable Long gasto, Model model,
			HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));

		model.addAttribute("proveedores",
				mapper.writeValueAsString(proveedorService.search(map)));
		model.addAttribute("bancosCajas", mapper
				.writeValueAsString(cuentaService.getCuentas(
						(Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaCajasBancos())));
		model.addAttribute("egresos", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaEgresos())));
		model.addAttribute("metodosPago", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoMetodoPago.class)));
		model.addAttribute("gasto",
				mapper.writeValueAsString(gastoService.editar(gasto)));
		return "administrador/gastos/editar";
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public Gasto actualizar(@RequestBody Gasto gasto, HttpSession session) {
		gastoService.actualizar(gasto);
		return gasto;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "cancelar")
	public Gasto cancelar(@RequestBody Gasto gasto, HttpSession session) {
		gastoService.cancelar(gasto);
		return gasto;
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "saldo")
	public BigDecimal getSaldo(@RequestParam Long idCuenta) {

		Cuenta cuenta = cuentaService.get(CuentaFactory.newInstance(idCuenta));

		BigDecimal saldo = cuenta.getInicial().add(movimientoService.getHaber(new Date(),
				CuentaFactory.newInstance(idCuenta))).subtract(
				movimientoService.getDebe(new Date(),
						CuentaFactory.newInstance(idCuenta)));
		return saldo;
	}
}
