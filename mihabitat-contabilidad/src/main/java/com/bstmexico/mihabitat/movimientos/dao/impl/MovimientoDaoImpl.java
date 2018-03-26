package com.bstmexico.mihabitat.movimientos.dao.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

import com.bstmexico.mihabitat.cargos.factory.CargoFactory;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.MovimientoIngresoNoIdentificado;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.*;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoConceptoOtroIngreso;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoOtroIngreso;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.transferencias.model.MovimientoDeposito;
import com.bstmexico.mihabitat.transferencias.model.MovimientoRetiro;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.departamentos.dao.DepartamentoDao;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.dao.MovimientoDao;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoDetallle;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoGasto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class MovimientoDaoImpl extends GenericDaoImpl<Movimiento, Long>
		implements MovimientoDao {

	private static Logger LOG = LoggerFactory
			.getLogger(MovimientoDaoImpl.class);

	@Autowired
	private DepartamentoDao departamentoDao;

	private static Properties cfg;

	public MovimientoDaoImpl() {
		try {
			cfg = PropertiesLoaderUtils.loadAllProperties("configuration.properties");
		} catch (IOException e) {
			LOG.error("Error al cargar el archivo de propiedades");
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Movimiento get(Long id, Class<? extends Movimiento> movimiento) {
		try {
			Movimiento m = (Movimiento) this.sessionFactory.getCurrentSession()
					.get(movimiento, id);
			return m;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO001", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public MovimientoCargo getConCargo(Long id,
			Class<? extends Movimiento> movimiento) {
		Session session = this.sessionFactory.openSession();
		try {
			MovimientoCargo m = (MovimientoCargo) session.get(movimiento, id);
			if(m != null) {
				Hibernate.initialize(m.getCargo());
				if(m.getCargo() != null) {
					m.getCargo()
							.setDepartamento(
									departamentoDao.get(m.getCargo().getDepartamento()
											.getId()));
					m.getCargo().getCondominio().getId();
				}
			}
			return m;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO001", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		} finally {
			session.close();
		}
	}

	@Transactional
	@Override
	public void delete(Movimiento movimiento) {
		try {
			SQLQuery query = this.sessionFactory
					.getCurrentSession()
					.createSQLQuery(
							"DELETE FROM tmovimientos WHERE NIdMovimiento = :id");
			query.setParameter("id", movimiento.getId());
			query.executeUpdate();
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO005", ex,
					getType().getSimpleName());
			LOG.error(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = true)
	@Override
	public Collection<Movimiento> search(Set<Entry> set,
			Class<? extends Movimiento> movimiento) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(movimiento);
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
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<MovimientoSaldo> getMovimientos(Contacto contacto) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteria.add(Restrictions.eq("contacto", contacto));
			Collection<MovimientoSaldo> movs = criteria.list();
			for(MovimientoSaldo ms : movs) {
				Hibernate.initialize(ms.getDepartamento());
			}
			return movs;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	/*@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<MovimientoSaldo> getMovimientosSaldo(Departamento departamento) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteria.add(Restrictions.eq("departamento", departamento));
			Collection<MovimientoSaldo> movs = criteria.list();
			for(MovimientoSaldo ms : movs) {
				Hibernate.initialize(ms.getDepartamento());
			}
			return movs;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}*/

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<MovimientoSaldo> getMovimientosSaldo(Departamento departamento, Date inicio, Date fin) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteria.add(Restrictions.eq("departamento", departamento));
			if (inicio != null) {
				criteria.add(Restrictions.between("fecha", inicio, fin));
			} else if (fin != null) {
				criteria.add(Restrictions.le("fecha", fin));
			}
			Collection<MovimientoSaldo> movs = criteria.list();
			for(MovimientoSaldo ms : movs) {
				Hibernate.initialize(ms.getDepartamento());
			}
			return movs;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<MovimientoSaldo> getMovimientos(Contacto contacto, Date inicio, Date fin) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteria.add(Restrictions.eq("contacto", contacto));
			if (inicio != null) {
				criteria.add(Restrictions.between("fecha", inicio, fin));
			} else if (fin != null) {
				criteria.add(Restrictions.le("fecha", fin));
			}
			Collection<MovimientoSaldo> movs = criteria.list();
			for(MovimientoSaldo ms : movs) {
				Hibernate.initialize(ms.getDepartamento());
			}
			return movs;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<MovimientoSaldo> getMovimientosSaldo(Departamento departamento) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteria.add(Restrictions.eq("departamento", departamento));
			criteria.addOrder(Order.asc("fecha"));
			Collection<MovimientoSaldo> saldos = criteria.list();
			for(MovimientoSaldo saldo : saldos) {
				/*if(saldo.getPago() != null) {
					Hibernate.initialize(saldo.getPago().getMovimientos());
					for(MovimientoCargoAplicado movimientoCargoAplicado : saldo.getPago().getMovimientos()) {
						Hibernate.initialize(movimientoCargoAplicado.getMovimientoCargo().getCargo());
					}
				}*/
				if(saldo.getPagoDepartamento() != null) {
					Hibernate.initialize(saldo.getPagoDepartamento().getPagoCondomino());
					Hibernate.initialize(saldo.getPagoDepartamento().getMovimientos());
					for(MovimientoCargoAplicado movimientoCargoAplicado : saldo.getPagoDepartamento().getMovimientos()) {
						Hibernate.initialize(movimientoCargoAplicado.getMovimientoCargo().getCargo());
					}
				}
			}
			return saldos;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<Movimiento> getMovimientos(Departamento departamento,
			Date inicio, Date fin) {
		try {
			List<Movimiento> movimientos = new ArrayList<Movimiento>();

			Collection<Movimiento> cargos = new ArrayList<Movimiento>();
			Criteria criteriaCargos = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargo.class);
			criteriaCargos.createAlias("cargo", "c");
			criteriaCargos.add(Restrictions.eq("c.departamento", departamento));
			if (inicio != null) {
				criteriaCargos.add(Restrictions.between("fecha", inicio, fin));
			} else {
				criteriaCargos.add(Restrictions.lt("fecha", fin));
			}
			cargos = criteriaCargos.list();




			Criteria criteriaPagoDepartamento = sessionFactory.getCurrentSession()
					.createCriteria(PagoDepartamento.class);
			criteriaPagoDepartamento.add(Restrictions.eq("departamento", departamento));
			if (inicio != null) {
				criteriaPagoDepartamento.createAlias("pagoCondomino", "pc");
				criteriaPagoDepartamento.add(Restrictions.between("pc.fecha", inicio, fin));
			} else {
				criteriaPagoDepartamento.createAlias("pagoCondomino", "pc");
				criteriaPagoDepartamento.add(Restrictions.lt("pc.fecha", fin));
			}
			Collection<PagoDepartamento>  pds = criteriaPagoDepartamento.list();
			Collection<Movimiento> tempMcas = new ArrayList<>();
			for(PagoDepartamento pd : pds) {
				EstatusPago ultimoEstatus = null;
				for (EstatusPago estatusPago : pd.getPagoCondomino().getEstatus()) {
					ultimoEstatus = estatusPago;
				}
				if(ultimoEstatus.getEstatus().getId().equals(Long.valueOf(cfg.getProperty("aprobado")))) {
					if(((pd.getAplicadoCargos()).compareTo(BigDecimal.ZERO) > 0) && (!pd.getPagoCondomino().getMetodoPago().getId().equals(Long.valueOf(cfg.getProperty("saldoafavor"))))) {
						MovimientoCargoAplicado nvoMca = MovimientoFactory.newInstance(MovimientoCargoAplicado.class, pd.getId());
						nvoMca.setAplicado(Boolean.TRUE);
						nvoMca.setCancelado(Boolean.FALSE);
						nvoMca.setCuenta(null);
						nvoMca.setImprimible(Boolean.TRUE);
						nvoMca.setMovimientoCargo((MovimientoCargo) MovimientoFactory.newInstance(MovimientoCargo.class));
							nvoMca.getMovimientoCargo().setCargo((CargoDepartamento) CargoFactory.newInstance(CargoDepartamento.class));
							nvoMca.getMovimientoCargo().getCargo().setConcepto("Gracias por su Pago");
						nvoMca.setPago(null);
						nvoMca.setPagoDepartamento(pd);
						nvoMca.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("pagocargo"))));
							nvoMca.getTipo().setDescripcion("Abono");
						nvoMca.setDebe(null);
						nvoMca.setFecha(pd.getPagoCondomino().getFecha());
						nvoMca.setHaber(pd.getAplicadoCargos());
						tempMcas.add(nvoMca);
					}
					if(!pd.getPagoCondomino().getMetodoPago().getId().equals(Long.valueOf(cfg.getProperty("saldoafavor")))
							&& (pd.getMonto().subtract(pd.getAplicadoCargos()).compareTo(BigDecimal.ZERO) > 0)) {
						MovimientoSaldo movSaldo = MovimientoFactory.newInstance(MovimientoSaldo.class, pd.getId());
						movSaldo.setCuenta(null);
						movSaldo.setPago(null);
						movSaldo.setPagoDepartamento(pd);
						movSaldo.setDebe(null);
						movSaldo.setFecha(pd.getPagoCondomino().getFecha());
						movSaldo.setHaber(pd.getMonto().subtract(pd.getAplicadoCargos()));
						movSaldo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("saldoafavorgenerado"))));
							movSaldo.getTipo().setDescripcion("Saldo a Favor Generado");
						tempMcas.add(movSaldo);
					} else if (pd.getPagoCondomino().getMetodoPago().getId().equals(Long.valueOf(cfg.getProperty("saldoafavor")))) {
						MovimientoSaldo movSaldo = MovimientoFactory.newInstance(MovimientoSaldo.class, pd.getId());
						movSaldo.setCuenta(null);
						movSaldo.setPago(null);
						movSaldo.setPagoDepartamento(pd);
						movSaldo.setDebe(null);
						movSaldo.setFecha(pd.getPagoCondomino().getFecha());
						movSaldo.setHaber(pd.getMonto());
						movSaldo.setTipo((CatalogoTipoMovimiento) CatalogoFactory.newInstance(CatalogoTipoMovimiento.class, Long.valueOf(cfg.getProperty("aplicaciondesaldoafavor"))));
							movSaldo.getTipo().setDescripcion("Aplicaci?n de Saldo a Favor");
						tempMcas.add(movSaldo);
					}
				}
			}

			movimientos.addAll(cargos);
			movimientos.addAll(tempMcas);
			/*movimientos.addAll(saldos);*/

			Collections.sort(movimientos);
			return movimientos;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getDebe(Date fin, Cuenta cuenta) {
		try {

			Criteria criteriaCA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargoAplicado.class);
			criteriaCA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCA = Projections.projectionList();
			projectionCA.add(Projections.sum("debe"));
			criteriaCA.setProjection(projectionCA);
			criteriaCA.add(Restrictions.lt("fecha", fin));
			BigDecimal debeCA = (BigDecimal) criteriaCA.uniqueResult();

			Criteria criteriaS = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteriaS.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionS = Projections.projectionList();
			projectionS.add(Projections.sum("debe"));
			criteriaS.setProjection(projectionS);
			criteriaS.add(Restrictions.lt("fecha", fin));
			BigDecimal debeS = (BigDecimal) criteriaS.uniqueResult();

			Criteria criteriaP = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPago.class);
			criteriaP.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionP = Projections.projectionList();
			projectionP.add(Projections.sum("debe"));
			criteriaP.setProjection(projectionP);
			criteriaP.add(Restrictions.lt("fecha", fin));
			BigDecimal debeP = (BigDecimal) criteriaP.uniqueResult();

			Criteria criteriaCfdiA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCfdiAplicado.class);
			criteriaCfdiA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCfdiA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCfdiA = Projections.projectionList();
			projectionCfdiA.add(Projections.sum("debe"));
			criteriaCfdiA.setProjection(projectionCfdiA);
			criteriaCfdiA.add(Restrictions.lt("fecha", fin));
			BigDecimal debeCfdiA = (BigDecimal) criteriaCfdiA.uniqueResult();

			Criteria criteriaE = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPagoProveedor.class);
			criteriaE.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionE = Projections.projectionList();
			projectionE.add(Projections.sum("debe"));
			criteriaE.setProjection(projectionE);
			criteriaE.add(Restrictions.lt("fecha", fin));
			BigDecimal debeE = (BigDecimal) criteriaE.uniqueResult();

			Criteria criteriaG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoGasto.class);
			criteriaG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionG = Projections.projectionList();
			projectionG.add(Projections.sum("debe"));
			criteriaG.setProjection(projectionG);
			criteriaG.add(Restrictions.lt("fecha", fin));
			BigDecimal debeG = (BigDecimal) criteriaG.uniqueResult();
			
			Criteria criteriaDG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDetallle.class);
			criteriaDG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDG = Projections.projectionList();
			projectionDG.add(Projections.sum("debe"));
			criteriaDG.setProjection(projectionDG);
			criteriaDG.add(Restrictions.lt("fecha", fin));
			BigDecimal debeDG = (BigDecimal) criteriaDG.uniqueResult();

			Criteria criteriaOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoOtroIngreso.class);
			criteriaOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionOtrIngr = Projections.projectionList();
			projectionOtrIngr.add(Projections.sum("debe"));
			criteriaOtrIngr.setProjection(projectionOtrIngr);
			criteriaOtrIngr.add(Restrictions.lt("fecha", fin));
			BigDecimal debeOtrIngr = (BigDecimal) criteriaOtrIngr.uniqueResult();

			Criteria criteriaConcOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoConceptoOtroIngreso.class);
			criteriaConcOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionConcOtrIngr = Projections.projectionList();
			projectionConcOtrIngr.add(Projections.sum("debe"));
			criteriaConcOtrIngr.setProjection(projectionConcOtrIngr);
			criteriaConcOtrIngr.add(Restrictions.lt("fecha", fin));
			BigDecimal debeConcOtrIngr = (BigDecimal) criteriaConcOtrIngr.uniqueResult();

			Criteria criteriaDeposito = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDeposito.class);
			criteriaDeposito.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDeposito = Projections.projectionList();
			projectionDeposito.add(Projections.sum("debe"));
			criteriaDeposito.setProjection(projectionDeposito);
			criteriaDeposito.add(Restrictions.lt("fecha", fin));
			BigDecimal debeDeposito = (BigDecimal) criteriaDeposito.uniqueResult();

			Criteria criteriaRetiro = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoRetiro.class);
			criteriaRetiro.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionRetiro = Projections.projectionList();
			projectionRetiro.add(Projections.sum("debe"));
			criteriaRetiro.setProjection(projectionRetiro);
			criteriaRetiro.add(Restrictions.lt("fecha", fin));
			BigDecimal debeRetiro = (BigDecimal) criteriaRetiro.uniqueResult();

			Criteria criteriaIngrNoIdentificado = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoIngresoNoIdentificado.class);
			criteriaIngrNoIdentificado.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionIngrNoIdentificado = Projections.projectionList();
			projectionIngrNoIdentificado.add(Projections.sum("debe"));
			criteriaIngrNoIdentificado.setProjection(projectionIngrNoIdentificado);
			criteriaIngrNoIdentificado.add(Restrictions.lt("fecha", fin));
			BigDecimal debeIngrNoIdentificado = (BigDecimal) criteriaIngrNoIdentificado.uniqueResult();

			return (debeP != null ? debeP : BigDecimal.ZERO)
					.add(debeCA != null ? debeCA : BigDecimal.ZERO)
					.add(debeS != null ? debeS : BigDecimal.ZERO)
					.add(debeCfdiA != null ? debeCfdiA : BigDecimal.ZERO)
					.add(debeE != null ? debeE : BigDecimal.ZERO)
					.add(debeG != null ? debeG : BigDecimal.ZERO)
					.add(debeDG != null ? debeDG : BigDecimal.ZERO)
					.add(debeOtrIngr != null ? debeOtrIngr : BigDecimal.ZERO)
					.add(debeConcOtrIngr != null ? debeConcOtrIngr : BigDecimal.ZERO)
					.add(debeDeposito != null ? debeDeposito : BigDecimal.ZERO)
					.add(debeRetiro != null ? debeRetiro : BigDecimal.ZERO)
					.add(debeIngrNoIdentificado != null ? debeIngrNoIdentificado : BigDecimal.ZERO);
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getDebeLtEq(Date fin, Cuenta cuenta) {
		try {

			Criteria criteriaCA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargoAplicado.class);
			criteriaCA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCA = Projections.projectionList();
			projectionCA.add(Projections.sum("debe"));
			criteriaCA.setProjection(projectionCA);
			criteriaCA.add(Restrictions.le("fecha", fin));
			BigDecimal debeCA = (BigDecimal) criteriaCA.uniqueResult();

			Criteria criteriaS = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteriaS.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionS = Projections.projectionList();
			projectionS.add(Projections.sum("debe"));
			criteriaS.setProjection(projectionS);
			criteriaS.add(Restrictions.le("fecha", fin));
			BigDecimal debeS = (BigDecimal) criteriaS.uniqueResult();

			Criteria criteriaP = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPago.class);
			criteriaP.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionP = Projections.projectionList();
			projectionP.add(Projections.sum("debe"));
			criteriaP.setProjection(projectionP);
			criteriaP.add(Restrictions.le("fecha", fin));
			BigDecimal debeP = (BigDecimal) criteriaP.uniqueResult();

			Criteria criteriaCfdiA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCfdiAplicado.class);
			criteriaCfdiA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCfdiA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCfdiA = Projections.projectionList();
			projectionCfdiA.add(Projections.sum("debe"));
			criteriaCfdiA.setProjection(projectionCfdiA);
			criteriaCfdiA.add(Restrictions.le("fecha", fin));
			BigDecimal debeCfdiA = (BigDecimal) criteriaCfdiA.uniqueResult();

			Criteria criteriaE = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPagoProveedor.class);
			criteriaE.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionE = Projections.projectionList();
			projectionE.add(Projections.sum("debe"));
			criteriaE.setProjection(projectionE);
			criteriaE.add(Restrictions.le("fecha", fin));
			BigDecimal debeE = (BigDecimal) criteriaE.uniqueResult();

			Criteria criteriaG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoGasto.class);
			criteriaG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionG = Projections.projectionList();
			projectionG.add(Projections.sum("debe"));
			criteriaG.setProjection(projectionG);
			criteriaG.add(Restrictions.le("fecha", fin));
			BigDecimal debeG = (BigDecimal) criteriaG.uniqueResult();

			Criteria criteriaDG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDetallle.class);
			criteriaDG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDG = Projections.projectionList();
			projectionDG.add(Projections.sum("debe"));
			criteriaDG.setProjection(projectionDG);
			criteriaDG.add(Restrictions.le("fecha", fin));
			BigDecimal debeDG = (BigDecimal) criteriaDG.uniqueResult();

			Criteria criteriaOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoOtroIngreso.class);
			criteriaOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionOtrIngr = Projections.projectionList();
			projectionOtrIngr.add(Projections.sum("debe"));
			criteriaOtrIngr.setProjection(projectionOtrIngr);
			criteriaOtrIngr.add(Restrictions.le("fecha", fin));
			BigDecimal debeOtrIngr = (BigDecimal) criteriaOtrIngr.uniqueResult();

			Criteria criteriaConcOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoConceptoOtroIngreso.class);
			criteriaConcOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionConcOtrIngr = Projections.projectionList();
			projectionConcOtrIngr.add(Projections.sum("debe"));
			criteriaConcOtrIngr.setProjection(projectionConcOtrIngr);
			criteriaConcOtrIngr.add(Restrictions.le("fecha", fin));
			BigDecimal debeConcOtrIngr = (BigDecimal) criteriaConcOtrIngr.uniqueResult();

			Criteria criteriaDeposito = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDeposito.class);
			criteriaDeposito.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDeposito = Projections.projectionList();
			projectionDeposito.add(Projections.sum("debe"));
			criteriaDeposito.setProjection(projectionDeposito);
			criteriaDeposito.add(Restrictions.le("fecha", fin));
			BigDecimal debeDeposito = (BigDecimal) criteriaDeposito.uniqueResult();

			Criteria criteriaRetiro = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoRetiro.class);
			criteriaRetiro.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionRetiro = Projections.projectionList();
			projectionRetiro.add(Projections.sum("debe"));
			criteriaRetiro.setProjection(projectionRetiro);
			criteriaRetiro.add(Restrictions.le("fecha", fin));
			BigDecimal debeRetiro = (BigDecimal) criteriaRetiro.uniqueResult();

			Criteria criteriaIngrNoIdentificado = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoIngresoNoIdentificado.class);
			criteriaIngrNoIdentificado.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionIngrNoIdentificado = Projections.projectionList();
			projectionIngrNoIdentificado.add(Projections.sum("debe"));
			criteriaIngrNoIdentificado.setProjection(projectionIngrNoIdentificado);
			criteriaIngrNoIdentificado.add(Restrictions.le("fecha", fin));
			BigDecimal debeIngrNoIdentificado = (BigDecimal) criteriaIngrNoIdentificado.uniqueResult();

			return (debeP != null ? debeP : BigDecimal.ZERO)
					.add(debeCA != null ? debeCA : BigDecimal.ZERO)
					.add(debeS != null ? debeS : BigDecimal.ZERO)
					.add(debeCfdiA != null ? debeCfdiA : BigDecimal.ZERO)
					.add(debeE != null ? debeE : BigDecimal.ZERO)
					.add(debeG != null ? debeG : BigDecimal.ZERO)
					.add(debeDG != null ? debeDG : BigDecimal.ZERO)
					.add(debeOtrIngr != null ? debeOtrIngr : BigDecimal.ZERO)
					.add(debeConcOtrIngr != null ? debeConcOtrIngr : BigDecimal.ZERO)
					.add(debeDeposito != null ? debeDeposito : BigDecimal.ZERO)
					.add(debeRetiro != null ? debeRetiro : BigDecimal.ZERO)
					.add(debeIngrNoIdentificado != null ? debeIngrNoIdentificado : BigDecimal.ZERO);
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getHaber(Date fin, Cuenta cuenta) {
		try {

			Criteria criteriaCA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargoAplicado.class);
			criteriaCA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCA = Projections.projectionList();
			projectionCA.add(Projections.sum("haber"));
			criteriaCA.setProjection(projectionCA);
			criteriaCA.add(Restrictions.lt("fecha", fin));
			BigDecimal haberCA = (BigDecimal) criteriaCA.uniqueResult();

			Criteria criteriaS = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteriaS.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionS = Projections.projectionList();
			projectionS.add(Projections.sum("haber"));
			criteriaS.setProjection(projectionS);
			criteriaS.add(Restrictions.lt("fecha", fin));
			BigDecimal haberS = (BigDecimal) criteriaS.uniqueResult();

			Criteria criteriaP = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPago.class);
			criteriaP.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionP = Projections.projectionList();
			projectionP.add(Projections.sum("haber"));
			criteriaP.setProjection(projectionP);
			criteriaP.add(Restrictions.lt("fecha", fin));
			BigDecimal haberP = (BigDecimal) criteriaP.uniqueResult();

			Criteria criteriaCfdiA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCfdiAplicado.class);
			criteriaCfdiA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCfdiA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCfdiA = Projections.projectionList();
			projectionCfdiA.add(Projections.sum("haber"));
			criteriaCfdiA.setProjection(projectionCfdiA);
			criteriaCfdiA.add(Restrictions.lt("fecha", fin));
			BigDecimal haberCfdiA = (BigDecimal) criteriaCfdiA.uniqueResult();

			Criteria criteriaE = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPagoProveedor.class);
			criteriaE.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionE = Projections.projectionList();
			projectionE.add(Projections.sum("haber"));
			criteriaE.setProjection(projectionE);
			criteriaE.add(Restrictions.lt("fecha", fin));
			BigDecimal haberE = (BigDecimal) criteriaE.uniqueResult();
			
			Criteria criteriaG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoGasto.class);
			criteriaG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionG = Projections.projectionList();
			projectionG.add(Projections.sum("haber"));
			criteriaG.setProjection(projectionG);
			criteriaG.add(Restrictions.lt("fecha", fin));
			BigDecimal haberG = (BigDecimal) criteriaG.uniqueResult();
			
			Criteria criteriaDG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDetallle.class);
			criteriaDG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDG = Projections.projectionList();
			projectionDG.add(Projections.sum("haber"));
			criteriaDG.setProjection(projectionDG);
			criteriaDG.add(Restrictions.lt("fecha", fin));
			BigDecimal haberDG = (BigDecimal) criteriaDG.uniqueResult();

			Criteria criteriaOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoOtroIngreso.class);
			criteriaOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionOtrIngr = Projections.projectionList();
			projectionOtrIngr.add(Projections.sum("haber"));
			criteriaOtrIngr.setProjection(projectionOtrIngr);
			criteriaOtrIngr.add(Restrictions.lt("fecha", fin));
			BigDecimal haberOtrIngr = (BigDecimal) criteriaOtrIngr.uniqueResult();

			Criteria criteriaConcOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoConceptoOtroIngreso.class);
			criteriaConcOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionConcOtrIngr = Projections.projectionList();
			projectionConcOtrIngr.add(Projections.sum("haber"));
			criteriaConcOtrIngr.setProjection(projectionConcOtrIngr);
			criteriaConcOtrIngr.add(Restrictions.lt("fecha", fin));
			BigDecimal haberConcOtrIngr = (BigDecimal) criteriaConcOtrIngr.uniqueResult();

			Criteria criteriaDeposito = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDeposito.class);
			criteriaDeposito.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDeposito = Projections.projectionList();
			projectionDeposito.add(Projections.sum("haber"));
			criteriaDeposito.setProjection(projectionDeposito);
			criteriaDeposito.add(Restrictions.lt("fecha", fin));
			BigDecimal haberDeposito = (BigDecimal) criteriaDeposito.uniqueResult();

			Criteria criteriaRetiro = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoRetiro.class);
			criteriaRetiro.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionRetiro = Projections.projectionList();
			projectionRetiro.add(Projections.sum("haber"));
			criteriaRetiro.setProjection(projectionRetiro);
			criteriaRetiro.add(Restrictions.lt("fecha", fin));
			BigDecimal haberRetiro = (BigDecimal) criteriaRetiro.uniqueResult();

			Criteria criteriaIngrNoIdentificado = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoIngresoNoIdentificado.class);
			criteriaIngrNoIdentificado.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionIngrNoIdentificado = Projections.projectionList();
			projectionIngrNoIdentificado.add(Projections.sum("haber"));
			criteriaIngrNoIdentificado.setProjection(projectionIngrNoIdentificado);
			criteriaIngrNoIdentificado.add(Restrictions.lt("fecha", fin));
			BigDecimal haberIngrNoIdentificado = (BigDecimal) criteriaIngrNoIdentificado.uniqueResult();

			return (haberP != null ? haberP : BigDecimal.ZERO)
					.add(haberCA != null ? haberCA : BigDecimal.ZERO)
					.add(haberS != null ? haberS : BigDecimal.ZERO)
					.add(haberCfdiA != null ? haberCfdiA : BigDecimal.ZERO)
					.add(haberE != null ? haberE : BigDecimal.ZERO)
					.add(haberG != null ? haberG : BigDecimal.ZERO)
					.add(haberDG != null ? haberDG : BigDecimal.ZERO)
					.add(haberOtrIngr != null ? haberOtrIngr : BigDecimal.ZERO)
					.add(haberConcOtrIngr != null ? haberConcOtrIngr : BigDecimal.ZERO)
					.add(haberDeposito != null ? haberDeposito : BigDecimal.ZERO)
					.add(haberRetiro != null ? haberRetiro : BigDecimal.ZERO)
					.add(haberIngrNoIdentificado != null ? haberIngrNoIdentificado : BigDecimal.ZERO);
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getHaberLtEq(Date fin, Cuenta cuenta) {
		try {

			Criteria criteriaCA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargoAplicado.class);
			criteriaCA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCA = Projections.projectionList();
			projectionCA.add(Projections.sum("haber"));
			criteriaCA.setProjection(projectionCA);
			criteriaCA.add(Restrictions.le("fecha", fin));
			BigDecimal haberCA = (BigDecimal) criteriaCA.uniqueResult();

			Criteria criteriaS = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteriaS.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionS = Projections.projectionList();
			projectionS.add(Projections.sum("haber"));
			criteriaS.setProjection(projectionS);
			criteriaS.add(Restrictions.le("fecha", fin));
			BigDecimal haberS = (BigDecimal) criteriaS.uniqueResult();

			Criteria criteriaP = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPago.class);
			criteriaP.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionP = Projections.projectionList();
			projectionP.add(Projections.sum("haber"));
			criteriaP.setProjection(projectionP);
			criteriaP.add(Restrictions.le("fecha", fin));
			BigDecimal haberP = (BigDecimal) criteriaP.uniqueResult();

			Criteria criteriaCfdiA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCfdiAplicado.class);
			criteriaCfdiA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCfdiA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCfdiA = Projections.projectionList();
			projectionCfdiA.add(Projections.sum("haber"));
			criteriaCfdiA.setProjection(projectionCfdiA);
			criteriaCfdiA.add(Restrictions.le("fecha", fin));
			BigDecimal haberCfdiA = (BigDecimal) criteriaCfdiA.uniqueResult();

			Criteria criteriaE = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPagoProveedor.class);
			criteriaE.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionE = Projections.projectionList();
			projectionE.add(Projections.sum("haber"));
			criteriaE.setProjection(projectionE);
			criteriaE.add(Restrictions.le("fecha", fin));
			BigDecimal haberE = (BigDecimal) criteriaE.uniqueResult();

			Criteria criteriaG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoGasto.class);
			criteriaG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionG = Projections.projectionList();
			projectionG.add(Projections.sum("haber"));
			criteriaG.setProjection(projectionG);
			criteriaG.add(Restrictions.le("fecha", fin));
			BigDecimal haberG = (BigDecimal) criteriaG.uniqueResult();

			Criteria criteriaDG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDetallle.class);
			criteriaDG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDG = Projections.projectionList();
			projectionDG.add(Projections.sum("haber"));
			criteriaDG.setProjection(projectionDG);
			criteriaDG.add(Restrictions.le("fecha", fin));
			BigDecimal haberDG = (BigDecimal) criteriaDG.uniqueResult();

			Criteria criteriaOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoOtroIngreso.class);
			criteriaOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionOtrIngr = Projections.projectionList();
			projectionOtrIngr.add(Projections.sum("haber"));
			criteriaOtrIngr.setProjection(projectionOtrIngr);
			criteriaOtrIngr.add(Restrictions.le("fecha", fin));
			BigDecimal haberOtrIngr = (BigDecimal) criteriaOtrIngr.uniqueResult();

			Criteria criteriaConcOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoConceptoOtroIngreso.class);
			criteriaConcOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionConcOtrIngr = Projections.projectionList();
			projectionConcOtrIngr.add(Projections.sum("haber"));
			criteriaConcOtrIngr.setProjection(projectionConcOtrIngr);
			criteriaConcOtrIngr.add(Restrictions.le("fecha", fin));
			BigDecimal haberConcOtrIngr = (BigDecimal) criteriaConcOtrIngr.uniqueResult();

			Criteria criteriaDeposito = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDeposito.class);
			criteriaDeposito.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDeposito = Projections.projectionList();
			projectionDeposito.add(Projections.sum("haber"));
			criteriaDeposito.setProjection(projectionDeposito);
			criteriaDeposito.add(Restrictions.le("fecha", fin));
			BigDecimal haberDeposito = (BigDecimal) criteriaDeposito.uniqueResult();

			Criteria criteriaRetiro = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoRetiro.class);
			criteriaRetiro.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionRetiro = Projections.projectionList();
			projectionRetiro.add(Projections.sum("haber"));
			criteriaRetiro.setProjection(projectionRetiro);
			criteriaRetiro.add(Restrictions.le("fecha", fin));
			BigDecimal haberRetiro = (BigDecimal) criteriaRetiro.uniqueResult();

			Criteria criteriaIngrNoIdentificado = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoIngresoNoIdentificado.class);
			criteriaIngrNoIdentificado.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionIngrNoIdentificado = Projections.projectionList();
			projectionIngrNoIdentificado.add(Projections.sum("haber"));
			criteriaIngrNoIdentificado.setProjection(projectionIngrNoIdentificado);
			criteriaIngrNoIdentificado.add(Restrictions.le("fecha", fin));
			BigDecimal haberIngrNoIdentificado = (BigDecimal) criteriaIngrNoIdentificado.uniqueResult();

			return (haberP != null ? haberP : BigDecimal.ZERO)
					.add(haberCA != null ? haberCA : BigDecimal.ZERO)
					.add(haberS != null ? haberS : BigDecimal.ZERO)
					.add(haberCfdiA != null ? haberCfdiA : BigDecimal.ZERO)
					.add(haberE != null ? haberE : BigDecimal.ZERO)
					.add(haberG != null ? haberG : BigDecimal.ZERO)
					.add(haberDG != null ? haberDG : BigDecimal.ZERO)
					.add(haberOtrIngr != null ? haberOtrIngr : BigDecimal.ZERO)
					.add(haberConcOtrIngr != null ? haberConcOtrIngr : BigDecimal.ZERO)
					.add(haberDeposito != null ? haberDeposito : BigDecimal.ZERO)
					.add(haberRetiro != null ? haberRetiro : BigDecimal.ZERO)
					.add(haberIngrNoIdentificado != null ? haberIngrNoIdentificado : BigDecimal.ZERO);
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getDebe(Date inicio, Date fin, Cuenta cuenta) {
		try {

			Criteria criteriaCA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargoAplicado.class);
			criteriaCA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCA = Projections.projectionList();
			projectionCA.add(Projections.sum("debe"));
			criteriaCA.setProjection(projectionCA);
			criteriaCA.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeCA = (BigDecimal) criteriaCA.uniqueResult();

			Criteria criteriaS = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteriaS.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionS = Projections.projectionList();
			projectionS.add(Projections.sum("debe"));
			criteriaS.setProjection(projectionS);
			criteriaS.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeS = (BigDecimal) criteriaS.uniqueResult();

			Criteria criteriaP = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPago.class);
			criteriaP.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionP = Projections.projectionList();
			projectionP.add(Projections.sum("debe"));
			criteriaP.setProjection(projectionP);
			criteriaP.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeP = (BigDecimal) criteriaP.uniqueResult();

			Criteria criteriaCfdiA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCfdiAplicado.class);
			criteriaCfdiA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCfdiA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCfdiA = Projections.projectionList();
			projectionCfdiA.add(Projections.sum("debe"));
			criteriaCfdiA.setProjection(projectionCfdiA);
			criteriaCfdiA.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeCfdiA = (BigDecimal) criteriaCfdiA.uniqueResult();

			Criteria criteriaE = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPagoProveedor.class);
			criteriaE.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionE = Projections.projectionList();
			projectionE.add(Projections.sum("debe"));
			criteriaE.setProjection(projectionE);
			criteriaE.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeE = (BigDecimal) criteriaE.uniqueResult();
			
			Criteria criteriaG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoGasto.class);
			criteriaG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionG = Projections.projectionList();
			projectionG.add(Projections.sum("debe"));
			criteriaG.setProjection(projectionG);
			criteriaG.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeG = (BigDecimal) criteriaG.uniqueResult();
			
			Criteria criteriaDG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDetallle.class);
			criteriaDG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDG = Projections.projectionList();
			projectionDG.add(Projections.sum("debe"));
			criteriaDG.setProjection(projectionDG);
			criteriaDG.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeDG = (BigDecimal) criteriaDG.uniqueResult();

			Criteria criteriaOtrIng = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoOtroIngreso.class);
			criteriaOtrIng.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionOtrIng = Projections.projectionList();
			projectionOtrIng.add(Projections.sum("debe"));
			criteriaOtrIng.setProjection(projectionOtrIng);
			criteriaOtrIng.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeOtrIng = (BigDecimal) criteriaOtrIng.uniqueResult();

			Criteria criteriaConcOtrIng = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoConceptoOtroIngreso.class);
			criteriaConcOtrIng.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionConcOtrIng = Projections.projectionList();
			projectionConcOtrIng.add(Projections.sum("debe"));
			criteriaConcOtrIng.setProjection(projectionConcOtrIng);
			criteriaConcOtrIng.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeConcOtrIng = (BigDecimal) criteriaConcOtrIng.uniqueResult();

			Criteria criteriaDeposito = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDeposito.class);
			criteriaDeposito.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDeposito = Projections.projectionList();
			projectionDeposito.add(Projections.sum("debe"));
			criteriaDeposito.setProjection(projectionDeposito);
			criteriaDeposito.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeDeposito = (BigDecimal) criteriaDeposito.uniqueResult();

			Criteria criteriaRetiro = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoRetiro.class);
			criteriaRetiro.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionRetiro = Projections.projectionList();
			projectionRetiro.add(Projections.sum("debe"));
			criteriaRetiro.setProjection(projectionRetiro);
			criteriaRetiro.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeRetiro = (BigDecimal) criteriaRetiro.uniqueResult();

			Criteria criteriaIngrNoIdentificado = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoIngresoNoIdentificado.class);
			criteriaIngrNoIdentificado.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionIngrNoIdentificado = Projections.projectionList();
			projectionIngrNoIdentificado.add(Projections.sum("debe"));
			criteriaIngrNoIdentificado.setProjection(projectionIngrNoIdentificado);
			criteriaIngrNoIdentificado.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal debeIngrNoIdentificado = (BigDecimal) criteriaIngrNoIdentificado.uniqueResult();

			return (debeP != null ? debeP : BigDecimal.ZERO)
					.add(debeCA != null ? debeCA : BigDecimal.ZERO)
					.add(debeS != null ? debeS : BigDecimal.ZERO)
					.add(debeCfdiA != null ? debeCfdiA : BigDecimal.ZERO)
					.add(debeE != null ? debeE : BigDecimal.ZERO)
					.add(debeG != null ? debeG : BigDecimal.ZERO)
					.add(debeDG != null ? debeDG : BigDecimal.ZERO)
					.add(debeOtrIng != null ? debeOtrIng : BigDecimal.ZERO)
					.add(debeConcOtrIng != null ? debeConcOtrIng : BigDecimal.ZERO)
					.add(debeDeposito != null ? debeDeposito : BigDecimal.ZERO)
					.add(debeRetiro != null ? debeRetiro : BigDecimal.ZERO)
					.add(debeIngrNoIdentificado != null ? debeIngrNoIdentificado : BigDecimal.ZERO)
					;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public BigDecimal getHaber(Date inicio, Date fin, Cuenta cuenta) {
		try {

			Criteria criteriaCA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargoAplicado.class);
			criteriaCA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCA = Projections.projectionList();
			projectionCA.add(Projections.sum("haber"));
			criteriaCA.setProjection(projectionCA);
			criteriaCA.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberCA = (BigDecimal) criteriaCA.uniqueResult();

			Criteria criteriaS = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteriaS.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionS = Projections.projectionList();
			projectionS.add(Projections.sum("haber"));
			criteriaS.setProjection(projectionS);
			criteriaS.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberS = (BigDecimal) criteriaS.uniqueResult();

			Criteria criteriaP = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPago.class);
			criteriaP.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionP = Projections.projectionList();
			projectionP.add(Projections.sum("haber"));
			criteriaP.setProjection(projectionP);
			criteriaP.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberP = (BigDecimal) criteriaP.uniqueResult();

			Criteria criteriaCfdiA = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCfdiAplicado.class);
			criteriaCfdiA.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaCfdiA.add(Restrictions.eq("cuenta", cuenta));
			ProjectionList projectionCfdiA = Projections.projectionList();
			projectionCfdiA.add(Projections.sum("haber"));
			criteriaCfdiA.setProjection(projectionCfdiA);
			criteriaCfdiA.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberCfdiA = (BigDecimal) criteriaCfdiA.uniqueResult();

			Criteria criteriaE = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPagoProveedor.class);
			criteriaE.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionE = Projections.projectionList();
			projectionE.add(Projections.sum("haber"));
			criteriaE.setProjection(projectionE);
			criteriaE.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberE = (BigDecimal) criteriaE.uniqueResult();
			
			Criteria criteriaG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoGasto.class);
			criteriaG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionG = Projections.projectionList();
			projectionG.add(Projections.sum("haber"));
			criteriaG.setProjection(projectionG);
			criteriaG.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberG = (BigDecimal) criteriaG.uniqueResult();
			
			Criteria criteriaDG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDetallle.class);
			criteriaDG.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDG = Projections.projectionList();
			projectionDG.add(Projections.sum("haber"));
			criteriaDG.setProjection(projectionDG);
			criteriaDG.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberDG = (BigDecimal) criteriaDG.uniqueResult();

			Criteria criteriaOtrIng = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoOtroIngreso.class);
			criteriaOtrIng.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionOtrIng = Projections.projectionList();
			projectionOtrIng.add(Projections.sum("haber"));
			criteriaOtrIng.setProjection(projectionOtrIng);
			criteriaOtrIng.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberOtrIng = (BigDecimal) criteriaOtrIng.uniqueResult();

			Criteria criteriaConcOtrIng = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoConceptoOtroIngreso.class);
			criteriaConcOtrIng.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionConcOtrIng = Projections.projectionList();
			projectionConcOtrIng.add(Projections.sum("haber"));
			criteriaConcOtrIng.setProjection(projectionConcOtrIng);
			criteriaConcOtrIng.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberConcOtrIng = (BigDecimal) criteriaConcOtrIng.uniqueResult();

			Criteria criteriaDeposito = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDeposito.class);
			criteriaDeposito.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionDeposito = Projections.projectionList();
			projectionDeposito.add(Projections.sum("haber"));
			criteriaDeposito.setProjection(projectionDeposito);
			criteriaDeposito.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberDeposito = (BigDecimal) criteriaDeposito.uniqueResult();

			Criteria criteriaRetiro = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoRetiro.class);
			criteriaRetiro.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionRetiro = Projections.projectionList();
			projectionRetiro.add(Projections.sum("haber"));
			criteriaRetiro.setProjection(projectionRetiro);
			criteriaRetiro.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberRetiro = (BigDecimal) criteriaRetiro.uniqueResult();

			Criteria criteriaIngrNoIdentificado = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoIngresoNoIdentificado.class);
			criteriaIngrNoIdentificado.add((Restrictions.eq("cuenta", cuenta)));
			ProjectionList projectionIngrNoIdentificado = Projections.projectionList();
			projectionIngrNoIdentificado.add(Projections.sum("haber"));
			criteriaIngrNoIdentificado.setProjection(projectionIngrNoIdentificado);
			criteriaIngrNoIdentificado.add(Restrictions.between("fecha", inicio, fin));
			BigDecimal haberIngrNoIdentificado = (BigDecimal) criteriaIngrNoIdentificado.uniqueResult();

			return (haberP != null ? haberP : BigDecimal.ZERO)
					.add(haberCA != null ? haberCA : BigDecimal.ZERO)
					.add(haberS != null ? haberS : BigDecimal.ZERO)
					.add(haberCfdiA != null ? haberCfdiA : BigDecimal.ZERO)
					.add(haberE != null ? haberE : BigDecimal.ZERO)
					.add(haberG != null ? haberG : BigDecimal.ZERO)
					.add(haberDG != null ? haberDG : BigDecimal.ZERO)
					.add(haberOtrIng != null ? haberOtrIng : BigDecimal.ZERO)
					.add(haberConcOtrIng != null ? haberConcOtrIng : BigDecimal.ZERO)
					.add(haberDeposito != null ? haberDeposito : BigDecimal.ZERO)
					.add(haberRetiro != null ? haberRetiro : BigDecimal.ZERO)
					.add(haberIngrNoIdentificado != null ? haberIngrNoIdentificado : BigDecimal.ZERO);
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<Movimiento> getMovimientos(Cuenta cuenta, Date inicio,
			Date fin) {
		try {
			List<Movimiento> movimientos = new ArrayList<Movimiento>();

			Collection<Movimiento> aplicados = new ArrayList<Movimiento>();
			Criteria criteriaAplicados = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargoAplicado.class);
			criteriaAplicados.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaAplicados.add(Restrictions.eq("cuenta", cuenta));
			criteriaAplicados.add(Restrictions.between("fecha", inicio, fin));
			aplicados = criteriaAplicados.list();

			if (!CollectionUtils.isEmpty(aplicados)) {
				for (Movimiento a : aplicados) {
					((MovimientoCargoAplicado) a).getMovimientoCargo()
							.getCargo().getId();
				}
			}

			Collection<Movimiento> pagos = new ArrayList<Movimiento>();
			Criteria criteriaPagos = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPago.class);
			criteriaPagos.add(Restrictions.eq("cuenta", cuenta));
			criteriaPagos.add(Restrictions.between("fecha", inicio, fin));
			pagos = criteriaPagos.list();

			if (!CollectionUtils.isEmpty(pagos)) {
				for (Movimiento p : pagos) {
					Hibernate.initialize(((MovimientoPago) p).getPago());
					if (((MovimientoPago) p).getPago() != null) {
						Hibernate.initialize(((MovimientoPago) p).getPago()
								.getContacto());
					}
				}
			}

			Collection<Movimiento> saldos = new ArrayList<Movimiento>();
			Criteria criteriaSaldos = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteriaSaldos.add(Restrictions.eq("cuenta", cuenta));
			criteriaSaldos.add(Restrictions.between("fecha", inicio, fin));
			saldos = criteriaSaldos.list();

			Collection<Movimiento> aplicadosCfdi = new ArrayList<Movimiento>();
			Criteria criteriaAplicadosCfdi = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCfdiAplicado.class);
			criteriaAplicadosCfdi
					.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaAplicadosCfdi.add(Restrictions.eq("cuenta", cuenta));
			criteriaAplicadosCfdi.add(Restrictions
					.between("fecha", inicio, fin));
			aplicadosCfdi = criteriaAplicadosCfdi.list();

			if (!CollectionUtils.isEmpty(aplicadosCfdi)) {
				for (Movimiento a : aplicadosCfdi) {
					((MovimientoCfdiAplicado) a).getMovimiento().getId();
					((MovimientoCfdiAplicado) a).getMovimiento().getId();
					/*((MovimientoCfdi) ((MovimientoCfdiAplicado) a).getMovimiento()).getDetalleCfdi().getId();*/
				}
			}

			Collection<Movimiento> pagosProveedor = new ArrayList<>();
			Criteria criteriaPagosProveedor = sessionFactory
					.getCurrentSession().createCriteria(
							MovimientoPagoProveedor.class);
			criteriaPagosProveedor.add(Restrictions.eq("cuenta", cuenta));
			criteriaPagosProveedor.add(Restrictions.between("fecha", inicio,
					fin));
			pagosProveedor = criteriaPagosProveedor.list();

			if (!CollectionUtils.isEmpty(pagosProveedor)) {
				for (Movimiento p : pagosProveedor) {
					Hibernate.initialize(((MovimientoPagoProveedor) p)
							.getPagoProveedor());
					if (((MovimientoPagoProveedor) p).getPagoProveedor() != null) {
						Hibernate.initialize(((MovimientoPagoProveedor) p)
								.getPagoProveedor().getProveedor());
					}
				}
			}
			
			Collection<Movimiento> gastos = new ArrayList<>();
			Criteria criteriaG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoGasto.class);
			criteriaG.add((Restrictions.eq("cuenta", cuenta)));
			criteriaG.add(Restrictions.between("fecha", inicio, fin));
			gastos = criteriaG.list();
			
			if (!CollectionUtils.isEmpty(gastos)) {
				for (Movimiento a : gastos) {
					Hibernate.initialize(((MovimientoGasto) a).getGasto().getProveedor());
					Hibernate.initialize(((MovimientoGasto) a).getGasto().getDetalles());
				}
			}
			
			Collection<Movimiento> detallesGastos = new ArrayList<>();
			Criteria criteriaDG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDetallle.class);
			criteriaDG.add((Restrictions.eq("cuenta", cuenta)));
			criteriaDG.add(Restrictions.between("fecha", inicio, fin));
			detallesGastos = criteriaDG.list();
			
			if (!CollectionUtils.isEmpty(detallesGastos)) {
				for (Movimiento a : detallesGastos) {
					Hibernate.initialize(((MovimientoDetallle) a).getDetalle());
				}
			}

			Collection<Movimiento> otrosIngresos = new ArrayList<>();
			Criteria criteriaOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoOtroIngreso.class);
			criteriaOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			criteriaOtrIngr.add(Restrictions.between("fecha", inicio, fin));
			otrosIngresos = criteriaOtrIngr.list();

			if (!CollectionUtils.isEmpty(otrosIngresos)) {
				for (Movimiento a : otrosIngresos) {
					Hibernate.initialize(((MovimientoOtroIngreso) a).getOtroIngreso().getConceptos());
				}
			}

			Collection<Movimiento> conceptosOtrosIngresos = new ArrayList<>();
			Criteria criteriaConcOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoConceptoOtroIngreso.class);
			criteriaConcOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			criteriaConcOtrIngr.add(Restrictions.between("fecha", inicio, fin));
			conceptosOtrosIngresos = criteriaConcOtrIngr.list();

			if (!CollectionUtils.isEmpty(conceptosOtrosIngresos)) {
				for (Movimiento a : conceptosOtrosIngresos) {
					Hibernate.initialize(((MovimientoConceptoOtroIngreso) a).getConceptoIngreso());
				}
			}

			Collection<Movimiento> transferenciasDeposito = new ArrayList<>();
			Criteria criteriaTransferenciasDeposito = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDeposito.class);
			criteriaTransferenciasDeposito.add((Restrictions.eq("cuenta", cuenta)));
			criteriaTransferenciasDeposito.add(Restrictions.between("fecha", inicio, fin));
			transferenciasDeposito = criteriaTransferenciasDeposito.list();

			if (!CollectionUtils.isEmpty(transferenciasDeposito)) {
				for (Movimiento a : transferenciasDeposito) {
					Hibernate.initialize(((MovimientoDeposito) a).getTransferencia());
				}
			}

			Collection<Movimiento> transferenciasRetiro = new ArrayList<>();
			Criteria criteriaTransferenciasRetiro = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoRetiro.class);
			criteriaTransferenciasRetiro.add((Restrictions.eq("cuenta", cuenta)));
			criteriaTransferenciasRetiro.add(Restrictions.between("fecha", inicio, fin));
			transferenciasRetiro = criteriaTransferenciasRetiro.list();

			if (!CollectionUtils.isEmpty(transferenciasRetiro)) {
				for (Movimiento a : transferenciasRetiro) {
					Hibernate.initialize(((MovimientoRetiro) a).getTransferencia());
				}
			}

			Collection<Movimiento> ingresosNoIdentificados = new ArrayList<>();
			Criteria criteriaIngresosNoIdentificados = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoIngresoNoIdentificado.class);
			criteriaIngresosNoIdentificados.add((Restrictions.eq("cuenta", cuenta)));
			criteriaIngresosNoIdentificados.add(Restrictions.between("fecha", inicio, fin));
			ingresosNoIdentificados = criteriaIngresosNoIdentificados.list();

			if (!CollectionUtils.isEmpty(ingresosNoIdentificados)) {
				for (Movimiento a : ingresosNoIdentificados) {
					Hibernate.initialize(((MovimientoIngresoNoIdentificado) a).getIngresoNoIdentificado());
				}
			}

			movimientos.addAll(aplicados);
			movimientos.addAll(pagos);
			movimientos.addAll(saldos);
			movimientos.addAll(aplicadosCfdi);
			movimientos.addAll(pagosProveedor);
			movimientos.addAll(gastos);
			movimientos.addAll(detallesGastos);
			movimientos.addAll(otrosIngresos);
			movimientos.addAll(conceptosOtrosIngresos);
			movimientos.addAll(transferenciasDeposito);
			movimientos.addAll(transferenciasRetiro);
			movimientos.addAll(ingresosNoIdentificados);

			Collections.sort(movimientos);
			return movimientos;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<Movimiento> getMovimientos(Cuenta cuenta, Date fin) {
		try {
			List<Movimiento> movimientos = new ArrayList<Movimiento>();

			Collection<Movimiento> aplicados = new ArrayList<Movimiento>();
			Criteria criteriaAplicados = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCargoAplicado.class);
			criteriaAplicados.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaAplicados.add(Restrictions.eq("cuenta", cuenta));
			criteriaAplicados.add(Restrictions.lt("fecha", fin));
			aplicados = criteriaAplicados.list();

			if (!CollectionUtils.isEmpty(aplicados)) {
				for (Movimiento a : aplicados) {
					((MovimientoCargoAplicado) a).getMovimientoCargo()
							.getCargo().getId();
				}
			}

			Collection<Movimiento> pagos = new ArrayList<Movimiento>();
			Criteria criteriaPagos = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoPago.class);
			criteriaPagos.add(Restrictions.eq("cuenta", cuenta));
			criteriaPagos.add(Restrictions.lt("fecha", fin));
			pagos = criteriaPagos.list();

			if (!CollectionUtils.isEmpty(pagos)) {
				for (Movimiento p : pagos) {
					Hibernate.initialize(((MovimientoPago) p).getPago());
					if (((MovimientoPago) p).getPago() != null) {
						Hibernate.initialize(((MovimientoPago) p).getPago()
								.getContacto());
					}
				}
			}

			Collection<Movimiento> saldos = new ArrayList<Movimiento>();
			Criteria criteriaSaldos = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoSaldo.class);
			criteriaSaldos.add(Restrictions.eq("cuenta", cuenta));
			criteriaSaldos.add(Restrictions.lt("fecha", fin));
			saldos = criteriaSaldos.list();

			Collection<Movimiento> aplicadosCfdi = new ArrayList<Movimiento>();
			Criteria criteriaAplicadosCfdi = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoCfdiAplicado.class);
			criteriaAplicadosCfdi
					.add(Restrictions.eq("aplicado", Boolean.TRUE));
			criteriaAplicadosCfdi.add(Restrictions.eq("cuenta", cuenta));
			criteriaAplicadosCfdi.add(Restrictions.lt("fecha", fin));
			aplicadosCfdi = criteriaAplicadosCfdi.list();

			if (!CollectionUtils.isEmpty(aplicadosCfdi)) {
				for (Movimiento a : aplicadosCfdi) {
					((MovimientoCfdiAplicado) a).getMovimiento().getId();
					if(((MovimientoCfdiAplicado) a).getMovimiento() instanceof MovimientoCfdi) {
						((MovimientoCfdi) ((MovimientoCfdiAplicado) a).getMovimiento()).getDetalleCfdi().getId();
					}
				}
			}

			Collection<Movimiento> pagosProveedor = new ArrayList<Movimiento>();
			Criteria criteriaPagosProveedor = sessionFactory
					.getCurrentSession().createCriteria(
							MovimientoPagoProveedor.class);
			criteriaPagosProveedor.add(Restrictions.eq("cuenta", cuenta));
			criteriaPagosProveedor.add(Restrictions.lt("fecha", fin));
			pagosProveedor = criteriaPagosProveedor.list();

			if (!CollectionUtils.isEmpty(pagosProveedor)) {
				for (Movimiento p : pagosProveedor) {
					Hibernate.initialize(((MovimientoPagoProveedor) p)
							.getPagoProveedor());
					if (((MovimientoPagoProveedor) p).getPagoProveedor() != null) {
						Hibernate.initialize(((MovimientoPagoProveedor) p)
								.getPagoProveedor().getProveedor());
					}
				}
			}
			
			Collection<Movimiento> gastos = new ArrayList<>();
			Criteria criteriaG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoGasto.class);
			criteriaG.add((Restrictions.eq("cuenta", cuenta)));
			criteriaG.add(Restrictions.lt("fecha", fin));
			gastos = criteriaG.list();
			
			if (!CollectionUtils.isEmpty(gastos)) {
				for (Movimiento a : gastos) {
					Hibernate.initialize(((MovimientoGasto) a).getGasto());
				}
			}
			
			Collection<Movimiento> detallesGastos = new ArrayList<>();
			Criteria criteriaDG = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoDetallle.class);
			criteriaDG.add((Restrictions.eq("cuenta", cuenta)));
			criteriaDG.add(Restrictions.lt("fecha", fin));
			detallesGastos = criteriaDG.list();
			
			if (!CollectionUtils.isEmpty(detallesGastos)) {
				for (Movimiento a : detallesGastos) {
					Hibernate.initialize(((MovimientoDetallle) a).getDetalle());
				}
			}

			Collection<Movimiento> otrosIngresos = new ArrayList<>();
			Criteria criteriaOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoOtroIngreso.class);
			criteriaOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			criteriaOtrIngr.add(Restrictions.lt("fecha", fin));
			otrosIngresos = criteriaOtrIngr.list();

			if (!CollectionUtils.isEmpty(otrosIngresos)) {
				for (Movimiento a : otrosIngresos) {
					Hibernate.initialize(((MovimientoOtroIngreso) a).getOtroIngreso());
				}
			}

			Collection<Movimiento> conceptosOtroIngreso = new ArrayList<>();
			Criteria criteriaConcOtrIngr = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoConceptoOtroIngreso.class);
			criteriaConcOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			criteriaConcOtrIngr.add(Restrictions.lt("fecha", fin));
			conceptosOtroIngreso = criteriaConcOtrIngr.list();

			if (!CollectionUtils.isEmpty(conceptosOtroIngreso)) {
				for (Movimiento a : conceptosOtroIngreso) {
					Hibernate.initialize(((MovimientoConceptoOtroIngreso) a).getConceptoIngreso());
				}
			}

			Collection<Movimiento> ingresosNoIdentificados = new ArrayList<>();
			Criteria criteriaIngresosNoIdentificados = sessionFactory.getCurrentSession()
					.createCriteria(MovimientoIngresoNoIdentificado.class);
			criteriaOtrIngr.add((Restrictions.eq("cuenta", cuenta)));
			criteriaOtrIngr.add(Restrictions.lt("fecha", fin));
			otrosIngresos = criteriaOtrIngr.list();

			if (!CollectionUtils.isEmpty(ingresosNoIdentificados)) {
				for (Movimiento a : ingresosNoIdentificados) {
					Hibernate.initialize(((MovimientoIngresoNoIdentificado) a).getIngresoNoIdentificado());
				}
			}

			movimientos.addAll(aplicados);
			movimientos.addAll(pagos);
			movimientos.addAll(saldos);
			movimientos.addAll(aplicadosCfdi);
			movimientos.addAll(pagosProveedor);
			movimientos.addAll(gastos);
			movimientos.addAll(detallesGastos);
			movimientos.addAll(otrosIngresos);
			movimientos.addAll(conceptosOtroIngreso);
			movimientos.addAll(ingresosNoIdentificados);

			Collections.sort(movimientos);
			return movimientos;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}
}
