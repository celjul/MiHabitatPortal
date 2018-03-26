package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdi;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tdetallefactura")
public class DetalleFactura implements Serializable {

	private static final long serialVersionUID = -9142343422228111L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdConcepto")
	private Long id;

	@NotNull
	@Column(name = "NCantidad")
	private Double cantidad;

	@NotNull
	@Size(max = 25)
	@Column(name = "VCodigo", length = 25)
	private String codigo;

	@NotNull
	@Size(max = 2048)
	@Column(name = "VDescripcion", length = 2048)
	private String descripcion;

	@NotNull
	@Column(name = "DPrecioUnitario")
	private BigDecimal precioUnitario;

	@NotNull
	@Column(name = "DImporte")
	private BigDecimal importe;

	@NotNull
	@Size(max = 20)
	@Column(name = "VUnidadMedida", length = 20)
	private String unidadDeMedida;

	@NotNull
	@JoinColumn(name = "NIdCuenta", nullable = false, referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Cuenta cuenta;

	@JsonIgnoreProperties(value = { "detalleCfdi" })
	@NotNull
	@OneToOne(mappedBy = "detalleCfdi", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private MovimientoCfdi movimientoCfdi;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getUnidadDeMedida() {
		return unidadDeMedida;
	}

	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public MovimientoCfdi getMovimientoCfdi() {
		return movimientoCfdi;
	}

	public void setMovimientoCfdi(MovimientoCfdi movimientoCfdi) {
		this.movimientoCfdi = movimientoCfdi;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		DetalleFactura that = (DetalleFactura) o;

		return !(id != null ? !id.equals(that.id) : that.id != null);

	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}