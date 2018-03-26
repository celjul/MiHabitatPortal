package com.bstmexico.mihabitat.proveedores.service.impl;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.proveedores.dao.ProveedorDao;
import com.bstmexico.mihabitat.proveedores.model.ContactoProveedor;
import com.bstmexico.mihabitat.proveedores.model.EmailContactoProveedor;
import com.bstmexico.mihabitat.proveedores.model.Proveedor;
import com.bstmexico.mihabitat.proveedores.model.TelefonoContactoProveedor;
import com.bstmexico.mihabitat.proveedores.service.ProveedorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Service
public class ProveedorServiceImpl implements ProveedorService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ProveedorServiceImpl.class);

	@Autowired
	private ProveedorDao proveedorDao;

	@Autowired
	private Validator validator;

	@Override
	public void save(Proveedor proveedor) {
		try {
			Assert.notNull(proveedor, "SEREX001");
			Set<ConstraintViolation<Proveedor>> violations = validator
					.validate(proveedor);
			if (violations.isEmpty()) {

				if (!CollectionUtils.isEmpty(proveedor.getContactos())) {
					for (ContactoProveedor cp: proveedor.getContactos()) {
						if (!CollectionUtils.isEmpty(cp.getEmails())) {
							for (Email epc : cp.getEmails()) {
								((EmailContactoProveedor) epc).setContactoProveedor(cp);
							}
						}
						if (!CollectionUtils.isEmpty(cp.getTelefonos())) {
							for (Telefono tpc: cp.getTelefonos()) {
								((TelefonoContactoProveedor) tpc).setContactoProveedor(cp);
							}
						}
					}
				}

				proveedorDao.save(proveedor);
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
	public void update(Proveedor proveedor) {
		try {
			Assert.notNull(proveedor, "SEREX001");
			Assert.notNull(proveedor.getId(), "SEREX003");
			Set<ConstraintViolation<Proveedor>> violations = validator
					.validate(proveedor);
			if (violations.isEmpty()) {
				if (!CollectionUtils.isEmpty(proveedor.getContactos())) {
					for (ContactoProveedor cp: proveedor.getContactos()) {
						if (!CollectionUtils.isEmpty(cp.getEmails())) {
							for (Email epc : cp.getEmails()) {
								((EmailContactoProveedor) epc).setContactoProveedor(cp);
							}
						}
						if (!CollectionUtils.isEmpty(cp.getTelefonos())) {
							for (Telefono tpc: cp.getTelefonos()) {
								((TelefonoContactoProveedor) tpc).setContactoProveedor(cp);
							}
						}
					}
				}
				proveedorDao.update(proveedor);
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
	public Proveedor get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return proveedorDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Proveedor> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return proveedorDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}


	@Override
	public Proveedor existeProveedor (Proveedor proveedor) {
		Proveedor proveedorExistente =  proveedorDao.findByRFC(proveedor);
		return proveedorExistente;
	}

}
