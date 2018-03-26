package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tpagosproveedor")
public class PagoProveedor implements Serializable {

	private static final long serialVersionUID = -8296222534722244348L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdPagoProveedor", nullable = false, unique = true)
	private Long id;

	@NotNull
	@JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Condominio condominio;

	@NotNull
	@JoinColumn(name = "NIdProveedor", referencedColumnName = "NIdProveedor", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Proveedor proveedor;

	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta cuenta;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFecha", nullable = false)
	private Date fechaPago;

	@NotNull
	@JoinColumn(name = "NIdMetodoPago", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoMetodoPago metodoPago;

	@NotNull
	@Min(value = 0)
	@Column(name = "DTotal", precision = 9, scale = 2, nullable = false)
	private BigDecimal total;

	@JoinColumn(name = "NIdPagoProveedor", nullable = true, referencedColumnName = "NIdPagoProveedor")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<MovimientoCfdiAplicado> movimientos;

	@Size(max = 32)
	@Column(name = "VReferencia", length = 32)
	private String referencia;

	@Size(max = 32)
	@Column(name = "VComentario", length = 32)
	private String comentario;

	public PagoProveedor() {
		super();
	}


	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Date getFechaPago() { return fechaPago; }

	public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CatalogoMetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(CatalogoMetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public BigDecimal getTotal() { return total; }

	public void setTotal(BigDecimal total) { this.total = total; }

	public Collection<MovimientoCfdiAplicado> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Collection<MovimientoCfdiAplicado> movimientos) {
		this.movimientos = movimientos;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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
		PagoProveedor other = (PagoProveedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addMovimiento(MovimientoCfdiAplicado movimiento) {
		if (this.movimientos == null) {
			this.movimientos = new ArrayList<MovimientoCfdiAplicado>();
		}
		this.movimientos.add(movimiento);
	}

}
