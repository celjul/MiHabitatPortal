package com.bstmexico.mihabitat.proveedores.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.proveedores.dao.FacturaPorPagarDao;
import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import com.bstmexico.mihabitat.proveedores.model.DetalleFactura;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Repository
public class FacturaPorPagarDaoImpl extends GenericDaoImpl<Cfdi, Long>
        implements FacturaPorPagarDao {

    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(FacturaPorPagarDaoImpl.class);
    }

    @SuppressWarnings("rawtypes")
    @Transactional(readOnly = true)
    @Override
    public Collection<Cfdi> search(Set<Map.Entry> map) {
        Collection<Cfdi> facturaxp = super.search(map);

        for (Cfdi factura : facturaxp) {
            factura.getProveedor().getId();
            factura.getConceptos().size();
            for (DetalleFactura concepto : factura.getConceptos()) {
                if (concepto.getMovimientoCfdi() != null) {
                    concepto.getMovimientoCfdi().getAplicados().size();
                }
            }
            if(factura.getMovimientoImpuestoRetenido() != null)
                Hibernate.initialize(factura.getMovimientoImpuestoRetenido().getAplicados());
            if(factura.getMovimientoImpuestoTrasladado() != null)
                Hibernate.initialize(factura.getMovimientoImpuestoTrasladado().getAplicados());
        }
        return facturaxp;
    }

    @Transactional(readOnly = true)
    @Override
    public Cfdi findByCfdi(Cfdi cfdi) {
        try {

            Assert.notNull(cfdi, "SERE001");
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(Cfdi.class);

            criteria.add(Restrictions.eq("condominio", cfdi.getCondominio()));

            if (cfdi.getUuid() != null) {
                criteria.add(Restrictions.eq("uuid", cfdi.getUuid()));
            } else {
                criteria.add(Restrictions.or(Restrictions.isNull("uuid"),
                        Restrictions.eq("uuid", "")));
            }

            Cfdi cfdiExistente = (Cfdi) criteria.uniqueResult();
            if (cfdiExistente != null) {
                return cfdiExistente;
            } else {
                return new Cfdi();
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
    public Collection<Cfdi> findByRfc(Map map) {
        try {

            Assert.notNull(map, "SERE001");
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(Cfdi.class);
            criteria.add(Restrictions.eq("condominio", map.get("condominio")));

            if (map.get("rfc") != null) {
                criteria.add(Restrictions.eq("rfc", map.get("rfc")));
            } else {
                criteria.add(Restrictions.or(Restrictions.isNull("rfc"),
                        Restrictions.eq("rfc", "")));
            }
            criteria.addOrder(org.hibernate.criterion.Order
                    .asc("fechaVencimiento"));
            Collection<Cfdi> cfdis = criteria.list();

            if (!CollectionUtils.isEmpty(cfdis)) {
                for (Cfdi cfdi : cfdis) {
                    Hibernate.initialize(cfdi.getConceptos());
                    if (!CollectionUtils.isEmpty(cfdi.getConceptos())) {
                        for (DetalleFactura det : cfdi.getConceptos()) {
                            Hibernate.initialize(det.getMovimientoCfdi().getAplicados());
                            if (!CollectionUtils.isEmpty(det.getMovimientoCfdi().getAplicados())) {
                                for (MovimientoCfdiAplicado apl : det.getMovimientoCfdi().getAplicados()) {
                                    apl.setMovimiento(null);
                                }
                            }
                        }
                    }
                }
            }
            return cfdis;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new DataAccessException("DAO004", ex,
                    getType().getSimpleName());
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Cfdi get(Long id) {
        try {
            Cfdi c = (Cfdi) this.sessionFactory.getCurrentSession().get(
                    Cfdi.class, id);
            Hibernate.initialize(c.getCondominio());
            Hibernate.initialize(c.getConceptos());
            return c;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new DataAccessException("DAO001", ex,
                    getType().getSimpleName());
            LOG.error(ex1.getMessage(), ex);
            throw ex1;
        }
    }

}
