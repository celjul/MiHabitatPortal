package com.bstmexico.mihabitat.comunes.direcciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.direcciones.model.Pais;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class PaisFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(PaisFactory.class);

	public static Pais newInstance() {
		return new Pais();
	}

	public static Pais newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Pais pais = newInstance();
			pais.setId(id);
			return pais;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
