package com.bstmexico.mihabitat.pagostdc.feenicia.factory;

import com.bstmexico.mihabitat.pagostdc.feenicia.dto.CreateReceipt;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class CreateReceiptFactory {

	public static CreateReceipt newInstance() {
		CreateReceipt createReceipt = new CreateReceipt();
		return createReceipt;
	}
}
