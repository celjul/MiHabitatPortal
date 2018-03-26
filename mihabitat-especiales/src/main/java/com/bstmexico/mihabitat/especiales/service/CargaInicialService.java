package com.bstmexico.mihabitat.especiales.service;

import com.bstmexico.mihabitat.especiales.model.CargaInicial;

import java.util.Collection;
import java.util.Map;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2017
 */
public interface CargaInicialService {

	Collection<CargaInicial> getList();

	void save(CargaInicial cargaInicial);

	CargaInicial get(Long id);

	void update(CargaInicial cargaInicial);

	@SuppressWarnings("rawtypes")
	Collection<CargaInicial> search(Map map);

}

