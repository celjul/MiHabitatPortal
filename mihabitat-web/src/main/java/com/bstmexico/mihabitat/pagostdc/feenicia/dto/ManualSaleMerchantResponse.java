package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class ManualSaleMerchantResponse {

	private String name;

	private String address;

	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ManualSaleMerchantResponse [name=" + name + ", address="
				+ address + ", id=" + id + "]";
	}
}
