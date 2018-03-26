package com.bstmexico.mihabitat.cargos.service.impl;

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

import com.bstmexico.mihabitat.cargos.dao.TipoConsumoDao;
import com.bstmexico.mihabitat.cargos.model.TipoConsumo;
import com.bstmexico.mihabitat.cargos.service.TipoConsumoService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class TipoConsumoServiceImpl implements TipoConsumoService {

	private static final Logger LOG = LoggerFactory
			.getLogger(TipoConsumoServiceImpl.class);

	@Autowired
	private TipoConsumoDao tipoConsumoDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(TipoConsumo consumo) {
		try {
			Assert.notNull(consumo, "SEREX001");
			Set<ConstraintViolation<TipoConsumo>> violations = validator
					.validate(consumo);
			if (violations.isEmpty()) {
				tipoConsumoDao.save(consumo);
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
	public TipoConsumo get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return tipoConsumoDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(TipoConsumo consumo) {
		try {
			Assert.notNull(consumo, "SEREX001");
			Assert.notNull(consumo.getId(), "SEREX003");
			Set<ConstraintViolation<TipoConsumo>> violations = validator
					.validate(consumo);
			if (violations.isEmpty()) {
				tipoConsumoDao.update(consumo);
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
	public Collection<TipoConsumo> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return tipoConsumoDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
