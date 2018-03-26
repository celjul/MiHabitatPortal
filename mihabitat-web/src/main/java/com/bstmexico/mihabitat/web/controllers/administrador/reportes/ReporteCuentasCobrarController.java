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
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import com.bstmexico.mihabitat.cargos.model.Cargo;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.web.dto.reportes.Adeudo;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentasCobrar;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/reportes/cuentas-cobrar")
public class ReporteCuentasCobrarController {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReporteCuentasCobrarController.class);

	@Autowired
	private CondominioService condominioService;
	
	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;
	
	@Autowired
	private DepartamentoService departamentoService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private HibernateAwareObjectMapper mapper;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String iniciar(Model model, @RequestParam(required = false) String fin) {
		model.addAttribute("fin", mapper.writeValueAsString(fin));
		return "administrador/reportes/cuentas-cobrar";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "consulta")
	@ResponseBody
	public ReporteCuentasCobrar consultar(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			HttpSession session) {
		return getReporte((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
				DateUtils.getEndOfDay(fin));
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "imprimir")
	public ResponseEntity<byte[]> imprimir(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
			@RequestParam String formato, HttpSession session) {

		ReporteCuentasCobrar reporte = getReporte((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()),
				DateUtils.getEndOfDay(fin));
		reporte.setCondominio((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
		
		byte[] bytes = getBytes(reporte, formato);
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		String filename = "cuentas_cobrar." + formato;
		
		reportUtils.setHttpHeaders(headers, formato);
		headers.setContentDispositionFormData(filename, filename);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] getBytes(ReporteCuentasCobrar reporte, String formato) {
		JRDataSource jrDataSource = null;

		String contexto = context.getRealPath("/");
		String directorio = contexto + "jrxml" + File.separator + "cuentas-cobrar" + File.separator;

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
		
		String sourceFile = formato.equals(reportUtils.PDF) ? directorio + "cuentas-cobrar.jasper" : directorio + "cuentas-cobrar-sin-formato.jasper";
		return reportUtils.export(formato, sourceFile, map, jrDataSource);
	}
	
	private ReporteCuentasCobrar getReporte(Condominio condominio, Date fin) {
		
		ReporteCuentasCobrar reporte = new ReporteCuentasCobrar();
		reporte.setFin(fin);
		agregarAdeudos(reporte, condominio);
		return reporte;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void agregarAdeudos(ReporteCuentasCobrar reporte, Condominio condominio) {
		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Departamento> deptos = departamentoService.search(map);

		for(Departamento dpto : deptos){
			Adeudo adeudo = new Adeudo();
			adeudo.setIdDepartamento(dpto.getId());
			adeudo.setDepartamento(dpto.getNombre());
			adeudo.setTorresEtiquetas(dpto
					.getStringGrupos());
			adeudo.setSaldo(BigDecimal.ZERO);
			adeudo.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(dpto, null, reporte.getFin()));
			reporte.addAdeudo(adeudo);
		}
		
		Collection<Cargo> cargos = cargoService.search(map, CargoDepartamento.class, Boolean.FALSE);
		for (Cargo cargo : cargos) {
			if( (((CargoDepartamento) cargo).getFecha().before(reporte.getFin()) || ((CargoDepartamento) cargo).getFecha().equals(reporte.getFin()))
					&& ((CargoDepartamento) cargo).getSaldoPendiente(reporte.getFin()).compareTo(BigDecimal.ZERO) != 0) {
				Adeudo adeudo = new Adeudo();
				adeudo.setIdDepartamento(((CargoDepartamento) cargo).getDepartamento().getId());
				adeudo.setDepartamento(((CargoDepartamento) cargo).getDepartamento().getNombre());
				adeudo.setTorresEtiquetas(((CargoDepartamento) cargo).getDepartamento()
						.getStringGrupos());
				adeudo.setSaldo(((CargoDepartamento) cargo).getSaldoPendiente(reporte.getFin()));
				adeudo.addCargo((CargoDepartamento) cargo);
				reporte.addAdeudo(adeudo);
			}
			/*for (MovimientoCargo mov : ((CargoDepartamento) cargo).getMovimientos()) {
				BigDecimal total = BigDecimal.ZERO;
				if (mov.getFecha().compareTo(reporte.getFin()) < 0) {
					if (mov.getDebe() != null) {
						total = total.add(mov.getDebe());
					} else if (mov.getHaber() != null) {
						total = total.subtract(mov.getHaber());
					}
				}
				for (MovimientoCargoAplicado apl : mov.getAplicados()) {
					if (apl.getAplicado() && apl.getFecha().compareTo(reporte.getFin()) < 0) {
						if (apl.getHaber() != null) {
							total = total.subtract(apl.getHaber());
						} else if (apl.getDebe() != null) {
							total = total.add(apl.getDebe());
						}
					}
				}
				//if (total.compareTo(BigDecimal.ZERO) != 0) {
					Adeudo adeudo = new Adeudo();
					adeudo.setIdDepartamento(((CargoDepartamento) cargo).getDepartamento().getId());
					adeudo.setDepartamento(((CargoDepartamento) cargo).getDepartamento().getNombre());
					adeudo.setSaldo(total);
					adeudo.addCargo((CargoDepartamento) cargo);
					reporte.addAdeudo(adeudo);
				//}
			}*/
		}
		
		setContacto(reporte);
		setAntiguedad(reporte);
	}
	
	private void setContacto(ReporteCuentasCobrar reporte) {
		if (!CollectionUtils.isEmpty(reporte.getAdeudos())) {
			for (Adeudo adeudo : reporte.getAdeudos()) {
				Departamento departamento = departamentoService.get(adeudo.getIdDepartamento());
				if (!CollectionUtils.isEmpty(departamento.getContactos())) {
					for (ContactoDepartamento cd : departamento.getContactos()) {
						if (cd.getPrincipal()) {
							adeudo.setContacto(cd.getContacto().getNombre() + " " + cd.getContacto().getApellidoPaterno());
							break;
						}
					}
				}
			}
		}
	}
	
	private void setAntiguedad(ReporteCuentasCobrar reporte) {
		if (!CollectionUtils.isEmpty(reporte.getAdeudos())) {
			PrettyTime p = new PrettyTime(reporte.getFin(), new Locale("es"));
			for (Adeudo adeudo : reporte.getAdeudos()) {
				if(adeudo.getSaldo().compareTo(BigDecimal.ZERO) == 0){
					adeudo.setAntiguedad("Sin Adeudos");
				} else {
					String antiguedad = p.format(new Date(adeudo.getMilisegundos()));
					antiguedad = antiguedad.replace("hace", "");
					adeudo.setAntiguedad(antiguedad);
				}
			}
		}
	}
}
