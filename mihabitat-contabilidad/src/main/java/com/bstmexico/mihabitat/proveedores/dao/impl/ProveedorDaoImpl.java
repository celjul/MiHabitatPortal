package com.bstmexico.mihabitat.proveedores.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.bstmexico.mihabitat.proveedores.dao.ProveedorDao;
import com.bstmexico.mihabitat.proveedores.model.ContactoProveedor;
import com.bstmexico.mihabitat.proveedores.model.Proveedor;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

@Repository
public class ProveedorDaoImpl extends GenericDaoImpl<Proveedor, Long>
		implements ProveedorDao {

    private static final Logger LOG;

    static {
        LOG = LoggerFactory
                .getLogger(ProveedorDaoImpl.class);
    }

    @SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	@Override
	public Collection<Proveedor> search(Set<Entry> map) {
		//Collection<Proveedor> proveedores = super.search(map);
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(Proveedor.class);
            for (Entry entry : map) {
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
            Collection<Proveedor> proveedores = criteria.list();

            for (Proveedor proveedor : proveedores) {
                proveedor.getContactos();

                for (ContactoProveedor cp : proveedor.getContactos()) {
                    ((PersonaAbstract) cp).getId();
                    //cp.getPersona().getId();
                }
            }
            return proveedores;

        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
	}

    @Transactional
    @Override
    public Proveedor get(Long key) {
        Proveedor proveedor = super.get(key);
        proveedor.getContactos().size();
        proveedor.getCuenta().getId();

        if (proveedor.getContactos() != null ) {
            for (ContactoProveedor contacto : proveedor.getContactos()) {
                contacto.getEmails().size();
                contacto.getTelefonos().size();
            }
        }
        return proveedor;
    }


    @Transactional(readOnly = true)
    @Override
    public Proveedor findByRFC(Proveedor proveedor) {
        try {

            Assert.notNull(proveedor, "SERE001");
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(Proveedor.class);

            criteria.add(Restrictions.eq("condominio", proveedor.getCondominio() ));

            if ( proveedor.getRfc() != null) {
                criteria.add(Restrictions.eq("rfc", proveedor.getRfc()));
            } else {
                criteria.add(Restrictions.or(Restrictions.isNull("rfc"),
                        Restrictions.eq("rfc", "")));
            }

            Proveedor proveedorExistente = (Proveedor) criteria.uniqueResult();
            if (proveedorExistente != null) {
                proveedorExistente.getCuenta().getId();
                proveedorExistente.getCondominio().getId();

                proveedorExistente.getContactos().size();
                for (ContactoProveedor contactoProveedor: proveedorExistente.getContactos()) {

                    contactoProveedor.getId();
                    contactoProveedor.getEmails().size();
                    contactoProveedor.getTelefonos().size();
                }
                return proveedorExistente;
            } else {
                return new Proveedor();
            }

        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new DataAccessException("DAO004", ex,
                    getType().getSimpleName());
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

}
