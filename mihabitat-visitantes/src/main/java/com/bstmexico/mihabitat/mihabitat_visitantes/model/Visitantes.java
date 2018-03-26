package com.bstmexico.mihabitat.mihabitat_visitantes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

@Entity
@Table(name = "tvisitas")
public class Visitantes implements Serializable {
	
private static final long serialVersionUID = -6578717662400663493L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdVisitas", nullable = false, unique = true)
	private Long nIdVisitas;
	
	
	@Column(name = "VNombre")
	private String vNombre;
	
	@Column(name = "VApPaterno")
	private String vApPaterno;
		
	@Column(name = "VApMaterno")
	private String vApMaterno;
	
	@Column(name = "DFechaEntrada")
	private String dFechaEntrada;
	
	@Column(name = "DFechaSalida" , nullable= true)
	private String dFechaSalida;
	
	@Column(name = "VPlacas")
	private String vPlacas;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = CatalogoVisitas.class)
	@JoinColumn(name = "NIdCatalogo", nullable = false, unique = true)
	private CatalogoVisitas nIdCatalogo;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Departamento.class)
	@JoinColumn(name = "NIdDepartamento", nullable = false, unique = true)
	private Departamento nIdDepartamento;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Condominio.class)
	@JoinColumn(name = "NIdCondominio", nullable = false, unique = true)
	private Condominio nIdCondominio;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Usuario.class)
	@JoinColumn(name = "NIdUsuario", nullable = false, unique = true)
	private Usuario nIdUsuario;

	public Long getIdArrendador() {
		return nIdVisitas;
	}

	public void setIdArrendador(Long idArrendador) {
		this.nIdVisitas = idArrendador;
	}

	public String getNombre() {
		return vNombre;
	}

	public void setNombre(String nombre) {
		this.vNombre = nombre;
	}

	public String getApPaterno() {
		return vApPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.vApPaterno = apPaterno;
	}

	public String getApMaterno() {
		return vApMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.vApMaterno = apMaterno;
	}

	public String getFechaEntrada() {
		return dFechaEntrada;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.dFechaEntrada = fechaEntrada;
	}

	public String getFechaSalida() {
		return dFechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.dFechaSalida = fechaSalida;
	}

	public String getPlacas() {
		return vPlacas;
	}

	public void setPlacas(String placas) {
		this.vPlacas = placas;
	}

	public CatalogoVisitas getIdStatus() {
		return nIdCatalogo;
	}

	public void setIdStatus(CatalogoVisitas idStatus) {
		this.nIdCatalogo = idStatus;
	}

	public Departamento getDepartamento() {
		return nIdDepartamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.nIdDepartamento = departamento;
	}

	public Condominio getCondominio() {
		return nIdCondominio;
	}

	public void setCondominio(Condominio condominio) {
		this.nIdCondominio = condominio;
	}

	public Usuario getAdministrador() {
		return nIdUsuario;
	}

	public void setAdministrador(Usuario administrador) {
		this.nIdUsuario = administrador;
	}	
	
}