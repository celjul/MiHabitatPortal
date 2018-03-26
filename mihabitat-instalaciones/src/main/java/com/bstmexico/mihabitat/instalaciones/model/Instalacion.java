/**
 * 
 */
package com.bstmexico.mihabitat.instalaciones.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

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
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 *
 */

@Entity
@Table(name = "tinstalaciones")
public class Instalacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3815819702550570986L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdInstalacion", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Condominio condominio;
	
	@NotNull
	@Size(min = 1, max = 128)
	@Column(length = 128, name = "VNombre", nullable = false)
	private String nombre;
	
	@Size(max = 512)
	@Column(length = 512, name = "VDescripcion")
	private String descripcion;
	
	@Min(value = 0)
	@NotNull
	@Column(name = "NMaximoReservaciones", nullable = false, unique = false)
	private Integer maximoReservaciones;
	
	@Size(max = 2048)
	@Column(length = 2048, name = "VReglamento")
	private String reglamento;
	
	@Min(value = 0)
	@Column(name = "DCosto", precision = 9, scale = 2)
	private BigDecimal costo;
	
	@JoinColumn(name = "NIdImagen", referencedColumnName = "NIdArchivo")
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private ArchivoImagenInstalacion imagen;
	
	@NotNull
	@Column(name = "BActivo", nullable = false)
	private Boolean activo;
	
	@JoinColumn(name = "NIdUnidad", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoUnidadInstalacion unidad;

	@JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.EAGER)
	private Cuenta cuenta;
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "NIdInstalacion", nullable = false, referencedColumnName = "NIdInstalacion")
	private Collection<Disponibilidad> disponibilidades;
	
	
	@JoinColumn(name = "NIdInstalacion", referencedColumnName = "NIdInstalacion")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Reservacion> reservaciones;
	
	@NotNull
	@Column(name = "BCobro", nullable = false)
	private Boolean cobroAutomatico;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the condominio
	 */
	public Condominio getCondominio() {
		return condominio;
	}

	/**
	 * @param condominio the condominio to set
	 */
	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the maximoReservaciones
	 */
	public Integer getMaximoReservaciones() {
		return maximoReservaciones;
	}

	/**
	 * @param maximoReservaciones the maximoReservaciones to set
	 */
	public void setMaximoReservaciones(Integer maximoReservaciones) {
		this.maximoReservaciones = maximoReservaciones;
	}

	/**
	 * @return the reglamento
	 */
	public String getReglamento() {
		return reglamento;
	}

	/**
	 * @param reglamento the reglamento to set
	 */
	public void setReglamento(String reglamento) {
		this.reglamento = reglamento;
	}

	/**
	 * @return the costo
	 */
	public BigDecimal getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	
	
	public ArchivoImagenInstalacion getImagen() {
		return imagen;
	}

	public void setImagen(ArchivoImagenInstalacion imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the unidad
	 */
	public CatalogoUnidadInstalacion getUnidad() {
		return unidad;
	}

	/**
	 * @param unidad the unidad to set
	 */
	public void setUnidad(CatalogoUnidadInstalacion unidad) {
		this.unidad = unidad;
	}

	/**
	 * @return the cuenta
	 */
	public Cuenta getCuenta() {
		return cuenta;
	}

	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	/**
	 * @return the disponibilidades
	 */
	public Collection<Disponibilidad> getDisponibilidades() {
		return disponibilidades;
	}

	/**
	 * @param disponibilidades the disponibilidades to set
	 */
	public void setDisponibilidades(Collection<Disponibilidad> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}

	/**
	 * @return the reservaciones
	 */
	public Collection<Reservacion> getReservaciones() {
		return reservaciones;
	}

	/**
	 * @param reservaciones the reservaciones to set
	 */
	public void setReservaciones(Collection<Reservacion> reservaciones) {
		this.reservaciones = reservaciones;
	}

	public Boolean getCobroAutomatico() {
		return cobroAutomatico;
	}

	public void setCobroAutomatico(Boolean cobroAutomatico) {
		this.cobroAutomatico = cobroAutomatico;
	}
	
	
	
}
