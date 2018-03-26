package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import com.bstmexico.mihabitat.proveedores.model.DetalleFactura;
import com.bstmexico.mihabitat.proveedores.service.FacturaPorPagarService;
import com.bstmexico.mihabitat.web.dto.reportes.AdeudoProveedor;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentasPagar;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/cuentas-pagar")
public class ReporteCuentasPagarController {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReporteCuentasPagarController.class);

	@Autowired
	private CondominioService condominioService;
	
	@Autowired
	private FacturaPorPagarService facturaPorPagarService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model, @RequestParam(required = false) String fin) {
		model.addAttribute("fin", mapper.writeValueAsString(fin));
		return "administrador/reportes/cuentas-pagar";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteCuentasPagar consultar(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			HttpSession session) {
		return getReporte((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
				DateUtils.getEndOfDay(fin));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam String formato, HttpSession session) {

		ReporteCuentasPagar reporte = getReporte((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
				DateUtils.getEndOfDay(fin));
		reporte.setCondominio((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		
		byte[] bytes = getBytes(reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "cuentas_pagar." + formato;
		
		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] getBytes(ReporteCuentasPagar reporte, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator + "cuentas-pagar" + File.separator;

		Map map = new HashMap();
		map.put("SUBREPORT_DIR", directorio);
		//map.put("IMAGEN", contexto + "recursos" + File.separator + "img" + File.separator + configurationServiceImpl.getLogo());
		map.put("FORMATO", formato);

		Condominio condominio = condominioService.readConImagen(reporte.getCondominio().getId());

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

		Collection collection = new ArrayList();
		collection.add(reporte);
		jrDataSource = new JRBeanCollectionDataSource(collection);
		
		String sourceFile = formato.equals(reportUtils.PDF) ? directorio + "cuentas-pagar.jasper" : directorio + "cuentas-pagar-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}
	
	private ReporteCuentasPagar getReporte(Condominio condominio, Date fin) {
		
		ReporteCuentasPagar reporte = new ReporteCuentasPagar();
		reporte.setFin(fin);
		agregarAdeudos(reporte, condominio);
		return reporte;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void agregarAdeudos(ReporteCuentasPagar reporte, Condominio condominio) {
		Map map = new HashMap();
		map.put("condominio", condominio);
		
		Collection<Cfdi> facturas = facturaPorPagarService.search(map);
		for (Cfdi cfdi : facturas) {
			for (DetalleFactura detalle : cfdi.getConceptos()) {
				BigDecimal total = BigDecimal.ZERO;
				
				if (detalle.getMovimientoCfdi().getFecha().compareTo(reporte.getFin()) < 0) {
					if (detalle.getMovimientoCfdi().getHaber() != null) {
						total = total.add(detalle.getMovimientoCfdi().getHaber());
					}
					for (MovimientoCfdiAplicado apl : detalle.getMovimientoCfdi().getAplicados()) {
						if (apl.getAplicado() && apl.getFecha().compareTo(reporte.getFin()) < 0) {
							if (apl.getHaber() != null) {
								total = total.add(apl.getHaber());
							} else if (apl.getDebe() != null) {
								total = total.subtract(apl.getDebe());
							}
						}
					}
					if (total.compareTo(BigDecimal.ZERO) > 0) {
						AdeudoProveedor adeudo = new AdeudoProveedor();
						adeudo.setIdProveedor(cfdi.getProveedor().getId());
						adeudo.setProveedor(cfdi.getProveedor().getNombre());
						adeudo.setSaldo(total);
						adeudo.addCfdi(cfdi);
						reporte.addAdeudo(adeudo);
					}
				}
			}
		}
		setAntiguedad(reporte);
	}
	
	private void setAntiguedad(ReporteCuentasPagar reporte) {
		if (!CollectionUtils.isEmpty(reporte.getAdeudos())) {
			PrettyTime p = new PrettyTime(reporte.getFin(), new Locale("es"));
			for (AdeudoProveedor adeudo : reporte.getAdeudos()) {
				String antiguedad = p.format(new Date(adeudo.getMilisegundos()));
				antiguedad = antiguedad.replace("hace", "");
				adeudo.setAntiguedad(antiguedad);
			}
		}
	}
}
