package com.bstmexico.mihabitat.condominios.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class MantenimientoCondominioFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(MantenimientoCondominioFactory.class);

	public static MantenimientoCondominio newInstance() {
		return new MantenimientoCondominio();
	}

	public static MantenimientoCondominio newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			MantenimientoCondominio mantenimiento = newInstance();
			mantenimiento.setId(id);
			return mantenimiento;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
