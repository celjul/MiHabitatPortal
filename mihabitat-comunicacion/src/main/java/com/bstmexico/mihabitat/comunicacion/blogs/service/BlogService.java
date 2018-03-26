package com.bstmexico.mihabitat.comunicacion.blogs.service;

import com.bstmexico.mihabitat.comunicacion.blogs.model.Blog;
import com.bstmexico.mihabitat.comunicacion.blogs.model.CatalogoTipoBlog;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Pegasus on 10/08/2015.
 */
public interface BlogService {
    void save(Blog blog);
    Blog get(Long id);
    void update(Blog blog);
    Collection<Blog> search(Map map);
    Collection<Blog> getList(Condominio condominio);
    Blog getTemasCondominio(Long id, Condominio condominio);
}
