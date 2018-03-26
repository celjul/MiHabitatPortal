package com.bstmexico.mihabitat.web.service;

import java.util.Collection;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

public interface UserService {

//	void sendEmailNuevoContacto(String emails, Contacto contacto);

	Collection<Contacto> getContactoDepartamento(Departamento departamento);

	void enviarLinkActivacion(String notificaciones, Contacto contacto, Condominio condominio);

	void enviarLinkActivacion(Collection<String> notificaciones,
			Collection<ContactoDepartamento> contactos, Condominio condominio);
	
}
