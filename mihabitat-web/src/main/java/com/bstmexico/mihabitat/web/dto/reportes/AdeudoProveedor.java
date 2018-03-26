package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.proveedores.model.Cfdi;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
public class AdeudoProveedor {

	private Collection<Cfdi> cfdis;

	private Long idProveedor;

	private String proveedor;

	private BigDecimal saldo;

	private Long milisegundos;

	private String antiguedad;

	public AdeudoProveedor() {
		super();
	}

	public Collection<Cfdi> getCfdis() {
		return cfdis;
	}

	public void setCfdis(Collection<Cfdi> cfdis) {
		this.cfdis = cfdis;
	}

	public Long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Long getMilisegundos() {
		List<Cfdi> facturas = (List<Cfdi>) getCfdis();
		if (!CollectionUtils.isEmpty(facturas)) {
			Collections.sort(facturas);
			milisegundos = facturas.get(0).getFechaEmision().getTime();
		}
		return milisegundos;
	}

	public void setMilisegundos(Long milisegundos) {
		this.milisegundos = milisegundos;
	}

	public String getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(String antiguedad) {
		this.antiguedad = antiguedad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProveedor == null) ? 0 : idProveedor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdeudoProveedor other = (AdeudoProveedor) obj;
		if (idProveedor == null) {
			if (other.idProveedor != null)
				return false;
		} else if (!idProveedor.equals(other.idProveedor))
			return false;
		return true;
	}

	public void addCfdi(Cfdi factura) {
		if (this.cfdis == null) {
			this.cfdis = new ArrayList<Cfdi>();
		}
		this.cfdis.add(factura);
	}
}
