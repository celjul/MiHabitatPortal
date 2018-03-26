package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class OrderSale {

	private BigDecimal amount;

	private Collection<OrderSaleItem> items;

	private String merchant;

	private String userId;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Collection<OrderSaleItem> getItems() {
		return items;
	}

	public void setItems(Collection<OrderSaleItem> items) {
		this.items = items;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void addItem(OrderSaleItem item) {
		if (this.items == null) {
			this.items = new ArrayList<>();
		}
		this.items.add(item);
	}
}
