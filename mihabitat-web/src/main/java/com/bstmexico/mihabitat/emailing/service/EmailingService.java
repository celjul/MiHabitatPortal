package com.bstmexico.mihabitat.emailing.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.web.dto.Adjunto;
import org.springframework.core.io.ByteArrayResource;

public interface EmailingService {



	@SuppressWarnings("rawtypes")
	void sendEmail(final Map<String,String> emails, final String asunto,
				   final String template, final Map atributosVelocity,
				   final Collection<Adjunto> adjuntos,
				   final String nombreCondominio);


}
