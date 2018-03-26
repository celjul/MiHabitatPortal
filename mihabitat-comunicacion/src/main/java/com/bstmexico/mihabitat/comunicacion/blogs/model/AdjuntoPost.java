package com.bstmexico.mihabitat.comunicacion.blogs.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Pegasus on 28/07/2015.
 */
@Entity
@Table(name = "tadjuntopost")
public class AdjuntoPost implements Serializable {

    private static final long serialVersionUID = -4934572648392883933L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdAdjunto", nullable = false, unique = true)
    protected Long id;

    @JoinColumn(name = "NIdArchivo", referencedColumnName = "NIdArchivo")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ArchivoAdjuntoPost archivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArchivoAdjuntoPost getArchivo() {
        return archivo;
    }

    public void setArchivo(ArchivoAdjuntoPost archivo) {
        this.archivo = archivo;
    }
}
