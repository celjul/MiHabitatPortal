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
@DiscriminatorValue("estatus_pago")
public class CatalogoEstatusPago extends Catalogo {
	
	private static final long serialVersionUID = 1932710071870839967L;
	
	public CatalogoEstatusPago() {
		super();
	}
}
