package com.bstmexico.mihabitat.comunicacion.directorio.service;

import com.bstmexico.mihabitat.comunicacion.directorio.model.DirectorioRegistro;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;

/**
 * Created by Zoe on 25/01/2016.
 */
public interface DirectorioRegistroService {

    void save(DirectorioRegistro directorioRegistro);
    void update(DirectorioRegistro directorioRegistro);
    DirectorioRegistro get(Long id);
    Collection<DirectorioRegistro> getList(Condominio condominio);

}
