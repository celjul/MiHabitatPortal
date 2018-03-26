package com.bstmexico.mihabitat.otrosingresos.dao.Impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.otrosingresos.dao.OtroIngresoDao;
import com.bstmexico.mihabitat.otrosingresos.model.ConceptoIngreso;
import com.bstmexico.mihabitat.otrosingresos.model.OtroIngreso;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Repository
public class OtroIngresoDaoImpl extends GenericDaoImpl<OtroIngreso, Long>
        implements OtroIngresoDao {

        @Transactional(readOnly = true)
        @Override
        public OtroIngreso get(Long arg0) {
                OtroIngreso otroIngreso = super.get(arg0);
                Hibernate.initialize(otroIngreso.getConceptos());
                for (ConceptoIngreso concepto : otroIngreso.getConceptos()) {
                        Hibernate.initialize(concepto.getMovimientoConceptoOtroIngreso().getCuenta());
                }
                Hibernate.initialize(otroIngreso.getMovimientoOtroIngreso().getCuenta());
                return otroIngreso;
        }

        @SuppressWarnings("rawtypes")
        @Transactional(readOnly = true)
        @Override
        public Collection<OtroIngreso> search(Set<Map.Entry> arg0) {
                Collection<OtroIngreso> otrosIngresos = super.search(arg0);
                for (OtroIngreso otroIngreso : otrosIngresos) {
                        Hibernate.initialize(otroIngreso.getMovimientoOtroIngreso().getCuenta());
                }
                return otrosIngresos;
        }
}
