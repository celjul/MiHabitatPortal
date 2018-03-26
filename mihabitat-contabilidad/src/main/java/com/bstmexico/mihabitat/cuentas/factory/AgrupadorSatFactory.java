package com.bstmexico.mihabitat.cuentas.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.cuentas.model.AgrupadorSat;

public class AgrupadorSatFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(AgrupadorSatFactory.class);

	public static AgrupadorSat newInstance() {
		return new AgrupadorSat();
	}

	public static AgrupadorSat newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			AgrupadorSat agrupadorSat = newInstance();
			agrupadorSat.setId(id);
			return agrupadorSat;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
