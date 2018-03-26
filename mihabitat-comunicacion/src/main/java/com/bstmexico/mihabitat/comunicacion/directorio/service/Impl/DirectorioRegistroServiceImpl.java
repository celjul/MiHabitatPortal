package com.bstmexico.mihabitat.comunicacion.directorio.service.Impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunicacion.directorio.dao.DirectorioRegistroDao;
import com.bstmexico.mihabitat.comunicacion.directorio.model.DirectorioRegistro;
import com.bstmexico.mihabitat.comunicacion.directorio.service.DirectorioRegistroService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Zoe on 25/01/2016.
 */
@Service
public class DirectorioRegistroServiceImpl implements DirectorioRegistroService {

    private static final Logger LOG = LoggerFactory
            .getLogger(DirectorioRegistroServiceImpl.class);

    @Autowired
    private Validator validator;

    @Autowired
    private DirectorioRegistroDao directorioRegistroDao;

    @Override
    public void save(DirectorioRegistro directorioRegistro) {
        try {
            Assert.notNull(directorioRegistro, "SEREX001");
            Set<ConstraintViolation<DirectorioRegistro>> violations = validator
                    .validate(directorioRegistro);
            if (violations.isEmpty()) {
                directorioRegistroDao.save(directorioRegistro);
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
    public void update(DirectorioRegistro directorioRegistro) {
        try {
            Assert.notNull(directorioRegistro, "SEREX001");
            Assert.notNull(directorioRegistro.getId(), "SEREX003");
            Set<ConstraintViolation<DirectorioRegistro>> violations = validator
                    .validate(directorioRegistro);
            if (violations.isEmpty()) {
                directorioRegistroDao.update(directorioRegistro);
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
    public DirectorioRegistro get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return directorioRegistroDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<DirectorioRegistro> getList(Condominio condominio) {
        try {
            Assert.notNull(condominio, "SEREX001");
            Assert.notNull(condominio.getId(), "SEREX001");
            return directorioRegistroDao.getList(condominio);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
