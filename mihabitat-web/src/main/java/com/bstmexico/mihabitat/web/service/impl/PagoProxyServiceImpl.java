package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.pagos.model.CatalogoEstatusPago;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Pegasus on 03/07/2015.
 */
@Service("pagoserviceproxy")
public class PagoProxyServiceImpl implements PagoService {

    @Autowired
    @Qualifier("pagoservicedefault")
    public PagoService pagoService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private NotificationHelperServiceImpl notificationHelperService;

    @Override
    public void save(Pago pago, Boolean aplicado) {
        pagoService.save(pago, aplicado);
        EstatusPago e = null;
        Iterator<EstatusPago> it = pago.getEstatus().iterator();
        while (it.hasNext()) {
            e = it.next();
        }
        if(e.getEstatus().getId() == configurationServiceImpl.getPagoPendiente()) {
            notificationHelperService.enviarNotificacionPagoPendiente(pago);
        }
        else {
            //SE COMENTA PARA EVITA EL ENVIO DE NOTIFICACIONES A CONDOMINIOS
            notificationHelperService.enviarNotificacionPagoValidado(pago);
        }
    }

    @Override
    public Pago get(Long id) {
        return pagoService.get(id);
    }

    @Override
    public Collection<Pago> search(Map map) {
        return pagoService.search(map);
    }

    @Override
    public void aprobar(Pago pago) {
        pagoService.aprobar(pago);
        notificationHelperService.enviarNotificacionPagoValidado(pago);
    }

    @Override
    public void rechazar(Pago pago) {
        pagoService.rechazar(pago);
        notificationHelperService.enviarNotificacionPagoValidado(pago);
    }

    @Override
    public void reenviar(Pago pago) {
        pagoService.reenviar(pago);
        notificationHelperService.enviarNotificacionPagoPendiente(pago);
    }

    @Override
    public void cancelar(Pago pago) {
        pagoService.cancelar(pago);
        notificationHelperService.enviarNotificacionPagoValidado(pago);
    }

    @Override
    public Collection<Pago> getPagosByStatus(Condominio condominio, CatalogoEstatusPago estatus) {
        return pagoService.getPagosByStatus(condominio, estatus);
    }

    @Override
    public Collection<Pago> getPagosByStatus(Contacto contacto, CatalogoEstatusPago estatus) {
        return pagoService.getPagosByStatus(contacto, estatus);
    }

    @Override
    public Collection<Pago> getList(Condominio condominio, Date inicio, Date fin) {
        return pagoService.getList(condominio, inicio, fin);
    }

    @Override
    public Collection<PagoDepartamento> getPagos(Departamento departamento) {
        return pagoService.getPagos(departamento);
    }
}
