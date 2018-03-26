package com.bstmexico.mihabitat.cargos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tcargos")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cargo implements Serializable {

	private static final long serialVersionUID = 2651793811429462827L;

	@NotNull
	@Column(name = "BActivo", nullable = false, unique = false)
	protected Boolean activo;

	@Size(min = 1, max = 128)
	@Column(length = 128, name = "VConcepto", nullable = true, unique = false)
	protected String concepto;

	@NotNull
	@JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	protected Condominio condominio;

	@NotNull
	@JoinColumn(name = "NIdCuenta", nullable = false, referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	protected Cuenta cuenta;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdCargo", nullable = false, unique = true)
	protected Long id;

	@NotNull
	@JoinColumn(name = "NIdTipo", nullable = false, referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	protected CatalogoCargo tipo;

	public Cargo() {
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CatalogoCargo getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoCargo tipo) {
		this.tipo = tipo;
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
		Cargo other = (Cargo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
