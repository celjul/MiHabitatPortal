package com.bstmexico.mihabitat.comunes.personas.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;

public class TelefonoFactory {
	private static final Logger LOG = LoggerFactory
			.getLogger(CatalogoFactory.class);

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Telefono> tipo) {
		try {
			Assert.notNull(tipo, "FACEX001");
			return (T) tipo.newInstance();
		} catch (IllegalAccessException ex) {
			ApplicationException ex1 = new FactoryException("FAC002", ex,
					tipo.getSimpleName());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		} catch (InstantiationException ex) {
			ApplicationException ex1 = new FactoryException("FAC002", ex,
					tipo.getSimpleName());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Telefono> tipo, Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Telefono telefono = newInstance(tipo);
			telefono.setId(id);
			return (T) telefono;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
