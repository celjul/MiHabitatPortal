package com.bstmexico.mihabitat.cargos.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Lopez Cruz Pamela Montserrat
 * @version 1.0
 * @since 2015
 */
@MappedSuperclass
public abstract class ConsumoDepartamento extends Consumo {

	private static final long serialVersionUID = -3208249577373065443L;

	@Column(name = "DLecturaAnterior", nullable = true, precision = 9, scale = 2)
	private Double lecturaAnterior;

	@Column(name = "DLecturaNueva", nullable = true, precision = 9, scale = 2)
	private Double lecturaNueva;

	public ConsumoDepartamento() {
		super();
	}

	public Double getLecturaAnterior() {
		return lecturaAnterior;
	}

	public void setLecturaAnterior(Double lecturaAnterior) {
		this.lecturaAnterior = lecturaAnterior;
	}

	public Double getLecturaNueva() {
		return lecturaNueva;
	}

	public void setLecturaNueva(Double lecturaNueva) {
		this.lecturaNueva = lecturaNueva;
	}
}
