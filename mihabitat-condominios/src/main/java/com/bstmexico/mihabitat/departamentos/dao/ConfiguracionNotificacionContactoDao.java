package com.bstmexico.mihabitat.departamentos.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.ConfiguracionNotificacionContacto;

import java.util.Collection;

/**
 * Created by Pegasus on 16/06/2015.
 */
public interface ConfiguracionNotificacionContactoDao extends GenericDao<ConfiguracionNotificacionContacto, Long> {
    Collection<ConfiguracionNotificacionContacto> getByCondominio(Condominio condominio);
}
