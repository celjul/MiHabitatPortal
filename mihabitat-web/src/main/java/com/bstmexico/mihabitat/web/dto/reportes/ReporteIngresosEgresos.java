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
public class ReporteIngresosEgresos {

	private Cuenta bancosCorte;

	private Cuenta cajasCorte;
	
	private Cuenta cobrar;
	
	private Cuenta pagar;

	private Cuenta ingresos;

	private Cuenta egresos;

	private Cuenta saldos;

	private Cuenta bancos;

	private Cuenta cajas;
	
	private Cuenta total;
	
	private Collection<Cuenta> cuentasinicial;
	
	private Collection<Cuenta> cuentas;
	
	private Collection<Cuenta> cuentasingresos;
	
	private Collection<Cuenta> cuentasegresos;
	
	private Collection<Cuenta> cuentasfinal;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	private Date inicio;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	private Date fin;

	private Condominio condominio;

	public ReporteIngresosEgresos() {
		super();
		this.cuentasinicial = new ArrayList<Cuenta>();
		this.cuentas = new ArrayList<Cuenta>();
		this.cuentasingresos = new ArrayList<Cuenta>();
		this.cuentasegresos = new ArrayList<Cuenta>();
		this.cuentasfinal = new ArrayList<Cuenta>();
	}

	public Cuenta getBancosCorte() {
		return bancosCorte;
	}

	public void setBancosCorte(Cuenta bancosCorte) {
		this.bancosCorte = bancosCorte;
	}

	public Cuenta getCajasCorte() {
		return cajasCorte;
	}

	public void setCajasCorte(Cuenta cajasCorte) {
		this.cajasCorte = cajasCorte;
	}
	
	public Cuenta getCobrar() {
		return cobrar;
	}

	public void setCobrar(Cuenta cobrar) {
		this.cobrar = cobrar;
	}
	
	public Cuenta getPagar() {
		return pagar;
	}

	public void setPagar(Cuenta pagar) {
		this.pagar = pagar;
	}

	public Cuenta getIngresos() {
		return ingresos;
	}

	public void setIngresos(Cuenta ingresos) {
		this.ingresos = ingresos;
	}

	public Cuenta getEgresos() {
		return egresos;
	}

	public void setEgresos(Cuenta egresos) {
		this.egresos = egresos;
	}

	public Cuenta getSaldos() {
		return saldos;
	}

	public void setSaldos(Cuenta saldos) {
		this.saldos = saldos;
	}

	public Cuenta getBancos() {
		return bancos;
	}

	public void setBancos(Cuenta bancos) {
		this.bancos = bancos;
	}

	public Cuenta getCajas() {
		return cajas;
	}

	public void setCajas(Cuenta cajas) {
		this.cajas = cajas;
	}

	public Cuenta getTotal() {
		return total;
	}

	public void setTotal(Cuenta total) {
		this.total = total;
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

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Collection<Cuenta> getCuentasinicial() {
		return cuentasinicial;
	}

	public void setCuentasinicial(Collection<Cuenta> cuentasinicial) {
		this.cuentasinicial = cuentasinicial;
	}

	public Collection<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(Collection<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public Collection<Cuenta> getCuentasingresos() {
		return cuentasingresos;
	}

	public void setCuentasingresos(Collection<Cuenta> cuentasingresos) {
		this.cuentasingresos = cuentasingresos;
	}

	public Collection<Cuenta> getCuentasegresos() {
		return cuentasegresos;
	}

	public void setCuentasegresos(Collection<Cuenta> cuentasegresos) {
		this.cuentasegresos = cuentasegresos;
	}

	public Collection<Cuenta> getCuentasfinal() {
		return cuentasfinal;
	}

	public void setCuentasfinal(Collection<Cuenta> cuentasfinal) {
		this.cuentasfinal = cuentasfinal;
	}
}
