package com.bstmexico.mihabitat.mihabitat_visitantes.service.impl;

import java.util.Collection;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.mihabitat_visitantes.dao.VisitanteDao;
import com.bstmexico.mihabitat.mihabitat_visitantes.model.Visitantes;
import com.bstmexico.mihabitat.mihabitat_visitantes.service.VisitanteService;

@Service("visitanteService")
public class VisitanteServiceImpl implements VisitanteService {

	private static final Logger LOG = LoggerFactory
			.getLogger(VisitanteServiceImpl.class);
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private VisitanteDao visitanteDao;
	
	public void save(Visitantes visitante) {
		try {
			Assert.notNull(visitante, "SEREX001");
			visitanteDao.save(visitante);
			
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
	public Collection<Visitantes> getAll(){
		try {
			return visitanteDao.getList();
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
	public Visitantes get(Long id){
		try {
			return visitanteDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
	public void update(Visitantes visitante) {
		try {
			Assert.notNull(visitante, "SEREX001");
			visitanteDao.update(visitante);
			
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	

}
