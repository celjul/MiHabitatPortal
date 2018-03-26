package com.bstmexico.mihabitat.comunes.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 * @param <T>
 * @param <PK>
 */
@Repository
public abstract class GenericDaoImpl<T, PK extends Serializable> implements
		GenericDao<T, PK> {

	protected static final Logger LOG = LoggerFactory
			.getLogger(GenericDaoImpl.class);

	@Autowired
	protected SessionFactory sessionFactory;

	private Class<T> type = null;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<T> getList() {
		try {
			return this.sessionFactory.getCurrentSession()
					.createCriteria(getType()).list();
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional
	@Override
	public void save(T type) {
		try {
			this.sessionFactory.getCurrentSession().save(type);
			this.sessionFactory.getCurrentSession().flush();
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO007", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		} catch (ConstraintViolationException ex) {
			ApplicationException ex1 = new DataAccessException("DAO008", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public T get(PK key) {
		try {
			return (T) this.sessionFactory.getCurrentSession().get(getType(),
					key);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO001", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public T merge(T type) {
		try {
			T t = (T) this.sessionFactory.getCurrentSession().merge(type);
			this.sessionFactory.getCurrentSession().flush();
			
			return t;
		} catch (HibernateOptimisticLockingFailureException ex) {
			ApplicationException ex1 = new DataAccessException("DAO002", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO002", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (ConstraintViolationException ex) {
			ApplicationException ex1 = new DataAccessException("DAO003", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional
	@Override
	public void update(T type) {
		try {
			this.sessionFactory.getCurrentSession().update(type);
			this.sessionFactory.getCurrentSession().flush();
		} catch (HibernateOptimisticLockingFailureException ex) {
			ApplicationException ex1 = new DataAccessException("DAO002", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO002", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (ConstraintViolationException ex) {
			ApplicationException ex1 = new DataAccessException("DAO003", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional
	@Override
	public void delete(T type) {
		try {
			this.sessionFactory.getCurrentSession().delete(type);
			this.sessionFactory.getCurrentSession().flush();
		} catch (HibernateOptimisticLockingFailureException ex) {
			ApplicationException ex1 = new DataAccessException("DAO005", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO005", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (ConstraintViolationException | IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO006", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	@Override
	public Collection<T> search(Set<Entry> set) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(getType());
			for (Map.Entry entry : set) {
				if (entry.getValue() != null) {
					if (entry.getValue() instanceof String) {
						criteria.add(Restrictions.like((String) entry.getKey(),
								"%" + entry.getValue() + "%").ignoreCase());
					} else {
						criteria.add(Restrictions.eq((String) entry.getKey(),
								entry.getValue()));
					}
				} else {
					criteria.add(Restrictions.isNull((String) entry.getKey()));
				}
			}
			return criteria.list();
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected final Class<T> getType() {
		if (type == null) {
			Class clazz = getClass();
			while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
				clazz = clazz.getSuperclass();
			}
			type = (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass())
					.getActualTypeArguments()[0];
		}
		return type;
	}
}
