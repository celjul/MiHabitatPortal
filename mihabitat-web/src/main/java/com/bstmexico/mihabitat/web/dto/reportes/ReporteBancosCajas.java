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
 * @since 2015
 */
public class ReporteBancosCajas {

	private Condominio condominio;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	private Date inicio;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	private Date fin;

	private Collection<Cuenta> cuentas;

	public ReporteBancosCajas() {
		super();
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
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

	public Collection<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(Collection<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	public void addCuenta(Cuenta cuenta) {
		if (this.cuentas == null) {
			this.cuentas = new ArrayList<Cuenta>();
		}
		this.cuentas.add(cuenta);
	}
}
