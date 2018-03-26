package com.bstmexico.mihabitat.condominios.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.condominios.model.Condominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class CondominioFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CondominioFactory.class);

	public static Condominio newInstance() {
		return new Condominio();
	}

	public static Condominio newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Condominio condominio = newInstance();
			condominio.setId(id);
			return condominio;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
