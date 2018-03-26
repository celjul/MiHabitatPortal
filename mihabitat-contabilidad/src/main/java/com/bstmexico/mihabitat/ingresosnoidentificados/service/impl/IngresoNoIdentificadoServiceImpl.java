package com.bstmexico.mihabitat.ingresosnoidentificados.service.impl;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.ingresosnoidentificados.dao.IngresoNoIdentificadoDao;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.CatalogoEstatusIngreso;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.MovimientoIngresoNoIdentificado;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.IngresoNoIdentificado;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.MovimientoIngresoNoIdentificado;
import com.bstmexico.mihabitat.ingresosnoidentificados.service.IngresoNoIdentificadoService;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

/**
 * @author Zo� Jonatan Tapia Hern�ndez
 * @version 1.0
 * @since 2015
 */
@Service
public class IngresoNoIdentificadoServiceImpl implements IngresoNoIdentificadoService {

    private static final Logger LOG = LoggerFactory
            .getLogger(IngresoNoIdentificadoServiceImpl.class);

    @Autowired
    private IngresoNoIdentificadoDao ingresoNoIdentificadoDao;

    @Autowired
    @Qualifier("pagoserviceproxy")
    private PagoService pagoService;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private Validator validator;

    private static Properties cfg;

    public IngresoNoIdentificadoServiceImpl() {
        try {
            cfg = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
        } catch (IOException e) {
            LOG.error("Error al cargar el archivo de propiedades");
        }
    }

    @Override
    public void guardar(IngresoNoIdentificado ingresoNoIdentificado) {
        try {
            Assert.notNull(ingresoNoIdentificado);
            ingresoNoIdentificado.setEstatus((CatalogoEstatusIngreso) CatalogoFactory.newInstance(
                    CatalogoEstatusIngreso.class, Long
                            .valueOf(cfg.getProperty("ingresoregistrado"))));
            Set<ConstraintViolation<IngresoNoIdentificado>> violations = validator
                    .validate(ingresoNoIdentificado);

            if (violations.isEmpty()) {
                generarMovimientos(ingresoNoIdentificado);
                createMovimientoIngresoNoIdentificado(ingresoNoIdentificado);
                ingresoNoIdentificadoDao.save(ingresoNoIdentificado);
                /*for(MovimientoIngresoNoIdentificado mini : ingresoNoIdentificado.getMovimientos()){
                    mini.setIngresoNoIdentificado(ingresoNoIdentificado);
                }*/
                /*ingresoNoIdentificadoDao.save(ingresoNoIdentificado);*/
            } else {
                String message = "Objeto no valido";
                ApplicationException ex1 = new ServiceException(message);
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
    public IngresoNoIdentificado get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return ingresoNoIdentificadoDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void actualizar(IngresoNoIdentificado ingresoNoIdentificado) {
        try {
            Assert.notNull(ingresoNoIdentificado);
            Assert.notNull(ingresoNoIdentificado.getId());
            Set<ConstraintViolation<IngresoNoIdentificado>> violations = validator
                    .validate(ingresoNoIdentificado);
            IngresoNoIdentificado ingresoNoIdentificadoBD = ingresoNoIdentificadoDao.get(ingresoNoIdentificado.getId());
            if (violations.isEmpty()) {
                actualizarMovimientos(ingresoNoIdentificado, ingresoNoIdentificadoBD);
                ingresoNoIdentificadoDao.update(ingresoNoIdentificado);
            } else {
                String message = "Objeto no valido";
                ApplicationException ex1 = new ServiceException(message);
                LOG.warn(ex1.getMessage() + violations);
                throw ex1;
            }
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional
    @Override
    public void cancelar(IngresoNoIdentificado ingresoNoIdentificado) {
        try {
            Assert.notNull(ingresoNoIdentificado);
            Assert.notNull(ingresoNoIdentificado.getId());

            ingresoNoIdentificado = ingresoNoIdentificadoDao.get(ingresoNoIdentificado.getId());
            ingresoNoIdentificado.setEstatus((CatalogoEstatusIngreso) CatalogoFactory.newInstance(
                    CatalogoEstatusIngreso.class, Long
                            .valueOf(cfg.getProperty("ingresocancelado"))));
            /*MovimientoIngresoNoIdentificado primerMovimiento =
                    ingresoNoIdentificado.getMovimientos().iterator().hasNext() ?
                            ingresoNoIdentificado.getMovimientos().iterator().next() : null;*/

            Collection<MovimientoIngresoNoIdentificado> movimientosCancelacion = new ArrayList<>();

            for (MovimientoIngresoNoIdentificado mini : ingresoNoIdentificado.getMovimientos()) {

                mini.setCancelado(Boolean.TRUE);

                MovimientoIngresoNoIdentificado ingresoNoIdentificadoCancelado = MovimientoFactory
                        .newInstance(MovimientoIngresoNoIdentificado.class);
                ingresoNoIdentificadoCancelado.setAplicado(Boolean.TRUE);
                ingresoNoIdentificadoCancelado.setCancelado(Boolean.FALSE);
                ingresoNoIdentificadoCancelado.setCuenta(mini.getCuenta());
                ingresoNoIdentificadoCancelado.setDebe(mini.getHaber());
                ingresoNoIdentificadoCancelado.setFecha(mini.getFecha());
                ingresoNoIdentificadoCancelado.setIngresoNoIdentificado(ingresoNoIdentificado);

                if(mini.getTipo().getId().equals(Long
                        .valueOf(cfg
                                .getProperty("ingresonoidentificado")))) {
                    ingresoNoIdentificadoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory
                            .newInstance(CatalogoTipoMovimiento.class, Long
                                    .valueOf(cfg
                                            .getProperty("ingresonoidentificadocancelado"))));
                } else {
                    ingresoNoIdentificadoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory
                            .newInstance(CatalogoTipoMovimiento.class, Long
                                    .valueOf(cfg
                                            .getProperty("ingresonoidentificadobancocancelado"))));
                }
                movimientosCancelacion.add(ingresoNoIdentificadoCancelado);
            }
            ingresoNoIdentificado.getMovimientos().addAll(movimientosCancelacion);
            ingresoNoIdentificadoDao.update(ingresoNoIdentificado);
            /*}*/

        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional
    @Override
    public void asignar(IngresoNoIdentificado ingresoNoIdentificado, Pago pago, Boolean aprobado) {
        try {
            Assert.notNull(ingresoNoIdentificado);
            Assert.notNull(ingresoNoIdentificado.getId());

            ingresoNoIdentificado = ingresoNoIdentificadoDao.get(ingresoNoIdentificado.getId());
            ingresoNoIdentificado.setEstatus((CatalogoEstatusIngreso) CatalogoFactory.newInstance(
                    CatalogoEstatusIngreso.class, Long
                            .valueOf(cfg.getProperty("ingresoasignado"))));

            if (pago.getContacto() != null && pago.getContacto().getNombre() == null) {
                pago.setContacto(contactoService.get(pago.getContacto().getId()));
            }


            ingresoNoIdentificado.setAplicadoEn("Identificado como Abono de " + pago.getContacto().getNombre() +
                    " " + pago.getContacto().getApellidoPaterno() + " " + pago.getContacto().getApellidoMaterno());

            Collection<MovimientoIngresoNoIdentificado> movimientosCancelacion = new ArrayList<>();

            for (MovimientoIngresoNoIdentificado mini : ingresoNoIdentificado.getMovimientos()) {

                mini.setCancelado(Boolean.TRUE);

                MovimientoIngresoNoIdentificado ingresoNoIdentificadoCancelado = MovimientoFactory
                        .newInstance(MovimientoIngresoNoIdentificado.class);
                ingresoNoIdentificadoCancelado.setAplicado(Boolean.TRUE);
                ingresoNoIdentificadoCancelado.setCancelado(Boolean.FALSE);
                ingresoNoIdentificadoCancelado.setCuenta(mini.getCuenta());
                ingresoNoIdentificadoCancelado.setDebe(mini.getHaber());
                ingresoNoIdentificadoCancelado.setFecha(mini.getFecha());
                ingresoNoIdentificadoCancelado.setIngresoNoIdentificado(ingresoNoIdentificado);

                if(mini.getTipo().getId().equals(Long
                        .valueOf(cfg
                                .getProperty("ingresonoidentificado")))) {
                    ingresoNoIdentificadoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory
                            .newInstance(CatalogoTipoMovimiento.class, Long
                                    .valueOf(cfg
                                            .getProperty("ingresonoidentificadocancelado"))));
                } else {
                    ingresoNoIdentificadoCancelado.setTipo((CatalogoTipoMovimiento) CatalogoFactory
                            .newInstance(CatalogoTipoMovimiento.class, Long
                                    .valueOf(cfg
                                            .getProperty("ingresonoidentificadobancocancelado"))));
                }
                movimientosCancelacion.add(ingresoNoIdentificadoCancelado);
            }
            ingresoNoIdentificado.getMovimientos().addAll(movimientosCancelacion);

            ingresoNoIdentificadoDao.update(ingresoNoIdentificado);
            pagoService.save(pago, aprobado);

        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<IngresoNoIdentificado> listar(Condominio condominio) {
        try {
            Assert.notNull(condominio);
            Assert.notNull(condominio.getId());
            Map map = new HashMap();
            map.put("condominio", condominio);
            return (List<IngresoNoIdentificado>) ingresoNoIdentificadoDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }


    /**
     *
     * @param ingresoNoIdentificado OBJETO QUE CONTIENE LOS DATOS ACTUALIZADOS
     * @param ingresoNoIdentificadoBD OBJETO QUE CONTIENE LOS DATOS QUE SE ENCUENTRAN EN LA BD Y QUE SE UTILIZARA
     *                                PARA HACER LA COMPARACION ENTRE LOS MOVIMIENTOS
     */
    private void actualizarMovimientos(IngresoNoIdentificado ingresoNoIdentificado, IngresoNoIdentificado ingresoNoIdentificadoBD) {
        /**
         * Se hace la comparación entre los movimientos que ya existen en la base de datos con los nuevos movimientos
         * y se comparan mediante id ya que es lo único que los movimientos nuevos tienen para identificar cada uno de
         * los movimientos y en caso de ser iguales dentro se revisa de qué tipo de movimiento son los de la base de
         * datos para así asignarlos a los que contendran la actualizacion ya que solo pueden ser de dos tipos de movimiento
         * y así asignarle el correspondiente y la cuenta que le corresponde a dicho registro.
         */
        for(MovimientoIngresoNoIdentificado movimiento : ingresoNoIdentificado.getMovimientos()) {
            movimiento.setAplicado(Boolean.TRUE);
            movimiento.setCancelado(Boolean.FALSE);
            movimiento.setHaber(ingresoNoIdentificado.getMonto());
            movimiento.setFecha(ingresoNoIdentificado.getFecha());
            for(MovimientoIngresoNoIdentificado movimientoBD : ingresoNoIdentificadoBD.getMovimientos()) {
                if (movimiento.getId().equals(movimientoBD.getId())){
                    if (movimientoBD.getTipo().equals((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("ingresonoidentificado"))))){
                        movimiento.setTipo(movimientoBD.getTipo());
                        movimiento.setCuenta(ingresoNoIdentificado.getCuentaIngreso());
                    }
                    if (movimientoBD.getTipo().equals((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("ingresonoidentificadobanco"))))){
                        movimiento.setCuenta(ingresoNoIdentificado.getCuentaBanco());
                        movimiento.setTipo(movimientoBD.getTipo());
                    }
                }
            }
        }
    }

    private void generarMovimientos(IngresoNoIdentificado ingresoNoIdentificado) {
        for (MovimientoIngresoNoIdentificado movimiento : ingresoNoIdentificado.getMovimientos()) {
            movimiento.setAplicado(Boolean.TRUE);
            movimiento.setCancelado(Boolean.FALSE);
            movimiento.setCuenta(ingresoNoIdentificado.getCuentaIngreso());
            movimiento.setIngresoNoIdentificado(ingresoNoIdentificado);
            movimiento.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("ingresonoidentificado"))));
            movimiento.setHaber(ingresoNoIdentificado.getMonto());
            movimiento.setFecha(ingresoNoIdentificado.getFecha());
            /*ingresoNoIdentificado.getMovimientos().add(movimiento);*/
            /*movimientoService.save(movimiento);*/
        }
    }

    private void createMovimientoIngresoNoIdentificado(IngresoNoIdentificado ingresoNoIdentificado) {
        MovimientoIngresoNoIdentificado movimientoIngresoNoIdentificado = MovimientoFactory.newInstance(MovimientoIngresoNoIdentificado.class);
        movimientoIngresoNoIdentificado.setAplicado(Boolean.TRUE);
        movimientoIngresoNoIdentificado.setCancelado(Boolean.FALSE);
        movimientoIngresoNoIdentificado.setCuenta(ingresoNoIdentificado.getCuentaBanco());
        movimientoIngresoNoIdentificado.setFecha(ingresoNoIdentificado.getFecha());
        movimientoIngresoNoIdentificado.setHaber(ingresoNoIdentificado.getMonto());
        movimientoIngresoNoIdentificado.setIngresoNoIdentificado(ingresoNoIdentificado);
        movimientoIngresoNoIdentificado.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("ingresonoidentificadobanco"))));
        /*movimientoService.save(movimientoIngresoNoIdentificado);*/
        ingresoNoIdentificado.getMovimientos().add(movimientoIngresoNoIdentificado);
    }
}
