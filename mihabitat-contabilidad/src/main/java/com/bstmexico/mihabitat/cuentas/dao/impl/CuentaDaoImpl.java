package com.bstmexico.mihabitat.cuentas.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.dao.CuentaDao;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;

@Repository
public class CuentaDaoImpl extends GenericDaoImpl<Cuenta, Long> implements
		CuentaDao {
	private final static Logger LOG = LoggerFactory
			.getLogger(CuentaDaoImpl.class);

	@Override
	@Transactional(readOnly = true)
	public Cuenta getCuenta(Cuenta cuenta) {
		try {
			Assert.notNull(cuenta, "SERE001");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Cuenta.class);
			if (!StringUtils.isEmpty(cuenta.getNombre())) {
				criteria.add(Restrictions.eq("nombre", cuenta.getNombre()));
			}
			if (cuenta.getId() != null) {
				criteria.add(Restrictions.eq("id", cuenta.getId()));
			}
			if (cuenta.getCondominio() != null) {
				criteria.add(Restrictions.eq("condominio", cuenta.getCondominio()));
			}

			cuenta = (Cuenta) criteria.uniqueResult();
			if (cuenta != null) {
				cuenta.getCuentasHijas();
				if (!CollectionUtils.isEmpty(cuenta.getCuentasHijas())) {
					Iterator<Cuenta> ith = cuenta.getCuentasHijas().iterator();
					while (ith.hasNext()) {
						Cuenta cuentaHija = ith.next();
						cuentaHija.getCuentasHijas();
						cuentaHija.setPadre(null);
						if (!cuentaHija.getActivo()) {
							ith.remove();
						}
						if (!CollectionUtils.isEmpty(cuentaHija
								.getCuentasHijas())) {
							Iterator<Cuenta> itn = cuentaHija.getCuentasHijas()
									.iterator();
							while (itn.hasNext()) {
								Cuenta cuentaNieta = itn.next();
								cuentaNieta.getCuentasHijas();
								cuentaNieta.setPadre(null);
								if (!cuentaNieta.getActivo()) {
									itn.remove();
								}
								if (!CollectionUtils.isEmpty(cuentaNieta
										.getCuentasHijas())) {
									for (Cuenta cuentaBisnieta : cuentaNieta
											.getCuentasHijas()) {
										cuentaBisnieta.setPadre(null);
									}
								}
							}
						}
					}
				}
			}
			return cuenta;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public String getCuentaIncrementar(Long idPadre, Byte nivel) {
		try {
			Assert.notNull(idPadre, "SERE001");
			Assert.notNull(nivel, "SERE001");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Cuenta.class);
			String cuentaIncrementada = "";
			ProjectionList proj = Projections.projectionList();
			switch (nivel) {
			case 2:
				proj.add(Projections.max("numeroHija"));
				break;
			case 3:
				proj.add(Projections.max("numeroNieto"));
				break;
			case 4:
				proj.add(Projections.max("numeroBis"));
				break;
			}
			criteria.setProjection(proj);
			criteria.add(Restrictions.eq("padre.id", idPadre));

			if (criteria.uniqueResult() != null) {
				if (Integer.parseInt((String) criteria.uniqueResult()) <= 9999) {
					cuentaIncrementada = String
							.format("%04d", (Integer.parseInt((String) criteria
									.uniqueResult()) + 1));
				}
			} else {
				cuentaIncrementada = "0001";
			}
			return cuentaIncrementada;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO001", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Cuenta> getCuentaHijos(Cuenta cuenta) {
		try {
			Assert.notNull(cuenta, "SERE001");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Cuenta.class);
			if (!StringUtils.isEmpty(cuenta.getNombre())) {
				criteria.add(Restrictions.eq("nombre", cuenta.getNombre()));
			}
			if (cuenta.getId() != null) {
				criteria.add(Restrictions.eq("id", cuenta.getId()));
			} else {
				criteria.add(Restrictions.eq("activo", Boolean.TRUE));
			}
			criteria.add(Restrictions.eq("condominio", cuenta.getCondominio()));
			Collection<Cuenta> cuentas = new ArrayList<Cuenta>();
			cuenta = (Cuenta) criteria.uniqueResult();
			if (cuenta != null) {
				cuenta.getCuentasHijas();

				if (!CollectionUtils.isEmpty(cuenta.getCuentasHijas())) {
					for (Cuenta cuentaHija : cuenta.getCuentasHijas()) {
						cuentaHija.getCuentasHijas();
						cuentaHija.setPadre(null);
						cuentas.add(cuentaHija);
						if (!CollectionUtils.isEmpty(cuentaHija
								.getCuentasHijas())) {
							for (Cuenta cuentaNieta : cuentaHija
									.getCuentasHijas()) {
								cuentaNieta.getCuentasHijas();
								cuentaNieta.setPadre(null);
								if (!CollectionUtils.isEmpty(cuentaNieta
										.getCuentasHijas())) {
									for (Cuenta cuentaBisnieta : cuentaNieta
											.getCuentasHijas()) {
										cuentaBisnieta.setPadre(null);

									}
								}
							}

						}
					}

				}

			}
			return cuentas;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Cuenta getPadre(Cuenta cuenta) {
		try {
			Assert.notNull(cuenta, "SERE001");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Cuenta.class);
			criteria.add(Restrictions.eq("condominio", cuenta.getCondominio()));
			cuenta = (Cuenta) criteria.uniqueResult();
			if (cuenta != null) {
				for (Cuenta cuentaPadre : cuenta.getCuentasHijas()) {
					cuentaPadre.setCuentasHijas(null);
					cuentaPadre.getPadre();
				}
			}
			return cuenta;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Cuenta get(Long id) {
		try {
			Assert.notNull(id, "SERE001");
			Cuenta cuenta = super.get(id);
			if (cuenta.getPadre() != null) {
				cuenta.getPadre().getId();
			}

			return cuenta;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Cuenta comprobarExistenciaCuenta(Cuenta cuenta) {
		try {
			Assert.notNull(cuenta, "SERE001");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Cuenta.class);
			criteria.add(Restrictions.eq("condominio", cuenta.getCondominio()));
			// criteria.add(Restrictions.eq("activo", cuenta.getActivo()));
			if (cuenta.getNumero() != null) {
				criteria.add(Restrictions.eq("numero", cuenta.getNumero()));
			} else {
				criteria.add(Restrictions.or(Restrictions.isNull("numero"),
						Restrictions.eq("numero", "")));
			}
			if (cuenta.getNumeroHija() != null) {
				criteria.add(Restrictions.eq("numeroHija",
						cuenta.getNumeroHija()));
			} else {
				criteria.add(Restrictions.or(Restrictions.isNull("numeroHija"),
						Restrictions.eq("numeroHija", "")));
			}
			if (cuenta.getNumeroNieto() != null) {
				criteria.add(Restrictions.eq("numeroNieto",
						cuenta.getNumeroNieto()));
			} else {
				criteria.add(Restrictions.or(
						Restrictions.isNull("numeroNieto"),
						Restrictions.eq("numeroNieto", "")));
			}
			if (cuenta.getNumeroBis() != null) {
				criteria.add(Restrictions.eq("numeroBis", cuenta.getNumeroBis()));
			} else {
				criteria.add(Restrictions.or(Restrictions.isNull("numeroBis"),
						Restrictions.eq("numeroBis", "")));
			}
			Cuenta cuentaExistente = (Cuenta) criteria.uniqueResult();
			if (cuentaExistente != null) {
				return cuentaExistente;
			} else {
				return new Cuenta();
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<Cuenta> getList(Condominio condominio) {
		try {
			Assert.notNull(condominio, "SERE001");
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Cuenta.class);
			criteria.add(Restrictions.eq("condominio", condominio));
			Collection<Cuenta> cuentas = criteria.list();
			if (!CollectionUtils.isEmpty(cuentas)) {
				for (Cuenta cuenta : cuentas) {
					cuenta.getPadre();
				}
			}
			return cuentas;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Cuenta get(Condominio condominio, String nombre) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(Cuenta.class);
			criteria.add(Restrictions.eq("nombre", nombre));
			criteria.add(Restrictions.eq("condominio", condominio));
			return (Cuenta) criteria.uniqueResult();
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}
}
