package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Lopez Cruz Pamela Montserrat
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("agrupador_simple")
public class ConsumoAgrupadorSimple extends ConsumoAgrupador {

	private static final long serialVersionUID = 9068123347294596858L;

	public ConsumoAgrupadorSimple() {
		super();
	}
}
