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
@DiscriminatorValue("reservacion_cada")
public class CatalogoReservacionesCada extends Catalogo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6924747318617680421L;
	
	public CatalogoReservacionesCada() {
		super();
	}

}
