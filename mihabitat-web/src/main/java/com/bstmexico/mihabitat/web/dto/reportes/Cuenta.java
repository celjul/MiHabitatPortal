package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class Cuenta {

	private BigDecimal debe;

	private BigDecimal haber;

	private Collection<Cuenta> hijas;

	private Long id;

	private BigDecimal inicial;

	private String nombre;

	private BigDecimal saldo;

	private Boolean ficticia;

	/**
	 * Propiedad reporte cajas y bancos
	 */
	private Collection<Movimiento> movimientos;

	/**
	 * Propiedad para balanza
	 */
	private String tipo;

	private String numero;

	public Cuenta() {
		super();
		this.ficticia = false;
	}

	public Cuenta(com.bstmexico.mihabitat.cuentas.model.Cuenta cuenta) {
		super();
		this.hijas = new ArrayList<Cuenta>();
		if (StringUtils.isEmpty(cuenta.getNumeroBis())) {
			if (!CollectionUtils.isEmpty(cuenta.getCuentasHijas())) {
				for (com.bstmexico.mihabitat.cuentas.model.Cuenta c : cuenta
						.getCuentasHijas()) {
					this.hijas.add(new Cuenta(c));
				}
			}
		}
		this.id = cuenta.getId();
		this.inicial = cuenta.getInicial();
		this.nombre = cuenta.getNombre();
		this.ficticia = false;
	}

	public BigDecimal getDebe() {
		return debe;
	}

	public void setDebe(BigDecimal debe) {
		this.debe = debe;
	}

	public BigDecimal getHaber() {
		return haber;
	}

	public void setHaber(BigDecimal haber) {
		this.haber = haber;
	}

	public Collection<Cuenta> getHijas() {
		return hijas;
	}

	public void setHijas(Collection<Cuenta> hijas) {
		this.hijas = hijas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getInicial() {
		return inicial;
	}

	public void setInicial(BigDecimal inicial) {
		this.inicial = inicial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getSaldo() {
		/*saldo = ((getHaber() != null ? getHaber() : BigDecimal.ZERO)
				.subtract(getDebe() != null ? getDebe() : BigDecimal.ZERO));*/
		saldo = (getInicial() != null ? getInicial() : BigDecimal.ZERO).add((getHaber() != null ? getHaber() : BigDecimal.ZERO)
				.subtract(getDebe() != null ? getDebe() : BigDecimal.ZERO));
		// TODO: ¿Considerar inicial?
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Boolean getFicticia() {
		return ficticia;
	}

	public void setFicticia(Boolean ficticia) {
		this.ficticia = ficticia;
	}

	public Collection<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Collection<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void addMovimiento(Movimiento movimiento) {
		if (this.movimientos == null) {
			this.movimientos = new ArrayList<Movimiento>();
		}
		this.movimientos.add(movimiento);
	}
}
