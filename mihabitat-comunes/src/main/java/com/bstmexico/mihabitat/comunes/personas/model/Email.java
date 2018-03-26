package com.bstmexico.mihabitat.comunes.personas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@MappedSuperclass
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class Email implements Serializable {

	private static final long serialVersionUID = 8563484928485306786L;

	@org.hibernate.validator.constraints.Email
	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VDireccion", nullable = false)
	private String direccion;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdEmail", nullable = false, unique = true)
	private Long id;

	@NotNull
	@JoinColumn(name = "NIdTipo", nullable = false, referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private CatalogoEmail tipo;
	
	public Email() {}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CatalogoEmail getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoEmail tipo) {
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
		Email other = (Email) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("[");
		buffer.append("id :").append(this.id).append(", ");
		buffer.append("id :").append(this.direccion);
		buffer.append("]");
		
		return buffer.toString();
	}
}
