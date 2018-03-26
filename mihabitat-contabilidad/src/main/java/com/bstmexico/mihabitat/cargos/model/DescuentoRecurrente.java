package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tdescuentocargorecurrente")
@PrimaryKeyJoinColumn(name = "NIdDescuento")
public class DescuentoRecurrente extends Descuento {

	private static final long serialVersionUID = 2110039388252905441L;

	@Min(value = 1)
	@NotNull
	@Column(name = "NDia", nullable = false)
	private Byte dia;

	public DescuentoRecurrente() {
	}

	public Byte getDia() {
		return dia;
	}

	public void setDia(Byte dia) {
		this.dia = dia;
	}
}
