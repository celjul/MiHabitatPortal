package com.bstmexico.mihabitat.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.web.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private CuentaService cuentaService;

	@Autowired
	private CondominioService condominioService;

	@Autowired
	private ConfigurationServiceImpl configurationServiceImpl;

	private static final Logger LOG = LoggerFactory
			.getLogger(AccountServiceImpl.class);

	@Transactional
	@Override
	public void guardarCondominio(Condominio condominio) {
		try {
			Assert.notNull(condominio, "SEREX001");
			Cuenta cuentasPadre[] = configurationServiceImpl
					.getCuentasPadreNoCuenta();
			condominioService.save(condominio);
			for (Cuenta cuenta : cuentasPadre) {
				cuenta.setCondominio(condominio);
				cuentaService.save(cuenta);
				if (cuenta.getNombre().equals(
						configurationServiceImpl.getCuentaIngresos()
								.getNombre())) {
					cuenta.setCuentasHijas(fix(condominio, cuenta, Arrays.asList(configurationServiceImpl.getCuentasHijasIngresos())));
				} else if (cuenta.getNombre().equals(
						configurationServiceImpl.getCuentaEgresos()
								.getNombre())) {
					cuenta.setCuentasHijas(fix(condominio, cuenta, Arrays.asList(configurationServiceImpl.getCuentasHijasEgresos())));
				} else if (cuenta.getNombre().equals(
						configurationServiceImpl.getCuentaCajas()
								.getNombre())) {
					cuenta.setCuentasHijas(fix(condominio, cuenta, Arrays.asList(configurationServiceImpl.getCuentasHijasCajas())));
				} else if (cuenta.getNombre().equals(
						configurationServiceImpl.getCuentaBancos()
								.getNombre())) {
					cuenta.setCuentasHijas(fix(condominio, cuenta, Arrays.asList(configurationServiceImpl.getCuentasHijasBancos())));
				}
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	private Collection<Cuenta> fix(Condominio condominio, Cuenta cuentaPadre, List<Cuenta> cuentas) {
		Collection<Cuenta> cuentasBuenas = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(cuentas)) {
			int i = 0;
			for (Cuenta cuenta : cuentas) {
				if(!cuenta.getNombre().trim().isEmpty()) {
					cuenta.setCondominio(condominio);
					cuenta.setNumero(cuentaPadre.getNumero());
					cuenta.setPadre(cuentaPadre);
					cuenta.setNumeroHija(String.format("%04d", ++i));
					cuentasBuenas.add(cuenta);
				}
			}
		}
		
		return cuentasBuenas;
	}
}
