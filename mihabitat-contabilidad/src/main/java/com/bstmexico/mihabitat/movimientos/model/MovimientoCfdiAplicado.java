package com.bstmexico.mihabitat.movimientos.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.proveedores.model.PagoProveedor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("cargo_cfdi_aplicado")
public class MovimientoCfdiAplicado extends Movimiento {

	private static final long serialVersionUID = -7262111236394222151L;

	@NotNull
	@Column(name = "BCancelado")
	private Boolean cancelado;

	@NotNull
	@Column(name = "BAplicado")
	private Boolean aplicado;

	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta cuenta;

	@JsonIgnore
	@NotNull
	@JoinColumn(name = "NIdMovimientoCfdi", referencedColumnName = "NIdMovimiento", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Movimiento movimientoCfdi;

	@JoinColumn(name = "NIdPagoProveedor", referencedColumnName = "NIdPagoProveedor", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private PagoProveedor pagoProveedor;

	@NotNull
	@JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoTipoMovimiento tipo;

	public MovimientoCfdiAplicado() {
		super();

	}

	public Boolean getAplicado() {
		return aplicado;
	}

	public void setAplicado(Boolean aplicado) {
		this.aplicado = aplicado;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	@JsonIgnoreProperties(value = { "aplicados" })
	public Movimiento getMovimiento() {
		return movimientoCfdi;
	}

	public void setMovimiento(Movimiento movimientoCfdi) {
		this.movimientoCfdi = movimientoCfdi;
	}

	public PagoProveedor getPagoProveedor() {
		return pagoProveedor;
	}

	public void setPagoProveedor(PagoProveedor pagoProveedor) {
		this.pagoProveedor = pagoProveedor;
	}

	public CatalogoTipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoTipoMovimiento tipo) {
		this.tipo = tipo;
	}

	public Boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}
}
