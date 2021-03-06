package com.bstmexico.mihabitat.comunes.direcciones.service.impl;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.direcciones.dao.ColoniaDao;
import com.bstmexico.mihabitat.comunes.direcciones.model.Colonia;
import com.bstmexico.mihabitat.comunes.direcciones.service.ColoniaService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class ColoniaServiceImpl implements ColoniaService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ColoniaServiceImpl.class);

	@Autowired
	private ColoniaDao coloniaDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Colonia> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return coloniaDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
