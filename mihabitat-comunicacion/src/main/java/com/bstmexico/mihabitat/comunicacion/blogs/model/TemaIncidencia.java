package com.bstmexico.mihabitat.comunicacion.blogs.model;

import javax.persistence.*;

/**
 * Created by Pegasus on 11/08/2015.
 */
@Entity
@DiscriminatorValue("tema_incidencia")
public class TemaIncidencia extends Tema {

    /**
     *
     */
    private static final long serialVersionUID = 1650156283297952659L;

    @JoinColumn(name = "NIdTipoIncidencia", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoIncidencia tipoIncidencia;

    public CatalogoTipoIncidencia getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(CatalogoTipoIncidencia tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }
}
