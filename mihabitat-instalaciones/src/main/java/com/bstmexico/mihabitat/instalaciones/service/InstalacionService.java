package com.bstmexico.mihabitat.instalaciones.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;

public interface InstalacionService {
	
	Collection<Instalacion> getList(Condominio condominio);
	
	void save(Instalacion instalacion);

	Instalacion get(Long id);

	Instalacion getConReservaciones(Long id);
	
	void update(Instalacion instalacion);

	@SuppressWarnings("rawtypes")
	Collection<Instalacion> search(Map map);

}
