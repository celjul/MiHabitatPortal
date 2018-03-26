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
import com.bstmexico.mihabitat.condominios.dao.MantenimientoCondominioDao;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;
import com.bstmexico.mihabitat.condominios.service.MantenimientoCondominioService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class MantenimientoCondominioServiceImpl implements
		MantenimientoCondominioService {

	private static final Logger LOG = LoggerFactory
			.getLogger(MantenimientoCondominioServiceImpl.class);

	@Autowired
	private MantenimientoCondominioDao mantenimientoCondominioDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(MantenimientoCondominio mantenimiento) {
		try {
			Assert.notNull(mantenimiento, "SEREX001");
			Set<ConstraintViolation<MantenimientoCondominio>> violations = validator
					.validate(mantenimiento);
			if (violations.isEmpty()) {
				mantenimientoCondominioDao.save(mantenimiento);
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
	public MantenimientoCondominio get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return mantenimientoCondominioDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(MantenimientoCondominio mantenimiento) {
		try {
			Assert.notNull(mantenimiento, "SEREX001");
			Assert.notNull(mantenimiento.getId(), "SEREX003");
			Set<ConstraintViolation<MantenimientoCondominio>> violations = validator
					.validate(mantenimiento);
			if (violations.isEmpty()) {
				mantenimientoCondominioDao.update(mantenimiento);
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
	public Collection<MantenimientoCondominio> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return mantenimientoCondominioDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
