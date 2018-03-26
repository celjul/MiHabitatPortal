package com.bstmexico.mihabitat.condominios.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface GrupoCondominioService {

	void save(GrupoCondominio grupo);

	GrupoCondominio get(Long id);

	void update(GrupoCondominio grupo);

	@SuppressWarnings("rawtypes")
	Collection<GrupoCondominio> search(Map map);
}
