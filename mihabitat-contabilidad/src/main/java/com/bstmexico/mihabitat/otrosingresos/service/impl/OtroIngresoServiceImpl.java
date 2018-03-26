package com.bstmexico.mihabitat.otrosingresos.service.impl;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.otrosingresos.dao.OtroIngresoDao;
import com.bstmexico.mihabitat.otrosingresos.model.ConceptoIngreso;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoConceptoOtroIngreso;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoOtroIngreso;
import com.bstmexico.mihabitat.otrosingresos.model.OtroIngreso;
import com.bstmexico.mihabitat.otrosingresos.service.OtroIngresoService;
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
public class OtroIngresoServiceImpl implements OtroIngresoService {

    private static final Logger LOG = LoggerFactory
            .getLogger(OtroIngresoServiceImpl.class);

    @Autowired
    private OtroIngresoDao otroIngresoDao;

    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;

    @Autowired
    private Validator validator;

    private static Properties cfg;

    public OtroIngresoServiceImpl() {
        try {
            cfg = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
        } catch (IOException e) {
            LOG.error("Error al cargar el archivo de propiedades");
        }
    }

    @Override
    public void guardar(OtroIngreso otroIngreso) {
        try {
            Assert.notNull(otroIngreso);
            Set<ConstraintViolation<OtroIngreso>> violations = validator
                    .validate(otroIngreso);

            if (violations.isEmpty()) {
                otroIngreso.getMovimientoOtroIngreso().setHaber(otroIngreso.getHaber());
                otroIngreso.getMovimientoOtroIngreso().setOtroIngreso(otroIngreso);
                for (ConceptoIngreso conceptoIngreso : otroIngreso.getConceptos()) {
                    conceptoIngreso.getMovimientoConceptoOtroIngreso().setConceptoIngreso(conceptoIngreso);
                }
                otroIngresoDao.save(otroIngreso);
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
    public OtroIngreso editar(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return otroIngresoDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void actualizar(OtroIngreso otroIngreso) {
        try {

            Assert.notNull(otroIngreso);
            Assert.notNull(otroIngreso.getId());
            Set<ConstraintViolation<OtroIngreso>> violations = validator
                    .validate(otroIngreso);

            if (violations.isEmpty()) {
                otroIngreso.getMovimientoOtroIngreso().setHaber(otroIngreso.getHaber());
                otroIngreso.getMovimientoOtroIngreso().setOtroIngreso(otroIngreso);
                for (ConceptoIngreso conceptoIngreso : otroIngreso.getConceptos()) {
                    conceptoIngreso.getMovimientoConceptoOtroIngreso().setConceptoIngreso(conceptoIngreso);
                }
                //Se deben borrar los movimientos de los conceptos que fueron eliminados
                OtroIngreso anterior = otroIngresoDao.get(otroIngreso.getId());
                anterior.getConceptos().removeAll(otroIngreso.getConceptos());
                for (ConceptoIngreso conceptoIngresoAnterior : anterior.getConceptos()) {
                    movimientoService.delete(conceptoIngresoAnterior.getMovimientoConceptoOtroIngreso());
                }
                otroIngresoDao.update(otroIngreso);
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
    public void cancelar(OtroIngreso otroIngreso) {
        try {
            Assert.notNull(otroIngreso);
            Assert.notNull(otroIngreso.getId());

            otroIngreso = otroIngresoDao.get(otroIngreso.getId());
            otroIngreso.getMovimientoOtroIngreso().setCancelado(Boolean.TRUE);

            MovimientoOtroIngreso otroIngresoCancelado = MovimientoFactory
                    .newInstance(MovimientoOtroIngreso.class);
            otroIngresoCancelado.setAplicado(Boolean.TRUE);
            otroIngresoCancelado.setCancelado(Boolean.FALSE);
            otroIngresoCancelado.setCuenta(otroIngreso.getMovimientoOtroIngreso().getCuenta());
            otroIngresoCancelado.setDebe(otroIngreso.getMovimientoOtroIngreso().getHaber());
            otroIngresoCancelado.setFecha(otroIngreso.getFecha());
            otroIngresoCancelado
                    .setTipo((CatalogoTipoMovimiento) CatalogoFactory
                            .newInstance(CatalogoTipoMovimiento.class, Long
                                    .valueOf(cfg
                                            .getProperty("pagocfdicancelado"))));
            movimientoService.save(otroIngresoCancelado);

            for (ConceptoIngreso conceptoIngreso : otroIngreso.getConceptos()) {
                conceptoIngreso.getMovimientoConceptoOtroIngreso().setCancelado(Boolean.TRUE);

                MovimientoConceptoOtroIngreso conceptoCancelado = MovimientoFactory
                        .newInstance(MovimientoConceptoOtroIngreso.class);
                conceptoCancelado.setAplicado(Boolean.TRUE);
                conceptoCancelado.setCancelado(Boolean.FALSE);
                conceptoCancelado.setCuenta(conceptoIngreso.getMovimientoConceptoOtroIngreso()
                        .getCuenta());
                conceptoCancelado.setFecha(conceptoIngreso.getMovimientoConceptoOtroIngreso().getFecha());
                conceptoCancelado.setDebe(conceptoIngreso.getMovimientoConceptoOtroIngreso()
                        .getHaber());
                conceptoCancelado
                        .setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(
                                CatalogoTipoMovimiento.class, Long.valueOf(cfg
                                        .getProperty("pagocfdicancelado"))));
                movimientoService.save(conceptoCancelado);
            }

            otroIngresoDao.update(otroIngreso);

        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<OtroIngreso> listar(Condominio condominio) {
        try {
            Assert.notNull(condominio);
            Assert.notNull(condominio.getId());
            Map map = new HashMap();
            map.put("condominio", condominio);
            return (List<OtroIngreso>) otroIngresoDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
