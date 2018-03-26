package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class ManualSaleBankResponse {

	private String name;

	private Integer id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ManualSaleBankResponse [name=" + name + ", id=" + id + "]";
	}
}
