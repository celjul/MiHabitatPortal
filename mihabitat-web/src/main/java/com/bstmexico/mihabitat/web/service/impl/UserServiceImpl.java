package com.bstmexico.mihabitat.web.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bstmexico.mihabitat.activacion.dao.EnlaceDao;
import com.bstmexico.mihabitat.activacion.model.Enlace;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("javamailservice")
	private EmailingService emailingService;

	@Autowired
	private EnlaceDao enlaceDao;

	@Autowired
	private ConfigurationServiceImpl configurationService;

	@Override
	public Collection<Contacto> getContactoDepartamento(
			Departamento departamento) {
		Collection<Contacto> contactos = new ArrayList<Contacto>();
		for (ContactoDepartamento cd : departamento.getContactos()) {
			contactos.add(cd.getContacto());
		}
		return contactos;
	}

	private Contacto getContacto(String email, Contacto contacto) {
		for (Email mail : contacto.getEmails()) {
			if (email.equals(mail.getDireccion())) {
				return contacto;
			}
		}

		return null;
	}

	@Override
	public void enviarLinkActivacion(String notificaciones,
									 Contacto contacto, Condominio condominio) {
		if (!StringUtils.isEmpty(notificaciones)) {
			Enlace enlaceTemp = null;

			Map map = new HashMap();
			map.put("contacto", contacto);

			Collection<Enlace> enlaces = enlaceDao.search(map.entrySet());
			if (CollectionUtils.isEmpty(enlaces)) {
				enlaceTemp = new Enlace();
				enlaceTemp.setEmail(notificaciones);
				enlaceTemp.setContacto(contacto);

				enlaceTemp.setVigencia(getVigencia());
				enlaceDao.save(enlaceTemp);
			} else {
				enlaceTemp = enlaces.iterator().next();
				enlaceTemp.setEmail(notificaciones);
				enlaceTemp.setVigencia(getVigencia());
				enlaceDao.update(enlaceTemp);
			}
			final Enlace enlace = enlaceTemp;

			final Map<String, String> emails = new HashMap<>();
			emails.put(notificaciones, contacto.getNombreCompleto());

			final Map mapVelocity = new HashMap();
			mapVelocity.put("nombre", contacto.getNombreCompleto());
			mapVelocity.put("host", configurationService.getHost());
			mapVelocity.put("linkactiva", configurationService.getHost() + "activacion/nueva?id=" + enlace.getId());

			final String asunto = "Activación de Cuenta MiHábitat";
			final String nombreCondominio = condominio.getNombre();

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emails, asunto,
							"usuario-nuevo.html", mapVelocity, null, nombreCondominio);
				}
			}).start();

			/*new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					if (!StringUtils.isEmpty(notificaciones)) {
						Contacto contacto2 = getContacto(notificaciones,
								contacto);
						if (contacto2 != null) {
							@SuppressWarnings("rawtypes")
							Map map = new HashMap();
							map.put("nombre",
									contacto.getNombre()
											+ " "
											+ contacto.getApellidoPaterno()
											+ " "
											+ (contacto.getApellidoMaterno() != null ? contacto
													.getApellidoMaterno() : ""));
							map.put("host", configurationService.getHost());
							map.put("linkactiva",
									configurationService.getHost()
											+ "activacion/nueva?id="
											+ enlace.getId());

							emailService.sendEmail(notificaciones,
									"Activación de Cuenta MiHábitat",
									"usuario-nuevo.html", map, contacto.getCondominio() != null ? contacto.getCondominio().getNombre() : null);

						}
					}

				}
			}).start();*/
		}
	}

	private Date getVigencia() {
		Calendar vigencia = GregorianCalendar.getInstance();
		vigencia.add(Calendar.DAY_OF_MONTH, configurationService.getVigencia());

		return vigencia.getTime();
	}

	@Override
	public void enviarLinkActivacion(Collection<String> notificaciones,
			Collection<ContactoDepartamento> contactos, Condominio condominio) {
		for (String email : notificaciones) {
			for (ContactoDepartamento contacto : contactos) {
				for (Email emailContacto : contacto.getId().getContacto()
						.getEmails()) {

					if (emailContacto.getDireccion().equalsIgnoreCase(email)) {
						this.enviarLinkActivacion(email, contacto.getId()
								.getContacto(), condominio);
					}
				}
			}
		}
	}
}
