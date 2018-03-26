/**
 * 
 */
package com.bstmexico.mihabitat.departamentos.model;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0 
 * @since 2015
 */

@Entity
@DiscriminatorValue("tipo_notificacion")
public class CatalogoTipoNotificacion extends Catalogo{

	/**
	 *
	 */
	private static final long serialVersionUID = -6114409762565432187L;

	public CatalogoTipoNotificacion() {
		super();
	}
	
}
