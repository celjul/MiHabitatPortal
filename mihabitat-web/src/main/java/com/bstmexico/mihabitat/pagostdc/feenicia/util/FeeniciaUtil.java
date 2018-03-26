package com.bstmexico.mihabitat.pagostdc.feenicia.util;

import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;
import com.google.common.hash.Hashing;
import com.serti.pandora.crypto.SymmetricEncryptionComponent;

/**
 * 
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 27-10-2017
 * @version 1.0
 *
 */
@Component
public class FeeniciaUtil {

	public String getToken(FeeniciaCfg cfg, SymmetricEncryptionComponent aes, String json) {
		StringBuilder builder = new StringBuilder(cfg.getMerchantFeenicia());
		builder.append("_");
		builder.append(encryptJson(aes, json));
		return builder.toString();
	}

	public String encryptJson(SymmetricEncryptionComponent aes, String json) {
		String hashed = Hashing.sha256().hashString(json, StandardCharsets.UTF_8).toString();
		String sig = aes.encrypt(hashed);
		return sig;
	}

	public String getURL(FeeniciaCfg cfg, String url) {
		StringBuilder builder = new StringBuilder(cfg.getHost());
		builder.append(url);
		return builder.toString();
	}

	public HttpHeaders getHttpHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-requested-with", token);
		return headers;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getResponse(String json, HttpHeaders headers, String url, Class clazz) {
		RestTemplate template = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>(json, headers);
		Object object = template.postForObject(url, entity, clazz);
		return object;
	}
}
