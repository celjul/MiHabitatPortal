package com.bstmexico.mihabitat.especiales.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.especiales.dao.CaInTemplateCargaDepartamentoDao;
import com.bstmexico.mihabitat.especiales.model.CaInTemplateCargaDepartamento;
import com.bstmexico.mihabitat.especiales.service.CaInTemplateCargaDepartamentoService;
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
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
@Service
public class CaInTemplateCargaDepartamentoServiceImpl implements CaInTemplateCargaDepartamentoService {

	private static final Logger LOG = LoggerFactory
			.getLogger(CaInTemplateCargaDepartamentoServiceImpl.class);

	@Autowired
	private CaInTemplateCargaDepartamentoDao caInTemplateCargaDepartamentoDao;

	@Autowired
	private Validator validator;

	@Override
	public Collection<CaInTemplateCargaDepartamento> getList() {
		Collection<CaInTemplateCargaDepartamento> caInTemplateCargaDepartamentos = caInTemplateCargaDepartamentoDao.getList();
		if (caInTemplateCargaDepartamentos.isEmpty()) {
			LOG.info("Lista vacía");
		}
		return caInTemplateCargaDepartamentos;
	}

	@Override
	public void save(CaInTemplateCargaDepartamento caInTemplateCargaDepartamento) {
		try {
			Assert.notNull(caInTemplateCargaDepartamento, "SEREX001");
			Set<ConstraintViolation<CaInTemplateCargaDepartamento>> violations = validator
					.validate(caInTemplateCargaDepartamento);
			if (violations.isEmpty()) {
				caInTemplateCargaDepartamentoDao.save(caInTemplateCargaDepartamento);
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
	public CaInTemplateCargaDepartamento get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return caInTemplateCargaDepartamentoDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(CaInTemplateCargaDepartamento caInTemplateCargaDepartamento) {
		try {
			Assert.notNull(caInTemplateCargaDepartamento, "SEREX001");
			Assert.notNull(caInTemplateCargaDepartamento.getId(), "SEREX003");
			Set<ConstraintViolation<CaInTemplateCargaDepartamento>> violations = validator
					.validate(caInTemplateCargaDepartamento);
			if (violations.isEmpty()) {
				caInTemplateCargaDepartamentoDao.update(caInTemplateCargaDepartamento);
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
	public Collection<CaInTemplateCargaDepartamento> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return caInTemplateCargaDepartamentoDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
