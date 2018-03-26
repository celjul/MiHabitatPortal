package com.bstmexico.mihabitat.departamentos.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
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
public class DepartamentoDaoImpl extends GenericDaoImpl<Departamento, Long>
		implements DepartamentoDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartamentoDaoImpl.class);

	@Autowired
	private ContactoDao contactoDao;

	@Transactional
	@Override
	public void save(Departamento departamento) {
		try {
			Collection<ContactoDepartamento> contactos = departamento
					.getContactos();
			departamento.setContactos(null);
			this.sessionFactory.getCurrentSession().save(departamento);
			if(contactos != null) {
				for (ContactoDepartamento cd : contactos) {
					cd.setDepartamento(departamento);

					if (cd.getContacto().getId() == null) {
						/*if (cd.getContacto().getPersona().getId() == null) {
							this.sessionFactory.getCurrentSession().save(
									cd.getContacto().getPersona());
						}*/

						this.sessionFactory.getCurrentSession().save(
								cd.getId().getContacto());
					}
					this.sessionFactory.getCurrentSession().merge(cd);
					departamento.addContacto(cd);
				}
			}
			this.sessionFactory.getCurrentSession().merge(departamento);
			this.sessionFactory.getCurrentSession().flush();
			if(departamento.getContactos() != null) {
				for (ContactoDepartamento cd : departamento.getContactos()) {
					cd.getId().setDepartamento(null);
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
	public Departamento get(Long id) {
		Departamento departamento = super.get(id);
		Hibernate.initialize(departamento.getContactos());
		Hibernate.initialize(departamento.getGrupos());
		if(departamento.getContactos() !=null){
			for (ContactoDepartamento cd : departamento.getContactos()) {
				cd.getId().getContacto().getId();
				Hibernate.initialize(cd.getContacto().getEmails());
				Hibernate.initialize(cd.getContacto().getTelefonos());
		//			cd.getContacto().getPersona().getEmails().size();
		//			cd.getContacto().getPersona().getTelefonos().size();

				cd.getId().setDepartamento(null);
			}
		}
		return departamento;
	}

	@Transactional
	@Override
	public void update(Departamento departamento) {
		try {
			Collection<ContactoDepartamento> contactos = departamento
					.getContactos();
			departamento.setContactos(null);
			
			this.sessionFactory.getCurrentSession().update(departamento);
			this.sessionFactory.getCurrentSession().evict(departamento);
			for (ContactoDepartamento cd : contactos) {
				cd.setDepartamento(departamento);
				
				if (cd.getContacto().getId() == null) {
//					if (cd.getContacto().getPersona().getId() == null) {
//						this.sessionFactory.getCurrentSession().save(
//								cd.getContacto().getPersona());
//					} else {
//						this.sessionFactory.getCurrentSession().update(
//								cd.getContacto().getPersona());
//					}

					this.sessionFactory.getCurrentSession().save(
							cd.getContacto());
					//this.sessionFactory.getCurrentSession().save(cd);
				} else {
					this.sessionFactory.getCurrentSession().update(cd.getContacto());
					//this.sessionFactory.getCurrentSession().saveOrUpdate(cd);
					//this.sessionFactory.getCurrentSession().evict(cd);
//					this.sessionFactory.getCurrentSession().update(cd.getContacto().getPersona());
				}
				this.sessionFactory.getCurrentSession().merge(cd);
				departamento.addContacto(cd);
			}
			
			departamento = (Departamento) this.sessionFactory.getCurrentSession().merge(departamento);
			
			this.sessionFactory.getCurrentSession().flush();
			for (ContactoDepartamento cd : departamento.getContactos()) {
				cd.getId().setDepartamento(null);
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

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public Collection<Departamento> search(Set<Entry> map) {
		Collection<Departamento> departamentos = super.search(map);
		for (Departamento departamento : departamentos) {
			departamento.getContactos();
			Hibernate.initialize(departamento.getGrupos());
			for (ContactoDepartamento cd : departamento.getContactos()) {
				cd.getId().getContacto().getId();
				cd.getId().setDepartamento(null);
			}
		}
		return departamentos;
	}

	@Transactional(readOnly = true)
	@Override
	public Departamento get(Departamento departamento) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Departamento.class);
			criteria.add(Restrictions.eq("nombre", departamento.getNombre()));
			criteria.add(Restrictions.eq("condominio",
					departamento.getCondominio()));
			departamento = (Departamento) criteria.uniqueResult();
			if (departamento != null) {
				departamento.getCondominio().getId();
				departamento.getContactos();
				departamento.getGrupos().size();
				for (ContactoDepartamento cd : departamento.getContactos()) {
					cd.getId().getContacto().getId();
					cd.getId().getContacto().getEmails().size();
					cd.getId().getContacto().getTelefonos().size();
					cd.getId().setDepartamento(null);
				}
			}
			return departamento;
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

//	private void fixContacto(Contacto anterior, Contacto nuevo) {
//		anterior.setNombre(nuevo.getNombre());
//		anterior.setApellidoPaterno(nuevo.getApellidoPaterno());
//		anterior.setApellidoMaterno(nuevo.getApellidoMaterno());
//		for (Email email : anterior.getEmails()) {
//			this.sessionFactory.getCurrentSession().evict((email));
//		}
//		anterior.getEmails().clear();
//		anterior.getEmails().addAll(nuevo.getEmails());
//		for (Telefono telefono : anterior.getTelefonos()) {
//			this.sessionFactory.getCurrentSession().evict((telefono));
//		}
//		anterior.getTelefonos().clear();
//		anterior.getTelefonos().addAll(nuevo.getTelefonos());
//		nuevo.setDepartamentos(anterior.getDepartamentos());
//	}

//	@Transactional(readOnly = true)
//	@Override
//	public Collection<Condominio> search(Usuario usuario) {
//		try {
//			Assert.notNull(usuario, "SERE001");
//			Assert.notNull(usuario.getId(), "SEREX003");
//			Criteria criteria = sessionFactory.getCurrentSession()
//					.createCriteria(Contacto.class);
//			criteria.createAlias("usuario", "u");
//			criteria.add(Restrictions.in("u.id",
//					new Object[] { usuario.getId() }));
//			Contacto contacto = (Contacto) criteria.uniqueResult();
//			Set<Condominio> condominios = null;
//			if (contacto != null) {
//				contacto.getDepartamentos();
//				for (ContactoDepartamento cd : contacto.getDepartamentos()) {
//					cd.getId().getDepartamento().getId();
//					cd.getId().setContacto(null);
//					cd.getId().getDepartamento().getCondominio().getId();
//				}
//				condominios = new HashSet<Condominio>();
//				for (ContactoDepartamento cd : contacto.getDepartamentos()) {
//					condominios.add(cd.getId().getDepartamento()
//							.getCondominio());
//				}
//			}
//			return condominios;
//		} catch (IllegalArgumentException ex) {
//			ApplicationException ex1 = new DataAccessException("DAO004", ex,
//					getType().getSimpleName());
//			LOG.error(ex1.getMessage(), ex);
//			throw ex1;
//
//		} catch (HibernateException ex) {
//			ApplicationException ex1 = new DataAccessException("DAO009", ex);
//			LOG.error(ex.getMessage(), ex);
//			throw ex1;
//		}
//	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<Condominio> search(Usuario usuario) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Contacto.class);
			criteria.add(Restrictions.eq("usuario", usuario));
			Collection<Contacto> contactos = criteria.list();
			Set<Condominio> condominios = new HashSet<Condominio>();
			if (!CollectionUtils.isEmpty(contactos)) {
				for (Contacto contacto : contactos) {
					contacto.getCondominio().getId();
					contacto.getCondominio().getAdministradores().size();
					for(Usuario usr : contacto.getCondominio().getAdministradores()){
						usr.getPersona().getEmails().size();
						usr.getPersona().getTelefonos().size();
					}
					condominios.add(contacto.getCondominio());
				}
			}
			return condominios;
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
	
	@SuppressWarnings({ "unchecked" })
	@Transactional(readOnly = true)
	@Override
	public List<Departamento> searchByCond(Long id) {
		List<Departamento> list = null;
		try {
			
			Query query = sessionFactory.getCurrentSession().createQuery("from Departamento where NIdCondominio =  "+id);
			list = query.list();
		}catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;

		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
		return list;
	}
	
}
