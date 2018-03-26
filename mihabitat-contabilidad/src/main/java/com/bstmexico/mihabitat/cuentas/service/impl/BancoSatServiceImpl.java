package com.bstmexico.mihabitat.cuentas.service.impl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bstmexico.mihabitat.cuentas.dao.BancoSatDao;
import com.bstmexico.mihabitat.cuentas.model.BancoSat;
import com.bstmexico.mihabitat.cuentas.service.BancoSatService;
@Service
public class BancoSatServiceImpl implements BancoSatService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BancoSatServiceImpl.class);
	
	@Autowired
	private BancoSatDao bancoSatDao;

	@Override
	public Collection<BancoSat> getList() {
		Collection<BancoSat> bancos = bancoSatDao.getList();
		if (bancos.isEmpty()) {
			LOG.info("Lista vacia");
		}
		return bancos;
	}

	

}
