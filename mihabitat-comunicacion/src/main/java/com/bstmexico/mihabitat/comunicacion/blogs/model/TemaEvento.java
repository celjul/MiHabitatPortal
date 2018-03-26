package com.bstmexico.mihabitat.comunicacion.blogs.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Pegasus on 11/08/2015.
 */
@Entity
@DiscriminatorValue("tema_evento")
public class TemaEvento extends Tema {

    /**
     *
     */
    private static final long serialVersionUID = 1650156464297952659L;

    @Column(name = "DFechaInicio", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fechaInicio;


    @Column(name = "DFechaFin", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fechaFin;

    public DateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(DateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public DateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(DateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}
