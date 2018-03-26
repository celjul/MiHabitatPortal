package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

import java.math.BigDecimal;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class Pago {

	private BigDecimal monto;

	private String tarjeta;

	private String nombre;

	private String cvv;

	private String expiracion;

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getExpiracion() {
		return expiracion;
	}

	public void setExpiracion(String expiracion) {
		this.expiracion = expiracion;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}
}
