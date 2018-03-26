package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Lopez Cruz Pamela Montserrat
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("departamento_prorrateo")
public class ConsumoDepartamentoProrrateo extends ConsumoDepartamento {

	private static final long serialVersionUID = 219473118753379267L;

	@Column(name = "DConsumoFactor", nullable = true, precision = 9, scale = 2)
	private Double consumoFactor;

	public ConsumoDepartamentoProrrateo() {
		super();
	}

	public Double getConsumoFactor() {
		return consumoFactor;
	}

	public void setConsumoFactor(Double consumoFactor) {
		this.consumoFactor = consumoFactor;
	}
}
