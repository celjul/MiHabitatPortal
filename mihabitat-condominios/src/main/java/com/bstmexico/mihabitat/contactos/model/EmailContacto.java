package com.bstmexico.mihabitat.contactos.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "temailscontacto")
@JsonIgnoreProperties({"contacto"})
public class EmailContacto extends Email {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1650156464297959029L;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NIdContacto")
	private Contacto contacto;
	
	public Contacto getContacto() {
		return contacto;
	}
	
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
}
