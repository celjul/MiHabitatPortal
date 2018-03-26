package com.bstmexico.mihabitat.web.dto.reportes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteCuentasCobrar {

	protected Collection<Adeudo> adeudos;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	private Date fin;

	private Condominio condominio;

	public ReporteCuentasCobrar() {
		super();
	}

	public Collection<Adeudo> getAdeudos() {
		return adeudos;
	}

	public void setAdeudos(Collection<Adeudo> adeudos) {
		this.adeudos = adeudos;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public void addAdeudo(Adeudo adeudo) {
		if (this.adeudos == null) {
			this.adeudos = new ArrayList<Adeudo>();
		}
		if (this.adeudos.contains(adeudo)) {
			for (Adeudo a : this.adeudos) {
				if (a.equals(adeudo)) {
					if(a.getCargos() == null){
						a.setCargos(new ArrayList<CargoDepartamento>());
					}
					a.getCargos().addAll(adeudo.getCargos());
					a.setSaldo(a.getSaldo().add(adeudo.getSaldo()));
				}
			}
		} else {
			this.adeudos.add(adeudo);
		}
	}
}
