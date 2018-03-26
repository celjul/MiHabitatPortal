package com.bstmexico.mihabitat.web.controllers;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.CreateReceipt;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.EmailSend;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.EmailSendResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.ManualSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.ManualSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.OrderSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.OrderSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.PagoExterno;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.ReceiptResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.RespuestaExterna;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.SavedSale;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.SavedSaleResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.reportes.ListadoTransaccionesResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.CreateReceiptFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.EmailSendFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.FeeniciaCfgFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.ManualSaleFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.OrderSaleFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.SavedSaleFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.SymmetricEncryptionComponentFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;
import com.bstmexico.mihabitat.pagostdc.feenicia.service.FeeniciaReporteService;
import com.bstmexico.mihabitat.pagostdc.feenicia.service.FeeniciaService;
import com.bstmexico.mihabitat.pagostdc.feenicia.util.FeeniciaException;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import com.google.gson.Gson;
import com.serti.pandora.crypto.SymmetricEncryptionComponent;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
@Controller
@RequestMapping(value = "feenicia")
public class FeeniciaController {

	private static final Logger LOG = LoggerFactory.getLogger(FeeniciaController.class);

	@Autowired
	private FeeniciaService feeniciaService;

	

	/*
	 * @Value("${feenicia.produccion.host}") private String feeniciaProduccionHost;
	 * 
	 * @Value("${feenicia.qa.host}") private String feeniciaQaHost;
	 */
	@Autowired
	private ConfigurationServiceImpl configurationService;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String getView() {
		return "feenicia/feenicia";
	}

	private static final Gson GSON;

	static {
		GSON = new Gson();
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "pagar")
	public Boolean pagar(@RequestBody Pago pago, HttpSession session) throws Exception {
		Condominio condominio = (Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue());
		feeniciaService.pay(pago, condominio);
		return Boolean.TRUE;
	}

	///////////////////////////////////////////////////////////////// EXTERNO////

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "pagar/externo")
	public RespuestaExterna externalSale(@RequestBody PagoExterno pagoExterno, HttpSession session) {
		return externalSale(pagoExterno, Boolean.TRUE);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "pagar/externo_qa")
	public RespuestaExterna externalSaleQA(@RequestBody PagoExterno pagoExterno, HttpSession session) {
		return externalSale(pagoExterno, Boolean.FALSE);
	}

	private RespuestaExterna externalSale(PagoExterno pagoExterno, Boolean produccion) {
		RespuestaExterna respuestaExterna = new RespuestaExterna();
		respuestaExterna.setResponseCode("BT");
		respuestaExterna.setDescripcion("Error Desconocido");

		FeeniciaCfg cfg = FeeniciaCfgFactory.newInstance();
		cfg.setAffiliationFeenicia(pagoExterno.getAffiliation());
		cfg.setMerchantFeenicia(pagoExterno.getMerchant());
		cfg.setUserFeenicia(pagoExterno.getUsuario());
		cfg.setMerchantRequestSignatureIV(pagoExterno.getIv());
		cfg.setMerchantRequestSignatureKey(pagoExterno.getKey());
		cfg.setMerchantRequestIV(pagoExterno.getIvRequest());
		cfg.setMerchantRequestKEY(pagoExterno.getKeyRequest());

		OrderSale order = OrderSaleFactory.newInstance(cfg, pagoExterno);
		SymmetricEncryptionComponent aes = SymmetricEncryptionComponentFactory.getAes(cfg);
		String jsonOrder = GSON.toJson(order);
		String tokenOrder = cfg.getMerchantFeenicia().concat("_").concat(feeniciaService.encryptJson(aes, jsonOrder));
		String urlOrder = (produccion ? configurationService.getFeeniciaProduccionHost()
				: configurationService.getFeeniciaQaHost()).concat("/receipt/order/create");

		LOG.info("JSON ORDER ".concat(jsonOrder));
		LOG.info("TOKEN ORDER ".concat(tokenOrder));

		HttpHeaders headersOrder = new HttpHeaders();
		headersOrder.setContentType(MediaType.APPLICATION_JSON);
		headersOrder.set("x-requested-with", tokenOrder);

		RestTemplate templateOrder = new RestTemplate();
		HttpEntity<String> entityOrder = new HttpEntity<String>(jsonOrder, headersOrder);
		try {
			OrderSaleResponse responseOrder = templateOrder.postForObject(urlOrder, entityOrder,
					OrderSaleResponse.class);

			LOG.info("RESPONSE ORDER ".concat(responseOrder.toString()));

			if (responseOrder.getResponseCode().equals("00")) {

				ManualSale sale = ManualSaleFactory.newInstance(cfg, pagoExterno, responseOrder);
				SymmetricEncryptionComponent aesRequest = SymmetricEncryptionComponentFactory.getAesRequest(cfg);
				sale.setCardholderName(feeniciaService.encryptText(aesRequest, sale.getCardholderName()));
				sale.setCvv2(feeniciaService.encryptText(aesRequest, sale.getCvv2()));
				sale.setExpDate(feeniciaService.encryptText(aesRequest, sale.getExpDate()));
				sale.setPan(feeniciaService.encryptText(aesRequest, sale.getPan()));
				String jsonSale = GSON.toJson(sale);
				String tokenSale = cfg.getMerchantFeenicia().concat("_")
						.concat(feeniciaService.encryptJson(aes, jsonSale));
				String urlSale = (produccion ? configurationService.getFeeniciaProduccionHost()
						: configurationService.getFeeniciaQaHost()).concat(
								(!produccion ? ":10080/atna/sale/manual" : "/atena-swa-services-0.1/sale/manual"));
				// String urlSale =
				// (produccion?configurationService.getFeeniciaProduccionHost():configurationService.getFeeniciaQaHost()).concat((!produccion?":10080":"")).concat("/atna/sale/manual");

				LOG.info("JSON SALE ".concat(jsonSale));
				LOG.info("TOKEN SALE ".concat(tokenSale));

				HttpHeaders headersSale = new HttpHeaders();
				headersSale.setContentType(MediaType.APPLICATION_JSON);
				headersSale.set("x-requested-with", tokenSale);

				RestTemplate templateSale = new RestTemplate();
				HttpEntity<String> entitySale = new HttpEntity<String>(jsonSale, headersSale);
				respuestaExterna.setTransactionDate(sale.getTransactionDate());
				try {
					ManualSaleResponse responseSale = templateSale.postForObject(urlSale, entitySale,
							ManualSaleResponse.class);

					LOG.info("RESPONSE SALE ".concat(responseSale.toString()));

					if (responseSale.getResponseCode().equals("00")) {

						respuestaExterna.setResponseCode(responseSale.getResponseCode());
						respuestaExterna.setDescripcion("Transaccion Aprobada, gracias");
						respuestaExterna.setAuthnum(responseSale.getAuthnum());
						respuestaExterna.setAmount(responseSale.getAmount());
						respuestaExterna.setApproved(responseSale.getApproved());
						respuestaExterna.setIssuerBank(responseSale.getIssuerBank());
						respuestaExterna.setMerchant(responseSale.getMerchant());
						respuestaExterna.setTransactionId(responseSale.getTransactionId());
						respuestaExterna.setAcquirerBank(responseSale.getAcquirerBank());

						SavedSale savedSale = SavedSaleFactory.newInstance(cfg, responseOrder, responseSale);
						String jsonSavedSale = GSON.toJson(savedSale);
						String tokenSavedSale = cfg.getMerchantFeenicia().concat("_")
								.concat(feeniciaService.encryptJson(aes, jsonSavedSale));
						String urlSavedSale = (produccion ? configurationService.getFeeniciaProduccionHost()
								: configurationService.getFeeniciaQaHost()).concat("/receipt/signature/save");

						LOG.info("JSON SAVED SALE ".concat(jsonSavedSale));
						LOG.info("TOKEN SAVED SALE ".concat(tokenSavedSale));

						HttpHeaders headersSavedSale = new HttpHeaders();
						headersSavedSale.setContentType(MediaType.APPLICATION_JSON);
						headersSavedSale.set("x-requested-with", tokenSavedSale);

						RestTemplate templateSavedSale = new RestTemplate();
						HttpEntity<String> entitySavedSale = new HttpEntity<String>(jsonSavedSale, headersSavedSale);
						SavedSaleResponse responseSavedSale = templateSavedSale.postForObject(urlSavedSale,
								entitySavedSale, SavedSaleResponse.class);

						LOG.info("RESPONSE SAVED SALE ".concat(responseSavedSale.toString()));

						if (responseSavedSale.getResponseCode().equals("00")) {
							CreateReceipt createReceipt = CreateReceiptFactory.newInstance();
							createReceipt.setOrderId(responseSavedSale.getOrderId());
							createReceipt.setTransactionId(responseSale.getTransactionId());
							createReceipt.setTotal(responseSale.getAmount());
							createReceipt.setReceiptDateTime(savedSale.getTransactionDate());
							createReceipt.setSendUrlByMail(Boolean.FALSE);
							createReceipt.setPropina(BigDecimal.ZERO);

							String jsonCreateReceipt = GSON.toJson(createReceipt);
							String tokenCreateReceipt = cfg.getMerchantFeenicia().concat("_")
									.concat(feeniciaService.encryptJson(aes, jsonCreateReceipt));
							String urlCreateReceipt = (produccion ? configurationService.getFeeniciaProduccionHost()
									: configurationService.getFeeniciaQaHost())
											.concat("/receipt/receipt/CreateReceipt");

							LOG.info("JSON CREATE RECEIPT ".concat(jsonCreateReceipt));
							LOG.info("TOKEN CREATE RECEIPT ".concat(tokenCreateReceipt));

							HttpHeaders headersCreateReceipt = new HttpHeaders();
							headersCreateReceipt.setContentType(MediaType.APPLICATION_JSON);
							headersCreateReceipt.set("x-requested-with", tokenCreateReceipt);

							RestTemplate templateCreateReceipt = new RestTemplate();
							HttpEntity<String> entityCreateReceipt = new HttpEntity<String>(jsonCreateReceipt,
									headersCreateReceipt);
							ReceiptResponse responseCreateReceipt = templateCreateReceipt
									.postForObject(urlCreateReceipt, entityCreateReceipt, ReceiptResponse.class);

							LOG.info("RESPONSE RECEIPT ".concat(responseCreateReceipt.toString()));

							if (responseCreateReceipt.getResponseCode().equals("00")) {

								EmailSend emailSend = EmailSendFactory.newInstance();
								emailSend.setReceiptId(responseCreateReceipt.getReceiptId());
								String emailEncrypt = feeniciaService.encryptText(aesRequest, pagoExterno.getEmail());
								emailSend.getEmail().add(emailEncrypt);
								String jsonEmailSend = GSON.toJson(emailSend);
								String tokenEmailSend = cfg.getMerchantFeenicia().concat("_")
										.concat(feeniciaService.encryptJson(aes, jsonEmailSend));
								String urlEmailSend = (produccion ? configurationService.getFeeniciaProduccionHost()
										: configurationService.getFeeniciaQaHost())
												.concat("/receipt/receipt/SendReceipt");

								LOG.info("JSON EMAIL SEND ".concat(jsonEmailSend));
								LOG.info("TOKEN EMAIL SEND ".concat(tokenEmailSend));

								HttpHeaders headersEmailSend = new HttpHeaders();
								headersEmailSend.setContentType(MediaType.APPLICATION_JSON);
								headersEmailSend.set("x-requested-with", tokenEmailSend);

								RestTemplate templateEmailSend = new RestTemplate();
								HttpEntity<String> entityEmailSend = new HttpEntity<String>(jsonEmailSend,
										headersEmailSend);
								EmailSendResponse responseEmailSend = templateEmailSend.postForObject(urlEmailSend,
										entityEmailSend, EmailSendResponse.class);

								LOG.info("RESPONSE EMAIL SEND ".concat(responseEmailSend.toString()));

							}
						}
					} else {
						respuestaExterna.setResponseCode(responseSale.getResponseCode());
						respuestaExterna.setDescripcion(FeeniciaException.getMensaje(responseSale.getResponseCode()));
					}
				} catch (HttpClientErrorException ce) {
					respuestaExterna.setResponseCode("FERR");
					respuestaExterna.setDescripcion(
							"Por el momento el servicio no se encuentra disponible, favor de intentarlo m�s tarde");
					LOG.error("ERROR EN EL SERVICIO MANUAL SALE: ".concat(ce.getMessage()));
				}
			} else {
				respuestaExterna.setResponseCode(responseOrder.getResponseCode());
				respuestaExterna.setDescripcion("No ha sido posible crear la orden: "
						+ FeeniciaException.getMensaje(responseOrder.getResponseCode()));
			}
		} catch (HttpClientErrorException ce) {
			respuestaExterna.setResponseCode("FERR");
			respuestaExterna.setDescripcion(
					"Por el momento el servicio no se encuentra disponible, favor de intentarlo m�s tarde");
			LOG.error("ERROR EN EL SERVICIO CREAR ORDEN: ".concat(ce.getMessage()));
		}
		return respuestaExterna;
	}
}
