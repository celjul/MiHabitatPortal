package com.bstmexico.mihabitat.contactos.factory;

import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;

public class ContactoDepartamentoFactory {

	public static ContactoDepartamento newInstance() {
		ContactoDepartamento contacto = new ContactoDepartamento();
		
		return contacto;
	}
}
