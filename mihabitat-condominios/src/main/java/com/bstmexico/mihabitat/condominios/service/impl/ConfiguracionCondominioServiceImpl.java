package com.bstmexico.mihabitat.condominios.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.dao.ConfiguracionCondominioDao;
import com.bstmexico.mihabitat.condominios.model.ConfiguracionCondominio;
import com.bstmexico.mihabitat.condominios.service.ConfiguracionCondominioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class ConfiguracionCondominioServiceImpl implements ConfiguracionCondominioService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ConfiguracionCondominioServiceImpl.class);

	@Autowired
	private ConfiguracionCondominioDao configuracionCondominioDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(ConfiguracionCondominio configuracionCondominio) {
		try {
			Assert.notNull(configuracionCondominio, "SEREX001");
			Set<ConstraintViolation<ConfiguracionCondominio>> violations = validator
					.validate(configuracionCondominio);
			if (violations.isEmpty()) {
				configuracionCondominioDao.save(configuracionCondominio);
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
	public ConfiguracionCondominio get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return configuracionCondominioDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(ConfiguracionCondominio configuracionCondominio) {
		try {
			Assert.notNull(configuracionCondominio, "SEREX001");
			Assert.notNull(configuracionCondominio.getId(), "SEREX003");
			Set<ConstraintViolation<ConfiguracionCondominio>> violations = validator
					.validate(configuracionCondominio);
			if (violations.isEmpty()) {
				configuracionCondominioDao.update(configuracionCondominio);
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
}
