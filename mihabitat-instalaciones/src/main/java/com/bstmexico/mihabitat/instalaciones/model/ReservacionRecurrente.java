/**
 * 
 */
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.joda.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0 
 * @since 2015
 */

@Entity
@Table(name = "treservacionesrecurrentes")
public class ReservacionRecurrente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5027182650299081131L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdReservacionRecurrente", nullable = false, unique = true)
	private Long id;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "datetime", name = "DFechaInicio", nullable = false)
	private LocalTime fechaInicio;
	
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy hh:mm", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "datetime", name = "DFechaFin", nullable = false)
	private LocalTime fechaFin;
	
	@NotNull
	@JoinColumn(name = "NIdPeriodoRecurrente", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoPeriodoRecurrente periodoRecurrente;
	
	@NotNull
	@JoinColumn(name = "NIdReservacionCada", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoReservacionesCada reservacionesCada;
	
	@Min(value = 2)
	@NotNull
	@Column(name = "NNumeroCada", nullable = false, unique = false)
	private Integer numeroCada;
	
	

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
	public LocalTime getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(LocalTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public LocalTime getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(LocalTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the reservacionRecurrente
	 */
	public CatalogoPeriodoRecurrente getReservacionRecurrente() {
		return periodoRecurrente;
	}

	/**
	 * @param reservacionRecurrente the reservacionRecurrente to set
	 */
	public void setReservacionRecurrente(
			CatalogoPeriodoRecurrente reservacionRecurrente) {
		this.periodoRecurrente = reservacionRecurrente;
	}

	/**
	 * @return the reservacionesCada
	 */
	public CatalogoReservacionesCada getReservacionesCada() {
		return reservacionesCada;
	}

	/**
	 * @param reservacionesCada the reservacionesCada to set
	 */
	public void setReservacionesCada(CatalogoReservacionesCada reservacionesCada) {
		this.reservacionesCada = reservacionesCada;
	}

	/**
	 * @return the numeroCada
	 */
	public Integer getNumeroCada() {
		return numeroCada;
	}

	/**
	 * @param numeroCada the numeroCada to set
	 */
	public void setNumeroCada(Integer numeroCada) {
		this.numeroCada = numeroCada;
	}
	
	

}
