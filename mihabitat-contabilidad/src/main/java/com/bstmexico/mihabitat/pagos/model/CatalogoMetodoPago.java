package com.bstmexico.mihabitat.pagos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
@Entity
@DiscriminatorValue("metodo_pago")
public class CatalogoMetodoPago extends Catalogo {
	
	private static final long serialVersionUID = 7174185323947220243L;

	public CatalogoMetodoPago() {
		super();
	}
}
