package com.bstmexico.mihabitat.comunes.direcciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.direcciones.model.Municipio;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class MunicipioFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(MunicipioFactory.class);

	public static Municipio newInstance() {
		return new Municipio();
	}

	public static Municipio newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Municipio municipio = newInstance();
			municipio.setId(id);
			return municipio;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
