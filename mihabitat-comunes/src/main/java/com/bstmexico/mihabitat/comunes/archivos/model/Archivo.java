package com.bstmexico.mihabitat.comunes.archivos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tarchivos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "VUso")
public abstract class Archivo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9132506738698528592L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdArchivo", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 256)
	@Column(length = 256, name = "VNombre", nullable = false)
	private String nombre;
	
	@NotNull
	@Size(min = 1, max = 256)
	@Column(length = 256, name = "VTamano", nullable = false)
	private String tamano;
	
	@NotNull
	@Size(min = 1, max = 256)
	@Column(length = 256, name = "VTipo", nullable = false)
	private String tipo;
	
	@Column(columnDefinition = "longblob", name = "BBytes")
	private byte[] bytes;
	
	public Archivo() {
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



	public String getTamano() {
		return tamano;
	}



	public void setTamano(String tamano) {
		this.tamano = tamano;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public byte[] getBytes() {
		return bytes;
	}



	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
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
		Archivo other = (Archivo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

