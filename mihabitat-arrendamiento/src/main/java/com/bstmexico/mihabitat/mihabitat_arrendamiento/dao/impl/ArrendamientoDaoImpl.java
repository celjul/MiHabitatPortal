package com.bstmexico.mihabitat.mihabitat_arrendamiento.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.HibernateException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.dao.ArrendamientoDao;
import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;

@Repository("arrendamientoDao")
public class ArrendamientoDaoImpl extends GenericDaoImpl<Arrendatario, Long> implements ArrendamientoDao {


	
	@Transactional
	@Override
	public void save(Arrendatario arredatario) {
		try {
			Assert.notNull(arredatario, "DAO010");	
			super.save(arredatario);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
	}
	
	@Transactional
	@Override
	public Collection<Arrendatario> getList() {
		Collection<Arrendatario> arrendatarios;
    	try {
    		arrendatarios = super.getList();
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
    	return arrendatarios;
	} 
	
	@Transactional
	@Override
	public Arrendatario get(Long idArrendador) {
		Arrendatario arrendatarios;
    	try {
    		arrendatarios = super.get(idArrendador);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
    	return arrendatarios;
	} 
	
	@Transactional
	@Override
	public void update(Arrendatario arrendatario) {

    	try {
    		super.update(arrendatario);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new DataAccessException("DAO004", ex,
					getType().getSimpleName());
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		} catch (HibernateException ex) {
			ApplicationException ex1 = new DataAccessException("DAO009", ex);
			LOG.error(ex.getMessage(), ex);
			throw ex1;
		}
    	
	} 
}
