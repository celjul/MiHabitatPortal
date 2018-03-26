package com.bstmexico.mihabitat.web.dto.reportes;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.util.Collection;
import java.util.Date;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteDirectorioDepartamento {

	private Condominio condominio;

	private Collection<Departamento> departamentos;

	public ReporteDirectorioDepartamento() {
		super();
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Collection<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Collection<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
}
