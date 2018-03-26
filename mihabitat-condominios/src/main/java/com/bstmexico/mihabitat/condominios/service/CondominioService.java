package com.bstmexico.mihabitat.condominios.service;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;
import java.util.Map;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface CondominioService {

	Collection<Condominio> getList();

	void save(Condominio condominio);

	Condominio get(Long id);

	Condominio readConImagen(Long id);

	Condominio readConConfiguracion(Long id);

	void update(Condominio condominio);

	@SuppressWarnings("rawtypes")
	Collection<Condominio> search(Map map);

	Collection<Condominio> search(Usuario usuario);
}

