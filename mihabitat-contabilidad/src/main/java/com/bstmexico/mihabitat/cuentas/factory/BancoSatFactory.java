package com.bstmexico.mihabitat.cuentas.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.cuentas.model.BancoSat;

public class BancoSatFactory {
	public static final Logger LOG = LoggerFactory
			.getLogger(BancoSatFactory.class);
	
	public static BancoSat newInstance() {
		return new BancoSat();
	}

	public static BancoSat newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			BancoSat bancoSat = newInstance();
			bancoSat.setId(id);
			return bancoSat;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
