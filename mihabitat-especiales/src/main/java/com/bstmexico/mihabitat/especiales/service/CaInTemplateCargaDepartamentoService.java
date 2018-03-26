package com.bstmexico.mihabitat.especiales.service;

import com.bstmexico.mihabitat.especiales.model.CaInTemplateCargaDepartamento;

import java.util.Collection;
import java.util.Map;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2017
 */
public interface CaInTemplateCargaDepartamentoService {

	Collection<CaInTemplateCargaDepartamento> getList();

	void save(CaInTemplateCargaDepartamento caInTemplateCargaDepartamento);

	CaInTemplateCargaDepartamento get(Long id);

	void update(CaInTemplateCargaDepartamento caInTemplateCargaDepartamento);

	@SuppressWarnings("rawtypes")
	Collection<CaInTemplateCargaDepartamento> search(Map map);

}

