package com.bstmexico.mihabitat.instalaciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;

public class ReservacionFactory {

	public static final Logger LOG = LoggerFactory
			.getLogger(ReservacionFactory.class);
	
	public static Reservacion newInstance() {
		return new Reservacion();
	}

	public static Reservacion newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Reservacion reservacion = newInstance();
			reservacion.setId(id);
			return reservacion;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
}
