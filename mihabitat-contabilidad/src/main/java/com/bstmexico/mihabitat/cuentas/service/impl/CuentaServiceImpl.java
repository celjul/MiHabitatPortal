package com.bstmexico.mihabitat.cuentas.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.dao.CuentaDao;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;

@Service
public class CuentaServiceImpl implements CuentaService {

	private static final Logger LOG = LoggerFactory
			.getLogger(CuentaServiceImpl.class);

	@Autowired
	private CuentaDao cuentaDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(Cuenta cuenta) {
		try {
			Assert.notNull(cuenta, "SEREX001");
			Set<ConstraintViolation<Cuenta>> violations = validator
					.validate(cuenta);
			if (violations.isEmpty()) {
				cuentaDao.save(cuenta);
			} else {
				String message = "SEREX002";
				ApplicationException ex1 = new ServiceException(message,
						violations);
				LOG.warn(ex1.getMessage() + violations, violations);
				throw ex1;
			}

		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}

	}

	@Override
	public Cuenta get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return cuentaDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public String incrementarCuenta(Long idPadre, Byte nivel) {
		return cuentaDao.getCuentaIncrementar(idPadre, nivel);
	}

	@Override
	public void update(Cuenta cuenta) {
		try {
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX003");
			Set<ConstraintViolation<Cuenta>> violations = validator
					.validate(cuenta);
			if (violations.isEmpty()) {
				cuentaDao.update(cuenta);
			} else {
				String message = "SEREX002";
				ApplicationException ex1 = new ServiceException(message,
						violations);
				LOG.warn(ex1.getMessage(), violations);
				throw ex1;
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Cuenta> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return cuentaDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<Cuenta> getList(Condominio condominio) {
		try {
			Assert.notNull(condominio, "SEREX001");
			Assert.notNull(condominio.getId(), "SEREX001");
			return cuentaDao.getList(condominio);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<Cuenta> getCuentas(Condominio condominio,
			Cuenta... cuentas) {
		try {
			Assert.notNull(cuentas, "SEREX001");
			Collection<Cuenta> cts = new ArrayList<Cuenta>();
			for (Cuenta cuenta : cuentas) {
				cuenta.setCondominio(condominio);
				cts.add(cuentaDao.getCuenta(cuenta));
			}
			return cts;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<Cuenta> getAllCuentas(Condominio condominio,
			Cuenta... cuentas) {
		try {
			Assert.notNull(cuentas, "SEREX001");
			Collection<Cuenta> cts = new ArrayList<Cuenta>();
			for (Cuenta cuenta : cuentas) {
				cuenta.setCuentasHijas(cuentaDao.getList(condominio));
				cts.add(cuenta);
			}
			return cts;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Cuenta comprobarExistenciaCuenta(Cuenta cuenta) {
		Cuenta cuentaExistente = cuentaDao.comprobarExistenciaCuenta(cuenta);
		return cuentaExistente;
	}

	@Override
	public void updateMultiple(Cuenta cuenta) {
		Cuenta anterior = cuentaDao.get(cuenta.getId());
		int nivel = 0;
		if (cuenta.getNumero() != anterior.getNumero()
				|| cuenta.getActivo() != anterior.getActivo()) {
			if (cuenta.getNumero() != null) {
				nivel = 1;
				if (cuenta.getNumeroHija() != null) {
					nivel = 2;
					if (cuenta.getNumeroNieto() != null) {
						nivel = 3;
						if (cuenta.getNumeroBis() != null) {
							nivel = 4;
						}
					}
				}
			}
			// Cuenta conHijos = cuentaDao.getCuenta(anterior);
			// cuenta.setCuentasHijas(conHijos.getCuentasHijas());
			cuenta.setCuentasHijas(cuentaDao.getCuentaHijos(anterior));
			if (!CollectionUtils.isEmpty(cuenta.getCuentasHijas())) {
				for (Cuenta hija : cuenta.getCuentasHijas()) {
					hija.setActivo(cuenta.getActivo());
					hija.setPadre(cuenta);
					if (nivel == 1) {
						hija.setNumero(cuenta.getNumero());
					} else if (nivel == 2) {
						hija.setNumeroHija(cuenta.getNumeroHija());
					} else if (nivel == 3) {
						hija.setNumeroNieto(cuenta.getNumeroNieto());
					} else if (nivel == 4) {
						hija.setNumeroBis(cuenta.getNumeroBis());
					}
					if (!CollectionUtils.isEmpty(hija.getCuentasHijas())) {
						for (Cuenta nieta : hija.getCuentasHijas()) {
							nieta.setActivo(hija.getActivo());
							nieta.setPadre(hija);
							if (nivel == 1) {
								nieta.setNumero(cuenta.getNumero());
							} else if (nivel == 2) {
								nieta.setNumeroHija(cuenta.getNumeroHija());
							}
							if (!CollectionUtils.isEmpty(nieta
									.getCuentasHijas())) {
								for (Cuenta bisnieta : nieta.getCuentasHijas()) {
									bisnieta.setActivo(nieta.getActivo());
									bisnieta.setPadre(nieta);
									if (nivel == 1) {
										bisnieta.setNumero(cuenta.getNumero());
									}
								}
							}
						}
					}
				}
			}
		}

		cuentaDao.merge(cuenta);
		cuenta.setCuentasHijas(null);
	}

	@Override
	public Cuenta get(Condominio condominio, String nombre) {
		try {
			Assert.notNull(condominio);
			Assert.notNull(condominio.getId());
			Assert.notNull(nombre);
			Assert.hasLength(nombre);
			return cuentaDao.get(condominio, nombre);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Cuenta get(Cuenta cuenta) {
		try {
			Assert.notNull(cuenta);
			Assert.notNull(cuenta.getId());
			return cuentaDao.getCuenta(cuenta);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
