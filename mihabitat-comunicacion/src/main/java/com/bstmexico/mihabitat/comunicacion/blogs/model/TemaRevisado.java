package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Pegasus on 24/08/2015.
 */
@Entity
@Table(name = "ttemarevisado")
public class TemaRevisado implements Serializable {

    private static final long serialVersionUID = -6582229462400663493L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdTemaRevisado", nullable = false, unique = true)
    private Long id;

    @JoinColumn(name = "NIdUsuario", referencedColumnName = "NIdUsuario", updatable = false, nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @JsonIgnoreProperties(value = { "temasRevisados", "posts" })
    @JoinColumn(name = "NIdTema", nullable = false, referencedColumnName = "NIdTema")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tema tema;

    @Column(name = "DFecha", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }
}
