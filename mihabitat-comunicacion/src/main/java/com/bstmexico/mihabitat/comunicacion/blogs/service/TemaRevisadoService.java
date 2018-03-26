package com.bstmexico.mihabitat.comunicacion.blogs.service;

import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaRevisado;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Pegasus on 26/07/2015.
 */
public interface TemaRevisadoService {

    void save(TemaRevisado temaRevisado);
    TemaRevisado get(Long id);
    void update(TemaRevisado temaRevisado);
    Collection<TemaRevisado> search(Map map);
    }
