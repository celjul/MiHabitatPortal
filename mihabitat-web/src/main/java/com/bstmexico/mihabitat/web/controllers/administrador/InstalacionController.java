package com.bstmexico.mihabitat.web.controllers.administrador;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.cargos.factory.CargoFactory;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.model.CatalogoCargo;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.instalaciones.factory.DisponibilidadFactory;
import com.bstmexico.mihabitat.instalaciones.model.*;
import com.bstmexico.mihabitat.instalaciones.service.ReservacionService;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bstmexico.mihabitat.comunes.archivos.factory.ArchivoFactory;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.instalaciones.service.InstalacionService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;

@Controller
@RequestMapping(value = "administrador/instalaciones")
public class InstalacionController {

	@Autowired
	private HibernateAwareObjectMapper mapper;
	
	@Autowired
	private InstalacionService instalacionService;
	
	@Autowired
	private CuentaService cuentaService;
	
	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private ContactoService contactoService;

	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	private ConfigurationServiceImpl configurationService;

	@Autowired
	@Qualifier("reservacionserviceproxy")
	private ReservacionService reservacionService;


	
	public static final String IMAGEN = "imageninstalacion";
	
	private static final Logger LOG = LoggerFactory
			 .getLogger(InstalacionController.class);
	
	@RequestMapping(method = RequestMethod.GET, value = "nuevo")
	public String nuevo(Model model, HttpSession session) {

		session.removeAttribute(IMAGEN);
		model.addAttribute("condominio",(Condominio) session.getAttribute("condominio"));
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationService.getCuentaIngresos())));
		model.addAttribute("unidades",
				mapper.writeValueAsString(catalogoService.getList(CatalogoUnidadInstalacion.class)));
		//model.addAttribute("dias",
		//		mapper.writeValueAsString(catalogoService.getList(CatalogoDiaSemana.class)));
		/*Collection<Disponibilidad> disponibilidades = new ArrayList<Disponibilidad>();
		for (int i=0 ; i<= GregorianCalendar.SATURDAY ; i++) {
			Disponibilidad disponibilidad = DisponibilidadFactory.newInstance();
			disponibilidad.setDia(i);
			disponibilidad.setActivo(true);
			disponibilidad.setHoraInicio(LocalTime.parse("00:00"));
			disponibilidad.setHoraInicio(LocalTime.parse("24:00"));
			disponibilidades.add(disponibilidad);
		}*/

		return "administrador/instalaciones/nuevo";
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "guardar")
	public @ResponseBody Instalacion guardar(@RequestBody Instalacion instalacion,
			HttpSession session) {
		
		ArchivoImagenInstalacion imagen = (ArchivoImagenInstalacion) session.getAttribute(IMAGEN);
		instalacion.setImagen(imagen);
		instalacionService.save(instalacion);
		session.removeAttribute(IMAGEN);
		return instalacion;
	}

	@RequestMapping(method = RequestMethod.POST, value = "subirimagen")
	public @ResponseBody ArchivoImagenInstalacion processUpload(
			@RequestParam MultipartFile file, HttpServletRequest request,
			HttpSession session) {

		session.removeAttribute(IMAGEN);

		ArchivoImagenInstalacion imagen = ArchivoFactory.newInstance(ArchivoImagenInstalacion.class);
		imagen.setNombre(file.getOriginalFilename());
		imagen.setTamano(file.getSize() / 1024 + " Kb");
		imagen.setTipo(file.getContentType());

		try {
			imagen.setBytes(file.getBytes());
		} catch (IOException ex) {
			String message = "Error al intentar subir el archivo al servidor.";
			LOG.warn(message, ex);
			throw new ServiceException(message, ex);
		}
		session.setAttribute(IMAGEN, imagen);

		return imagen;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "eliminarimagen")
	public @ResponseBody ArchivoImagenInstalacion processDelete(
			HttpServletRequest request,
			HttpSession session) {
		
		session.removeAttribute(IMAGEN);
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "lista")
	public String lista(Model model, HttpSession session) {
		//Map map = new HashMap();
		//map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("instalaciones",
				mapper.writeValueAsString(instalacionService.getList((Condominio) session.getAttribute("condominio"))));
		return "administrador/instalaciones/lista";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "actualizar/{instalacion}")
	public String editar(@PathVariable Long instalacion, Model model, HttpSession session) {
		
		Instalacion instalacionObject = instalacionService
				.get(instalacion); 
		model.addAttribute("instalacion", mapper
				.writeValueAsString(instalacionObject));
		model.addAttribute("condominio",(Condominio) session.getAttribute("condominio"));
		model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
				.getCuentas((Condominio) session.getAttribute("condominio"),
						configurationService.getCuentaIngresos())));
		model.addAttribute("unidades",
				mapper.writeValueAsString(catalogoService.getList(CatalogoUnidadInstalacion.class)));
		
		session.setAttribute(IMAGEN, instalacionObject.getImagen());
		
		return "administrador/instalaciones/editar";
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "actualizar")
	public @ResponseBody Instalacion actualizar(
			@RequestBody Instalacion instalacion,
			HttpSession session) {
		
		ArchivoImagenInstalacion imagen = (ArchivoImagenInstalacion) session.getAttribute(IMAGEN);
		instalacion.setImagen(imagen);
		instalacion.setCondominio((Condominio) session
				.getAttribute("condominio"));
		instalacionService.update(instalacion);
		instalacion.getReservaciones().clear();
		return instalacion;
	}



	@RequestMapping(method = RequestMethod.GET, value = "reservar/{instalacion}")
	public String reservar(@PathVariable Long instalacion, Model model, HttpSession session) {

		Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		model.addAttribute("contactos",
				mapper.writeValueAsString(contactoService.search(map)));
		model.addAttribute("estatus",
				mapper.writeValueAsString(catalogoService.getList(CatalogoEstatusReservacion.class)));
		Instalacion instalacionObject = instalacionService
				.getConReservaciones(instalacion);
		model.addAttribute("instalacion", mapper
				.writeValueAsString(instalacionObject));
		model.addAttribute("condominio", (Condominio) session.getAttribute("condominio"));

		session.setAttribute("instalacion", instalacionObject.getId());

		return "administrador/instalaciones/reservaciones/nuevo";
	}

	@RequestMapping(method = RequestMethod.POST, value = "reservar/guardar")
	public @ResponseBody Collection<Reservacion> reservarGuardar(@RequestBody Reservacion reservacion, HttpSession session) {

		String instalacionId = session.getAttribute("instalacion").toString();

		reservacion.setInstalacion(instalacionService.get(reservacion.getInstalacion().getId()));

		if (reservacion.getId() == null){
			if(reservacion.getInstalacion().getCobroAutomatico() && reservacion.getDepartamento() != null &&
					reservacion.getDepartamento().getId() != null &&
					reservacion.getEstatusReservacion().getId() == configurationService.getReservacionAprobada()) {
				CargoDepartamento cargoDepartamento= CargoFactory.newInstance(CargoDepartamento.class);
				cargoDepartamento.setDepartamento(reservacion.getDepartamento());
				cargoDepartamento.setFecha(new Date());
				cargoDepartamento.setActivo(true);
				cargoDepartamento.setConcepto("Uso de Instalación: " + reservacion.getInstalacion().getNombre() +
						" " + reservacion.getFechaInicio().getYear() + "-" + reservacion.getFechaInicio().getMonthOfYear() + "-" + reservacion.getFechaInicio().getDayOfMonth());
				cargoDepartamento.setCondominio((Condominio) session.getAttribute("condominio"));
				cargoDepartamento.setCuenta(reservacion.getInstalacion().getCuenta());
				cargoDepartamento.setMantenimientoDepartamento(false);
				cargoDepartamento.setTipo((CatalogoCargo) CatalogoFactory.newInstance(CatalogoCargo.class, configurationService.getCargoInstalacion()));
				MovimientoCargo movimientoCargo = MovimientoFactory.newInstance(MovimientoCargo.class);
				movimientoCargo.setCancelado(false);
				movimientoCargo.setFecha(cargoDepartamento.getFecha());
				movimientoCargo.setCargo(cargoDepartamento);
				movimientoCargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, configurationService.getCargo()));
				movimientoCargo.setDebe(calcularCargoReservacion(reservacion));
				cargoDepartamento.getMovimientos().add(movimientoCargo);
				cargoService.save(cargoDepartamento);
				reservacion.setCargo(cargoDepartamento);
			}
			reservacionService.save(reservacion);
		}
		else {
			Reservacion resTemp = reservacionService.get(reservacion.getId());

			if(reservacion.getInstalacion().getCobroAutomatico() && reservacion.getDepartamento() != null &&
					reservacion.getDepartamento().getId() != null &&
					reservacion.getEstatusReservacion().getId() == configurationService.getReservacionAprobada() &&
					resTemp.getCargo() == null) {
				CargoDepartamento cargoDepartamento= CargoFactory.newInstance(CargoDepartamento.class);
				cargoDepartamento.setDepartamento(reservacion.getDepartamento());
				cargoDepartamento.setFecha(new Date());
				cargoDepartamento.setActivo(true);
				cargoDepartamento.setConcepto("Uso de Instalación: " + reservacion.getInstalacion().getNombre() +
						" " + reservacion.getFechaInicio().getYear() + "-" + reservacion.getFechaInicio().getMonthOfYear() + "-" + reservacion.getFechaInicio().getDayOfMonth());
				cargoDepartamento.setCondominio((Condominio) session.getAttribute("condominio"));
				cargoDepartamento.setCuenta(reservacion.getInstalacion().getCuenta());
				cargoDepartamento.setMantenimientoDepartamento(false);
				cargoDepartamento.setTipo((CatalogoCargo) CatalogoFactory.newInstance(CatalogoCargo.class, configurationService.getCargoInstalacion()));
				MovimientoCargo movimientoCargo = MovimientoFactory.newInstance(MovimientoCargo.class);
				movimientoCargo.setCancelado(false);
				movimientoCargo.setFecha(cargoDepartamento.getFecha());
				movimientoCargo.setCargo(cargoDepartamento);
				movimientoCargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, configurationService.getCargo()));
				movimientoCargo.setDebe(calcularCargoReservacion(reservacion));
				cargoDepartamento.getMovimientos().add(movimientoCargo);
				cargoService.save(cargoDepartamento);
				reservacion.setCargo(cargoDepartamento);
			}
			reservacionService.update(reservacion);
		}

		Collection<Reservacion> res = instalacionService.getConReservaciones(Long.parseLong(instalacionId)).getReservaciones();
		return res;
	}

	private BigDecimal calcularCargoReservacion(Reservacion reservacion) {
		BigDecimal cargo = BigDecimal.ZERO;
		Instalacion instalacion = reservacion.getInstalacion();
		if(instalacion.getUnidad().getId() == configurationService.getUnidadDia()) {
			BigDecimal dias = BigDecimal.valueOf((reservacion.getFechaFin().toDate().getTime() -
					reservacion.getFechaInicio().toDate().getTime())/(1000*60*60*24))
					.setScale(1,RoundingMode.HALF_UP);
			cargo = instalacion.getCosto().multiply(dias);
		}
		else  {
			BigDecimal horas = BigDecimal.valueOf((reservacion.getFechaFin().toDate().getTime() -
					reservacion.getFechaInicio().toDate().getTime())/(1000*60*60))
					.setScale(2,RoundingMode.HALF_UP);
			cargo = instalacion.getCosto().multiply(horas);
		}
		return cargo;
	}

	@RequestMapping(method = RequestMethod.POST, value = "reservar/eliminar")
	public @ResponseBody Collection<Reservacion> reservarEliminar(@RequestBody Reservacion reservacion, HttpSession session) {

		String instalacionId = session.getAttribute("instalacion").toString();

		if (reservacion.getId() != null){
			reservacionService.delete(reservacion);
		}

		Collection<Reservacion> res = instalacionService.getConReservaciones(Long.parseLong(instalacionId)).getReservaciones();
		return res;
	}
}
