package com.bstmexico.mihabitat.comunes.direcciones.test.dao;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.junit.Assert;

import com.bstmexico.mihabitat.comunes.direcciones.dao.EstadoDao;
import com.bstmexico.mihabitat.comunes.direcciones.factory.EstadoFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.PaisFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Estado;
import com.bstmexico.mihabitat.comunes.test.utils.StringUtilsTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = { "/spring-context.xml" })
@DatabaseSetup(value = { "/datasets/paises.xml", "/datasets/estados.xml" }, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/datasets/tear_down.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
public class EstadoDaoTest {
	
	@Autowired
	private EstadoDao estadoDao;

	@Test
	public void notNullDao() {
		Assert.assertNotNull(estadoDao);
	}

	@Test
	public void createEstado() {
		Estado estado = crearEstado();
		estadoDao.save(estado);
	}
	
	@Test
	public void crear() {
		Estado estado = crearEstado();
		estadoDao.save(estado);
	}

	@Test(expected = DataAccessException.class)
	public void crearNull() {
		Estado estado = null;
		estadoDao.save(estado);
	}

	@Test(expected = DataAccessException.class)
	public void crearEmpty() {
		Estado estado = EstadoFactory.newInstance();
		estadoDao.save(estado);
	}

	@Test(expected = DataAccessException.class)
	public void crearNombreNull() {
		Estado estado = crearEstado();
		estado.setNombre(null);
		estadoDao.save(estado);
	}

	@Test(expected = DataAccessException.class)
	public void crearNombreEmpty() {
		Estado estado = crearEstado();
		estado.setNombre(new String());
		estadoDao.save(estado);
	}

	@Test(expected = DataAccessException.class)
	public void crearNombreLength() {
		Estado estado = crearEstado();
		estado.setNombre(StringUtilsTest.insertChars("Monterrey", 128));
		estadoDao.save(estado);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void search() {
		Map map = new HashMap();
		map.put("pais", PaisFactory.newInstance(2L));
		Collection<Estado> estados = estadoDao.search(map.entrySet());
		Assert.assertNotNull(estados);
		Assert.assertFalse(estados.isEmpty());
		Assert.assertEquals(3, estados.size());
		Assert.assertTrue(estados.contains(EstadoFactory.newInstance(1L)));
	}
	
	private static Estado crearEstado() {
		Estado estado = EstadoFactory.newInstance();
		estado.setNombre("Distrito Federal");
		estado.setPais(PaisFactory.newInstance(1L));
		return estado;
	}		

}