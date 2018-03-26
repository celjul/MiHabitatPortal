package com.bstmexico.mihabitat.movimientos.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("saldo")
public class MovimientoSaldo extends Movimiento {

	private static final long serialVersionUID = -6404405349621544240L;

	@NotNull
	@JoinColumn(name = "NIdDepartamento", referencedColumnName = "NIdDepartamento")
	@ManyToOne(fetch = FetchType.LAZY)
	private Departamento departamento;

	@NotNull
	@JoinColumn(name = "NIdContacto", referencedColumnName = "NIdContacto")
	@ManyToOne(fetch = FetchType.LAZY)
	private Contacto contacto;

	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta cuenta;

	@JoinColumn(name = "NIdSaldoPago", referencedColumnName = "NIdPago", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Pago pago;

	@JoinColumn(name = "NIdSaldoPagoDepartamento", referencedColumnName = "NIdPagoDepartamento", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private PagoDepartamento pagoDepartamento;

	@NotNull
	@JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoTipoMovimiento tipo;

	public MovimientoSaldo() {
		super();
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
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

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public PagoDepartamento getPagoDepartamento() {
		return pagoDepartamento;
	}

	public void setPagoDepartamento(PagoDepartamento pagoDepartamento) {
		this.pagoDepartamento = pagoDepartamento;
	}

	public CatalogoTipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoTipoMovimiento tipo) {
		this.tipo = tipo;
	}
}
