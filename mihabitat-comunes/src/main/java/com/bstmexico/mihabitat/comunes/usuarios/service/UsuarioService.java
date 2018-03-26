package com.bstmexico.mihabitat.comunes.usuarios.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

public interface UsuarioService {
	Usuario get(Usuario usuario);

	Usuario getByEmail(String email);

	void update(Usuario usuario);
	
	void save(Usuario usuario);

	Collection<Usuario> getByRol(CatalogoRol... roles);

	Usuario get(Long id);

	Usuario getByUsername(String user);

	Collection<Usuario> getAll();

	Collection<Usuario> search(Map map);
}
