package com.bstmexico.mihabitat.comunes.personas.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
@Entity
@DiscriminatorValue("telefono")
public class CatalogoTelefono extends Catalogo {
	
	private static final long serialVersionUID = -3869155274132807582L;
	
	public CatalogoTelefono() {
	}
}
