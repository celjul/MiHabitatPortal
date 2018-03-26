package com.bstmexico.mihabitat.proveedores.service.impl;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoPagoProveedor;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.proveedores.dao.PagoProveedorDao;
import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import com.bstmexico.mihabitat.proveedores.model.DetalleFactura;
import com.bstmexico.mihabitat.proveedores.model.PagoProveedor;
import com.bstmexico.mihabitat.proveedores.service.FacturaPorPagarService;
import com.bstmexico.mihabitat.proveedores.service.PagoProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Service
public class PagoProveedorServiceImpl implements PagoProveedorService {

	private static final Logger LOG = LoggerFactory
			.getLogger(PagoProveedorServiceImpl.class);

	@Autowired
	private PagoProveedorDao pagoProveedorDao;

	@Autowired
	private FacturaPorPagarService facturaPorPagarService;
	
	@Autowired
	@Qualifier("movimientoserviceproxy")
	private MovimientoService movimientoService;

	@Autowired
	private Validator validator;

	private static Properties cfg;
	
	public PagoProveedorServiceImpl() {
		try {
			cfg = PropertiesLoaderUtils.loadAllProperties("configuration.properties");
		} catch (IOException e) {
			LOG.error("Error al cargar el archivo de propiedades");
		}
	 }

	@Transactional
	@Override
	public void save(PagoProveedor pago) {
		try {
			Assert.notNull(pago, "SEREX001");
			Set<ConstraintViolation<PagoProveedor>> violations = validator
					.validate(pago);

			if (violations.isEmpty()) {
				generarMovimientos(pago);
				pagoProveedorDao.save(pago);
				createMovimientoPago(pago);
			} else {
				String message = "SEREX002";
				ApplicationException ex1 = new ServiceException(message,
						violations);
				LOG.warn(ex1.getMessage() + violations);
				throw ex1;
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	private void generarMovimientos(PagoProveedor pago) {
		Collection<MovimientoCfdiAplicado> temporales = pago.getMovimientos();
		pago.setMovimientos(null);
		
		if (!CollectionUtils.isEmpty(temporales)) {
			for (MovimientoCfdiAplicado temporal : temporales) {
				
				Cfdi cfdi = facturaPorPagarService.get(temporal.getId());//Se guarda en el id
				BigDecimal cantidadPagada = temporal.getDebe();
				
				if (cantidadPagada.compareTo(BigDecimal.ZERO) > 0) {
					////////////////SE PAGAN LOS CONCEPTOS/////////////////////
					if (!CollectionUtils.isEmpty(cfdi.getConceptos())) {
						for (DetalleFactura concepto : cfdi.getConceptos()) {
							if (concepto.getMovimientoCfdi().getPendiente().compareTo(BigDecimal.ZERO) > 0) {
								BigDecimal montoPago = null;
								
								if (cantidadPagada.compareTo(concepto.getMovimientoCfdi().getPendiente()) >= 0) {
									montoPago = concepto.getMovimientoCfdi().getPendiente();
								} else {
									montoPago = cantidadPagada;
								}
								cantidadPagada = cantidadPagada.subtract(montoPago);
								
								if (montoPago.compareTo(BigDecimal.ZERO) > 0) {
									MovimientoCfdiAplicado movimiento = MovimientoFactory.newInstance(MovimientoCfdiAplicado.class);
									movimiento.setAplicado(Boolean.TRUE);
									movimiento.setCancelado(Boolean.FALSE);
									movimiento.setCuenta(concepto.getCuenta());
									movimiento.setDebe(montoPago);
									movimiento.setFecha(pago.getFechaPago());
									movimiento.setMovimiento(concepto.getMovimientoCfdi());
									movimiento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocfdi"))));
									pago.addMovimiento(movimiento);
								}
							}
						}
					}
					/////////////SE PAGAN LOS IMPUESTOS TRASLADADOS////////////
					if (cantidadPagada.compareTo(BigDecimal.ZERO) > 0) {
						BigDecimal montoPago = null;
						
						if (cantidadPagada.compareTo(cfdi.getMovimientoImpuestoTrasladado().getPendiente()) >= 0) {
							montoPago = cfdi.getMovimientoImpuestoTrasladado().getPendiente();
						} else {
							montoPago = cantidadPagada;
						}
						cantidadPagada = cantidadPagada.subtract(montoPago);
						
						MovimientoCfdiAplicado movimiento = MovimientoFactory.newInstance(MovimientoCfdiAplicado.class);
						movimiento.setAplicado(Boolean.TRUE);
						movimiento.setCancelado(Boolean.FALSE);
						movimiento.setCuenta(CuentaFactory.newInstance(Long.valueOf(22)));//TODO: Reemplazar por cuenta de impuestos
						movimiento.setDebe(montoPago);
						movimiento.setFecha(pago.getFechaPago());
						movimiento.setMovimiento(cfdi.getMovimientoImpuestoTrasladado());
						movimiento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocfdi"))));
						pago.addMovimiento(movimiento);
					}
				}
			}
		}
	}
	
	
	private void createMovimientoPago(PagoProveedor pago) {
		MovimientoPagoProveedor movimientoPago = MovimientoFactory.newInstance(MovimientoPagoProveedor.class);
		movimientoPago.setCuenta(pago.getCuenta());
		movimientoPago.setFecha(pago.getFechaPago());
		movimientoPago.setDebe(pago.getTotal());
		movimientoPago.setPagoProveedor(pago);
		movimientoService.save(movimientoPago);
	}

	@Transactional
	@Override
	public void cancelar(PagoProveedor pago) {
		try {
			Assert.notNull(pago, "SEREX001");
			Assert.notNull(pago.getId(), "SEREX003");

			cancelarMovimientos(pago);
			pagoProveedorDao.merge(pago);
			cancelarMovimientoPago(pago);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	private void cancelarMovimientos(PagoProveedor pago) {
		pago.setMovimientos(null);
		
		PagoProveedor anterior = pagoProveedorDao.get(pago.getId());
		
		if (!CollectionUtils.isEmpty(anterior.getMovimientos())) {
			for (MovimientoCfdiAplicado aplicado : anterior.getMovimientos()) {
				
				if (!aplicado.getCancelado()) {
					aplicado.setCancelado(Boolean.TRUE);
					
					MovimientoCfdiAplicado movimiento = MovimientoFactory.newInstance(MovimientoCfdiAplicado.class);
					movimiento.setAplicado(Boolean.TRUE);
					movimiento.setCancelado(Boolean.FALSE);
					movimiento.setCuenta(aplicado.getCuenta());
					movimiento.setFecha(aplicado.getFecha());
					movimiento.setMovimiento(aplicado.getMovimiento());
					movimiento.setHaber(aplicado.getDebe());
					movimiento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocfdicancelado"))));
					pago.addMovimiento(aplicado);
					pago.addMovimiento(movimiento);
				}
			}
		}
	}
	
	private void cancelarMovimientoPago(PagoProveedor pago) {
		MovimientoPagoProveedor movimientoPago = MovimientoFactory.newInstance(MovimientoPagoProveedor.class);
		movimientoPago.setCuenta(pago.getCuenta());
		movimientoPago.setFecha(pago.getFechaPago());
		movimientoPago.setHaber(pago.getTotal());
		movimientoPago.setPagoProveedor(pago);
		movimientoService.save(movimientoPago);
	}
	
	@Override
	public List<PagoProveedor> getList(Condominio condominio) {
		// TODO Obtiene la lista de pagos por condominio.
		return null;
	}

	@Override
	public PagoProveedor get(Long id) {
		// TODO Obtiene un pago por identificador
		return null;
	}

	@Override
	public void update(PagoProveedor pago) {
		// TODO Actualiza un pago
	}

	@Override
	public List<PagoProveedor> listarPorFecha(Condominio condominio, Date inicio, Date fin) {
		try {
			Assert.notNull(condominio);
			Assert.notNull(inicio);
			Assert.notNull(fin);
			Assert.notNull(condominio.getId());
			return (List<PagoProveedor>) pagoProveedorDao.getPorFecha(condominio,inicio,fin);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
