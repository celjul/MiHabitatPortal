package com.bstmexico.mihabitat.movimientos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("movimiento")
public class CatalogoTipoMovimiento extends Catalogo {

	private static final long serialVersionUID = -8826537374400867644L;
}
