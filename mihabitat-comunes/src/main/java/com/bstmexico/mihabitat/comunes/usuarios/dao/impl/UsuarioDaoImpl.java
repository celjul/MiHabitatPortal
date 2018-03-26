package com.bstmexico.mihabitat.comunes.usuarios.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.usuarios.dao.UsuarioDao;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

@Repository("usuarioDao")
public class UsuarioDaoImpl extends GenericDaoImpl<Usuario, Long> implements
		UsuarioDao {
	
	@Transactional
	@Override
	public Usuario get(Long id) {
		try {
			Assert.notNull(id, "DAO010");
			
			Usuario usuario = super.get(id);
			
			getDatosPersona(usuario);
			
			return usuario;
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

	private void getDatosPersona(Usuario usuario) {
		if (usuario != null && usuario.getPersona().getId() != null) {
			usuario.getPersona().getEmails().size();
			usuario.getPersona().getTelefonos().size();
		}
	}
	
	@Transactional
	@Override
	public void save(Usuario usuario) {
		try {
			Assert.notNull(usuario, "DAO010");
			
			if (usuario.getPersona() != null && usuario.getPersona().getId() == null) {
				sessionFactory.getCurrentSession().save(usuario.getPersona());
			}
			
			super.save(usuario);
			
//			usuario.getPersona().setUsuario(usuario);
			sessionFactory.getCurrentSession().update(usuario.getPersona());
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
	public Usuario getByUserName(String usuario) {
		try {
			Assert.notNull(usuario, "DAO010");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Usuario.class);
			if (StringUtils.hasText(usuario)) {
				criteria.add(Restrictions.eq("user", usuario));
			}

			return (Usuario) criteria.uniqueResult();
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
	public Usuario getByEmail(String email) {
		try {
			Assert.notNull(email, "DAO010");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Usuario.class);
			if (StringUtils.hasText(email)) {
				criteria.add(Restrictions.eq("email", email));
			}
			
			Usuario usuario = (Usuario) criteria.uniqueResult();
			
			if (usuario != null && usuario.getId() != null) {
				getDatosPersona(usuario);
			}
			return usuario;
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

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Collection<Usuario> getByRol(CatalogoRol... roles) {
		try {
			Assert.notNull(roles, "SERE001");
			Long ids[] = new Long[roles.length];
			int i = 0;
			for (CatalogoRol rol : roles) {
				ids[i++] = rol.getId();
			}
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Usuario.class);
			criteria.createAlias("roles", "rol");
			criteria.add(Restrictions.disjunction().add(
					Restrictions.in("rol.id", ids)));
			
			Collection<Usuario> usuarios = new LinkedHashSet<Usuario>(criteria.list());
			return new ArrayList<Usuario>(usuarios);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex, getType());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}
}
