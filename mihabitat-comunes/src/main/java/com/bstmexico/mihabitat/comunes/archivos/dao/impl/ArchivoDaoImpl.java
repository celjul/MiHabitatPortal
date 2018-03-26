package com.bstmexico.mihabitat.comunes.archivos.dao.impl;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.archivos.dao.ArchivoDao;
import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;
import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Repository
public class ArchivoDaoImpl extends GenericDaoImpl<Archivo, Long> implements
		ArchivoDao {
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<Archivo> getList(Class<? extends Archivo> type) {
		try {	
			Assert.notNull(type, "El tipo de cat?logo es nulo.");
			return sessionFactory.getCurrentSession().createCriteria(type)
					.list();
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO007", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
