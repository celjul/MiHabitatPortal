package com.bstmexico.mihabitat.comunicacion.blogs.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Blog;
import com.bstmexico.mihabitat.comunicacion.blogs.model.CatalogoEstatusIncidencia;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaIncidencia;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;

import java.util.Collection;

/**
 * Created by Pegasus on 26/07/2015.
 */
public interface TemaDao extends GenericDao<Tema, Long> {

    Tema getConArchivos(Long id);
    Collection<Tema> getList(Condominio condominio, Blog blog);
    Collection<Tema> getList(Condominio condominio);
    Collection<TemaIncidencia> getIncidenciasByStatus(Contacto contacto, CatalogoEstatusIncidencia estatus);
    Collection<TemaIncidencia> getIncidenciasByStatus(Condominio condominio, CatalogoEstatusIncidencia estatus);
    Collection<Tema> getListConPosts(Condominio condominio);

}
