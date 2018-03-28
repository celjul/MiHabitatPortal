package com.bstmexico.mihabitat.mihabitat_arrendamiento.service.impl;



import java.util.Collection;
import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.dao.ArrendamientoDao;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.service.ArrendatarioService;

@Service("arrendatarioService")
public class ArrendatarioServiceImpl implements ArrendatarioService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ArrendatarioServiceImpl.class);

	@Autowired
	private ArrendamientoDao arrendamientoDao;
	
	@Autowired
	private Validator validator;
	
	
	public Arrendatario get(Long idArrendatario) {
		Arrendatario arrendatario;
		try {
			arrendatario=arrendamientoDao.get(idArrendatario);
			
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
		return arrendatario;
	}
	
	@Override
	public void save(Arrendatario arrendatario) {
		try {
			Assert.notNull(arrendatario, "SEREX001");
			arrendamientoDao.save(arrendatario);
			
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
		
	@Override
	public Collection<Arrendatario> getAll() {
		try {
			return arrendamientoDao.getList();
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	public void update(Arrendatario arrendatario){
		try {
			Assert.notNull(arrendatario, "SEREX001");
			arrendamientoDao.update(arrendatario);
			
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

}
