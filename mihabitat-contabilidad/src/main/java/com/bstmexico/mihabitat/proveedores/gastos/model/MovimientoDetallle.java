package com.bstmexico.mihabitat.proveedores.gastos.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
@Entity
@DiscriminatorValue("detalle_gasto")
public class MovimientoDetallle extends Movimiento {

	private static final long serialVersionUID = -6396039370624184491L;
	
	@NotNull
	@Column(name = "BAplicado")
	private Boolean aplicado;
	
	@NotNull
	@Column(name = "BCancelado")
	private Boolean cancelado;

	@NotNull
	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta cuenta;

	@JsonIgnore
	@JoinColumn(name="NIdDetalle", referencedColumnName = "NIdDetalle")
	@OneToOne(fetch = FetchType.LAZY)
	private Detalle detalle;

	@NotNull
	@JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoTipoMovimiento tipo;
	
	public MovimientoDetallle() {
		super();
	}

	public Boolean getAplicado() {
		return aplicado;
	}

	public void setAplicado(Boolean aplicado) {
		this.aplicado = aplicado;
	}

	public Boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Detalle getDetalle() {
		return detalle;
	}

	public void setDetalle(Detalle detalle) {
		this.detalle = detalle;
	}

	public CatalogoTipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoTipoMovimiento tipo) {
		this.tipo = tipo;
	}
}
