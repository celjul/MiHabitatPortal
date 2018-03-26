package com.bstmexico.mihabitat.web.controllers.administrador;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import com.bstmexico.mihabitat.proveedores.model.PagoProveedor;
import com.bstmexico.mihabitat.proveedores.service.FacturaPorPagarService;
import com.bstmexico.mihabitat.proveedores.service.PagoProveedorService;
import com.bstmexico.mihabitat.proveedores.service.ProveedorService;
import com.bstmexico.mihabitat.web.exceptions.GenericController;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/pago-proveedores")
public class PagoProveedorController extends GenericController {

	protected static final Logger LOG = LoggerFactory
			.getLogger(PagoProveedorController.class);

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ProveedorService proveedorService;

	@Autowired
	private FacturaPorPagarService facturaporpagarService;

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private PagoProveedorService pagoProveedorService;

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		Map map = new HashMap();

		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		map.put("esFacturable", true);
		model.addAttribute("cuenta", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationServiceImpl.getCuentaCajasBancos())));
		model.addAttribute("proveedores",
				mapper.writeValueAsString(proveedorService.search(map)));
		model.addAttribute("metodosPago", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoMetodoPago.class)));

		return "administrador/pago-proveedores/nuevo";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.POST, value = "facturasxpagar")
	public @ResponseBody Collection<Cfdi> getFacturasProveedores(
			@RequestParam String rfc, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		map.put("rfc", rfc);
		return facturaporpagarService.cfdiByRfc(map);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public PagoProveedor guardar(@RequestBody PagoProveedor pagoProveedor,
			HttpSession session) {

		pagoProveedorService.save(pagoProveedor);
		// paymentService.sendEmail(pago);
		pagoProveedor.setMovimientos(null);
		return pagoProveedor;
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
