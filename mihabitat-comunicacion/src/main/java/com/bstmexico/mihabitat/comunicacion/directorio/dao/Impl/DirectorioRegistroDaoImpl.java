package com.bstmexico.mihabitat.comunicacion.directorio.dao.Impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunicacion.directorio.dao.DirectorioRegistroDao;
import com.bstmexico.mihabitat.comunicacion.directorio.model.DirectorioRegistro;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Zoe on 25/01/2016.
 */
@Repository
public class DirectorioRegistroDaoImpl extends GenericDaoImpl<DirectorioRegistro, Long> implements DirectorioRegistroDao {

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<DirectorioRegistro> getList(Condominio condominio) {

        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(DirectorioRegistro.class);

            criteria.createAlias("condominios", "c", JoinType.LEFT_OUTER_JOIN);
            criteria.add(Restrictions.disjunction().
                    add(Restrictions.eq("pais.id",
                            condominio.getDireccion().getColonia().getMunicipio().getEstado().getPais().getId())).
                    add(Restrictions.eq("estado.id", condominio.getDireccion().getColonia().getMunicipio().getEstado().getId())).
                    add(Restrictions.eq("municipio.id", condominio.getDireccion().getColonia().getMunicipio().getId())).
                    add(Restrictions.eq("c.id", condominio.getId())));
            criteria.addOrder(Order.asc("tipo.id"));

            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Collection<DirectorioRegistro> directorioRegistros = criteria.list();

            return directorioRegistros;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }
}
