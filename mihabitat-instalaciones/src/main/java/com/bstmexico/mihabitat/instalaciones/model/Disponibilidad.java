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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "tdisponibilidades")
public class Disponibilidad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4869182801107452969L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdDisponibilidad", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@Column(name = "NDia", nullable = false, unique = false)
	private Integer dia;

	@NotNull
	@Column(name = "BActivo", nullable = false)
	private Boolean activo;

	//@Column(columnDefinition = "time", name = "DHoraInicio", nullable = true)
	@Column(name = "DHoraInicio", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	private LocalTime horaInicio;
	
	//@Column(columnDefinition = "time", name = "DHoraFin", nullable = true)
	@Column(name = "DHoraFin", nullable = true)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	private LocalTime horaFin;

	public Disponibilidad() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}
}
