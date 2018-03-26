package com.bstmexico.mihabitat.movimientos.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("impuesto_cfdi")
public class MovimientoCfdiImpuesto extends Movimiento {

	private static final long serialVersionUID = -8119770111890060722L;


	@NotNull
	@Column(name = "BCancelado")
	private Boolean cancelado;

	@JsonIgnoreProperties(value = {"movimientoImpuestoRetenido", "movimientoImpuestoTrasladado"})
	@JoinColumn(name = "NIdCfdi", referencedColumnName = "NIdCfdi", updatable = false, nullable = true)
	@OneToOne(fetch = FetchType.LAZY)
	private Cfdi cfdi;

	@NotNull
	@JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoTipoMovimiento tipo;

	@JoinColumn(name = "NIdMovimientoCfdi", nullable = true, referencedColumnName = "NIdMovimiento")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<MovimientoCfdiAplicado> aplicados;


	@JsonIgnore
	@Transient
	private static Properties configuration;

	public MovimientoCfdiImpuesto() {
		super();
		try {
			configuration = PropertiesLoaderUtils
					.loadAllProperties("configuration.properties");
		} catch (IOException e) {
		}
	}


	public Boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Cfdi getCfdi() {
		return cfdi;
	}

	public void setCfdi(Cfdi cfdi) {
		this.cfdi = cfdi;
	}

	public CatalogoTipoMovimiento getTipo() {
		return tipo;
	}

	public void setTipo(CatalogoTipoMovimiento tipo) {
		this.tipo = tipo;
	}

	public Collection<MovimientoCfdiAplicado> getAplicados() {
		return aplicados;
	}

	public void setAplicados(Collection<MovimientoCfdiAplicado> aplicados) {
		this.aplicados = aplicados;
	}
	
	@Transient
	private Set<MovimientoCfdiAplicado> pagos;

	@Transient
	private BigDecimal pagado;
	
	@Transient
	private BigDecimal pendiente;

	public Set<MovimientoCfdiAplicado> getPagos() {
		pagos = new HashSet<MovimientoCfdiAplicado>();
		if (!CollectionUtils.isEmpty(getAplicados())) {
			for (MovimientoCfdiAplicado aplicado : getAplicados()) {
				if (aplicado
						.getTipo()
						.getId()
						.equals(Long.valueOf(configuration
								.getProperty("pagocfdi")))) {
					pagos.add(aplicado);
				}
			}
		}
		return pagos;
	}

	public BigDecimal getPagado() {
		pagado = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(getPagos())) {
			for (MovimientoCfdiAplicado pago : getPagos()) {
				if (pago.getAplicado()) {
					if (pago.getHaber() != null) {
						pagado = pagado.subtract(pago.getHaber());
					} else if (pago.getDebe() != null) {
						pagado = pagado.add(pago.getDebe());
					}
				}
			}
		}
		return pagado;
	}
	
	public BigDecimal getPendiente() {
		pendiente = (getHaber() != null ? getHaber() : BigDecimal.ZERO)
				.subtract(getPagado());
		return pendiente;
	}
}
