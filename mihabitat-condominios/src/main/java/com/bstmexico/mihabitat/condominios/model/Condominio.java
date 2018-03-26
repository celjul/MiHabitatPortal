package com.bstmexico.mihabitat.condominios.model;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tcondominios")
public class Condominio implements Serializable {

	private static final long serialVersionUID = 1354049397051994635L;

	@Fetch(FetchMode.SELECT)
	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tcondominioadministradores", joinColumns = { @JoinColumn(name = "NIdCondominio", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "NIdUsuario", nullable = false) })
	private Set<Usuario> administradores;

	@Valid
	@NotNull
	@JoinColumn(name = "NIdDireccion", nullable = false, referencedColumnName = "NIdDireccion")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
	private DireccionCondominio direccion;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdCondominio", nullable = false, unique = true)
	private Long id;

	@NotEmpty
	@Size(min = 1, max = 128)
	@Column(length = 128, name = "VNombre", nullable = false)
	private String nombre;

	@JoinColumn(name = "NIdLogo", referencedColumnName = "NIdArchivo")
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private ArchivoImagenCondominio logoCondominio;

	/*@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longblob", name = "BLogoCondominio")
	private byte[] logoCondominio;*/

	/*@Size(max = 512)
	@Column(length = 512, name = "VMensajeEstadoCuenta", nullable = true)
	private String mensajeEstadoCuenta;*/

	@JoinColumn(name = "NIdConfiguracion", referencedColumnName = "NIdConfiguracionCondominio")
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private ConfiguracionCondominio configuracionCondominio;

	public Condominio() {
	}

	public Set<Usuario> getAdministradores() {
		return administradores;
	}

	public void setAdministradores(Set<Usuario> administradores) {
		this.administradores = administradores;
	}

	public DireccionCondominio getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionCondominio direccion) {
		this.direccion = direccion;
	}

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

/*	public byte[] getLogoCondominio() {
		return logoCondominio;
	}

	public void setLogoCondominio(byte[] logoCondominio) {
		this.logoCondominio = logoCondominio;
	}*/

	public ArchivoImagenCondominio getLogoCondominio() {
		return logoCondominio;
	}

	public void setLogoCondominio(ArchivoImagenCondominio logoCondominio) {
		this.logoCondominio = logoCondominio;
	}

	public ConfiguracionCondominio getConfiguracionCondominio() {
		return configuracionCondominio;
	}

	public void setConfiguracionCondominio(ConfiguracionCondominio configuracionCondominio) {
		this.configuracionCondominio = configuracionCondominio;
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
		Condominio other = (Condominio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
