package com.bstmexico.mihabitat.cobranza.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.cobranza.model.Nota;
import com.bstmexico.mihabitat.condominios.model.Condominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface NotaService {

	void save(Nota nota);

	Nota get(Long id);

	void update(Nota nota);

	void delete(Nota nota);

	@SuppressWarnings("rawtypes")
	Collection<Nota> search(Map map);

	@SuppressWarnings("rawtypes")
	Collection<Nota> getList(Condominio condominio);
}
