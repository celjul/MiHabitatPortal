package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tproveedorcontactos")

public class ContactoProveedor extends PersonaAbstract implements Serializable {

	private static final long serialVersionUID = 2891220993508476355L;


	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "contactoProveedor", targetEntity = EmailContactoProveedor.class)
	private Collection<? extends Email> emails;

	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "contactoProveedor", targetEntity = TelefonoContactoProveedor.class)
	private Collection<? extends Telefono> telefonos;
	

	public ContactoProveedor() {
	}

	public Collection<? extends Email> getEmails() {
		return emails;
	}

	public void setEmails(Collection<? extends Email> emails) {
		this.emails = emails;
	}

	public Collection<? extends Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(Collection<? extends Telefono> telefonos) {
		this.telefonos = telefonos;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ContactoProveedor other = (ContactoProveedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
	

	
	
}
