package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("giro")
public class CatalogoGiro extends Catalogo{
	
	private static final long serialVersionUID = 2235747866174950455L;
	
	public CatalogoGiro () {
		
	}
}
