package com.bstmexico.mihabitat.pagostdc.feenicia.factory;

import java.util.HashMap;
import java.util.Map;

import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;
import com.serti.pandora.crypto.SymmetricEncryptionComponent;
import com.serti.pandora.crypto.symmetric.impl.AesEncryption;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class SymmetricEncryptionComponentFactory {

	private static final String MODE;
	private static final String ENCODING;

	static {
		MODE = "AES/CBC/PKCS7Padding";
		ENCODING = "HEX";
	}

	public static SymmetricEncryptionComponent getAes(FeeniciaCfg cfg) {
		SymmetricEncryptionComponent aes = new AesEncryption();

		Map<String, String> map = new HashMap<>();
		map.put("AES.KEY", cfg.getMerchantRequestSignatureKey());
		map.put("AES.IV", cfg.getMerchantRequestSignatureIV());
		map.put("AES.MODE", MODE);
		map.put("AES.ENCODING", ENCODING);
		aes.setInitParams(map);
		return aes;
	}

	public static SymmetricEncryptionComponent getAesRequest(FeeniciaCfg cfg) {
		SymmetricEncryptionComponent aesRequest = new AesEncryption();

		Map<String, String> map = new HashMap<>();
		map.put("AES.KEY", cfg.getMerchantRequestKEY());
		map.put("AES.IV", cfg.getMerchantRequestIV());
		map.put("AES.MODE", MODE);
		map.put("AES.ENCODING", ENCODING);
		aesRequest.setInitParams(map);
		return aesRequest;
	}
}
