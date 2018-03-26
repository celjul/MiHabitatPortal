package com.bstmexico.mihabitat.comunes.archivos.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.archivos.dao.ArchivoDao;
import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;
import com.bstmexico.mihabitat.comunes.archivos.service.ArchivoService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;

/**
 * @author Zoé Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Service
public class ArchivoServiceImpl implements ArchivoService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ArchivoServiceImpl.class);

	@Autowired
	private ArchivoDao archivoDao;

	@Override
	public Collection<Archivo> getList(Class<? extends Archivo> type) {
		try {
			Assert.notNull(type, "SEREX001");
			return archivoDao.getList(type);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Archivo get(Long id){
		try {
			Assert.notNull(id, "SEREX001");
			return archivoDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
