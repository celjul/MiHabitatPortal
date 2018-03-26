package com.bstmexico.mihabitat.comunes.personas.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.personas.dao.PersonaDao;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.service.PersonaService;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class PersonaServiceImpl implements PersonaService {

	private static final Logger LOG = LoggerFactory
			.getLogger(PersonaServiceImpl.class);

	@Autowired
	private PersonaDao personaDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(Persona persona) {
		try {
			Assert.notNull(persona, "SEREX001");
			Set<ConstraintViolation<Persona>> violations = validator
					.validate(persona);
			if (violations.isEmpty()) {
				personaDao.save(persona);
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

	@Override
	public Persona get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return personaDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Persona persona) {
		try {
			Assert.notNull(persona, "SEREX001");
			Assert.notNull(persona.getId(), "SEREX003");
			Set<ConstraintViolation<Persona>> violations = validator
					.validate(persona);
			if (violations.isEmpty()) {
				personaDao.merge(persona);
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
	public Collection<Persona> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return personaDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Persona get(Usuario usuario) {
		try {
			Assert.notNull(usuario, "SEREX001");
			return personaDao.get(usuario);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

//	@Override
//	public Collection<Persona> getList(CatalogoRol... roles) {
//		try {
//			Assert.notNull(roles, "SEREX001");
//			return personaDao.getList(roles);
//		} catch (IllegalArgumentException ex) {
//			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
//			LOG.warn(ex1.getMessage(), ex);
//			throw ex1;
//		}
//	}
}
