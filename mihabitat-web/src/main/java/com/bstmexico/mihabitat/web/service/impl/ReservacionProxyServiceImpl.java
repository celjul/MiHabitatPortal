package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoEstatusReservacion;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.bstmexico.mihabitat.instalaciones.service.ReservacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Pegasus on 13/07/2015.
 */
@Service("reservacionserviceproxy")
public class ReservacionProxyServiceImpl implements ReservacionService {

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private NotificationHelperServiceImpl notificationHelperService;

    @Autowired
    @Qualifier("reservacionservicedefault")
    private ReservacionService reservacionService;

    @Override
    public Collection<Reservacion> getList(Instalacion instalacion) {
        return reservacionService.getList(instalacion);
    }

    @Override
    public void save(Reservacion reservacion) {
        reservacionService.save(reservacion);
        if(reservacion.getEstatusReservacion().getId() == configurationServiceImpl.getReservacionPendiente()){
            notificationHelperService.enviarNotificacionReservacionPendiente(reservacion);
        }
        else {
            if(reservacion.getContacto() != null) {
                notificationHelperService.enviarNotificacionReservacionValidada(reservacion);
            }
        }
    }

    @Override
    public Reservacion get(Long id) {
        return reservacionService.get(id);
    }

    @Override
    public void update(Reservacion reservacion) {
        reservacionService.update(reservacion);
        if(reservacion.getEstatusReservacion().getId() == configurationServiceImpl.getReservacionPendiente()){
            notificationHelperService.enviarNotificacionReservacionPendiente(reservacion);
        }
        else {
            if(reservacion.getContacto() != null) {
                notificationHelperService.enviarNotificacionReservacionValidada(reservacion);
            }
        }
    }

    @Override
    public Collection<Reservacion> search(Map map) {
        return reservacionService.search(map);
    }

    /*@Override
    public void saveAll(Collection<Reservacion> reservaciones) {
        reservacionService.saveAll(reservaciones);
    }

    @Override
    public void deleteAll(Collection<Reservacion> reservaciones) {
        reservacionService.deleteAll(reservaciones);
    }*/

    @Override
    public Collection<Reservacion> getListByCondominioByEstatus(Condominio condominio,
                                                                CatalogoEstatusReservacion estatus) {
        return reservacionService.getListByCondominioByEstatus(condominio, estatus);
    }

    @Override
    public void delete(Reservacion reservacion) {
        reservacionService.delete(reservacion);
    }

    @Override
    public Reservacion getConInstalacion(Long id) {
        return reservacionService.getConInstalacion(id);
    }
}
