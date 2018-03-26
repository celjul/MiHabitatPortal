package com.bstmexico.mihabitat.pagostdc.feenicia.repository;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagostdc.feenicia.model.FeeniciaCfg;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public interface FeeniciaRepository {
	
	FeeniciaCfg get(Condominio condominio);
}
