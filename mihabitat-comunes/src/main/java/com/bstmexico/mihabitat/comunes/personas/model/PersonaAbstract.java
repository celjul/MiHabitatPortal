package com.bstmexico.mihabitat.comunes.personas.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Pablo Cruz Santos
 * @author Luis Ángel Cárdenas Ortiz
 * @version 1.0
 * @since 2015
 */
@MappedSuperclass
public abstract class PersonaAbstract implements Serializable {

	private static final long serialVersionUID = 5658050544405238541L;

	@Size(max = 64)
	@Column(name = "VApellidoMaterno", nullable = true, length = 64)
	protected String apellidoMaterno;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "VApellidoPaterno", nullable = false, length = 64)
	protected String apellidoPaterno;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "NIdPersona", nullable = false, unique = true)
	protected Long id;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(name = "VNombre", nullable = false, length = 64)
	protected String nombre;

	public String getApellidoMaterno() {
		return apellidoMaterno == null ? "" : apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreCompleto(){
		if(this.getNombre() != null) {
			return this.getNombre() + " " + this.getApellidoPaterno() +
					((this.getApellidoMaterno()!= null && !this.getApellidoMaterno().isEmpty())?(" " + this.getApellidoMaterno()):"");
		}
		return "";
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
		PersonaAbstract other = (PersonaAbstract) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("[");
		buffer.append("id :").append(this.id).append(", ");
		buffer.append("nombre :").append(this.nombre).append(", ");
		buffer.append("apellidoPaterno :").append(this.apellidoPaterno)
				.append(", ");
		buffer.append("]");
		
		return buffer.toString();
	}
}
