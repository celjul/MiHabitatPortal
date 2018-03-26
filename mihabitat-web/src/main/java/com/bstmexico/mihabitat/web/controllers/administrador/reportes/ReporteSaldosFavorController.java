package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoSaldo;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
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
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteSaldoFavor;
import com.bstmexico.mihabitat.web.dto.reportes.Saldo;
import com.bstmexico.mihabitat.web.dto.reportes.SaldoDetalle;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/saldo-favor")
public class ReporteSaldosFavorController {

	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory
			.getLogger(ReporteSaldosFavorController.class);

	@Autowired
	private CondominioService condominioService;

	@Autowired
	@Qualifier("pagoserviceproxy")
	private PagoService pagoService;

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private ReportUtils reportUtils;

	@Autowired
	private ServletContext context;

	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model) {
		return "administrador/reportes/saldo-favor";
	}

	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteSaldoFavor consultar(HttpSession session) {
		return getReporte((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));
	}

	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(@RequestParam String formato,
			@RequestParam Boolean detalle, HttpSession session) {

		ReporteSaldoFavor reporte = getReporte((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		reporte.setCondominio((Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue()));

		byte[] bytes = getBytes(reporte, formato, detalle);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "saldo-favor." + formato;

		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private byte[] getBytes(ReporteSaldoFavor reporte, String formato,
			Boolean detalle) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator + "saldo-favor"
				+ File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		/*map.put("IMAGEN", contexto + "recursos" + File.separator + "img"
				+ File.separator + configurationServiceImpl.getLogo());*/
		map.put("FORMATO", formato);
		map.put("DETALLE", detalle);

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
				+ "saldo-favor.jasper" : directorio
				+ "saldo-favor-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ReporteSaldoFavor getReporte(Condominio condominio) {

		ReporteSaldoFavor reporte = new ReporteSaldoFavor();

		Map map = new HashMap();
		map.put("condominio", condominio);
		Collection<Departamento> departamentos = departamentoService.search(map);
		if (!CollectionUtils.isEmpty(departamentos)) {
			for (Departamento departamento : departamentos) {
				Saldo saldo = new Saldo();
				saldo.setDepartamento(departamento.getNombre());
				saldo.setTorresEtiquetas(departamento.getStringGrupos());
				saldo.setMonto(movimientoService.getSaldoFavorPorDepartamento(departamento));


				Collection<MovimientoSaldo> saldos = movimientoService.getSaldosFavor(departamento);
				if (!CollectionUtils.isEmpty(saldos)) {
					for (MovimientoSaldo movimientoSaldo : saldos) {
						EstatusPago ultimoEstatus = null;
						if(movimientoSaldo.getPago() != null) {
							for(EstatusPago estatusPago : movimientoSaldo.getPago().getEstatus()){
								ultimoEstatus = estatusPago;
							}
						}
						if(movimientoSaldo.getPago() == null || (ultimoEstatus != null &&
								!ultimoEstatus.getEstatus().getId().equals(configurationServiceImpl.getPagoCancelado()))) {
							/*if(movimientoSaldo.getDebe() != null && movimientoSaldo.getDebe().compareTo(BigDecimal.ZERO) > 0) {*/
							if((movimientoSaldo.getTipo() != null && movimientoSaldo.getTipo().getId().equals(configurationServiceImpl.getAplicacionDeSaldoAFavor()))
									|| (movimientoSaldo.getDebe() != null && movimientoSaldo.getDebe().compareTo(BigDecimal.ZERO) > 0)) {
								SaldoDetalle detalle = new SaldoDetalle();
								detalle.setFecha(movimientoSaldo.getFecha());
								detalle.setId(movimientoSaldo.getId());
								detalle.setMonto(movimientoSaldo.getDebe() != null ? movimientoSaldo.getDebe().negate() : BigDecimal.ZERO);
								StringBuffer sb = new StringBuffer();
								if(movimientoSaldo.getPagoDepartamento() != null) {
									sb.append(" -> ");
									for (MovimientoCargoAplicado mca : movimientoSaldo.getPagoDepartamento().getMovimientos()) {
										if (mca.getMovimientoCargo() != null && mca.getMovimientoCargo().getCargo() != null) {
											sb.append("\"" + mca.getMovimientoCargo().getCargo().getConcepto() + "\", ");
										}
									}
									/*for (PagoDepartamento pd : movimientoSaldo.getPago().getPagosDepartamento()) {
										for (MovimientoCargoAplicado mca : pd.getMovimientos()) {
											if (mca.getMovimientoCargo() != null && mca.getMovimientoCargo().getCargo() != null) {
												sb.append("\"" + mca.getMovimientoCargo().getCargo().getConcepto() + "\", ");
											}
										}
									}*/
									sb.delete(sb.length() -2, sb.length());
								}
								detalle.setDescripcion((movimientoSaldo.getTipo() != null ? movimientoSaldo.getTipo().getDescripcion() : "Saldo a favor utilizado " )
										+ " " +sb.toString());
								saldo.addGenerado(detalle);
							} else if((movimientoSaldo.getTipo() != null && movimientoSaldo.getTipo().getId().equals(configurationServiceImpl.getSaldoAFavorGenerado()))
									|| (movimientoSaldo.getHaber() != null && movimientoSaldo.getHaber().compareTo(BigDecimal.ZERO) > 0)) {
								SaldoDetalle detalle = new SaldoDetalle();
								detalle.setFecha(movimientoSaldo.getFecha());
								detalle.setId(movimientoSaldo.getId());
								detalle.setMonto(movimientoSaldo.getHaber());

								String comentarios = "";
								if(movimientoSaldo.getPago() != null && movimientoSaldo.getPago().getEstatus().iterator().hasNext()) {
									comentarios = movimientoSaldo.getPago().getEstatus().iterator().next().getComentario();
								}

								detalle.setDescripcion((movimientoSaldo.getTipo() != null ? movimientoSaldo.getTipo().getDescripcion() : "Saldo a favor generado ") + " " +
										((movimientoSaldo.getPago() != null && movimientoSaldo.getPago().getReferencia() != null) ?
												(" <- " + movimientoSaldo.getPago().getReferencia()) : "") +
										(comentarios != null ?
												(" <- " + comentarios) : ""));
								saldo.addGenerado(detalle);
							}

						}
					}
				}

				/*Collection<Pago> pagos = pagoService.search(map);
				if (!CollectionUtils.isEmpty(pagos)) {
					for (Pago pago : pagos) {
						BigDecimal montoGastado = BigDecimal.ZERO;
						if (!CollectionUtils.isEmpty(pago.getMovimientos())) {
							for (MovimientoCargoAplicado mov : pago
									.getMovimientos()) {
								if (mov.getHaber() != null) {
									montoGastado = montoGastado.add(mov
											.getHaber());
								}
							}
						}
						if (pago.getMetodoPago().equals(
								configurationServiceImpl.getSaldoFavor())) {
							SaldoDetalle detalle = new SaldoDetalle();
							detalle.setFecha(pago.getFecha());
							detalle.setId(pago.getId());
							detalle.setMonto(montoGastado.negate());
							detalle.setDescripcion("Saldo a favor utilizado");
							saldo.addGastado(detalle);
						} else {
							if (pago.getMonto().compareTo(montoGastado) > 0) {
								SaldoDetalle detalle = new SaldoDetalle();
								detalle.setFecha(pago.getFecha());
								detalle.setId(pago.getId());
								detalle.setMonto(pago.getMonto().subtract(
										montoGastado));
								detalle.setDescripcion("Saldo a favor generado");
								saldo.addGenerado(detalle);
							}
						}
					}
				}*/
				reporte.addSaldo(saldo);
			}
		}
		return reporte;
	}
}
