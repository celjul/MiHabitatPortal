package com.bstmexico.mihabitat.comunes.personas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.personas.dao.TelefonoPersonaDao;
import com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona;
import com.bstmexico.mihabitat.comunes.personas.service.TelefonoPersonaService;

@Service("telefonoPersonaService")
public class TelefonoPersonaServiceImpl implements TelefonoPersonaService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TelefonoPersonaServiceImpl.class);
	@Autowired
	private TelefonoPersonaDao telefonoPersonaDao;

	@Override
	public void delete(Long id) {
		try {
			Assert.notNull(id, "SEREX001");
			TelefonoPersona tel = telefonoPersonaDao.get(id);
			
			Assert.notNull(tel, "SEREX001");
			telefonoPersonaDao.delete(tel);
		} catch (DataAccessException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOGGER.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
