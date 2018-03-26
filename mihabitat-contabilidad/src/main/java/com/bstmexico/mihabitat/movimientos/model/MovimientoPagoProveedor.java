package com.bstmexico.mihabitat.movimientos.model;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.proveedores.model.PagoProveedor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("pago_proveedor")
public class MovimientoPagoProveedor extends Movimiento {

	private static final long serialVersionUID = 7048231111157422951L;

	@NotNull
	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta cuenta;

	@NotNull
	@JoinColumn(name = "NIdPagoProveedorMov", referencedColumnName = "NIdPagoProveedor", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private PagoProveedor pagoProveedor;

	public MovimientoPagoProveedor() {
		super();
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public PagoProveedor getPagoProveedor() {
		return pagoProveedor;
	}

	public void setPagoProveedor(PagoProveedor pagoProveedor) {
		this.pagoProveedor = pagoProveedor;
	}
}
