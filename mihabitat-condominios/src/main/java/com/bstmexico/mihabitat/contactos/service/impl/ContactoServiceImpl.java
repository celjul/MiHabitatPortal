package com.bstmexico.mihabitat.contactos.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.personas.dao.PersonaDao;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.comunes.personas.utils.PersonaUtils;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.dao.ContactoDao;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.model.EmailContacto;
import com.bstmexico.mihabitat.contactos.model.TelefonoContacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class ContactoServiceImpl implements ContactoService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ContactoServiceImpl.class);

	@Autowired
	private ContactoDao contactoDao;

	@Autowired
	private PersonaDao personaDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(Contacto contacto) {
		try {
			Assert.notNull(contacto, "SEREX001");
			
			fixContacto(contacto);
			Set<ConstraintViolation<Contacto>> violations = validator
					.validate(contacto);
			if (violations.isEmpty()) {
				contactoDao.save(contacto);
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

	private void fixContacto(Contacto contacto) {
		if (CollectionUtils.isNotEmpty(contacto.getEmails())) {
			for (Email email : contacto.getEmails()) {
				((EmailContacto) email).setContacto(contacto);
			}
		}
		
		if (CollectionUtils.isNotEmpty(contacto.getTelefonos())) {
			for (Telefono tel : contacto.getTelefonos()) {
				((TelefonoContacto) tel).setContacto(contacto);
			}
		}
		
//		if (contacto.getPersona() != null) {
//			fixPersona(contacto.getPersona());
//		}
	}

	private void fixPersona(Persona persona) {
		PersonaUtils.fix(persona);
	}

	@Override
	public Contacto get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return contactoDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Contacto contacto) {
		try {
			Assert.notNull(contacto, "SEREX001");
			Assert.notNull(contacto.getId(), "SEREX003");
			Set<ConstraintViolation<Contacto>> violations = validator
					.validate(contacto);
			if (violations.isEmpty()) {
				contactoDao.update(contacto);
			} else {
				String message = "SEREX002";
				ApplicationException ex1 = new ServiceException(message,
						violations);
				LOG.warn(ex1.getMessage() + violations);
				throw ex1;
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Contacto> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return contactoDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Contacto get(Condominio condominio, String email) {
		try {
			Assert.notNull(condominio, "SEREX001");
			Assert.notNull(condominio.getId(), "SEREX001");
			Assert.hasLength(email, "SEREX002");
			return contactoDao.get(condominio, email);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Contacto get(Long id, String email) {
		try {
			Assert.notNull(id, "SEREX001");
			Assert.hasLength(email, "SEREX002");
			return contactoDao.get(id, email);
		} catch (IllegalArgumentException e) {
			ApplicationException ex1 = new ServiceException(e.getMessage(), e);
			LOG.warn(ex1.getMessage(), e);
			throw ex1;
		}
	}

	@Override
	public Collection<Contacto> buscar(Condominio condominio, String key) {
		try {
			Assert.notNull(condominio, "SEREX001");
			Assert.notNull(condominio.getId(), "SEREX001");
			Assert.notNull(key, "SEREX001");
			Assert.hasLength(key, "SEREX002");
			return contactoDao.buscar(condominio, key);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<ContactoDepartamento> getDepartamentos(Long idContacto) {
		try {
			Assert.notNull(idContacto, "SEREX001");
			return contactoDao.getDepartamentos(idContacto);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
