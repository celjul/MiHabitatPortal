package com.bstmexico.mihabitat.proveedores.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.proveedores.model.Proveedor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 
 * @author JPC
 * @version 1.0
 * @since 2015
 */
public class ProveedorFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(ProveedorFactory.class);
	
	public static Proveedor newInstance() {
		return new Proveedor();
	}
	
	public static Proveedor newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Proveedor proveedor = newInstance();
			proveedor.setId(id);
			return proveedor;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
