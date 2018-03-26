package com.bstmexico.mihabitat.movimientos.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.dao.MovimientoDao;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoSaldo;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service("movimientoservicedefault")
public class MovimientoServiceImpl implements MovimientoService {

	private static final Logger LOG = LoggerFactory
			.getLogger(MovimientoServiceImpl.class);

	@Autowired
	private MovimientoDao movimientoDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(Movimiento movimiento) {
		try {
			Assert.notNull(movimiento, "SEREX001");
			Set<ConstraintViolation<Movimiento>> violations = validator
					.validate(movimiento);
			if (violations.isEmpty()) {
				movimientoDao.save(movimiento);
			} else {
				String message = "SEREX002";
				ApplicationException ex1 = new ServiceException(message,
						violations);
				LOG.warn(ex1.getMessage() + violations);
				throw ex1;
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Movimiento get(Long id, Class<? extends Movimiento> movimiento) {
		try {
			Assert.notNull(id, "SEREX003");
			return movimientoDao.get(id, movimiento);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public MovimientoCargo getConCargo(Long id, Class<? extends Movimiento> movimiento){
		try {
			Assert.notNull(id, "SEREX003");
			Assert.notNull(movimiento, "SEREX003");
			return movimientoDao.getConCargo(id, movimiento);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Movimiento movimiento) {
		try {
			Assert.notNull(movimiento, "SEREX001");
			Assert.notNull(movimiento.getId(), "SEREX003");
			Set<ConstraintViolation<Movimiento>> violations = validator
					.validate(movimiento);
			if (violations.isEmpty()) {
				movimientoDao.merge(movimiento);
			} else {
				String message = "SEREX002";
				ApplicationException ex1 = new ServiceException(message,
						violations);
				LOG.warn(ex1.getMessage() + violations);
				throw ex1;
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void delete(Movimiento movimiento) {
		try {
			Assert.notNull(movimiento, "SEREX003");
			Assert.notNull(movimiento.getId(), "SEREX003");
			movimientoDao.delete(movimiento);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Movimiento> search(Map map,
			Class<? extends Movimiento> movimiento) {
		try {
			Assert.notNull(map, "SEREX001");
			return movimientoDao.search(map.entrySet(), movimiento);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getSaldoFavor(Contacto contacto) {
		try {
			Assert.notNull(contacto, "SEREX001");
			Assert.notNull(contacto.getId(), "SEREX003");
			BigDecimal saldo = BigDecimal.ZERO;
			Collection<MovimientoSaldo> saldos = movimientoDao
					.getMovimientos(contacto);
			if (!CollectionUtils.isEmpty(saldos)) {
				for (MovimientoSaldo mov : saldos) {
					if (mov.getDebe() != null) {
						saldo = saldo.subtract(mov.getDebe());
					} else if (mov.getHaber() != null) {
						saldo = saldo.add(mov.getHaber());
					}
				}
			}
			return saldo;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getSaldoFavorPorDepartamento(Departamento departamento) {
		try {
			Assert.notNull(departamento, "SEREX001");
			Assert.notNull(departamento.getId(), "SEREX003");
			Collection<MovimientoSaldo> saldos = movimientoDao
					.getMovimientosSaldo(departamento);
			BigDecimal actual = BigDecimal.ZERO;
			//Map<Long, BigDecimal> map = new HashedMap();
			if (!CollectionUtils.isEmpty(saldos)) {

				for (MovimientoSaldo mov : saldos) {
					/*if(map.containsKey(mov.getDepartamento() != null ? mov.getDepartamento().getId() : Long.valueOf(0))) {
						actual = ( map.get(mov.getDepartamento() != null ? mov.getDepartamento().getId() : Long.valueOf(0)));
					}*/
					if (mov.getDebe() != null) {
						actual = actual.subtract(mov.getDebe());
					} else if (mov.getHaber() != null) {
						actual = actual.add(mov.getHaber());
					}
					//map.put(mov.getDepartamento() != null ? mov.getDepartamento().getId() : Long.valueOf(0), actual);
				}
			}
			return actual;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getSaldoFavorPorDepartamento(Departamento departamento, Date inicio, Date fin) {
		try {
			Assert.notNull(departamento, "SEREX001");
			Assert.notNull(departamento.getId(), "SEREX003");
			Collection<MovimientoSaldo> saldos = movimientoDao
					.getMovimientosSaldo(departamento, inicio, fin);
			BigDecimal actual = BigDecimal.ZERO;
			//Map<Long, BigDecimal> map = new HashedMap();
			if (!CollectionUtils.isEmpty(saldos)) {

				for (MovimientoSaldo mov : saldos) {
					/*if(map.containsKey(mov.getDepartamento() != null ? mov.getDepartamento().getId() : Long.valueOf(0))) {
						actual = ( map.get(mov.getDepartamento() != null ? mov.getDepartamento().getId() : Long.valueOf(0)));
					}*/
					if (mov.getDebe() != null) {
						actual = actual.subtract(mov.getDebe());
					} else if (mov.getHaber() != null) {
						actual = actual.add(mov.getHaber());
					}
					//map.put(mov.getDepartamento() != null ? mov.getDepartamento().getId() : Long.valueOf(0), actual);
				}
			}
			return actual;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<MovimientoSaldo> getSaldosFavor(Departamento departamento) {
		try {
			Assert.notNull(departamento, "SEREX001");
			Assert.notNull(departamento.getId(), "SEREX003");
			return movimientoDao
					.getMovimientosSaldo(departamento);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<Movimiento> getMovimientos(Departamento departamento,
			Date inicio, Date fin) {
		try {
			Assert.notNull(departamento, "SEREX001");
			Assert.notNull(departamento.getId(), "SEREX001");
			// Assert.notNull(inicio, "SEREX001");
			Assert.notNull(fin, "SEREX001");
			return movimientoDao.getMovimientos(departamento, inicio, fin);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getDebe(Date fin, Cuenta cuenta) {
		try {
			Assert.notNull(fin, "SEREX001");
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX001");
			return movimientoDao.getDebe(fin, cuenta);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getDebeLtEq(Date fin, Cuenta cuenta) {
		try {
			Assert.notNull(fin, "SEREX001");
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX001");
			return movimientoDao.getDebeLtEq(fin, cuenta);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getHaber(Date fin, Cuenta cuenta) {
		try {
			Assert.notNull(fin, "SEREX001");
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX001");
			return movimientoDao.getHaber(fin, cuenta);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getHaberLtEq(Date fin, Cuenta cuenta) {
		try {
			Assert.notNull(fin, "SEREX001");
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX001");
			return movimientoDao.getHaberLtEq(fin, cuenta);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getDebe(Date inicio, Date fin, Cuenta cuenta) {
		try {
			Assert.notNull(inicio, "SEREX001");
			Assert.notNull(fin, "SEREX001");
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX001");
			return movimientoDao.getDebe(inicio, fin, cuenta);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public BigDecimal getHaber(Date inicio, Date fin, Cuenta cuenta) {
		try {
			Assert.notNull(inicio, "SEREX001");
			Assert.notNull(fin, "SEREX001");
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX001");
			return movimientoDao.getHaber(inicio, fin, cuenta);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<Movimiento> getMovimientos(Cuenta cuenta, Date inicio,
			Date fin) {
		try {
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX001");
			Assert.notNull(inicio, "SEREX001");
			Assert.notNull(fin, "SEREX001");
			return movimientoDao.getMovimientos(cuenta, inicio, fin);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
	@Override
	public Collection<Movimiento> getMovimientos(Cuenta cuenta, Date fin) {
		try {
			Assert.notNull(cuenta, "SEREX001");
			Assert.notNull(cuenta.getId(), "SEREX001");
			Assert.notNull(fin, "SEREX001");
			return movimientoDao.getMovimientos(cuenta, fin);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
