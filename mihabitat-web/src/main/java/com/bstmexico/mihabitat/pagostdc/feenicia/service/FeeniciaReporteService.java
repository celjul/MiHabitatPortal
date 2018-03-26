package com.bstmexico.mihabitat.pagostdc.feenicia.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagostdc.feenicia.dto.reportes.ListadoTransaccionesResponse;

/**
 * @author Pablo Cruz Santos (pablocrusa@gmail.com)
 * @since 25-10-2017
 * @version 1.0
 *
 */
public interface FeeniciaReporteService {
	
	ListadoTransaccionesResponse get(Condominio condominio);
}
