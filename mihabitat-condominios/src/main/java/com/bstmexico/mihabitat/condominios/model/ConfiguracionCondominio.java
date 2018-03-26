package com.bstmexico.mihabitat.condominios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tconfiguracioncondominio")
public class ConfiguracionCondominio implements Serializable {

	private static final long serialVersionUID = 1354049397051992445L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdConfiguracionCondominio", nullable = false, unique = true)
	private Long id;

	@Size(max = 1024)
	@Column(length = 1024, name = "VMensajeEstadoCuenta", nullable = true)
	private String mensajeEstadoCuenta;

	@Size(max = 1024)
	@Column(length = 1024, name = "VMensajeAvisoCobro", nullable = true)
	private String mensajeAvisoCobro;

	@Size(max = 1024)
	@Column(length = 1024, name = "VMensajeReciboPago", nullable = true)
	private String mensajeReciboPago;

	@Column(name = "BDatosContactosEstadoCta", nullable = true)
	private Boolean datosContactoEstadoCta;
	
	@JoinColumn(name = "NIdPagoTDC", nullable = true, 
		referencedColumnName = "NIdPagoTDC")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private PagoTDC pagoTDC;

	public ConfiguracionCondominio() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensajeEstadoCuenta() {
		return mensajeEstadoCuenta;
	}

	public void setMensajeEstadoCuenta(String mensajeEstadoCuenta) {
		this.mensajeEstadoCuenta = mensajeEstadoCuenta;
	}

	public String getMensajeAvisoCobro() {
		return mensajeAvisoCobro;
	}

	public void setMensajeAvisoCobro(String mensajeAvisoCobro) {
		this.mensajeAvisoCobro = mensajeAvisoCobro;
	}

	public String getMensajeReciboPago() {
		return mensajeReciboPago;
	}

	public void setMensajeReciboPago(String mensajeReciboPago) {
		this.mensajeReciboPago = mensajeReciboPago;
	}

	public Boolean getDatosContactoEstadoCta() {
		return datosContactoEstadoCta;
	}

	public void setDatosContactoEstadoCta(Boolean datosContactoEstadoCta) {
		this.datosContactoEstadoCta = datosContactoEstadoCta;
	}

	public PagoTDC getPagoTDC() {
		return pagoTDC;
	}

	public void setPagoTDC(PagoTDC pagoTDC) {
		this.pagoTDC = pagoTDC;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ConfiguracionCondominio other = (ConfiguracionCondominio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
