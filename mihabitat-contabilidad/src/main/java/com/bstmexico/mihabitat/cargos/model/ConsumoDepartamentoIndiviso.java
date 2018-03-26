package com.bstmexico.mihabitat.cargos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Lopez Cruz Pamela Montserrat
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("departamento_indiviso")
public class ConsumoDepartamentoIndiviso extends ConsumoDepartamento {

	private static final long serialVersionUID = -4549804411745693243L;

	@Column(name = "DConsumoFactor", nullable = true, precision = 9, scale = 2)
	private Double consumoFactor;

	public ConsumoDepartamentoIndiviso() {
		super();
	}

	public Double getConsumoFactor() {
		return consumoFactor;
	}

	public void setConsumoFactor(Double consumoFactor) {
		this.consumoFactor = consumoFactor;
	}
}
