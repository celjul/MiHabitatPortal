package com.bstmexico.mihabitat.pagostdc.feenicia.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;
import com.bstmexico.mihabitat.pagostdc.feenicia.repository.FeeniciaRepository;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
@Repository
public class FeeniciaRepositoryImpl extends GenericDaoImpl<FeeniciaCfg, Long>
		implements FeeniciaRepository {

	@Transactional(readOnly = true)
	@Override
	public FeeniciaCfg get(Condominio condominio) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(FeeniciaCfg.class);
			criteria.add(Restrictions.eq("condominio", condominio));
			FeeniciaCfg feeniciaCfg = (FeeniciaCfg) criteria.uniqueResult();
			return feeniciaCfg;
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
