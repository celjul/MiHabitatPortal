package com.bstmexico.mihabitat.cargos.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bstmexico.mihabitat.departamentos.model.Departamento;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tcargosrecurrentes")
@PrimaryKeyJoinColumn(name = "NIdCargo")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class CargoRecurrente extends Cargo {

	private static final long serialVersionUID = -264699073833737205L;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tcargosrecurrentesdepartamentos", joinColumns = { @JoinColumn(name = "NIdCargo", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "NIdDepartamento", nullable = false) })
	private Collection<Departamento> departamentos;

	@JoinColumn(name = "NIdDescuento", nullable = true, referencedColumnName = "NIdDescuento")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
	private DescuentoRecurrente descuento;

	@Max(value = 28)
	@Min(value = 1)
	@Column(name = "NDia", nullable = true, unique = false)
	private Byte dia;

	@NotNull
	@Column(name = "BPrimerDiaMes", nullable = false)
	private Boolean primerDiaMes;

	@NotNull
	@Column(name = "BUltimoDiaMes", nullable = false)
	private Boolean ultimoDiaMes;

	//TODO: Quitar este campo, ya no tiene uso, por ahora
	@NotNull
	@Column(name = "BMantenimientoDepartamento", nullable = false)
	private Boolean mantenimientoDepartamento;

	@NotNull
	@Column(name = "BTodos", nullable = false)
	private Boolean todos;

	@NotEmpty
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tcargorecurrentemeses", joinColumns = @JoinColumn(name = "NIdCargo"))
	@Column(name = "NMes", nullable = false)
	private Set<Byte> meses;

	@Min(value = 0)
	@NotNull
	@Column(name = "NMonto", nullable = false, precision = 9, scale = 2)
	private BigDecimal monto;

	@JoinColumn(name = "NIdRecargo", nullable = true, referencedColumnName = "NIdRecargo")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
	private RecargoRecurrente recargo;

	public Collection<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Collection<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	public DescuentoRecurrente getDescuento() {
		return descuento;
	}

	public void setDescuento(DescuentoRecurrente descuento) {
		this.descuento = descuento;
	}

	public Byte getDia() {
		return dia;
	}

	public void setDia(Byte dia) {
		this.dia = dia;
	}

	public Boolean getMantenimientoDepartamento() {
		return mantenimientoDepartamento;
	}

	public void setMantenimientoDepartamento(Boolean mantenimientoDepartamento) {
		this.mantenimientoDepartamento = mantenimientoDepartamento;
	}

	public Set<Byte> getMeses() {
		return meses;
	}

	public void setMeses(Set<Byte> meses) {
		this.meses = meses;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public RecargoRecurrente getRecargo() {
		return recargo;
	}

	public void setRecargo(RecargoRecurrente recargo) {
		this.recargo = recargo;
	}

	public Boolean getPrimerDiaMes() {
		return primerDiaMes;
	}

	public void setPrimerDiaMes(Boolean primerDiaMes) {
		this.primerDiaMes = primerDiaMes;
	}

	public Boolean getUltimoDiaMes() {
		return ultimoDiaMes;
	}

	public void setUltimoDiaMes(Boolean ultimoDiaMes) {
		this.ultimoDiaMes = ultimoDiaMes;
	}

	public Boolean getTodos() {
		return todos;
	}

	public void setTodos(Boolean todos) {
		this.todos = todos;
	}
}
