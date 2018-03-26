package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ttelefonoscontactoproveedor")
@JsonIgnoreProperties({"contactoProveedor"})
public class TelefonoContactoProveedor extends Telefono {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4733246985038214666L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NIdContacto", nullable = false, referencedColumnName = "NIdPersona")
	private ContactoProveedor contactoProveedor;

	public ContactoProveedor getContactoProveedor() {
		return contactoProveedor;
	}

	public void setContactoProveedor(ContactoProveedor contactoProveedor) {
		this.contactoProveedor = contactoProveedor;
	}
}
