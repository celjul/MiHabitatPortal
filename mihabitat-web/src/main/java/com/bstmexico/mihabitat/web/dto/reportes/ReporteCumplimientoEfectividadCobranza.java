package com.bstmexico.mihabitat.web.dto.reportes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bstmexico.mihabitat.condominios.model.Condominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteCumplimientoEfectividadCobranza {

	private Short anio;

	private Collection<Short> anios;

	private Condominio condominio;

	private List<PeriodoCobranza> periodos;

	public ReporteCumplimientoEfectividadCobranza() {
		super();
	}

	public Short getAnio() {
		return anio;
	}

	public void setAnio(Short anio) {
		this.anio = anio;
	}

	public Collection<Short> getAnios() {
		return anios;
	}

	public void setAnios(Collection<Short> anios) {
		this.anios = anios;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<PeriodoCobranza> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<PeriodoCobranza> periodos) {
		this.periodos = periodos;
	}

	public void addPeriodo(PeriodoCobranza periodo) {
		if (periodos == null) {
			periodos = new ArrayList<PeriodoCobranza>();
		}
		if (this.periodos.contains(periodo)) {
			for (PeriodoCobranza p : this.periodos) {
				if (periodo.equals(p)) {
					p.setCobros(p.getCobros().add(periodo.getCobros()));
					p.setPagosATiempo(p.getPagosATiempo().add(
							periodo.getPagosATiempo()));
					p.setPagosExtemporaneos(p.getPagosExtemporaneos().add(
							periodo.getPagosExtemporaneos()));
					break;
				}
			}
		} else {
			periodos.add(periodo);
		}
	}
}
