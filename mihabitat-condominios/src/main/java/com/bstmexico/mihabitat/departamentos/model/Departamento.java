package com.bstmexico.mihabitat.departamentos.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tdepartamentos", uniqueConstraints = { 
		@UniqueConstraint(columnNames = {"NIdCondominio", "VNombre" }) })
public class Departamento implements Serializable {

	private static final long serialVersionUID = -7577682341577773991L;

	@NotNull
	@Column(name = "BActivo", nullable = false)
	private Boolean activo;

	@NotNull
	@JoinColumn(name = "NIdCondominio", nullable = false, 
		referencedColumnName = "NIdCondominio")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Condominio condominio;

	@Valid
	@OneToMany(fetch = FetchType.LAZY, 
		mappedBy = "id.departamento", orphanRemoval = true)
	private Collection<ContactoDepartamento> contactos;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tdepartamentogrupos", joinColumns = { 
			@JoinColumn(name = "NIdDepartamento", nullable = false) }, 
			inverseJoinColumns = { 
				@JoinColumn(name = "NIdGrupo", nullable = false) })
	private Collection<GrupoCondominio> grupos;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdDepartamento", nullable = false, unique = true)
	private Long id;

	@NotNull
	@JoinColumn(name = "NIdMantenimiento", nullable = false, 
		referencedColumnName = "NIdMantenimiento")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private MantenimientoCondominio mantenimiento;

	@NotNull
	@Size(min = 1, max = 128)
	@Column(length = 128, name = "VNombre", nullable = false)
	private String nombre;

	@Size(max = 512)
	@Column(name = "VObservaciones", nullable = true)
	private String observaciones;

	@Size(max = 512)
	@Column(name = "VMensajeCobro", nullable = true)
	private String mensajeCobro;

	@Column(name = "NUnidadIndiviso", nullable = true)
	private BigDecimal unidadIndiviso;

	public Departamento() {
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Collection<ContactoDepartamento> getContactos() {
		return contactos;
	}

	public void setContactos(Collection<ContactoDepartamento> contactos) {
		this.contactos = contactos;
	}

	public Collection<GrupoCondominio> getGrupos() {
		return grupos;
	}

	public void setGrupos(Collection<GrupoCondominio> grupos) {
		this.grupos = grupos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MantenimientoCondominio getMantenimiento() {
		return mantenimiento;
	}

	public void setMantenimiento(MantenimientoCondominio mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getMensajeCobro() {
		return mensajeCobro;
	}

	public void setMensajeCobro(String mensajeCobro) {
		this.mensajeCobro = mensajeCobro;
	}

	@Transient
	public String getStringGrupos() {
		StringBuffer sb = new StringBuffer("");
		if(grupos != null && grupos.size() > 0) {
			for(GrupoCondominio gpo : grupos) {
				sb.append(gpo.getDescripcion() + ", ");
			}
			sb.delete(sb.lastIndexOf(", "), sb.length());
		}
		return sb.toString();
	}

	public void addContacto(ContactoDepartamento contacto) {
		if (this.contactos == null) {
			this.contactos = new ArrayList<ContactoDepartamento>();
		}
		this.contactos.add(contacto);
	}

	@Transient
	public ContactoDepartamento obtenerPrincipal() {
		ContactoDepartamento contactoDepartamento = null;
		if (!CollectionUtils.isEmpty(this.getContactos())) {
			for (ContactoDepartamento contacto : this
					.getContactos()) {
				contactoDepartamento = contacto;
				if (contacto.getPrincipal() && !CollectionUtils.isEmpty(contacto.getContacto().getEmails())) {
					contactoDepartamento = contacto;
					break;
				}
			}
		}
		return contactoDepartamento;
	}
	public BigDecimal getUnidadIndiviso() { return unidadIndiviso; }

	public void setUnidadIndiviso(BigDecimal unidadIndiviso) { this.unidadIndiviso = unidadIndiviso; }
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
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("[");
		buffer.append("id : ").append(this.id).append(", ");
		buffer.append("nombre : ").append(this.nombre);
		buffer.append("]");
		
		return buffer.toString();
	}
}
