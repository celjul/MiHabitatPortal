package com.bstmexico.mihabitat.cobranza.service;

import java.util.Collection;
import java.util.Date;

import com.bstmexico.mihabitat.cobranza.model.Cobranza;
import com.bstmexico.mihabitat.condominios.model.Condominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public interface CobranzaService {
	
	Collection<Cobranza> getCobranza(Condominio condominio, Date hasta);
}
