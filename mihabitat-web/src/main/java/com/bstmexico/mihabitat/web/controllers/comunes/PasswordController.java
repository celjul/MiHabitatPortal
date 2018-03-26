package com.bstmexico.mihabitat.web.controllers.comunes;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunes.usuarios.service.UsuarioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pamela Lopez Cruz
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "password/recuperar")
public class PasswordController {

	private static final Logger LOG = LoggerFactory
			.getLogger(PasswordController.class);

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ConfigurationServiceImpl configurationService;

	@Autowired
	@Qualifier("javamailservice")
	private EmailingService emailingService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/enviar", method = RequestMethod.POST)
	public @ResponseBody Boolean enviarPassword(@RequestParam String email) {
		final Usuario usuario = usuarioService.getByEmail(email);
		String password = "";
		if (usuario != null) {
			final Map<String, String> emails = new HashMap<>();
			emails.put(usuario.getEmail(), usuario.getPersona().getNombreCompleto());
			PasswordGenerator generator = new PasswordGenerator();
			password = generator.generatePassword(8);
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			usuario.setPassword(encoder.encodePassword(password, null));
			usuarioService.update(usuario);
			final Map mapVelocity = new HashMap();
			mapVelocity.put("usuario", usuario.getUser());
			mapVelocity.put("password", password);
			mapVelocity.put("urlApp", configurationService.getHost());
			mapVelocity.put("host", configurationService.getHost());

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emails, "Recuperación de Password",
							"usuario-password.html", mapVelocity, null, "");
				}
			}).start();

			/*Runnable thread = new Runnable() {
				@Override
				public void run() {
					emailingService.sendEmail(usuario.getEmail(),
							"Recuperación de Password",
							"usuario-password.html", model);
				}
			};

			thread.run();*/

			/*new Thread(new Runnable() {
				@Override
				public void run() {
					emailingService.sendEmail(usuario.getEmail(),
							"Recuperación de Password",
							"usuario-password.html", model);
				}
			}).start();*/
			LOG.info("Se envio el password");
			return Boolean.TRUE;
		} else {
			LOG.info("No se encontro el email");
			return Boolean.FALSE;
		}
	}

	class PasswordGenerator {
		private final String[] UPPER = { "A", "B", "C", "D", "E", "F", "G",
				"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
				"T", "V", "W", "X", "Y", "Z" };

		private final String[] LOWER = { "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "v", "w", "x", "y", "z" };

		private String[] SPECIAL = { "@", "!", "#", "$", "%", "&", "/", "+",
				"-" };

		private static final int UPPER_INDEX = 0;
		private static final int LOWER_INDEX = 1;
		private static final int SPECIAL_INDEX = 2;

		public String generatePassword(int tamanio) {
			StringBuffer passBuffer = new StringBuffer();
			while (passBuffer.length() < tamanio) {
				passBuffer.append(getChar());
			}
			return passBuffer.toString();
		}

		private String getChar() {
			Random random = new Random();
			int index = 0;
			switch (random.nextInt(3)) {
			case UPPER_INDEX:
				index = random.nextInt(UPPER.length);
				return UPPER[index];

			case LOWER_INDEX:
				index = random.nextInt(LOWER.length);
				return LOWER[index];

			case SPECIAL_INDEX:
				index = random.nextInt(SPECIAL.length);
				return SPECIAL[index];

			default:
				return "";
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/cambiar", method = RequestMethod.POST)
	public @ResponseBody Boolean cambiarPassword(
			@RequestParam String passwordActual, String passwordNuevo,
			HttpSession session) {
		final Usuario usuario = (Usuario) session
				.getAttribute(SessionEnum.USUARIO.getValue());
		if (usuario != null && usuario.getPassword().equals(passwordActual)) {
			final Map<String, String> emailUsuario = new HashMap<>();
			emailUsuario.put(usuario.getEmail(), usuario.getPersona().getNombreCompleto());

			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			usuario.setPassword(encoder.encodePassword(passwordNuevo, null));
			usuarioService.update(usuario);
			final Map mapVelocity = new HashMap();
			mapVelocity.put("usuario", usuario.getUser());
			mapVelocity.put("urlApp", configurationService.getHost());
			mapVelocity.put("host", configurationService.getHost());

			/*Runnable thread = new Runnable() {
				@Override
				public void run() {
					emailingService.sendEmail(usuario.getEmail(),
							"Cambio de Password", "cambiar-password.html",
							model);
				}
			};

			thread.run();*/

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailUsuario, "Cambio de Contraseña",
							"cambiar-password.html", mapVelocity, null, "");
				}
			}).start();

			LOG.info("Se envio el password");
			return Boolean.TRUE;
		} else {
			LOG.info("No se encontro al usuario");
			return Boolean.FALSE;
		}
	}
}
