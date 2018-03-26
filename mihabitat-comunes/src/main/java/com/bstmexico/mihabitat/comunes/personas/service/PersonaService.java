package com.bstmexico.mihabitat.comunes.personas.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface PersonaService {

	void save(Persona persona);

	Persona get(Long id);

	void update(Persona contacto);

	@SuppressWarnings("rawtypes")
	Collection<Persona> search(Map map);

	Persona get(Usuario usuario);
	
//	Collection<Persona> getList(CatalogoRol... roles);
}
