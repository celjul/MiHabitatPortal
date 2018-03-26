package com.bstmexico.mihabitat.mihabitat_visitantes.service;

import java.util.Collection;

import com.bstmexico.mihabitat.mihabitat_visitantes.model.Visitantes;

public interface VisitanteService {

	void save(Visitantes visitante);

	Collection<Visitantes> getAll();
	
	Visitantes get(Long id);
	
	void update(Visitantes visitante);
}
