package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.factory.ContactoFactory;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.dto.EstadoCuenta;
import com.bstmexico.mihabitat.web.service.EstadoCuentaService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
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
import java.util.*;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class EstadoCuentaServiceImpl implements EstadoCuentaService {

	private static final Logger LOG = LoggerFactory
			.getLogger(EstadoCuentaServiceImpl.class);

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@Autowired
	@Qualifier("javamailservice")
	private EmailingService emailingService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	private ServletContext context;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Override
	public EstadoCuenta getEstadoCuenta(Condominio condominio,Departamento departamento, Date inicio, Date fin, Contacto contacto) {
		
		EstadoCuenta estado = new EstadoCuenta();
		
		estado.setMovimientos(movimientoService.getMovimientos(departamento,inicio, fin));
		
		departamento = departamentoService.get(departamento.getId());
		
		if (contacto != null && contacto.getId() != null) {
			estado.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(departamento, null, fin));
			estado.setSaldoFavorAlDia(movimientoService.getSaldoFavorPorDepartamento(departamento));
		}else{
			contacto = departamento.obtenerPrincipal().getContacto();
			estado.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(departamento, null, fin));
			estado.setSaldoFavorAlDia(movimientoService.getSaldoFavorPorDepartamento(departamento));
		}
		estado.setDepartamento(departamento);
		estado.setContacto(contacto);
		estado.getDepartamento().setCondominio(condominio);
		estado.setInicio(inicio);
		estado.setFin(fin);

		Map map = new HashMap();
		map.put("departamento", departamento);
		estado.setCargos(cargoService.search(map, CargoDepartamento.class, false));

		EstadoCuenta anterior = new EstadoCuenta();
		
		anterior.setMovimientos(movimientoService.getMovimientos(departamento, null, inicio));
		anterior.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(departamento, null, inicio));
		estado.setSaldoAnterior(anterior.getSaldoDeudor());
		estado.setSaldoFavorAnterior(anterior.getSaldoFavor());
		return estado;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public byte[] getEstadoCuenta(EstadoCuenta estado) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "estado-cuenta" + File.separator;

		try {
			Map map = new HashMap();
			map.put("SUBREPORT_DIR", directorio);

			Condominio condominio = condominioService.readConImagen(estado.getDepartamento().getCondominio().getId());

			if(condominio.getLogoCondominio() != null) {
				InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
				try{
					BufferedImage image = ImageIO.read(is);
					map.put("IMAGEN", image);
				} catch (IOException ioe) {
					LOG.error("Error leyendo logo del Condominio, se colocar? el de MH");
					File initialFile = new File(contexto + "recursos" + File.separator + "img"
							+ File.separator + configurationServiceImpl.getLogo());
					try {
						is = new FileInputStream(initialFile);
						BufferedImage image = ImageIO.read(is);
						map.put("IMAGEN", image);
					}  catch (IOException ioedos) {
						LOG.error("No se encontr? el logo de MiHabitat");
					}
				}

			} else {
				LOG.warn("No se encontr? logo del Condominio, se colocar? el de MH");
				File initialFile = new File(contexto + "recursos" + File.separator + "img"
						+ File.separator + configurationServiceImpl.getLogo());
				try {
					InputStream is = new FileInputStream(initialFile);
					BufferedImage image = ImageIO.read(is);
					map.put("IMAGEN", image);
				}  catch (IOException ioedos) {
					LOG.error("No se encontr? el logo de MiHabitat");
				}
			}

			condominio.setLogoCondominio(null);
			estado.getDepartamento().setCondominio(condominio);

			Collection collection = new ArrayList();
			collection.add(estado);
			jrDataSource = new JRBeanCollectionDataSource(collection);
			return JasperRunManager.runReportToPdf(
					directorio + "estado.jasper", map, jrDataSource);
		} catch (JRException ex) {
			String message = "Error al generar el estado de cuenta.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public byte[] getEstadoDeCuentaMultiple(Collection<EstadoCuenta> estadosCuenta, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "estado-cuenta" + File.separator;

		byte[] bites = null;

		try {
			Map map = new HashMap();
			map.put("FORMATO", formato);
			map.put("SUBREPORT_DIR", directorio);
			String sourceFile = null;
			ByteArrayOutputStream streamXlsx = null;

			if(!CollectionUtils.isEmpty(estadosCuenta)) {
				Condominio condominio = condominioService.readConImagen(estadosCuenta.iterator().next().getDepartamento().
						getCondominio().getId());
				if(condominio.getLogoCondominio() != null) {
					InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
					try{
						BufferedImage image = ImageIO.read(is);
						map.put("IMAGEN", image);
					} catch (IOException ioe) {
						LOG.error("Error leyendo logo del Condominio, se colocar? el de MH");
						File initialFile = new File(contexto + "recursos" + File.separator + "img"
								+ File.separator + configurationServiceImpl.getLogo());
						try {
							is = new FileInputStream(initialFile);
							BufferedImage image = ImageIO.read(is);
							map.put("IMAGEN", image);
						}  catch (IOException ioedos) {
							LOG.error("No se encontr? el logo de MiHabitat");
						}
					}

				} else {
					LOG.warn("No se encontr? logo del Condominio, se colocar? el de MH");
					File initialFile = new File(contexto + "recursos" + File.separator + "img"
							+ File.separator + configurationServiceImpl.getLogo());
					try {
						InputStream is = new FileInputStream(initialFile);
						BufferedImage image = ImageIO.read(is);
						map.put("IMAGEN", image);
					}  catch (IOException ioedos) {
						LOG.error("No se encontr? el logo de MiHabitat");
					}
				}

				condominio.setLogoCondominio(null);
				sourceFile = directorio + "estado.jasper";

				List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
				for(EstadoCuenta estadoCuenta : estadosCuenta){

					estadoCuenta.getDepartamento().setCondominio(condominio);

					Collection collection = new ArrayList();
					collection.add(estadoCuenta);
					jrDataSource = new JRBeanCollectionDataSource(collection);
					JasperPrint jasperPrint1 = JasperFillManager.fillReport(sourceFile, map, jrDataSource);
					jasperPrintList.add(jasperPrint1);
				}

				if (formato.equals("pdf")) {
					JRPdfExporter exporter = new JRPdfExporter();
					exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList)); //Set as export input my list with JasperPrint s

					ByteArrayOutputStream b = new ByteArrayOutputStream();
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(b)); //or any other out streaam
					exporter.exportReport();
					bites = b.toByteArray();
				} else if (formato.equals("xlsx")){
					JRXlsxExporter exporterXlsx = new JRXlsxExporter();
					exporterXlsx.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));

					SimpleXlsxReportConfiguration configurationXlsx = new SimpleXlsxReportConfiguration();
					configurationXlsx.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
					configurationXlsx.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
					configurationXlsx.setDetectCellType(Boolean.TRUE);
					exporterXlsx.setConfiguration(configurationXlsx);

					streamXlsx = new ByteArrayOutputStream();
					SimpleOutputStreamExporterOutput outputXlsx = new SimpleOutputStreamExporterOutput(streamXlsx);
					exporterXlsx.setExporterOutput(outputXlsx);

					exporterXlsx.exportReport();
					outputXlsx.close();
				}
			}
			return formato.equals("pdf") ? bites: streamXlsx.toByteArray();
		} catch (JRException ex) {
			String message = "Error al generar el estado de cuenta.";
			LOG.error(message, ex);
			throw new ServiceException(message, ex);
		}
	}

	@Override
	public void sendEstadoCuenta(Condominio condominio, String mensaje, Long idDepartamento, Long idContacto, Date inicio, Date fin,
								 String... emails)  {

		/*if (emails != null && emails.length > 0) {
			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					@SuppressWarnings("rawtypes")
					Map map = new HashMap();
					map.put("mensaje", mensaje.replaceAll("(\r\n|\n)", "<br />"));
					map.put("host", configurationServiceImpl.getHost());
					Collection<Adjunto> adjuntos = new ArrayList<Adjunto>();
					adjuntos.add(estado);
					emailingService.sendEmailBCC(emails, asunto,
							"estado-cuenta.html", map, adjuntos, condominio.getNombre());
				}
			}).start();
		}*/

		Departamento dpto = departamentoService.get(idDepartamento);

		final Map<String, String> emailsMap = new HashMap<>();
		for(String e : emails){
			emailsMap.put(e, dpto.getNombre());
		}

		if (!CollectionUtils.isEmpty(emailsMap)) {
			final Collection<Adjunto> adjuntos = new ArrayList<>();
			EstadoCuenta estado = this.getEstado(condominio, idDepartamento,
					inicio, fin, idContacto);
			Adjunto adj = new Adjunto("Estado_de_Cuenta_" + dpto.getNombre() + ".pdf", new ByteArrayResource(
					this.getEstadoCuenta(estado)));
			adjuntos.add(adj);

			final Map mapVelocity = new HashMap();
			mapVelocity.put("mensaje", mensaje.replaceAll("(\r\n|\n)", "<br />"));
			mapVelocity.put("host", configurationServiceImpl.getHost());

			final String asunto = "Estado de Cuenta " + dpto.getNombre();
			final String nombreCondominio = condominio.getNombre();

			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				@Override
				public void run() {
					emailingService.sendEmail(emailsMap, asunto,
							"estado-cuenta.html", mapVelocity, adjuntos, nombreCondominio);
				}
			}).start();
		}
	}

	@Override
	public void sendEstadoDeCuentaMultiple(Condominio condominio, String mensaje, Date inicio, Date fin, Long... ids) {

		for (Long idDepartamento : ids) {
			Departamento departamento = departamentoService.get(idDepartamento);
			Contacto contacto = contactoService.get(departamento.obtenerPrincipal().getContacto().getId());

			final Map<String, String> emailsMap = new HashMap<>();
			if (contacto != null && !CollectionUtils.isEmpty(contacto
					.getEmails())) {
				for (Email email : contacto.getEmails()) {
					emailsMap.put(email.getDireccion(), contacto.getNombreCompleto());
				}
			}
			if(!CollectionUtils.isEmpty(emailsMap)){
				final Collection<Adjunto> adjuntos = new ArrayList<>();

				EstadoCuenta estadoCuenta = this.getEstado(condominio, idDepartamento, inicio,
						fin, contacto.getId());

				Adjunto adj = new Adjunto("Estado_de_Cuenta_" + departamento.getNombre() + ".pdf", new ByteArrayResource(
						this.getEstadoCuenta(estadoCuenta)));
				adjuntos.add(adj);

				final Map mapVelocity = new HashMap();
				mapVelocity.put("mensaje", mensaje.replaceAll("(\r\n|\n)", "<br />"));
				mapVelocity.put("host", configurationServiceImpl.getHost());

				final String asunto = "Estado de Cuenta " + departamento.getNombre();
				final String nombreCondominio = condominio.getNombre();

				new Thread(new Runnable() {
					@SuppressWarnings("unchecked")
					@Override
					public void run() {
						emailingService.sendEmail(emailsMap, asunto,
								"estado-cuenta.html", mapVelocity, adjuntos, nombreCondominio);
					}
				}).start();
			}
		}
	}

	public EstadoCuenta getEstado(Condominio condominio, Long idDepartamento,
								   Date inicio, Date fin, Long idContacto) {
		Contacto contacto = null;
		if(idContacto != null) {
			contacto = contactoService.get(idContacto);
		} else {
			contacto = ContactoFactory.newInstance();
		}
		return this.getEstadoCuenta(condominio,
				DepartamentoFactory.newInstance(idDepartamento), inicio, fin,
				contacto);
	}

	private Date getFinal(Date inicio) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inicio);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}
}
