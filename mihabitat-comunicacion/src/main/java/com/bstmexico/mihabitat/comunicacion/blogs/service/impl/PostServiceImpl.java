package com.bstmexico.mihabitat.comunicacion.blogs.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.PostDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Post;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.blogs.service.PostService;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaService;
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
 * Created by Pegasus on 29/07/2015.
 */
@Service("postservicedefault")
public class PostServiceImpl implements PostService {

    private static final Logger LOG = LoggerFactory
            .getLogger(PostServiceImpl.class);

    @Autowired
    private Validator validator;
    
    @Autowired
    private PostDao postDao;

    @Autowired
    private TemaService temaService;

    @Override
    public Tema save(Post post) {
        try {
            Assert.notNull(post, "SEREX001");
            Set<ConstraintViolation<Post>> violations = validator
                    .validate(post);
            if (violations.isEmpty()) {
                postDao.save(post);
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
        return temaService.getConArchivos(post.getTema().getId());
    }

    @Override
    public Post get(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return postDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void update(Post post) {
        try {
            Assert.notNull(post, "SEREX001");
            Assert.notNull(post.getId(), "SEREX003");
            Set<ConstraintViolation<Post>> violations = validator
                    .validate(post);
            if (violations.isEmpty()) {
                postDao.update(post);
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
    public Collection<Post> search(Map map) {
        try {
            Assert.notNull(map, "SEREX001");
            return postDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
