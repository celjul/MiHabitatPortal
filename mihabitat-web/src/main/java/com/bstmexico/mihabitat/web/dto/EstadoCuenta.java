package com.bstmexico.mihabitat.web.dto;

import com.bstmexico.mihabitat.cargos.model.Cargo;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoSaldo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class EstadoCuenta {

	private BigDecimal saldoAnterior;

	private BigDecimal saldoFavorAnterior;

	private BigDecimal totalCargos;

	private BigDecimal totalRecargos;

	private BigDecimal totalDescuentos;

	private BigDecimal total;

	private BigDecimal totalPagos;

	private BigDecimal saldoDeudor;

	private BigDecimal saldoFavor;

	private BigDecimal saldoFavorAlDia;

	private Collection<Movimiento> movimientos;

	private Departamento departamento;

	private Contacto contacto;

	private Date inicio;

	private Date fin;

	private Collection<Cargo> cargos;

	private AvisoDeCobro avisoDeCobro;

	private HistoricoDeCargos historicoDeCargos;

	private HistoricoDeAbonos historicoDeAbonos;

	@JsonIgnore
	private static Properties properties;

	public EstadoCuenta() {
		saldoAnterior = BigDecimal.ZERO;
		try {
			properties = PropertiesLoaderUtils
					.loadAllProperties("configuration.properties");
		} catch (IOException e) {
		}
	}

	public BigDecimal getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(BigDecimal saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public BigDecimal getTotalCargos() {
		totalCargos = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getMovimientos())) {
			for (Movimiento movimiento : getMovimientos()) {
				if (movimiento instanceof MovimientoCargo) {
					if (((MovimientoCargo) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.cargo")))) {
						totalCargos = totalCargos.add(movimiento.getDebe());
					}
					if (((MovimientoCargo) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.cancelacioncargo")))) {
						totalCargos = totalCargos.subtract(movimiento
								.getHaber());
					}
					if (((MovimientoCargo) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.ajustecargo")))) {
						if (movimiento.getDebe() != null) {
							totalCargos = totalCargos.add(movimiento.getDebe());
						}
						if (movimiento.getHaber() != null) {
							totalCargos = totalCargos.subtract(movimiento
									.getHaber());
						}
					}
				}
			}
		}
		return totalCargos;
	}

	public void setTotalCargos(BigDecimal totalCargos) {
		this.totalCargos = totalCargos;
	}

	public BigDecimal getTotalRecargos() {
		totalRecargos = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getMovimientos())) {
			for (Movimiento movimiento : getMovimientos()) {
				if (movimiento instanceof MovimientoCargo) {
					if (((MovimientoCargo) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.recargo")))) {
						totalRecargos = totalRecargos.add(movimiento.getDebe());
					}
					if (((MovimientoCargo) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.cancelacionrecargo")))) {
						totalRecargos = totalRecargos.subtract(movimiento
								.getHaber());
					}
				}

			}
		}
		return totalRecargos;
	}

	public void setTotalRecargos(BigDecimal totalRecargos) {
		this.totalRecargos = totalRecargos;
	}

	public BigDecimal getTotalDescuentos() {
		totalDescuentos = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getMovimientos())) {
			for (Movimiento movimiento : getMovimientos()) {
				if (movimiento instanceof MovimientoCargo) {
					if (((MovimientoCargo) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.descuento")))) {
						totalDescuentos = totalDescuentos.add(movimiento
								.getHaber());
					}
					if (((MovimientoCargo) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.cancelaciondescuento")))) {
						totalDescuentos = totalDescuentos.subtract(movimiento
								.getDebe());
					}
				}
			}
		}
		return totalDescuentos;
	}

	public void setTotalDescuentos(BigDecimal totalDescuentos) {
		this.totalDescuentos = totalDescuentos;
	}

	public BigDecimal getTotal() {
		total = getTotalCargos().add(getTotalRecargos()).subtract(
				getTotalDescuentos());
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalPagos() {
		totalPagos = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getMovimientos())) {
			for (Movimiento movimiento : getMovimientos()) {
				if (movimiento instanceof MovimientoCargoAplicado
						&& ((MovimientoCargoAplicado) movimiento).getAplicado()
						&& ((MovimientoCargoAplicado) movimiento)
								.getImprimible()) {
					if (((MovimientoCargoAplicado) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.pagorecargo")))
							|| ((MovimientoCargoAplicado) movimiento)
									.getTipo()
									.getId()
									.equals(Long.valueOf(properties
											.getProperty("movimiento.tipo.pagocargo")))

							|| ((MovimientoCargoAplicado) movimiento)
									.getTipo()
									.getId()
									.equals(Long.valueOf(properties
											.getProperty("movimiento.tipo.pagodescuento")))) {
						if (movimiento.getHaber() != null) {
							totalPagos = totalPagos.add(movimiento.getHaber());
						}
					}
					if (((MovimientoCargoAplicado) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.cancelacionpagorecargo")))
							|| ((MovimientoCargoAplicado) movimiento)
									.getTipo()
									.getId()
									.equals(Long.valueOf(properties
											.getProperty("movimiento.tipo.cancelacionpagocargo")))
							|| ((MovimientoCargoAplicado) movimiento)
									.getTipo()
									.getId()
									.equals(Long.valueOf(properties
											.getProperty("movimiento.tipo.cancelacionpagodescuento")))) {
						if (movimiento.getDebe() != null) {
							totalPagos = totalPagos.subtract(movimiento
									.getDebe());
						}
					}
				} else if (movimiento instanceof MovimientoSaldo) {
                    if (movimiento.getHaber() != null && ((MovimientoSaldo) movimiento)
							.getTipo()
							.getId()
							.equals(Long.valueOf(properties
									.getProperty("movimiento.tipo.saldoafavorgenerado")))) {
                        totalPagos = totalPagos.add(movimiento.getHaber());
                    }
                }

			}
		}
		return totalPagos;
	}

	public void setTotalPagos(BigDecimal totalPagos) {
		this.totalPagos = totalPagos;
	}

	public BigDecimal getSaldoDeudor() {
		saldoDeudor = getSaldoAnterior().add(getTotal()).subtract(
				getTotalPagos()).add(getSaldoFavor()).subtract(getSaldoFavorAnterior());
		return saldoDeudor;
	}

	public void setSaldoDeudor(BigDecimal saldoDeudor) {
		this.saldoDeudor = saldoDeudor;
	}

	public BigDecimal getSaldoFavor() {
		if(saldoFavor == null) {
			saldoFavor = BigDecimal.ZERO;
		}
		return saldoFavor;
	}

	public void setSaldoFavor(BigDecimal saldoFavor) {
		this.saldoFavor = saldoFavor;
	}

	public BigDecimal getSaldoFavorAnterior() {
		if(saldoFavorAnterior == null) {
			saldoFavorAnterior = BigDecimal.ZERO;
		}
		return saldoFavorAnterior;
	}

	public void setSaldoFavorAnterior(BigDecimal saldoFavorAnterior) {
		this.saldoFavorAnterior = saldoFavorAnterior;
	}

	public Collection<Movimiento> getMovimientos() {
		Collection<Movimiento> movs = new ArrayList<Movimiento>();
		if (!CollectionUtils.isEmpty(movimientos)) {
			for (Movimiento movimiento : movimientos) {
				if (movimiento instanceof MovimientoCargoAplicado) {
					if (((MovimientoCargoAplicado) movimiento).getAplicado()
							&& ((MovimientoCargoAplicado) movimiento)
									.getImprimible()) {
						movs.add(movimiento);
					}
				} else {
					movs.add(movimiento);
				}
			}
		}
		return movs;
	}

	public void setMovimientos(Collection<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Contacto getContacto() {
		/*if (!CollectionUtils.isEmpty(getDepartamento().getContactos())) {
			for (ContactoDepartamento contacto : getDepartamento()
					.getContactos()) {
				if (contacto.obtenerPrincipal()) {
					return contacto.getContacto();
				}
			}
		}*/
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Collection<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(Collection<Cargo> cargos) {
		this.cargos = cargos;
	}

	public AvisoDeCobro getAvisoDeCobro() {
		return avisoDeCobro;
	}

	public void setAvisoDeCobro(AvisoDeCobro avisoDeCobro) {
		this.avisoDeCobro = avisoDeCobro;
	}

	public HistoricoDeCargos getHistoricoDeCargos() {
		return historicoDeCargos;
	}

	public void setHistoricoDeCargos(HistoricoDeCargos historicoDeCargos) {
		this.historicoDeCargos = historicoDeCargos;
	}

	public HistoricoDeAbonos getHistoricoDeAbonos() {
		return historicoDeAbonos;
	}

	public void setHistoricoDeAbonos(HistoricoDeAbonos historicoDeAbonos) {
		this.historicoDeAbonos = historicoDeAbonos;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFin() {
		return fin;
	}

	public void setFin(Date fin) {
		this.fin = fin;
	}

	public BigDecimal getSaldoFavorAlDia() {
		return saldoFavorAlDia;
	}

	public void setSaldoFavorAlDia(BigDecimal saldoFavorAlDia) {
		this.saldoFavorAlDia = saldoFavorAlDia;
	}
}
