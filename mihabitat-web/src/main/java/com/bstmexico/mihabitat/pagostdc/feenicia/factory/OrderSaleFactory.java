package com.bstmexico.mihabitat.pagostdc.feenicia.factory;

import com.bstmexico.mihabitat.pagostdc.feenicia.dto.OrderSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.OrderSaleItem;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago;
import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class OrderSaleFactory {

	public static OrderSale newInstance(FeeniciaCfg cfg, Pago pago) {
		OrderSale order = new OrderSale();
		order.addItem(newInstance(pago));
		order.setMerchant(cfg.getMerchantFeenicia());
		order.setUserId(cfg.getUserFeenicia());
		order.setAmount(pago.getMonto());//item
		return order;
	}

	private static OrderSaleItem newInstance(Pago pago) {
		OrderSaleItem item = new OrderSaleItem();
		item.setDescription("Servicio");
		item.setId(0L);
		item.setQuantity(1);
		item.setUnitPrice(pago.getMonto());
		item.setAmount(pago.getMonto());//cantidad
		return item;
	}
}
