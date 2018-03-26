package com.bstmexico.mihabitat.movimientos.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tmovimientos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "VTipo")
public abstract class Movimiento implements Serializable,
		Comparable<Movimiento> {

	private static final long serialVersionUID = 8802030805081468783L;

	@Min(value = 0)
	@Column(name = "DDebe", precision = 9, scale = 2)
	protected BigDecimal debe;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFecha", nullable = false)
	protected Date fecha;

	@Min(value = 0)
	@Column(name = "DHaber", precision = 9, scale = 2)
	protected BigDecimal haber;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdMovimiento", nullable = false, unique = true)
	protected Long id;

	public Movimiento() {
		super();
	}

	public BigDecimal getDebe() {
		return debe;
	}

	public void setDebe(BigDecimal debe) {
		this.debe = debe;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getHaber() {
		return haber;
	}

	public void setHaber(BigDecimal haber) {
		this.haber = haber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		Movimiento other = (Movimiento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Movimiento o) {
		return this.id.compareTo(o.getId());
	}
}
