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
@Table(name = "trecargocargodepartamento")
@PrimaryKeyJoinColumn(name = "NIdRecargo")
public class RecargoDepartamento extends Recargo {

	private static final long serialVersionUID = 7411573665269133401L;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFecha", nullable = false)
	private Date fecha;

	public RecargoDepartamento() {
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
