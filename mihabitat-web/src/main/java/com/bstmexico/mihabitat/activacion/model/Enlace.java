package com.bstmexico.mihabitat.activacion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.bstmexico.mihabitat.contactos.model.Contacto;

@Entity
@Table(name = "tenlaces")
public class Enlace {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdEnlace", nullable = false, unique = true)
	private Long id;

	@NotNull
	@Column(name = "DVigencia", nullable = false)
	private Date vigencia;

	@NotNull
	@OneToOne(optional = false)
	@JoinColumn(name = "NIdContacto", referencedColumnName = "NIdContacto")
	private Contacto contacto;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(name = "VEmail", nullable = false, length = 255)
	private String email;

	public Enlace() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getVigencia() {
		return vigencia;
	}

	public void setVigencia(Date vigencia) {
		this.vigencia = vigencia;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
