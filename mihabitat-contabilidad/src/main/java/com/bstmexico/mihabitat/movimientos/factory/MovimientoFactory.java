package com.bstmexico.mihabitat.movimientos.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class MovimientoFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(MovimientoFactory.class);

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends Movimiento> tipo) {
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
	public static <T> T newInstance(Class<? extends Movimiento> tipo, Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Movimiento movimiento = newInstance(tipo);
			movimiento.setId(id);
			return (T) movimiento;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
