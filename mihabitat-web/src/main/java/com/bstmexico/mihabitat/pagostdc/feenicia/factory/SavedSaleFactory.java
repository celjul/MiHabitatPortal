package com.bstmexico.mihabitat.pagostdc.feenicia.factory;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.bstmexico.mihabitat.pagostdc.feenicia.dto.ManualSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.OrderSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.SavedSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class SavedSaleFactory {

	public static SavedSale newInstance(FeeniciaCfg cfg,
			OrderSaleResponse order, ManualSaleResponse manualSale) {
		SavedSale sale = new SavedSale();
		sale.setAffiliation(cfg.getAffiliationFeenicia());
		sale.setAuthnum(manualSale.getAuthnum());
		sale.setMerchant(cfg.getMerchantFeenicia());
		sale.setOrderId(String.valueOf(order.getOrderId()));
		sale.setPanTermination(manualSale.getCard().getLast4Digits());
		sale.setTransactionDate(getDate());
		sale.setTransactionId(manualSale.getTransactionId());
		return sale;
	}
	
	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(new Date());
	}
}
