package com.bstmexico.mihabitat.comunes.usuarios.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;

import com.bstmexico.mihabitat.comunes.personas.model.Persona;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tusuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -6582217662400663493L;

	@NotNull
	@Column(name = "BActivo", nullable = false)
	private Boolean activo;

	@Email
	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VEmail", nullable = false, unique = true)
	private String email;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdUsuario", nullable = false, unique = true)
	private Long id;

	@NotNull
	@Size(min = 32, max = 32)
	@Column(name = "VPassword", nullable = false, length = 32)
	private String password;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Persona.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "NIdPersona", nullable = false, unique = true)
	private Persona persona;

	@Size(min = 1)
	@NotNull
	@Fetch(FetchMode.SELECT)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tusuarioroles", joinColumns = { 
			@JoinColumn(name = "NIdUsuario", nullable = false) }, 
			inverseJoinColumns = { 
					@JoinColumn(name = "NIdCatalogo", nullable = false) })
	private Collection<CatalogoRol> roles;

	@NotNull
	@Size(min = 5, max = 64)
	@Column(name = "VUser", nullable = false, length = 64, unique = true)
	private String user;

	public Usuario() {
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Persona getPersona() {
		return persona;
	}
	
	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Collection<CatalogoRol> getRoles() {
		return roles;
	}

	public void setRoles(Collection<CatalogoRol> roles) {
		this.roles = roles;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void addRol(CatalogoRol rol) {
		if (this.roles == null) {
			this.roles = new HashSet<CatalogoRol>();
		}
		this.roles.add(rol);
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
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
