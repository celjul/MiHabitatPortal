package com.bstmexico.mihabitat.pagostdc.openpay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.bstmexico.mihabitat.condominios.model.PagoTDC;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 21-02-2018
 * @version 1.0
 *
 */
@Entity
@Table(name = "topenpay")
@PrimaryKeyJoinColumn(name = "NIdOpenPay")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class OpenPayCfg extends PagoTDC {
	
	private static final long serialVersionUID = 3618400759756904436L;
	
	@Column(name = "VEjemplo")
	private String ejemplo;

	public String getEjemplo() {
		return ejemplo;
	}

	public void setEjemplo(String ejemplo) {
		this.ejemplo = ejemplo;
	}
}
