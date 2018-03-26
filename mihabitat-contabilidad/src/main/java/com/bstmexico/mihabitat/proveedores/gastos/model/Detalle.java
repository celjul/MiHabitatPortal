package com.bstmexico.mihabitat.proveedores.gastos.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
@Entity
@Table(name = "tgastosdetalleproveedor")
public class Detalle implements Serializable {
	
	private static final long serialVersionUID = -6044162092620301438L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdDetalle", nullable = false, unique = true)
	private Long id;
	
	@Size(max = 128)
	@Column(name = "VConcepto", length = 128)
	private String concepto;
	
	@NotNull
	@OneToOne(mappedBy = "detalle", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private MovimientoDetallle movimientoDetallle;
	
	public Detalle() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public MovimientoDetallle getMovimientoDetallle() {
		return movimientoDetallle;
	}

	public void setMovimientoDetallle(MovimientoDetallle movimientoDetallle) {
		this.movimientoDetallle = movimientoDetallle;
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
		Detalle other = (Detalle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
