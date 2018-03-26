package com.bstmexico.mihabitat.comunes.personas.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "temailspersona")
@JsonIgnoreProperties({"persona"})
public class EmailPersona extends Email {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5444700185012501709L;

	@NotNull
	@ManyToOne(optional = false, targetEntity = Persona.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "NidPersona", nullable = false, referencedColumnName = "NIdPersona")
	private Persona persona;
	
	public Persona getPersona() {
		return persona;
	}
	
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
}
