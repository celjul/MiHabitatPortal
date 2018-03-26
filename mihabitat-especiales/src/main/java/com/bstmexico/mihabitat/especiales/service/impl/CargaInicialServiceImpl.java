package com.bstmexico.mihabitat.especiales.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.especiales.dao.CargaInicialDao;
import com.bstmexico.mihabitat.especiales.model.CargaInicial;
import com.bstmexico.mihabitat.especiales.service.CargaInicialService;
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
public class CargaInicialServiceImpl implements CargaInicialService {

	private static final Logger LOG = LoggerFactory
			.getLogger(CargaInicialServiceImpl.class);

	@Autowired
	private CargaInicialDao cargaInicialDao;

	@Autowired
	private Validator validator;

	@Override
	public Collection<CargaInicial> getList() {
		Collection<CargaInicial> cargaInicials = cargaInicialDao.getList();
		if (cargaInicials.isEmpty()) {
			LOG.info("Lista vacía");
		}
		return cargaInicials;
	}

	@Override
	public void save(CargaInicial cargaInicial) {
		try {
			Assert.notNull(cargaInicial, "SEREX001");
			Set<ConstraintViolation<CargaInicial>> violations = validator
					.validate(cargaInicial);
			if (violations.isEmpty()) {
				cargaInicialDao.save(cargaInicial);
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
	public CargaInicial get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return cargaInicialDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(CargaInicial cargaInicial) {
		try {
			Assert.notNull(cargaInicial, "SEREX001");
			Assert.notNull(cargaInicial.getId(), "SEREX003");
			Set<ConstraintViolation<CargaInicial>> violations = validator
					.validate(cargaInicial);
			if (violations.isEmpty()) {
				cargaInicialDao.update(cargaInicial);
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
	public Collection<CargaInicial> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return cargaInicialDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
