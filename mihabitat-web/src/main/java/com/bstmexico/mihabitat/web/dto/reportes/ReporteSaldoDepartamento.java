package com.bstmexico.mihabitat.web.dto.reportes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.web.dto.EstadoCuenta;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteSaldoDepartamento {

	private Date inicio;

	private Date fin;

	private Condominio condominio;
	private Collection<EstadoCuenta> saldos;

	public ReporteSaldoDepartamento() {
		super();
	}


	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Collection<EstadoCuenta> getSaldos() {
		return saldos;
	}

	public void setSaldos(Collection<EstadoCuenta> saldos) {
		this.saldos = saldos;
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

	public void addSaldo(EstadoCuenta saldo) {
		if (this.saldos == null) {
			this.saldos = new ArrayList<EstadoCuenta>();
		}
		this.saldos.add(saldo);
	}
}
