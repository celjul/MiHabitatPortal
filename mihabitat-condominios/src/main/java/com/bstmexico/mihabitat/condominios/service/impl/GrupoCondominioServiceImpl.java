package com.bstmexico.mihabitat.condominios.service.impl;

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
import com.bstmexico.mihabitat.condominios.dao.GrupoCondominioDao;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;
import com.bstmexico.mihabitat.condominios.service.GrupoCondominioService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class GrupoCondominioServiceImpl implements GrupoCondominioService {

	private static final Logger LOG = LoggerFactory
			.getLogger(GrupoCondominioServiceImpl.class);

	@Autowired
	private GrupoCondominioDao grupoCondominioDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(GrupoCondominio grupo) {
		try {
			Assert.notNull(grupo, "SEREX001");
			Set<ConstraintViolation<GrupoCondominio>> violations = validator
					.validate(grupo);
			if (violations.isEmpty()) {
				grupoCondominioDao.save(grupo);
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
	public GrupoCondominio get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return grupoCondominioDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(GrupoCondominio grupo) {
		try {
			Assert.notNull(grupo, "SEREX001");
			Assert.notNull(grupo.getId(), "SEREX003");
			Set<ConstraintViolation<GrupoCondominio>> violations = validator
					.validate(grupo);
			if (violations.isEmpty()) {
				grupoCondominioDao.update(grupo);
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
	public Collection<GrupoCondominio> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return grupoCondominioDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
