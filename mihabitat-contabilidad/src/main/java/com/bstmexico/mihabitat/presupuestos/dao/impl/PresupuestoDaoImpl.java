package com.bstmexico.mihabitat.presupuestos.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.presupuestos.dao.PresupuestoDao;
import com.bstmexico.mihabitat.presupuestos.model.Presupuesto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by Pegasus on 08/06/2015.
 */
@Repository
public class PresupuestoDaoImpl extends GenericDaoImpl<Presupuesto, Long> implements
        PresupuestoDao {

    private final static Logger LOG = LoggerFactory
            .getLogger(PresupuestoDaoImpl.class);

    @Override
    public Presupuesto get(Cuenta cuenta, Integer anio) {
        return null;
    }

    @Override
    public Collection<Integer> getPresupuestosAnios(Condominio condominio) {
        return null;
    }
}
