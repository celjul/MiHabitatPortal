package com.bstmexico.mihabitat.movimientos.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("cargo_aplicado")
public class MovimientoCargoAplicado extends Movimiento {

	private static final long serialVersionUID = -7262696236394247151L;

	@NotNull
	@Column(name = "BAplicado")
	private Boolean aplicado;
	
	@NotNull
	@Column(name = "BCancelado")
	private Boolean cancelado;

	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta cuenta;

	@NotNull
	@Column(name = "BImprimible")
	private Boolean imprimible;

//	@NotNull
	@JoinColumn(name = "NIdMovimientoCargo", referencedColumnName = "NIdMovimiento", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private MovimientoCargo movimientoCargo;

	@JsonIgnoreProperties(value = { "pagosDepartamento" })
	@JoinColumn(name = "NIdPago", referencedColumnName = "NIdPago", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Pago pago;

	@JsonIgnoreProperties(value = { "movimientos" })
	@JoinColumn(name = "NIdPagoDepartamento", referencedColumnName = "NIdPagoDepartamento", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private PagoDepartamento pagoDepartamento;

	@NotNull
	@JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoTipoMovimiento tipo;

	public MovimientoCargoAplicado() {
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

	public Boolean getImprimible() {
		return imprimible;
	}

	public void setImprimible(Boolean imprimible) {
		this.imprimible = imprimible;
	}

	public MovimientoCargo getMovimientoCargo() {
		return movimientoCargo;
	}

	public void setMovimientoCargo(MovimientoCargo movimientoCargo) {
		this.movimientoCargo = movimientoCargo;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public CatalogoTipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoTipoMovimiento tipo) {
		this.tipo = tipo;
	}

	public PagoDepartamento getPagoDepartamento() {
		return pagoDepartamento;
	}

	public void setPagoDepartamento(PagoDepartamento pagoDepartamento) {
		this.pagoDepartamento = pagoDepartamento;
	}
}
