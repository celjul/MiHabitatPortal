package com.bstmexico.mihabitat.cargos.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tcargoagrupador")
@PrimaryKeyJoinColumn(name = "NIdCargo")
public class CargoAgrupador extends Cargo {

	private static final long serialVersionUID = 6919686336743933509L;

	@Size(min = 1)
	@NotNull
	@JoinColumn(name = "NIdCargoAgrupador", nullable = true, referencedColumnName = "NIdCargo")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Collection<CargoDepartamento> cargos;

	@JoinColumn(name = "NIdDescuento", nullable = true, referencedColumnName = "NIdDescuento")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
	private DescuentoDepartamento descuento;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFecha", nullable = false)
	private Date fecha;

	@NotNull
	@Column(name = "BMantenimientoDepartamento", nullable = false)
	private Boolean mantenimientoDepartamento;

	@Min(value = 0)
	@NotNull
	@Column(name = "NMonto", nullable = false, precision = 9, scale = 2)
	private BigDecimal monto;

	@JoinColumn(name = "NIdRecargo", nullable = true, referencedColumnName = "NIdRecargo")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
	private RecargoDepartamento recargo;

	// poner esta solo en la clase abstracta @JsonTypeInfo(use =
	// JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property =
	// "@class")
	// mi json debe de tener la referencia a la clase @class :
	// com.bstmexico.mihabitat.cargos.model.Consumoprorrateo
	@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
	@JsonSubTypes({
			@JsonSubTypes.Type(value = ConsumoAgrupadorSimple.class, name = "simple"),
			@JsonSubTypes.Type(value = ConsumoAgrupadorProrrateo.class, name = "prorrateo"),
			@JsonSubTypes.Type(value = ConsumoAgrupadorIndiviso.class, name = "indiviso") })
	@JoinColumn(name = "NIdConsumo", nullable = true, referencedColumnName = "NIdConsumo")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
	private Consumo consumo;

	public CargoAgrupador() {
	}

	public Collection<CargoDepartamento> getCargos() {
		return cargos;
	}

	public void setCargos(Collection<CargoDepartamento> cargos) {
		this.cargos = cargos;
	}

	public DescuentoDepartamento getDescuento() {
		return descuento;
	}

	public void setDescuento(DescuentoDepartamento descuento) {
		this.descuento = descuento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getMantenimientoDepartamento() {
		return mantenimientoDepartamento;
	}

	public void setMantenimientoDepartamento(Boolean mantenimientoDepartamento) {
		this.mantenimientoDepartamento = mantenimientoDepartamento;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public RecargoDepartamento getRecargo() {
		return recargo;
	}

	public void setRecargo(RecargoDepartamento recargo) {
		this.recargo = recargo;
	}

	public Consumo getConsumo() {
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}
}