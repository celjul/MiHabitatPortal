package com.bstmexico.mihabitat.proveedores.gastos.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.proveedores.gastos.dao.GastoDao;
import com.bstmexico.mihabitat.proveedores.gastos.model.Detalle;
import com.bstmexico.mihabitat.proveedores.gastos.model.Gasto;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
@Repository
public class GastoDaoImpl extends GenericDaoImpl<Gasto, Long> implements GastoDao {

    @Transactional(readOnly = true)
    @Override
    public Gasto get(Long arg0) {
        Gasto gasto = super.get(arg0);
        Hibernate.initialize(gasto.getDetalles());
        for (Detalle detalle : gasto.getDetalles()) {
            Hibernate.initialize(detalle.getMovimientoDetallle().getCuenta());
        }
        Hibernate.initialize(gasto.getProveedor());
        Hibernate.initialize(gasto.getMovimientoGasto().getCuenta());
        return gasto;
    }

    @SuppressWarnings("rawtypes")
    @Transactional(readOnly = true)
    @Override
    public Collection<Gasto> search(Set<Entry> arg0) {
        Collection<Gasto> gastos = super.search(arg0);
        for (Gasto gasto : gastos) {
            Hibernate.initialize(gasto.getProveedor());
            Hibernate.initialize(gasto.getMovimientoGasto().getCuenta());
        }
        return gastos;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Gasto> getPorFecha(Condominio condominio, Date inicio, Date fin) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Gasto.class);
            criteria.add(Restrictions.eq("condominio", condominio));
            criteria.add(Restrictions.between("fecha", inicio, fin));
            Collection<Gasto> gastos = (Collection<Gasto>) criteria.list();
            for (Gasto gasto : gastos) {
                Hibernate.initialize(gasto.getDetalles());
                Hibernate.initialize(gasto.getCondominio());
                for (Detalle detalle : gasto.getDetalles()) {
                    Hibernate.initialize(detalle.getMovimientoDetallle().getCuenta());
                }
                Hibernate.initialize(gasto.getProveedor());
                Hibernate.initialize(gasto.getMovimientoGasto().getCuenta());
            }
            return gastos;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

}
