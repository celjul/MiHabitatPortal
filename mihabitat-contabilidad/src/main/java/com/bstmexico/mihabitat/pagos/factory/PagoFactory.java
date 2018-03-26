package com.bstmexico.mihabitat.pagos.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.pagos.model.Pago;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class PagoFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(PagoFactory.class);

	public static Pago newInstance() {
		return new Pago();
	}

	public static Pago newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Pago pago = newInstance();
			pago.setId(id);
			return pago;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
