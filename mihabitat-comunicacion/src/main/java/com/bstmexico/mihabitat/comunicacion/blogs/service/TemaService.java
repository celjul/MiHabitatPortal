package com.bstmexico.mihabitat.comunicacion.blogs.service;

import com.bstmexico.mihabitat.comunicacion.blogs.model.Blog;
import com.bstmexico.mihabitat.comunicacion.blogs.model.CatalogoEstatusIncidencia;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaIncidencia;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Pegasus on 26/07/2015.
 */
public interface TemaService {

    Collection<Tema> getList(Condominio condominio, Blog blog);
    Collection<Tema> getList(Condominio condominio);
    void save(Tema incidencia);
    Tema get(Long id);
    void update(Tema incidencia);
    Collection<Tema> search(Map map);
    Tema getConArchivos(Long id);
    Collection<TemaIncidencia> getIncidenciasByStatus(Contacto contacto, CatalogoEstatusIncidencia estatus);
    Collection<TemaIncidencia> getIncidenciasByStatus(Condominio condominio, CatalogoEstatusIncidencia estatus);
    Collection<Tema> getListConPosts(Condominio condominio);
}
