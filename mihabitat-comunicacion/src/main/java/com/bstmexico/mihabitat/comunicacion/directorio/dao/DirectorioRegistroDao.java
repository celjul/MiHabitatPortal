package com.bstmexico.mihabitat.comunicacion.directorio.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.comunicacion.directorio.model.DirectorioRegistro;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;

/**
 * Created by Zoe on 25/01/2016.
 */
public interface DirectorioRegistroDao extends GenericDao<DirectorioRegistro, Long> {

    Collection<DirectorioRegistro> getList(Condominio condominio);
}
