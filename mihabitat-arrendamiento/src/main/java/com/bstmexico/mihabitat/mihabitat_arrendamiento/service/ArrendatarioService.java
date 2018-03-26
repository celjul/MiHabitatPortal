package com.bstmexico.mihabitat.mihabitat_arrendamiento.service;

import java.util.Collection;

import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;

public interface ArrendatarioService {
	
	void save(Arrendatario arrendatario);
	
	Collection<Arrendatario> getAll();
	
	Arrendatario get(Long id_arrendatario);
	
	void update(Arrendatario arrendatario);

}
