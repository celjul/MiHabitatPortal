package com.bstmexico.mihabitat.pagos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "testatuspago")
public class EstatusPago implements Serializable {

	private static final long serialVersionUID = -4909078662792805133L;

	@Size(max = 256)
	@Column(name = "VComentario", length = 256)
	private String comentario;

	@NotNull
	@JoinColumn(name = "NIdEstatus", referencedColumnName = "NIdCatalogo")
	@ManyToOne(fetch = FetchType.EAGER)
	private CatalogoEstatusPago estatus;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdEstatusPago", nullable = false, unique = true)
	private Long id;

	@JoinColumn(name = "NIdUsuario", referencedColumnName = "NIdUsuario", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	@JsonIgnoreProperties(value = { "estatus" })
	@JoinColumn(name = "NIdPago", nullable = false , referencedColumnName = "NIdPago", updatable = false, insertable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Pago pago;

	public EstatusPago() {
		super();
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public CatalogoEstatusPago getEstatus() {
		return estatus;
	}

	public void setEstatus(CatalogoEstatusPago estatus) {
		this.estatus = estatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
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
		EstatusPago other = (EstatusPago) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
