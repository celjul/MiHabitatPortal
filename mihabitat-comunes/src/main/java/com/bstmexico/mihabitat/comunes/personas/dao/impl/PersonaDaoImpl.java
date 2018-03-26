package com.bstmexico.mihabitat.comunes.personas.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.personas.dao.PersonaDao;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class PersonaDaoImpl extends GenericDaoImpl<Persona, Long> implements
		PersonaDao {


	@Transactional(readOnly = true)
	@Override
	public Persona get(Long key) {
		try {
			Assert.notNull(key, "DAO010");
			Persona persona = super.get(key);
			persona.getEmails().size();
			persona.getTelefonos().size();

			return persona;
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

	@Transactional(readOnly = true)
	@Override
	public Persona get(Usuario usuario) {
		try {
			Assert.notNull(usuario, "DAO010");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Usuario.class, "u");
//			criteria.createAlias("usuario", "u");
			if (StringUtils.hasText(usuario.getUser())) {
				criteria.add(Restrictions.eq("u.user", usuario.getUser()));
			}
			if (StringUtils.hasText(usuario.getEmail())) {
				criteria.add(Restrictions.eq("u.email", usuario.getEmail()));
			}
			
			usuario = (Usuario) criteria.uniqueResult();
			if(usuario != null) {
				usuario.getPersona().getEmails().size();
				usuario.getPersona().getTelefonos().size();
			}

			return usuario == null ? null : usuario.getPersona();
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

//	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
//	@Override
//	public Collection<Persona> getList(CatalogoRol... roles) {
//		try {
//			Assert.notNull(roles, "SERE001");
//			Criteria criteria = sessionFactory.getCurrentSession()
//					.createCriteria(Persona.class);
//			criteria.createAlias("usuario", "u");
//			criteria.createAlias("u.roles", "rol");
//			criteria.add(Restrictions.disjunction().add(
//					Restrictions.in("rol", roles)));
//			
//			return criteria.list();
//		} catch (IllegalArgumentException ex) {
//			ApplicationException ex1 = new DataAccessException("DAO004", ex,
//					getType().getSimpleName());
//			LOG.error(ex1.getMessage(), ex);
//			throw ex1;
//		} catch (HibernateException ex) {
//			ApplicationException ex1 = new DataAccessException("DAO009", ex);
//			LOG.error(ex.getMessage(), ex);
//			throw ex1;
//		}
//	}

}
