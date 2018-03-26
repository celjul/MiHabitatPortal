package com.bstmexico.mihabitat.comunicacion.tareas.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "trecordatoriosadministracion")
public class RecordatorioAdministracion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3815819234550570986L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdRecordatorio", nullable = false, unique = true)
    private Long id;

    @NotNull
    @JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Condominio condominio;

    @Column(name = "DFecha", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fecha;

    @Size(max = 128)
    @Column(length = 128, name = "VTitulo", nullable = false)
    private String titulo;

    @Size(max = 1024)
    @Column(length = 1024, name = "VDetalle", nullable = true)
    private String detalle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
