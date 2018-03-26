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
public abstract class Telefono implements Serializable {

	private static final long serialVersionUID = 5145365578871821150L;

	@Size(max = 5)
	@Column(length = 5, name = "VExtension", nullable = true)
	private String extension;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdTelefono", nullable = false, unique = true)
	private Long id;

	@Size(max = 5)
	@Column(length = 5, name = "VLada", nullable = true)
	private String lada;

	@NotNull
	@Size(min = 1, max = 16)
	@Column(length = 16, name = "VNumero", nullable = true)
	private String numero;

	@NotNull
	@JoinColumn(name = "NIdTipo", nullable = false, referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private CatalogoTelefono tipo;

	public Telefono() {
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLada() {
		return lada;
	}

	public void setLada(String lada) {
		this.lada = lada;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public CatalogoTelefono getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoTelefono tipo) {
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
		Telefono other = (Telefono) obj;
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
		buffer.append("id : ").append(this.id).append(", ");
		buffer.append("lada : ").append(this.lada).append(", ");
		buffer.append("numero : ").append(this.numero);
		buffer.append("]");
		
		return buffer.toString();
	}
}
