package com.bstmexico.mihabitat.comunicacion.blogs.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.TemaRevisadoDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaRevisado;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaRevisadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pegasus on 27/07/2015.
 */
@Service
public class TemaRevisadoServiceImpl implements TemaRevisadoService {

    private static final Logger LOG = LoggerFactory
            .getLogger(TemaRevisadoServiceImpl.class);

    @Autowired
    private TemaRevisadoDao temaRevisadoDao;

    @Autowired
    private Validator validator;

    @Override
    public void save(TemaRevisado temaRevisado) {
        try {
            Assert.notNull(temaRevisado, "SEREX001");
            Set<ConstraintViolation<TemaRevisado>> violations = validator
                    .validate(temaRevisado);
            if (violations.isEmpty()) {
                temaRevisadoDao.save(temaRevisado);
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
    public TemaRevisado get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return temaRevisadoDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void update(TemaRevisado temaRevisado) {
        try {
            Assert.notNull(temaRevisado, "SEREX001");
            Assert.notNull(temaRevisado.getId(), "SEREX003");
            Set<ConstraintViolation<TemaRevisado>> violations = validator
                    .validate(temaRevisado);
            if (violations.isEmpty()) {
                temaRevisadoDao.update(temaRevisado);
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
    public Collection<TemaRevisado> search(Map map) {
        try {
            Assert.notNull(map, "SEREX001");
            return temaRevisadoDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
