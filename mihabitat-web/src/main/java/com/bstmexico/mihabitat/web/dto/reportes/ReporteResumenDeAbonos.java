package com.bstmexico.mihabitat.web.dto.reportes;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagos.model.Pago;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ReporteResumenDeAbonos {

	private Date inicio;

	private Date fin;

	private Condominio condominio;
	private Collection<Pago> abonos;

	public ReporteResumenDeAbonos() {
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

	public Collection<Pago> getAbonos() {
		return abonos;
	}

	public void setAbonos(Collection<Pago> abonos) {
		this.abonos = abonos;
	}

	public void addCargo(Pago abono) {
		if (this.abonos == null) {
			this.abonos = new ArrayList<Pago>();
		}
		this.abonos.add(abono);
	}
}
