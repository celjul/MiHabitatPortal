package com.bstmexico.mihabitat.comunes.usuarios.dao;

import java.util.Collection;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

public interface UsuarioDao extends GenericDao<Usuario, Long> {

	Usuario getByUserName(String user);

	Usuario getByEmail(String email);

	Collection<Usuario> getByRol(CatalogoRol... roles);
}
