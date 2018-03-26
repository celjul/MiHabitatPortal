package com.bstmexico.mihabitat.cuentas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.presupuestos.model.Presupuesto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@Entity
@Table(name = "tcuentas")
public class Cuenta implements Serializable {

	private static final long serialVersionUID = 8867033753764336047L;

	@NotNull
	@Column(name = "BActiva", nullable = false)
	private Boolean activo;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "NIdAgrupadorSat", nullable = true, referencedColumnName = "NIdAgrupadorSat")
	private AgrupadorSat agrupadorSat;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "NIdBancoSat", nullable = true, referencedColumnName = "NIdBancoSat")
	private BancoSat bancoSat;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
	private Condominio condominio;

	@NotNull
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
	@Column(columnDefinition = "Date", name = "DFecha", nullable = false)
	private Date fecha;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdCuenta", nullable = false, unique = true)
	private Long id;

	@Min(value = 0)
	@NotNull
	@Column(name = "NInicial", nullable = false, precision = 9, scale = 2)
	private BigDecimal inicial;

	@NotNull
	@Size(min = 1, max = 128)
	@Column(length = 128, name = "VNombre", nullable = false, unique = false)
	private String nombre;

	@NotNull
	@Size(min = 4, max = 4)
	@Column(name = "VNoCuenta", nullable = false, length = 4)
	private String numero;

	@Size(min = 4, max = 4)
	@Column(name = "VNoCuentaHija", nullable = true, length = 4)
	private String numeroHija;

	@Size(min = 4, max = 4)
	@Column(name = "VNoCuentaNieto", nullable = true, length = 4)
	private String numeroNieto;

	@Size(min = 4, max = 4)
	@Column(name = "VNoCuentaBis", nullable = true, length = 4)
	private String numeroBis;

	@JoinColumn(name = "NIdCuentaPadre", nullable = true, referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Cuenta padre;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "padre")
	private Collection<Cuenta> cuentasHijas;

	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Presupuesto> presupuestos;

	public Cuenta() {
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public AgrupadorSat getAgrupadorSat() {
		return agrupadorSat;
	}

	public void setAgrupadorSat(AgrupadorSat agrupadorSat) {
		this.agrupadorSat = agrupadorSat;
	}

	public BancoSat getBancoSat() {
		return bancoSat;
	}

	public void setBancoSat(BancoSat bancoSat) {
		this.bancoSat = bancoSat;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
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

	public BigDecimal getInicial() {
		return inicial;
	}

	public void setInicial(BigDecimal inicial) {
		this.inicial = inicial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroHija() {
		return numeroHija;
	}

	public void setNumeroHija(String numeroHija) {
		this.numeroHija = numeroHija;
	}

	public String getNumeroNieto() {
		return numeroNieto;
	}

	public void setNumeroNieto(String numeroNieto) {
		this.numeroNieto = numeroNieto;
	}

	public String getNumeroBis() {
		return numeroBis;
	}

	public void setNumeroBis(String numeroBis) {
		this.numeroBis = numeroBis;
	}

	public Cuenta getPadre() {
		return padre;
	}

	public void setPadre(Cuenta padre) {
		this.padre = padre;
	}

	public Collection<Cuenta> getCuentasHijas() {
		return cuentasHijas;
	}

	public void setCuentasHijas(Collection<Cuenta> cuentasHijas) {
		this.cuentasHijas = cuentasHijas;
	}

	public Collection<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public void setPresupuestos(Collection<Presupuesto> presupuestos) {
		this.presupuestos = presupuestos;
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
		Cuenta other = (Cuenta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}