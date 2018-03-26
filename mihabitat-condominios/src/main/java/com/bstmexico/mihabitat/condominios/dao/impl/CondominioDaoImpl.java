package com.bstmexico.mihabitat.condominios.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.dao.CondominioDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class CondominioDaoImpl extends GenericDaoImpl<Condominio, Long>
		implements CondominioDao {

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<Condominio> search(Usuario usuario) {
		try {
			Assert.notNull(usuario, "SERE001");
			Assert.notNull(usuario.getId(), "SEREX003");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Condominio.class);
			criteria.createAlias("administradores", "adm");
			criteria.add(Restrictions.in("adm.id",
					new Object[] { usuario.getId() }));
			return criteria.list();
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;

		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Condominio readConImagen(Long id) {
		try {
			Condominio condominio = super.get(id);
			Hibernate.initialize(condominio.getLogoCondominio());
			condominio.getLogoCondominio();
			Hibernate.initialize(condominio.getConfiguracionCondominio());
			return condominio;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;

		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Condominio readConConfiguracion(Long id) {
		try {
			Condominio condominio = super.get(id);
			Hibernate.initialize(condominio.getConfiguracionCondominio());
			return condominio;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;

		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}
}
