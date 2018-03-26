package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

@Entity
@DiscriminatorValue("unidad")
public class CatalogoUnidad extends Catalogo {

	private static final long serialVersionUID = 4524543450030652329L;

	public CatalogoUnidad() {
	}
}
