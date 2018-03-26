package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Pegasus on 09/08/2015.
 */
@Entity
@Table(name = "tblogs")
public class Blog implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3813579202558970986L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdBlog", nullable = false, unique = true)
    private Long id;

    @JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoBlog tipo;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(length = 128, name = "VNombre", nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 1, max = 256)
    @Column(length = 128, name = "VDescripcion", nullable = false)
    private String descripcion;

    @Column(name = "DFecha", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fecha;

    @NotNull
    @Column(name = "BActivo", nullable = false)
    private Boolean activo;

    @JoinColumn(name = "NIdBlog", referencedColumnName = "NIdBlog")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Tema> temas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Collection<Tema> getTemas() {
        return temas;
    }

    public void setTemas(Collection<Tema> temas) {
        this.temas = temas;
    }

    public CatalogoTipoBlog getTipo() {
        return tipo;
    }

    public void setTipo(CatalogoTipoBlog tipo) {
        this.tipo = tipo;
    }
}
