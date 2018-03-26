package com.bstmexico.mihabitat.notificaciones.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.notificaciones.dao.NotificationDao;
import com.bstmexico.mihabitat.notificaciones.model.Notification;
import com.bstmexico.mihabitat.notificaciones.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

	private static final Logger LOG = LoggerFactory
			.getLogger(NotificationServiceImpl.class);

	/*@Autowired
	@Qualifier("cargoservicedefault")
	private CargoService cargoService;*/

	@Autowired
	private Validator validator;

	@Autowired
	private NotificationDao notificationDao;

	@Override
	public void save(Notification notification) {

		try {
			Assert.notNull(notification, "SEREX001");
			Set<ConstraintViolation<Notification>> violations = validator
					.validate(notification);
			if (violations.isEmpty()) {
				notificationDao.save(notification);
			} else {
				String message = "SEREX002";
				ApplicationException ex1 = new ServiceException(message,
						violations);
				LOG.warn(ex1.getMessage(), violations);
				throw ex1;
			}

		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}

	}

	@Override
	public void delete(Notification notification) {

		try {
			Assert.notNull(notification, "SEREX001");
			notificationDao.delete(notification);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}

	}

	@Override
	public Collection<Notification> getByContacto(Contacto contacto/*, List<Class> clases*/) {
		try {
			Assert.notNull(contacto, "SEREX001");
			return notificationDao.getByContacto(contacto/*, clases*/);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Notification get(Long id) {
		try {
			Assert.notNull(id, "SEREX001");
			return notificationDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Notification notification) {
		try {
			Assert.notNull(notification, "SEREX001");
			Assert.notNull(notification.getId(), "SEREX001");
			notificationDao.update(notification);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
