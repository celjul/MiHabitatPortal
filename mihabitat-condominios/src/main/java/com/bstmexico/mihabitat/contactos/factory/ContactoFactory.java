package com.bstmexico.mihabitat.contactos.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.bstmexico.mihabitat.contactos.model.Contacto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class ContactoFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(ContactoFactory.class);

	public static Contacto newInstance() {
		return new Contacto();
	}

	public static Contacto newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Contacto contacto = newInstance();
			contacto.setId(id);
			return contacto;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
	public static Contacto newInstance(PersonaAbstract persona){
		try{
			Assert.notNull(persona, "SEREX003");
			Contacto contacto = newInstance();
			contacto.setApellidoMaterno(persona.getApellidoMaterno());
			contacto.setApellidoPaterno(persona.getApellidoPaterno());
			contacto.setNombre(persona.getNombre());
//			contacto.setPersona(persona);
			return contacto;
		} catch (IllegalArgumentException ex) {
			ApplicationException e = new FactoryException(ex.getMessage());
			LOG.warn(e.getMessage(), ex);
			throw e;
		}
	}
}
