package com.bstmexico.mihabitat.comunes.dummy;

import java.util.ArrayList;
import java.util.Collection;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.personas.factory.PersonaFactory;
import com.bstmexico.mihabitat.comunes.personas.model.CatalogoEmail;
import com.bstmexico.mihabitat.comunes.personas.model.CatalogoTelefono;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.EmailPersona;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona;

public class PersonaDummy {
	public static Persona getPersonaDummy(int emails, int telefonos) {
		Persona persona= PersonaFactory.newInstance();
		persona.setNombre("Mauricio");
		persona.setApellidoPaterno("Fernandez");
		persona.setApellidoMaterno("Rios");
		persona.setEmails(getEmailsDummy(emails, persona));
		persona.setTelefonos(getTelefonosDummy(telefonos, persona));
		
		return persona;
	}

	private static Collection<? extends Telefono> getTelefonosDummy(int size, Persona persona) {
		Collection<TelefonoPersona> telefonos = new ArrayList<TelefonoPersona>();

		for (int i = 0; i < size; i++) {
			telefonos.add(getTelefonoDummy(persona));
		}
		return telefonos;
	}

	private static TelefonoPersona getTelefonoDummy(Persona persona) {
		TelefonoPersona telefono = new TelefonoPersona();
		telefono.setExtension("00");
		telefono.setLada("000");
		telefono.setNumero("000000");
		telefono.setPersona(persona);
		CatalogoTelefono tipo = CatalogoFactory.newInstance(CatalogoTelefono.class, 1L);
		telefono.setTipo(tipo);
		
		return telefono;
	}

	private static Collection<? extends Email> getEmailsDummy(int size, Persona persona) {
		Collection<EmailPersona> emails = new ArrayList<EmailPersona>();
		
		for (int i = 0; i < size; i++) {
			emails.add(getEmailDummy(persona));
		}
		return emails;
	}

	private static EmailPersona getEmailDummy(Persona persona) {
		EmailPersona email = new EmailPersona();
		email.setPersona(persona);
		email.setDireccion("correo@bstmexico.com");
		CatalogoEmail tipo = CatalogoFactory.newInstance(CatalogoEmail.class, 1L);
		email.setTipo(tipo);
		
		return email;
	}

	public static Persona getPersonaIdDummy(int emails, int telefonos) {
		Persona persona = getPersonaDummy(emails, telefonos);
		persona.setId(1l);
		
		return persona;
	}
}
