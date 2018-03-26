package com.bstmexico.mihabitat.web.controllers.administrador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.cargos.model.Cargo;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.web.dto.AvisoDeCobro;
import com.bstmexico.mihabitat.web.service.AvisoDeCobroService;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.factory.ContactoFactory;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.dto.EstadoCuenta;
import com.bstmexico.mihabitat.web.dto.Item;
import com.bstmexico.mihabitat.web.exceptions.GenericController;
import com.bstmexico.mihabitat.web.service.EstadoCuentaService;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/estado-cuenta")
public class EstadoCuentaController extends GenericController {

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private EstadoCuentaService estadoCuentaService;

	@Autowired
	private AvisoDeCobroService avisoDeCobroService;

	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	@Qualifier("pagoserviceproxy")
	private PagoService pagoService;

	@Autowired
	private ServletContext context;

	@Autowired
	private ReportUtils reportUtils;

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory
			.getLogger(EstadoCuentaController.class);

	@RequestMapping(method = RequestMethod.GET, value = "individual")
	public String getIndividual(Model model) {
		/*model.addAttribute("periodos",
				mapper.writeValueAsString(DateUtils.getPeriodos()));*/
		model.addAttribute("idDepartamento", 0);
		model.addAttribute("idContacto", 0);
		return "administrador/estado-cuenta/individual";
	}

	@RequestMapping(method = RequestMethod.GET, value = "individual/{idDepartamento}/{idContacto}")
	public String getIndividualConId(Model model, @PathVariable Long idDepartamento, @PathVariable Long idContacto) {
		/*model.addAttribute("periodos",
				mapper.writeValueAsString(DateUtils.getPeriodos()));*/
		model.addAttribute("idDepartamento", idDepartamento);
		model.addAttribute("idContacto", idContacto);
		return "administrador/estado-cuenta/individual";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "buscar")
	public Collection<Item> buscar(@RequestParam String key, HttpSession session) {
		Collection<Item> items = new ArrayList<Item>();

		Set<Contacto> contactos = new HashSet<Contacto>();
		contactos.addAll(contactoService.buscar((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()), key));

		Set<Departamento> departamentos = new HashSet<Departamento>();
		Map map = new HashMap();
		map.put("condominio", (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		map.put("nombre", key);
		departamentos.addAll(departamentoService.search(map));

		for (Contacto contacto : contactos) {
			if (!CollectionUtils.isEmpty(contacto.getDepartamentos())) {
				for (ContactoDepartamento cd : contacto.getDepartamentos()) {
					Item item = new Item();
					item.setLabel(new StringBuilder()
							.append(!StringUtils.isEmpty(contacto.getNombre()) ? contacto
									.getNombre() : "")
							.append(!StringUtils.isEmpty(contacto
									.getApellidoPaterno()) ? " "
									+ contacto.getApellidoPaterno() : "")
							.append(!StringUtils.isEmpty(contacto
									.getApellidoMaterno()) ? " "
									+ contacto.getApellidoMaterno() : "")
							.append(" - ")
							.append(cd.getDepartamento().getNombre())
							.toString());
					item.setUrl("/administrador/estado-cuenta/"
							+ cd.getDepartamento().getId() + "/departamento");
					item.setId(String.valueOf(contacto.getId()));
					items.add(item);
				}
			}
		}

		for (Departamento departamento : departamentos) {
			if (!CollectionUtils.isEmpty(departamento.getContactos())) {
				for (ContactoDepartamento cd : departamento.getContactos()) {
					Item item = new Item();
					item.setLabel(new StringBuilder()
							.append(departamento.getNombre())
							.append(" - ")
							.append(!StringUtils.isEmpty(cd.getContacto()
									.getNombre()) ? cd.getContacto()
									.getNombre() : "")
							.append(!StringUtils.isEmpty(cd.getContacto()
									.getApellidoPaterno()) ? " "
									+ cd.getContacto().getApellidoPaterno()
									: "")
							.append(!StringUtils.isEmpty(cd.getContacto()
									.getApellidoMaterno()) ? " "
									+ cd.getContacto().getApellidoMaterno()
									: "").toString());
					item.setUrl("/administrador/estado-cuenta/"
							+ departamento.getId() + "/departamento");
					item.setId(String.valueOf(cd.getContacto().getId()));
					items.add(item);
				}
			}
		}
		return items;
	}

	@RequestMapping(method = RequestMethod.GET, value = "{idDepartamento}/departamento")
	@ResponseBody
	public EstadoCuenta getEstadoCuenta(@PathVariable Long idDepartamento,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam(required = false) Long idContacto, HttpSession session) {
		Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		return estadoCuentaService.getEstado(condominio, idDepartamento, inicio, fin, idContacto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "avisocobro/{idDepartamento}/departamento")
	@ResponseBody
	public AvisoDeCobro getAvisoDeCobro(@PathVariable Long idDepartamento,
										@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
										@RequestParam(required = false) Long idContacto, HttpSession session) {
		Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		return avisoDeCobroService.getAvisoDeCobro(condominio, idDepartamento, fin, idContacto);
	}

	@RequestMapping(method = RequestMethod.GET, value = "cargos/{idDepartamento}/departamento")
	@ResponseBody
	public Collection<Cargo> getCargos(@PathVariable Long idDepartamento) {
		Departamento dpto = DepartamentoFactory.newInstance(idDepartamento);
		Map map = new HashMap();
		map.put("departamento", dpto);
		return cargoService.search(map, CargoDepartamento.class, Boolean.FALSE);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "abonos/{idDepartamento}/departamento")
	@ResponseBody
	public Collection<PagoDepartamento> getAbonos(@PathVariable Long idDepartamento) {
		Collection<PagoDepartamento> pagos = pagoService.getPagos(DepartamentoFactory.newInstance(idDepartamento));
		return pagos;
	}

	@RequestMapping(method = RequestMethod.GET, value = "{idDepartamento}/imprimir")
	public ResponseEntity<byte[]> imprimir(@PathVariable Long idDepartamento,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam(required = false) Long idContacto, HttpSession session) {

		Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		byte[] bytes = estadoCuentaService.getEstadoCuenta(estadoCuentaService.getEstado(
				condominio, idDepartamento, inicio, fin, idContacto));
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "estado_cuenta_" + idDepartamento + ".pdf";
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "avisocobro/{idDepartamento}/imprimir")
	public ResponseEntity<byte[]> imprimirAvisoDeCobro(@PathVariable Long idDepartamento,
										   @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
										   @RequestParam(required = false) Long idContacto, HttpSession session) {

		Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		AvisoDeCobro ac = avisoDeCobroService.getAvisoDeCobro(
				condominio, idDepartamento, fin, idContacto);
		byte[] bytes = avisoDeCobroService.getAvisoDeCobro(ac);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "aviso_cobro_" + idDepartamento + ".pdf";
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "{idDepartamento}/enviar")
	public Boolean enviar(@PathVariable Long idDepartamento,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam(required = false) Long idContacto,
			@RequestParam(value = "emails[]") String[] emails,
			@RequestParam String mensaje, HttpSession session) {

		/*Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		ByteArrayResource resource = new ByteArrayResource(
				estadoCuentaService.getEstadoCuenta(estadoCuentaService.getEstado(condominio,
						idDepartamento, inicio, fin, idContacto)));

		Departamento dpto = departamentoService.get(idDepartamento);

		final String asunto = "Estado de Cuenta " + dpto.getNombre();
		estadoCuentaService.sendEstadoCuenta(new Adjunto("estado_cuenta_"
				+ idDepartamento + ".pdf", resource), mensaje, condominio, asunto, emails);*/

		estadoCuentaService.sendEstadoCuenta((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()), mensaje, idDepartamento, idContacto, inicio, fin, emails);

		return Boolean.TRUE;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "avisocobro/{idDepartamento}/enviar")
	public Boolean enviarAvisoCobro(@PathVariable Long idDepartamento,
						  @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
						  @RequestParam(required = false) Long idContacto,
						  @RequestParam(value = "emails[]") String[] emails,
						  @RequestParam String mensaje, HttpSession session) {

		avisoDeCobroService.sendAvisoDeCobro((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()),mensaje, idDepartamento, idContacto, fin,emails);

		/*Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		AvisoDeCobro ac = getAvisoDeCobro(condominio,
				idDepartamento, fin, idContacto);
		ByteArrayResource resource = new ByteArrayResource(
				avisoDeCobroService.getAvisoDeCobro(ac));

		Departamento dpto = departamentoService.get(idDepartamento);

		final String asunto = "Aviso de Cobro " + dpto.getNombre();
		avisoDeCobroService.sendAvisoDeCobro(new Adjunto("aviso_cobro_"
				+ idDepartamento + ".pdf", resource), mensaje, condominio, asunto, emails);*/
		return Boolean.TRUE;
	}

	@RequestMapping(method = RequestMethod.GET, value = "masivo")
	public String getMasivo(Model model, HttpSession session) {
		/*model.addAttribute("periodos",
				mapper.writeValueAsString(DateUtils.getPeriodos()));*/
		return "administrador/estado-cuenta/masivo";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "masivo/lista")
	public Collection<EstadoCuenta> getEstadosCuenta(HttpSession session,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin) {

		Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<EstadoCuenta> estados = new ArrayList<EstadoCuenta>();
		for (Departamento departamento : departamentoService.search(map)) {
			EstadoCuenta estado = estadoCuentaService.getEstado(condominio, departamento.getId(),
					inicio, fin, null);
			if (!CollectionUtils.isEmpty(estado.getDepartamento()
					.getContactos())) {
				for (ContactoDepartamento cd : estado.getDepartamento()
						.getContactos()) {
					if (cd.getPrincipal()) {
						estado.setContacto(cd.getContacto());
						break;
					}
				}
			}

			estados.add(estado);
		}
		return estados;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "masivo/enviar")
	public Boolean envioMasivo(@RequestParam(value = "ids[]") Long[] ids,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			HttpSession session) {

		Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		/*Map map = new HashMap();
		map.put("condominio", condominio);

		for (Long idDepartamento : ids) {
			Departamento departamento = departamentoService.get(idDepartamento);
			Contacto contacto = contactoService.get(departamento.obtenerPrincipal().getContacto().getId());
			EstadoCuenta estadoCuenta = estadoCuentaService.getEstado(condominio, idDepartamento,
					inicio, fin, contacto.getId());

			Collection<String> emails = new ArrayList<String>();
			if (contacto != null && !CollectionUtils.isEmpty(contacto
					.getEmails())) {
				for (Email email : contacto.getEmails()) {
					emails.add(email.getDireccion());
				}
			}
			if(emails != null && !emails.isEmpty()){
				ByteArrayResource resource = new ByteArrayResource(
						estadoCuentaService.getEstadoCuenta(estadoCuenta));
				final String asunto = "Estado de Cuenta " + departamento.getNombre();

				estadoCuentaService.sendEstadoCuenta(new Adjunto("estado_cuenta_"
								+ departamento.getId() + ".pdf", resource), "", condominio, asunto,
						emails.toArray(new String[emails.size()]));
			}
		}*/
		return Boolean.TRUE;
	}

	@RequestMapping(method = RequestMethod.GET, value = "masivo/imprimir")
	public ResponseEntity<byte[]> impresionMasivo(@RequestParam(value = "ids[]") Long[] ids,
												  @RequestParam String formato,
												  @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
												  @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
												  HttpSession session) {

		Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<EstadoCuenta> estadosCuenta = new ArrayList<>();

		for (Long idDepartamento : ids) {
			Departamento departamento = departamentoService.get(idDepartamento);
			Contacto contacto = contactoService.get(departamento.obtenerPrincipal().getContacto().getId());
			EstadoCuenta estadoCuenta = estadoCuentaService.getEstado(condominio, idDepartamento,
					inicio, fin, contacto.getId());
			estadosCuenta.add(estadoCuenta);
		}

		byte[] bytes = estadoCuentaService.getEstadoDeCuentaMultiple(estadosCuenta, formato);

		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "estados_cuenta_" + condominio.getNombre() + "_" + (new SimpleDateFormat("yyyyMM")).format(fin) + "."+ formato;
		//headers.setContentType(MediaType.parseMediaType("application/pdf"));
		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	/*private EstadoCuenta getEstado(Condominio condominio, Long idDepartamento,
			Date inicio, Date fin, Long idContacto) {
		Contacto contacto = null;
		if(idContacto != null) {
			contacto = contactoService.get(idContacto);
		} else {
			contacto = ContactoFactory.newInstance();
		}
		return estadoCuentaService.getEstadoCuenta(condominio,
				DepartamentoFactory.newInstance(idDepartamento), inicio, fin,
				contacto);
	}*/

	/*private AvisoDeCobro getAvisoDeCobro(Condominio condominio, Long idDepartamento,
										 Date fin, Long idContacto) {
		Contacto contacto = null;
		if(idContacto != null) {
			contacto = contactoService.get(idContacto);
		} else {
			contacto = ContactoFactory.newInstance();
		}

		return avisoDeCobroService.getAvisoDeCobro(condominio,
				DepartamentoFactory.newInstance(idDepartamento),
				contacto, fin);
	}*/
}
