package com.bstmexico.mihabitat.comunicacion.blogs.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Blog;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;

/**
 * Created by Pegasus on 10/08/2015.
 */
public interface BlogDao  extends GenericDao<Blog, Long> {

    Collection<Blog> getList(Condominio condominio);
    Blog getTemasCondominio(Long id, Condominio condominio);

}
