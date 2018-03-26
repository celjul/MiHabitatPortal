package com.bstmexico.mihabitat.departamentos.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Embeddable
public class ContactoDepartamentoId implements Serializable {

	private static final long serialVersionUID = -4436274484118349539L;

	@JsonIgnoreProperties(value = { "departamentos" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Contacto contacto;

	@JsonIgnoreProperties(value = { "contactos" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Departamento departamento;

	public ContactoDepartamentoId() {
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contacto == null) ? 0 : contacto.hashCode());
		result = prime * result
				+ ((departamento == null) ? 0 : departamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactoDepartamentoId other = (ContactoDepartamentoId) obj;
		if (contacto == null) {
			if (other.contacto != null)
				return false;
		} else if (!contacto.equals(other.contacto))
			return false;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		return true;
	}
}
