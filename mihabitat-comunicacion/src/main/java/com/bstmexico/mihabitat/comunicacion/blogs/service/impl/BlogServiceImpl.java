package com.bstmexico.mihabitat.comunicacion.blogs.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.BlogDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Blog;
import com.bstmexico.mihabitat.comunicacion.blogs.model.CatalogoTipoBlog;
import com.bstmexico.mihabitat.comunicacion.blogs.service.BlogService;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaService;
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
 * Created by Pegasus on 10/08/2015.
 */
@Service
public class BlogServiceImpl implements BlogService {

    private static final Logger LOG = LoggerFactory
            .getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private Validator validator;

    @Override
    public Collection<Blog> getList(Condominio condominio) {
        return blogDao.getList(condominio);
    }

    @Override
    public Blog getTemasCondominio(Long id, Condominio condominio) {
        return blogDao.getTemasCondominio(id, condominio);
    }

    @Override
    public void save(Blog blog) {
        try {
            Assert.notNull(blog, "SEREX001");
            Set<ConstraintViolation<Blog>> violations = validator
                    .validate(blog);
            if (violations.isEmpty()) {
                blogDao.save(blog);
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
    public Blog get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return blogDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }


    @Override
    public void update(Blog blog) {
        try {
            Assert.notNull(blog, "SEREX001");
            Assert.notNull(blog.getId(), "SEREX003");
            Set<ConstraintViolation<Blog>> violations = validator
                    .validate(blog);
            if (violations.isEmpty()) {
                blogDao.update(blog);
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
    public Collection<Blog> search(Map map) {
        try {
            Assert.notNull(map, "SEREX001");
            return blogDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
