package com.bstmexico.mihabitat.pagos.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.dao.PagoDao;
import com.bstmexico.mihabitat.pagos.dao.PagoDepartamentoDao;
import com.bstmexico.mihabitat.pagos.model.CatalogoEstatusPago;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Zoé Jonatan Tapia Hernández
 * @version 1.0
 * @since 2016
 */
@Repository
public class PagoDepartamentoDaoImpl extends GenericDaoImpl<PagoDepartamento, Long> implements PagoDepartamentoDao {

	@Transactional(readOnly = true)
	@Override
	public PagoDepartamento get(Long key) {
		PagoDepartamento pagoDepartamento = super.get(key);
		pagoDepartamento.getCondominio().getId();
		if (!CollectionUtils.isEmpty(pagoDepartamento.getCondominio().getAdministradores())) {
			for (Usuario u : pagoDepartamento.getCondominio().getAdministradores()) {
				u.getPersona().getEmails().size();
			}
		}
		Hibernate.initialize(pagoDepartamento.getPagoCondomino().getContacto());
		if (pagoDepartamento.getPagoCondomino().getContacto() != null) {
			Hibernate.initialize(pagoDepartamento.getPagoCondomino().getContacto().getEmails());
		}
		if (pagoDepartamento.getPagoCondomino().getCuenta() != null) {
			pagoDepartamento.getPagoCondomino().getCuenta().getId();
		}
		if (!CollectionUtils.isEmpty(pagoDepartamento.getMovimientos())) {
			for (MovimientoCargoAplicado aplicado : pagoDepartamento.getMovimientos()) {
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
		return pagoDepartamento;
	}
}