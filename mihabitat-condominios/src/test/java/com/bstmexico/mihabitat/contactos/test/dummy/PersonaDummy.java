package com.bstmexico.mihabitat.contactos.test.dummy;

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

	public static Persona getFullPersona(int emails, int tels) {
		Persona persona = PersonaFactory.newInstance();
		persona.setApellidoMaterno("Dummymaterno");
		persona.setApellidoPaterno("Dummypaterno");
		persona.setNombre("Dummynombre");
		persona.setEmails(getFullEmails(persona, emails));
		persona.setTelefonos(getFullTelefonos(persona, tels));
		
		return persona;
	}

	private static Collection<Email> getFullEmails(Persona persona, int emails) {
		Collection<Email> lista = null;
		if (emails > 0) {
			lista = new ArrayList<Email>();
			for (int i = 1; i <= emails; i++) {
				EmailPersona email = new EmailPersona();
				email.setDireccion(String.format("dummy%s@mihabitat.com.mx", i));
				CatalogoEmail tipo = CatalogoFactory.newInstance(CatalogoEmail.class, 1L);
				email.setTipo(tipo);
				email.setPersona(persona);
				
				lista.add(email);
			}
			
		}
		return lista;
	}

	private static Collection<? extends Telefono> getFullTelefonos(Persona persona, int tels) {
		Collection<TelefonoPersona> lista = null;
		if (tels > 0) {
			lista = new ArrayList<TelefonoPersona>();
			for (int i = 1; i <= tels; i++) {
				TelefonoPersona tel = new TelefonoPersona();
				tel.setExtension(String.format("%s%s%s", i,i,i));
				tel.setLada(String.format("%s%s%s", i,i,i));
				tel.setNumero(String.format("%s%s-%s%s%s-%s%s%s", i,i,i,i,i,i,i,i));
				tel.setPersona(persona);
				
				CatalogoTelefono tipo = CatalogoFactory.newInstance(CatalogoTelefono.class, 10L);
				tel.setTipo(tipo);
				
				lista.add(tel);
			}
			
		}
		return lista;
	}
}
