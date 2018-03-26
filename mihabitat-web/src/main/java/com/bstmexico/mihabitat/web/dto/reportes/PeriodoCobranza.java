package com.bstmexico.mihabitat.web.dto.reportes;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class PeriodoCobranza implements Comparable<PeriodoCobranza> {

	private String periodo;

	private BigDecimal cobros;

	private BigDecimal pagosATiempo;

	private BigDecimal pagosExtemporaneos;

	private BigDecimal porCobrar;

	private Double porcentajeCumplimiento;

	private Double porcentajeEfectividad;

	private Double porcentajeMorosidad;

	private Byte id;

	public PeriodoCobranza() {
		super();
		cobros = BigDecimal.ZERO;
		pagosATiempo = BigDecimal.ZERO;
		pagosExtemporaneos = BigDecimal.ZERO;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getCobros() {
		return cobros;
	}

	public void setCobros(BigDecimal cobros) {
		this.cobros = cobros;
	}

	public BigDecimal getPagosATiempo() {
		return pagosATiempo;
	}

	public void setPagosATiempo(BigDecimal pagosATiempo) {
		this.pagosATiempo = pagosATiempo;
	}

	public BigDecimal getPagosExtemporaneos() {
		return pagosExtemporaneos;
	}

	public void setPagosExtemporaneos(BigDecimal pagosExtemporaneos) {
		this.pagosExtemporaneos = pagosExtemporaneos;
	}

	public BigDecimal getPorCobrar() {
		porCobrar = getCobros().subtract(getPagosATiempo()).subtract(
				getPagosExtemporaneos());
		return porCobrar;
	}

	public void setPorCobrar(BigDecimal porCobrar) {
		this.porCobrar = porCobrar;
	}

	public Double getPorcentajeCumplimiento() {
		if (getCobros().compareTo(BigDecimal.ZERO) != 0) {
			try {
				porcentajeCumplimiento = getPagosATiempo()
						.divide(getCobros(), 2, RoundingMode.HALF_UP)
						.multiply(BigDecimal.valueOf(100)).doubleValue();
			} catch (ArithmeticException ex) {
				porcentajeCumplimiento = null;
			}
		} else {
			porcentajeCumplimiento = new Double(100);
		}
		return porcentajeCumplimiento;
	}

	public void setPorcentajeCumplimiento(Double porcentajeCumplimiento) {
		this.porcentajeCumplimiento = porcentajeCumplimiento;
	}

	public Double getPorcentajeEfectividad() {
		if (getCobros().compareTo(BigDecimal.ZERO) != 0) {
			try {
				porcentajeEfectividad = (getPagosExtemporaneos())
						.divide(getCobros().subtract(getPagosATiempo()), 2,
								RoundingMode.HALF_UP)
						.multiply(BigDecimal.valueOf(100)).doubleValue();
			} catch (ArithmeticException ex) {
				porcentajeEfectividad = null;
			}
		} else {
			porcentajeEfectividad = new Double(100);
		}
		return porcentajeEfectividad;
	}

	public void setPorcentajeEfectividad(Double porcentajeEfectividad) {
		this.porcentajeEfectividad = porcentajeEfectividad;
	}

	public Double getPorcentajeMorosidad() {
		if (getCobros().compareTo(BigDecimal.ZERO) != 0) {
			try {
				porcentajeMorosidad = getCobros().subtract(getPagosATiempo())
						.subtract(getPagosExtemporaneos())
						.divide(getCobros(), 2, RoundingMode.HALF_UP)
						.multiply(BigDecimal.valueOf(100)).doubleValue();
			} catch (ArithmeticException ex) {
				porcentajeMorosidad = null;
			}
		} else {
			porcentajeMorosidad = new Double(0);
		}
		return porcentajeMorosidad;
	}

	public void setPorcentajeMorosidad(Double porcentajeMorosidad) {
		this.porcentajeMorosidad = porcentajeMorosidad;
	}

	public Byte getId() {
		return id;
	}

	public void setId(Byte id) {
		this.id = id;
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
		PeriodoCobranza other = (PeriodoCobranza) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(PeriodoCobranza o) {
		return getId().compareTo(o.getId());
	}
}
