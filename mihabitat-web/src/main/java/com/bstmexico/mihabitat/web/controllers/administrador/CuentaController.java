package com.bstmexico.mihabitat.web.controllers.administrador;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.cuentas.service.AgrupadorSatService;
import com.bstmexico.mihabitat.cuentas.service.BancoSatService;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

@Controller
@RequestMapping(value = "administrador/cuentas")
public class CuentaController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private BancoSatService bancoSatService;

	@Autowired
	private AgrupadorSatService agrupadoresSatService;

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	private static final Logger LOG = LoggerFactory
			.getLogger(CuentaController.class);

	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {

		model.addAttribute("bancos",
				mapper.writeValueAsString(bancoSatService.getList()));
		model.addAttribute("agrupadores",
				mapper.writeValueAsString(agrupadoresSatService.getList()));
		model.addAttribute("cuentasContables", mapper
				.writeValueAsString(cuentaService.getCuentas(
						(Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentasPadre())));
		return "administrador/cuentas/nuevo";
	}

	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public @ResponseBody Cuenta guardar(@RequestBody Cuenta cuenta,
			HttpSession session) {
		cuentaService.save(cuenta);
		return cuenta;
	}

	/*@RequestMapping(method = RequestMethod.GET, value = "actualizar/{cuenta}")
	public String editar(@PathVariable Long cuenta, Model model,
			HttpSession session) {
		model.addAttribute("bancos",
				mapper.writeValueAsString(bancoSatService.getList()));
		model.addAttribute("agrupadores",
				mapper.writeValueAsString(agrupadoresSatService.getList()));
		model.addAttribute("cuentasContables", mapper
				.writeValueAsString(cuentaService.getAllCuentas(
						(Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentasPadre())));
		model.addAttribute("cuenta",
				mapper.writeValueAsString(cuentaService.get(cuenta)));
		// model.addAttribute("cuentasPadre", mapper
		// .writeValueAsString(cuentaService
		// .getPadres((Condominio) session
		// .getAttribute("condominio"))));
		return "administrador/cuentas/editar";
	}*/

	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{cuenta}")
	public @ResponseBody Cuenta editar(@PathVariable Long cuenta, Model model,
						 HttpSession session) {
		model.addAttribute("bancos",
				mapper.writeValueAsString(bancoSatService.getList()));
		model.addAttribute("agrupadores",
				mapper.writeValueAsString(agrupadoresSatService.getList()));
		model.addAttribute("cuentasContables", mapper
				.writeValueAsString(cuentaService.getAllCuentas(
						(Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentasPadre())));
		return cuentaService.get(cuenta);
	}

	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public @ResponseBody Cuenta actualizar(@RequestBody Cuenta cuenta,
			HttpSession session) {
		cuentaService.updateMultiple(cuenta);
		return cuenta;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getList((Condominio) session.getAttribute("condominio"))));
		model.addAttribute("bancos",
				mapper.writeValueAsString(bancoSatService.getList()));
		model.addAttribute("agrupadores",
				mapper.writeValueAsString(agrupadoresSatService.getList()));
		model.addAttribute("cuentasContables", mapper
				.writeValueAsString(cuentaService.getCuentas(
						(Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentasPadre())));
		// model.addAttribute("cuentasPadre", mapper
		// .writeValueAsString(cuentaService
		// .getPadres((Condominio) session
		// .getAttribute("condominio"))));

		return "administrador/cuentas/lista";
	}

	@RequestMapping(method = RequestMethod.POST, value = "incrementar")
	public @ResponseBody String incrementarCuenta(@RequestParam Long idPadre,
			@RequestParam Byte nivelCuenta) {
		return cuentaService.incrementarCuenta(idPadre, nivelCuenta);
	}

	@RequestMapping(method = RequestMethod.POST, value = "comprobar")
	public @ResponseBody Cuenta comprobarCuenta(
			@RequestParam(required = false) String cuentaPadre,
			@RequestParam(required = false) String cuentaHija,
			@RequestParam(required = false) String cuentaNieto,
			@RequestParam(required = false) String cuentaBis,
			HttpSession session) {
		Cuenta cuenta = CuentaFactory.newInstance();
		cuenta.setCondominio((Condominio) session.getAttribute("condominio"));
		cuenta.setActivo(true);
		if (cuentaPadre != null) {
			cuenta.setNumero(cuentaPadre);
		}
		if (cuentaHija != null) {
			cuenta.setNumeroHija(cuentaHija);
		}
		if (cuentaNieto != null) {
			cuenta.setNumeroNieto(cuentaNieto);
		}
		if (cuentaBis != null) {
			cuenta.setNumeroBis(cuentaBis);
		}
		return cuentaService.comprobarExistenciaCuenta(cuenta);
	}
}
