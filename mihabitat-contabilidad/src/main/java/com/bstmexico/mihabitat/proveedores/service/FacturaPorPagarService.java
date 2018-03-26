package com.bstmexico.mihabitat.proveedores.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.proveedores.model.Cfdi;

/**
 * @author JPC
 * @version 1.0 
 * @since 2015
 */
public interface FacturaPorPagarService {
	
	void save(Cfdi facturaxp);

	//void update(Facturasporpagar facturaxp);

	@SuppressWarnings("rawtypes")
	Collection<Cfdi> search(Map map);

	void create(Collection<Cfdi> cfdi);

	Cfdi existeCfdi(Cfdi cfdi);

	@SuppressWarnings("rawtypes")
	Collection<Cfdi> cfdiByRfc(Map map);

	Cfdi get(Long id);
}
