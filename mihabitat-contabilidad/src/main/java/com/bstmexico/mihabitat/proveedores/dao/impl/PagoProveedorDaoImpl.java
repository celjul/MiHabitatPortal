package com.bstmexico.mihabitat.proveedores.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdi;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiImpuesto;
import com.bstmexico.mihabitat.proveedores.dao.PagoProveedorDao;
import com.bstmexico.mihabitat.proveedores.model.PagoProveedor;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Repository
public class PagoProveedorDaoImpl extends GenericDaoImpl<PagoProveedor, Long> implements PagoProveedorDao {

	/*@Transactional(readOnly = true)
    @Override
	public Pago get(Long key) {
		Pago pago = super.get(key);
		pago.getCondominio().getId();
		if (!CollectionUtils.isEmpty(pago.getCondominio().getAdministradores())) {
			for (Usuario u : pago.getCondominio().getAdministradores()) {
				u.getPersona().getEmails().size();
			}
		}
		pago.getContacto().getPersona().getId();
		if (!CollectionUtils.isEmpty(pago.getContacto().getPersona()
				.getEmails())) {
			pago.getContacto().getPersona().getEmails().size();
		}
		if (pago.getCuenta() != null) {
			pago.getCuenta().getId();
		}
		if (!CollectionUtils.isEmpty(pago.getMovimientos())) {
			for (MovimientoCargoAplicado aplicado : pago.getMovimientos()) {
				aplicado.getMovimientoCargo().getCargo().getMovimientos()
						.size();
				if (!CollectionUtils.isEmpty(aplicado.getMovimientoCargo()
						.getCargo().getMovimientos())) {
					for (MovimientoCargo movimiento : aplicado
							.getMovimientoCargo().getCargo().getMovimientos()) {
						movimiento.getAplicados().size();
					}
				}
			}
		}
		return pago;
	}*/


    @Transactional
    @Override
    public void evict(PagoProveedor pagoProveedor) {
        this.sessionFactory.getCurrentSession().evict(pagoProveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PagoProveedor> getPorFecha(Condominio condominio, Date inicio, Date fin) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PagoProveedor.class);
            criteria.add(Restrictions.eq("condominio", condominio));
            criteria.add(Restrictions.between("fechaPago", inicio, fin));
            Collection<PagoProveedor> pagosProveedores = (Collection<PagoProveedor>) criteria.list();
            for (PagoProveedor pagoProveedor : pagosProveedores) {
                Hibernate.initialize(pagoProveedor.getMovimientos());
                Hibernate.initialize(pagoProveedor.getCondominio());
                for (MovimientoCfdiAplicado mcfdi : pagoProveedor.getMovimientos()) {
                    if (mcfdi.getMovimiento() instanceof MovimientoCfdi) {
                        ((MovimientoCfdi) mcfdi.getMovimiento()).getDetalleCfdi().getDescripcion();
                    } else if (mcfdi.getMovimiento() instanceof MovimientoCfdiImpuesto) {
                        ((MovimientoCfdiImpuesto) mcfdi.getMovimiento()).getCfdi().getFechaEmision();
                    }
                }
                Hibernate.initialize(pagoProveedor.getProveedor());
                Hibernate.initialize(pagoProveedor.getCuenta());
            }
            return pagosProveedores;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

}
