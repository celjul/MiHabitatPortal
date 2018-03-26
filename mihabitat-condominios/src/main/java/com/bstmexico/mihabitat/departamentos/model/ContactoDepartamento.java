package com.bstmexico.mihabitat.departamentos.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.bstmexico.mihabitat.condominios.factory.ConfiguracionNotificacionFactory;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tdepartamentocontactos")
@AssociationOverrides({
		@AssociationOverride(name = "id.contacto", joinColumns = 
				@JoinColumn(name = "NIdPersona")),
		@AssociationOverride(name = "id.departamento", joinColumns = 
				@JoinColumn(name = "NIdDepartamento")) })
public class ContactoDepartamento implements Serializable {

	private static final long serialVersionUID = 2467683185271659574L;

	@EmbeddedId
	private ContactoDepartamentoId id = new ContactoDepartamentoId();

	@NotNull
	@Column(name = "BHabitante", nullable = false)
	private Boolean habitante;

	@NotNull
	@Column(name = "BPrincipal", nullable = false)
	private Boolean principal;

	@NotNull
	@Column(name = "BPropietario", nullable = false)
	private Boolean propietario;

	@JsonIgnore
	@OneToOne(mappedBy = "contactoDepartamento", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	private ConfiguracionNotificacionContacto configuracionNotificacionContacto;

	public ContactoDepartamento() {
	}

	public ContactoDepartamentoId getId() {
		return id;
	}

	public void setId(ContactoDepartamentoId id) {
		this.id = id;
	}
	
	@Transient
	public Contacto getContacto() {
		return getId().getContacto();
	}
	
	public void setContacto(Contacto contacto) {
		getId().setContacto(contacto);
	}

	@Transient
	public Departamento getDepartamento() {
		return getId().getDepartamento();
	}

	public void setDepartamento(Departamento departamento) {
		getId().setDepartamento(departamento);
	}

	public Boolean getHabitante() {
		return habitante;
	}

	public void setHabitante(Boolean habitante) {
		this.habitante = habitante;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public Boolean getPropietario() {
		return propietario;
	}

	public void setPropietario(Boolean propietario) {
		this.propietario = propietario;
	}

	public ConfiguracionNotificacionContacto getConfiguracionNotificacionContacto() {
		if(this.configuracionNotificacionContacto == null){
			this.configuracionNotificacionContacto = ConfiguracionNotificacionFactory.newInstance();
		}
		return configuracionNotificacionContacto;
	}

	public void setConfiguracionNotificacionContacto(ConfiguracionNotificacionContacto configuracionNotificacionContacto) {
		this.configuracionNotificacionContacto = configuracionNotificacionContacto;
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
		ContactoDepartamento other = (ContactoDepartamento) obj;
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
		
		buffer.append("contacto : ").append(this.getContacto()).append(", ");
		buffer.append("departamento : ").append(this.getDepartamento()).append(", ");
		buffer.append("habitante : ").append(this.habitante).append(", ");
		buffer.append("principal : ").append(this.principal).append(", ");
		buffer.append("propietario : ").append(this.propietario);
		buffer.append("]");
		
		return buffer.toString();
	}
}
