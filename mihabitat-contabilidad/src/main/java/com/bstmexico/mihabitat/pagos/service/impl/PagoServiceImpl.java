package com.bstmexico.mihabitat.pagos.service.impl;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.dao.MovimientoDao;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.*;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.pagos.dao.PagoDao;
import com.bstmexico.mihabitat.pagos.dao.PagoDepartamentoDao;
import com.bstmexico.mihabitat.pagos.factory.PagoFactory;
import com.bstmexico.mihabitat.pagos.model.CatalogoEstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
import java.math.RoundingMode;
import java.util.*;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service("pagoservicedefault")
public class PagoServiceImpl implements PagoService {

    private static final Logger LOG = LoggerFactory
            .getLogger(PagoServiceImpl.class);

    @Autowired
    private PagoDao pagoDao;

    @Autowired
    private PagoDepartamentoDao pagoDepartamentoDao;

    @Autowired
    @Qualifier("cargoserviceproxy")
    private CargoService cargoService;

    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;

    @Autowired
    private MovimientoDao movimientoDao;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private Validator validator;

    private static Properties cfg;

    Collection<MovimientoCargoAplicado> temporales = null;

    public PagoServiceImpl() {
        try {
            cfg = PropertiesLoaderUtils.loadAllProperties("configuration.properties");
        } catch (IOException e) {
            LOG.error("Error al cargar el archivo de propiedades");
        }
    }

    @Transactional
    @Override
    public void save(Pago pago, Boolean aplicado) {
        try {
            Assert.notNull(pago, "SEREX001");
            Set<ConstraintViolation<Pago>> violations = validator
                    .validate(pago);
            if (violations.isEmpty()) {
                if (aplicado) {
                    pago.setFolio(pagoDao.getLastFolio(pago.getCondominio()));
                }
                fixPago(pago, aplicado);
                pagoDao.save(pago);
                generarMovimientos(pago, aplicado);
                //pagoDao.save(pago); //Se comenta ya que se colocó dentro del m;etodo de generar movimientos.
                createMovimientoPago(aplicado, pago);
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

    @Override
    public Pago get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return pagoDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Collection<Pago> search(Map map) {
        try {
            Assert.notNull(map, "SEREX001");
            return pagoDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Collection<Pago> getList(Condominio condominio, Date inicio, Date fin) {
        try {
            Assert.notNull(condominio, "SEREX001");
            Assert.notNull(condominio.getId(), "SEREX001");
            Assert.notNull(inicio, "SEREX001");
            Assert.notNull(fin, "SEREX001");
            return pagoDao.getList(condominio, inicio, fin);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional
    @Override
    public void aprobar(Pago pago) {
        try {
            Assert.notNull(pago, "SEREX001");
            Assert.notNull(pago.getId(), "SEREX003");

            eliminarMovimientos(pago);
            fixPago(pago, Boolean.TRUE);
            generarMovimientos(pago, Boolean.TRUE);
            pago.setFolio(pagoDao.getLastFolio(pago.getCondominio()));
            pagoDao.merge(pago);
            createMovimientoPago(Boolean.TRUE, pago);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional
    @Override
    public void rechazar(Pago pago) {
        try {
            Assert.notNull(pago, "SEREX001");
            Assert.notNull(pago.getId(), "SEREX003");

            eliminarMovimientos(pago);
            fixPago(pago, Boolean.FALSE);
            generarMovimientos(pago, Boolean.FALSE);
            pagoDao.merge(pago);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional
    @Override
    public void reenviar(Pago pago) {
        try {
            Assert.notNull(pago, "SEREX001");
            Assert.notNull(pago.getId(), "SEREX003");

            eliminarMovimientos(pago);
            fixPago(pago, Boolean.FALSE);
            generarMovimientos(pago, Boolean.FALSE);
            pagoDao.merge(pago);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional
    @Override
    public void cancelar(Pago pago) {
        try {
            Assert.notNull(pago, "SEREX001");
            Assert.notNull(pago.getId(), "SEREX003");

            cancelarMovimientos(pago);
            pagoDao.merge(pago);
            cancelarMovimientoPago(pago);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    private void cancelarMovimientos(Pago pago) {
        for(PagoDepartamento pd : pago.getPagosDepartamento()){
            cancelarMovimientos(pd, pago);
        }
    }

    private void cancelarMovimientos(PagoDepartamento pago, Pago pagoCondomino) {

        pago.setCondominio(pagoCondomino.getCondominio());
        pago.setPagoCondomino(pagoCondomino);
        pago.setMovimientos(null);

        PagoDepartamento anterior = pagoDepartamentoDao.get(pago.getId());
        BigDecimal pagadoNoCancelado = BigDecimal.ZERO;
        BigDecimal pagado = BigDecimal.ZERO;

        if (!CollectionUtils.isEmpty(anterior.getMovimientos())) {

            for (MovimientoCargoAplicado aplicado : anterior.getMovimientos()) {

                if (!aplicado.getCancelado() && aplicado.getHaber() != null) {
                    pagadoNoCancelado = pagadoNoCancelado.add(aplicado.getHaber());
                }
                if (aplicado.getHaber() != null) {
                    pagado = pagado.add(aplicado.getHaber());
                }

                if (!aplicado.getCancelado() && aplicado.getDebe() != null) {
                    pagadoNoCancelado = pagadoNoCancelado.subtract(aplicado.getDebe());
                }
                if (aplicado.getDebe() != null) {
                    pagado = pagado.subtract(aplicado.getDebe());
                }

                if (!aplicado.getCancelado() &&
                        (
                                aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagorecargo"))) ||
                                        aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagocargo"))) ||
                                        aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagodescuento")))
                        )
                        ) {

                    aplicado.setCancelado(Boolean.TRUE);

                    MovimientoCargoAplicado movimiento = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                    movimiento.setAplicado(Boolean.TRUE);
                    movimiento.setCancelado(Boolean.FALSE);
                    movimiento.setCuenta(aplicado.getCuenta());
                    movimiento.setFecha(aplicado.getFecha());
                    movimiento.setPago(aplicado.getPago());
                    movimiento.setPagoDepartamento(aplicado.getPagoDepartamento());
                    movimiento.setMovimientoCargo(aplicado.getMovimientoCargo());

					/* SECCION DE RECARGOS */
                    if (aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagorecargo")))) {
                        movimiento.setDebe(aplicado.getHaber());
                        movimiento.setImprimible(Boolean.TRUE);
                        movimiento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionpagorecargo"))));

					/* SECCION DE CARGOS */
                    } else if (aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagocargo")))) {
                        movimiento.setDebe(aplicado.getHaber());
                        movimiento.setImprimible(aplicado.getImprimible());
                        movimiento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionpagocargo"))));

					/* SECCION DE DESCUENTOS */
                    } else if (aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagodescuento")))) {
                        movimiento.setHaber(aplicado.getDebe());
                        movimiento.setImprimible(Boolean.FALSE);
                        movimiento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionpagodescuento"))));

                        MovimientoCargo descuentoCancelado = MovimientoFactory.newInstance(MovimientoCargo.class);
                        descuentoCancelado.setCancelado(Boolean.FALSE);
                        descuentoCancelado.setCargo(aplicado.getMovimientoCargo().getCargo());
                        descuentoCancelado.setDebe(aplicado.getDebe());
                        descuentoCancelado.setFecha(aplicado.getFecha());
                        descuentoCancelado.setAutogenerado(Boolean.TRUE);
                        descuentoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelaciondescuento"))));
                        movimientoService.save(descuentoCancelado);

                        aplicado.getMovimientoCargo().setCancelado(Boolean.TRUE);
                        // Se tuvo que usar el dao directamente debido a que el validator rechaza la entidad proxy retornada por Hibernate
                        //movimientoService.update(aplicado.getMovimientoCargo());
                        movimientoDao.merge(aplicado.getMovimientoCargo());
                    }
                    pago.addMovimiento(aplicado);
                    pago.addMovimiento(movimiento);
                }
            }
        }

		/*BigDecimal pagadoNoCancelado = BigDecimal.ZERO;
        BigDecimal pagado = BigDecimal.ZERO;
		for (MovimientoCargoAplicado aplicado : anterior.getMovimientos()) {

			if (!aplicado.getCancelado() && aplicado.getHaber() != null) {
				pagadoNoCancelado = pagadoNoCancelado.add(aplicado.getHaber());
			}
			if (aplicado.getHaber() != null) {
				pagado = pagado.add(aplicado.getHaber());
			}

			if (!aplicado.getCancelado() && aplicado.getDebe() != null) {
				pagadoNoCancelado = pagadoNoCancelado.subtract(aplicado.getDebe());
			}
			if (aplicado.getDebe() != null) {
				pagado = pagado.subtract(aplicado.getDebe());
			}
		}
		*/
        /* SECCION DE SALDOS A FAVOR */
        if (!pagoCondomino.getMetodoPago().getId().equals(Long.valueOf(cfg.getProperty("saldofavor")))) {

            BigDecimal saldoContra = pago.getMonto().subtract(pagado);
            if (saldoContra.compareTo(BigDecimal.ZERO) > 0) {
                Cuenta cuentaSaldo = cuentaService.get(pagoCondomino.getCondominio(), cfg.getProperty("cuenta.saldos"));
                if (cuentaSaldo != null) {
                    MovimientoSaldo saldoFavor = MovimientoFactory.newInstance(MovimientoSaldo.class);
                    saldoFavor.setContacto(pagoCondomino.getContacto());
                    saldoFavor.setCuenta(cuentaSaldo);
                    saldoFavor.setFecha(pagoCondomino.getFecha());
                    saldoFavor.setDebe(saldoContra);
                    saldoFavor.setPago(pagoCondomino);
                    saldoFavor.setPagoDepartamento(pago);
                    saldoFavor.setDepartamento(pago.getDepartamento());
                    saldoFavor.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelaciondesaldoafavor"))));

                    movimientoService.save(saldoFavor);
                } else {
                    throw new ServiceException("EXC_CUENTA_SALDO");
                }
            }
        } else {
            if (pagadoNoCancelado.compareTo(BigDecimal.ZERO) > 0) {
                Cuenta cuentaSaldo = cuentaService.get(pagoCondomino.getCondominio(), cfg.getProperty("cuenta.saldos"));
                if (cuentaSaldo != null) {
                    MovimientoSaldo saldoFavor = MovimientoFactory.newInstance(MovimientoSaldo.class);
                    saldoFavor.setContacto(pagoCondomino.getContacto());
                    saldoFavor.setCuenta(cuentaSaldo);
                    saldoFavor.setFecha(pagoCondomino.getFecha());
                    saldoFavor.setHaber(pagadoNoCancelado);
                    saldoFavor.setPago(pagoCondomino);
                    saldoFavor.setPagoDepartamento(pago);
                    saldoFavor.setDepartamento(pago.getDepartamento());
                    saldoFavor.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelaciondeaplicacion"))));

                    movimientoService.save(saldoFavor);
                } else {
                    throw new ServiceException("EXC_CUENTA_SALDO");
                }
            }
        }
    }

    private void eliminarMovimientos(Pago pago) {
        Pago anterior = pagoDao.get(pago.getId());
        for(PagoDepartamento pd : anterior.getPagosDepartamento()){
            /*pagoDepartamentoDao.delete(pd);*/
            this.eliminarMovimientos(pd);
        }
    }

    private void eliminarMovimientos(PagoDepartamento pago) {
        PagoDepartamento anterior = pagoDepartamentoDao.get(pago.getId());
        if (!CollectionUtils.isEmpty(anterior.getMovimientos())) {
            for (MovimientoCargoAplicado aplicado : anterior.getMovimientos()) {
                movimientoService.delete(aplicado);
                if (aplicado.getMovimientoCargo().getId().equals(Long.valueOf(cfg.getProperty("descuento")))) {
                    movimientoService.delete(aplicado.getMovimientoCargo());
                }
            }
        }
        if (pago.getPagoCondomino().getComprobante() == null) {
            pago.getPagoCondomino().setComprobante(anterior.getPagoCondomino().getComprobante());
        }
    }

    private void generarMovimientos(Pago pago, Boolean aplicado) {
        for(PagoDepartamento pd : pago.getPagosDepartamento()){
            this.generarMovimientos(pd, pago, aplicado);
        }
    }

    private void generarMovimientos(PagoDepartamento pago, Pago pagoCondomino, Boolean aplicado) {


        /* SECCION DE SALDOS A FAVOR */
        if (aplicado) {
            if (!pagoCondomino.getMetodoPago().getId().equals(Long.valueOf(cfg.getProperty("saldofavor")))) {
                BigDecimal cantidadSaldoFavor = pago.getMonto();

                if (!CollectionUtils.isEmpty(pago.getMovimientos())) {
                    for (MovimientoCargoAplicado temporal : pago.getMovimientos()) {
                        if(temporal.getHaber() != null && temporal.getHaber().compareTo(BigDecimal.ZERO) > 0) {
                            cantidadSaldoFavor = cantidadSaldoFavor.subtract(temporal.getHaber());
                        }
                        if(temporal.getDebe() != null && temporal.getDebe().compareTo(BigDecimal.ZERO) > 0) {
                            cantidadSaldoFavor = cantidadSaldoFavor.add(temporal.getDebe());
                        }
                    }
                }
                if (cantidadSaldoFavor.compareTo(BigDecimal.ZERO) > 0) {
                    Cuenta cuentaSaldo = cuentaService.get(pago.getCondominio(), cfg.getProperty("cuenta.saldos"));
                    if (cuentaSaldo != null) {
                        MovimientoSaldo saldoFavor = MovimientoFactory.newInstance(MovimientoSaldo.class);
                        saldoFavor.setContacto(pagoCondomino.getContacto());
                        saldoFavor.setCuenta(cuentaSaldo);
                        saldoFavor.setFecha(pagoCondomino.getFecha());
                        saldoFavor.setHaber(cantidadSaldoFavor);
                        saldoFavor.setPago(pagoCondomino);
                        saldoFavor.setPagoDepartamento(pago);
                        saldoFavor.setDepartamento(pago.getDepartamento());
                        saldoFavor.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("saldoafavorgenerado"))));
                        movimientoService.save(saldoFavor);
                    } else {
                        throw new ServiceException("EXC_CUENTA_SALDO");
                    }
                }
            } else {
                if (!CollectionUtils.isEmpty(pago.getMovimientos())) {
                    BigDecimal cantidadGastadaSaldoFavor = BigDecimal.ZERO;
                    /*for (MovimientoCargoAplicado temporal : temporales) {
                        cantidadGastadaSaldoFavor = cantidadGastadaSaldoFavor.add(temporal.getHaber());
                    }*/
                    cantidadGastadaSaldoFavor = pago.getAplicadoCargos();
                    //CAMBIO Para el saldo a favor
                    BigDecimal saldoFavorReal = movimientoService.getSaldoFavorPorDepartamento(pago.getDepartamento());
                    if (saldoFavorReal.compareTo(cantidadGastadaSaldoFavor) >= 0) {
                        Cuenta cuentaSaldo = cuentaService.get(pago.getCondominio(), cfg.getProperty("cuenta.saldos"));
                        if (cuentaSaldo != null) {
                            MovimientoSaldo saldoFavor = MovimientoFactory.newInstance(MovimientoSaldo.class);
                            saldoFavor.setContacto(pagoCondomino.getContacto());
                            saldoFavor.setCuenta(cuentaSaldo);
                            saldoFavor.setFecha(pagoCondomino.getFecha());
                            saldoFavor.setDebe(cantidadGastadaSaldoFavor);
                            saldoFavor.setPago(pagoCondomino);
                            saldoFavor.setPagoDepartamento(pago);
                            saldoFavor.setDepartamento(pago.getDepartamento());
                            saldoFavor.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("aplicaciondesaldoafavor"))));
                            movimientoService.save(saldoFavor);
                        } else {
                            throw new ServiceException("EXC_CUENTA_SALDO");
                        }
                    } else {
                        throw new ServiceException("SEREXPAGO");
                    }
                } else {
                    throw new ServiceException("SEREXPAGOSALDO");
                }
            }
        }
    }

    /**
     * @param pago     El pago que podria generar el descuento
     * @param cargo    El cargo sobre el que se podria generar el descuento
     * @param temporal El movimiento pago que paga el cargo
     * @return Si aplica o no un descuento segun se haya realizado el pago en fecha y haya pagado completo el cargo
     */
    private Boolean aplicaDescuento(Pago pago, CargoDepartamento cargo, MovimientoCargoAplicado temporal) {
        Boolean aplica = Boolean.FALSE;
        if (cargo.getDescuento() != null) { //Verificamos que el cargo tenga un descuento automatico
            if (pago.getFecha().compareTo(cargo.getDescuento().getFecha()) <= 0) { // Verificamos que el pago se haya realizado antes o en la fecha del descuento
                if (cargo.getSaldoPendiente().subtract(calcularMontoDelDescuento(cargo)).compareTo(temporal.getHaber()) <= 0) { //Verificamos que haya realizado el pago (completo - decuento automatico)
                    aplica = Boolean.TRUE;
                }
            }
        }
        return aplica;
    }

    /**
     * @param cargo el cargo sobre el que se quiere calcular el descuento total automatico
     * @return El monto del descuento automatico. Cero si el cargo o el descuento son nulos.
     */
    private BigDecimal calcularMontoDelDescuento(CargoDepartamento cargo) {
        BigDecimal montoDescuento = BigDecimal.ZERO;
        if (cargo != null && cargo.getDescuento() != null) {
            if (cargo.getDescuento().getPorcentaje()) {
                montoDescuento = cargo.getTotalMonto().multiply(cargo.getDescuento().getMonto()).multiply(new BigDecimal(.01)).setScale(2, RoundingMode.HALF_UP);
            } else {
                montoDescuento = cargo.getDescuento().getMonto();
            }
        }
        return montoDescuento;
    }

    private void createMovimientoPago(Boolean aplicado, Pago pago) {
        if (aplicado && !pago.getMetodoPago().getId().equals(Long.valueOf(cfg.getProperty("saldofavor")))) {
            MovimientoPago movimientoPago = MovimientoFactory.newInstance(MovimientoPago.class);
            movimientoPago.setCuenta(pago.getCuenta());
            movimientoPago.setFecha(pago.getFecha());
            movimientoPago.setHaber(pago.getMonto());
            movimientoPago.setPago(pago);
            movimientoService.save(movimientoPago);
        }
    }

    private void cancelarMovimientoPago(Pago pago) {
        if (!pago.getMetodoPago().getId().equals(Long.valueOf(cfg.getProperty("saldofavor")))) {
            MovimientoPago movimientoPagoCancelado = MovimientoFactory.newInstance(MovimientoPago.class);
            movimientoPagoCancelado.setCuenta(pago.getCuenta());
            movimientoPagoCancelado.setFecha(pago.getFecha());
            movimientoPagoCancelado.setDebe(pago.getMonto());
            movimientoPagoCancelado.setPago(pago);
            movimientoService.save(movimientoPagoCancelado);
        }
    }

    @Override
    public Collection<Pago> getPagosByStatus(Condominio condominio, CatalogoEstatusPago estatus) {
        try {
            Assert.notNull(estatus, "SEREX001");
            Assert.notNull(estatus.getId(), "SEREX001");
            Assert.notNull(condominio, "SEREX001");
            Assert.notNull(condominio.getId(), "SEREX001");
            Collection<Pago> pagos = pagoDao.getPagosByStatus(condominio, estatus);

            return pagos;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<Pago> getPagosByStatus(Contacto contacto, CatalogoEstatusPago estatus) {
        try {
            Assert.notNull(estatus, "SEREX001");
            Assert.notNull(estatus.getId(), "SEREX001");
            Assert.notNull(contacto, "SEREX001");
            Assert.notNull(contacto.getId(), "SEREX001");
            Collection<Pago> pagos = pagoDao.getPagosByStatus(contacto, estatus);

            return pagos;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Collection<PagoDepartamento> getPagos(Departamento departamento) {
        try {
            Assert.notNull(departamento, "SEREX001");
            Assert.notNull(departamento.getId(), "SEREX001");
            Collection<PagoDepartamento> pagos = pagoDao.getPagos(departamento);

            return pagos;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    private void fixPago(Pago pagoCondomino, Boolean aplicado) {
        for(PagoDepartamento pago : pagoCondomino.getPagosDepartamento()) {
            pago.setCondominio(pagoCondomino.getCondominio());
            pago.setPagoCondomino(pagoCondomino);

            temporales = pago.getMovimientos();
            pago.setMovimientos(null);

            if (!CollectionUtils.isEmpty(temporales)) {
                for (MovimientoCargoAplicado temporal : temporales) {
                    BigDecimal cantidadPagada = temporal.getHaber();
                    CargoDepartamento cargo = (CargoDepartamento) cargoService.get(temporal.getMovimientoCargo().getCargo().getId(), CargoDepartamento.class, Boolean.TRUE);

				/* SECCION DE DESCUENTOS */
                    if (aplicaDescuento(pagoCondomino, cargo, temporal)) {
                        BigDecimal montoDescuento = calcularMontoDelDescuento(cargo);
                        if (cantidadPagada.compareTo(cargo.getSaldoPendiente().subtract(montoDescuento)) >= 0) {
                            cantidadPagada = cantidadPagada.subtract(cargo.getSaldoPendiente().subtract(montoDescuento));

                            Cuenta cuentaDescuento = cuentaService.get(cargo.getCondominio(), cfg.getProperty("cuenta.descuentos"));
                            if (cuentaDescuento != null) {
                                MovimientoCargo descuento = MovimientoFactory.newInstance(MovimientoCargo.class);
                                descuento.setCancelado(Boolean.FALSE);
                                descuento.setCargo(cargo);
                                descuento.setFecha(pagoCondomino.getFecha());
                                descuento.setHaber(montoDescuento);
                                descuento.setAutogenerado(Boolean.TRUE);
                                descuento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("descuento"))));
                                movimientoService.save(descuento);

                                MovimientoCargoAplicado pagoDescuento = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                                pagoDescuento.setAplicado(aplicado);
                                pagoDescuento.setCancelado(Boolean.FALSE);
                                pagoDescuento.setCuenta(cuentaDescuento);
                                pagoDescuento.setDebe(montoDescuento);
                                pagoDescuento.setFecha(pagoCondomino.getFecha());
                                pagoDescuento.setImprimible(Boolean.FALSE);
                                pagoDescuento.setMovimientoCargo(descuento);
                                pagoDescuento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagodescuento"))));
                                pagoDescuento.setPago(pagoCondomino);
                                pago.addMovimiento(pagoDescuento);

                                MovimientoCargoAplicado pagoCargoDescuento = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                                pagoCargoDescuento.setAplicado(aplicado);
                                pagoCargoDescuento.setCancelado(Boolean.FALSE);
                                pagoCargoDescuento.setCuenta(cargo.getCuenta());
                                pagoCargoDescuento.setFecha(pagoCondomino.getFecha());
                                pagoCargoDescuento.setHaber(montoDescuento);
                                pagoCargoDescuento.setImprimible(Boolean.FALSE);
                                pagoCargoDescuento.setMovimientoCargo(cargo.getCargo());
                                pagoCargoDescuento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocargo"))));
                                pagoCargoDescuento.setPago(pagoCondomino);
                                pago.addMovimiento(pagoCargoDescuento);
                            } else {
                                throw new ServiceException("EXC_CUENTA_DESCUENTO");
                            }

                            if (cargo.getSaldoPendiente().subtract(montoDescuento).compareTo(BigDecimal.ZERO) > 0) {
                                MovimientoCargoAplicado pagoCargo = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                                pagoCargo.setAplicado(aplicado);
                                pagoCargo.setCancelado(Boolean.FALSE);
                                pagoCargo.setCuenta(cargo.getCuenta());
                                pagoCargo.setFecha(pagoCondomino.getFecha());
                            /*pagoCargo.setHaber(cargo.getTotalMonto().subtract(montoDescuento));*/
                                pagoCargo.setHaber(cargo.getSaldoPendiente().subtract(montoDescuento));
                                pagoCargo.setImprimible(Boolean.TRUE);
                                pagoCargo.setMovimientoCargo(cargo.getCargo());
                                pagoCargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocargo"))));
                                pagoCargo.setPago(pagoCondomino);
                                pago.addMovimiento(pagoCargo);
                            }
                        }
                    }

				/* SECCION DE RECARGOS */
                    if (cantidadPagada.compareTo(BigDecimal.ZERO) > 0) {
                        if (!CollectionUtils.isEmpty(cargo.getRecargos())) {
                            for (MovimientoCargo recargo : cargo.getRecargos()) {
                                if (!recargo.getCancelado() && recargo.getDebe().compareTo(recargo.getTotal()) > 0 && cantidadPagada.compareTo(BigDecimal.ZERO) > 0) {
                                    BigDecimal montoRecargo = null;
                                    if (cantidadPagada.compareTo(recargo.getDebe().subtract(recargo.getTotal())) >= 0) {
                                        montoRecargo = recargo.getDebe().subtract(recargo.getTotal());
                                    } else {
                                        montoRecargo = cantidadPagada;
                                    }
                                    cantidadPagada = cantidadPagada.subtract(montoRecargo);

                                    Cuenta cuentaRecargo = cuentaService.get(cargo.getCondominio(), cfg.getProperty("cuenta.recargos"));
                                    if (cuentaRecargo != null) {
                                        MovimientoCargoAplicado pagoRecargo = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                                        pagoRecargo.setAplicado(aplicado);
                                        pagoRecargo.setCancelado(Boolean.FALSE);
                                        pagoRecargo.setCuenta(cuentaRecargo);
                                        pagoRecargo.setFecha(pagoCondomino.getFecha());
                                        pagoRecargo.setHaber(montoRecargo);
                                        pagoRecargo.setImprimible(Boolean.TRUE);
                                        pagoRecargo.setMovimientoCargo(recargo);
                                        pagoRecargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagorecargo"))));
                                        pagoRecargo.setPago(pagoCondomino);
                                        pago.addMovimiento(pagoRecargo);
                                    } else {
                                        throw new ServiceException("EXC_CUENTA_RECARGO");
                                    }
                                }
                            }
                        }

					/* SECCCION DE CARGO */
                        if (cantidadPagada.compareTo(BigDecimal.ZERO) > 0) {
                            BigDecimal montoCargo = null;
                            if (cantidadPagada.compareTo(cargo.getSaldoPendiente()) >= 0) {
                                montoCargo = cargo.getSaldoPendiente();
                            } else {
                                montoCargo = cantidadPagada;
                            }
                            cantidadPagada = cantidadPagada.subtract(montoCargo);

                            MovimientoCargoAplicado pagoCargo = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                            pagoCargo.setAplicado(aplicado);
                            pagoCargo.setCancelado(Boolean.FALSE);
                            pagoCargo.setCuenta(cargo.getCuenta());
                            pagoCargo.setFecha(pagoCondomino.getFecha());
                            pagoCargo.setHaber(montoCargo);
                            pagoCargo.setImprimible(Boolean.TRUE);
                            pagoCargo.setMovimientoCargo(cargo.getCargo());
                            pagoCargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocargo"))));
                            pagoCargo.setPago(pagoCondomino);
                            pago.addMovimiento(pagoCargo);
                        }
                    }
                }
            }
        }
    }

    /*private void fixPagoParaCancelar(Pago pagoCondomino) {
        for(PagoDepartamento pago : pagoCondomino.getPagosDepartamento()) {
            pago.setCondominio(pagoCondomino.getCondominio());
            pago.setPagoCondomino(pagoCondomino);
        }
    }*/
}
