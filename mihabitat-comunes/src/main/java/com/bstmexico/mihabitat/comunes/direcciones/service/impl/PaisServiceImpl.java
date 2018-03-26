package com.bstmexico.mihabitat.comunes.direcciones.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bstmexico.mihabitat.comunes.direcciones.dao.PaisDao;
import com.bstmexico.mihabitat.comunes.direcciones.model.Pais;
import com.bstmexico.mihabitat.comunes.direcciones.service.PaisService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class PaisServiceImpl implements PaisService {

	private static final Logger LOG = LoggerFactory
			.getLogger(PaisServiceImpl.class);

	@Autowired
	private PaisDao paisDao;

	@Override
	public Collection<Pais> getList() {
		Collection<Pais> paises = paisDao.getList();
		if (paises.isEmpty()) {
			LOG.info("Lista vacía");
		}
		return paises;
	}
}
