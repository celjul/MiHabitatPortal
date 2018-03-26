package com.bstmexico.mihabitat.comunicacion.tareas.service.Impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunicacion.tareas.dao.TareaDao;
import com.bstmexico.mihabitat.comunicacion.tareas.model.Tarea;
import com.bstmexico.mihabitat.comunicacion.tareas.service.TareaService;
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
public class TareaServiceImpl implements TareaService {

    private static final Logger LOG = LoggerFactory
            .getLogger(TareaServiceImpl.class);

    @Autowired
    private TareaDao tareaDao;

    @Autowired
    private Validator validator;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<Tarea> getList(Condominio condominio) {
        Map params = new HashMap();
        params.put("condominio.id", condominio.getId());

        return tareaDao.search(params.entrySet());
    }

    @Override
    public void save(Tarea tarea) {
        try {
            Assert.notNull(tarea, "SEREX001");
            Set<ConstraintViolation<Tarea>> violations = validator
                    .validate(tarea);
            if (violations.isEmpty()) {
                tareaDao.save(tarea);
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
    public Tarea get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return tareaDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void update(Tarea tarea) {
        try {
            Assert.notNull(tarea, "SEREX001");
            Assert.notNull(tarea.getId(), "SEREX003");
            Set<ConstraintViolation<Tarea>> violations = validator
                    .validate(tarea);
            if (violations.isEmpty()) {
                tareaDao.update(tarea);
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
    public Collection<Tarea> search(Map map) {
        try {
            Assert.notNull(map, "SEREX001");
            return tareaDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void delete(Tarea tarea) {
        try {
            Assert.notNull(tarea, "SEREX001");
            Assert.notNull(tarea.getId(), "SEREX001");
            tareaDao.delete(tarea);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
