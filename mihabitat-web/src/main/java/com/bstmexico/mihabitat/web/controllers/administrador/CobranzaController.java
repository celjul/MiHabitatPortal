package com.bstmexico.mihabitat.web.controllers.administrador;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.cobranza.model.Cobranza;
import com.bstmexico.mihabitat.cobranza.model.Nota;
import com.bstmexico.mihabitat.cobranza.service.CobranzaService;
import com.bstmexico.mihabitat.cobranza.service.NotaService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/cobranza")
public class CobranzaController {

	@Autowired
	private CobranzaService cobranzaService;

	@Autowired
	private NotaService notaService;
	
	@Autowired
	@Qualifier("javamailservice")
	private EmailingService emailingService;

	@Autowired
	private ConfigurationServiceImpl configurationService;

	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	public String inicio() {
		return "administrador/cobranza/cobranza";
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public Collection<Cobranza> getCobranza(HttpSession session) {
		Collection<Cobranza> cobranza = cobranzaService.getCobranza(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), getHasta());
		return cobranza;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "notas")
	public Collection<Nota> getNotas(@RequestParam Long idDepartamento) {

		Map map = new HashMap();
		map.put("departamento", DepartamentoFactory.newInstance(idDepartamento));
		return notaService.search(map);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "nota/guardar")
	public Nota guardar(@RequestBody Nota nota) {
		notaService.save(nota);
		return nota;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "nota/actualizar")
	public Nota actualizar(@RequestBody Nota nota) {
		notaService.update(nota);
		return nota;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "nota/eliminar")
	public Boolean eliminar(@RequestBody Nota nota) {
		notaService.delete(nota);
		return Boolean.TRUE;
	}

	//Mejorar ya que no obtengo el departamento sino solo el email.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "envio")
	public Boolean enviar(@RequestParam(value = "emails[]") String[] emails,
			@RequestParam String mensaje, HttpSession session) {

		Condominio condominio = (Condominio) session.getAttribute(SessionEnum.CONDOMINIO
				.getValue());
		final Map<String, String> emailsContacto = new HashMap<>();
		for(String em : emails) {
			emailsContacto.put(em, "Morosidad");
		}

		if(!CollectionUtils.isEmpty(emailsContacto)){
			final Map mapVelocity = new HashMap();
			mapVelocity.put("mensaje", mensaje.replaceAll("(\r\n|\n)", "<br />"));
			mapVelocity.put("condominio", condominio.getNombre());
			mapVelocity.put("host", configurationService.getHost());

			final String nombreCondominio = condominio.getNombre();
			final String asunto = "Mensaje de la Administración";

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailsContacto, asunto,
							"cobranza.html", mapVelocity, null, nombreCondominio);
				}
			}).start();
		}
		return Boolean.TRUE;
	}

	/**
	 * TODO: Por defecto un mes; mejora a configurable por condominio.
	 * 
	 * @return
	 */
	private Date getHasta() {
		Calendar calendar = Calendar.getInstance();
		/*calendar.add(Calendar.MONTH, -1);*/
		return calendar.getTime();
	}
}
