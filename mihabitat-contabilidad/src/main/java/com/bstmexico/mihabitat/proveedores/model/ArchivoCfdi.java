package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("cfdi_proveedor")
public class ArchivoCfdi extends Archivo {


	private static final long serialVersionUID = 478231341431278289L;

	public ArchivoCfdi() {
		super();
	}
	
}

