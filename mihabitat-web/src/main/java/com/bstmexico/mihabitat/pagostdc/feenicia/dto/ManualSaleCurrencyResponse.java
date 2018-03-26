package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class ManualSaleCurrencyResponse {

	private Integer id;
	
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ManualSaleCurrencyResponse [id=" + id + ", description="
				+ description + "]";
	}
}
