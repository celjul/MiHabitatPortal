package com.bstmexico.mihabitat.condominios.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 07-11-2017
 * @version 1.0
 *
 */
@Entity
@Table(name = "tpagostdc")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PagoTDC implements Serializable {
	
	private static final long serialVersionUID = 1590784857022425049L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NIdPagoTDC", nullable = false, unique = true)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
