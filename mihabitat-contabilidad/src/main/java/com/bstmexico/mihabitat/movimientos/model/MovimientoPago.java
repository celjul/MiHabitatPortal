package com.bstmexico.mihabitat.movimientos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.pagos.model.Pago;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("pago")
public class MovimientoPago extends Movimiento {

	private static final long serialVersionUID = 7058231545157489950L;

	@NotNull
	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta cuenta;

	@NotNull
	@JoinColumn(name = "NIdPagoMov", referencedColumnName = "NIdPago", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Pago pago;

	public MovimientoPago() {
		super();
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}
}
