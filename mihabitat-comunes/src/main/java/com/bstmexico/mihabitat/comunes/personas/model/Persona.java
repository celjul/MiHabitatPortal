	package com.bstmexico.mihabitat.comunes.personas.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

@Entity
@Table(name = "tpersonas")
public class Persona extends PersonaAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7395525800007747060L;
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
		orphanRemoval = true, mappedBy = "persona", targetEntity = TelefonoPersona.class)
	private Collection<? extends Telefono> telefonos;
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, 
		orphanRemoval = true, mappedBy = "persona", 
		targetEntity = EmailPersona.class)
	private Collection<? extends Email> emails;
	
	public Collection<? extends Email> getEmails() {
		return emails;
	}

	public void setEmails(Collection<? extends Email> emails) {
		if (this.emails != null) {
			this.emails.clear();
		}
		this.emails = emails;
	}
	
	public Collection<? extends Telefono> getTelefonos() {
		return telefonos;
	}
	
	public void setTelefonos(Collection<? extends Telefono> telefonos) {
		if (this.telefonos != null) {
			this.telefonos.clear();
		}
		this.telefonos = telefonos;
	}
}
