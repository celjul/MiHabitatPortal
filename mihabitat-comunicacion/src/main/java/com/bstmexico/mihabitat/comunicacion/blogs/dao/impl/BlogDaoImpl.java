package com.bstmexico.mihabitat.comunicacion.blogs.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.BlogDao;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.TemaDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Blog;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by Pegasus on 10/08/2015.
 */
@Repository
public class BlogDaoImpl extends GenericDaoImpl<Blog, Long> implements
        BlogDao {

    @Autowired
    private TemaDao temaDao;

    @Transactional(readOnly = true)
    @Override
    public Blog get(Long id) {
        Blog blog = super.get(id);
        blog.getTemas().size();
        return blog;
    }

    @Transactional(readOnly = true)
    @Override
    public Blog getTemasCondominio(Long id, Condominio condominio) {
        Blog blog = super.get(id);
        blog.getTemas().size();

        Collection<Tema> temas = new ArrayList<>();

        if(!CollectionUtils.isEmpty(blog.getTemas())) {
            for (Tema tema : blog.getTemas()) {
                Hibernate.initialize(tema.getCondominio());
                tema.getPosts().size();
                if (tema.getCondominio() == null){
                    temas.add(tema);
                } else if (condominio != null && tema.getCondominio() != null && tema.getCondominio().getId() != null) {
                    if(tema.getCondominio().getId() == condominio.getId()) {
                        temas.add(tema);
                        /*blog.getTemas().remove(tema);*/
                    }
                }
                /*if ((condominio != null && tema.getCondominio() != null && tema.getCondominio().getId() != null) && (tema.getCondominio().getId() == condominio.getId())) {
                    tema.getPosts().size();
                    temas.add(tema);
                }*/
            }
            blog.setTemas(temas);
        }
        return blog;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Blog> search(Set<Map.Entry> set) {
        Collection<Blog> blogs = super.search(set);
        for(Blog blog : blogs) {
            blog.getTemas().size();
        }
        return blogs;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Blog> getList(Condominio condominio) {
        Collection<Blog> blogs = super.getList();

        for(Blog blog : blogs) {
            blog.getTemas().size();
            Collection<Tema> temasTemp = new ArrayList<>();
            for(Tema tema : blog.getTemas()) {
                Hibernate.initialize(tema.getCondominio());
                tema.getPosts().size();
                if (tema.getCondominio() == null){
                    temasTemp.add(tema);
                } else if (condominio != null && tema.getCondominio() != null && tema.getCondominio().getId() != null) {
                    if(tema.getCondominio().getId() == condominio.getId()) {
                        temasTemp.add(tema);
                        /*blog.getTemas().remove(tema);*/
                    }
                }
            }
            blog.setTemas(temasTemp);
        }
        return blogs;
    }
}
