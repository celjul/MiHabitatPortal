package com.bstmexico.mihabitat.cobranza.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class Cobranza implements Comparable<Cobranza> {

	private BigDecimal adeudo;

	private Collection<CargoDepartamento> cargos;

	private Departamento departamento;

	private Long milisegundos;

	private String tiempo;

	public Cobranza() {
		super();
	}

	public BigDecimal getAdeudo() {
		adeudo = BigDecimal.ZERO;
		Collection<CargoDepartamento> cargos = getCargos();
		if (!CollectionUtils.isEmpty(cargos)) {
			for (CargoDepartamento cargo : cargos) {
				adeudo = adeudo.add(cargo.getSaldoPendiente());
			}
		}
		return adeudo;
	}

	public void setAdeudo(BigDecimal adeudo) {
		this.adeudo = adeudo;
	}

	public Collection<CargoDepartamento> getCargos() {
		return cargos;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Long getMilisegundos() {
		List<CargoDepartamento> cargos = (List<CargoDepartamento>) getCargos();
		if (!CollectionUtils.isEmpty(cargos)) {
			Collections.sort(cargos);
			milisegundos = cargos.get(0).getFecha().getTime();
		}
		return milisegundos;
	}

	public void setMilisegundos(Long milisegundos) {
		this.milisegundos = milisegundos;
	}

	public String getTiempo() {
		PrettyTime p = new PrettyTime(new Locale("es"));
		tiempo = p.format(new Date(getMilisegundos()));
		tiempo = tiempo.replace("hace", "");
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public void addCargo(CargoDepartamento cargo) {
		if (this.cargos == null) {
			this.cargos = new ArrayList<CargoDepartamento>();
		}
		if (cargo.getSaldoPendiente().compareTo(BigDecimal.ZERO) > 0) {
			this.cargos.add(cargo);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((departamento == null) ? 0 : departamento.hashCode());
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
		Cobranza other = (Cobranza) obj;
		if (departamento == null) {
			if (other.departamento != null)
				return false;
		} else if (!departamento.equals(other.departamento))
			return false;
		return true;
	}

	@Override
	public int compareTo(Cobranza o) {
		return o.getAdeudo().compareTo(this.getAdeudo());
	}
}
