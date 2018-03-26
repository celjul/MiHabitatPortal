package com.bstmexico.mihabitat.comunes.direcciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.direcciones.model.Estado;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class EstadoFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(EstadoFactory.class);

	public static Estado newInstance() {
		return new Estado();
	}

	public static Estado newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Estado estado = newInstance();
			estado.setId(id);
			return estado;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
