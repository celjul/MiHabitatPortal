package com.bstmexico.mihabitat.proveedores.model;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiImpuesto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tcfdi")
public class Cfdi implements Serializable, Comparable<Cfdi> {

	private static final long serialVersionUID = -6486582211010874741L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdCfdi", nullable = false, unique = true)
	private Long id;

	@NotNull
	@JoinColumn(name = "NIdProveedor", referencedColumnName = "NIdProveedor", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Proveedor proveedor;

	@NotNull
	@JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Condominio condominio;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFechaEmision", nullable = false)
	private Date fechaEmision;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFechaVencimiento", nullable = false)
	private Date fechaVencimiento;

	@NotNull
	@Column(name = "VRfc", nullable = false)
	private String rfc;

	@NotNull
	@Column(name = "VEmisor")
	private String emisor;

	@NotNull
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "NIdCfdi", referencedColumnName = "NIdCfdi", nullable = false)
	private Collection<DetalleFactura> conceptos;

	@NotNull
	@Column(name = "VUuid", nullable = false, unique = true)
	private String uuid;

	@NotNull
	@Min(value = 0)
	@Column(name = "DImpuestoTrasladado", precision = 9, scale = 2, nullable = true)
	private BigDecimal impuestoTrasladado;

	@NotNull
	@Min(value = 0)
	@Column(name = "DImpuestoRetenido", precision = 9, scale = 2, nullable = true)
	private BigDecimal impuestoRetenido;

	@NotNull
	@Min(value = 0)
	@Column(name = "DTotal", precision = 9, scale = 2, nullable = true)
	private BigDecimal total;

	@Column(name = "VTipo", nullable = true)
	private String tipo;

	@JoinColumn(name = "NIdArchivo", referencedColumnName = "NIdArchivo")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArchivoCfdi archivo;

	@Column(name = "VFormaPago", nullable = true)
	private String formadepago;

	@Column(name = "VMetodoPago", nullable = true)
	private String metododepago;

	@Column(name = "VCondicionPago", nullable = true)
	private String condiciondepago;

	@JsonIgnoreProperties(value = { "cfdi" })
	@OneToOne(mappedBy = "cfdi", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private MovimientoCfdiImpuesto movimientoImpuestoRetenido;

	@JsonIgnoreProperties(value = { "cfdi" })
	@OneToOne(mappedBy = "cfdi", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private MovimientoCfdiImpuesto movimientoImpuestoTrasladado;

	@JsonIgnore
	@Transient
	private static Properties configuration;

	public Cfdi() {
		super();
		try {
			configuration = PropertiesLoaderUtils
					.loadAllProperties("configuration.properties");
		} catch (IOException e) {
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public Collection<DetalleFactura> getConceptos() {
		return conceptos;
	}

	public void setConceptos(Collection<DetalleFactura> conceptos) {
		this.conceptos = conceptos;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid.toUpperCase();
	}

	public BigDecimal getImpuestoTrasladado() {
		return impuestoTrasladado;
	}

	public void setImpuestoTrasladado(BigDecimal impuestoTrasladado) {
		this.impuestoTrasladado = impuestoTrasladado;
	}

	public BigDecimal getImpuestoRetenido() {
		return impuestoRetenido;
	}

	public void setImpuestoRetenido(BigDecimal impuestoRetenido) {
		this.impuestoRetenido = impuestoRetenido;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public ArchivoCfdi getArchivo() {
		return archivo;
	}

	public void setArchivo(ArchivoCfdi archivo) {
		this.archivo = archivo;
	}

	public String getFormadepago() {
		return formadepago;
	}

	public void setFormadepago(String formadepago) {
		this.formadepago = formadepago;
	}

	public String getMetododepago() {
		return metododepago;
	}

	public void setMetododepago(String metododepago) {
		this.metododepago = metododepago;
	}

	public String getCondiciondepago() {
		return condiciondepago;
	}

	public void setCondiciondepago(String condiciondepago) {
		this.condiciondepago = condiciondepago;
	}

	public MovimientoCfdiImpuesto getMovimientoImpuestoRetenido() {
		return movimientoImpuestoRetenido;
	}

	public void setMovimientoImpuestoRetenido(
			MovimientoCfdiImpuesto movimientoImpuestoRetenido) {
		this.movimientoImpuestoRetenido = movimientoImpuestoRetenido;
	}

	public MovimientoCfdiImpuesto getMovimientoImpuestoTrasladado() {
		return movimientoImpuestoTrasladado;
	}

	public void setMovimientoImpuestoTrasladado(
			MovimientoCfdiImpuesto movimientoImpuestoTrasladado) {
		this.movimientoImpuestoTrasladado = movimientoImpuestoTrasladado;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Cfdi cfdi = (Cfdi) o;

		return !(id != null ? !id.equals(cfdi.id) : cfdi.id != null);

	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	// /////////////////////////////////////////////////////////////////////////
	// CAMPOS PARA CALCULOS

	@Transient
	private BigDecimal pagado;

	@Transient
	private BigDecimal pendiente;

	public BigDecimal getPagado() {
		pagado = getMovimientoImpuestoTrasladado().getPagado();
		if (!CollectionUtils.isEmpty(getConceptos())) {
			for (DetalleFactura detalle : getConceptos()) {
				pagado = detalle.getMovimientoCfdi().getPagado();
			}
		}
		return pagado;
	}

	public BigDecimal getPendiente() {
		pendiente = getTotal().subtract(getPagado());
		return pendiente;
	}
	
	@Override
	public int compareTo(Cfdi o) {
		return getFechaEmision().compareTo(o.getFechaEmision());
	}
}