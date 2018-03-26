package com.bstmexico.mihabitat.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

	private static final Logger LOG = LoggerFactory
			.getLogger(HibernateAwareObjectMapper.class);

	private static final long serialVersionUID = -863106355985840435L;

	public HibernateAwareObjectMapper() {
		Hibernate4Module hm = new Hibernate4Module();
//		hm.disable(Feature.USE_TRANSIENT_ANNOTATION);
		registerModule(hm);
		registerModule(new JodaModule());
	}

	@Override
	public String writeValueAsString(Object value) {
		try {
			return super.writeValueAsString(value);
		} catch (JsonProcessingException ex) {
			LOG.warn("Error al procesar el JSON" + ex);
			return null;
		}
	}
}