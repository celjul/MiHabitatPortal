package com.bstmexico.mihabitat.comunicacion.tareas.service.Impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunicacion.tareas.dao.RecordatorioAdministracionDao;
import com.bstmexico.mihabitat.comunicacion.tareas.model.RecordatorioAdministracion;
import com.bstmexico.mihabitat.comunicacion.tareas.service.RecordatorioAdministracionService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Service
public class RecordatorioAdministracionServiceImpl implements RecordatorioAdministracionService {

    private static final Logger LOG = LoggerFactory
            .getLogger(TareaServiceImpl.class);

    @Autowired
    private RecordatorioAdministracionDao recordatorioAdministracionDao;

    @Autowired
    private Validator validator;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<RecordatorioAdministracion> getList(Condominio condominio) {
        Map params = new HashMap();
        params.put("condominio.id", condominio.getId());

        return recordatorioAdministracionDao.search(params.entrySet());
    }

    @Override
    public void save(RecordatorioAdministracion recordatorioAdministracion) {
        try {
            Assert.notNull(recordatorioAdministracion, "SEREX001");
            Set<ConstraintViolation<RecordatorioAdministracion>> violations = validator
                    .validate(recordatorioAdministracion);
            if (violations.isEmpty()) {
                recordatorioAdministracionDao.save(recordatorioAdministracion);
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

    @Override
    public RecordatorioAdministracion get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return recordatorioAdministracionDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void update(RecordatorioAdministracion recordatorioAdministracion) {
        try {
            Assert.notNull(recordatorioAdministracion, "SEREX001");
            Assert.notNull(recordatorioAdministracion.getId(), "SEREX003");
            Set<ConstraintViolation<RecordatorioAdministracion>> violations = validator
                    .validate(recordatorioAdministracion);
            if (violations.isEmpty()) {
                recordatorioAdministracionDao.update(recordatorioAdministracion);
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<RecordatorioAdministracion> search(Map map) {
        try {
            Assert.notNull(map, "SEREX001");
            return recordatorioAdministracionDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void delete(RecordatorioAdministracion recordatorioAdministracion) {
        try {
            Assert.notNull(recordatorioAdministracion, "SEREX001");
            Assert.notNull(recordatorioAdministracion.getId(), "SEREX001");
            Set<ConstraintViolation<RecordatorioAdministracion>> violations = validator
                    .validate(recordatorioAdministracion);
            if (violations.isEmpty()) {
                recordatorioAdministracionDao.delete(recordatorioAdministracion);
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
}
