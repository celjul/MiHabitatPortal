package com.bstmexico.mihabitat.comunes.personas.utils;

import org.apache.commons.collections.CollectionUtils;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.EmailPersona;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona;

public final class PersonaUtils {
	public static final void fix(Persona persona) {
		if (CollectionUtils.isNotEmpty(persona.getEmails())) {
			for (Email email : persona.getEmails()) {
				((EmailPersona) email).setPersona(persona);
			}
		}
		
		if (CollectionUtils.isNotEmpty(persona.getTelefonos())) {
			for (Telefono tel : persona.getTelefonos()) {
				((TelefonoPersona) tel).setPersona(persona);
			}
		}
	}
}
