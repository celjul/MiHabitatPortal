package com.bstmexico.mihabitat.comunes.direcciones.service.impl;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.direcciones.dao.EstadoDao;
import com.bstmexico.mihabitat.comunes.direcciones.model.Estado;
import com.bstmexico.mihabitat.comunes.direcciones.service.EstadoService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class EstadoServiceImpl implements EstadoService {

	private static final Logger LOG = LoggerFactory
			.getLogger(EstadoServiceImpl.class);

	@Autowired
	private EstadoDao estadoDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Estado> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return estadoDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
