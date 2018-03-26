package com.bstmexico.mihabitat.cargos.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
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
@Table(name = "ttiposconsumos")
public class TipoConsumo implements Serializable {

	private static final long serialVersionUID = -8815425707990873054L;

	@NotNull
	@JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Condominio condominio;

	@NotNull
	@JoinColumn(name = "NIdCuenta", nullable = false, referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Cuenta cuenta;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdTipoConsumo", nullable = false, unique = true)
	private Long id;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VNombre", nullable = false)
	private String nombre;

	@Min(value = 0)
	@Column(name = "NCostoPorUnidad", nullable = true, precision = 9, scale = 2)
	private BigDecimal costoPorUnidad;

	@NotNull
	@JoinColumn(name = "NIdUnidad", nullable = false, referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private CatalogoUnidad unidad;
	
	@NotNull
	@JoinColumn(name = "NIdCatalogoTipoConsumo", nullable = false, referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private CatalogoTipoConsumo catalogoTipoConsumo;

	@Min(value = 0)
	@Column(name = "NFactorConversion", nullable = true, precision = 9, scale = 5)
	private BigDecimal factorConversion = BigDecimal.ONE;

	@JoinColumn(name = "NIdUnidadConversion", nullable = true, referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private CatalogoUnidad unidadConversion;

	@NotNull
	@Column(length = 64, name = "BAplicaConversion", nullable = false)
	private Boolean aplicaConversion;

	public TipoConsumo() {
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CatalogoUnidad getUnidad() {
		return unidad;
	}

	public void setUnidad(CatalogoUnidad unidad) {
		this.unidad = unidad;
	}

	public BigDecimal getCostoPorUnidad() {
		return costoPorUnidad;
	}

	public void setCostoPorUnidad(BigDecimal costoPorUnidad) {
		this.costoPorUnidad = costoPorUnidad;
	}

	public CatalogoTipoConsumo getCatalogoTipoConsumo() {
		return catalogoTipoConsumo;
	}

	public void setCatalogoTipoConsumo(CatalogoTipoConsumo catalogoTipoConsumo) {
		this.catalogoTipoConsumo = catalogoTipoConsumo;
	}

	public BigDecimal getFactorConversion() {
		return factorConversion;
	}

	public void setFactorConversion(BigDecimal factorConversion) {
		this.factorConversion = factorConversion;
	}

	public CatalogoUnidad getUnidadConversion() {
		return unidadConversion;
	}

	public void setUnidadConversion(CatalogoUnidad unidadConversion) {
		this.unidadConversion = unidadConversion;
	}

	public Boolean getAplicaConversion() {
		return aplicaConversion;
	}

	public void setAplicaConversion(Boolean aplicaConversion) {
		this.aplicaConversion = aplicaConversion;
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
		TipoConsumo other = (TipoConsumo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
