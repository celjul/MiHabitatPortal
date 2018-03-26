package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Pamela Lopez Cruz
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("interes")
public class CatalogoInteres extends Catalogo {

	private static final long serialVersionUID = 495367985631184719L;

	public CatalogoInteres() {

	}
}
