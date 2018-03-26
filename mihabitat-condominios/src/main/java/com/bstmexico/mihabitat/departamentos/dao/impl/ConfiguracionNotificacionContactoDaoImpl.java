package com.bstmexico.mihabitat.departamentos.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.dao.ConfiguracionNotificacionContactoDao;
import com.bstmexico.mihabitat.departamentos.model.ConfiguracionNotificacionContacto;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Pegasus on 16/06/2015.
 */
@Repository
public class ConfiguracionNotificacionContactoDaoImpl extends GenericDaoImpl<ConfiguracionNotificacionContacto, Long> implements
        ConfiguracionNotificacionContactoDao {

        @Override
        @Transactional(readOnly = true)
        public Collection<ConfiguracionNotificacionContacto> getByCondominio(Condominio condominio){
                try {
                        String query = "SELECT cnf FROM ConfiguracionNotificacionContacto cnf join cnf.contactoDepartamento cd"
                                + " join cd.id cdid "
                                + " join cdid.contacto c"
                                + " WHERE c.condominio.id = :idCondominio ";

                        Collection<ConfiguracionNotificacionContacto> cnfs = (Collection<ConfiguracionNotificacionContacto>)sessionFactory.getCurrentSession()
                                .createQuery(query).setParameter("idCondominio", condominio.getId()).list();


                        /*Criteria criteria = sessionFactory.getCurrentSession()
                                .createCriteria(ConfiguracionNotificacionContacto.class);
                        criteria.createAlias("contactoDepartamento","cd");
                        criteria.createAlias("cd.id.contacto", "c");
                        criteria.add(Restrictions.eq("c.condominio", condominio));
                        Collection<ConfiguracionNotificacionContacto> cnfs = criteria.list();*/

                        return cnfs;
                } catch (HibernateException ex) {
                        ApplicationException ex1 = new DataAccessException("DAO009", ex);
                        LOG.error(ex.getMessage(), ex);
                        throw ex1;
                }
        }
}
