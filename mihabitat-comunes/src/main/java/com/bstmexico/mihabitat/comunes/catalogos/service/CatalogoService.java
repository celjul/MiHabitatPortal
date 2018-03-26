package com.bstmexico.mihabitat.comunes.catalogos.service;

import java.util.Collection;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface CatalogoService {

	Collection<Catalogo> getList(Class<? extends Catalogo> type);

	Catalogo get(Long id);
}