package com.bstmexico.mihabitat.presupuestos.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.dao.CuentaDao;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.presupuestos.dao.PresupuestoDao;
import com.bstmexico.mihabitat.presupuestos.factory.PresupuestoFactory;
import com.bstmexico.mihabitat.presupuestos.model.Presupuesto;
import com.bstmexico.mihabitat.presupuestos.service.PresupuestoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

/**
 * Created by Pegasus on 08/06/2015.
 */
@Service
public class PresupuestoServiceImpl implements PresupuestoService {
    private static final Logger LOG = LoggerFactory
            .getLogger(PresupuestoServiceImpl.class);

    @Autowired
    private PresupuestoDao presupuestoDao;

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private Validator validator;

    public void save(Presupuesto presupuesto){
        try {
            Assert.notNull(presupuesto, "SEREX001");
            Set<ConstraintViolation<Presupuesto>> violations = validator
                    .validate(presupuesto);
            if (violations.isEmpty()) {
                presupuestoDao.save(presupuesto);
            } else {
                String message = "SEREX002";
                ApplicationException ex1 = new ServiceException(message,
                        violations);
                LOG.warn(ex1.getMessage() + violations, violations);
                throw ex1;
            }

        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    public Presupuesto get(Long id){
        try {
            Assert.notNull(id, "SEREX003");
            return presupuestoDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    public Presupuesto get(Cuenta cuenta, Integer anio){
        try {
            Assert.notNull(cuenta, "SEREX003");
            Assert.notNull(cuenta.getId(), "SEREX003");
            Assert.notNull(anio, "SEREX003");
            Map map = new HashMap();
            map.put("cuenta", cuenta);
            map.put("anio", anio);
            Collection<Presupuesto> presupuestos = presupuestoDao.search(map.entrySet());
            return presupuestos.iterator().hasNext()?presupuestos.iterator().next():null;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    public Collection<Presupuesto> get(Integer anio){
        try {
            Assert.notNull(anio, "SEREX003");
            Map map = new HashMap();
            map.put("anio", anio);
            return presupuestoDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    public void update(Presupuesto presupuesto){
        try {
            Assert.notNull(presupuesto, "SEREX001");
            Assert.notNull(presupuesto.getId(), "SEREX003");
            Set<ConstraintViolation<Presupuesto>> violations = validator
                    .validate(presupuesto);
            if (violations.isEmpty()) {
                presupuestoDao.update(presupuesto);
            } else {
                String message = "SEREX002";
                ApplicationException ex1 = new ServiceException(message,
                        violations);
                LOG.warn(ex1.getMessage(), violations);
                throw ex1;
            }
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings("rawtypes")
    public Collection<Presupuesto> search(Map map){
        try {
            Assert.notNull(map, "SEREX001");
            return presupuestoDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Collection<Presupuesto> getList(Condominio condominio, Integer anio) {
        Collection<Cuenta> cuentas = cuentaDao.getList(condominio);
        Collection<Presupuesto> presupuestos = new ArrayList<>();
        Presupuesto presupuesto = null;
        for(Cuenta cta : cuentas) {
            presupuesto = presupuestoDao.get(cta, anio);
            if(presupuesto == null){
                presupuesto = PresupuestoFactory.newInstance();
                presupuesto.setCuenta(cta);
                presupuesto.setAnio(anio);
            }
            presupuestos.add(presupuesto);
        }
        return presupuestos;
    }

}
