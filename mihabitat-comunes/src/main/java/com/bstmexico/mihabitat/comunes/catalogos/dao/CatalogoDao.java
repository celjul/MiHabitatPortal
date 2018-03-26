package com.bstmexico.mihabitat.comunes.catalogos.dao;

import java.util.Collection;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;
import com.bstmexico.mihabitat.comunes.dao.GenericDao;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface CatalogoDao extends GenericDao<Catalogo, Long> {

	Collection<Catalogo> getList(Class<? extends Catalogo> type);
}
