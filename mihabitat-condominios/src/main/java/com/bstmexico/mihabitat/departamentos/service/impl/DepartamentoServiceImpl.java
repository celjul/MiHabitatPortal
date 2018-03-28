package com.bstmexico.mihabitat.departamentos.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.comunes.personas.utils.PersonaUtils;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.model.EmailContacto;
import com.bstmexico.mihabitat.contactos.model.TelefonoContacto;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.dao.ContactoDao;
import com.bstmexico.mihabitat.departamentos.dao.DepartamentoDao;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class DepartamentoServiceImpl implements DepartamentoService {

	private static final Logger LOG = LoggerFactory
			.getLogger(DepartamentoServiceImpl.class);

	@Autowired
	private DepartamentoDao departamentoDao;

	@Autowired
	private ContactoDao contactoDao;

	@Autowired
	private Validator validator;

	@Transactional
	@Override
	public void save(Departamento departamento) {
		try {
			Assert.notNull(departamento, "SEREX001");
			for(ContactoDepartamento contactoDepartamento : departamento.getContactos()) {
				fixContacto(contactoDepartamento.getId().getContacto());
				contactoDepartamento.getId().getContacto().setCondominio(departamento.getCondominio());
			}
			Set<ConstraintViolation<Departamento>> violations = validator
					.validate(departamento);
			if (violations.isEmpty()) {
				departamentoDao.save(departamento);
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

	private void fixContacto(Contacto contacto) {
		if (CollectionUtils.isNotEmpty(contacto.getEmails())) {
			for (Email email : contacto.getEmails()) {
				((EmailContacto) email).setContacto(contacto);
			}
		}

		if (CollectionUtils.isNotEmpty(contacto.getTelefonos())) {
			for (Telefono tel : contacto.getTelefonos()) {
				((TelefonoContacto) tel).setContacto(contacto);
			}
		}

//		if (contacto.getPersona() != null) {
//			fixPersona(contacto.getPersona());
//		}
	}

	@Override
	public Departamento get(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			return departamentoDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Departamento departamento) {
		try {
			Assert.notNull(departamento, "SEREX001");
			Assert.notNull(departamento.getId(), "SEREX003");
			for(ContactoDepartamento contactoDepartamento : departamento.getContactos()) {
				fixContacto(contactoDepartamento.getId().getContacto());
				contactoDepartamento.getId().getContacto().setCondominio(departamento.getCondominio());
			}
			Set<ConstraintViolation<Departamento>> violations = validator
					.validate(departamento);
			if (violations.isEmpty()) {
				departamentoDao.update(departamento);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Collection<Departamento> search(Map map) {
		try {
			Assert.notNull(map, "SEREX001");
			return departamentoDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Departamento get(Departamento departamento) {
		try {
			Assert.notNull(departamento, "SEREX001");
			Assert.notNull(departamento.getCondominio(), "SEREX001");
			Assert.notNull(departamento.getCondominio().getId(), "SEREX001");
			Assert.hasLength(departamento.getNombre(), "SEREX002");
			return departamentoDao.get(departamento);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<Condominio> search(Usuario usuario) {
		try {
			Assert.notNull(usuario, "SEREX001");
			Assert.notNull(usuario.getId(), "SEREX003");
			return departamentoDao.search(usuario);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	public List<Departamento> searchByCond(Long id){
		try {
			return departamentoDao.searchByCond(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
	
	public List<Departamento> searchByPersona(Long id){
		try {
			return departamentoDao.searchByPersona(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
