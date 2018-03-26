package com.bstmexico.mihabitat.instalaciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.instalaciones.model.Disponibilidad;

public class DisponibilidadFactory {

	public static final Logger LOG = LoggerFactory
			.getLogger(DisponibilidadFactory.class);
	
	public static Disponibilidad newInstance() {
		return new Disponibilidad();
	}

	public static Disponibilidad newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Disponibilidad disponibilidad = newInstance();
			disponibilidad.setId(id);
			return disponibilidad;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
}
