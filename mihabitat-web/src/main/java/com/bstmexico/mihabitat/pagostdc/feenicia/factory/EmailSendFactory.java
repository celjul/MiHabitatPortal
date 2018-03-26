package com.bstmexico.mihabitat.pagostdc.feenicia.factory;

import com.bstmexico.mihabitat.pagostdc.feenicia.dto.EmailSend;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class EmailSendFactory {

	public static EmailSend newInstance() {
		EmailSend emailSend = new EmailSend();
		return emailSend;
	}
}
