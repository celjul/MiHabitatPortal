package com.bstmexico.mihabitat.comunes.usuarios.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
@Entity
@DiscriminatorValue("rol")
public class CatalogoRol extends Catalogo {
	
	private static final long serialVersionUID = -3494056695946735705L;

	public CatalogoRol(){
	}
}
