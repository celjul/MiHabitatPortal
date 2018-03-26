package com.bstmexico.mihabitat.cargos.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.cargos.model.TipoConsumo;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class TipoConsumoFactory {
	
	public static final Logger LOG = LoggerFactory
			.getLogger(TipoConsumoFactory.class);
	
	public static TipoConsumo newInstance() {
		return new TipoConsumo();
	}

	public static TipoConsumo newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			TipoConsumo tipo = newInstance();
			tipo.setId(id);
			return tipo;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
