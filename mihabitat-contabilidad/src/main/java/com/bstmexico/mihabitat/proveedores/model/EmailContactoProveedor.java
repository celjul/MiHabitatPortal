package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "temailscontactoproveedor")
@JsonIgnoreProperties({"contactoProveedor"})
public class EmailContactoProveedor extends Email {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1650156464297959029L;
	
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
