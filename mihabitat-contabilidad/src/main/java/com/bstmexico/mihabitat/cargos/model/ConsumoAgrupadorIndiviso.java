package com.bstmexico.mihabitat.cargos.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Lopez Cruz Pamela Montserrat
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("agrupador_indiviso")
public class ConsumoAgrupadorIndiviso extends ConsumoAgrupador {

	private static final long serialVersionUID = -6018045395617883088L;

	@Column(name = "DConsumo", nullable = true, precision = 9, scale = 2)
	private Double consumo;

	@Column(name = "DFactor", nullable = true, precision = 9, scale = 2)
	private BigDecimal factor;

	public ConsumoAgrupadorIndiviso() {
		super();
	}

	public Double getConsumo() {
		return consumo;
	}

	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}

	public BigDecimal getFactor() {
		return factor;
	}

	public void setFactor(BigDecimal factor) {
		this.factor = factor;
	}
}
