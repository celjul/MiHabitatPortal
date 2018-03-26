package com.bstmexico.mihabitat.ingresosnoidentificados.dao.Impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.ingresosnoidentificados.dao.IngresoNoIdentificadoDao;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.IngresoNoIdentificado;
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
public class IngresoNoIdentificadoDaoImpl extends GenericDaoImpl<IngresoNoIdentificado, Long>
        implements IngresoNoIdentificadoDao {

        @Transactional(readOnly = true)
        @Override
        public IngresoNoIdentificado get(Long arg0) {
                IngresoNoIdentificado ingresoNoIdentificado = super.get(arg0);
                Hibernate.initialize(ingresoNoIdentificado.getCuentaBanco());
                Hibernate.initialize(ingresoNoIdentificado.getCuentaIngreso());
                Hibernate.initialize(ingresoNoIdentificado.getMovimientos());
                /*Hibernate.initialize(otroIngreso.getConceptos());
                for (ConceptoIngreso concepto : otroIngreso.getConceptos()) {
                        Hibernate.initialize(concepto.getMovimientoConceptoOtroIngreso().getCuenta());
                }*/
                /*if(ingresoNoIdentificado.getMovimientos().iterator().hasNext()){
                        Hibernate.initialize(ingresoNoIdentificado.
                                getMovimientos().iterator().next().getCuenta());
                }*/
                return ingresoNoIdentificado;
        }

        @SuppressWarnings("rawtypes")
        @Transactional(readOnly = true)
        @Override
        public Collection<IngresoNoIdentificado> search(Set<Map.Entry> arg0) {
                Collection<IngresoNoIdentificado> ingresoNoIdentificados = super.search(arg0);
                for (IngresoNoIdentificado ingresoNoIdentificado : ingresoNoIdentificados) {
                        Hibernate.initialize(ingresoNoIdentificado.getCuentaBanco());
                        Hibernate.initialize(ingresoNoIdentificado.getCuentaIngreso());
                        /*if(ingresoNoIdentificado.getMovimientos().iterator().hasNext()){
                                Hibernate.initialize(ingresoNoIdentificado.getMovimientos().
                                        iterator().next().getCuenta());
                        }*/

                }
                return ingresoNoIdentificados;
        }
}
