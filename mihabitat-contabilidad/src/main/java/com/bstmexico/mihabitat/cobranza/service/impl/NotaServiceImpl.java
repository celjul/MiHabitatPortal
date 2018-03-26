package com.bstmexico.mihabitat.cobranza.service.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.cobranza.dao.NotaDao;
import com.bstmexico.mihabitat.cobranza.model.Nota;
import com.bstmexico.mihabitat.cobranza.service.NotaService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class NotaServiceImpl implements NotaService {

	private static final Logger LOG = LoggerFactory
			.getLogger(NotaServiceImpl.class);

	@Autowired
	private NotaDao notaDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(Nota nota) {
		try {
			Assert.notNull(nota, "SEREX001");
			Set<ConstraintViolation<Nota>> violations = validator
					.validate(nota);
			if (violations.isEmpty()) {
				notaDao.save(nota);
			} else {
				ApplicationException ex1 = new ServiceException("SEREX002",
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
	public Nota get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return notaDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Nota nota) {
		try {
			Assert.notNull(nota, "SEREX001");
			Assert.notNull(nota.getId(), "SEREX003");
			Set<ConstraintViolation<Nota>> violations = validator
					.validate(nota);
			if (violations.isEmpty()) {
				notaDao.merge(nota);
			} else {
				ApplicationException ex1 = new ServiceException("SEREX002",
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
	public void delete(Nota nota) {
		try {
			Assert.notNull(nota, "SEREX003");
			Assert.notNull(nota.getId(), "SEREX003");
			notaDao.delete(nota);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Nota> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return notaDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Nota> getList(Condominio condominio) {
		try {
			Assert.notNull(condominio, "SEREX001");
			Assert.notNull(condominio.getId(), "SEREX001");
			return notaDao.getList(condominio);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
