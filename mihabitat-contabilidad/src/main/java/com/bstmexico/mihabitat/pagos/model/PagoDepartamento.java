package com.bstmexico.mihabitat.pagos.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author Zo� Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
@Entity
@Table(name = "tpagosdepartamento")
public class PagoDepartamento implements Serializable {

	private static final long serialVersionUID = -8296778535622219348L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdPagoDepartamento", nullable = false, unique = true)
	private Long id;

	@JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Condominio condominio;

	@JsonIgnoreProperties(value = { "pagosDepartamento" })
	@JoinColumn(name = "NIdPago", referencedColumnName = "NIdPago", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Pago pagoCondomino;

	@NotNull
	@JoinColumn(name = "NIdDepartamento", referencedColumnName = "NIdDepartamento", nullable = false, updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Departamento departamento;

	@NotNull
	@Min(value = 0)
	@Column(name = "DMonto", precision = 9, scale = 2, nullable = false)
	private BigDecimal monto;

	@JoinColumn(name = "NIdPagoDepartamento", nullable = true, referencedColumnName = "NIdPagoDepartamento")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Collection<MovimientoCargoAplicado> movimientos;

	public PagoDepartamento() {
		super();
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Pago getPagoCondomino() {
		return pagoCondomino;
	}

	public void setPagoCondomino(Pago pagoCondomino) {
		this.pagoCondomino = pagoCondomino;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Collection<MovimientoCargoAplicado> getMovimientos() {
		if(movimientos == null){
			movimientos = new ArrayList<>();
		}
		return movimientos;
	}

	public void setMovimientos(Collection<MovimientoCargoAplicado> movimientos) {
		this.movimientos = movimientos;
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
		PagoDepartamento other = (PagoDepartamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public void addMovimiento(MovimientoCargoAplicado movimiento) {
		if (this.movimientos == null) {
			this.movimientos = new ArrayList<MovimientoCargoAplicado>();
		}
		this.movimientos.add(movimiento);
	}

	@Transient
	public BigDecimal getAplicadoCargos() {
		BigDecimal aplicadoCargos = BigDecimal.ZERO;
		for(MovimientoCargoAplicado mca : movimientos) {
			if(mca.getHaber() != null) {
				aplicadoCargos = aplicadoCargos.add(mca.getHaber());
			}
			if(mca.getDebe() != null) {
				aplicadoCargos = aplicadoCargos.subtract(mca.getDebe());
			}
		}
		return aplicadoCargos;
	}
}
