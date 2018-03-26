package com.bstmexico.mihabitat.contactos.dao.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.dao.ContactoDao;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.dao.DepartamentoDao;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class ContactoDaoImpl extends GenericDaoImpl<Contacto, Long> implements
		ContactoDao {

	@Autowired
	private DepartamentoDao departamentoDao;

	@Transactional
	@Override
	public void save(Contacto contacto) {
		try {
			Collection<ContactoDepartamento> contactos = 
					contacto.getDepartamentos();
			
//			if (contacto.getPersona().getId() == null) {
//				this.sessionFactory.getCurrentSession()
//						.save(contacto.getPersona());
//			}
//			
			if (contacto.getDepartamentos() != null 
					&& !contacto.getDepartamentos().isEmpty()) {
				contacto.setDepartamentos(null);
			}
			super.save(contacto);
			
			if (CollectionUtils.isNotEmpty(contactos)) {
				for (ContactoDepartamento cd : contactos) {
					if (cd.getDepartamento() != null && cd.getDepartamento()
							.getId() == null) {
						this.sessionFactory.getCurrentSession()
							.save(cd.getDepartamento());
					}
					
					this.sessionFactory.getCurrentSession().save(cd);
				}
			}
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

	@Transactional(readOnly = true)
	@Override
	public Contacto get(Long id) {
		Contacto contacto = super.get(id);
		if (contacto != null) {
			contacto.getEmails().size();
			contacto.getTelefonos().size();
			contacto.getDepartamentos();

			for (ContactoDepartamento cd : contacto.getDepartamentos()) {
				cd.getId().getDepartamento().getId();
				cd.getId().setContacto(null);
			}
		}
		return contacto;
	}

	@Transactional
	@Override
	public void update(Contacto contacto) {
		try {
			Collection<ContactoDepartamento> departamentos = contacto
					.getDepartamentos();
			if (departamentos != null && !departamentos.isEmpty()) {
				contacto.setDepartamentos(null);
				for (ContactoDepartamento cd : departamentos) {
					cd.getId().setContacto(contacto);
					if (cd.getDepartamento().getId() == null) {
						this.sessionFactory.getCurrentSession().save(
								cd.getDepartamento());
					} else {
						Departamento departamento = departamentoDao.get(cd
								.getDepartamento().getId());
						fixDepartamento(departamento, cd.getDepartamento());
					}
					contacto.addDepartamento(cd);
				}
			} else {
				contacto.setDepartamentos(new ArrayList<ContactoDepartamento>());
				contacto.getDepartamentos().clear();
			}
			this.sessionFactory.getCurrentSession().merge(contacto);
			this.sessionFactory.getCurrentSession().flush();
			if (contacto.getDepartamentos() != null) {
				for (ContactoDepartamento cd : contacto.getDepartamentos()) {
					cd.getId().setContacto(null);
				}
			}
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

	@Transactional(readOnly = true)
	@SuppressWarnings("rawtypes")
	@Override
	public Collection<Contacto> search(Set<Entry> map) {
		Collection<Contacto> contactos = super.search(map);
		for (Contacto contacto : contactos) {
//			contacto.getPersona().getEmails().size();
//			contacto.getPersona().getTelefonos().size();
			Hibernate.initialize(contacto.getEmails());
			Hibernate.initialize(contacto.getTelefonos());
			contacto.getDepartamentos().size();
			for (ContactoDepartamento cd : contacto.getDepartamentos()) {
				Hibernate.initialize(cd.getId().getDepartamento().getId());
				Hibernate.initialize(cd.getId().getDepartamento().getGrupos());
				cd.getId().setContacto(null);
			}
		}
		return contactos;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("rawtypes")
	@Override
	public Collection<Contacto> searchConConfNot(Set<Entry> map) {
		Collection<Contacto> contactos = super.search(map);
		for (Contacto contacto : contactos) {
//			contacto.getPersona().getEmails().size();
//			contacto.getPersona().getTelefonos().size();
			Hibernate.initialize(contacto.getEmails());
			Hibernate.initialize(contacto.getTelefonos());
			contacto.getDepartamentos().size();
			for (ContactoDepartamento cd : contacto.getDepartamentos()) {
				Hibernate.initialize(cd.getId().getDepartamento().getId());
				Hibernate.initialize(cd.getId().getDepartamento().getGrupos());
				Hibernate.initialize(cd.getConfiguracionNotificacionContacto());
				cd.getId().setContacto(null);
			}
		}
		return contactos;
	}

	@Transactional(readOnly = true)
	@Override
	public Contacto get(Condominio condominio, String email) {
		try {
			Assert.notNull(condominio, "DAO010");
			Assert.notNull(email, "DAO010");
			Assert.hasLength(email, "DAO011");

			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Contacto.class);
			criteria.createAlias("usuario", "u");
			criteria.add(Restrictions.like("u.email", email));
			criteria.add(Restrictions.eq("condominio.id", condominio.getId()));

			Contacto contacto = (Contacto) criteria.uniqueResult();
			if (contacto != null) {
				contacto.getCondominio().getId();
				contacto.getEmails().size();
				contacto.getTelefonos().size();
				contacto.getDepartamentos();
				for (ContactoDepartamento cd : contacto.getDepartamentos()) {
					cd.getId().getDepartamento().getId();
					cd.getId().setContacto(null);
				}
			}
			return contacto;
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
	public Contacto get(Long idUsuario, String email) {
		try {
			Assert.notNull(idUsuario, "DAO010");
			Assert.notNull(email, "DAO010");
			Assert.hasLength(email, "DAO011");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Contacto.class);
			criteria.createAlias("usuario", "u");
			criteria.add(Restrictions.eq("u.id", idUsuario));
			criteria.add(Restrictions.like("u.email", email));

			Contacto contacto = (Contacto) criteria.uniqueResult();
			if (contacto != null) {
				contacto.getCondominio().getId();
				contacto.getDepartamentos();
				for (ContactoDepartamento cd : contacto.getDepartamentos()) {
					cd.getId().getDepartamento().getId();
					cd.getId().setContacto(null);
				}
			}
			return contacto;
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
	@Transactional(readOnly = true)
	@Override
	public Collection<Contacto> buscar(Condominio condominio, String key) {

		key = Normalizer.normalize(key, Normalizer.Form.NFKD).replaceAll(
				"\\p{Block=CombiningDiacriticalMarks}+", "");
		try {
			String[] palabras = key.split(" ");
			Collection<Contacto> contactos = new HashSet<>();

			for(String p : palabras){
				if(p.length() > 1) {
					Criteria criteriaNombre = getCriteria(condominio);
					criteriaNombre.add(Restrictions.ilike("nombre", p,
							MatchMode.ANYWHERE));
					contactos.addAll(criteriaNombre.list());

					Criteria criteriaApellidoPaterno = getCriteria(condominio);
					criteriaApellidoPaterno.add(Restrictions.ilike("apellidoPaterno",
							p, MatchMode.ANYWHERE));
					contactos.addAll(criteriaApellidoPaterno.list());

					Criteria criteriaApellidoMaterno = getCriteria(condominio);
					criteriaApellidoMaterno.add(Restrictions.ilike("apellidoMaterno",
							p, MatchMode.ANYWHERE));
					contactos.addAll(criteriaApellidoMaterno.list());
				}
			}



			for (Contacto contacto : contactos) {
				Hibernate.initialize(contacto.getDepartamentos());
			}

			return contactos;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	private Criteria getCriteria(Condominio condominio) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Contacto.class);
		criteria.add(Restrictions.eq("condominio", condominio));
		return criteria;
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<ContactoDepartamento> getDepartamentos(Long id) {
		try {
			Contacto contacto = get(id);
			if (contacto != null) {
				for (ContactoDepartamento cd : contacto.getDepartamentos()) {
					cd.getId().getDepartamento().getId();
					cd.getId().setContacto(null);
				}
			}
			return contacto.getDepartamentos();
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	private void fixDepartamento(Departamento anterior, Departamento nuevo) {
		anterior.setMantenimiento(nuevo.getMantenimiento());
		anterior.setGrupos(nuevo.getGrupos());
		anterior.setObservaciones(nuevo.getObservaciones());
		nuevo.setContactos(anterior.getContactos());
	}
}
