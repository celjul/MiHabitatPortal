package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.model.CatalogoEstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.web.dto.reportes.PagoDetalle;
import com.bstmexico.mihabitat.web.dto.reportes.ReportePagos;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/pagos-departamento")
public class ReportePagosDepartamentoController {

	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory
			.getLogger(ReportePagosDepartamentoController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	@Qualifier("pagoserviceproxy")
	private PagoService pagoService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ServletContext context;

	@Autowired
	private ReportUtils reportUtils;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar() {
		return "administrador/reportes/pagos-departamento";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consultar")
	@ResponseBody
	public ReportePagos consultar(@RequestParam Short anio, HttpSession session) {
		return getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), anio);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(@RequestParam String formato,
			@RequestParam Short anio, HttpSession session) {

		ReportePagos reporte = getReporte(
				(Condominio) session.getAttribute(SessionEnum.CONDOMINIO
						.getValue()), anio);
		reporte.setCondominio((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "pagos-departamento." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private byte[] getBytes(ReportePagos reporte, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator
				+ "pagos-reporte" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		/*map.put("IMAGEN", contexto + "recursos" + File.separator + "img"
				+ File.separator + configurationServiceImpl.getLogo());*/
		map.put("FORMATO", formato);
		map.put("TITULO", "REPORTE DE PAGOS POR DEPARTAMENTO");

		Condominio condominio = condominioService.readConImagen(reporte.getCondominio().getId());

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

		Collection collection = new ArrayList();
		collection.add(reporte);
		jrDataSource = new JRBeanCollectionDataSource(collection);

		String sourceFile = formato.equals(reportUtils.PDF) ? directorio
				+ "pagos.jasper" : directorio
				+ "pagos-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ReportePagos getReporte(Condominio condominio, Short anio) {
		ReportePagos reporte = new ReportePagos();
		reporte.setAnio(anio);

		Map map = new HashMap();
		map.put("condominio", condominio);
		Collection<Pago> pagos = pagoService.getPagosByStatus(condominio,
				(CatalogoEstatusPago) CatalogoFactory.newInstance(
						CatalogoEstatusPago.class,
						configurationServiceImpl.getPagoAprobado()));
		if (!CollectionUtils.isEmpty(pagos)) {
			for (Pago pago : pagos) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(pago.getFecha());

				if (calendar.get(Calendar.YEAR) == anio && !pago.getMetodoPago().getId().equals(configurationServiceImpl.getSaldoFavor().getId())) {
					if (!CollectionUtils.isEmpty(pago.getPagosDepartamento())) {
						for (PagoDepartamento pd : pago
								.getPagosDepartamento()) {
							PagoDetalle detalle = new PagoDetalle();
							detalle.setId(pd
									.getDepartamento().getId());
							detalle.setNombre(pd.getDepartamento().getNombre());
							detalle.setTorresEtiquetas(pd.getDepartamento().getStringGrupos());
							switch (calendar.get(Calendar.MONTH)) {
								case 0:
									detalle.setEnero(pd.getMonto());

									break;
								case 1:
									detalle.setFebrero(pd.getMonto());
									break;
								case 2:
									detalle.setMarzo(pd.getMonto());
									break;
								case 3:
									detalle.setAbril(pd.getMonto());
									break;
								case 4:
									detalle.setMayo(pd.getMonto());
									break;
								case 5:
									detalle.setJunio(pd.getMonto());
									break;
								case 6:
									detalle.setJulio(pd.getMonto());
									break;
								case 7:
									detalle.setAgosto(pd.getMonto());
									break;
								case 8:
									detalle.setSeptiembre(pd.getMonto());
									break;
								case 9:
									detalle.setOctubre(pd.getMonto());
									break;
								case 10:
									detalle.setNoviembre(pd.getMonto());
									break;
								case 11:
									detalle.setDiciembre(pd.getMonto());
									break;
							}
							reporte.addPago(detalle);
							/*if (!CollectionUtils.isEmpty(pd.getMovimientos())) {
								for (MovimientoCargoAplicado mov : pd
										.getMovimientos()) {
									PagoDetalle detalle = new PagoDetalle();
									detalle.setId(mov.getMovimientoCargo().getCargo()
											.getDepartamento().getId());
									detalle.setNombre(mov.getMovimientoCargo()
											.getCargo().getDepartamento().getNombre());


								}
							}*/
						}
					}
				}
			}
		}
		return reporte;
	}
}
