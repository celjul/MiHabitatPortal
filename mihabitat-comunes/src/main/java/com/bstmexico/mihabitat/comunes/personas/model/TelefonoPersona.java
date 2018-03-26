package com.bstmexico.mihabitat.comunes.personas.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "ttelefonospersona")
@JsonIgnoreProperties({"persona"})
public class TelefonoPersona extends Telefono {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2817813742847795830L;
	
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "NIdPersona", referencedColumnName = "NIdPersona", nullable = false)
	private Persona persona;
	
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public Persona getPersona() {
		return persona;
	}
}
