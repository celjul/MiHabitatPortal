package com.bstmexico.mihabitat.ingresosnoidentificados.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2016
 */
@Entity
@DiscriminatorValue("estatus_ingreso")
public class CatalogoEstatusIngreso extends Catalogo {

    private static final long serialVersionUID = 1932313421870839967L;

    public CatalogoEstatusIngreso() {
        super();
    }
}
