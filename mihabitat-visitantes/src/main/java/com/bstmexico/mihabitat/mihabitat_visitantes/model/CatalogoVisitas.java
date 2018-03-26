package com.bstmexico.mihabitat.mihabitat_visitantes.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tcatalogos")
public class CatalogoVisitas implements Serializable {
	
	private static final long serialVersionUID = -6581717662400173493L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdCatalogo", nullable = false, unique = true)
	private Long NIdCatalogo;
	
	@Column(name = "VDescripcion",nullable = true)
	private String VDescripcion;
	
	@Column(name = "VTipo", nullable = true)
	private String VTipo;

	public Long getNIdCatalogo() {
		return NIdCatalogo;
	}

	public void setNIdCatalogo(Long nIdCatalogo) {
		NIdCatalogo = nIdCatalogo;
	}

	public String getVDescripcion() {
		return VDescripcion;
	}

	public void setVDescripcion(String vDescripcion) {
		VDescripcion = vDescripcion;
	}

	public String getVTipo() {
		return VTipo;
	}

	public void setVTipo(String vTipo) {
		VTipo = vTipo;
	}
	
	
}