package com.bstmexico.mihabitat.cargos.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
@Entity
@Table(name = "tdescuentos")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Descuento implements Serializable {
	
	private static final long serialVersionUID = -3140478943065219400L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdDescuento", nullable = false, unique = true)
	protected Long id;
	
	@Min(value = 0)
	@NotNull
	@Column(name = "NMonto", nullable = false, precision = 9, scale = 2)
	protected BigDecimal monto;
	
	@NotNull
	@Column(name = "BPorcentaje", nullable = false, unique = false)
	protected Boolean porcentaje;
	
	public Descuento() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Boolean getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Boolean porcentaje) {
		this.porcentaje = porcentaje;
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
		Descuento other = (Descuento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
