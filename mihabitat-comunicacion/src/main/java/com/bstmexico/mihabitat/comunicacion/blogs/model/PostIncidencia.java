package com.bstmexico.mihabitat.comunicacion.blogs.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Pegasus on 18/08/2015.
 */
@Entity
@DiscriminatorValue("post_incidencia")
public class PostIncidencia extends Post {

    /**
     *
     */
    private static final long serialVersionUID = 1650156283297293659L;

    @NotNull
    @JoinColumn(name = "NIdEstatus", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoEstatusIncidencia estatusIncidencia;

    public CatalogoEstatusIncidencia getEstatusIncidencia() {
        return estatusIncidencia;
    }

    public void setEstatusIncidencia(CatalogoEstatusIncidencia estatusIncidencia) {
        this.estatusIncidencia = estatusIncidencia;
    }
}
