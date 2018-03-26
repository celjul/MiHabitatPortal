package com.bstmexico.mihabitat.web.dto.reportes;

import java.util.ArrayList;
import java.util.List;

import com.bstmexico.mihabitat.condominios.model.Condominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteSaldoFavor {

	private Condominio condominio;

	private List<Saldo> saldos;

	public ReporteSaldoFavor() {
		super();
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public List<Saldo> getSaldos() {
		return saldos;
	}

	public void setSaldos(List<Saldo> saldos) {
		this.saldos = saldos;
	}

	public void addSaldo(Saldo saldo) {
		if (this.saldos == null) {
			this.saldos = new ArrayList<Saldo>();
		}
		this.saldos.add(saldo);
	}
}
