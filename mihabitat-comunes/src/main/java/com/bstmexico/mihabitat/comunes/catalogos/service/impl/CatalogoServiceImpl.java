package com.bstmexico.mihabitat.comunes.catalogos.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.catalogos.dao.CatalogoDao;
import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class CatalogoServiceImpl implements CatalogoService {

	private static final Logger LOG = LoggerFactory
			.getLogger(CatalogoServiceImpl.class);

	@Autowired
	private CatalogoDao catalogoDao;

	@Override
	public Collection<Catalogo> getList(Class<? extends Catalogo> type) {
		try {
			Assert.notNull(type, "SEREX001");
			return catalogoDao.getList(type);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Catalogo get(Long id) {
		try {
			Assert.notNull(id, "SEREX001");
			return catalogoDao.get(id);
		} catch (IllegalArgumentException iae) {
			ApplicationException ex1 = new ServiceException(iae.getMessage(), iae);
			LOG.warn(ex1.getMessage(), iae);
			throw ex1;
		}
	}
}