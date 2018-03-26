package com.bstmexico.mihabitat.pagostdc.srpago.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.bstmexico.mihabitat.condominios.model.PagoTDC;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 07-11-2017
 * @version 1.0
 *
 */
@Entity
@Table(name = "tsrpago")
@PrimaryKeyJoinColumn(name = "NIdSrPago")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class SrPagoCfg extends PagoTDC {
	
	private static final long serialVersionUID = -3954107906956064377L;
	
	@Column(name = "VEjemplo")
	private String ejemplo;

	public String getEjemplo() {
		return ejemplo;
	}

	public void setEjemplo(String ejemplo) {
		this.ejemplo = ejemplo;
	}
}
