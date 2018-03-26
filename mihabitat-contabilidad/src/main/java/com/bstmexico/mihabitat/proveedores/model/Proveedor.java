package com.bstmexico.mihabitat.proveedores.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "tproveedores", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"NIdCondominio", "VNombre" }) })

public class Proveedor implements Serializable {
	
	private static final long serialVersionUID = -6486582253210874741L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdProveedor", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@Column(name ="BActivo", nullable = false)
	private Boolean activo;
	
	@NotNull
	@Column(name ="BEsEmpleado", nullable = false)
	private Boolean esEmpleado;

	@Column(name ="VRfc", nullable = true)
	private String rfc;
	
	@Column(name ="VRazonSocial", nullable = true)
	private String razonSocial;
	
	@Column(name ="IDiasCredito", nullable = true)
	private Integer diasCredito;
	
	@NotNull
	@Size(min = 1, max = 128)
	@Column(length = 128, name = "VNombre", nullable = false)
	private String nombre;
	
	@NotNull
	@JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Condominio condominio;
	
	@JoinColumn(name = "NIdProveedor", nullable = false, referencedColumnName = "NIdProveedor")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<ContactoProveedor> contactos;
	
	@NotNull
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tproveedoresgiros", joinColumns = { @JoinColumn(name = "NIdProveedor", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "NIdCatalogo", nullable = false) })
	private Collection<CatalogoGiro> giros;
	
	@NotNull
	@JoinColumn(name = "NIdCuenta", nullable = false, referencedColumnName = "NIdCuenta")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Cuenta cuenta;
	
	@JoinColumn(name = "NIdDireccion", referencedColumnName = "NIdDireccion")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private DireccionProveedor direccion;

	@NotNull
	@Column(name ="BTipoPersona", nullable = false)
	private Boolean tipoPersona;

	@NotNull
	@Column(name ="BEsFacturable", nullable = false)
	private Boolean esFacturable;
	
	
	public Proveedor() {
		
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getEsEmpleado() {
		return esEmpleado;
	}

	public void setEsEmpleado(Boolean esEmpleado) {
		this.esEmpleado = esEmpleado;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {this.rfc = rfc.toUpperCase();}

	public String getRazonSocial() {return razonSocial; }

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Integer getDiasCredito() {
		return diasCredito;
	}

	public void setDiasCredito(Integer diasCredito) {
		this.diasCredito = diasCredito;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}	

	public Collection<CatalogoGiro> getGiros() {
		return giros;
	}

	public void setGiros(Collection<CatalogoGiro> giros) {
		this.giros = giros;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Collection<ContactoProveedor> getContactos() {
		return contactos;
	}

	public void setContactos(Collection<ContactoProveedor> contactos) {
		this.contactos = contactos;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public DireccionProveedor getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionProveedor direccion) {
		this.direccion = direccion;
	}

	public Boolean getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(Boolean tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public Boolean getEsFacturable() {
		return esFacturable;
	}

	public void setEsFacturable(Boolean esFacturable) {
		this.esFacturable = esFacturable;
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
		Proveedor other = (Proveedor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}
