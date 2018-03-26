package com.bstmexico.mihabitat.cobranza.service.impl;

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

import com.bstmexico.mihabitat.cobranza.dao.RecordatorioDao;
import com.bstmexico.mihabitat.cobranza.model.Recordatorio;
import com.bstmexico.mihabitat.cobranza.service.RecordatorioService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class RecordatorioServiceImpl implements RecordatorioService {

	private static final Logger LOG = LoggerFactory
			.getLogger(RecordatorioServiceImpl.class);

	@Autowired
	private RecordatorioDao recordatorioDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(Recordatorio recordatorio) {
		try {
			Assert.notNull(recordatorio, "SEREX001");
			Set<ConstraintViolation<Recordatorio>> violations = validator
					.validate(recordatorio);
			if (violations.isEmpty()) {
				recordatorioDao.save(recordatorio);
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
	public Recordatorio get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return recordatorioDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Recordatorio recordatorio) {
		try {
			Assert.notNull(recordatorio, "SEREX001");
			Assert.notNull(recordatorio.getId(), "SEREX003");
			Set<ConstraintViolation<Recordatorio>> violations = validator
					.validate(recordatorio);
			if (violations.isEmpty()) {
				recordatorioDao.merge(recordatorio);
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
	public void delete(Recordatorio recordatorio) {
		try {
			Assert.notNull(recordatorio, "SEREX003");
			Assert.notNull(recordatorio.getId(), "SEREX003");
			recordatorioDao.delete(recordatorio);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Recordatorio> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return recordatorioDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
