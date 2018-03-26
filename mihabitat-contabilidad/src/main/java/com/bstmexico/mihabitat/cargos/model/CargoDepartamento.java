package com.bstmexico.mihabitat.cargos.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tcargosdepartamento")
@PrimaryKeyJoinColumn(name = "NIdCargo")
public class CargoDepartamento extends Cargo implements Comparable<CargoDepartamento> {

	private static final long serialVersionUID = -906771912261968901L;

	@JoinColumn(name = "NIdCargoAgrupador", nullable = true, referencedColumnName = "NIdCargo", updatable = false)
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	private CargoAgrupador agrupador;

	@NotNull
	@JoinColumn(name = "NIdDepartamento", nullable = false, referencedColumnName = "NIdDepartamento")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private Departamento departamento;

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

	@JoinColumn(name = "NIdRecargo", nullable = true, referencedColumnName = "NIdRecargo")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
	private RecargoDepartamento recargo;

	@JoinColumn(name = "NIdCargo", nullable = true, referencedColumnName = "NIdCargo")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<MovimientoCargo> movimientos;

	@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
	@JsonSubTypes({
			@JsonSubTypes.Type(value = ConsumoDepartamentoSimple.class, name = "dep-simple"),
			@JsonSubTypes.Type(value = ConsumoDepartamentoProrrateo.class, name = "dep-prorrateo"),
			@JsonSubTypes.Type(value = ConsumoDepartamentoIndiviso.class, name = "dep-indiviso") })
	@JoinColumn(name = "NIdConsumo", nullable = true, referencedColumnName = "NIdConsumo")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true, orphanRemoval = true)
	private Consumo consumo;

	@JsonIgnore
	@Transient
	private static Properties configuration;

	public CargoDepartamento() {
		super();
		try {
			configuration = PropertiesLoaderUtils
					.loadAllProperties("configuration.properties");
		} catch (IOException e) {
		}
	}

	public CargoAgrupador getAgrupador() {
		return agrupador;
	}

	public void setAgrupador(CargoAgrupador agrupador) {
		this.agrupador = agrupador;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
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

	@Override
	public String getConcepto() {
		String concepto = super.getConcepto();
		if(getConsumo() != null && getConsumo() instanceof ConsumoDepartamento){
			ConsumoDepartamento consumoDepartamento = (ConsumoDepartamento) getConsumo();
			/*concepto = super.getConcepto() + " | Consumo Del Periodo: " + (BigDecimal.valueOf(consumoDepartamento.getLecturaNueva()).subtract(
					BigDecimal.valueOf(consumoDepartamento.getLecturaAnterior())).setScale(4, BigDecimal.ROUND_HALF_UP)) +
					", Lectura Anterior: " + consumoDepartamento.getLecturaAnterior() + " - Nueva Lectura: " +
					consumoDepartamento.getLecturaNueva() + ", Costo Por Unidad: $" + consumoDepartamento.getCostoUnidad();*/
			concepto = super.getConcepto() + " | Lectura Anterior: " + consumoDepartamento.getLecturaAnterior() + " - Nueva Lectura: " +
					consumoDepartamento.getLecturaNueva() + ", Costo Por Unidad: $" + consumoDepartamento.getCostoUnidad();

		}
		return concepto;
	}

	public Boolean getMantenimientoDepartamento() {
		return mantenimientoDepartamento;
	}

	public void setMantenimientoDepartamento(Boolean mantenimientoDepartamento) {
		this.mantenimientoDepartamento = mantenimientoDepartamento;
	}

	public RecargoDepartamento getRecargo() {
		return recargo;
	}

	public void setRecargo(RecargoDepartamento recargo) {
		this.recargo = recargo;
	}

	public Collection<MovimientoCargo> getMovimientos() {
		if(movimientos == null) {
			movimientos = new ArrayList<>();
		}
		return movimientos;
	}

	public Collection<MovimientoCargo> getMovimientos(Date fecha) {
		Collection<MovimientoCargo> movsAux = new ArrayList<>();
		if(movimientos == null) {
			movimientos = new ArrayList<>();
		}
		if(fecha != null) {
			for (MovimientoCargo mov : movimientos) {
				if (mov.getFecha().before(fecha) || mov.getFecha().equals(fecha)) {
					movsAux.add(mov);
				}
			}
		} else {
			movsAux = movimientos;
		}
		return movsAux;
	}

	public void setMovimientos(Collection<MovimientoCargo> movimientos) {
		this.movimientos = movimientos;
	}

	public Consumo getConsumo() {
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}

	/** Propiedades para realizar calculos **/

	@Transient
	private MovimientoCargo cargo;

	@Transient
	private Set<MovimientoCargo> ajustes;

	@Transient
	private Set<MovimientoCargo> recargos;

	@Transient
	private Set<MovimientoCargo> descuentos;

	@Transient
	private MovimientoCargo cargoCancelado;

	@Transient
	private Set<MovimientoCargo> recargosCancelados;

	@Transient
	private Set<MovimientoCargo> descuentosCancelados;

	@Transient
	private BigDecimal totalMonto;

	@Transient
	private BigDecimal totalRecargos;

	@Transient
	private BigDecimal totalDescuentos;

	@Transient
	private BigDecimal totalPagado;

	@Transient
	private BigDecimal saldoPendiente;

	@Transient
	private BigDecimal recargosCalculados;

	@Transient
	private BigDecimal descuentosCalculados;


	public MovimientoCargo getCargo() {
		if (!CollectionUtils.isEmpty(getMovimientos(null))) {
			for (MovimientoCargo movimiento : getMovimientos(null)) {
				/* 28 cargo */
				if (movimiento
						.getTipo()
						.getId()
						.equals(Long.valueOf(configuration.getProperty("cargo")))) {
					cargo = movimiento;
					break;
				}
			}
		}
		return cargo;
	}

	public Set<MovimientoCargo> getAjustes() {
		ajustes = new HashSet<MovimientoCargo>();
		if (!CollectionUtils.isEmpty(getMovimientos(null))) {
			for (MovimientoCargo movimiento : getMovimientos(null)) {
				/* 40 ajustecargo */
				if (movimiento
						.getTipo()
						.getId()
						.equals(Long.valueOf(configuration
								.getProperty("ajustecargo")))) {
					ajustes.add(movimiento);
				}
			}
		}
		return ajustes;
	}

	public Set<MovimientoCargo> getRecargos() {
		return getRecargos(null);
	}

	public Set<MovimientoCargo> getRecargos(Date fecha) {
		recargos = new HashSet<MovimientoCargo>();
		if (!CollectionUtils.isEmpty(getMovimientos(fecha))) {
			for (MovimientoCargo movimiento : getMovimientos(fecha)) {
				/* 29 recargo */
				if (movimiento
						.getTipo()
						.getId()
						.equals(Long.valueOf(configuration
								.getProperty("recargo")))) {
					recargos.add(movimiento);
				}
			}
		}
		return recargos;
	}

	public Set<MovimientoCargo> getDescuentos() {
		return getDescuentos(null);
	}

	public Set<MovimientoCargo> getDescuentos(Date fecha) {
		descuentos = new HashSet<MovimientoCargo>();
		if (!CollectionUtils.isEmpty(getMovimientos(fecha))) {
			for (MovimientoCargo movimiento : getMovimientos(fecha)) {
				/* 30 descuento */
				if (movimiento
						.getTipo()
						.getId()
						.equals(Long.valueOf(configuration
								.getProperty("descuento")))) {
					descuentos.add(movimiento);
				}
			}
		}
		return descuentos;
	}

	public MovimientoCargo getCargoCancelado() {
		return getCargoCancelado(null);
	}

	public MovimientoCargo getCargoCancelado(Date fecha) {
		if (!CollectionUtils.isEmpty(getMovimientos(fecha))) {
			for (MovimientoCargo movimiento : getMovimientos(fecha)) {
				/* 36 cancelacioncargo */
				if (movimiento
						.getTipo()
						.getId()
						.equals(Long.valueOf(configuration
								.getProperty("cancelacioncargo")))) {
					cargoCancelado = movimiento;
					break;
				}
			}
		}
		return cargoCancelado;
	}

	public Set<MovimientoCargo> getRecargosCancelados() {
		return  getRecargosCancelados(null);
	}

	public Set<MovimientoCargo> getRecargosCancelados(Date fecha) {
		recargosCancelados = new HashSet<MovimientoCargo>();
		if (!CollectionUtils.isEmpty(getMovimientos(fecha))) {
			for (MovimientoCargo movimiento : getMovimientos(fecha)) {
				/* 34 cancelacionrecargo */
				if (movimiento
						.getTipo()
						.getId()
						.equals(Long.valueOf(configuration
								.getProperty("cancelacionrecargo")))) {
					recargosCancelados.add(movimiento);
				}
			}
		}
		return recargosCancelados;
	}

	public Set<MovimientoCargo> getDescuentosCancelados() {
		return getDescuentosCancelados(null);
	}

	public Set<MovimientoCargo> getDescuentosCancelados(Date fecha) {
		descuentosCancelados = new HashSet<MovimientoCargo>();
		if (!CollectionUtils.isEmpty(getMovimientos(fecha))) {
			for (MovimientoCargo movimiento : getMovimientos(fecha)) {
				/* 35 cancelaciondescuento */
				if (movimiento
						.getTipo()
						.getId()
						.equals(Long.valueOf(configuration
								.getProperty("cancelaciondescuento")))) {
					descuentosCancelados.add(movimiento);
				}
			}
		}
		return descuentosCancelados;
	}

	public BigDecimal getTotalMonto() {
		return getTotalMonto(null);
	}

	public BigDecimal getTotalMonto(Date fecha) {
		totalMonto = getCargo().getDebe() != null ? getCargo().getDebe()
				: BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getAjustes())) {
			for (MovimientoCargo ajuste : getAjustes()) {
				totalMonto = ajuste.getDebe() != null ? totalMonto.add(ajuste.getDebe()) : ajuste.getHaber() != null ? totalMonto
						.subtract(ajuste.getHaber()) : totalMonto;
			}
		}
		return totalMonto;
	}

	public BigDecimal getTotalRecargos() {
		return getTotalRecargos(null);
	}

	public BigDecimal getTotalRecargos(Date fecha) {
		totalRecargos = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getRecargos(fecha))) {
			for (MovimientoCargo recargo : getRecargos(fecha)) {
				totalRecargos = totalRecargos.add(recargo.getDebe());
			}
		}
		if (!CollectionUtils.isEmpty(getRecargosCancelados(fecha))) {
			for (MovimientoCargo recargoCancelado : getRecargosCancelados(fecha)) {
				totalRecargos = totalRecargos.subtract(recargoCancelado
						.getHaber());
			}
		}
		return totalRecargos;
	}

	public BigDecimal getTotalDescuentos() {
		return getTotalDescuentos(null);
	}

	public BigDecimal getTotalDescuentos(Date fecha) {
		totalDescuentos = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getDescuentos(fecha))) {
			for (MovimientoCargo descuento : getDescuentos(fecha)) {
				totalDescuentos = totalDescuentos.add(descuento.getHaber());
			}
		}
		if (!CollectionUtils.isEmpty(getDescuentosCancelados(fecha))) {
			for (MovimientoCargo descuentoCancelado : getDescuentosCancelados(fecha)) {
				totalDescuentos = totalDescuentos.subtract(descuentoCancelado
						.getDebe());
			}
		}
		return totalDescuentos;
	}

	public BigDecimal getTotalPagado() {
		return getTotalPagado(null);
	}

	public BigDecimal getTotalPagado(Date fecha) {
		totalPagado = cargo.getTotal();
		if (!CollectionUtils.isEmpty(getRecargos(fecha))) {
			for (MovimientoCargo recargo : getRecargos(fecha)) {
				totalPagado = totalPagado.add(recargo.getTotal());
			}
		}
		if (!CollectionUtils.isEmpty(descuentos)) {
			for (MovimientoCargo descuento : descuentos) {
				totalPagado = totalPagado.add(descuento.getTotal());
			}
		}
		return totalPagado;
	}

	public BigDecimal getSaldoPendiente() {
		return getSaldoPendiente(null);
	}

	public BigDecimal getSaldoPendiente(Date fecha) {
		if(getCargo() == null || getCargo().getCancelado()) {
			saldoPendiente = BigDecimal.ZERO;
		} else {
			saldoPendiente = getTotalMonto(fecha).add(getTotalRecargos(fecha))
					.subtract(getTotalDescuentos(fecha)).subtract(getTotalPagado(fecha));
		}
		return saldoPendiente;
	}

	public BigDecimal getRecargosCalculados() {
		recargosCalculados = BigDecimal.ZERO;
		if(this.getRecargo() != null) {
			if(this.getRecargo().getPorcentaje()) {
				if(this.getRecargo().getTipoInteres().getId() == Long.valueOf(configuration
						.getProperty("tipoInteres.simple")) || this.getRecargo().getTipoInteres().getId() == Long.valueOf(configuration
						.getProperty("tipoInteres.unico"))){
					recargosCalculados = (getTotalMonto().subtract(cargo.getTotalPagado()))// Checar si esto es correcto o hay que ir por el total pagado de todo.
							.multiply(this.getRecargo().getMonto().divide(BigDecimal.valueOf(100L)));
				}
				else {
					recargosCalculados = (getTotalMonto().add(getTotalRecargos()).subtract(getTotalPagado()))
							.multiply(this.getRecargo().getMonto().divide(BigDecimal.valueOf(100L)));
				}
			}
			else {
				recargosCalculados = this.getRecargo().getMonto();
			}
		}

		return recargosCalculados;
	}

	public BigDecimal getDescuentosCalculados() {
		descuentosCalculados = BigDecimal.ZERO;
		if(this.getDescuento() != null) {
			if(this.getDescuento().getPorcentaje()) {
				descuentosCalculados = (getTotalMonto()
						.multiply(this.getDescuento().getMonto().divide(BigDecimal.valueOf(100L))));
			}
			else {
				descuentosCalculados = this.getDescuento().getMonto();
			}
		}

		return descuentosCalculados;
	}

	@Override
	public int compareTo(CargoDepartamento o) {
		return getFecha().compareTo(o.getFecha());
	}
}
