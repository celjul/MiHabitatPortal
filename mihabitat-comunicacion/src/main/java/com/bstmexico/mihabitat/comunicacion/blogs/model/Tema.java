package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Pegasus on 09/08/2015.
 */
@Entity
@Table(name = "ttemas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "VTipo")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Tema implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3813256702558627986L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdTema", nullable = false, unique = true)
    private Long id;

    @JsonIgnoreProperties(value = { "temas" })
    @JoinColumn(name = "NIdBlog", nullable = false, referencedColumnName = "NIdBlog")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Blog blog;

    @JoinColumn(name = "NIdCondominio", nullable = true, referencedColumnName = "NIdCondominio")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Condominio condominio;

    @JoinColumn(name = "NIdUsuario", referencedColumnName = "NIdUsuario", updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(length = 128, name = "VNombre", nullable = false)
    private String nombre;

    @Column(name = "DFecha", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fecha;

    @NotNull
    @Column(name = "BActivo", nullable = false)
    private Boolean activo;

    @OrderBy("fecha ASC")
    @JoinColumn(name = "NIdTema", referencedColumnName = "NIdTema")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Post> posts;

    @JoinColumn(name = "NIdTema", referencedColumnName = "NIdTema")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Collection<TemaRevisado> temasRevisados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public Collection<TemaRevisado> getTemasRevisados() {
        return temasRevisados;
    }

    public void setTemasRevisados(Collection<TemaRevisado> temasRevisados) {
        this.temasRevisados = temasRevisados;
    }

    @Transient
    public Post getPrimerPost() {
        Post primer = null;
        if(this.getPosts() != null) {
            Iterator<Post> it = this.getPosts().iterator();
            if (it.hasNext()) {
                primer = it.next();
            }
        }
        return primer;
    }

    @Transient
    public Post getUltimoPost() {
        Post ultimo = null;
        if(this.getPosts() != null) {
            Iterator<Post> it = this.getPosts().iterator();
            while (it.hasNext()) {
                ultimo = it.next();
            }
        }
        return ultimo;
    }

    @Transient
    public DateTime getUltimaActividad() {
        return this.getUltimoPost()!=null?this.getUltimoPost().getFecha():null;
    }

    public TemaRevisado getTemaRevisado(Usuario usuario) {
        TemaRevisado tr = null;
        if(this.getTemasRevisados() != null && this.getTemasRevisados().size() > 0) {
            for(TemaRevisado temaRevisado : this.getTemasRevisados()) {
                if(temaRevisado.getUsuario().getId() == usuario.getId()) {
                    tr = temaRevisado;
                }
            }
        }
        return tr;
    }
}
