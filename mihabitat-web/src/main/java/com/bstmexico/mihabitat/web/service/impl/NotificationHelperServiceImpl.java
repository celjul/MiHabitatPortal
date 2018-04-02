package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.cargos.model.CargoAgrupador;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunes.usuarios.service.UsuarioService;
import com.bstmexico.mihabitat.comunicacion.blogs.model.*;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.ConfiguracionNotificacionContacto;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.notificaciones.factory.NotificationFactory;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoEstatusReservacion;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.bstmexico.mihabitat.instalaciones.service.ReservacionService;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.service.ArrendatarioService;
import com.bstmexico.mihabitat.notificaciones.model.*;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.departamentos.service.ConfiguracionNotificacionService;
import com.bstmexico.mihabitat.pagos.model.CatalogoEstatusPago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.notificaciones.service.NotificationService;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NotificationHelperServiceImpl implements NotificationHelperService {

	private static final Logger LOG = LoggerFactory
			.getLogger(NotificationHelperServiceImpl.class);

	private SimpMessagingTemplate template;

	@Autowired
	@Qualifier("pagoserviceproxy")
	private PagoService pagoService;

	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	@Qualifier("javamailservice")
	private EmailingService emailingService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private PaymentServiceImpl paymentService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private TemaService temaService;
	
	@Autowired
	private ArrendatarioService arrendatarioService;

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	@Qualifier("reservacionserviceproxy")
	private ReservacionService reservacionService;

	@Autowired
	private ContactoService contactoService;

	private static final SimpleDateFormat FORMATO = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat FORMATO_HORA = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionNuevoTema(Tema tema) {
		Tema temaObj = temaService.get(tema.getId());
		Condominio condominio = condominioService.get(temaObj.getCondominio().getId());

		//Definir los usuarios a los que se enviará la notificación
		//En esta parte se obtienen los objetos Usuario para mandar la notificación en el sistema y los correos para los Emails.
		Collection<Usuario> usuarios = new HashSet<>();
		final Map<String, String> emails = new HashMap();
		if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogBienvenidos() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogNoticias() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogExpertos()){
			usuarios.addAll(usuarioService.getByRol((CatalogoRol) CatalogoFactory.newInstance(
					CatalogoRol.class, configurationServiceImpl.getRolAdministrador().getId())));
			for(Usuario usuario : usuarios) {
				emails.put(usuario.getEmail(), usuario.getPersona().getNombreCompleto());
			}
			/*usuarios.addAll(usuarioService.getByRol((CatalogoRol) CatalogoFactory.newInstance(
					CatalogoRol.class, configurationServiceImpl.getRolContacto().getId())));*/
		} else if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogAvisos() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogTemas() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogDocumentos()) {
			if (condominio!=null) {
				if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogTemas()) {
					if (!org.springframework.util.CollectionUtils.isEmpty(condominio.getAdministradores())) {
						usuarios.addAll(condominio.getAdministradores());
					}
				}
				Map map2 = new HashMap();
				map2.put("condominio.id", condominio.getId());
				Collection<Contacto> contactos;
				contactos = contactoService.search(map2);
				for(Contacto contacto : contactos) {
					Collection<ContactoDepartamento> cds = contacto.getDepartamentos();
					for(ContactoDepartamento cd : cds) {
						if((temaObj.getBlog().getId() != configurationServiceImpl.getBlogAvisos() &&
								cd.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoTema().getId().equals(configurationServiceImpl.getNotConfEmail())) ||
								(temaObj.getBlog().getId() == configurationServiceImpl.getBlogAvisos() &&
										cd.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoAviso().getId().equals(configurationServiceImpl.getNotConfEmail()))
								) {
							for(Email email : contacto.getEmails()){
								emails.put(email.getDireccion(), contacto.getNombreCompleto());
							}
							if(contacto.getUsuario()!=null) {
								usuarios.add(contacto.getUsuario());
							}
						} else if((temaObj.getBlog().getId() != configurationServiceImpl.getBlogAvisos() &&
									cd.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoTema().getId().equals(configurationServiceImpl.getNotConfAplicacion())) ||
										(temaObj.getBlog().getId() == configurationServiceImpl.getBlogAvisos() &&
											cd.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoAviso().getId().equals(configurationServiceImpl.getNotConfAplicacion()))
								) {
							if(contacto.getUsuario()!=null) {
								usuarios.add(contacto.getUsuario());
							}
						}
					}

				}
			}
		} else if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogIncidencias()) {
			if (condominio!=null) {
				if(!org.springframework.util.CollectionUtils.isEmpty(condominio.getAdministradores())) {
					for(Usuario usuario : condominio.getAdministradores()) {
						if(!usuario.equals(temaObj.getUsuario())) {
							usuarios.add(usuario);
							emails.put(usuario.getEmail(), usuario.getPersona().getNombreCompleto());
						}
					}
				}
			}
		} else if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogSoporte()) {
			usuarios.addAll(usuarioService.getByRol((CatalogoRol) CatalogoFactory.newInstance(
					CatalogoRol.class, configurationServiceImpl.getRolSuperAministrador().getId())));
		}

		if(usuarios != null && !CollectionUtils.isEmpty(usuarios)) {
			//Notificaciones en tiempo real desactivadas
			//enviarNotificacionPorUsuarios(usuarios, notification);
		}
		//PARA EL ENVÍO DE EMAIL
		if(emails != null && !org.springframework.util.CollectionUtils.isEmpty(emails)) {

			final Map mapVelocity = new HashMap();
			mapVelocity.put("usuario", temaObj.getPrimerPost().getUsuario().getPersona().getNombreCompleto());
			mapVelocity.put("fecha", FORMATO_HORA.format(temaObj.getPrimerPost().getFecha().toDate()));
			mapVelocity.put("titulo", temaObj.getNombre());
			mapVelocity.put("comentario", temaObj.getPrimerPost().getComentario().replaceAll("(\r\n|\n)", "<br />"));
			mapVelocity.put("idTema", temaObj.getId());
			mapVelocity.put("host", configurationServiceImpl.getHost());

			Notification notification = null;
			if(temaObj instanceof TemaEvento) {
				notification = NotificationFactory.newInstance(NuevoTemaNotification.class);
				notification.setCondominio(condominio);
				((NuevoTemaNotification)notification).setTema(temaObj);
				mapVelocity.put("fechaInicio",
						FORMATO_HORA.format(((TemaEvento) temaObj).getFechaInicio().toDate()));
				mapVelocity.put("fechaFin",
						FORMATO_HORA.format(((TemaEvento) temaObj).getFechaFin().toDate()));
			} else if(temaObj instanceof TemaIncidencia) {
				notification = NotificationFactory.newInstance(IncidenciaActualizadaNotification.class);
				notification.setCondominio(condominio);
				((IncidenciaActualizadaNotification)notification).setAvanceIncidencia((PostIncidencia)((TemaIncidencia)temaObj).getUltimoPost());
			} else {
				notification = NotificationFactory.newInstance(NuevoTemaNotification.class);
				notification.setCondominio(condominio);
				((NuevoTemaNotification)notification).setTema(temaObj);
			}
			//final String[] correos = emails.toArray(new String[emails.size()]);
			final String asunto = notification.getTitulo();
			final String templateEmail = notification.getEmailTemplate();
			final String nombreCondominio = condominio.getNombre();


			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emails, asunto,
							templateEmail, mapVelocity, null, nombreCondominio);
				}
			}).start();
			//emailingService.sendEmail(emails, asunto, plantilla, map, null, nombreCondominio);

			/*new Thread(new Runnable() {
				@Override
				public void run() {
					*//*emailingService.sendEmail(correos,
							asunto, plantilla,
							map, null, nombreCondominio);*//*
				}
			}).start();*/
		}
	}

	//TODO: Falta funcionalidad para incidencia actualizada y ver que hacer con los nuevos comentarios de un tema
	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionNuevoPost(Post post) {
		Tema temaObj = temaService.get(post.getTema().getId());
		Condominio condominio = null;
		if(temaObj.getCondominio() != null) {
			condominio = condominioService.get(temaObj.getCondominio().getId());
		}

		Post primerPost = temaObj.getPrimerPost();
		Post ultimoPost = temaObj.getUltimoPost();
		Collection<Usuario> usuarios = new HashSet<>();
		Collection<String> emails = new HashSet<>();

		if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogExpertos() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogTemas()) {

			usuarios.add(temaObj.getUsuario());
			for(Post post1 : temaObj.getPosts()) {
				usuarios.add(post1.getUsuario());
			}

			enviarCorreo = true;

		}





		boolean enviarCorreo = false;
		final Map map = new HashMap();
		map.put("usuario", ultimoPost.getUsuario().getPersona().getNombre() + " "
				+ ultimoPost.getUsuario().getPersona().getApellidoPaterno() + " "
				+ ultimoPost.getUsuario().getPersona().getApellidoMaterno());
		map.put("fecha",
				FORMATO_HORA.format(ultimoPost.getFecha().toDate()));
		map.put("nombre", temaObj.getNombre());
		map.put("comentario", ultimoPost.getComentario().replaceAll("(\r\n|\n)", "<br />"));
		map.put("idTema", temaObj.getId());
		map.put("host", configurationServiceImpl.getHost());

		final Collection<String> emails = new ArrayList<>();

		final Collection<Adjunto> adjuntos = new ArrayList<>();

		Collection<Usuario> usuarios = new HashSet<>();

		if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogBienvenidos() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogNoticias()){

			*//*usuarios.addAll(usuarioService.getByRol((CatalogoRol) CatalogoFactory.newInstance(
					CatalogoRol.class, configurationServiceImpl.getRolAdministrador().getId())));
			usuarios.addAll(usuarioService.getByRol((CatalogoRol) CatalogoFactory.newInstance(
					CatalogoRol.class, configurationServiceImpl.getRolContacto().getId())));*//*

			enviarCorreo = false;


		} else if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogExpertos() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogTemas()) {

			usuarios.add(temaObj.getUsuario());
			for(Post post1 : temaObj.getPosts()) {
				usuarios.add(post1.getUsuario());
			}

			enviarCorreo = true;

		} else if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogDocumentos() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogIncidencias() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogAnuncios()) {

			usuarios.add(temaObj.getUsuario());

			enviarCorreo = true;

		} else if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogHowto() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogFaqs() ||
				temaObj.getBlog().getId() == configurationServiceImpl.getBlogSoporte()) {
			usuarios.add(temaObj.getUsuario());
			usuarios.addAll(usuarioService.getByRol((CatalogoRol) CatalogoFactory.newInstance(
					CatalogoRol.class, configurationServiceImpl.getRolSuperAministrador().getId())));

			enviarCorreo = true;

		} *//*else if(temaObj.getBlog().getId() == configurationServiceImpl.getBlogIncidencias()) {
			Map map2 = new HashMap();
			map2.put("condominio.id", condominio.getId());
			Collection<Contacto> contactos;
			contactos = contactoService.search(map2);
			for(Contacto contacto : contactos){
				usuarios.add(contacto.getUsuario());
			}
		}*//*

		if (!org.springframework.util.CollectionUtils.isEmpty(usuarios)) {
			for (Usuario usuario : usuarios) {
				Persona persona = personaService.get(usuario.getPersona().getId());
				if (!org.springframework.util.CollectionUtils.isEmpty(persona.getEmails())) {
					for (Email email : persona.getEmails()) {
						emails.add(email.getDireccion());
					}
				}
			}
		}

		Notification notification = null;

		if(temaObj instanceof TemaEvento || temaObj instanceof TemaNormal) {
			notification = NotificationFactory.newInstance(NuevoPostNotification.class);
			notification.setCondominio(condominio);
			notification.setTitulo("Nuevo Comentario");
			notification.setMensaje(ultimoPost.getUsuario().getPersona().getNombre() + " " +
					ultimoPost.getUsuario().getPersona().getApellidoPaterno() + " " +
					ultimoPost.getUsuario().getPersona().getApellidoMaterno() + " ha comentado en  \"" + temaObj.getNombre() + "\"");
		} else if(temaObj instanceof TemaIncidencia) {
			notification = NotificationFactory.newInstance(IncidenciaActualizadaNotification.class);
			notification.setCondominio(condominio);
			notification.setTitulo("Nueva Actualización");
			notification.setMensaje("Hay una nueva actualización para \"" + temaObj.getNombre() + "\" " +
					"con estatus <strong>" + (ultimoPost instanceof PostIncidencia ? ((PostIncidencia) ultimoPost).getEstatusIncidencia().getDescripcion() : "Desconocido") + "</strong>");
		} else {
			notification.setTitulo(temaObj.getNombre());
			notification.setMensaje(ultimoPost.getComentario().length()<50?ultimoPost.getComentario():ultimoPost.getComentario().substring(0,50) + "...");
		}
		notification.setCondominio(condominio);
		notification.setLink("/blogs/temas/actualizar/" + temaObj.getId());
		notification.setFecha(new DateTime(ultimoPost.getFecha()));
		notification.setIdReferencia(ultimoPost.getId());
		notification.setVisto(false);
		enviarNotificacionPorUsuarios(usuarios, notification);
		if(notification instanceof IncidenciaActualizadaNotification) {
			notificationService.save(notification);
		}
		final String titulo = notification.getTitulo();
		if ((!org.springframework.util.CollectionUtils.isEmpty(emails)) && enviarCorreo) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					emailingService.sendEmailBCC(emails
									.toArray(new String[emails.size()]),
							titulo, null,
							map, adjuntos, cond.getNombre());
				}
			}).start();
		}
	}*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionPagoPendiente(Pago pago) {
		Pago p = pagoService.get(pago.getId());
		Condominio condominio = condominioService.get(p.getCondominio().getId());
		Contacto contacto = contactoService.get(p.getContacto().getId());

		EstatusPago e = p.getUltimoEstatus();

		if(e != null && e.getEstatus() != null && e.getEstatus().getId() != null && (e.getEstatus().getDescripcion() == null || e.getEstatus().getDescripcion().trim().equals(""))){
			e.getEstatus().setDescripcion(catalogoService.get(e.getEstatus().getId()).getDescripcion());
		}

		//Se cambia la mecanica de emails para enviarlos con SendingBlue
		/*final Collection<String> emailsAdmins = new HashSet<>();
		if (!org.springframework.util.CollectionUtils.isEmpty(condominio.getAdministradores())) {
			for (Usuario usuario : condominio.getAdministradores()) {
				emailsAdmins.add(usuario.getEmail());
			}
		}*/

		final Map<String,String> emailsAdmins = new HashMap<>();
		if (!org.springframework.util.CollectionUtils.isEmpty(condominio.getAdministradores())) {
			for (Usuario usuario : condominio.getAdministradores()) {
				emailsAdmins.put(usuario.getEmail(), usuario.getPersona()!=null?usuario.getPersona().getNombreCompleto():
						"Administración " + condominio.getNombre());
				//emailsAdmins.add(usuario.getEmail());
			}
		}

		PagoValidadoNotification notification = NotificationFactory.newInstance(PagoValidadoNotification.class);
		notification.setCondominio(condominio);
		notification.setEstatusPago(e);
		notification.setRol(configurationServiceImpl.getRolAdministrador());

		//Notificaciones en tiempo real desactivadas
		/*enviarNotificacionPorPerfil(condominio,
				(CatalogoRol) CatalogoFactory.newInstance(CatalogoRol.class, configurationServiceImpl.getRolAdministrador().getId()),
				notification);*/
		if (!org.springframework.util.CollectionUtils.isEmpty(emailsAdmins)) {
			//Se prepara el comprobante de pago

			final Collection<Adjunto> adjuntos = new ArrayList<>();
			if (p.getComprobante() != null) {

				ByteArrayResource resource = new ByteArrayResource(
						p.getComprobante());
				try {
					Adjunto adjunto = new Adjunto("comprobante_."
							+ Magic.getMagicMatch(p.getComprobante())
							.getExtension(), resource);
					adjuntos.add(adjunto);
				} catch (MagicParseException | MagicMatchNotFoundException
						| MagicException exception) {
					LOG.warn("Error al procesar el archivo " + exception);
				}

			}

			/*final Collection<Adjunto> adjuntos = new ArrayList<>();
			if (p.getComprobante() != null) {
				ByteArrayResource resource = new ByteArrayResource(
						p.getComprobante());
				try {
					Adjunto adjunto = new Adjunto("comprobante_."
							+ Magic.getMagicMatch(p.getComprobante())
							.getExtension(), resource);
					adjuntos.add(adjunto);
				} catch (MagicParseException | MagicMatchNotFoundException
						| MagicException exception) {
					LOG.warn("Error al procesar el archivo " + exception);
				}
			}*/

			final Map mapVelocity = new HashMap();
			mapVelocity.put("contacto", contacto.getNombreCompleto());
			mapVelocity.put("fecha",
					FORMATO.format(p.getFecha()));
			mapVelocity.put("metodoPago", p.getMetodoPago().getDescripcion());
			mapVelocity.put("monto", p.getMonto().setScale(2, RoundingMode.HALF_UP));
			mapVelocity.put("referencia", p.getReferencia()==null?" ":p.getReferencia());
			mapVelocity.put("comentario", e.getComentario()==null?" ":e.getComentario().replaceAll("(\r\n|\n)", "<br />"));
			mapVelocity.put("host", configurationServiceImpl.getHost());
			mapVelocity.put("idPago", p.getId());
			final String templateEmail = notification.getEmailTemplate();
			final String nombreCondominio = condominio.getNombre();
			final String asunto = notification.getTitulo();


			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailsAdmins, asunto,
							templateEmail, mapVelocity, adjuntos, nombreCondominio);
				}
			}).start();
			//emailingService.sendEmail(emailsAdmins, asunto, templateEmail, map, adjuntos, nombreCondominio);
			/*new Thread(new Runnable() {
				@Override
				public void run() {
					emailingService.sendEmailBCC(emailsAdmins
									.toArray(new String[emailsAdmins.size()]),
							asunto, templateEmail,
							map, adjuntos, nombreCondominio);
				}
			}).start();*/
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionPagoValidado(Pago pago) {
		Pago p = pagoService.get(pago.getId());
		Condominio condominio = condominioService.get(p.getCondominio().getId());
		Contacto contacto = contactoService.get(p.getContacto().getId());
		EstatusPago estatusActual = p.getUltimoEstatus();
		if(estatusActual != null && estatusActual.getEstatus() != null && estatusActual.getEstatus().getId() != null && (estatusActual.getEstatus().getDescripcion() == null || estatusActual.getEstatus().getDescripcion().trim().equals(""))){
			estatusActual.getEstatus().setDescripcion(catalogoService.get(estatusActual.getEstatus().getId()).getDescripcion());
		}


		/*for(PagoDepartamento pd : p.getPagosDepartamento()){
			for(ContactoDepartamento cd : pd.getDepartamento().getContactos()){
				if(cd.getConfiguracionNotificacionContacto().getTipoNotificacionAbonoValidado().getId().equals(configurationServiceImpl.getNotConfEmail())){
					for(Email email : cd.getContacto().getEmails()) {
						emailsContacto.add(email.getDireccion());
					}
				}
			}
		}*/

		//TODO: Encontrar una forma de que solo se entere si el pago aplico a su departamento y que esto se relacione con sus permisos configurados
		final Map<String, String> emailsContacto = new HashMap<>();
		if(contacto.getDepartamentos().iterator().hasNext()) {
			ContactoDepartamento cd = contacto.getDepartamentos().iterator().next();
			if(cd.getConfiguracionNotificacionContacto().getTipoNotificacionAbonoValidado().getId().equals(configurationServiceImpl.getNotConfEmail())){
				for (Email email : contacto.getEmails()) {
					emailsContacto.put(email.getDireccion(), contacto.getNombreCompleto());
				}
			}
		}

		if (estatusActual.getEstatus().getId()
				.equals(configurationServiceImpl.getPagoAprobado()) ||
				estatusActual.getEstatus().getId()
						.equals(configurationServiceImpl.getPagoRechazado()) ||
				estatusActual.getEstatus().getId()
						.equals(configurationServiceImpl.getPagoCancelado())) {

			PagoValidadoNotification notification = NotificationFactory.newInstance(PagoValidadoNotification.class);
			notification.setCondominio(pago.getCondominio());
			notification.setRol(configurationServiceImpl.getRolContacto());
			notification.setEstatusPago(estatusActual);
			notificationService.save(notification);
			//Notificaciones en tiempo real desactivadas
			//enviarNotificacionPorUsuario(contacto.getUsuario(), notification);

			if (!org.springframework.util.CollectionUtils.isEmpty(emailsContacto)) {
				//Se prepara el recibo de pago
				final Collection<Adjunto> adjuntos = new ArrayList<Adjunto>();
				if (estatusActual.getEstatus().getId().equals(configurationServiceImpl.getPagoAprobado())) {
					byte[] recibo = paymentService.getRecibo(p);
					ByteArrayResource resource = new ByteArrayResource(recibo);
					try {
						Adjunto adjunto = new Adjunto("Recibo_de_Pago_" + FORMATO.format(p.getFecha()) + "."
								+ Magic.getMagicMatch(recibo)
								.getExtension(), resource);
						adjuntos.add(adjunto);
					} catch (MagicParseException | MagicMatchNotFoundException
							| MagicException exception) {
						LOG.warn("Error al procesar el archivo " + exception);
					}
				}

				final Map mapVelocity = new HashMap();
				mapVelocity.put("contacto", contacto.getNombreCompleto());
				mapVelocity.put("estatus", estatusActual.getEstatus().getDescripcion()==null?" ":estatusActual.getEstatus().getDescripcion());
				mapVelocity.put("fecha",
						FORMATO.format(p.getFecha()));
				mapVelocity.put("metodoPago", p.getMetodoPago().getDescripcion()==null?" ":p.getMetodoPago().getDescripcion());
				mapVelocity.put("monto", p.getMonto().setScale(2, RoundingMode.HALF_UP));
				mapVelocity.put("referencia", p.getReferencia()==null?" ":p.getReferencia());
				mapVelocity.put("comentario", estatusActual.getComentario()==null ? " " : estatusActual.getComentario().replaceAll("(\r\n|\n)", "<br />"));

				mapVelocity.put("idPago", p.getId());
				mapVelocity.put("host", configurationServiceImpl.getHost());

				final String templateEmail = notification.getEmailTemplate();
				final String nombreCondominio = condominio.getNombre();
				final String asunto = notification.getTitulo();
				/*new Thread(new Runnable() {
					@Override
					public void run() {
						emailingService.sendEmailBCC(emailsContacto
										.toArray(new String[emailsContacto.size()]),
								asunto, templateEmail, map,
								adjuntos, nombreCondominio);
					}
				}).start();*/
				new Thread(new Runnable() {
					@SuppressWarnings("unchecked")
					@Override
					public void run() {
						emailingService.sendEmail(emailsContacto, asunto,
								templateEmail, mapVelocity, adjuntos, nombreCondominio);
					}
				}).start();
				//emailingService.sendEmail(emailsContacto, asunto, templateEmail, map, adjuntos, nombreCondominio);
			}
		}
	}

	@Override
	public void enviarNotificacionNuevosCargos(CargoAgrupador cargo) {
		for(CargoDepartamento cargoDepartamento : cargo.getCargos()){
			enviarNotificacionNuevoCargo(cargoDepartamento);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionNuevoCargo(CargoDepartamento cargo) {
		CargoDepartamento cargoObj = (CargoDepartamento)cargoService.getConDepartamento(cargo.getId(), CargoDepartamento.class, false);
		Condominio condominio = condominioService.get(cargo.getCondominio().getId());

		final Map<String, String> emailsContacto = new HashMap<>();
		if (!org.springframework.util.CollectionUtils.isEmpty(cargoObj.getDepartamento().getContactos())) {
			for(ContactoDepartamento contactoDepartamento : cargoObj.getDepartamento().getContactos()) {
				if(contactoDepartamento.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoCargo().getId().equals(configurationServiceImpl.getNotConfEmail())) {
					if (!org.springframework.util.CollectionUtils.isEmpty(contactoDepartamento.getContacto().getEmails())) {
						for (Email email : contactoDepartamento.getContacto().getEmails()) {
							emailsContacto.put(email.getDireccion(), contactoDepartamento.getContacto().getNombreCompleto());
						}
					}
				}
			}
		}

		NuevoCargoNotification notification = NotificationFactory.newInstance(NuevoCargoNotification.class);
		notification.setCondominio(condominio);
		notification.setVisto(false);
		notification.setCargo(cargoObj);
		notificationService.save(notification);

		if(!org.springframework.util.CollectionUtils.isEmpty(emailsContacto)) {
			final Map mapVelocity = new HashMap();
			mapVelocity.put("condominio", condominio.getNombre());
			mapVelocity.put("departamento", cargoObj.getDepartamento().getNombre());
			mapVelocity.put("concepto", cargoObj.getConcepto()==null?" ":cargoObj.getConcepto());
			mapVelocity.put("fecha",FORMATO.format(cargoObj.getFecha()));
			mapVelocity.put("monto", cargoObj.getTotalMonto().setScale(2, RoundingMode.HALF_UP));
			mapVelocity.put("montoDescuento", cargoObj.getDescuentosCalculados().setScale(2, RoundingMode.HALF_UP));
			mapVelocity.put("fechaDescuento", cargoObj.getDescuento()!=null?cargoObj.getDescuento().getFecha():"");
			mapVelocity.put("fechaRecargo", cargoObj.getRecargo()!=null?cargoObj.getRecargo().getFecha():"");
			mapVelocity.put("montoRecargo", cargoObj.getRecargosCalculados().setScale(2, RoundingMode.HALF_UP));
			mapVelocity.put("host", configurationServiceImpl.getHost());

			final String templateEmail = notification.getEmailTemplate();
			final String nombreCondominio = condominio.getNombre();
			final String asunto = notification.getTitulo();

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailsContacto, asunto,
							templateEmail, mapVelocity, null, nombreCondominio);
				}
			}).start();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionNuevoMovimiento(MovimientoCargo movimientoCargo) {
		if(movimientoCargo.getTipo().getId() == configurationServiceImpl.getRecargo()) {
			MovimientoCargo recargoObj = movimientoService.getConCargo(movimientoCargo.getId(), MovimientoCargo.class);
			Condominio condominio = condominioService.get(recargoObj.getCargo().getCondominio().getId());

			NuevoRecargoNotificacion notification = NotificationFactory.newInstance(NuevoRecargoNotificacion.class);
			notification.setVisto(false);
			notification.setCondominio(condominio);
			notification.setRecargo(recargoObj);
			notificationService.save(notification);
			//Notificaciones en tiempo real desactivadas
			//enviarNotificacionPorDepartamento(recargoObj.getCargo().getDepartamento(), 4, notification);

			final Map<String, String> emailsContacto = new HashMap<>();
			if (!org.springframework.util.CollectionUtils.isEmpty(recargoObj.getCargo().getDepartamento().getContactos())) {
				for (ContactoDepartamento contactoDepartamento : recargoObj.getCargo().getDepartamento().getContactos()) {
					if(contactoDepartamento.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoRecargo().getId().equals(configurationServiceImpl.getNotConfEmail())) {
						if (!org.springframework.util.CollectionUtils.isEmpty(contactoDepartamento.getContacto().getEmails())) {
							for (Email email : contactoDepartamento.getContacto().getEmails()) {
								emailsContacto.put(email.getDireccion(), contactoDepartamento.getContacto().getNombreCompleto());
							}
						}
					}
				}
			}

			if(!org.springframework.util.CollectionUtils.isEmpty(emailsContacto)) {
				final Map mapVelocity = new HashMap();
				mapVelocity.put("condominio", condominio.getNombre());
				mapVelocity.put("departamento", recargoObj.getCargo().getDepartamento().getNombre());
				mapVelocity.put("concepto", recargoObj.getCargo().getConcepto() == null ? " " : recargoObj.getCargo().getConcepto());
				mapVelocity.put("fechaRecargo",
						FORMATO.format(recargoObj.getFecha()));
				mapVelocity.put("montoRecargo", recargoObj.getDebe().setScale(2, RoundingMode.HALF_UP));
				mapVelocity.put("host", configurationServiceImpl.getHost());

				final String templateEmail = notification.getEmailTemplate();
				final String nombreCondominio = condominio.getNombre();
				final String asunto = notification.getTitulo();

				/*new Thread(new Runnable() {
					@Override
					public void run() {
						emailingService.sendEmailBCC(emailsContacto
										.toArray(new String[emailsContacto.size()]),
								asunto, templateEmail,
								map, null, nombreCondominio);
					}
				}).start();*/

				new Thread(new Runnable() {
					@SuppressWarnings("unchecked")
					@Override
					public void run() {
						emailingService.sendEmail(emailsContacto, asunto,
								templateEmail, mapVelocity, null, nombreCondominio);
					}
				}).start();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionReservacionValidada(Reservacion reservacion) {
		Reservacion reservacionObj = reservacionService.getConInstalacion(reservacion.getId());
		Contacto contacto = contactoService.get(reservacionObj.getContacto().getId());
		Condominio condominio = condominioService.get(reservacionObj.getContacto().getCondominio().getId());

		/*final Collection<String> emailsAdmins = new HashSet<>();
		if (!org.springframework.util.CollectionUtils.isEmpty(condominio.getAdministradores())) {
			for (Usuario usuario : condominio.getAdministradores()) {
				emailsAdmins.add(usuario.getEmail());
			}
		}*/

		ReservacionValidadaNotification notification = NotificationFactory.newInstance(ReservacionValidadaNotification.class);
		notification.setRol(configurationServiceImpl.getRolAdministrador());
		notification.setCondominio(condominio);
		notification.setReservacion(reservacionObj);
		notificationService.save(notification);
		//Notificaciones en tiempo real desactivadas
		//enviarNotificacionPorUsuario(contacto.getUsuario(), notification);

		final Map<String, String> emailsContacto = new HashMap<>();
		if(contacto.getDepartamentos().iterator().hasNext()) {
			ContactoDepartamento cd = contacto.getDepartamentos().iterator().next();
			if(cd.getConfiguracionNotificacionContacto().getTipoNotificacionReservacionValidada().getId().equals(configurationServiceImpl.getNotConfEmail())){
				for (Email email : contacto.getEmails()) {
					emailsContacto.put(email.getDireccion(), contacto.getNombreCompleto());
				}
			}
		}

		if(!org.springframework.util.CollectionUtils.isEmpty(emailsContacto)) {
			final Map mapVelocity = new HashMap();
			mapVelocity.put("condominio", condominio.getNombre());
			mapVelocity.put("estatus", reservacionObj.getEstatusReservacion().getDescripcion());
			mapVelocity.put("fechaIni", FORMATO_HORA.format(reservacionObj.getFechaInicio().toDate()));
			mapVelocity.put("fechaFin", FORMATO_HORA.format(reservacionObj.getFechaFin().toDate()));
			mapVelocity.put("instalacion", reservacionObj.getInstalacion().getNombre());
			mapVelocity.put("idInstalacion", reservacionObj.getInstalacion().getId());
			mapVelocity.put("host", configurationServiceImpl.getHost());

			final String templateEmail = notification.getEmailTemplate();
			final String nombreCondominio = condominio.getNombre();
			final String asunto = notification.getTitulo();

			/*new Thread(new Runnable() {
				@Override
				public void run() {
					emailingService.sendEmailBCC(emailsContacto
									.toArray(new String[emailsContacto.size()]),
							asunto, templateEmail,
							map, null, nombreCondominio);
				}
			}).start();*/

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailsContacto, asunto,
							templateEmail, mapVelocity, null, nombreCondominio);
				}
			}).start();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionReservacionPendiente(Reservacion reservacion) {

		Reservacion reservacionObj = reservacionService.getConInstalacion(reservacion.getId());
		Condominio condominio = condominioService.get(reservacionObj.getContacto().getCondominio().getId());

		ReservacionValidadaNotification notification = NotificationFactory.newInstance(ReservacionValidadaNotification.class);
		notification.setCondominio(condominio);
		notification.setReservacion(reservacion);
		//Notificaciones en tiempo real desactivadas
		/*enviarNotificacionPorPerfil(condominio,
				(CatalogoRol) CatalogoFactory.newInstance(CatalogoRol.class, configurationServiceImpl.getRolAdministrador().getId()),
				notification);*/

		final Map<String, String> emailsAdmins = new HashMap<>();
		if (!org.springframework.util.CollectionUtils.isEmpty(condominio.getAdministradores())) {
			for (Usuario usuario : condominio.getAdministradores()) {
				emailsAdmins.put(usuario.getEmail(), usuario.getPersona().getNombreCompleto());
			}
		}

		if(!org.springframework.util.CollectionUtils.isEmpty(emailsAdmins)) {
			final Map mapVelocity = new HashMap();
			mapVelocity.put("condominio", condominio.getNombre());
			mapVelocity.put("contacto", reservacionObj.getContacto().getNombre() + " " + reservacionObj.getContacto().getApellidoPaterno() +
					" " + reservacionObj.getContacto().getApellidoMaterno());
			mapVelocity.put("fechaIni", FORMATO_HORA.format(reservacionObj.getFechaInicio().toDate()));
			mapVelocity.put("fechaFin", FORMATO_HORA.format(reservacionObj.getFechaFin().toDate()));
			mapVelocity.put("instalacion", reservacionObj.getInstalacion().getNombre());
			mapVelocity.put("idInstalacion", reservacionObj.getInstalacion().getId());
			mapVelocity.put("host", configurationServiceImpl.getHost());

			final String templateEmail = notification.getEmailTemplate();
			final String nombreCondominio = condominio.getNombre();
			final String asunto = notification.getTitulo();
		/*}*/
			/*new Thread(new Runnable() {
				@Override
				public void run() {
					emailingService.sendEmailBCC(emailsAdmins
									.toArray(new String[emailsAdmins.size()]),
							asunto, templateEmail,
							map, null, nombreCondominio);
				}
			}).start();*/

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailsAdmins, asunto,
							templateEmail, mapVelocity, null, nombreCondominio);
				}
			}).start();
		}
	}

	//TODO para re envío de recibos de pago
	/*@Override
	public void enviarNotificacionPagoAprobado(final Adjunto recibo, final String mensaje,
						   final String... emails) {

		if (emails != null && emails.length > 0) {
			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					@SuppressWarnings("rawtypes")
					Map map = new HashMap();
					map.put("mensaje", mensaje);
					Collection<Adjunto> adjuntos = new ArrayList<Adjunto>();
					adjuntos.add(recibo);
					emailingService.sendEmail(emails, "Recibo de Pago",
							"pago-recibo.html", map, adjuntos);
				}
			}).start();
		}
	}*/


	@Override
	public void enviarNotificacionPorDepartamento(Departamento departamento, Integer tipo,
											Notification notificacion) {
		try {
			Assert.notNull(departamento);
			Assert.notNull(departamento.getId());
			Assert.notNull(tipo);
			if(tipo == 0) {
				enviarMensaje(String.format("departamento/%s/principal", departamento.getId()), notificacion);
			}
			else if(tipo == 1) {
				enviarMensaje(String.format("departamento/%s/habitantes", departamento.getId()), notificacion);
			}
			else if(tipo == 2) {
				enviarMensaje(String.format("departamento/%s/propietarios", departamento.getId()), notificacion);
			}
			else if(tipo == 4) {
				enviarMensaje(String.format("departamento/%s/todos", departamento.getId()), notificacion);
			}
		}  catch (IllegalArgumentException ex) {
			LOG.info("La notificacion no pude ser enviada, ya que o el "
					+ "usuario es nulo.");
		}
	}

	@Override
	public void enviarNotificacionPorPerfil(Condominio condominio, CatalogoRol rol, Notification notificacion) {
		try {
			Assert.notNull(condominio);
			Assert.notNull(condominio.getId());
			Assert.notNull(rol);
			Assert.notNull(rol.getId());
			if(rol.getId() == configurationServiceImpl.getRolAdministrador().getId()) {
				enviarMensaje(String.format("condominio/%s/administradores", condominio.getId()), notificacion);
			}
			else if(rol.getId() == configurationServiceImpl.getRolContacto().getId()) {
				enviarMensaje(String.format("condominio/%s/contactos", condominio.getId()), notificacion);
			}
			//TODO funcionalidad para el consejo de vigilancia
			/*else if(rol.getId() == configurationServiceImpl.getRolConsejo().getId()) {
				enviarMensaje(String.format("condominio/%s/consejo", condominio.getId()), notificacion);
			}*/
		}  catch (IllegalArgumentException ex) {
			LOG.info("La notificacion no pude ser enviada, ya que o el "
					+ "usuario es nulo.");
		}
	}

	@Override
	public void enviarNotificacionPorUsuario(Usuario usuario,
											 Notification notificacion) {
		try {
			Assert.notNull(usuario);
			Assert.notNull(usuario.getId());
			enviarMensaje(String.format("usuario/%s", usuario.getId()), notificacion);
		}  catch (IllegalArgumentException ex) {
			LOG.info("La notificacion no pude ser enviada, ya que o el "
					+ "usuario es nulo.");
		}
	}

	@Override
	public void enviarNotificacionPorUsuarios(Collection<Usuario> usuarios, Notification notificacion) {
		try {
			Assert.notNull(usuarios);
			Assert.notNull(notificacion);
			for(Usuario usuario : usuarios){
				enviarNotificacionPorUsuario(usuario, notificacion);
			}
		}  catch (IllegalArgumentException ex) {
			LOG.info("La notificacion no pude ser enviada, ya que o el "
					+ "usuario es nulo.");
		}
	}

	@Override
	public void enviarNotificacionParaTodos(Notification notificacion) {
		try {
			Assert.notNull(notificacion);
			enviarMensaje("todos", notificacion);
		}  catch (IllegalArgumentException ex) {
			LOG.info("La notificacion no pude ser enviada, ya que o el "
					+ "usuario es nulo.");
		}
	}

	private void enviarMensaje(String canal, Notification notificacion) {
		try {
			Assert.notNull(canal);
			Assert.notNull(notificacion);
			if(notificacion.getCondominio() != null) {
				notificacion.getCondominio().setLogoCondominio(null);
			}

			String canalCompleto = String.format("/topic/notificaciones/%s",
					canal);
			LOG.debug(String.format("La notificacion sera enviada al canal %s.",
					canalCompleto));
			LOG.debug("Mensaje: " + notificacion.toString());
			if(template != null) {
				template.convertAndSend(canalCompleto, notificacion);
			}
		} catch (IllegalArgumentException ex) {
			LOG.info("La notificacion no pude ser enviada, ya que o el "
					+ "usuario o el mensaje son nulos.");
		}
	}

	public void setTemplate(SimpMessagingTemplate template) {
		this.template = template;
	}

	public SimpMessagingTemplate getTemplate() {
		return template;
	}

	@Override
	public Collection<Notification> listPendientesAdmin(Condominio condominio) {
		DateTime hoy = DateTime.now();
		try {
			Assert.notNull(condominio, "SEREX001");
			Collection<Notification> notificacionesPendientes = new ArrayList<>();
			for(Pago pago :pagoService.getPagosByStatus(condominio,
					(CatalogoEstatusPago) CatalogoFactory.newInstance(CatalogoEstatusPago.class, configurationServiceImpl.getPagoPendiente()))) {
				if(pago != null) {
					PagoValidadoNotification notification = (PagoValidadoNotification)NotificationFactory.newInstance(PagoValidadoNotification.class, condominio);
					EstatusPago estatusPago = pago.getUltimoEstatus();
					estatusPago.setPago(pago);
					notification.setRol(configurationServiceImpl.getRolAdministrador());
					notification.setEstatusPago(pago.getUltimoEstatus());
					notificacionesPendientes.add(notification);
				}
			}
			for(Reservacion reservacion :reservacionService.getListByCondominioByEstatus(condominio,
					(CatalogoEstatusReservacion) CatalogoFactory.newInstance(CatalogoEstatusReservacion.class, configurationServiceImpl.getReservacionPendiente()))) {
				ReservacionValidadaNotification notification = (ReservacionValidadaNotification)NotificationFactory.newInstance(
						ReservacionValidadaNotification.class, condominio);
				notification.setReservacion(reservacion);
				notification.setRol(configurationServiceImpl.getRolAdministrador());
				notificacionesPendientes.add(notification);
			}
			for(Tema tema :temaService.getIncidenciasByStatus(condominio,
					(CatalogoEstatusIncidencia) CatalogoFactory.newInstance(CatalogoEstatusIncidencia.class, configurationServiceImpl.getIncidenciaEstatusSolicitada()))) {
				if(tema != null) {
					PostIncidencia ultimoAvance = (PostIncidencia) tema.getUltimoPost();
					/*PostIncidencia ultimoAvance = null;
					for(Post avanceIncidencia : tema.getPosts()) {
						ultimoAvance = (PostIncidencia)avanceIncidencia;
					}*/
					if(ultimoAvance != null) {
						IncidenciaActualizadaNotification notification = (IncidenciaActualizadaNotification) NotificationFactory.newInstance(IncidenciaActualizadaNotification.class, condominio);
						notification.setRol(configurationServiceImpl.getRolAdministrador());
						notification.setAvanceIncidencia(ultimoAvance);
						notificacionesPendientes.add(notification);
					}
				}
			}
			return notificacionesPendientes;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}

	}

	@Override
	public Collection<Notification> listaNotificacionesBlogs(Condominio condominio, Usuario usuario) {

		Collection<Notification> notificaciones = new ArrayList<>();
		//TODO:Pendientes notificaciones de actividad en foros
		/*for(Tema tema : temaService.getList(condominio)) {
			boolean notificar = true;
			if(!org.springframework.util.CollectionUtils.isEmpty(tema.getTemasRevisados())){
				for(TemaRevisado temaRevisado : tema.getTemasRevisados()) {
					if(temaRevisado.getUsuario().getId() == usuario.getId() &&
							temaRevisado.getFecha().toDate().getTime() >
									tema.getUltimaActividad().toDate().getTime()) {
						notificar = false;
					}
				}
			}
			if(notificar && !(tema instanceof TemaIncidencia)) {
				NuevoPostNotification nuevoPostNotification = NotificationFactory.newInstance(
						NuevoPostNotification.class);
				nuevoPostNotification.setFecha(tema.getUltimaActividad());
				nuevoPostNotification.setSubtipo(
						tema instanceof TemaNormal ? "tema_normal" : (
								tema instanceof TemaEvento ? "tema_evento" : (
										tema instanceof TemaIncidencia ? (
												((TemaIncidencia) tema).getTipoIncidencia().getId() == configurationServiceImpl.getIncidenciaTipoIncidencia()?"tema_incidencia" : "tema_proyecto"
										) : "tema_otro"
								)
						)
				);
				nuevoPostNotification.setTitulo("/blogs/temas/actualizar/" + tema.getId());
				if(!org.springframework.util.CollectionUtils.isEmpty(tema.getPosts()) && tema.getPosts().size() > 1) {
					nuevoPostNotification.setTitulo("Comentarios sin leer");
					nuevoPostNotification.setMensaje("Hay nuevos comentarios sin leer en <strong>\"" + tema.getNombre() + "\"</strong>");
				} else {
					nuevoPostNotification.setTitulo(
							tema instanceof TemaNormal ? "Nuevo Tema" : (
									tema instanceof TemaEvento ? "Nuevo Evento" : (
											tema instanceof TemaIncidencia ? (
													((TemaIncidencia) tema).getTipoIncidencia().getId() == configurationServiceImpl.getIncidenciaTipoIncidencia()?"Tema Incidencia" : "Tema Proyecto"
											) : "tema_otro"
									)
							)
					);
					nuevoPostNotification.setMensaje(tema.getNombre());
				}
				notificaciones.add(nuevoPostNotification);
			}
		}*/
		return notificaciones;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Notification> listarNotificacionesContacto(Condominio condominio, Usuario usuario) {

		Collection<Notification> notificationesTodas = new ArrayList<>();
		Map map = new HashMap();
		map.put("condominio", condominio);
		map.put("usuario", usuario);
		for(Contacto contacto: contactoService.search(map)){

			notificationesTodas.addAll(notificationService.getByContacto(contacto));

			for(Pago pago :pagoService.getPagosByStatus(contacto,
					(CatalogoEstatusPago) CatalogoFactory.newInstance(CatalogoEstatusPago.class, configurationServiceImpl.getPagoPendiente()))) {
				if(pago != null) {
					PagoValidadoNotification notification = (PagoValidadoNotification)NotificationFactory.newInstance(
							PagoValidadoNotification.class, condominio);
					notification.setEstatusPago(pago.getUltimoEstatus());
					notification.setRol(configurationServiceImpl.getRolContacto());
					notificationesTodas.add(notification);
				}
			}
			map.clear();
			map.put("usuario", contacto.getUsuario());
			Collection<TemaIncidencia> incidencias = temaService.getIncidenciasByStatus(contacto, (CatalogoEstatusIncidencia) CatalogoFactory.newInstance(
					CatalogoEstatusIncidencia.class, configurationServiceImpl.getIncidenciaEstatusSolicitada()));
			for(TemaIncidencia incidenciaPendiente : incidencias) {
				IncidenciaActualizadaNotification notification = (IncidenciaActualizadaNotification)NotificationFactory.newInstance(
						IncidenciaActualizadaNotification.class, condominio);
				notification.setRol(configurationServiceImpl.getRolContacto());
				notification.setAvanceIncidencia((PostIncidencia)incidenciaPendiente.getUltimoPost());
				notificationesTodas.add(notification);
			}
			/*for(Tema tema : temaService.search(map)) {
				temaService
				if(tema != null && tema instanceof TemaIncidencia) {
					PostIncidencia ultimoAvance = (PostIncidencia)tema.getUltimoPost();
					if(ultimoAvance != null && ultimoAvance.getEstatusIncidencia().getId() == configurationServiceImpl.getIncidenciaEstatusSolicitada()) {
						//Duration duracion = new Duration(new DateTime(ultimoAvance.getFecha()), hoy);
						IncidenciaActualizadaNotification notification = (IncidenciaActualizadaNotification)NotificationFactory.newInstance(
								IncidenciaActualizadaNotification.class, condominio);
						notification.setAvanceIncidencia(ultimoAvance);
						notificationesTodas.add(notification);
				} else if(ultimoAvance != null && ultimoAvance.getEstatusIncidencia().getId() == configurationServiceImpl.getIncidenciaEstatusSolicitada()) {
						Duration duracion = new Duration(new DateTime(ultimoAvance.getFecha()), hoy);
						Notification notification = NotificationFactory.newInstance(IncidenciasPendientesNotification.class,
								condominio,
								"",
								"Nueva actualización de su incidencia reportada <strong>" +
										tema.getNombre() + "</strong> con estatus <strong>" +
										(
												ultimoAvance.getEstatusIncidencia().getId() == configurationServiceImpl.getIncidenciaEstatusPendiente() ? "En Atención" :
														(
																ultimoAvance.getEstatusIncidencia().getId() == configurationServiceImpl.getIncidenciaEstatusRepetida() ? "Repetida" :
																		(
																				ultimoAvance.getEstatusIncidencia().getId() == configurationServiceImpl.getIncidenciaEstatusConcluido() ? "Concluida" :
																						(
																								ultimoAvance.getEstatusIncidencia().getId() == configurationServiceImpl.getIncidenciaEstatusCancelado() ? "Cancelada" :
																										"Modificada"
																						)
																		)
														)
										)
										+
										"</stronf>.&nbsp;" +
										"<span class='pull-right font-xs text-muted'><i> " +

										"Hace " +
										(duracion.getStandardDays() != 0 ? duracion.getStandardDays() + " dias y " : " ") +
										(duracion.getStandardHours() - (duracion.getStandardDays() * 24)) + " horas..."

										+ "...</i></span>",
								String.format("/contacto/blogs/temas/actualizar/%s", tema.getId()));
						notification.setFecha(new DateTime(ultimoAvance.getFecha()));
						notification.setVisto(!(tema.getTemaRevisado(usuario) == null ||
								tema.getTemaRevisado(usuario).getFecha().toDate().getTime() < tema.getUltimaActividad().toDate().getTime()));
						notificationesTodas.add(notification);
					}
				}
			}*/

			map.clear();
			map.put("contacto", contacto);
			map.put("estatusReservacion.id", configurationServiceImpl.getReservacionPendiente());
			for (Reservacion reservacion : reservacionService.search(map)) {
				Reservacion reservacionObj = reservacionService.getConInstalacion(reservacion.getId());
				ReservacionValidadaNotification notification = (ReservacionValidadaNotification)NotificationFactory.newInstance(
						ReservacionValidadaNotification.class, condominio);
				notification.setReservacion(reservacionObj);
				notification.setRol(configurationServiceImpl.getRolContacto());
				notificationesTodas.add(notification);
			}
		}
		return notificationesTodas;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void enviarNotificacionNuevoArrendador(Arrendatario arrendatario) {
		Arrendatario arrendatarioObj = arrendatarioService.get(arrendatario.getIdArrendador());
		Condominio condominio = condominioService.get(arrendatarioObj.getCondominio().getId());

		//Definir los usuarios a los que se enviara� la notificacion
		//En esta parte se obtienen los objetos Usuario para mandar la notificacion en el sistema 
		// y los correos para los Emails.
		Collection<Usuario> usuarios = new HashSet<>();
		final Map<String, String> emails = new HashMap();
			if (condominio!=null) {
				if(!org.springframework.util.CollectionUtils.isEmpty(condominio.getAdministradores())) {
					for(Usuario usuario : condominio.getAdministradores()) {
							usuarios.add(usuario);
							emails.put(usuario.getEmail(), usuario.getPersona().getNombreCompleto());					
					}
				}
			}
		//PARA EL ENVIO DE EMAIL
		if(emails != null && !org.springframework.util.CollectionUtils.isEmpty(emails)) {

			final Map mapVelocity = new HashMap();
			mapVelocity.put("usuario", temaObj.getPrimerPost().getUsuario().getPersona().getNombreCompleto());
			mapVelocity.put("fecha", FORMATO_HORA.format(temaObj.getPrimerPost().getFecha().toDate()));
			mapVelocity.put("titulo", temaObj.getNombre());
			mapVelocity.put("comentario", temaObj.getPrimerPost().getComentario().replaceAll("(\r\n|\n)", "<br />"));
			mapVelocity.put("idTema", temaObj.getId());
			mapVelocity.put("host", configurationServiceImpl.getHost());

			Notification notification = null;
			if(temaObj instanceof TemaEvento) {
				notification = NotificationFactory.newInstance(NuevoTemaNotification.class);
				notification.setCondominio(condominio);
				((NuevoTemaNotification)notification).setTema(temaObj);
				mapVelocity.put("fechaInicio",
						FORMATO_HORA.format(((TemaEvento) temaObj).getFechaInicio().toDate()));
				mapVelocity.put("fechaFin",
						FORMATO_HORA.format(((TemaEvento) temaObj).getFechaFin().toDate()));
			} 
			final String asunto = notification.getTitulo();
			final String templateEmail = notification.getEmailTemplate();
			final String nombreCondominio = condominio.getNombre();


			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emails, asunto,
							templateEmail, mapVelocity, null, nombreCondominio);
				}
			}).start();
		}
	}
	
}
