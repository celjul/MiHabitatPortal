package com.bstmexico.mihabitat.instalaciones.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0 
 * @since 2015
 */

@Entity
@DiscriminatorValue("periodo_recurrente")
public class CatalogoPeriodoRecurrente extends Catalogo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1251002418497915834L;
	
	public CatalogoPeriodoRecurrente() {
		super();
	}

}
