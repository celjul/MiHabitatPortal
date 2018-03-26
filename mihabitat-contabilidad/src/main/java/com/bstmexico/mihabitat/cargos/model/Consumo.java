package com.bstmexico.mihabitat.cargos.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Lopez Cruz Pamela Montserrat
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tconsumos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "VTipo")
public abstract class Consumo implements Serializable {

	private static final long serialVersionUID = 6144063580089295745L;

	@Min(value = 0)
	@NotNull
	@Column(name = "DCostoUnidad", nullable = false, precision = 9, scale = 2)
	private BigDecimal costoUnidad;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdConsumo", nullable = false, unique = true)
	private Long id;

	@NotNull
	@JoinColumn(name = "NIdTipo", nullable = false, referencedColumnName = "NIdTipoConsumo")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private TipoConsumo tipo;

	@JoinColumn(name = "NIdCatalogoTipo", nullable = false, referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private CatalogoTipoConsumo catalogoTipo;

	public Consumo() {
		super();
	}

	public BigDecimal getCostoUnidad() {
		return costoUnidad;
	}

	public void setCostoUnidad(BigDecimal costoUnidad) {
		this.costoUnidad = costoUnidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoConsumo getTipo() {
		return tipo;
	}

	public void setTipo(TipoConsumo tipo) {
		this.tipo = tipo;
	}

	public CatalogoTipoConsumo getCatalogoTipo() {
		return catalogoTipo;
	}

	public void setCatalogoTipo(CatalogoTipoConsumo catalogoTipo) {
		this.catalogoTipo = catalogoTipo;
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
		Consumo other = (Consumo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}