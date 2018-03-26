package com.bstmexico.mihabitat.instalaciones.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.cargos.model.Cargo;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0 
 * @since 2015
 */

@Entity
@Table(name = "treservaciones")
public class Reservacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5905234563794083244L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdReservacion", nullable = false, unique = true)
	private Long id;
	
	@JsonIgnoreProperties(value = { "reservaciones" })
	@JoinColumn(name = "NIdInstalacion", nullable = false , referencedColumnName = "NIdInstalacion")
	@ManyToOne(fetch = FetchType.LAZY)
	private Instalacion instalacion;

	@Column(name = "DFechaInicio", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime fechaInicio;
	

	@Column(name = "DFechaFin", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime fechaFin;
	
	@NotNull
	@Column(name = "BDiaCompleto", nullable = false)
	private Boolean diaCompleto;
	
	@NotNull
	@Column(name = "BActivo", nullable = false)
	private Boolean activo;
	
	@JoinColumn(name = "NIdReservacionRecurrente", referencedColumnName = "NIdReservacionRecurrente")
	@OneToOne(fetch = FetchType.EAGER, optional = true)
	private ReservacionRecurrente reservacionRecurrente;
	
	@JoinColumn(name = "NIdDepartamento", nullable = true, referencedColumnName = "NIdDepartamento")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Departamento departamento;
	
	@JoinColumn(name = "NIdContacto", nullable = true, referencedColumnName = "NIdContacto")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private Contacto contacto;

	@JoinColumn(name = "NIdEstatus", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoEstatusReservacion estatusReservacion;

	@JoinColumn(name = "NIdCargo", nullable = true, referencedColumnName = "NIdCargo")
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private CargoDepartamento cargo;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the fechaInicio
	 */
	public DateTime getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(DateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public DateTime getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(DateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the diaCompleto
	 */
	public Boolean getDiaCompleto() {
		return diaCompleto;
	}

	/**
	 * @param diaCompleto the diaCompleto to set
	 */
	public void setDiaCompleto(Boolean diaCompleto) {
		this.diaCompleto = diaCompleto;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the reservacionRecurrente
	 */
	public ReservacionRecurrente getReservacionRecurrente() {
		return reservacionRecurrente;
	}

	/**
	 * @param reservacionRecurrente the reservacionRecurrente to set
	 */
	public void setReservacionRecurrente(ReservacionRecurrente reservacionRecurrente) {
		this.reservacionRecurrente = reservacionRecurrente;
	}

	/**
	 * @return the departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the contacto
	 */
	public Contacto getContacto() {
		return contacto;
	}

	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Instalacion getInstalacion() {
		return instalacion;
	}

	public void setInstalacion(Instalacion instalacion) {
		this.instalacion = instalacion;
	}

	public CatalogoEstatusReservacion getEstatusReservacion() {
		return estatusReservacion;
	}

	public void setEstatusReservacion(CatalogoEstatusReservacion estatusReservacion) {
		this.estatusReservacion = estatusReservacion;
	}

	public CargoDepartamento getCargo() {
		return cargo;
	}

	public void setCargo(CargoDepartamento cargo) {
		this.cargo = cargo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Reservacion)) return false;

		Reservacion that = (Reservacion) o;

		return !(getId() != null ? !getId().equals(that.getId()) : that.getId() != null);

	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}
