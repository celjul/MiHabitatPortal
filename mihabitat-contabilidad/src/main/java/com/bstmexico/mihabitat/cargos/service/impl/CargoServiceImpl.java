package com.bstmexico.mihabitat.cargos.service.impl;

import com.bstmexico.mihabitat.cargos.dao.CargoDao;
import com.bstmexico.mihabitat.cargos.model.*;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoSaldo;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
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
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service("cargoservicedefault")
public class CargoServiceImpl implements CargoService {

    private static final Logger LOG = LoggerFactory
            .getLogger(CargoServiceImpl.class);
    private static Properties cfg;
    @Autowired
    private CargoDao cargoDao;
    @Autowired
    private ContactoService contactoService;
    @Autowired
    private Validator validator;
    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;
    @Autowired
    private CuentaService cuentaService;

    public CargoServiceImpl() {
        try {
            cfg = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
        } catch (IOException e) {
            LOG.error("Error al cargar el archivo de propiedades");
        }
    }

    @Override
    public void save(Cargo cargo) {
        try {
            Assert.notNull(cargo, "SEREX001");
            Set<ConstraintViolation<Cargo>> violations = validator
                    .validate(cargo);
            if (violations.isEmpty()) {
                cargoDao.save(cargo);
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
    public Cargo getConDepartamento(Long id, Class<? extends Cargo> cargo, Boolean desbordar) {
        try {
            Assert.notNull(id, "SEREX003");
            Assert.notNull(cargo, "SEREX003");
            Assert.notNull(desbordar, "SEREX003");
            return cargoDao.getConDepartamento(id, cargo, desbordar);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Cargo get(Long id, Class<? extends Cargo> cargo, Boolean desbordar) {
        try {
            Assert.notNull(id, "SEREX003");
            Assert.notNull(cargo, "SEREX003");
            Assert.notNull(desbordar, "SEREX003");
            return cargoDao.get(id, cargo, desbordar);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional
    @Override
    public void update(Cargo cargo) {
        try {
            Assert.notNull(cargo, "SEREX001");
            Assert.notNull(cargo.getId(), "SEREX003");
            Set<ConstraintViolation<Cargo>> violations = validator
                    .validate(cargo);
            if (violations.isEmpty()) {
                if (cargo instanceof CargoAgrupador) {
                    CargoAgrupador anterior = (CargoAgrupador) cargoDao.get(
                            cargo.getId(), CargoAgrupador.class, Boolean.FALSE);
                    Collection<CargoDepartamento> cancelados = new ArrayList<CargoDepartamento>();
                    if (!CollectionUtils.isEmpty(anterior.getCargos())) {
                        for (CargoDepartamento cd : anterior.getCargos()) {
                            if (!((CargoAgrupador) cargo).getCargos().contains(
                                    cd)) {
                                cancelados.add(cd);
                            }
                        }
                    }
                    if (!CollectionUtils.isEmpty(cancelados)) {
                        for (CargoDepartamento cd : cancelados) {
                            cancelarCargo(cd);
                            ((CargoAgrupador) cargo).getCargos().add(cd);
                        }
                    }
                } else if(cargo instanceof  CargoDepartamento){
                    CargoDepartamento anterior = (CargoDepartamento) cargoDao.get(cargo.getId(), CargoDepartamento.class, Boolean.FALSE);
                    long dias = (((CargoDepartamento) cargo).getFecha().getTime() - anterior.getFecha().getTime()) / (3600 * 24 * 1000);
                    if (dias != 0) {
                        for (MovimientoCargo movimientoCargo : ((CargoDepartamento) cargo).getMovimientos()) {
                            if (movimientoCargo.getTipo().getId() != Long.parseLong(cfg.getProperty("pagodescuento")) &&
                                    movimientoCargo.getTipo().getId() != Long.parseLong(cfg.getProperty("pagorecargo")) &&
                                    movimientoCargo.getTipo().getId() != Long.parseLong(cfg.getProperty("pagocargo")) &&
                                    movimientoCargo.getTipo().getId() != Long.parseLong(cfg.getProperty("cancelacionpagorecargo")) &&
                                    movimientoCargo.getTipo().getId() != Long.parseLong(cfg.getProperty("cancelacionpagocargo")) &&
                                    movimientoCargo.getTipo().getId() != Long.parseLong(cfg.getProperty("cancelacionpagodescuento")) &&
                                    (movimientoCargo.getAutogenerado() == null || movimientoCargo.getAutogenerado().booleanValue() == false)) {

                                Calendar cal = GregorianCalendar.getInstance();
                                cal.setTime(movimientoCargo.getFecha());
                                cal.add(Calendar.DATE, (int) dias);
                                movimientoCargo.setFecha(cal.getTime());
                            }
                        }

                    }
                }
                /*if(((CargoDepartamento)cargo).getRecargo() != null){
                    //((CargoDepartamento) cargo).getRecargo().getId();
                    ((CargoDepartamento)cargo).setRecargo(null);
                }*/
                cargoDao.merge(cargo);
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Collection<Cargo> search(Map map, Class<? extends Cargo> cargo, Boolean desbordar) {
        try {
            Assert.notNull(map, "SEREX001");
            return cargoDao.search(map.entrySet(), cargo, desbordar);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Collection<CargoDepartamento> getList(Condominio condominio, Date inicio, Date fin) {
        try {
            Assert.notNull(condominio, "SEREX001");
            Assert.notNull(condominio.getId(), "SEREX001");
            Assert.notNull(inicio, "SEREX001");
            Assert.notNull(fin, "SEREX001");

            return cargoDao.getList(condominio, inicio, fin);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public CargoAgrupador getAnterior(Condominio condominio, TipoConsumo consumo) {
        try {
            Assert.notNull(condominio, "SEREX001");
            Assert.notNull(condominio.getId(), "SEREX001");
            Assert.notNull(consumo, "SEREX001");
            Assert.notNull(consumo.getId(), "SEREX001");
            return cargoDao.getAnterior(condominio, consumo);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<CargoDepartamento> getCargos(Collection<Departamento> departamentos) {
        try {
            Assert.notNull(departamentos, "SEREX001");
            Assert.notEmpty(departamentos, "SEREX001");

            return cargoDao.getCargos(departamentos, null);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<CargoDepartamento> getCargosPendientes(Departamento departamento, Date fin) {
        try {
            Assert.notNull(departamento, "SEREX001");
            Assert.notNull(departamento.getId(), "SEREX001");

            return cargoDao.getCargosPendientes(departamento, fin);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<CargoDepartamento> getCargos(Contacto contacto, Date fecha) {
        try {
            Assert.notNull(contacto, "SEREX001");
            Assert.notNull(contacto.getId(), "SEREX001");
            contacto = contactoService.get(contacto.getId());
            Collection<Departamento> departamentos = new ArrayList<Departamento>();
            if (!CollectionUtils.isEmpty(contacto.getDepartamentos())) {
                for (ContactoDepartamento cd : contacto.getDepartamentos()) {
                    departamentos.add(cd.getDepartamento());
                }
            }
            if (CollectionUtils.isEmpty(departamentos)) {
                return null;
            } else {
                return cargoDao.getCargos(departamentos, fecha);
            }
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<CargoDepartamento> getCargos(Pago pago) {
        try {
            Assert.notNull(pago, "SEREX001");
            Assert.notNull(pago.getId(), "SEREX001");
            Collection<CargoDepartamento> cds = new ArrayList<>();
            for(PagoDepartamento pd : pago.getPagosDepartamento()) {
                cds.addAll(this.getCargos(pd));
            }
            return cds;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<CargoDepartamento> getCargos(PagoDepartamento pago) {
        try {
            Assert.notNull(pago, "SEREX001");
            Assert.notNull(pago.getId(), "SEREX001");
            if (!CollectionUtils.isEmpty(pago.getMovimientos())) {
                return cargoDao.getCargos(pago);
            } else {
                return new ArrayList<CargoDepartamento>();
            }
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<CargoDepartamento> getCargosPorFecha(Date fecha) {
        try {
            Assert.notNull(fecha, "SEREX001");

            return cargoDao.getCargosPorFecha(fecha);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<CargoRecurrente> getCargosRecurrentesPorFecha(Date fecha) {
        try {
            Assert.notNull(fecha, "SEREX001");
            return cargoDao.getCargosRecurrentesActivosPorFecha(fecha);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<CargoDepartamento> getCargosRecargosPorAplicar(Date fecha) {
        try {
            Assert.notNull(fecha, "SEREX001");
            return cargoDao.getCargosRecargosPorAplicar(fecha);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional
    @Override
    public void generarDescuento(MovimientoCargo descuento) {

        descuento.setAutogenerado(Boolean.FALSE);
        movimientoService.save(descuento);

        CargoDepartamento cargo = (CargoDepartamento) cargoDao.get(descuento.getCargo().getId(), CargoDepartamento.class, Boolean.TRUE);

        //if (cargo.getTotalMonto().subtract(cargo.getCargo().getTotal()).subtract(cargo.getTotalDescuentos()).compareTo(descuento.getHaber()) >= 0) {
        if (cargo.getSaldoPendiente().compareTo(BigDecimal.ZERO) >= 0) {
            Cuenta cuentaDescuento = cuentaService.get(cargo.getCondominio(), cfg.getProperty("cuenta.descuentos"));
            if (cuentaDescuento != null) {
                MovimientoCargoAplicado pagoDescuento = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                pagoDescuento.setAplicado(Boolean.TRUE);
                pagoDescuento.setCancelado(Boolean.FALSE);
                pagoDescuento.setCuenta(cuentaDescuento);
                pagoDescuento.setDebe(descuento.getHaber());
                pagoDescuento.setFecha(descuento.getFecha());
                pagoDescuento.setImprimible(Boolean.FALSE);
                pagoDescuento.setMovimientoCargo(descuento);
                pagoDescuento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagodescuento"))));
                movimientoService.save(pagoDescuento);

                MovimientoCargoAplicado pagoCargoDescuento = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                pagoCargoDescuento.setAplicado(Boolean.TRUE);
                pagoCargoDescuento.setCancelado(Boolean.FALSE);
                pagoCargoDescuento.setCuenta(cargo.getCuenta());
                pagoCargoDescuento.setFecha(descuento.getFecha());
                pagoCargoDescuento.setHaber(descuento.getHaber());
                pagoCargoDescuento.setImprimible(Boolean.FALSE);
                pagoCargoDescuento.setMovimientoCargo(cargo.getCargo());
                pagoCargoDescuento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocargo"))));
                movimientoService.save(pagoCargoDescuento);
            } else {
                throw new ServiceException("EXC_CUENTA_DESCUENTO");
            }
        } else {
            throw new ServiceException("EXC_DESC");
        }
    }

    @Override
    public void generarRecargo(MovimientoCargo recargo) {
        movimientoService.save(recargo);
    }

    @Transactional
    @Override
    public void cancelarDescuento(MovimientoCargo descuento) {

        CargoDepartamento cargo = (CargoDepartamento) cargoDao.get(descuento.getCargo().getId(), CargoDepartamento.class, Boolean.TRUE);

        if (!CollectionUtils.isEmpty(cargo.getMovimientos())) {
            for (MovimientoCargo movimiento : cargo.getMovimientos()) {
                if (movimiento.getId().equals(descuento.getId())) {
                    descuento = movimiento;
                    break;
                }
            }
        }

        cancelarDescuento(descuento, cargo);
    }

    @Transactional
    @Override
    public void cancelarRecargo(MovimientoCargo recargo) {

        CargoDepartamento cargo = (CargoDepartamento) cargoDao.get(recargo.getCargo().getId(), CargoDepartamento.class, Boolean.TRUE);

        if (!CollectionUtils.isEmpty(cargo.getMovimientos())) {
            for (MovimientoCargo movimiento : cargo.getMovimientos()) {
                if (movimiento.getId().equals(recargo.getId())) {
                    recargo = movimiento;
                    break;
                }
            }
        }

        // cancelarRecargo(recargo, cargo);
        recargo.setCancelado(Boolean.TRUE);
        movimientoService.update(recargo);

        MovimientoCargo recargoCancelado = MovimientoFactory.newInstance(MovimientoCargo.class);
        recargoCancelado.setCancelado(Boolean.FALSE);
        recargoCancelado.setCargo(cargo);
        recargoCancelado.setHaber(recargo.getDebe());
        recargoCancelado.setFecha(recargo.getFecha());
        recargoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionrecargo"))));
        movimientoService.save(recargoCancelado);

        if (!CollectionUtils.isEmpty(recargo.getAplicados())) {
            BigDecimal totalCargo = cargo.getTotalMonto().subtract(cargo.getCargo().getTotal());

            for (MovimientoCargoAplicado aplicado : recargo.getAplicados()) {
                if (!aplicado.getCancelado() && aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagorecargo")))) {

                    aplicado.setCancelado(Boolean.TRUE);
                    movimientoService.update(aplicado);

                    MovimientoCargoAplicado pagoRecargoCancelado = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                    pagoRecargoCancelado.setAplicado(Boolean.TRUE);
                    pagoRecargoCancelado.setCancelado(Boolean.FALSE);
                    pagoRecargoCancelado.setCuenta(aplicado.getCuenta());
                    pagoRecargoCancelado.setFecha(aplicado.getFecha());
                    pagoRecargoCancelado.setPago(aplicado.getPago());
                    pagoRecargoCancelado.setMovimientoCargo(recargo);
                    pagoRecargoCancelado.setDebe(aplicado.getHaber());
                    pagoRecargoCancelado.setImprimible(Boolean.TRUE);
                    pagoRecargoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionpagorecargo"))));
                    movimientoService.save(pagoRecargoCancelado);

                    if (totalCargo.compareTo(BigDecimal.ZERO) > 0 && aplicado.getHaber().compareTo(totalCargo) > 0) {
                        MovimientoCargoAplicado pagoCargo = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                        pagoCargo.setAplicado(Boolean.TRUE);
                        pagoCargo.setCancelado(Boolean.FALSE);
                        pagoCargo.setCuenta(cargo.getCuenta());
                        pagoCargo.setFecha(aplicado.getFecha());
                        pagoCargo.setPago(aplicado.getPago());
                        pagoCargo.setHaber(totalCargo);
                        pagoCargo.setImprimible(Boolean.TRUE);
                        pagoCargo.setMovimientoCargo(cargo.getCargo());
                        pagoCargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocargo"))));
                        movimientoService.save(pagoCargo);

                        Cuenta cuentaSaldo = cuentaService.get(cargo.getCondominio(), cfg.getProperty("cuenta.saldos"));
                        if (cuentaSaldo != null) {
                            MovimientoSaldo saldoFavor = MovimientoFactory.newInstance(MovimientoSaldo.class);
                            saldoFavor.setContacto(aplicado.getPago().getContacto());
                            saldoFavor.setCuenta(cuentaSaldo);
                            saldoFavor.setFecha(aplicado.getFecha());
                            saldoFavor.setHaber(aplicado.getHaber().subtract(totalCargo));
                            saldoFavor.setTipo((CatalogoTipoMovimiento)CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("saldoafavorgenerado"))));
                            movimientoService.save(saldoFavor);
                        } else {
                            throw new ServiceException("EXC_CUENTA_SALDO");
                        }

                        totalCargo = BigDecimal.ZERO;
                    } else if (totalCargo.compareTo(aplicado.getHaber()) >= 0) {
                        MovimientoCargoAplicado pagoCargo = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                        pagoCargo.setAplicado(Boolean.TRUE);
                        pagoCargo.setCancelado(Boolean.FALSE);
                        pagoCargo.setCuenta(cargo.getCuenta());
                        pagoCargo.setFecha(aplicado.getFecha());
                        pagoCargo.setPago(aplicado.getPago());
                        pagoCargo.setHaber(aplicado.getHaber());
                        pagoCargo.setImprimible(Boolean.TRUE);
                        pagoCargo.setMovimientoCargo(cargo.getCargo());
                        pagoCargo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocargo"))));
                        movimientoService.save(pagoCargo);

                        totalCargo = totalCargo.subtract(aplicado.getHaber());
                    } else {
                        Cuenta cuentaSaldo = cuentaService.get(cargo.getCondominio(), cfg.getProperty("cuenta.saldos"));
                        if (cuentaSaldo != null) {
                            MovimientoSaldo saldoFavor = MovimientoFactory.newInstance(MovimientoSaldo.class);
                            saldoFavor.setContacto(aplicado.getPago().getContacto());
                            saldoFavor.setCuenta(cuentaSaldo);
                            saldoFavor.setFecha(aplicado.getFecha());
                            saldoFavor.setHaber(aplicado.getHaber());
                            saldoFavor.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("saldoafavorgenerado"))));
                            movimientoService.save(saldoFavor);
                        } else {
                            throw new ServiceException("EXC_CUENTA_SALDO");
                        }
                    }
                }
            }
        }
    }

    @Transactional
    @Override
    public void cancelarCargo(CargoDepartamento cargo) {
        cargo = (CargoDepartamento) cargoDao.get(cargo.getId(),
                CargoDepartamento.class, Boolean.TRUE);

        if (!CollectionUtils.isEmpty(cargo.getDescuentos())) {
            for (MovimientoCargo descuento : cargo.getDescuentos()) {
                if (!descuento.getCancelado()) {
                    cancelarDescuento(descuento, cargo);
                }
            }
        }

        if (!CollectionUtils.isEmpty(cargo.getRecargos())) {
            for (MovimientoCargo recargo : cargo.getRecargos()) {
                if (!recargo.getCancelado()) {
                    cancelarRecargo(recargo, cargo);
                }
            }
        }

        if (!cargo.getCargo().getCancelado()) {
            cancelarCargo(cargo.getCargo(), cargo);
        }
    }

    @Transactional
    private void cancelarDescuento(MovimientoCargo descuento, CargoDepartamento cargo) {

        descuento.setCancelado(Boolean.TRUE);
        movimientoService.update(descuento);

        MovimientoCargo descuentoCancelado = MovimientoFactory.newInstance(MovimientoCargo.class);
        descuentoCancelado.setCancelado(Boolean.FALSE);
        descuentoCancelado.setCargo(cargo);
        descuentoCancelado.setDebe(descuento.getHaber());
        descuentoCancelado.setFecha(descuento.getFecha());
        descuentoCancelado.setAutogenerado(descuento.getAutogenerado());
        descuentoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelaciondescuento"))));
        movimientoService.save(descuentoCancelado);

        MovimientoCargoAplicado pagoDescuento = null;
        if (!CollectionUtils.isEmpty(descuento.getAplicados())) {
            for (MovimientoCargoAplicado aplicado : descuento.getAplicados()) {
                if (!aplicado.getCancelado() && aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagodescuento")))) {
                    pagoDescuento = aplicado;
                    break;
                }
            }
        }

        pagoDescuento.setCancelado(Boolean.TRUE);
        movimientoService.update(pagoDescuento);

        MovimientoCargoAplicado pagoDescuentoCancelado = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
        pagoDescuentoCancelado.setAplicado(Boolean.TRUE);
        pagoDescuentoCancelado.setCancelado(Boolean.FALSE);
        pagoDescuentoCancelado.setCuenta(pagoDescuento.getCuenta());
        pagoDescuentoCancelado.setFecha(pagoDescuento.getFecha());
        pagoDescuentoCancelado.setMovimientoCargo(descuento);
        pagoDescuentoCancelado.setHaber(pagoDescuento.getDebe());
        pagoDescuentoCancelado.setImprimible(Boolean.FALSE);
        pagoDescuentoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionpagodescuento"))));
        movimientoService.save(pagoDescuentoCancelado);

        if (!CollectionUtils.isEmpty(cargo.getCargo().getAplicados())) {
            for (MovimientoCargoAplicado aplicado : cargo.getCargo().getAplicados()) {
                if (!aplicado.getCancelado() && aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagocargo"))) && aplicado.getHaber().equals(descuento.getHaber())) {

                    aplicado.setCancelado(Boolean.TRUE);
                    movimientoService.update(aplicado);

                    MovimientoCargoAplicado pagoCargoCancelado = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                    pagoCargoCancelado.setAplicado(Boolean.TRUE);
                    pagoCargoCancelado.setCancelado(Boolean.FALSE);
                    pagoCargoCancelado.setCuenta(aplicado.getCuenta());
                    pagoCargoCancelado.setFecha(aplicado.getFecha());
                    pagoCargoCancelado.setPago(aplicado.getPago());
                    pagoCargoCancelado.setMovimientoCargo(cargo.getCargo());
                    pagoCargoCancelado.setDebe(aplicado.getHaber());
                    pagoCargoCancelado.setImprimible(Boolean.FALSE);
                    pagoCargoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionpagocargo"))));
                    movimientoService.save(pagoCargoCancelado);
                    break;
                }
            }
        }
    }

    @Transactional
    private void cancelarRecargo(MovimientoCargo recargo, CargoDepartamento cargo) {
        recargo.setCancelado(Boolean.TRUE);
        movimientoService.update(recargo);

        MovimientoCargo recargoCancelado = MovimientoFactory.newInstance(MovimientoCargo.class);
        recargoCancelado.setCancelado(Boolean.FALSE);
        recargoCancelado.setCargo(cargo);
        recargoCancelado.setHaber(recargo.getDebe());
        recargoCancelado.setFecha(recargo.getFecha());
        recargoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionrecargo"))));
        movimientoService.save(recargoCancelado);

        if (!CollectionUtils.isEmpty(recargo.getAplicados())) {
            for (MovimientoCargoAplicado aplicado : recargo.getAplicados()) {
                if (!aplicado.getCancelado() && aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagorecargo")))) {

                    aplicado.setCancelado(Boolean.TRUE);
                    movimientoService.update(aplicado);

                    MovimientoCargoAplicado pagoRecargoCancelado = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                    pagoRecargoCancelado.setAplicado(Boolean.TRUE);
                    pagoRecargoCancelado.setCancelado(Boolean.FALSE);
                    pagoRecargoCancelado.setCuenta(aplicado.getCuenta());
                    pagoRecargoCancelado.setDebe(aplicado.getHaber());
                    pagoRecargoCancelado.setFecha(aplicado.getFecha());
                    pagoRecargoCancelado.setPago(aplicado.getPago());
                    pagoRecargoCancelado.setImprimible(Boolean.TRUE);
                    pagoRecargoCancelado.setMovimientoCargo(recargo);
                    pagoRecargoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionpagorecargo"))));
                    movimientoService.save(pagoRecargoCancelado);

                    Cuenta cuentaSaldo = cuentaService.get(cargo.getCondominio(), cfg.getProperty("cuenta.saldos"));

                    if (cuentaSaldo != null) {
                        MovimientoSaldo saldoFavor = MovimientoFactory.newInstance(MovimientoSaldo.class);
                        saldoFavor.setContacto(aplicado.getPago().getContacto());
                        saldoFavor.setCuenta(cuentaSaldo);
                        saldoFavor.setFecha(aplicado.getFecha());
                        saldoFavor.setHaber(aplicado.getHaber());
                        saldoFavor.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("saldoafavorgenerado"))));
                        movimientoService.save(saldoFavor);
                    } else {
                        throw new ServiceException("EXC_CUENTA_SALDO");
                    }
                }
            }
        }
    }

    @Transactional
    private void cancelarCargo(MovimientoCargo movCargo, CargoDepartamento cargo) {

        movCargo.setCancelado(Boolean.TRUE);
        movimientoService.update(movCargo);

        MovimientoCargo cargoCancelado = MovimientoFactory.newInstance(MovimientoCargo.class);
        cargoCancelado.setCancelado(Boolean.FALSE);
        cargoCancelado.setCargo(cargo);
        cargoCancelado.setHaber(movCargo.getDebe());
        cargoCancelado.setFecha(movCargo.getFecha());
        cargoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacioncargo"))));
        movimientoService.save(cargoCancelado);

        if (!CollectionUtils.isEmpty(movCargo.getAplicados())) {
            for (MovimientoCargoAplicado aplicado : movCargo.getAplicados()) {
                if (!aplicado.getCancelado() && aplicado.getTipo().getId().equals(Long.valueOf(cfg.getProperty("pagocargo")))) {

                    aplicado.setCancelado(Boolean.TRUE);
                    movimientoService.update(aplicado);

                    MovimientoCargoAplicado pagoCargoCancelado = MovimientoFactory.newInstance(MovimientoCargoAplicado.class);
                    pagoCargoCancelado.setAplicado(Boolean.TRUE);
                    pagoCargoCancelado.setCancelado(Boolean.FALSE);
                    pagoCargoCancelado.setCuenta(aplicado.getCuenta());
                    pagoCargoCancelado.setFecha(aplicado.getFecha());
                    pagoCargoCancelado.setMovimientoCargo(movCargo);
                    pagoCargoCancelado.setDebe(aplicado.getHaber());
                    pagoCargoCancelado.setImprimible(Boolean.TRUE);
                    pagoCargoCancelado.setPago(aplicado.getPago());
                    pagoCargoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("cancelacionpagocargo"))));
                    movimientoService.save(pagoCargoCancelado);

                    Cuenta cuentaSaldo = cuentaService.get(cargo.getCondominio(), cfg.getProperty("cuenta.saldos"));
                    if (cuentaSaldo != null) {
                        MovimientoSaldo saldoFavor = MovimientoFactory.newInstance(MovimientoSaldo.class);
                        saldoFavor.setContacto(aplicado.getPago().getContacto());
                        saldoFavor.setCuenta(cuentaSaldo);
                        saldoFavor.setFecha(aplicado.getFecha());
                        saldoFavor.setHaber(aplicado.getHaber());
                        saldoFavor.setTipo((CatalogoTipoMovimiento)CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("saldoafavorgenerado"))));
                        movimientoService.save(saldoFavor);
                    } else {
                        throw new ServiceException("EXC_CUENTA_SALDO");
                    }
                }
            }
        }
    }
}
