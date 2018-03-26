package com.bstmexico.mihabitat.proveedores.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
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
import com.bstmexico.mihabitat.proveedores.dao.FacturaPorPagarDao;
import com.bstmexico.mihabitat.proveedores.model.Cfdi;
import com.bstmexico.mihabitat.proveedores.model.DetalleFactura;
import com.bstmexico.mihabitat.proveedores.service.FacturaPorPagarService;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Service
public class FacturaPorPagarServiceImpl implements FacturaPorPagarService {

	private static final Logger LOG = LoggerFactory
			.getLogger(FacturaPorPagarServiceImpl.class);

	@Autowired
	private FacturaPorPagarDao facturaPorPagarDao;

	@Autowired
	private Validator validator;


	@Transactional
	@Override
	public void save(Cfdi facturaxp) {
		try {
			Assert.notNull(facturaxp, "SEREX001");
			Set<ConstraintViolation<Cfdi>> violations = validator
					.validate(facturaxp);

			if (violations.isEmpty()) {
				if (!CollectionUtils.isEmpty(facturaxp.getConceptos())) {
					for (DetalleFactura det: facturaxp.getConceptos()) {
						det.getMovimientoCfdi().setDetalleCfdi(det);
					}
				}
				if (facturaxp.getMovimientoImpuestoRetenido() != null) {
					facturaxp.getMovimientoImpuestoRetenido().setCfdi(facturaxp);;
				}
				if (facturaxp.getMovimientoImpuestoTrasladado() != null) {
					facturaxp.getMovimientoImpuestoTrasladado().setCfdi(facturaxp);;
				}

				facturaPorPagarDao.save(facturaxp);
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

	/**
	@Override
	public void update(Facturasporpagar facturaxp) {
		try {
			Assert.notNull(facturaxp, "SEREX001");
			Assert.notNull(facturaxp.getId(), "SEREX003");
			Set<ConstraintViolation<Facturasporpagar>> violations = validator
					.validate(facturaxp);
			if (violations.isEmpty()) {
				facturaPorPagarDao.update(facturaxp);
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
	}**/


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Cfdi> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return new HashSet<>(facturaPorPagarDao.search(map.entrySet()));
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}


	@Transactional
	@Override
	public void create(Collection<Cfdi> cfdi) {
		try {
			Assert.notNull(cfdi, "SEREX001");

			Set<ConstraintViolation<Cfdi>> violations;
			for (Cfdi obj: cfdi) {
				violations = validator.validate(obj);

				if (violations.isEmpty()) {
					if (!CollectionUtils.isEmpty(obj.getConceptos())) {
						for (DetalleFactura det: obj.getConceptos()) {
							det.getMovimientoCfdi().setDetalleCfdi(det);
						}
					}
					if (obj.getMovimientoImpuestoRetenido() != null) {
						obj.getMovimientoImpuestoRetenido().setCfdi(obj);;
					}
					if (obj.getMovimientoImpuestoTrasladado() != null) {
						obj.getMovimientoImpuestoTrasladado().setCfdi(obj);;
					}

					facturaPorPagarDao.save(obj);
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
			//throw ex1;
		}
	}

	@Override
	public Cfdi existeCfdi(Cfdi cfdi) {
		Cfdi cfdiExistente = facturaPorPagarDao.findByCfdi(cfdi);
		return cfdiExistente;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Cfdi> cfdiByRfc(Map map) {

		try {
			Assert.notNull(map, "SEREX001");
			return new HashSet<>(facturaPorPagarDao.findByRfc(map));
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}


	@Override
	public Cfdi get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return facturaPorPagarDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
