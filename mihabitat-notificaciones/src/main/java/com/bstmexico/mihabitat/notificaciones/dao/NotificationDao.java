package com.bstmexico.mihabitat.notificaciones.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.notificaciones.model.Notification;

import java.util.Collection;
import java.util.List;

/**
 * Created by Pegasus on 16/06/2015.
 */
public interface NotificationDao extends GenericDao<Notification, Long> {

    Collection<Notification> getByContacto(Contacto contacto/*, List<Class> tiposDeNotificaciones*/);

}
