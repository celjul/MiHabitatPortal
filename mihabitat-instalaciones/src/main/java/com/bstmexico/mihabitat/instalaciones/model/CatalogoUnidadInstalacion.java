/**
 * 
 */
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
@DiscriminatorValue("unidad_instalacion")
public class CatalogoUnidadInstalacion extends Catalogo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6114409762415432187L;

	public CatalogoUnidadInstalacion() {
		super();
	}
	
}
