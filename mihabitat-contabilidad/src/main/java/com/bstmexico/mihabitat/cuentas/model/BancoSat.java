package com.bstmexico.mihabitat.cuentas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "tbancossat")
public class BancoSat implements Serializable{

	private static final long serialVersionUID = -5452446310208290231L;
	
	@NotNull
	@Size(min = 1, max = 32)
	@Column(length= 32 ,name = "VCodigo", nullable= false)
	private String codigo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdBancoSat", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 128)
	@Column(length= 128, name = "VNombre", nullable = false )
	private String nombre;
	
	@NotNull
	@Size(min = 1, max = 512)
	@Column(length= 512 ,name = "VRazon" , nullable= false)
	private String razon;
	
	public BancoSat(){
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazon() {
		return razon;
	}

	public void setRazon(String razon) {
		this.razon = razon;
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
		BancoSat other = (BancoSat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
