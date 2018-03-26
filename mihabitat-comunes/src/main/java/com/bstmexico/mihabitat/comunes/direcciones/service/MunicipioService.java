package com.bstmexico.mihabitat.comunes.direcciones.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.comunes.direcciones.model.Municipio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public interface MunicipioService {
	
	@SuppressWarnings("rawtypes")
	Collection<Municipio> search(Map map);
}
