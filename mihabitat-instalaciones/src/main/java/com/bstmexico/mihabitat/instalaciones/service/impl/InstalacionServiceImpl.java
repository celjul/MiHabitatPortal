package com.bstmexico.mihabitat.instalaciones.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.bstmexico.mihabitat.cargos.factory.CargoFactory;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.instalaciones.dao.InstalacionDao;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;
import com.bstmexico.mihabitat.instalaciones.service.InstalacionService;

@Service
public class InstalacionServiceImpl implements InstalacionService {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(InstalacionServiceImpl.class);

	@Autowired
	private InstalacionDao instalacionDao;
	
	@Autowired
	private Validator validator;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Instalacion> getList(Condominio condominio) {

		Map params = new HashMap();
		params.put("condominio.id", condominio.getId());

		return instalacionDao.search(params.entrySet());
		
	}

	@Override
	public void save(Instalacion instalacion) {
		
		try {
			Assert.notNull(instalacion, "SEREX001");
			Set<ConstraintViolation<Instalacion>> violations = validator
					.validate(instalacion);
			if (violations.isEmpty()) {
				instalacionDao.save(instalacion);
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
	public Instalacion get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return instalacionDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Instalacion getConReservaciones(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return instalacionDao.getConReservaciones(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Instalacion instalacion) {

		try {
			Assert.notNull(instalacion, "SEREX001");
			Assert.notNull(instalacion.getId(), "SEREX003");
			Set<ConstraintViolation<Instalacion>> violations = validator
					.validate(instalacion);
			if (violations.isEmpty()) {
				Instalacion instTemp = instalacionDao.getConReservaciones(instalacion.getId());
				instalacion.setReservaciones(instTemp.getReservaciones());
				instalacionDao.update(instalacion);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Instalacion> search(Map map) {

		try {
			Assert.notNull(map, "SEREX001");
			return instalacionDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
		
	}

}
