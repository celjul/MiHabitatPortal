package com.bstmexico.mihabitat.mihabitat_arrendamiento.model;

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
@Table(name = "tarrendador")
public class Arrendatario implements Serializable {

	private static final long serialVersionUID = -6581717662400663493L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idArrendador" ,nullable = false, unique = true )
	private Long idArrendador;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "apPaterno", nullable = false)
	private String apPaterno;
		
	@Column(name = "apMaterno" )
	private String apMaterno;
	
	@Column(name = "fecharegistro", nullable = false)
	private String fechaRegistro;
	
	@Column(name = "fechaentrada", nullable = false)
	private String fechaEntrada;
	
	@Column(name = "fechasalida", nullable = false)
	private String fechaSalida;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Condominio.class)
	@JoinColumn(name = "NIdCondominio", nullable = false)
	private Condominio condominio;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = CatalogoArrendamiento.class)
	@JoinColumn(name = "NIdCatalogo", nullable = false)
	private CatalogoArrendamiento idStatus;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Departamento.class)
	@JoinColumn(name = "NIdDepartamento", nullable = false)
	private Departamento departamento;
	
	@Column(name = "numAdultos")
	private int numAdultos;
	
	@Column(name = "numNinos")
	private int numNinos;
	

	@Column(name = "placas")
	private String placas;
	
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = Usuario.class)
	@JoinColumn(name = "NIdUsuario", nullable = false)
	private Usuario administrador;	



	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	public Usuario getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Usuario administrador) {
		this.administrador = administrador;
	}

	public Long getIdArrendador() {
		return idArrendador;
	}

	public void setIdArrendador(Long idArrendador) {
		this.idArrendador = idArrendador;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public String getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public int getNumAdultos() {
		return numAdultos;
	}

	public void setNumAdultos(int numAdultos) {
		this.numAdultos = numAdultos;
	}

	public int getNumNinos() {
		return numNinos;
	}

	public void setNumNinos(int numNinos) {
		this.numNinos = numNinos;
	}

	public CatalogoArrendamiento getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(CatalogoArrendamiento idStatus) {
		this.idStatus = idStatus;
	}

	public String getPlacas() {
		return placas;
	}

	public void setPlacas(String placas) {
		this.placas = placas;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	}