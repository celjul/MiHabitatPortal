package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoSaldo;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by Zoé Jonatan Tapia Hernández on 30/10/2015.
 */
@Service("movimientoserviceproxy")
public class MovimientoProxyServiceImpl implements MovimientoService {

    @Autowired
    private NotificationHelperService notificationHelperService;

    @Autowired
    @Qualifier("movimientoservicedefault")
    private MovimientoService movimientoService;

    @Override
    public void save(Movimiento movimiento) {
        movimientoService.save(movimiento);
        if (movimiento instanceof MovimientoCargo) {
            //SE COMENTA PARA EVITA EL ENVIO DE NOTIFICACIONES A CONDOMINIOS
            notificationHelperService.enviarNotificacionNuevoMovimiento((MovimientoCargo) movimiento);
        }
    }

    @Override
    public Movimiento get(Long id, Class<? extends Movimiento> movimiento) {
        return movimientoService.get(id, movimiento);
    }

    @Override
    public MovimientoCargo getConCargo(Long id, Class<? extends Movimiento> movimiento) {
        return movimientoService.getConCargo(id, movimiento);
    }

    @Override
    public void update(Movimiento movimiento) {
        movimientoService.update(movimiento);
    }

    @Override
    public void delete(Movimiento movimiento) {
        movimientoService.delete(movimiento);
    }

    @Override
    public Collection<Movimiento> search(Map map, Class<? extends Movimiento> movimiento) {
        return movimientoService.search(map, movimiento);
    }

    @Override
    public BigDecimal getSaldoFavor(Contacto contacto) {
        return movimientoService.getSaldoFavor(contacto);
    }

    @Override
    public BigDecimal getSaldoFavorPorDepartamento(Departamento departamento) {
        return movimientoService.getSaldoFavorPorDepartamento(departamento);
    }

    @Override
    public BigDecimal getSaldoFavorPorDepartamento(Departamento departamento, Date inicio, Date fin) {
        return movimientoService.getSaldoFavorPorDepartamento(departamento, inicio, fin);
    }

    @Override
    public Collection<MovimientoSaldo> getSaldosFavor(Departamento departamento) {
        return movimientoService.getSaldosFavor(departamento);
    }

    @Override
    public Collection<Movimiento> getMovimientos(Departamento departamento, Date inicio, Date fin) {
        return movimientoService.getMovimientos(departamento, inicio, fin);
    }

    @Override
    public BigDecimal getDebe(Date fin, Cuenta cuenta) {
        return movimientoService.getDebe(fin, cuenta);
    }

    @Override
    public BigDecimal getHaber(Date fin, Cuenta cuenta) {
        return movimientoService.getHaber(fin, cuenta);
    }

    @Override
    public BigDecimal getDebe(Date inicio, Date fin, Cuenta cuenta) {
        return movimientoService.getDebe(inicio, fin, cuenta);
    }

    @Override
    public BigDecimal getHaber(Date inicio, Date fin, Cuenta cuenta) {
        return movimientoService.getHaber(inicio, fin, cuenta);
    }

    @Override
    public Collection<Movimiento> getMovimientos(Cuenta cuenta, Date inicio, Date fin) {
        return movimientoService.getMovimientos(cuenta, inicio, fin);
    }

    @Override
    public Collection<Movimiento> getMovimientos(Cuenta cuenta, Date fin) {
        return movimientoService.getMovimientos(cuenta, fin);
    }

    @Override
    public BigDecimal getDebeLtEq(Date fin, Cuenta cuenta) {
        return movimientoService.getDebeLtEq(fin, cuenta);
    }

    @Override
    public BigDecimal getHaberLtEq(Date fin, Cuenta cuenta) {
        return movimientoService.getHaberLtEq(fin, cuenta);
    }
}
