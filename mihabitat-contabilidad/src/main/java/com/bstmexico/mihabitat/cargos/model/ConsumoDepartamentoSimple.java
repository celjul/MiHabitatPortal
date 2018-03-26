package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Lopez Cruz Pamela Montserrat
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("departamento_simple")
public class ConsumoDepartamentoSimple extends ConsumoDepartamento {

	private static final long serialVersionUID = 7785122256122311588L;

	public ConsumoDepartamentoSimple() {
		super();
	}
}
