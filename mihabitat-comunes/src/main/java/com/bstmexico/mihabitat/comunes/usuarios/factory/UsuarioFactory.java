package com.bstmexico.mihabitat.comunes.usuarios.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class UsuarioFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(UsuarioFactory.class);

	public static Usuario newInstance() {
		return new Usuario();
	}

	public static Usuario newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Usuario usuario = newInstance();
			usuario.setId(id);
			return usuario;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
