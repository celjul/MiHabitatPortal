package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.factory.PagoFactory;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.service.PaymentService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOG = LoggerFactory
			.getLogger(PaymentServiceImpl.class);

	@Autowired
	@Qualifier("pagoserviceproxy")
	private PagoService pagoService;

	@Autowired
	@Qualifier("javamailservice")
	private EmailingService emailingService;

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private ServletContext context;

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void sendEmail(Pago pago) {
		Pago p = pagoService.get(pago.getId());

		EstatusPago e = null;
		Iterator<EstatusPago> it = p.getEstatus().iterator();
		while (it.hasNext()) {
			e = it.next();
		}

		final Map map = new HashMap();
		map.put("contacto", p.getContacto().getNombre() + " "
				+ p.getContacto().getApellidoPaterno());
		map.put("fecha",
				new SimpleDateFormat("dd-MM-yyyy").format(p.getFecha()));
		map.put("metodoPago", p.getMetodoPago().getDescripcion());
		map.put("monto", p.getMonto().setScale(2, RoundingMode.HALF_UP));
		map.put("referencia",
				!StringUtils.isEmpty(p.getReferencia()) ? p.getReferencia()
						: "");
		map.put("comentario",
				!StringUtils.isEmpty(e.getComentario()) ? e.getComentario()
						: "");

		final Collection<String> emailsAdmins = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(p.getCondominio().getAdministradores())) {
			for (Usuario usuario : p.getCondominio().getAdministradores()) {
				if (!CollectionUtils.isEmpty(usuario.getPersona().getEmails())) {
					for (Email email : usuario.getPersona().getEmails()) {
						emailsAdmins.add(email.getDireccion());
					}
				}
			}
		}

		final Collection<String> emailsContacto = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(p.getContacto().getEmails())) {
			for (Email email : p.getContacto().getEmails()) {
				emailsContacto.add(email.getDireccion());
			}
		}

		final Collection<Adjunto> adjuntos = new ArrayList<Adjunto>();
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

		if (e.getEstatus().getId()
				.equals(configurationServiceImpl.getPagoPendiente())) {
			if (!CollectionUtils.isEmpty(emailsAdmins)) {
				if (p.getEstatus().size() == 1) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							emailingService.sendEmail(emailsAdmins
									.toArray(new String[emailsAdmins.size()]),
									"Pago Pendiente", "pago-pendiente.html",
									map, adjuntos);
						}
					}).run();
				} else {
					new Thread(new Runnable() {
						@Override
						public void run() {
							emailingService.sendEmail(emailsAdmins
									.toArray(new String[emailsAdmins.size()]),
									"Pago Reenviado", "pago-reenviado.html",
									map, adjuntos);
						}
					}).run();
				}
			}
		} else if (e.getEstatus().getId()
				.equals(configurationServiceImpl.getPagoAprobado())) {
			if (!CollectionUtils.isEmpty(emailsContacto)) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						emailingService.sendEmail(emailsContacto
								.toArray(new String[emailsContacto.size()]),
								"Pago Aprobado", "pago-validado.html", map,
								adjuntos);
					}
				}).run();
			}
		} else if (e.getEstatus().getId()
				.equals(configurationServiceImpl.getPagoRechazado())) {
			if (!CollectionUtils.isEmpty(emailsContacto)) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						emailingService.sendEmail(emailsContacto
								.toArray(new String[emailsContacto.size()]),
								"Pago Rechazado", "pago-rechazado.html", map,
								adjuntos);
					}
				}).run();
			}
		} else if (e.getEstatus().getId()
				.equals(configurationServiceImpl.getPagoCancelado())) {
			if (!CollectionUtils.isEmpty(emailsContacto)) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						emailingService.sendEmail(emailsContacto
								.toArray(new String[emailsContacto.size()]),
								"Pago Cancelado", "pago-cancelado.html", map,
								adjuntos);
					}
				}).run();
			}
		}
	}*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public byte[] getRecibo(Pago pago) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator + "pagos"
				+ File.separator;
		pago = pagoService.get(pago.getId());

		try {
			Map map = new HashMap();
			map.put("SUBREPORT_DIR", directorio);
			map.put("IMAGEN", contexto + "recursos" + File.separator + "img"
					+ File.separator + configurationServiceImpl.getLogo());
			map.put("CONTEXTO", contexto);

			EstatusPago ultimoEstatus = null;
			StringBuffer sb = new StringBuffer();
			for (EstatusPago estatusPago : pago.getEstatus()){
				ultimoEstatus = estatusPago;
				sb.append((estatusPago.getComentario() != null && !estatusPago.getComentario().equals("")) ? estatusPago.getComentario() + " | " : "");
			}
			if(ultimoEstatus != null) {
				map.put("CANCELADO", ultimoEstatus.getEstatus().getId() == configurationServiceImpl.getPagoCancelado());
				map.put("COMENTARIOS", sb.toString());
			}

			for(PagoDepartamento pd : pago.getPagosDepartamento()) {
				Collection<MovimientoCargoAplicado> tempMcas = new ArrayList<>();
				for(MovimientoCargoAplicado movimiento : pd.getMovimientos()) {
					Boolean existe = Boolean.FALSE;
					for(MovimientoCargoAplicado tmpMca : tempMcas) {
						if(tmpMca.getMovimientoCargo().getCargo().getId().equals(movimiento.getMovimientoCargo().getCargo().getId())) {
							if(movimiento.getHaber() != null) {
								tmpMca.setHaber(tmpMca.getHaber().add(( movimiento.getHaber() != null) ? movimiento.getHaber() : BigDecimal.ZERO));
							}
							if(movimiento.getDebe() != null) {
								tmpMca.setDebe(tmpMca.getDebe().add((movimiento.getDebe() != null) ? movimiento.getDebe() : BigDecimal.ZERO));
							}
							existe = Boolean.TRUE;
						}
					}
					if(!existe.booleanValue()){
						MovimientoCargoAplicado nvoMca = MovimientoFactory.newInstance(MovimientoCargoAplicado.class, movimiento.getId());
						nvoMca.setAplicado(Boolean.TRUE);
						nvoMca.setCancelado(Boolean.FALSE);
						nvoMca.setCuenta(movimiento.getCuenta());
						nvoMca.setImprimible(Boolean.TRUE);
						nvoMca.setMovimientoCargo(movimiento.getMovimientoCargo());
						nvoMca.setPago(movimiento.getPago());
						nvoMca.setPagoDepartamento(movimiento.getPagoDepartamento());
						nvoMca.setTipo(movimiento.getTipo());
						nvoMca.setDebe(movimiento.getDebe() != null ? movimiento.getDebe() :  BigDecimal.ZERO);
						nvoMca.setFecha(movimiento.getFecha());
						nvoMca.setHaber(movimiento.getHaber() != null ? movimiento.getHaber() :  BigDecimal.ZERO);
						nvoMca.setId(movimiento.getId());
						tempMcas.add(nvoMca);
					}
				}
				pd.setMovimientos(tempMcas);
			}

			Condominio condominio = condominioService.readConImagen(pago.getCondominio().getId());

			if(condominio.getLogoCondominio() != null) {
				InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
				try{
					BufferedImage image = ImageIO.read(is);
					map.put("IMAGEN", image);
				} catch (IOException ioe) {
					LOG.error("Error leyendo logo del Condominio, se colocar� el de MH");
					File initialFile = new File(contexto + "recursos" + File.separator + "img"
							+ File.separator + configurationServiceImpl.getLogo());
					try {
						is = new FileInputStream(initialFile);
						BufferedImage image = ImageIO.read(is);
						map.put("IMAGEN", image);
					}  catch (IOException ioedos) {
						LOG.error("No se encontr� el logo de MiHabitat");
					}
				}

			} else {
				LOG.warn("No se encontr� logo del Condominio, se colocar� el de MH");
				File initialFile = new File(contexto + "recursos" + File.separator + "img"
						+ File.separator + configurationServiceImpl.getLogo());
				try {
					InputStream is = new FileInputStream(initialFile);
					BufferedImage image = ImageIO.read(is);
					map.put("IMAGEN", image);
				}  catch (IOException ioedos) {
					LOG.error("No se encontr� el logo de MiHabitat");
				}
			}

			condominio.setLogoCondominio(null);
			pago.setCondominio(condominio);

			Collection collection = new ArrayList();
			collection.add(pago);
			jrDataSource = new JRBeanCollectionDataSource(collection);
			return JasperRunManager.runReportToPdf(
					directorio + "recibo.jasper", map, jrDataSource);
		} catch (JRException ex) {
			String message = "Error al generar el recibo de pago.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public void sendRecibo(Condominio condominio, String mensaje,
						   Long idPago, String... emails) {

		Pago pago = pagoService.get(idPago);

		final Map<String, String> emailsMap = new HashMap<>();
		for(String e : emails){
			emailsMap.put(e, pago.getContacto().getNombreCompleto());
		}

		if (!CollectionUtils.isEmpty(emailsMap)) {
			final Collection<Adjunto> adjuntos = new ArrayList<>();
			ByteArrayResource resource = new ByteArrayResource(
					this.getRecibo(PagoFactory.newInstance(idPago)));
			Adjunto adj = new Adjunto("recibo_" + pago.getFolio() + ".pdf", resource);
			adjuntos.add(adj);

			final Map mapVelocity = new HashMap();
			mapVelocity.put("mensaje", mensaje.replaceAll("(\r\n|\n)", "<br />"));
			mapVelocity.put("host", configurationServiceImpl.getHost());

			final String asunto = "Su Recibo de Pago Folio " + pago.getFolio();
			final String nombreCondominio = condominio.getNombre();

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailsMap, asunto,
							"pago-recibo.html", mapVelocity, adjuntos, nombreCondominio);
				}
			}).start();
		}
	}

	/*@Override
	public void sendRecibo(final Adjunto recibo, final String mensaje, final Condominio condominio,
			final String... emails) {

		if (emails != null && emails.length > 0) {
			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					@SuppressWarnings("rawtypes")
					Map map = new HashMap();
					map.put("mensaje", mensaje.replaceAll("(\r\n|\n)", "<br />"));
					map.put("host", configurationServiceImpl.getHost());
					Collection<Adjunto> adjuntos = new ArrayList<Adjunto>();
					adjuntos.add(recibo);
					emailingService.sendEmailBCC(emails, "Recibo de Pago",
							"pago-recibo.html", map, adjuntos, condominio.getNombre());
				}
			}).start();


			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailsMap, asunto,
							"aviso-cobro.html", mapVelocity, adjuntos, nombreCondominio);
				}
			}).start();
		}
	}*/
}
