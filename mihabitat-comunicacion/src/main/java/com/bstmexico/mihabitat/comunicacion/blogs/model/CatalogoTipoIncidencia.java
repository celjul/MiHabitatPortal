package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Pegasus on 26/07/2015.
 */
@Entity
@DiscriminatorValue("tipo_incidencia")
public class CatalogoTipoIncidencia extends Catalogo {

    /**
     *
     */
    private static final long serialVersionUID = -6114409762463832187L;

    public CatalogoTipoIncidencia() {
        super();
    }
}
