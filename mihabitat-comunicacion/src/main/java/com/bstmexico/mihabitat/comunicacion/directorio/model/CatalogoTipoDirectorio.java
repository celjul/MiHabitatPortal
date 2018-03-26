package com.bstmexico.mihabitat.comunicacion.directorio.model;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */

@Entity
@DiscriminatorValue("tipo_directorio")
public class CatalogoTipoDirectorio extends Catalogo {
    /**
     *
     */
    private static final long serialVersionUID = -6114409773815432187L;

    public CatalogoTipoDirectorio() {
        super();
    }
}
