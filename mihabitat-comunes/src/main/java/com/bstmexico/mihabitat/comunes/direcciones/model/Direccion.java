package com.bstmexico.mihabitat.comunes.direcciones.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Direccion implements Serializable {

	private static final long serialVersionUID = 304248091581735953L;

	@NotNull
	@Size(min = 1, max = 128)
	@Column(length = 128, name = "VCalle", nullable = false)
	protected String calle;

	@NotNull
	@JoinColumn(name = "NIdColonia", nullable = false, referencedColumnName = "NIdColonia")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	protected Colonia colonia;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdDireccion", nullable = false, unique = true)
	protected Long id;

	@NotNull
	@Size(min = 1, max = 32)
	@Column(length = 32, name = "VNoExterior", nullable = false)
	protected String noExterior;

	@Size(max = 32)
	@Column(length = 32, name = "VNoInterior", nullable = true)
	protected String noInterior;

	@Size(max = 512)
	@Column(length = 512, name = "VReferencias", nullable = true)
	protected String referencias;

	public Direccion() {
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Colonia getColonia() {
		return colonia;
	}

	public void setColonia(Colonia colonia) {
		this.colonia = colonia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoExterior() {
		return noExterior;
	}

	public void setNoExterior(String noExterior) {
		this.noExterior = noExterior;
	}

	public String getNoInterior() {
		return noInterior;
	}

	public void setNoInterior(String noInterior) {
		this.noInterior = noInterior;
	}

	public String getReferencias() {
		return referencias;
	}

	public void setReferencias(String referencias) {
		this.referencias = referencias;
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
		Direccion other = (Direccion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
