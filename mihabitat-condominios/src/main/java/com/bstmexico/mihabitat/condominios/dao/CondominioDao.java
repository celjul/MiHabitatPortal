package com.bstmexico.mihabitat.condominios.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface CondominioDao extends GenericDao<Condominio, Long> {

	Collection<Condominio> search(Usuario usuario);

	Condominio readConImagen(Long id);

	Condominio readConConfiguracion(Long id);
}
