package com.bstmexico.mihabitat.cobranza.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.cobranza.model.Recordatorio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public interface RecordatorioService {
	
	void save(Recordatorio recordatorio);

	Recordatorio get(Long id);

	void update(Recordatorio recordatorio);

	void delete(Recordatorio recordatorio);

	@SuppressWarnings("rawtypes")
	Collection<Recordatorio> search(Map map);
}
