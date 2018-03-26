package com.bstmexico.mihabitat.mihabitat_visitantes.dao.impl;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.mihabitat_visitantes.dao.VisitanteDao;
import com.bstmexico.mihabitat.mihabitat_visitantes.model.Visitantes;

@Repository("visitanteDao")
public class VisitanteDaoImpl extends GenericDaoImpl<Visitantes, Long> implements VisitanteDao {
	
	@Transactional
	@Override
	public void save(Visitantes visitante) {
		try {
			Assert.notNull(visitante, "DAO010");	
			super.save(visitante);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}
	
	@Transactional
	@Override
	public Collection<Visitantes> getList() {
		Collection<Visitantes> visitantes;
    	try {
    		visitantes = super.getList();
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
    	return visitantes;
	} 
	
	@Transactional
	@Override
	public Visitantes get(Long id) {
		Visitantes visitantes;
    	try {
    		visitantes = super.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
    	return visitantes;
	} 
	
	@Transactional
	@Override
	public void update(Visitantes visitante) {
		try {
			Assert.notNull(visitante, "DAO010");	
			super.update(visitante);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

}
