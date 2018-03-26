package com.bstmexico.mihabitat.presupuestos.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.presupuestos.model.Presupuesto;

import java.util.Collection;

/**
 * Created by Pegasus on 08/06/2015.
 */
public interface PresupuestoDao extends GenericDao<Presupuesto, Long> {

    Presupuesto get(Cuenta cuenta, Integer anio);

    Collection<Integer> getPresupuestosAnios(Condominio condominio);
}
