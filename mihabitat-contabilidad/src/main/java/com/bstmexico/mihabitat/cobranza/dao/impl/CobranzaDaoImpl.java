package com.bstmexico.mihabitat.cobranza.dao.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cobranza.dao.CobranzaDao;
import com.bstmexico.mihabitat.cobranza.service.impl.CobranzaServiceImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class CobranzaDaoImpl implements CobranzaDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(CobranzaServiceImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Collection<CargoDepartamento> getCargos(Condominio condominio,
			Date hasta) {
		try {
			Criteria criteria = sessionFactory.getCurrentSession()
					.createCriteria(CargoDepartamento.class);
			criteria.add(Restrictions.eq("condominio", condominio));
			criteria.add(Restrictions.lt("fecha", hasta));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Collection<CargoDepartamento> cargos = criteria.list();
			if (!CollectionUtils.isEmpty(cargos)) {
				for (CargoDepartamento cargo : cargos) {
					cargo.getDepartamento().getId();
					if (!CollectionUtils.isEmpty(cargo.getMovimientos())) {
						for (MovimientoCargo movimiento : cargo
								.getMovimientos()) {
							movimiento.setCargo(null);
							if (!CollectionUtils.isEmpty(movimiento
									.getAplicados())) {
								for (MovimientoCargoAplicado aplicado : movimiento
										.getAplicados()) {
									aplicado.setMovimientoCargo(null);
								}
							}
						}
					}
				}
			}
			return cargos;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}
}
