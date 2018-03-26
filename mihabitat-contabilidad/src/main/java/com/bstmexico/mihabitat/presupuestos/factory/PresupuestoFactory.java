package com.bstmexico.mihabitat.presupuestos.factory;

import com.bstmexico.mihabitat.presupuestos.model.Presupuesto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Pegasus on 08/06/2015.
 */
public class PresupuestoFactory {
    public static final Logger LOG = LoggerFactory
            .getLogger(PresupuestoFactory.class);

    public static Presupuesto newInstance() {
        return new Presupuesto();
    }
}
