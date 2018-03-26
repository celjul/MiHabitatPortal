package com.bstmexico.mihabitat.pagostdc.feenicia.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.reportes.ListadoTransaccionesRequest;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.reportes.ListadoTransaccionesResponse;
import com.bstmexico.mihabitat.pagostdc.feenicia.factory.SymmetricEncryptionComponentFactory;
import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;
import com.bstmexico.mihabitat.pagostdc.feenicia.repository.FeeniciaRepository;
import com.bstmexico.mihabitat.pagostdc.feenicia.service.FeeniciaReporteService;
import com.bstmexico.mihabitat.pagostdc.feenicia.util.FeeniciaUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serti.pandora.crypto.SymmetricEncryptionComponent;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 27-10-2017
 * @version 1.0
 *
 */
@Service
public class FeeniciaReporteServiceImpl implements FeeniciaReporteService {

	private static final Logger LOG = LoggerFactory.getLogger(FeeniciaReporteServiceImpl.class);

	@Autowired
	private FeeniciaRepository repository;

	@Autowired
	private FeeniciaUtil util;

	private static final ObjectMapper MAPPER;

	static {
		MAPPER = new ObjectMapper();
	}

	@Override
	public ListadoTransaccionesResponse get(Condominio condominio) {
		FeeniciaCfg cfg = repository.get(condominio);
		Assert.notNull(cfg, "No se encontro la configuracion.");
		SymmetricEncryptionComponent aes = SymmetricEncryptionComponentFactory.getAes(cfg);

		ListadoTransaccionesRequest request = get(cfg);

		String json;
		try {
			json = MAPPER.writeValueAsString(request);
			LOG.info("JSON: " + json);
			String token = util.getToken(cfg, aes, json);
			LOG.info("TOKEN: " + token);
			String url = util.getURL(cfg, "Aura_Dashboard/Transactions/Transactions");
			LOG.info("URL: " + url);

			HttpHeaders headers = util.getHttpHeaders(token);
			String response = (String) util.getResponse(json, headers, url, String.class);
			LOG.info(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(response));
			
			ListadoTransaccionesResponse transacciones = MAPPER.readValue(response, ListadoTransaccionesResponse.class);	
			return transacciones;
		} catch (JsonProcessingException e) {
			LOG.warn("Error", e);
			return null;
		} catch (IOException e) {
			LOG.warn("Error", e);
			return null;
		}
	}

	private ListadoTransaccionesRequest get(FeeniciaCfg cfg) {
		ListadoTransaccionesRequest request = new ListadoTransaccionesRequest();
		request.setAprobada(true);
		request.setMerchantId(cfg.getMerchantFeenicia());
		request.setPage(1);
		request.setPageSize(10000);
		request.setRefund(false);
		return request;
	}
}
