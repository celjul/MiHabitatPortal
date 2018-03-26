package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class Saldo {

	private Long id;

	private String departamento;

	private String torresEtiquetas;

	private BigDecimal monto;

	private List<SaldoDetalle> generados;

	private List<SaldoDetalle> gastados;

	public Saldo() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public List<SaldoDetalle> getGenerados() {
		return generados;
	}

	public void setGenerados(List<SaldoDetalle> generados) {
		this.generados = generados;
	}

	public List<SaldoDetalle> getGastados() {
		return gastados;
	}

	public void setGastados(List<SaldoDetalle> gastados) {
		this.gastados = gastados;
	}

	public String getTorresEtiquetas() {
		return torresEtiquetas;
	}

	public void setTorresEtiquetas(String torresEtiquetas) {
		this.torresEtiquetas = torresEtiquetas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Saldo other = (Saldo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addGenerado(SaldoDetalle detalle) {
		if (this.generados == null) {
			this.generados = new ArrayList<SaldoDetalle>();
		}
		this.generados.add(detalle);
	}

	public void addGastado(SaldoDetalle detalle) {
		if (this.gastados == null) {
			this.gastados = new ArrayList<SaldoDetalle>();
		}
		this.gastados.add(detalle);
	}
}
