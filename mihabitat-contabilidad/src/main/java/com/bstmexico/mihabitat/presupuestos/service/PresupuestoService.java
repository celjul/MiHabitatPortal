package com.bstmexico.mihabitat.presupuestos.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.presupuestos.model.Presupuesto;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Pegasus on 08/06/2015.
 */

public interface PresupuestoService {

    void save(Presupuesto presupuesto);

    Presupuesto get(Cuenta cuenta, Integer anio);

    Collection<Presupuesto> getList(Condominio condominio, Integer anio);

    void update(Presupuesto presupuesto);

    @SuppressWarnings("rawtypes")
    Collection<Presupuesto> search(Map map);
}
