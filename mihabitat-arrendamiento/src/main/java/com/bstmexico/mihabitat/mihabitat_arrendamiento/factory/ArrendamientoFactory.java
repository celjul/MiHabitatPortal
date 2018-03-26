package com.bstmexico.mihabitat.mihabitat_arrendamiento.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;

public class ArrendamientoFactory {
	private static final Logger LOG = LoggerFactory
			.getLogger(ArrendamientoFactory.class);

	public static Arrendatario newInstance() {
		return new Arrendatario();
	}

	public static Arrendatario newInstance(Long id) {
		try {

			Arrendatario arrendatario = newInstance();
			arrendatario.setIdArrendador(id);
			return arrendatario;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
