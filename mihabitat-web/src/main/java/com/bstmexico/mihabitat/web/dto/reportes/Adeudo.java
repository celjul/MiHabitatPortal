package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class Adeudo {

	private Collection<CargoDepartamento> cargos;

	private Long idDepartamento;

	private String departamento;

	private String torresEtiquetas;

	private String contacto;

	private BigDecimal saldo;

	private BigDecimal saldoFavor;

	private Long milisegundos;

	private String antiguedad;

	public Adeudo() {
		super();
	}

	public Collection<CargoDepartamento> getCargos() {
		return cargos;
	}

	public void setCargos(Collection<CargoDepartamento> cargos) {
		this.cargos = cargos;
	}

	public Long getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getSaldoFavor() {
		return saldoFavor;
	}

	public void setSaldoFavor(BigDecimal saldoFavor) {
		this.saldoFavor = saldoFavor;
	}

	public Long getMilisegundos() {
		List<CargoDepartamento> cargos = (List<CargoDepartamento>) getCargos();
		if (!CollectionUtils.isEmpty(cargos)) {
			Collections.sort(cargos);
			milisegundos = cargos.get(0).getFecha().getTime();
		}
		/*NumberFormat nf = new DecimalFormat("");
		nf.format(saldo);*/
		return milisegundos;
	}

	public void setMilisegundos(Long milisegundos) {
		this.milisegundos = milisegundos;
	}

	public String getAntiguedad() {
		return antiguedad;
	}

	public String getTorresEtiquetas() {
		return torresEtiquetas;
	}

	public void setTorresEtiquetas(String torresEtiquetas) {
		this.torresEtiquetas = torresEtiquetas;
	}

	public void setAntiguedad(String antiguedad) {
		this.antiguedad = antiguedad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDepartamento == null) ? 0 : idDepartamento.hashCode());
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
		Adeudo other = (Adeudo) obj;
		if (idDepartamento == null) {
			if (other.idDepartamento != null)
				return false;
		} else if (!idDepartamento.equals(other.idDepartamento))
			return false;
		return true;
	}

	public void addCargo(CargoDepartamento cargo) {
		if (this.cargos == null) {
			this.cargos = new ArrayList<CargoDepartamento>();
		}
		this.cargos.add(cargo);
	}
}
