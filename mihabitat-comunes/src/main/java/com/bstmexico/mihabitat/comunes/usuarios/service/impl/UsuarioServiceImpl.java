package com.bstmexico.mihabitat.comunes.usuarios.service.impl;

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

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.usuarios.dao.UsuarioDao;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunes.usuarios.service.UsuarioService;
import com.bstmexico.mihabitat.comunes.usuarios.utils.UsuarioUtils;

@Service("usuarioService")
public class UsuarioServiceImpl implements UsuarioService {
	private static final Logger LOG = LoggerFactory
			.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioDao usuarioDao;

	@Autowired
	private Validator validator;

	@Override
	public Usuario get(Usuario usuario) {
		try {
			Assert.notNull(usuario, "SEREX001");
			Assert.notNull(usuario.getUser(), "SEREX004");
			return usuarioDao.getByUserName(usuario.getUser());
		} catch (DataAccessException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Usuario getByEmail(String email) {
		try {
			Assert.notNull(email, "SEREX001");
			return usuarioDao.getByEmail(email);
		} catch (DataAccessException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(Usuario usuario) {
		try {
			Assert.notNull(usuario, "SEREX001");
			Assert.notNull(usuario.getId(), "SEREX003");
			Set<ConstraintViolation<Usuario>> violations = validator
					.validate(usuario);
			if (violations.isEmpty()) {
				UsuarioUtils.fix(usuario);
				usuario = usuarioDao.merge(usuario);
				usuarioDao.update(usuario);
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
	public void save(Usuario usuario) {
		try {
			Assert.notNull(usuario, "SEREX001");
			UsuarioUtils.fix(usuario);
			
			Set<ConstraintViolation<Usuario>> violations = validator
					.validate(usuario);
			if (violations.isEmpty()) {
				usuarioDao.save(usuario);
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
	public Collection<Usuario> getByRol(CatalogoRol... roles) {
		try {
			Assert.notNull(roles, "SEREX001");
			return usuarioDao.getByRol(roles);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Usuario get(Long id) {
		try {
			Assert.notNull(id, "SEREX001");
			return usuarioDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Usuario getByUsername(String user) {
		try {
			Assert.notNull(user, "SEREX001");
			return usuarioDao.getByUserName(user);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<Usuario> getAll() {
		try {
			return usuarioDao.getList();
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<Usuario> search(Map map) {
		try {
			return usuarioDao.search(map.entrySet());
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
