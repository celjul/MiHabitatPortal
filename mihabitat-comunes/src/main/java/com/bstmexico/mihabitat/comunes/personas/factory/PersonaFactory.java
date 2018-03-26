package com.bstmexico.mihabitat.comunes.personas.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class PersonaFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(PersonaFactory.class);

	public static Persona newInstance() {
		return new Persona();
	}

	public static PersonaAbstract newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			PersonaAbstract persona = newInstance();
			persona.setId(id);
			return persona;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
