package com.bstmexico.mihabitat.instalaciones.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.bstmexico.mihabitat.instalaciones.model.ReservacionRecurrente;

public class ReservacionRecurrenteFactory {

	public static final Logger LOG = LoggerFactory
			.getLogger(ReservacionRecurrenteFactory.class);
	
	public static ReservacionRecurrente newInstance() {
		return new ReservacionRecurrente();
	}

	public static ReservacionRecurrente newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			ReservacionRecurrente reservacionRecurrente = newInstance();
			reservacionRecurrente.setId(id);
			return reservacionRecurrente;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
