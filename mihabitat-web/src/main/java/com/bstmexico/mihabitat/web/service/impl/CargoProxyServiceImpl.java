package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.cargos.model.*;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/*
*
 * Created by Pegasus on 02/07/2015.
*/


@Service("cargoserviceproxy")
public class CargoProxyServiceImpl implements CargoService {

    @Autowired
    @Qualifier("cargoservicedefault")
    private CargoService cargoService;

    @Autowired
    private NotificationHelperService notificationHelperService;

    @Override
    public void save(Cargo cargo) {
        cargoService.save(cargo);
        Date hoy = new Date();
        if(cargo instanceof CargoAgrupador){
            CargoAgrupador cargoAgrupador = (CargoAgrupador)cargo;
            if(cargoAgrupador.getFecha().before(hoy)){
                //SE COMENTA PARA QUE NO ENV?E NOTIFICACIONES A LOS CONCOMINOS
                notificationHelperService.enviarNotificacionNuevosCargos(cargoAgrupador);
            }
        }
        else if(cargo instanceof CargoDepartamento){
            CargoDepartamento cargoDepartamento = (CargoDepartamento) cargo;
            if(cargoDepartamento.getFecha().before(hoy)) {
                //SE COMENTA PARA QUE NO ENV?E NOTIFICACIONES A LOS CONCOMINOS
                notificationHelperService.enviarNotificacionNuevoCargo(cargoDepartamento);
            }
        }
    }

    @Override
    public Cargo get(Long id, Class<? extends Cargo> cargo, Boolean desbordar) {
        return cargoService.get(id, cargo, desbordar);
    }

    @Override
    public void update(Cargo cargo) {
        cargoService.update(cargo);
    }

    @Override
    public Collection<Cargo> search(Map map, Class<? extends Cargo> cargo, Boolean desbordar) {
        return cargoService.search(map, cargo, desbordar);
    }

    @Override
    public Collection<CargoDepartamento> getList(Condominio condominio, Date inicio, Date fin) {
        return cargoService.getList(condominio, inicio, fin);
    }

    @Override
    public CargoAgrupador getAnterior(Condominio condominio, TipoConsumo consumo) {
        return cargoService.getAnterior(condominio, consumo);
    }



    @Override
    public Collection<CargoDepartamento> getCargos(Pago pago) {
        return cargoService.getCargos(pago);
    }

    @Override
    public Collection<CargoDepartamento> getCargos(PagoDepartamento pago) {
        return cargoService.getCargos(pago);
    }

    @Override
    public Collection<CargoDepartamento> getCargosPorFecha(Date fecha) {
        return cargoService.getCargosPorFecha(fecha);
    }

    @Override
    public Collection<CargoRecurrente> getCargosRecurrentesPorFecha(Date fecha) {
        return cargoService.getCargosRecurrentesPorFecha(fecha);
    }

    @Override
    public Collection<CargoDepartamento> getCargosRecargosPorAplicar(Date fecha) {
        return cargoService.getCargosRecargosPorAplicar(fecha);
    }

    @Override
    public Cargo getConDepartamento(Long id, Class<? extends Cargo> cargo, Boolean desbordar) {
        return cargoService.getConDepartamento(id, cargo, desbordar);
    }

    @Override
    public void generarDescuento(MovimientoCargo descuento) {
        cargoService.generarDescuento(descuento);
        //SE COMENTA PARA QUE NO ENV?E NOTIFICACIONES A LOS CONCOMINOS
        //notificationHelperService.enviarNotificacionNuevoMovimiento(descuento);
    }

    @Override
    public void generarRecargo(MovimientoCargo recargo) {
        cargoService.generarRecargo(recargo);
        //SE COMENTA PARA QUE NO ENV?E NOTIFICACIONES A LOS CONCOMINOS
        //notificationHelperService.enviarNotificacionNuevoMovimiento(recargo);
    }

    @Override
    public void cancelarDescuento(MovimientoCargo descuento) {
        cargoService.cancelarDescuento(descuento);
        // TODO se hara notificacion de cancelacionde desceunto?? notificationHelperService.enviarNotificacionNuevoMovimiento(descuento);
    }

    @Override
    public void cancelarRecargo(MovimientoCargo recargo) {
        cargoService.cancelarRecargo(recargo);
        // TODO se hara notificacion de cancelacionde recargo?? notificationHelperService.enviarNotificacionNuevoMovimiento(recargo);
    }

    @Override
    public void cancelarCargo(CargoDepartamento cargo) {
        cargoService.cancelarCargo(cargo);
        // TODO se hara notificacion de cancelacionde cargo?? notificationHelperService.enviarNotificacionNuevoCargo(cargo);
    }

    @Override
    public Collection<CargoDepartamento> getCargos(Collection<Departamento> departamentos) {
        return cargoService.getCargos(departamentos);
    }

    @Override
    public Collection<CargoDepartamento> getCargosPendientes(Departamento departamento, Date fin) {
        return cargoService.getCargosPendientes(departamento, fin);
    }

    @Override
    public Collection<CargoDepartamento> getCargos(Contacto contacto, Date fecha) {
        return cargoService.getCargos(contacto, fecha);
    }
}
