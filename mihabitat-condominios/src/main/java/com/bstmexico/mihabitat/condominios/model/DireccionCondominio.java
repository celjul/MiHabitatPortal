package com.bstmexico.mihabitat.condominios.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.bstmexico.mihabitat.comunes.direcciones.model.Direccion;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
@Entity
@Table(name = "tdireccioncondominio")
public class DireccionCondominio extends Direccion {
	
	private static final long serialVersionUID = -1381565811229415923L;
	
	public DireccionCondominio() {
	}
}
