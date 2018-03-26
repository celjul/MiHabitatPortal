package com.bstmexico.mihabitat.pagostdc.feenicia.factory;

import java.util.Date;

import com.bstmexico.mihabitat.pagostdc.feenicia.dto.ManualSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.OrderSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago;
import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class ManualSaleFactory {
	
	public static ManualSale newInstance(FeeniciaCfg cfg, Pago pago, OrderSaleResponse order) {
		ManualSale sale = new ManualSale();
		sale.setAffiliation(cfg.getAffiliationFeenicia());
		sale.setAmount(pago.getMonto());
		sale.setCardholderName(pago.getNombre());
		sale.setCvv2(pago.getCvv());
		sale.setExpDate(pago.getExpiracion());
		sale.setOrderId(String.valueOf(order.getOrderId()));
		sale.setPan(pago.getTarjeta());
		sale.setTip(0.0);
		sale.setTransactionDate(new Date().getTime());
		return sale;
	}
}
