package com.bstmexico.mihabitat.comunicacion.tareas.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
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
@Table(name = "ttareas")
public class Tarea implements Serializable {

    private static final long serialVersionUID = 3813579213558970986L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdTarea", nullable = false, unique = true)
    private Long id;

    @NotNull
    @JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Condominio condominio;

    @Column(name = "DFecha", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fecha;

    @Column(name = "DFechaVencimiento", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fechaVencimiento;

    @Size(max = 128)
    @Column(length = 128, name = "VTitulo", nullable = false)
    private String titulo;

    @Size(max = 1024)
    @Column(length = 1024, name = "VDetalle", nullable = true)
    private String detalle;

    @NotNull
    @Column(name = "BCompletada")
    private Boolean completada;

    @NotNull
    @Column(name = "BCritica")
    private Boolean critica;

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

    public Boolean getCompletada() {
        return completada;
    }

    public void setCompletada(Boolean completada) {
        this.completada = completada;
    }

    public Boolean getCritica() {
        return critica;
    }

    public void setCritica(Boolean critica) {
        this.critica = critica;
    }

    public DateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(DateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tarea other = (Tarea) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
