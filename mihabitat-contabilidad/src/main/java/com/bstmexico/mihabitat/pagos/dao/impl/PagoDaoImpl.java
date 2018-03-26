package com.bstmexico.mihabitat.pagos.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.dao.PagoDao;
import com.bstmexico.mihabitat.pagos.model.CatalogoEstatusPago;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class PagoDaoImpl extends GenericDaoImpl<Pago, Long> implements PagoDao {

	@Transactional(readOnly = true)
	@Override
	public Pago get(Long key) {
		Pago pago = super.get(key);
		pago.getCondominio().getId();
		if (!CollectionUtils.isEmpty(pago.getCondominio().getAdministradores())) {
			for (Usuario u : pago.getCondominio().getAdministradores()) {
				u.getPersona().getEmails().size();
			}
		}
		Hibernate.initialize(pago.getContacto());
		if (pago.getContacto() != null) {
			Hibernate.initialize(pago.getContacto().getEmails());
		}
		if (pago.getCuenta() != null) {
			pago.getCuenta().getId();
		}
		if (!CollectionUtils.isEmpty(pago.getPagosDepartamento())) {
			for (PagoDepartamento pd : pago.getPagosDepartamento()) {
				Hibernate.initialize(pd.getDepartamento().getGrupos());
				if (!CollectionUtils.isEmpty(pd.getMovimientos())) {
					for (MovimientoCargoAplicado aplicado : pd.getMovimientos()) {
						Hibernate.initialize(aplicado.getMovimientoCargo().getCargo().getMovimientos());
						if (!CollectionUtils.isEmpty(aplicado.getMovimientoCargo()
								.getCargo().getMovimientos())) {
							for (MovimientoCargo movimiento : aplicado
									.getMovimientoCargo().getCargo().getMovimientos()) {
								movimiento.getAplicados().size();
							}
						}
					}
				}
			}
		}
		return pago;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public Collection<Pago> search(Set<Entry> set) {
		Collection<Pago> pagos = super.search(set);
		if (!CollectionUtils.isEmpty(pagos)) {
			for (Pago pago : pagos) {
				Hibernate.initialize(pago.getPagosDepartamento());
				for(PagoDepartamento pd : pago.getPagosDepartamento()) {
					Hibernate.initialize(pd.getMovimientos());
					Hibernate.initialize(pd.getDepartamento().getGrupos());
					for (MovimientoCargoAplicado movimientoCargoAplicado : pd.getMovimientos()) {
						Hibernate.initialize(movimientoCargoAplicado.getMovimientoCargo().getCargo().getDepartamento());
					}
				}
				pago.getContacto().getEmails().size();
				pago.getContacto().getDepartamentos().size();
				for(ContactoDepartamento contactoDepartamento : pago.getContacto().getDepartamentos()) {
					contactoDepartamento.setContacto(null);
				}
			}
		}
		return pagos;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public Collection<Pago> getList(Condominio condominio, Date inicio, Date fin) {
		Collection<Pago> pagos = new ArrayList<>();
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Pago.class);
		criteria.add(Restrictions.between("fecha", inicio, fin));
		criteria.add(Restrictions.eq("condominio",condominio));
		pagos = criteria.list();
		if (!CollectionUtils.isEmpty(pagos)) {
			for (Pago pago : pagos) {
				Hibernate.initialize(pago.getPagosDepartamento());
				for(PagoDepartamento pd : pago.getPagosDepartamento()) {
					Hibernate.initialize(pd.getMovimientos());
					Hibernate.initialize(pd.getDepartamento().getGrupos());
					for (MovimientoCargoAplicado movimientoCargoAplicado : pd.getMovimientos()) {
						Hibernate.initialize(movimientoCargoAplicado.getMovimientoCargo().getCargo().getDepartamento().getGrupos());
					}
				}
				pago.getContacto().getEmails().size();
				pago.getContacto().getDepartamentos().size();
				for(ContactoDepartamento contactoDepartamento : pago.getContacto().getDepartamentos()) {
					contactoDepartamento.setContacto(null);
				}
				for(EstatusPago estatusPago : pago.getEstatus()) {
					estatusPago.getUsuario().getUser();
				}
			}
		}
		return pagos;
	}

	@Transactional
	@Override
	public void save(Pago pago) {
		super.save(pago);
	}

	@Transactional(readOnly = true)
	@Override
	public Long getLastFolio(Condominio condominio){
		try {
			Long folio = 1L;
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Pago.class);
			ProjectionList proj = Projections.projectionList();
			proj.add(Projections.max("folio"));
			criteria.setProjection(proj);
			criteria.add(Restrictions.eq("condominio", condominio));
			if (criteria.uniqueResult() != null) {
				folio = (Long.parseLong(criteria
						.uniqueResult().toString()) + 1);
			}
			return folio;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public Collection<Pago> getPagosByStatus(Condominio condominio,CatalogoEstatusPago estatus) {
		try {
			String query = "SELECT p FROM Pago p join p.estatus e"
					+ " WHERE e.id =  (SELECT MAX(id) " +
					"FROM EstatusPago " +
					"WHERE pago.id = p.id) AND e.estatus.id = :estatus " +
					"AND p.condominio.id = :condominio ";

			Collection<Pago> pagos = (Collection<Pago>)sessionFactory.getCurrentSession()
					.createQuery(query).setParameter("estatus", estatus.getId())
					.setParameter("condominio", condominio.getId()).list();

			for (Pago pago : pagos) {
				Hibernate.initialize(pago.getContacto());
				Hibernate.initialize(pago.getContacto().getUsuario());
				Hibernate.initialize(pago.getPagosDepartamento());
				if (!CollectionUtils.isEmpty(pago.getPagosDepartamento())) {
					for (PagoDepartamento pd : pago.getPagosDepartamento()) {
						Hibernate.initialize(pd.getMovimientos());
						Hibernate.initialize(pd.getDepartamento().getGrupos());
						if (!CollectionUtils.isEmpty(pd.getMovimientos())) {
							for (MovimientoCargoAplicado mov : pd.getMovimientos()) {
								Hibernate.initialize(mov.getMovimientoCargo());
								Hibernate.initialize(mov.getMovimientoCargo().getCargo());
							}
						}
					}
				}
			}

			return pagos;
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

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public Collection<Pago> getPagosByStatus(Contacto contacto,CatalogoEstatusPago estatus) {
		try {
			String query = "SELECT p FROM Pago p join p.estatus e"
					+ " WHERE e.id =  (SELECT MAX(id) " +
					"FROM EstatusPago " +
					"WHERE pago.id = p.id) AND e.estatus.id = :estatus " +
					"AND p.contacto.id = :contacto ";

			Collection<Pago> pagos = (Collection<Pago>)sessionFactory.getCurrentSession()
					.createQuery(query).setParameter("estatus", estatus.getId())
					.setParameter("contacto", contacto.getId()).list();

			for (Pago pago : pagos) {
				pago.getContacto().getUsuario().getEmail();
			}

			return pagos;
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

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public EstatusPago getPagoByStatus(Long idEstatusPago) {
		try {
			String query = "SELECT e FROM EstatusPago e "
					+ " WHERE e.id = :estatusPago ";

			EstatusPago estatusPago = (EstatusPago)sessionFactory.getCurrentSession()
					.createQuery(query).setParameter("estatusPago", idEstatusPago).uniqueResult();

			return estatusPago;
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

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public Collection<PagoDepartamento> getPagos(Departamento departamento) {
		try {


			/*String query = "SELECT distinct p FROM PagoDepartamento p join p.movimientos mp join mp.movimientoCargo mc " +
					"Where mc.cargo.departamento.id = :departamento";

			*//*String query = "SELECT p FROM Pago p join p.estatus e"
					+ " WHERE e.id =  (SELECT MAX(id) " +
					"FROM EstatusPago " +
					"WHERE pago.id = p.id) AND e.estatus.id = :estatus " +
					"AND p.contacto.id = :contacto ";*//*

			Collection<Pago> pagos = (Collection<Pago>)sessionFactory.getCurrentSession()
					.createQuery(query).setParameter("departamento", departamento.getId())
					.list();*/
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(PagoDepartamento.class);
			criteria.add(Restrictions.eq("departamento", departamento));
			Collection<PagoDepartamento> pds = criteria.list();

			if (!CollectionUtils.isEmpty(pds)) {
				for (PagoDepartamento pd : pds) {
					Hibernate.initialize(pd.getPagoCondomino());
						Hibernate.initialize(pd.getMovimientos());
						Hibernate.initialize(pd.getDepartamento().getGrupos());
						for (MovimientoCargoAplicado movimientoCargoAplicado : pd.getMovimientos()) {
							Hibernate.initialize(movimientoCargoAplicado.getMovimientoCargo().getCargo().getDepartamento());
							movimientoCargoAplicado.setPago(null);
						}
					pd.getPagoCondomino().getContacto().getEmails().size();
					pd.getPagoCondomino().getContacto().getDepartamentos().size();
					for(ContactoDepartamento contactoDepartamento : pd.getPagoCondomino().getContacto().getDepartamentos()) {
						contactoDepartamento.setContacto(null);
					}
					for(EstatusPago ep : pd.getPagoCondomino().getEstatus()) {
						ep.getUsuario().getId();
					}
				}
			}

			return pds;
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
	public void evict(Pago pago) {
		this.sessionFactory.getCurrentSession().evict(pago);
	}
}
