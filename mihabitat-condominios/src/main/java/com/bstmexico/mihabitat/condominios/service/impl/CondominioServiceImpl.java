package com.bstmexico.mihabitat.condominios.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.dao.CondominioDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class CondominioServiceImpl implements CondominioService {

	private static final Logger LOG = LoggerFactory
			.getLogger(CondominioServiceImpl.class);

	@Autowired
	private CondominioDao condominioDao;

	@Autowired
	private Validator validator;

	@Override
	public Collection<Condominio> getList() {
		Collection<Condominio> condominios = condominioDao.getList();
		if (condominios.isEmpty()) {
			LOG.info("Lista vacía");
		}
		return condominios;
	}

	@Override
	public void save(Condominio condominio) {
		try {
			Assert.notNull(condominio, "SEREX001");
			Set<ConstraintViolation<Condominio>> violations = validator
					.validate(condominio);
			if (violations.isEmpty()) {
				condominioDao.save(condominio);
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
	public Condominio get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return condominioDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Condominio readConImagen(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return condominioDao.readConImagen(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Condominio readConConfiguracion(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return condominioDao.readConConfiguracion(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Condominio condominio) {
		try {
			Assert.notNull(condominio, "SEREX001");
			Assert.notNull(condominio.getId(), "SEREX003");
			Set<ConstraintViolation<Condominio>> violations = validator
					.validate(condominio);
			if (violations.isEmpty()) {
				condominioDao.update(condominio);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Condominio> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return condominioDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
	@Override
	public Collection<Condominio> search(Usuario usuario) {
		try {
			Assert.notNull(usuario, "SEREX001");
			Assert.notNull(usuario.getId(), "SEREX003");
			return condominioDao.search(usuario);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
