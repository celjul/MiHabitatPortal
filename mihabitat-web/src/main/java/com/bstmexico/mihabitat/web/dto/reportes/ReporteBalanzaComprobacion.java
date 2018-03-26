package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class ReporteBalanzaComprobacion {
	
	private Condominio condominio;
	
	private BigDecimal debe;
	
	private BigDecimal haber;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	private Date inicio;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	private Date fin;

	private Collection<Cuenta> cuentas;
	
	public ReporteBalanzaComprobacion() {
		super();
		debe = BigDecimal.ZERO;
		haber = BigDecimal.ZERO;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public BigDecimal getDebe() {
		if (!CollectionUtils.isEmpty(getCuentas())) {
			for (Cuenta cuenta : getCuentas()) {
				if (cuenta.getDebe() != null) {
					debe = debe.add(cuenta.getDebe());
				}
			}
		}
		return debe;
	}

	public void setDebe(BigDecimal debe) {
		this.debe = debe;
	}

	public BigDecimal getHaber() {
		if (!CollectionUtils.isEmpty(getCuentas())) {
			for (Cuenta cuenta : getCuentas()) {
				if (cuenta.getHaber() != null) {
					haber = haber.add(cuenta.getHaber());
				}
			}
		}
		return haber;
	}

	public void setHaber(BigDecimal haber) {
		this.haber = haber;
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
