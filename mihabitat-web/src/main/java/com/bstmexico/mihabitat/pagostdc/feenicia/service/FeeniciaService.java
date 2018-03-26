package com.bstmexico.mihabitat.pagostdc.feenicia.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago;
import com.serti.pandora.crypto.SymmetricEncryptionComponent;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public interface FeeniciaService {
	
	void pay(Pago pago, Condominio condominio) throws Exception;

	String encryptJson(SymmetricEncryptionComponent aes, String json);

	String encryptText(SymmetricEncryptionComponent aesRequest, String text);
}
