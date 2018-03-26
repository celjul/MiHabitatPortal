package com.bstmexico.mihabitat.web.dto.reportes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
public class ReporteCuentasPagar {
	
	protected Collection<AdeudoProveedor> adeudos;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	private Date fin;

	private Condominio condominio;
	
	public ReporteCuentasPagar() {
		super();
	}

	public Collection<AdeudoProveedor> getAdeudos() {
		return adeudos;
	}

	public void setAdeudos(Collection<AdeudoProveedor> adeudos) {
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
	
	public void addAdeudo(AdeudoProveedor adeudo) {
		if (this.adeudos == null) {
			this.adeudos = new ArrayList<AdeudoProveedor>();
		}
		if (this.adeudos.contains(adeudo)) {
			for (AdeudoProveedor a : this.adeudos) {
				if (a.equals(adeudo)) {
					a.setSaldo(a.getSaldo().add(adeudo.getSaldo()));
				}
			}
		} else {
			this.adeudos.add(adeudo);
		}
	}
}
