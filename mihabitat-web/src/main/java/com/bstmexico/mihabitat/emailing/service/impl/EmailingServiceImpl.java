package com.bstmexico.mihabitat.emailing.service.impl;

import java.util.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.sendinblue.Sendinblue;
import org.apache.commons.codec.binary.Base64;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import org.springframework.util.StringUtils;

@Service("javamailservice")
public class EmailingServiceImpl implements EmailingService {

	private static final Logger LOG = LoggerFactory
			.getLogger(EmailingServiceImpl.class);


	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	/**
	 *@deprecated
	 **//*
	@SuppressWarnings("rawtypes")
	@Override
	public void sendEmail(final String email, final String asunto,
			final String template, final Map map) {
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@SuppressWarnings("unchecked")
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage);
					message.setFrom("desarrollo@bstmexico.com", "MiHábitat");
					message.setTo(email);
					message.setSubject(asunto);
					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, template, "UTF8", map);
					message.setText(text, true);
				}
			};
			javaMailSender.send(preparator);
		} catch (MailPreparationException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailParseException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailAuthenticationException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailSendException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (Throwable ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		}
	}

	*//**
	 *@deprecated
	 **//*
	@SuppressWarnings("rawtypes")
	@Override
	public void sendEmail(final String email, final String asunto,
						  final String template, final Map map,
						  final String nombreCondominio) {
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@SuppressWarnings("unchecked")
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage);
					message.setFrom("desarrollo@bstmexico.com", (nombreCondominio == null || nombreCondominio .equals("")) ? "MiHábitat" : nombreCondominio + " | MiHábitat");
					message.setTo(email);
					message.setSubject(asunto);
					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, template, "UTF8", map);
					message.setText(text, true);
				}
			};
			javaMailSender.send(preparator);
		} catch (MailPreparationException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailParseException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailAuthenticationException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailSendException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (Throwable ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		}
	}

	*//**
	 *@deprecated
	 **//*
	@SuppressWarnings("rawtypes")
	@Override
	public void sendEmail(final String[] emails, final String asunto,
			final String template, final Map map,
			final Collection<Adjunto> adjuntos, final String nombreCondominio) {
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@SuppressWarnings("unchecked")
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage, true);
					message.setTo(getDirecciones(emails));
					message.setSubject(asunto);
					message.setFrom("desarrollo@bstmexico.com", (nombreCondominio == null || nombreCondominio .equals("")) ? "MiHábitat" : nombreCondominio + " | MiHábitat");
					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, template, "UTF8", map);
					message.setText(text, true);
					if (!CollectionUtils.isEmpty(adjuntos)) {
						for (Adjunto adjunto : adjuntos) {
							message.addAttachment(adjunto.getNombre(),
									adjunto.getSource());
						}
					}
				}
			};
			javaMailSender.send(preparator);
		} catch (MailPreparationException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailParseException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailAuthenticationException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailSendException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (Throwable ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		}
	}

	*//**
	*@deprecated
	**//*
	@SuppressWarnings("rawtypes")
	@Override
	public void sendEmailBCC(final String[] emails, final String asunto,
						  final String template, final Map map,
						  final Collection<Adjunto> adjuntos, final String nombreCondominio) {
		try {
			final InternetAddress[] emailsValidados = getDirecciones(emails);
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				@SuppressWarnings("unchecked")
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(
							mimeMessage, true);
					message.setBcc(emailsValidados);
					message.setSubject(asunto);
					message.setFrom("desarrollo@bstmexico.com", (nombreCondominio == null || nombreCondominio .equals("")) ? "MiHábitat" : nombreCondominio + " | MiHábitat");
					String text = VelocityEngineUtils.mergeTemplateIntoString(
							velocityEngine, template, "ISO-8859-1", map);
					message.setText(text, true);
					if (!CollectionUtils.isEmpty(adjuntos)) {
						for (Adjunto adjunto : adjuntos) {
							message.addAttachment(adjunto.getNombre(),
									adjunto.getSource());
						}
					}
				}
			};
			javaMailSender.send(preparator);
		} catch (MailPreparationException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailParseException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailAuthenticationException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (MailSendException ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		} catch (Throwable ex) {
			String message = "Error al intentar enviar el correo.";
			LOG.error(message, ex);
		}
	}

	private InternetAddress[] getDirecciones(String[] emails) {
		List<InternetAddress> direcciones = new ArrayList<>();
		if (emails != null && emails.length > 0) {
			//direcciones = new InternetAddress[emails.length];
			int i = 0;
			for (String string : emails) {
				try {
					InternetAddress address = new InternetAddress(string);
					address.validate();
					if(address != null){
						direcciones.add(address);
					}
				} catch (AddressException e) {
					LOG.error("El email " + string + " no es válido.");
				}
				i++;
			}
		}
		return direcciones.toArray(new InternetAddress[0]);
	}

	//SendinBlue Support


	@Override
	public synchronized void sendEmail(Map<String,String> emails, String asunto, String template, Map atributosVelocity) {

	}

	@Override
	public synchronized void sendEmail(Map<String,String> emails, String asunto, String template, Map atributosVelocity, String nombreCondominio) {

	}*/

	@Override
	public synchronized void sendEmail(Map<String,String> emails, String asunto, String template, Map atributosVelocity,
						  Collection<Adjunto> adjuntos, String nombreCondominio) {
		Sendinblue http = new Sendinblue(configurationServiceImpl.getSendingblueHost(),configurationServiceImpl.getSendingblueKey());
		Map < String, Object > data = new HashMap < String, Object > ();
		//data.put("to", );
		data.put("from", new String [] {configurationServiceImpl.getEmailDirOrigenEmail(),
				(!StringUtils.isEmpty(nombreCondominio)?nombreCondominio + " | " : "")  + configurationServiceImpl.getEmailDirOrigenNombre()});
		data.put("subject", asunto);
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, template, "ISO-8859-1", atributosVelocity);
		data.put("html", text);
		Map < String, String > attachment = new HashMap < String, String > ();
		if(!CollectionUtils.isEmpty(adjuntos)) {
			for (Adjunto resource : adjuntos) {
				attachment.put(resource.getNombre(), Base64.encodeBase64String(((ByteArrayResource)resource.getSource()).getByteArray()));
			}
			data.put("attachment", attachment);
		}

		for(Map.Entry<String, String> email : validarEmails(emails).entrySet()) {
			data.remove("to");
			Map < String, Object > tmpTo = new HashMap < String, Object > ();
			tmpTo.put(email.getKey(), email.getValue());
			data.put("to", tmpTo);
			String str = http.send_email(data);
			System.out.println(str);
		}


	}

	/**
	 * @deprecated
	 *
	 *//*
	@Override
	public synchronized void sendEmailBCC(Map<String,String> emails, String asunto, String template, Map atributosVelocity,
							 Collection<ByteArrayResource> adjuntos, String nombreCondominio) {
		Sendinblue http = new Sendinblue(configurationServiceImpl.getSendingblueHost(),configurationServiceImpl.getSendingblueKey());
		Map < String, Object > data = new HashMap < String, Object > ();
		data.put("bcc", validarEmails(emails));
		data.put("from", new String [] {configurationServiceImpl.getEmailDirOrigenEmail(), nombreCondominio + " | " + configurationServiceImpl.getEmailDirOrigenNombre()});
		data.put("subject", asunto);
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, template, "ISO-8859-1", atributosVelocity);
		data.put("html", text);
		Map < String, String > attachment = new HashMap < String, String > ();
		for(ByteArrayResource resource : adjuntos) {
			attachment.put(resource.getFilename(), Base64.encodeBase64String(resource.getByteArray()));
		}
		data.put("attachment", attachment);

		String str = http.send_email(data);
		System.out.println(str);
	}*/

	private Map<String, String> validarEmails(Map<String, String> original) {
		Map<String, String> map = new HashMap<>();
		for (Map.Entry<String, String> emailPar: original.entrySet()) {
			try {
				InternetAddress emailAddr = new InternetAddress(emailPar.getKey());
				emailAddr.validate();
				map.put(emailPar.getKey(), emailPar.getValue());
			} catch (AddressException ex) {
				LOG.error("El correo " + emailPar.getKey() + " tiene un formato incorrecto.");
			}
		}
		return map;
	}
}
