package com.bstmexico.mihabitat.condominios.service;

import com.bstmexico.mihabitat.condominios.model.ConfiguracionCondominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface ConfiguracionCondominioService {

	void save(ConfiguracionCondominio configuracionCondominio);

	ConfiguracionCondominio get(Long id);

	void update(ConfiguracionCondominio configuracionCondominio);

}

