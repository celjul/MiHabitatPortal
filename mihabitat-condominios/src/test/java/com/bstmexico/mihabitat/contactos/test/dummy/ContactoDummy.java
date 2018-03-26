package com.bstmexico.mihabitat.contactos.test.dummy;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.factory.ContactoFactory;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.model.EmailContacto;
import com.bstmexico.mihabitat.contactos.model.TelefonoContacto;

public class ContactoDummy {
	public static Contacto getEmptyContacto() {
		return ContactoFactory.newInstance();
	}
	
	public static Contacto getFullContacto(Condominio condominio, int departamentos) {
		Contacto contacto = getEmptyContacto();
		contacto.setCondominio(condominio);
		contacto.setDepartamentos(DepartamentoDummy.getListaDepartamentos(contacto, departamentos));
		//contacto.setPersona(PersonaDummy.getFullPersona(3,3));
		
//		contacto.setApellidoMaterno(contacto.getPersona().getApellidoMaterno());
//		contacto.setApellidoPaterno(contacto.getPersona().getApellidoPaterno());
//		contacto.setEmails(getEmails(contacto.getPersona().getEmails(), contacto));
//		contacto.setNombre(contacto.getPersona().getNombre());
//		contacto.setTelefonos(getTelefonos(contacto.getPersona().getTelefonos(), contacto));
		
		return contacto;
	}

	private static Collection<? extends Telefono> getTelefonos(
			Collection<? extends Telefono> telefonos, Contacto contacto) {
		Collection<Telefono> lista = null;
		
		if (CollectionUtils.isNotEmpty(telefonos)) {
			lista = new ArrayList<Telefono>();
			for (Telefono tel : telefonos) {
				TelefonoContacto tCon = new TelefonoContacto();
				tCon.setExtension(tel.getExtension());
				tCon.setLada(tel.getLada());
				tCon.setNumero(tel.getNumero());
				tCon.setTipo(tel.getTipo());
				tCon.setContacto(contacto);
				
				lista.add(tCon);
			}
		}
		
		return lista;
	}

	private static Collection<? extends Email> getEmails(
			Collection<? extends Email> emails, Contacto contacto) {
		Collection<Email> lista = null;
		
		if (CollectionUtils.isNotEmpty(emails)) {
			lista = new ArrayList<Email>();
			for (Email email : emails) {
				EmailContacto eCon = new EmailContacto();
				eCon.setDireccion(email.getDireccion());
				eCon.setTipo(email.getTipo());
				eCon.setContacto(contacto);
				
				lista.add(eCon);
			}
		}
		
		return lista;
	}
}
