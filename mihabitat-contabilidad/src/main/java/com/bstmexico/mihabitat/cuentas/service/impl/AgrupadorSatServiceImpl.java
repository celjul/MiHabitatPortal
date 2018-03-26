package com.bstmexico.mihabitat.cuentas.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bstmexico.mihabitat.cuentas.dao.AgrupadorSatDao;
import com.bstmexico.mihabitat.cuentas.model.AgrupadorSat;
import com.bstmexico.mihabitat.cuentas.service.AgrupadorSatService;
@Service
public class AgrupadorSatServiceImpl implements AgrupadorSatService {
	private static final Logger LOG = LoggerFactory.getLogger(AgrupadorSatServiceImpl.class);

	@Autowired
	private AgrupadorSatDao agrupadorSatDao;

	@Override
	public Collection<AgrupadorSat> getList() {
		Collection<AgrupadorSat> agrupadores = agrupadorSatDao.getList();
		if (agrupadores.isEmpty()) {
			LOG.info("Lista vacia");
		}
		return agrupadores;
	}



}
