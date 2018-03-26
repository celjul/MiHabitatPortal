package com.bstmexico.mihabitat.proveedores.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 
 * @author JPC
 * @version 1.0
 * @since 2015
 */
public class CfdiFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(CfdiFactory.class);
	
	public static Cfdi newInstance() {
		return new Cfdi();
	}
	
	public static Cfdi newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Cfdi cfdi = newInstance();
			cfdi.setId(id);
			return cfdi;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
