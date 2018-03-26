package com.bstmexico.mihabitat.cobranza.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cobranza.dao.CobranzaDao;
import com.bstmexico.mihabitat.cobranza.model.Cobranza;
import com.bstmexico.mihabitat.cobranza.service.CobranzaService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class CobranzaServiceImpl implements CobranzaService {

	private static final Logger LOG = LoggerFactory
			.getLogger(CobranzaServiceImpl.class);

	@Autowired
	private CobranzaDao cobranzaDao;

	@Autowired
	private DepartamentoService departamentoService;

	@Override
	public Collection<Cobranza> getCobranza(Condominio condominio, Date hasta) {
		try {
			Assert.notNull(condominio);
			Assert.notNull(condominio.getId());
			Assert.notNull(hasta);

			List<Cobranza> cobranzas = new ArrayList<Cobranza>();
			Collection<CargoDepartamento> cargos = cobranzaDao.getCargos(
					condominio, hasta);
			if (!CollectionUtils.isEmpty(cargos)) {
				for (CargoDepartamento cargo : cargos) {
					Cobranza cobranza = new Cobranza();
					cobranza.setDepartamento(cargo.getDepartamento());
					if (!cobranzas.contains(cobranza)) {
						cobranza.addCargo(cargo);
						cobranzas.add(cobranza);
					} else {
						for (Cobranza cobranzaE : cobranzas) {
							if (cobranzaE.equals(cobranza)) {
								cobranzaE.addCargo(cargo);
								break;
							}
						}
					}
				}
			}

			if (!CollectionUtils.isEmpty(cobranzas)) {
				Iterator<Cobranza> iterator = cobranzas.iterator();
				while (iterator.hasNext()) {
					Cobranza cobranza = iterator.next();
					if (CollectionUtils.isEmpty(cobranza.getCargos())) {
						iterator.remove();
					} else {
						cobranza.setDepartamento(departamentoService
								.get(cobranza.getDepartamento().getId()));
					}
				}
			}
			Collections.sort(cobranzas);
			return cobranzas;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
