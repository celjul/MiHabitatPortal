package com.bstmexico.mihabitat.proveedores.gastos.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.proveedores.model.Proveedor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
@Entity
@Table(name = "tgastosproveedor")
public class Gasto implements Serializable {

	private static final long serialVersionUID = -7884953498838017162L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdGasto", nullable = false, unique = true)
	private Long id;

	@NotNull
	@JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Condominio condominio;

	@NotEmpty
	@JoinColumn(name = "NIdGasto", nullable = true, referencedColumnName = "NIdGasto")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Collection<Detalle> detalles;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFecha", nullable = false)
	private Date fecha;

	@NotNull
	@JoinColumn(name = "NIdMetodoPago", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoMetodoPago metodoPago;

	@NotNull
	@OneToOne(mappedBy = "gasto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private MovimientoGasto movimientoGasto;

	@NotNull
	@JoinColumn(name = "NIdProveedor", referencedColumnName = "NIdProveedor", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Proveedor proveedor;

	public Gasto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Collection<Detalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(Collection<Detalle> detalles) {
		this.detalles = detalles;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public CatalogoMetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(CatalogoMetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public MovimientoGasto getMovimientoGasto() {
		return movimientoGasto;
	}

	public void setMovimientoGasto(MovimientoGasto movimientoGasto) {
		this.movimientoGasto = movimientoGasto;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
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
		Gasto other = (Gasto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@JsonIgnore
	public BigDecimal getDebe() {
		BigDecimal debe = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getDetalles())) {
			for (Detalle detalle : detalles) {
				if (detalle.getMovimientoDetallle().getDebe() != null) {
					debe = debe.add(detalle.getMovimientoDetallle().getDebe());
				}
			}
		}
		return debe;
	}
}
