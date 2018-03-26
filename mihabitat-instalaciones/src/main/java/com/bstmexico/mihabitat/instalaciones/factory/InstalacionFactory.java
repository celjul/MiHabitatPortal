package com.bstmexico.mihabitat.instalaciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;

public class InstalacionFactory {

	public static final Logger LOG = LoggerFactory
			.getLogger(InstalacionFactory.class);
	
	public static Instalacion newInstance() {
		return new Instalacion();
	}

	public static Instalacion newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Instalacion instalacion = newInstance();
			instalacion.setId(id);
			return instalacion;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
