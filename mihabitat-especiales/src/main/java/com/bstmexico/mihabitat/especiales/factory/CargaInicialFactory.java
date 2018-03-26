package com.bstmexico.mihabitat.especiales.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.especiales.model.CargaInicial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
public class CargaInicialFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CargaInicialFactory.class);

	public static CargaInicial newInstance() {
		return new CargaInicial();
	}

	public static CargaInicial newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			CargaInicial cargaInicial = newInstance();
			cargaInicial.setId(id);
			return cargaInicial;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
