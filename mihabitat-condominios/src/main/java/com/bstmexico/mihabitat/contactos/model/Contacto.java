package com.bstmexico.mihabitat.contactos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tcontactos")
// @PrimaryKeyJoinColumn(name = "NIdPersona")
// @AttributeOverrides({
// @AttributeOverride(name="startDate", column=@Column(name = "EMP_START"))
// })
@AttributeOverride(name = "id", column = @Column(name = "NIdContacto"))
public class Contacto extends PersonaAbstract implements Serializable {

	private static final long serialVersionUID = -2736360469212877697L;

	@NotNull
	@JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Condominio condominio;

	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "contacto", targetEntity = EmailContacto.class)
	private Collection<? extends Email> emails;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "NIdUsuario", nullable = true, referencedColumnName = "NIdUsuario")
	private Usuario usuario;

	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "contacto", targetEntity = TelefonoContacto.class)
	private Collection<? extends Telefono> telefonos;

	@Valid
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "id.contacto", orphanRemoval = true)
	private Collection<ContactoDepartamento> departamentos;

	public Contacto() {
		super();
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Collection<ContactoDepartamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Collection<ContactoDepartamento> departamentos) {
		this.departamentos = departamentos;
	}

	public void addDepartamento(ContactoDepartamento departamento) {
		if (this.departamentos == null) {
			this.departamentos = new ArrayList<ContactoDepartamento>();
		}
		this.departamentos.add(departamento);
	}

	public Collection<? extends Email> getEmails() {
		return emails;
	}

	public void setEmails(Collection<? extends Email> emails) {
		this.emails = emails;
	}

	// public Long getId() {
	// return id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Collection<? extends Telefono> getTelefonos() {
		return telefonos;
	}

	public void setTelefonos(Collection<? extends Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	// @Override
	// public String toString() {
	// // TODO Auto-generated method stub
	// return super.toString();
	// }
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Contacto)) return false;

		Contacto that = (Contacto) o;

		return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);
	}
}
