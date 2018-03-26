package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class ManualSaleCardResponse {

	private String product;

	private String brand;

	private String last4Digits;

	private String first6Digits;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getLast4Digits() {
		return last4Digits;
	}

	public void setLast4Digits(String last4Digits) {
		this.last4Digits = last4Digits;
	}

	public String getFirst6Digits() {
		return first6Digits;
	}

	public void setFirst6Digits(String first6Digits) {
		this.first6Digits = first6Digits;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ManualSaleCardResponse [product=" + product + ", brand=" + brand
				+ ", last4Digits=" + last4Digits + ", first6Digits="
				+ first6Digits + "]";
	}
}
