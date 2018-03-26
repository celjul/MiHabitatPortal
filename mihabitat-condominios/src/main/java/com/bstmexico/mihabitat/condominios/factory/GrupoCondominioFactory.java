package com.bstmexico.mihabitat.condominios.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class GrupoCondominioFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(GrupoCondominioFactory.class);

	public static GrupoCondominio newInstance() {
		return new GrupoCondominio();
	}

	public static GrupoCondominio newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			GrupoCondominio grupo = newInstance();
			grupo.setId(id);
			return grupo;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
