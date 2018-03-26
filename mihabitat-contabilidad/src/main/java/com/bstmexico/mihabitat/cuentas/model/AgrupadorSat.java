package com.bstmexico.mihabitat.cuentas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "tagrupadoressat")
public class AgrupadorSat implements Serializable{

	private static final long serialVersionUID = -6080290809511578499L;
	
	@NotNull
	@Size(min = 1, max = 32)
	@Column(length=32, name="VCodigo", nullable = false)
	private String codigo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdAgrupadorSat", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 128)
	@Column(length= 128, name ="VNombre", nullable = false )
	private String nombre;
	
	@Max(value = 2)
	@Min(value = 1)
	@Column(name = "NNivel", nullable = false, unique = false)
	private Byte nivel;
	
	public AgrupadorSat(){
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		AgrupadorSat other = (AgrupadorSat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
