package com.bstmexico.mihabitat.notificaciones.dao.impl;

import com.bstmexico.mihabitat.cargos.dao.CargoDao;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.PostDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.PostIncidencia;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.dao.DepartamentoDao;
import com.bstmexico.mihabitat.departamentos.model.CatalogoTipoNotificacion;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.instalaciones.dao.ReservacionDao;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.bstmexico.mihabitat.notificaciones.model.*;
import com.bstmexico.mihabitat.notificaciones.dao.NotificationDao;
import com.bstmexico.mihabitat.movimientos.dao.MovimientoDao;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.pagos.dao.PagoDao;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import java.io.IOException;
import java.util.*;

/**
 * Created by Pegasus on 16/06/2015.
 */
@Repository
public class NotificationDaoImpl extends GenericDaoImpl<Notification, Long> implements
        NotificationDao {

        protected static Properties configuration;

        public NotificationDaoImpl() {
                try {
                        if(configuration == null) {
                                configuration = PropertiesLoaderUtils
                                        .loadAllProperties("configuration.properties");
                        }
                } catch (IOException e) {
                }
        }

        @Override
        @Transactional(readOnly = true)
        public Notification get(Long key) {
                Notification notification = super.get(key);
                if(notification instanceof PagoValidadoNotification) {
                        ((PagoValidadoNotification) notification).getEstatusPago().getPago().getMetodoPago();
                        ((PagoValidadoNotification) notification).getEstatusPago().getUsuario().getPersona().getNombreCompleto();
                } else if(notification instanceof ReservacionValidadaNotification) {
                        ((ReservacionValidadaNotification) notification).getReservacion().getInstalacion().getNombre();
                } else if(notification instanceof NuevoCargoNotification) {
                        ((NuevoCargoNotification) notification).getCargo().getSaldoPendiente();
                }
                return  notification;
        }

        @SuppressWarnings("unchecked")
        @Transactional(readOnly = true)
        @Override
        public Collection<Notification> getByContacto(Contacto contacto) {
                try {
                        Collection<Notification> notificationesTodas = new ArrayList<>();

                        /*for(Class clase : tiposDeNotificaciones) {
                                if(clase == NuevoCargoNotification.class) {*/
                                        Criteria criteria = sessionFactory.getCurrentSession()
                                                .createCriteria(NuevoCargoNotification.class);
                                        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

                                        criteria.add(Restrictions.eq("condominio", contacto.getCondominio()));
                                        criteria.createAlias("cargo", "c");
                                        criteria.createAlias("c.departamento", "d");
                                        criteria.createAlias("d.contactos", "con");
                                        /*criteria.createAlias("con.configuracionNotificacionContacto", "conf");
                                        criteria.add(Restrictions.disjunction(Restrictions.eq("conf.tipoNotificacionNuevoCargo",
                                                CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")))),
                                                Restrictions.eq("conf.tipoNotificacionNuevoCargo",
                                                        CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")))))
                                        );*/
                                        criteria.add(Restrictions.eq("con.id.contacto", contacto));

                                        Collection<NuevoCargoNotification> notificationesCarTemp = criteria.list();

                                        for(NuevoCargoNotification notTemp : notificationesCarTemp){
                                            for(ContactoDepartamento cd : notTemp.getCargo().getDepartamento().getContactos()){
                                                if(cd.getContacto().equals(contacto) && (cd.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoCargo().
                                                        equals(CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")))) ||
                                                        cd.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoCargo().
                                                                equals(CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))))
                                                    )) {
                                                    notificationesTodas.add(notTemp);
                                                }
                                            }
                                        }
                                /*} else if (clase == NuevoRecargoNotificacion.class) {*/
                                        criteria = sessionFactory.getCurrentSession()
                                                .createCriteria(NuevoRecargoNotificacion.class);
                                        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

                                        criteria.add(Restrictions.eq("condominio", contacto.getCondominio()));
                                        criteria.createAlias("recargo", "r");
                                        criteria.createAlias("r.cargo", "c");
                                        criteria.createAlias("c.departamento", "d");
                                        criteria.createAlias("d.contactos", "con");
                                        /*criteria.add(Restrictions.disjunction(Restrictions.eq("con.configuracionNotificacionContacto.tipoNotificacionNuevoRecargo",
                                                CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")))),
                                                Restrictions.eq("con.configuracionNotificacionContacto.tipoNotificacionNuevoRecargo",
                                                        CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")))))
                                        );*/
                                        criteria.add(Restrictions.eq("con.id.contacto", contacto));

                                        Collection<NuevoRecargoNotificacion> notificationesRecTemp = criteria.list();

                                        for(NuevoRecargoNotificacion notTemp : notificationesRecTemp){
                                            for(ContactoDepartamento cd : notTemp.getRecargo().getCargo().getDepartamento().getContactos()){
                                                if(cd.getContacto().equals(contacto) && (cd.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoRecargo().
                                                        equals(CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")))) ||
                                                        cd.getConfiguracionNotificacionContacto().getTipoNotificacionNuevoRecargo().
                                                                equals(CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))))
                                                )) {
                                                    notificationesTodas.add(notTemp);
                                                }
                                            }
                                        }
                                /*} else if (clase == PagoValidadoNotification.class) {*/
                                        if(contacto.getDepartamentos().iterator().hasNext()) {
                                                if(contacto.getDepartamentos().iterator().next().getConfiguracionNotificacionContacto().getTipoNotificacionAbonoValidado().getId().
                                                        equals(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))) ||
                                                        contacto.getDepartamentos().iterator().next().getConfiguracionNotificacionContacto().getTipoNotificacionAbonoValidado().getId().
                                                                equals(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")))) {

                                                        criteria = sessionFactory.getCurrentSession()
                                                                .createCriteria(PagoValidadoNotification.class);
                                                        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

                                                        criteria.add(Restrictions.eq("condominio", contacto.getCondominio()));
                                                        criteria.createAlias("estatusPago", "e");
                                                        criteria.createAlias("e.pago", "p");
                                                        criteria.add(Restrictions.eq("p.contacto", contacto));

                                                        notificationesTodas.addAll(criteria.list());
                                                }
                                        }
                                /*} else if (clase == ReservacionValidadaNotification.class) {*/
                                        if(contacto.getDepartamentos().iterator().hasNext()) {
                                                if (contacto.getDepartamentos().iterator().next().getConfiguracionNotificacionContacto().getTipoNotificacionReservacionValidada().getId().
                                                        equals(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))) ||
                                                        contacto.getDepartamentos().iterator().next().getConfiguracionNotificacionContacto().getTipoNotificacionReservacionValidada().getId().
                                                                equals(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")))) {

                                                        criteria = sessionFactory.getCurrentSession()
                                                                .createCriteria(ReservacionValidadaNotification.class);
                                                        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

                                                        criteria.add(Restrictions.eq("condominio", contacto.getCondominio()));
                                                        criteria.createAlias("reservacion", "r");
                                                        criteria.add(Restrictions.eq("r.contacto", contacto));

                                                        notificationesTodas.addAll(criteria.list());
                                                }
                                        }
                                /*} else if (clase == IncidenciaActualizadaNotification.class) {
                                        //TODO: Notificaciones de actualizaciones de incidencias.
                                }
                        }*/


                        /*Criteria criteria = sessionFactory.getCurrentSession()
                                .createCriteria(Notification.class);

                        criteria.add(Restrictions.eq("condominio", contacto.getCondominio()));

                        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                        Collection<Notification> notificationesTodas = criteria.list();*/

                        for(Notification notification : notificationesTodas) {
                                if(notification instanceof PagoValidadoNotification) {
                                        ((PagoValidadoNotification) notification).getEstatusPago().getPago().getMetodoPago();
                                        ((PagoValidadoNotification) notification).getEstatusPago().getUsuario().getPersona().getNombreCompleto();
                                } else if(notification instanceof ReservacionValidadaNotification) {
                                        ((ReservacionValidadaNotification) notification).getReservacion().getInstalacion().getNombre();
                                } else if(notification instanceof NuevoCargoNotification) {
                                        ((NuevoCargoNotification) notification).getCargo().getSaldoPendiente();
                                } else if(notification instanceof NuevoRecargoNotificacion) {
                                        ((NuevoRecargoNotificacion) notification).getRecargo().getCargo().getSaldoPendiente();
                                }
                        }

                        /*for(Notification notification : notificationesTodas) {
                                if(notification instanceof NuevoCargoNotification) {
                                        CargoDepartamento cargo = (CargoDepartamento) cargoDao.get(
                                                notification.getIdReferencia(), CargoDepartamento.class, false);
                                        if(cargo != null) {
                                                if(cargo.getDepartamento() != null && cargo.getDepartamento().getId() != null && cargo.getDepartamento().getNombre() == null) {
                                                        cargo.setDepartamento(departamentoDao.get(cargo.getDepartamento().getId()));
                                                }
                                                for (ContactoDepartamento contactoDepartamento : cargo.getDepartamento().getContactos()) {
                                                        //TODO: Checar esta parte, encaso de que el contacto est? en dos edificios diferentes por alguna raz?n el contactodepartamento no tra? contacto
                                                        if (contactoDepartamento.getId().getContacto() == null || contactoDepartamento.getId().getContacto().equals(contacto)) {
                                                                ((NuevoCargoNotification) notification).setCargo(cargo);
                                                                notificaciones.add(notification);
                                                                break;
                                                        }
                                                }
                                        }
                                }
                                else if(notification instanceof NuevoRecargoNotificacion) {
                                        MovimientoCargo recargo = (MovimientoCargo) movimientoDao.get(notification.getIdReferencia(), MovimientoCargo.class);
                                        if(recargo != null) {
                                                recargo = movimientoDao.getConCargo(recargo.getId(), MovimientoCargo.class);
                                                for (ContactoDepartamento contactoDepartamento : recargo.getCargo().getDepartamento().getContactos()) {
                                                        if (contactoDepartamento.getId().getContacto().equals(contacto)) {
                                                                ((NuevoRecargoNotificacion) notification).setRecargo(recargo);
                                                                notificaciones.add(notification);
                                                                break;
                                                        }
                                                }
                                        }
                                }
                                else if(notification instanceof PagoValidadoNotification) {
                                        EstatusPago estatusPago = (EstatusPago) pagoDao.getPagoByStatus(notification.getIdReferencia());
                                        if(estatusPago != null) {
                                                if(estatusPago.getPago().getContacto().equals(contacto)) {
                                                        ((PagoValidadoNotification) notification).setEstatusPago(estatusPago);
                                                        notificaciones.add(notification);
                                                }
                                        }
                                }
                                else if(notification instanceof ReservacionValidadaNotification) {
                                        Reservacion reservacion = reservacionDao.getConInstalacion(notification.getIdReferencia());
                                        if(reservacion != null) {
                                                if(reservacion.getContacto().equals(contacto)) {
                                                        ((ReservacionValidadaNotification) notification).setReservacion(reservacion);
                                                        notificaciones.add(notification);
                                                }
                                        }
                                }
                                else if(notification instanceof IncidenciaActualizadaNotification) {
                                        PostIncidencia avanceIncidencia = (PostIncidencia)postDao.get(notification.getIdReferencia());
                                        if(avanceIncidencia != null) {
                                                Contacto contactoAvance = contactoService.get(notification.getCondominio(), avanceIncidencia.getTema()
                                                        .getPosts().iterator().next().getUsuario().getEmail());
                                                if(contactoAvance != null && contactoAvance.equals(contacto)) {
                                                        ((IncidenciaActualizadaNotification) notification).setAvanceIncidencia(avanceIncidencia);
                                                        notificaciones.add(notification);
                                                }
                                        }
                                }
                        }*/

                        return notificationesTodas;
                } catch (HibernateException ex) {
                        ApplicationException ex1 = new DataAccessException("DAO009", ex);
                        LOG.error(ex.getMessage(), ex);
                        throw ex1;
                }
        }
}
