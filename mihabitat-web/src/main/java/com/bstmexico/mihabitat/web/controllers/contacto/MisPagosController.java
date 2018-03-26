package com.bstmexico.mihabitat.web.controllers.contacto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.web.controllers.comunes.ComprobanteController;
import com.bstmexico.mihabitat.web.dto.FileMetaData;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.web.controllers.administrador.PagoController;
import com.bstmexico.mihabitat.web.service.PaymentService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "contacto/mis-pagos")
public class MisPagosController {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory
			.getLogger(PagoController.class);

	@Autowired
	@Qualifier("pagoserviceproxy")
	private PagoService pagoService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private NotificationHelperService notificationService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {
		session.removeAttribute(ComprobanteController.COMPROBANTE);
		Map map = new HashMap();
		map.put("condominio", (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		map.put("usuario",
				(Usuario) session.getAttribute(SessionEnum.USUARIO.getValue()));
		model.addAttribute("contactos",
				mapper.writeValueAsString(contactoService.search(map)));
		model.addAttribute("metodosPago", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoMetodoPago.class)));
		return "contacto/pagos/nuevo";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		map.put("usuario",
				(Usuario) session.getAttribute(SessionEnum.USUARIO.getValue()));
		Contacto contacto = contactoService.search(map).iterator().next();
		map.put("contacto", contacto);
		map.remove("usuario");
		Collection<Pago> pagos = pagoService.search(map);
		for(Pago pago : pagos) {
			for(PagoDepartamento pd :  pago.getPagosDepartamento()) {
				for (MovimientoCargoAplicado movimientoCargoAplicado : pd.getMovimientos()) {
					movimientoCargoAplicado.setPago(null);
				}
			}
		}
		model.addAttribute("pagos",
				mapper.writeValueAsString(pagos));
		return "contacto/pagos/lista";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "/actualizar/{idPago}")
	public String editar(@PathVariable Long idPago, Model model,
			HttpSession session) {
		Map map = new HashMap();
		map.put("condominio", (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		model.addAttribute("contactos",
				mapper.writeValueAsString(contactoService.search(map)));
		model.addAttribute("metodosPago", mapper
				.writeValueAsString(catalogoService
						.getList(CatalogoMetodoPago.class)));
		Pago pago = pagoService.get(idPago);

		FileMetaData fileMeta = new FileMetaData();
		if(pago.getComprobante() != null) {
			try {
				String extension = Magic.getMagicMatch(pago.getComprobante()).getExtension();

				fileMeta.setName("comprobante_" + String.valueOf(idPago) + "."
						+ extension);
				fileMeta.setSize(pago.getComprobante().length / 1024 + "");
				if (extension.equals("pdf")) {
					fileMeta.setType("application/pdf");
				} else if (extension.equals("doc")) {
					fileMeta.setType("application/ms-word");
				} else if (extension.equals("docx")) {
					fileMeta.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
				} else if (extension.equals("jpg")) {
					fileMeta.setType("image/jpeg");
				} else if (extension.equals("gif")) {
					fileMeta.setType("image/gif");
				} else if (extension.equals("png")) {
					fileMeta.setType("image/png");
				}
				fileMeta.setBytes(pago.getComprobante());
			} catch (MagicParseException | MagicMatchNotFoundException
					| MagicException e) {
				LOG.warn("Error al procesar el archivo " + e);
			}
			session.setAttribute(ComprobanteController.COMPROBANTE, fileMeta);
		}
		model.addAttribute("archivo", mapper.writeValueAsString(fileMeta));

		for (PagoDepartamento pd : pago.getPagosDepartamento()) {
			for (MovimientoCargoAplicado aplicado : pd.getMovimientos()) {
				aplicado.setPago(null);
				aplicado.getMovimientoCargo().setAplicados(null);
				aplicado.getMovimientoCargo().getCargo().setMovimientos(null);
			}
		}
		Collection<CargoDepartamento> cargos = cargoService.getCargos(pago);
		model.addAttribute("pago", mapper.writeValueAsString(pago));
		model.addAttribute("cargos", mapper.writeValueAsString(cargos));
		return "contacto/pagos/editar";
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "/reenviar")
	public Pago reenviar(@RequestBody Pago pago) {
		pagoService.reenviar(pago);
		/*notificationService.enviarNotificacionPago(pago);*/
		/*paymentService.sendEmail(pago);*/


		//pago.setMovimientos(null);
		pago.setPagosDepartamento(null);

		return pago;
	}
}
