package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.comunes.direcciones.model.Direccion;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author JPC
 * @version 1.0 
 * @since 2015
 */
@Entity
@Table(name = "tdireccionproveedor")
public class DireccionProveedor extends Direccion {
	
	private static final long serialVersionUID = -2340502267684029908L;

	public DireccionProveedor() {
	}
}
