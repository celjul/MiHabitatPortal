package com.bstmexico.mihabitat.jobs.impl;

import com.bstmexico.mihabitat.cargos.factory.CargoFactory;
import com.bstmexico.mihabitat.cargos.factory.DescuentoFactory;
import com.bstmexico.mihabitat.cargos.factory.RecargoFactory;
import com.bstmexico.mihabitat.cargos.model.*;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.instalaciones.model.CargoRecurrenteInstalacion;
import com.bstmexico.mihabitat.jobs.CargoJob;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service(value = "cargoJob")
public class CargoJobImpl implements CargoJob {

	private static final Logger LOG = LoggerFactory
			.getLogger(CargoJobImpl.class);

	@Autowired
	@Qualifier("javamailservice")
	private EmailingService emailingService;

	@Autowired
	@Qualifier("cargoserviceproxy")
	private CargoService cargoService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@Autowired
	private ConfigurationServiceImpl configurationService;

	@Autowired
	private NotificationHelperService notificationService;

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private MessageSendingOperations<String> messagingTemplate;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void jobHolaMundo() {

		LOG.warn("Hola Mundo :)");

		//this.messagingTemplate.convertAndSend("/topic/notificaciones", "q hubo!");

		/*EventBus eventBus = new EventBus();
		eventBus.register(applicationContext.getBean(NotificationsController.class));
		System.out.println("Post Simple EventBus Example");
		eventBus.post("");*/


	}

	@Override
	public void jobCargoRecurrente() {
		LOG.info("Ejecutando Batch de Cargos Programados...");
		Date hoy = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("MMMM '-' yyyy", new Locale("es"));
		LOG.info("Con Fecha... " + hoy);
		Calendar calHoy = GregorianCalendar.getInstance();
		Collection<CargoRecurrente> crs = cargoService.getCargosRecurrentesPorFecha(hoy);
		for (CargoRecurrente cr : crs) {
			LOG.warn("Creando cargo para el cargo recurrente: " + cr.getId().toString() + "-" + cr.getConcepto());
			try {
				calHoy.setTime(hoy);
				CargoAgrupador cargoAgrupador = CargoFactory.newInstance(CargoAgrupador.class);

				if (cr.getDescuento() != null && cr.getDescuento().getId() != null) {
					/*calHoy.set(GregorianCalendar.DAY_OF_MONTH, cr.getDescuento().getDia());*/
					calHoy.add(GregorianCalendar.DAY_OF_MONTH, cr.getDescuento().getDia());
					DescuentoDepartamento descuentoDepartamento = DescuentoFactory.newInstance(DescuentoDepartamento.class);
					descuentoDepartamento.setFecha(calHoy.getTime());
					descuentoDepartamento.setMonto(cr.getDescuento().getMonto());
					descuentoDepartamento.setPorcentaje(cr.getDescuento().getPorcentaje());
					cargoAgrupador.setDescuento(descuentoDepartamento);
				}

				calHoy.setTime(hoy);

				if (cr.getRecargo() != null && cr.getRecargo().getId() != null) {
					/*calHoy.set(GregorianCalendar.DAY_OF_MONTH, cr.getRecargo().getDia());*/
					calHoy.add(GregorianCalendar.DAY_OF_MONTH, cr.getRecargo().getDia());
					RecargoDepartamento recargoDepartamento = RecargoFactory.newInstance(RecargoDepartamento.class);
					recargoDepartamento.setFecha(calHoy.getTime());
					recargoDepartamento.setMonto(cr.getRecargo().getMonto());
					recargoDepartamento.setPorcentaje(cr.getRecargo().getPorcentaje());
					recargoDepartamento.setRedondear(cr.getRecargo().getRedondear());
					recargoDepartamento.setTipoInteres(cr.getRecargo().getTipoInteres());
					cargoAgrupador.setRecargo(recargoDepartamento);
				}

				cargoAgrupador.setFecha(hoy);

				cargoAgrupador.setActivo(true);
				cargoAgrupador.setCondominio(cr.getCondominio());
				cargoAgrupador.setCuenta(cr.getCuenta());
				cargoAgrupador.setTipo(cr.getTipo());

				cargoAgrupador.setConcepto(cr.getConcepto() + " " + formateador.format(hoy).toUpperCase());
				cargoAgrupador.setMantenimientoDepartamento(true);

				if (cr instanceof CargoRecurrenteMantenimiento) {
					if (((CargoRecurrenteMantenimiento) cr).getMantenimiento().getTipoCobroDepartamento().getId().equals(configurationService.getTipoCobroMantenimientoFijo())) {
						cargoAgrupador.setMonto(((CargoRecurrenteMantenimiento) cr).getMantenimiento().getMonto());
					} else if (((CargoRecurrenteMantenimiento) cr).getMantenimiento().getTipoCobroDepartamento().getId().equals(configurationService.getTipoCobroMantenimientoIndiviso())) {
						Collection<Departamento> departamentos = new ArrayList<>();
						Map map = new HashMap();
						departamentos.addAll(departamentoService.search(map));
						for (Departamento departamento : departamentos) {
							if (departamento.getUnidadIndiviso() != null) {
								cargoAgrupador.setMonto(((CargoRecurrenteMantenimiento) cr).getMantenimiento().getMonto().multiply(departamento.getUnidadIndiviso()));
							}
						}
					}
				} else if (cr instanceof CargoRecurrenteInstalacion) {
					//	TODO: Por ahora no hay nada especial que hacer en estos casos.
					cargoAgrupador.setMonto(cr.getMonto());
				} else {
					//	TODO: Por ahora no hay nada especial que hacer en estos casos.
					cargoAgrupador.setMonto(cr.getMonto());
				}


				Collection<CargoDepartamento> cargoDepartamentos = new ArrayList<>();
				Collection<Departamento> departamentos = new ArrayList<>();
				if (cr instanceof CargoRecurrenteMantenimiento) {
					Map map = new HashMap();
					map.put("condominio", cr.getCondominio());
					map.put("activo", true);
					map.put("mantenimiento", ((CargoRecurrenteMantenimiento) cr).getMantenimiento());
					departamentos.addAll(departamentoService.search(map));
				} else if (cr.getTodos()) {
					Map map = new HashMap();
					map.put("condominio", cr.getCondominio());
					map.put("activo", true);
					departamentos.addAll(departamentoService.search(map));
				} else {
					departamentos.addAll(cr.getDepartamentos());
				}

				if (!CollectionUtils.isEmpty(departamentos)) {
					for (Departamento depa : departamentos) {
						CargoDepartamento cargoDepartamento = CargoFactory.newInstance(CargoDepartamento.class);
						cargoDepartamento.setDepartamento(depa);
						if (cargoAgrupador.getDescuento() != null) {
							DescuentoDepartamento descuentoDepartamento = DescuentoFactory.newInstance(DescuentoDepartamento.class);
							descuentoDepartamento.setFecha(cargoAgrupador.getDescuento().getFecha());
							descuentoDepartamento.setMonto(cargoAgrupador.getDescuento().getMonto());
							descuentoDepartamento.setPorcentaje(cargoAgrupador.getDescuento().getPorcentaje());
							cargoDepartamento.setDescuento(descuentoDepartamento);
						}
						cargoDepartamento.setFecha(hoy);
						cargoDepartamento.setMantenimientoDepartamento(cargoAgrupador.getMantenimientoDepartamento());
						if (cargoAgrupador.getRecargo() != null) {
							RecargoDepartamento recargoDepartamento = RecargoFactory.newInstance(RecargoDepartamento.class);
							recargoDepartamento.setFecha(cargoAgrupador.getRecargo().getFecha());
							recargoDepartamento.setMonto(cargoAgrupador.getRecargo().getMonto());
							recargoDepartamento.setPorcentaje(cargoAgrupador.getRecargo().getPorcentaje());
							recargoDepartamento.setRedondear(cargoAgrupador.getRecargo().getRedondear());
							recargoDepartamento.setTipoInteres(cargoAgrupador.getRecargo().getTipoInteres());
							cargoDepartamento.setRecargo(recargoDepartamento);
						}
						cargoDepartamento.setActivo(true);
						cargoDepartamento.setConcepto(cargoAgrupador.getConcepto());
						cargoDepartamento.setCondominio(cargoAgrupador.getCondominio());
						cargoDepartamento.setCuenta(cargoAgrupador.getCuenta());
						cargoDepartamento.setTipo(cargoAgrupador.getTipo());

						MovimientoCargo movimientoCargo = MovimientoFactory.newInstance(MovimientoCargo.class);
						movimientoCargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, configurationService.getCargo()));
						/*movimientoCargo.setDebe(
								cargoAgrupador.getMantenimientoDepartamento() ? depa.getMantenimiento().getMonto() : cargoAgrupador.getMonto());*/
						movimientoCargo.setDebe(cargoAgrupador.getMonto());
						movimientoCargo.setFecha(cargoDepartamento.getFecha());
						movimientoCargo.setCancelado(false);

						Collection<MovimientoCargo> movimientoCargos = new ArrayDeque<>();
						movimientoCargos.add(movimientoCargo);
						cargoDepartamento.setMovimientos(movimientoCargos);

						cargoDepartamentos.add(cargoDepartamento);
					}
					cargoAgrupador.setCargos(cargoDepartamentos);
					cargoService.save(cargoAgrupador);
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
				final Map<String,String> emails = new HashMap<>();
				emails.put("Soporte MiHábitat", "soporte@mihabitat.com.mx");
				emails.put("Zoé Jonatan Tapia Hernández", "ztapia@bstmexico.com");
				final Map mapVelocity = new HashMap();
				mapVelocity.put("mensaje", e.getMessage() );
				final String asunto = "ERROR En la Ejecución del Job de Cargo Recurrente" + e.getMessage();

				if (!CollectionUtils.isEmpty(emails)) {
					/*new Thread(new Runnable() {
						@SuppressWarnings("unchecked")
						@Override
						public void run() {
							@SuppressWarnings("rawtypes")
							Collection<Adjunto> adjuntos = new ArrayList<>();
							emailingService.sendEmail(emails,
									"ERROR En la Ejecución del Job de Cargo Recurrente",
									"error-sistema.html", map, adjuntos, "");
						}
					}).start();*/

					new Thread(new Runnable() {
						@SuppressWarnings("unchecked")
						@Override
						public void run() {
							emailingService.sendEmail(emails, asunto,
									"error-sistema.html", mapVelocity, null, "");
						}
					}).start();
				}

			}
		}


	}

	@Override
	public void jobCargoDepartamento() {

		Calendar hoy = GregorianCalendar.getInstance();
		Collection<CargoDepartamento> cargosPorNotificar = cargoService.getCargosPorFecha(hoy.getTime());
		for(CargoDepartamento cargoPorNotificar : cargosPorNotificar) {
			//notificationService.enviarNotificacionNuevoCargo(cargoPorNotificar);
		}

	}

	@Override
	public void jobRecargo() {
		LOG.info("Ejecutando Batch de Recargos...");
		Calendar hoy = GregorianCalendar.getInstance();

		for(CargoDepartamento cargoDepartamento : cargoService.getCargosRecargosPorAplicar(hoy.getTime())){
			LOG.info("Para el cargo: " + cargoDepartamento.getConcepto() + " | id: " + cargoDepartamento.getId());
			if(cargoDepartamento.getRecargo() != null && cargoDepartamento.getRecargosCalculados().compareTo(BigDecimal.ZERO) > 0) {
				LOG.info("Si aplica recargos: " + cargoDepartamento.getRecargosCalculados());
				try {
					MovimientoCargo movimientoRecargo = MovimientoFactory.newInstance(MovimientoCargo.class);
					movimientoRecargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, configurationService.getRecargo()));
					movimientoRecargo.setFecha(hoy.getTime());
					if (cargoDepartamento.getRecargo().getRedondear()){
						movimientoRecargo.setDebe(cargoDepartamento.getRecargosCalculados().setScale(0, RoundingMode.HALF_UP));
					}
					else{
						movimientoRecargo.setDebe(cargoDepartamento.getRecargosCalculados());
					}
					movimientoRecargo.setCargo(cargoDepartamento);
					movimientoRecargo.setCancelado(false);
					//LOG.warn("REc: " + cargoDepartamento.getDescuentosCalculados());
					movimientoService.save(movimientoRecargo);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
					final Map<String,String> emails = new HashMap<>();
					emails.put("Soporte MiHábitat", "soporte@mihabitat.com.mx");
					emails.put("Zoé Jonatan Tapia Hernández", "ztapia@bstmexico.com");
					final Map mapVelocity = new HashMap();
					mapVelocity.put("mensaje", e.getMessage() );
					final String asunto = "ERROR En la Ejecución del Job de Recargo" + e.getMessage();

					if (!CollectionUtils.isEmpty(emails)) {
						/*new Thread(new Runnable() {
							@SuppressWarnings("unchecked")
							@Override
							public void run() {
								@SuppressWarnings("rawtypes")
								Collection<Adjunto> adjuntos = new ArrayList<>();
								emailingService.sendEmail(emails,
										"ERROR En la Ejecución del Job de Recargos",
										"error-sistema.html", map, adjuntos, "");
							}
						}).start();*/

						new Thread(new Runnable() {
							@SuppressWarnings("unchecked")
							@Override
							public void run() {
								emailingService.sendEmail(emails, asunto,
										"error-sistema.html", mapVelocity, null, "");
							}
						}).start();
					}

				}
			}
		}
	}
}
