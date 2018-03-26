package com.bstmexico.mihabitat.instalaciones.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0 
 * @since 2015
 */

@Entity
@DiscriminatorValue("imagen_instalacion")
public class ArchivoImagenInstalacion extends Archivo {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5369967549877955640L;

	public ArchivoImagenInstalacion() {
		super();
	}
	
}

