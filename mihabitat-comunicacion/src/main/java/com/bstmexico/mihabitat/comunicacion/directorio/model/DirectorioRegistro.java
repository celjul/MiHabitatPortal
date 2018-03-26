package com.bstmexico.mihabitat.comunicacion.directorio.model;

import com.bstmexico.mihabitat.comunes.direcciones.model.Estado;
import com.bstmexico.mihabitat.comunes.direcciones.model.Municipio;
import com.bstmexico.mihabitat.comunes.direcciones.model.Pais;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tdirectorioregistro")
public class DirectorioRegistro {

    private static final long serialVersionUID = 3813579213558837986L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdRegistroDirectorio", nullable = false, unique = true)
    private Long id;

    @Size(max = 128)
    @Column(length = 128, name = "VTitulo", nullable = false)
    private String titulo;

    @Size(max = 128)
    @Column(length = 128, name = "VTelefono", nullable = true)
    private String telefono;

    @Size(max = 128)
    @Column(length = 128, name = "VEmail", nullable = true)
    private String email;

    @Size(max = 128)
    @Column(length = 128, name = "VPagina", nullable = true)
    private String pagina;

    @Size(max = 512)
    @Column(length = 512, name = "VDireccion", nullable = true)
    private String direccion;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tdirectoriocondominios",
            joinColumns = { @JoinColumn(name = "NIdRegistroDirectorio", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "NIdCondominio", nullable = false) })
    private Collection<Condominio> condominios;

    @JoinColumn(name = "NIdPais", nullable = true, referencedColumnName = "NIdPais")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Pais pais;

    @JoinColumn(name = "NIdEstado", nullable = true, referencedColumnName = "NIdEstado")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Estado estado;

    @JoinColumn(name = "NIdMunicipio", nullable = true, referencedColumnName = "NIdMunicipio")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Municipio municipio;

    @JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoDirectorio tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Collection<Condominio> getCondominios() {
        if(condominios == null) {
            condominios = new ArrayList<>();
        }
        return condominios;
    }

    public void setCondominios(Collection<Condominio> condominios) {
        this.condominios = condominios;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public CatalogoTipoDirectorio getTipo() {
        return tipo;
    }

    public void setTipo(CatalogoTipoDirectorio tipo) {
        this.tipo = tipo;
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
        DirectorioRegistro other = (DirectorioRegistro) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
