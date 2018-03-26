package com.bstmexico.mihabitat.cargos.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.cargos.model.TipoConsumo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public interface TipoConsumoService {
	
	void save(TipoConsumo consumo);

	TipoConsumo get(Long id);

	void update(TipoConsumo consumo);

	@SuppressWarnings("rawtypes")
	Collection<TipoConsumo> search(Map map);
}
