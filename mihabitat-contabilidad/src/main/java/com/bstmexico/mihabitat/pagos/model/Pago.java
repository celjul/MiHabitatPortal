package com.bstmexico.mihabitat.pagos.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tpagos",
		uniqueConstraints = @UniqueConstraint(columnNames = {"NIdCondominio", "NFolio"}))
public class Pago implements Serializable {

	private static final long serialVersionUID = -8296778534722219348L;

	@NotNull
	@JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Condominio condominio;

	@NotNull
	@JoinColumn(name = "NIdContacto", referencedColumnName = "NIdContacto", nullable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Contacto contacto;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "longblob", name = "BComprobante")
	private byte[] comprobante;

	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta cuenta;

	@JsonIgnoreProperties({ "pago" })
	@LazyCollection(LazyCollectionOption.FALSE)
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "NIdPago", nullable = false, referencedColumnName = "NIdPago")
	private Collection<EstatusPago> estatus;

	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@NotNull
	@Column(columnDefinition = "date", name = "DFecha", nullable = false)
	private Date fecha;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdPago", nullable = false, unique = true)
	private Long id;

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "NFolio", nullable = true)
	private Long folio;

	@NotNull
	@JoinColumn(name = "NIdMetodoPago", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoMetodoPago metodoPago;

	@NotNull
	@Min(value = 0)
	@Column(name = "DMonto", precision = 9, scale = 2, nullable = false)
	private BigDecimal monto;

	/*@JoinColumn(name = "NIdPago", nullable = true, referencedColumnName = "NIdPago")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<MovimientoCargoAplicado> movimientos;*/

	@JoinColumn(name = "NIdPago", referencedColumnName = "NIdPago")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<PagoDepartamento> pagosDepartamento;

	@Size(max = 32)
	@Column(name = "VReferencia", length = 32)
	private String referencia;

	public Pago() {
		super();
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public byte[] getComprobante() {
		return comprobante;
	}

	public void setComprobante(byte[] comprobante) {
		this.comprobante = comprobante;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Collection<EstatusPago> getEstatus() {
		return estatus;
	}

	public void setEstatus(Collection<EstatusPago> estatus) {
		this.estatus = estatus;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CatalogoMetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(CatalogoMetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	@Transient
	public EstatusPago getUltimoEstatus() {
		EstatusPago apTmp = null;
		if(!CollectionUtils.isEmpty(getEstatus())) {
			for (EstatusPago ep : getEstatus()) {
				apTmp = ep;
			}
		}
		return apTmp;
	}

	@Transient
	public String getStringComentarios() {
		StringBuffer strComentarios = new StringBuffer("");
		if(!CollectionUtils.isEmpty(estatus)) {
			for(EstatusPago ep : estatus) {
				if(ep.getComentario() != null) {
					strComentarios.append(ep.getUsuario().getUser() + ": " + ep.getComentario() + "; ");
				}
			}
		}
		return strComentarios.toString();
	}

	@Transient
	public String getStringComentariosSinUsuario() {
		StringBuffer strComentarios = new StringBuffer("");
		if(!CollectionUtils.isEmpty(estatus)) {
			for(EstatusPago ep : estatus) {
				if(ep.getComentario() != null) {
					strComentarios.append( ep.getComentario() + "; ");
				}
			}
		}
		return strComentarios.toString();
	}
	@Transient
	public String getStringDepartamentos() {
		StringBuffer strDepartamentos = new StringBuffer("");
		if(!CollectionUtils.isEmpty(pagosDepartamento)) {
			for(PagoDepartamento pd: pagosDepartamento) {
					strDepartamentos.append(pd.getDepartamento().getNombre());
					if(!CollectionUtils.isEmpty(pd.getDepartamento().getGrupos())) {
						strDepartamentos.append(" - ");
						for (GrupoCondominio gp : pd.getDepartamento().getGrupos()) {
							strDepartamentos.append(gp.getDescripcion() + ", ");
						}
						strDepartamentos.delete(strDepartamentos.length()-2, strDepartamentos.length());
					}
				strDepartamentos.append("; ");
			}
		}
		return strDepartamentos.toString();
	}

/*	public Collection<MovimientoCargoAplicado> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(Collection<MovimientoCargoAplicado> movimientos) {
		this.movimientos = movimientos;
	}*/

	public Collection<PagoDepartamento> getPagosDepartamento() {
		return pagosDepartamento;
	}

	public void setPagosDepartamento(Collection<PagoDepartamento> pagosDepartamento) {
		this.pagosDepartamento = pagosDepartamento;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Long getFolio() {
		return folio;
	}

	public void setFolio(Long folio) {
		this.folio = folio;
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
		Pago other = (Pago) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*public void addMovimiento(MovimientoCargoAplicado movimiento) {
		if (this.movimientos == null) {
			this.movimientos = new ArrayList<MovimientoCargoAplicado>();
		}
		this.movimientos.add(movimiento);
	}*/
}
