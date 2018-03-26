package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Pegasus on 26/07/2015.
 */
@Entity
@DiscriminatorValue("estatus_incidencia")
public class CatalogoEstatusIncidencia extends Catalogo {

    private static final long serialVersionUID = 1932710072830839967L;

    public CatalogoEstatusIncidencia() {
        super();
    }
}
