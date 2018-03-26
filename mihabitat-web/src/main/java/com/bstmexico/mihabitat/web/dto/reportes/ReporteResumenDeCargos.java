package com.bstmexico.mihabitat.web.dto.reportes;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteResumenDeCargos {

	private Date inicio;

	private Date fin;

	private Condominio condominio;
	private Collection<CargoDepartamento> cargos;

	public ReporteResumenDeCargos() {
		super();
	}


	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Collection<CargoDepartamento> getCargos() {
		return cargos;
	}

	public void setCargos(Collection<CargoDepartamento> cargos) {
		this.cargos = cargos;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public void addCargo(CargoDepartamento cargo) {
		if (this.cargos == null) {
			this.cargos = new ArrayList<CargoDepartamento>();
		}
		this.cargos.add(cargo);
	}
}
