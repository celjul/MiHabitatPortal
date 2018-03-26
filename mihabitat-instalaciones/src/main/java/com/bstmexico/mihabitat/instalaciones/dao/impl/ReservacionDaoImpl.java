package com.bstmexico.mihabitat.instalaciones.dao.impl;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoEstatusReservacion;
import com.bstmexico.mihabitat.pagos.model.Pago;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.instalaciones.dao.ReservacionDao;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public class ReservacionDaoImpl extends GenericDaoImpl<Reservacion, Long> implements
		ReservacionDao {

	@Transactional(readOnly = true)
	@Override
	public Collection<Reservacion> getListByCondominioAndEstatus(Condominio condominio, CatalogoEstatusReservacion estatus) {
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Reservacion.class);
		criteria.createAlias("instalacion","i");
		criteria.createAlias("i.condominio", "c");
		criteria.add(Restrictions.eq("c.id", condominio.getId()));
		criteria.add(Restrictions.eq("estatusReservacion.id", estatus.getId()));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Collection<Reservacion> reservaciones = criteria.list();

		return reservaciones;

	}

	@Transactional(readOnly = true)
	@Override
	public Reservacion getConInstalacion(Long id) {
		Reservacion reservacion = super.get(id);
		if(reservacion != null) {
			reservacion.getInstalacion().getCondominio().getId();
		}
		return reservacion;

	}
}
