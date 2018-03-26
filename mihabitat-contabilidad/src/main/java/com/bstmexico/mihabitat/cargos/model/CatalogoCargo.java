package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
@Entity
@DiscriminatorValue("cargo")
public class CatalogoCargo extends Catalogo {
	
	private static final long serialVersionUID = -4762885119906922503L;

	public CatalogoCargo() {
	}
}
