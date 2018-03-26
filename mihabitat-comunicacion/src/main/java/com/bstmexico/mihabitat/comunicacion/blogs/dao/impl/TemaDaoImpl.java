package com.bstmexico.mihabitat.comunicacion.blogs.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.TemaDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.*;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Created by Pegasus on 26/07/2015.
 */
@Repository
public class TemaDaoImpl extends GenericDaoImpl<Tema, Long> implements
        TemaDao {

        @Transactional(readOnly = true)
        @Override
        public Tema get(Long id) {
                Tema tema = super.get(id);
                tema.getBlog().getId();
                tema.getPosts().size();
                Hibernate.initialize(tema.getCondominio());
                return tema;
        }

        @Transactional(readOnly = true)
        @Override
        public Collection<Tema> search(Set<Map.Entry> set) {
                Collection<Tema> temas = super.search(set);
                for(Tema tema : temas) {
                        tema.getPosts().size();
                }
                return temas;
        }

        @Transactional(readOnly = true)
        @Override
        public Tema getConArchivos(Long id) {
                Tema tema = get(id);
                tema.getBlog().getId();
                for(Post post : tema.getPosts()){
                        post.getAdjuntos().size();
                }
                /*tema.getTemasRevisados().size();*/

                return tema;
        }

        @Transactional(readOnly = true)
        @Override
        public Collection<Tema> getList(Condominio condominio) {
                Collection<Tema> temas = this.getList();
                Collection<Tema> temasTemp = new ArrayList<>();
                for(Tema tema : temas) {
                        Hibernate.initialize(tema.getCondominio());

                        if (tema.getCondominio() == null){
                                temasTemp.add(tema);
                        } else if (condominio != null && tema.getCondominio() != null && tema.getCondominio().getId() != null) {
                                if(tema.getCondominio().getId() == condominio.getId()) {
                                        temasTemp.add(tema);
                        /*blog.getTemas().remove(tema);*/
                                }
                        }

                        /*if (condominio != null && tema.getCondominio() != null && tema.getCondominio().getId() != null) {
                                if(tema.getCondominio().getId() == condominio.getId()) {
                                        temasTemp.add(tema);
                                }
                        }*/
                }
                return temasTemp;
        }

        @Transactional(readOnly = true)
        @Override
        public Collection<Tema> getListConPosts(Condominio condominio) {
                try {
                        Criteria criteria = sessionFactory.getCurrentSession()
                                .createCriteria(Tema.class);
                        criteria.createAlias("condominio", "c");
                        criteria.add(Restrictions.eq("c.id", condominio.getId()));
                        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                        Collection<Tema> temas = criteria.list();

                        for(Tema tema : temas) {
                                tema.getCondominio().getId();
                                tema.getPosts().size();
                                tema.getBlog().getId();
                        }
                        return temas;
                } catch (HibernateException ex) {
                        ApplicationException ex1 = new DataAccessException("DAO009", ex);
                        LOG.error(ex.getMessage(), ex);
                        throw ex1;
                }
        }

        @Transactional(readOnly = true)
        @Override
        public Collection<Tema> getList(Condominio condominio, Blog blog) {
                Map map = new HashMap();
                map.put("blog.id", blog.getId());

                Collection<Tema> temas = this.search(map.entrySet());
                Collection<Tema> temasTemp = new ArrayList<>();
                for(Tema tema : temas) {
                        Hibernate.initialize(tema.getCondominio());
                        if (tema.getCondominio() == null){
                                temasTemp.add(tema);
                        } else if (condominio != null && tema.getCondominio() != null && tema.getCondominio().getId() != null) {
                                if(tema.getCondominio().getId() == condominio.getId()) {
                                        temasTemp.add(tema);
                        /*blog.getTemas().remove(tema);*/
                                }
                        }
                        /*if (condominio != null && tema.getCondominio() != null && tema.getCondominio().getId() != null) {
                                if(tema.getCondominio().getId() == condominio.getId()) {
                                        temasTemp.add(tema);
                                        *//*temas.remove(tema);*//*
                                }
                        }*/
                }
                return temasTemp;
        }

        @SuppressWarnings("rawtypes")
        @Transactional(readOnly = true)
        @Override
        public Collection<TemaIncidencia> getIncidenciasByStatus(Condominio condominio, CatalogoEstatusIncidencia estatus) {
                try {
                        String query = "SELECT t FROM TemaIncidencia t join t.posts p"
                                + " WHERE p.id =  (SELECT MAX(id) " +
                                "FROM PostIncidencia " +
                                "WHERE tema.id = t.id) AND p.estatusIncidencia.id = :estatus " +
                                "AND (t.condominio.id = :condominio OR t.condominio.id IS NULL) ";

                        Collection<TemaIncidencia> incidencias = (Collection<TemaIncidencia>)sessionFactory.getCurrentSession()
                                .createQuery(query).setParameter("estatus", estatus.getId())
                                .setParameter("condominio", condominio.getId()).list();

                        for (TemaIncidencia incidencia : incidencias) {
                                incidencia.getPosts().size();
                        }

                        return incidencias;
                } catch (IllegalArgumentException ex) {
                        ApplicationException ex1 = new DataAccessException("DAO004", ex,
                                getType().getSimpleName());
                        LOG.error(ex.getMessage(), ex);
                        throw ex1;
                } catch (HibernateException ex) {
                        ApplicationException ex1 = new DataAccessException("DAO009", ex);
                        LOG.error(ex.getMessage(), ex);
                        throw ex1;
                }
        }

        @SuppressWarnings("rawtypes")
        @Transactional(readOnly = true)
        @Override
        public Collection<TemaIncidencia> getIncidenciasByStatus(Contacto contacto, CatalogoEstatusIncidencia estatus) {
                try {
                        String query = "SELECT t FROM TemaIncidencia t join t.posts p"
                                + " WHERE p.id =  (SELECT MAX(id) " +
                                "FROM PostIncidencia " +
                                "WHERE tema.id = t.id) AND p.estatusIncidencia.id = :estatus ";

                        Collection<TemaIncidencia> incidenciasTemp = (Collection<TemaIncidencia>)sessionFactory.getCurrentSession()
                                .createQuery(query).setParameter("estatus", estatus.getId())
                                .list();
                        Collection<TemaIncidencia> incidencias = new ArrayList<>();
                        for (TemaIncidencia temaIncidencia : incidenciasTemp) {
                                temaIncidencia.getPosts().size();
                                if(temaIncidencia.getUsuario().getId() == contacto.getUsuario().getId()) {
                                        incidencias.add(temaIncidencia);
                                }
                        }
                        return incidencias;
                } catch (IllegalArgumentException ex) {
                        ApplicationException ex1 = new DataAccessException("DAO004", ex,
                                getType().getSimpleName());
                        LOG.error(ex.getMessage(), ex);
                        throw ex1;
                } catch (HibernateException ex) {
                        ApplicationException ex1 = new DataAccessException("DAO009", ex);
                        LOG.error(ex.getMessage(), ex);
                        throw ex1;
                }
        }
}
