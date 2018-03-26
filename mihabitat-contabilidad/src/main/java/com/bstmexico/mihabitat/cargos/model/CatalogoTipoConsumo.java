package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Lopez Cruz Pamela Montserrat
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("tipo_consumo")
public class CatalogoTipoConsumo extends Catalogo {

	private static final long serialVersionUID = -1122129570017470799L;

	public CatalogoTipoConsumo() {
		super();
	}
}
