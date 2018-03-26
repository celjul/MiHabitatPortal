package com.bstmexico.mihabitat.notificaciones.service;

import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.notificaciones.model.Notification;

import java.util.Collection;
import java.util.List;

public interface NotificationService {

	void save(Notification notification);
	void delete(Notification notification);

	Collection<Notification> getByContacto(Contacto contacto/*, List<Class> clases*/);

	Notification get(Long id);

	void update(Notification notification);

}
