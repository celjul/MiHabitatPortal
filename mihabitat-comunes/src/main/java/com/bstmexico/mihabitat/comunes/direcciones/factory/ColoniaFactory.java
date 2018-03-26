package com.bstmexico.mihabitat.comunes.direcciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.direcciones.model.Colonia;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class ColoniaFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(ColoniaFactory.class);

	public static Colonia newInstance() {
		return new Colonia();
	}

	public static Colonia newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Colonia colonia = newInstance();
			colonia.setId(id);
			return colonia;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
