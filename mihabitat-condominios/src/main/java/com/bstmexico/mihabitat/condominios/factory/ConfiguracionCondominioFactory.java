package com.bstmexico.mihabitat.condominios.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.condominios.model.ConfiguracionCondominio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class ConfiguracionCondominioFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(ConfiguracionCondominioFactory.class);

	public static ConfiguracionCondominio newInstance() {
		return new ConfiguracionCondominio();
	}

	public static ConfiguracionCondominio newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			ConfiguracionCondominio configuracionCondominio = newInstance();
			configuracionCondominio.setId(id);
			return configuracionCondominio;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
