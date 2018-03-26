package com.bstmexico.mihabitat.comunes.archivos.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
public class ArchivoFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(ArchivoFactory.class);

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Archivo> tipo) {
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
	public static <T> T newInstance(Class<? extends Archivo> tipo, Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Archivo archivo = newInstance(tipo);
			archivo.setId(id);
			return (T) archivo;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}

