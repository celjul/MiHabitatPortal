package com.bstmexico.mihabitat.otrosingresos.model;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.proveedores.gastos.model.Gasto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @created 2015
 */
@Entity
@DiscriminatorValue("otroingreso")
public class MovimientoOtroIngreso extends Movimiento {

	private static final long serialVersionUID = 1242934324256456469L;

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
	@JoinColumn(name="NIdOtroIngreso", referencedColumnName = "NIdOtroIngreso")
	@OneToOne(fetch = FetchType.LAZY)
	private OtroIngreso otroIngreso;

	@NotNull
	@JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoTipoMovimiento tipo;
	
	public MovimientoOtroIngreso() {
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

	public CatalogoTipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoTipoMovimiento tipo) {
		this.tipo = tipo;
	}

	public OtroIngreso getOtroIngreso() {
		return otroIngreso;
	}

	public void setOtroIngreso(OtroIngreso otroIngreso) {
		this.otroIngreso = otroIngreso;
	}
}
