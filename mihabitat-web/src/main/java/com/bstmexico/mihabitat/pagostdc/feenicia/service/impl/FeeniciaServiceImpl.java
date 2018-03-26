package com.bstmexico.mihabitat.pagostdc.feenicia.service.impl;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.ManualSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.ManualSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.OrderSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.OrderSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.SavedSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.SavedSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.ManualSaleFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.OrderSaleFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.SavedSaleFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.SymmetricEncryptionComponentFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;
import com.bstmexico.mihabitat.pagostdc.feenicia.repository.FeeniciaRepository;
import com.bstmexico.mihabitat.pagostdc.feenicia.service.FeeniciaService;
import com.bstmexico.mihabitat.pagostdc.feenicia.util.FeeniciaException;
import com.bstmexico.mihabitat.pagostdc.feenicia.util.FeeniciaUtil;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.serti.pandora.crypto.SymmetricEncryptionComponent;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
@Service
public class FeeniciaServiceImpl implements FeeniciaService {

	private static final Logger LOG = LoggerFactory
			.getLogger(FeeniciaServiceImpl.class);
	
	@Autowired
	private FeeniciaRepository repository;
	
	@Autowired
	private FeeniciaUtil util;
	
	private static final Gson GSON;
	private static final String EXITO;

	static {
		GSON = new Gson();
		EXITO = "00";
	}
	
	@Override
	public void pay(Pago pago, Condominio condominio) throws Exception {
		FeeniciaCfg cfg = repository.get(condominio);
		Assert.notNull(cfg, "No se encontro la configuracion.");
		SymmetricEncryptionComponent aes = SymmetricEncryptionComponentFactory.getAes(cfg);
		SymmetricEncryptionComponent aesRequest = SymmetricEncryptionComponentFactory.getAesRequest(cfg);

		OrderSaleResponse order = null;
		ManualSaleResponse manualSale = null;
		SavedSaleResponse savedSale = null;
		try {
			order = getOrder(cfg, pago, aes);
			checkResponse(order);
			manualSale = getManualSale(cfg, pago, aes, aesRequest, order);
			checkResponse(manualSale);
			savedSale = getSavedSale(cfg, aes, order, manualSale);
			checkResponse(savedSale);
		} catch (Exception e) {
			LOG.error("ERROR " + e);
			throw e;
		}
	}

	@Override
	public String encryptJson(SymmetricEncryptionComponent aes, String json) {
		String hashed = Hashing.sha256()
				.hashString(json, StandardCharsets.UTF_8).toString();
		String sig = aes.encrypt(hashed);
		return sig;
	}

	@Override
	public String encryptText(SymmetricEncryptionComponent aesRequest, String text) {
		return aesRequest.encrypt(text);
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	private OrderSaleResponse getOrder(FeeniciaCfg cfg, Pago pago, SymmetricEncryptionComponent aes) {
		OrderSale order = OrderSaleFactory.newInstance(cfg, pago);

		String json = GSON.toJson(order);
		String token = util.getToken(cfg, aes, json);
		String url = util.getURL(cfg, "/receipt/order/create");

		LOG.info("ORDER JSON - ".concat(json));
		LOG.info("ORDER TOKEN - ".concat(token));

		HttpHeaders headers = util.getHttpHeaders(token);
		OrderSaleResponse response = (OrderSaleResponse) util.getResponse(json, headers, url, OrderSaleResponse.class);

		LOG.info("ORDER RESPONSE - ".concat(GSON.toJson(response)));
		return response;
	}

	private ManualSaleResponse getManualSale(FeeniciaCfg cfg, Pago pago, SymmetricEncryptionComponent aes, 
			SymmetricEncryptionComponent aesRequest, OrderSaleResponse order) {
		ManualSale sale = ManualSaleFactory.newInstance(cfg, pago, order);
		sale.setCardholderName(encrypt(aesRequest, sale.getCardholderName()));
		sale.setCvv2(encrypt(aesRequest, sale.getCvv2()));
		sale.setExpDate(encrypt(aesRequest, sale.getExpDate()));
		sale.setPan(encrypt(aesRequest, sale.getPan()));

		String json = GSON.toJson(sale);
		String token = util.getToken(cfg, aes, json);
		String url = util.getURL(cfg, ":10080/atna/sale/manual");

		LOG.info("MANUAL SALE JSON - ".concat(json));
		LOG.info("MANUAL SALE TOKEN - ".concat(token));

		HttpHeaders headers = util.getHttpHeaders(token);
		ManualSaleResponse response = (ManualSaleResponse) util.getResponse(json, headers, url, ManualSaleResponse.class);

		LOG.info("MANUAL SALE RESPONSE - ".concat(GSON.toJson(response)));
		return response;
	}
	
	private SavedSaleResponse getSavedSale(FeeniciaCfg cfg, SymmetricEncryptionComponent aes, 
			OrderSaleResponse order, ManualSaleResponse manualSale) {
		SavedSale savedSale = SavedSaleFactory.newInstance(cfg, order, manualSale);
		
		String json = GSON.toJson(savedSale);
		String token = util.getToken(cfg, aes, json);
		String url = util.getURL(cfg, "/receipt/signature/save");

		LOG.info("SAVED SALE JSON - ".concat(json));
		LOG.info("SAVED SALE TOKEN - ".concat(token));

		HttpHeaders headers = util.getHttpHeaders(token);
		SavedSaleResponse response = (SavedSaleResponse) util.getResponse(json, headers, url, SavedSaleResponse.class);

		LOG.info("SAVED SALE RESPONSE - ".concat(GSON.toJson(response)));
		return response;
	}

	private String encrypt(SymmetricEncryptionComponent aesRequest, String text) {
		return encryptText(aesRequest, text);
	}
	
	private void checkResponse(Object object) throws Exception {
		Method method;
		try {
		  method = object.getClass().getMethod("getResponseCode");
		  String response = (String) method.invoke(object);
		  Assert.isTrue(response.equals(EXITO), FeeniciaException.getMensaje(response));
		} catch (Exception e) {
			throw e;
		}
	}
}
