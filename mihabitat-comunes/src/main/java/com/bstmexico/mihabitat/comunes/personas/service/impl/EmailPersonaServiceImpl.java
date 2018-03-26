package com.bstmexico.mihabitat.comunes.personas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.personas.dao.EmailPersonaDao;
import com.bstmexico.mihabitat.comunes.personas.model.EmailPersona;
import com.bstmexico.mihabitat.comunes.personas.service.EmailPersonaService;

@Service("emailPersonaService")
public class EmailPersonaServiceImpl implements EmailPersonaService {
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(EmailPersonaServiceImpl.class);
	
	@Autowired
	private EmailPersonaDao emailPersonaDao;
	
	@Override
	public void delete(Long id) {
		try {
			Assert.notNull(id, "SEREX001");
			EmailPersona email = emailPersonaDao.get(id);
			
			Assert.notNull(email, "SEREX001");
			emailPersonaDao.delete(email);
		} catch (DataAccessException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOGGER.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
