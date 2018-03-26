package com.bstmexico.mihabitat.cargos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tdescuentocargodepartamento")
@PrimaryKeyJoinColumn(name = "NIdDescuento")
public class DescuentoDepartamento extends Descuento {

	private static final long serialVersionUID = -8178609423055956139L;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFecha", nullable = false)
	private Date fecha;

	public DescuentoDepartamento() {
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
