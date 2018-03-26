package com.bstmexico.mihabitat.comunes.personas.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface PersonaDao extends GenericDao<Persona, Long> {

	Persona get(Usuario usuario);

//	Collection<Persona> getList(CatalogoRol... roles);
}
