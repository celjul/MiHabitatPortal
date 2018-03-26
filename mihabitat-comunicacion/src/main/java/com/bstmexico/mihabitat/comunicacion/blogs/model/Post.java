package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.Type;
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
@Table(name = "tposts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "VTipo")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Post implements Serializable, Comparable<Post> {

    /**
     *
     */
    private static final long serialVersionUID = -3813523402558970986L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdPost", nullable = false, unique = true)
    private Long id;

    @JsonIgnoreProperties(value = { "posts" })
    @JoinColumn(name = "NIdTema", nullable = false, referencedColumnName = "NIdTema")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Tema tema;

    @Size(max = 512)
    @Column(name = "VComentario", length = 512)
    private String comentario;

    @JoinColumn(name = "NIdUsuario", referencedColumnName = "NIdUsuario", updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "NIdPost", nullable = false, referencedColumnName = "NIdPost")
    private Collection<AdjuntoPost> adjuntos;

    @Column(name = "DFecha", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fecha;

    @NotNull
    @Column(name = "BActivo", nullable = false)
    private Boolean activo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<AdjuntoPost> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(Collection<AdjuntoPost> adjuntos) {
        this.adjuntos = adjuntos;
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

    @Override
    public int compareTo(Post p) {
        return p.getFecha().compareTo(p.getFecha());
    }
}
