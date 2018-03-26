package com.bstmexico.mihabitat.condominios.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface MantenimientoCondominioService {

	void save(MantenimientoCondominio mantenimiento);

	MantenimientoCondominio get(Long id);

	void update(MantenimientoCondominio mantenimiento);

	@SuppressWarnings("rawtypes")
	Collection<MantenimientoCondominio> search(Map map);
}
