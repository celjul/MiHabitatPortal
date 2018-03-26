package com.bstmexico.mihabitat.cobranza.dao.impl;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.cobranza.dao.NotaDao;
import com.bstmexico.mihabitat.cobranza.model.Nota;
import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class NotaDaoImpl extends GenericDaoImpl<Nota, Long> implements NotaDao {

    @Transactional(readOnly = true)
    @Override
    public Collection<Nota> getList(Condominio condominio) {
        Collection<Nota> notas = this.getList();

        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(Nota.class);
        criteria.createAlias("departamento", "d");
        criteria.createAlias("d.condominio", "c");

        criteria.add(Restrictions.eq("c.id", condominio.getId()));

        notas = criteria.list();

        return notas;
    }
}
