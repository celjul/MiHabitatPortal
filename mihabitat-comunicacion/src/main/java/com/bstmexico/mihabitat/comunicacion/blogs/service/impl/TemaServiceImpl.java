package com.bstmexico.mihabitat.comunicacion.blogs.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.TemaDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Blog;
import com.bstmexico.mihabitat.comunicacion.blogs.model.CatalogoEstatusIncidencia;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaIncidencia;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
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
 * Created by Pegasus on 27/07/2015.
 */
@Service
public class TemaServiceImpl implements TemaService {

    private static final Logger LOG = LoggerFactory
            .getLogger(TemaServiceImpl.class);

    @Autowired
    private TemaDao temaDao;

    @Autowired
    private Validator validator;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<Tema> getList(Condominio condominio, Blog blog) {
        try {
            Assert.notNull(blog, "SEREX001");
            Assert.notNull(blog.getId(), "SEREX001");
            return temaDao.getList(condominio, blog);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<Tema> getList(Condominio condominio) {
        try {
            return temaDao.getList(condominio);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<Tema> getListConPosts(Condominio condominio) {
        try {
            return temaDao.getListConPosts(condominio);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void save(Tema tema) {
        try {
            Assert.notNull(tema, "SEREX001");
            Set<ConstraintViolation<Tema>> violations = validator
                    .validate(tema);
            if (violations.isEmpty()) {
                temaDao.save(tema);
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
    public Tema get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return temaDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Tema getConArchivos(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return temaDao.getConArchivos(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void update(Tema tema) {
        try {
            Assert.notNull(tema, "SEREX001");
            Assert.notNull(tema.getId(), "SEREX003");
            Set<ConstraintViolation<Tema>> violations = validator
                    .validate(tema);
            if (violations.isEmpty()) {
                temaDao.update(tema);
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
    public Collection<Tema> search(Map map) {
        try {
            Assert.notNull(map, "SEREX001");
            return temaDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<TemaIncidencia> getIncidenciasByStatus(Contacto contacto, CatalogoEstatusIncidencia estatus) {
        try {
            Assert.notNull(contacto, "SEREX001");
            Assert.notNull(estatus, "SEREX001");
            Assert.notNull(contacto.getUsuario(), "SEREX001");
            return temaDao.getIncidenciasByStatus(contacto, estatus);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Collection<TemaIncidencia> getIncidenciasByStatus(Condominio condominio, CatalogoEstatusIncidencia estatus) {
        try {
            Assert.notNull(condominio, "SEREX001");
            Assert.notNull(estatus, "SEREX001");
            return temaDao.getIncidenciasByStatus(condominio, estatus);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
