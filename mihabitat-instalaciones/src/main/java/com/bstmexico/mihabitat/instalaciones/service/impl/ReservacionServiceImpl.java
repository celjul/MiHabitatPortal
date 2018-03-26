package com.bstmexico.mihabitat.instalaciones.service.impl;

import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.bstmexico.mihabitat.cargos.factory.CargoFactory;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.model.CatalogoCargo;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoEstatusReservacion;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoUnidadInstalacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.instalaciones.dao.ReservacionDao;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.bstmexico.mihabitat.instalaciones.service.ReservacionService;

@Service("reservacionservicedefault")
public class ReservacionServiceImpl implements ReservacionService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ReservacionServiceImpl.class);

	@Autowired
	private ReservacionDao reservacionDao;
	
	@Autowired
	private Validator validator;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Reservacion> getList(Instalacion instalacion) {

		Map params = new HashMap();
		params.put("instalacion.id", instalacion.getId());

		return reservacionDao.search(params.entrySet());
		
	}

	@Override
	public void save(Reservacion reservacion) {
		
		try {
			Assert.notNull(reservacion, "SEREX001");
			Set<ConstraintViolation<Reservacion>> violations = validator
					.validate(reservacion);
			if (violations.isEmpty()) {
				reservacionDao.save(reservacion);
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
	public Reservacion get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return reservacionDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Reservacion getConInstalacion(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return reservacionDao.getConInstalacion(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Reservacion reservacion) {

		try {
			Assert.notNull(reservacion, "SEREX001");
			Assert.notNull(reservacion.getId(), "SEREX003");
			Set<ConstraintViolation<Reservacion>> violations = validator
					.validate(reservacion);
			if (violations.isEmpty()) {
				reservacionDao.update(reservacion);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Collection<Reservacion> search(Map map) {

		try {
			Assert.notNull(map, "SEREX001");
			return reservacionDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
		
	}

	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void saveAll(Collection<Reservacion> reservaciones) {

		try {
			Assert.notNull(reservaciones, "SEREX001");
			for(Reservacion r : reservaciones) {
				Set<ConstraintViolation<Reservacion>> violations = validator
						.validate(r);
				if (violations.isEmpty()) {
					if(r.getId()==null) {
						reservacionDao.save(r);
					} else {
						reservacionDao.update(r);
					}
				} else {
					String message = "SEREX002";
					ApplicationException ex1 = new ServiceException(message,
							violations);
					LOG.warn(ex1.getMessage(), violations);
					throw ex1;
				}
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void deleteAll(Collection<Reservacion> reservaciones) {

		try {
			Assert.notNull(reservaciones, "SEREX001");
			for(Reservacion r : reservaciones) {
				if(r.getId() != null) {
					reservacionDao.delete(r);
				}
			}
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}

	}*/

	@Override
	public void delete(Reservacion reservacion) {

		try {
			Assert.notNull(reservacion, "SEREX001");
			Assert.notNull(reservacion.getId(), "SEREX001");

			reservacionDao.delete(reservacion);

		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}

	}

	@Override
	public Collection<Reservacion> getListByCondominioByEstatus(Condominio condominio,
																CatalogoEstatusReservacion estatus) {
		try {
			Assert.notNull(condominio, "SEREX001");
			Assert.notNull(condominio.getId(), "SEREX001");
			Assert.notNull(estatus, "SEREX001");
			Assert.notNull(estatus.getId(), "SEREX001");
			return reservacionDao.getListByCondominioAndEstatus(condominio, estatus);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
