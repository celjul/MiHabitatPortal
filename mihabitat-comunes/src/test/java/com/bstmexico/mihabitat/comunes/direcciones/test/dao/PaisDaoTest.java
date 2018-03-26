package com.bstmexico.mihabitat.comunes.direcciones.test.dao;

import java.util.Collection;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.bstmexico.mihabitat.comunes.direcciones.dao.PaisDao;
import com.bstmexico.mihabitat.comunes.direcciones.factory.PaisFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Pais;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
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
public class PaisDaoTest {
	
	@Autowired
	private PaisDao paisDao;
	
	@Test
	public void notNullDao() {
		Assert.assertNotNull(paisDao);
	}

	@Test
	public void crear() {
		Pais pais = crearPais();
		paisDao.save(pais);
		Assert.assertNotNull(pais.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void crearPaisNull() {
		Pais pais = null;
		paisDao.save(pais);
	}
	
	@Test(expected = DataAccessException.class)
	public void crearPaisVacio() {
		Pais pais = PaisFactory.newInstance();
		paisDao.save(pais);
	}

	@Test(expected = DataAccessException.class)
	public void crearPaisNombreNull() {
		Pais pais = crearPais();
		pais.setNombre(null);
		paisDao.save(pais);
	}
	
	@Test(expected = DataAccessException.class)
	public void crearPaisNombreVacio() {
		Pais pais = crearPais();
		pais.setNombre(new String());
		paisDao.save(pais);
	}

	@Test(expected = DataAccessException.class)
	public void crearPaisNombreLength() {
		Pais pais = crearPais();
		pais.setNombre(StringUtilsTest.insertChars("Italia", 64));
		paisDao.save(pais);
	}
	
	@Test
	public void get() {
		Long id = 1L;
		Pais pais = paisDao.get(id);
		Assert.assertNotNull(pais);
		Assert.assertNotNull(pais.getId());
		Assert.assertEquals(pais.getId(), new Long(1L));
		Assert.assertNotNull(pais.getNombre());
		Assert.assertFalse(pais.getNombre().isEmpty());
		Assert.assertEquals(pais.getNombre(), "México");
	}

	@Test(expected = DataAccessException.class)
	public void getIdNull() {
		Long id = null;
		Pais pais = paisDao.get(id);
		Assert.assertNull(pais);
	}

	@Test
	public void getIdNonExistent() {
		Long id = 0L;
		Pais pais = paisDao.get(id);
		Assert.assertNull(pais);
	}
	
	@Test
	public void actualizar() {
		Pais pais = paisDao.get(1L);
		pais.setNombre("Estados Unidos Mexicanos");
		paisDao.update(pais);
		Pais paisDB = paisDao.get(1L);
		Assert.assertTrue(pais != paisDB);
		Assert.assertEquals(pais.getNombre(), paisDB.getNombre());
	}

	@Test(expected = DataAccessException.class)
	public void actualizarNull() {
		Pais pais = null;
		paisDao.update(pais);
	}

	@Test(expected = DataAccessException.class)
	public void actualizarEmpty() {
		Pais pais = PaisFactory.newInstance();
		paisDao.update(pais);
	}

	@Test(expected = DataAccessException.class)
	public void actualizarNombreNull() {
		Pais pais = paisDao.get(1L);
		pais.setNombre(null);
		paisDao.update(pais);
	}

	@Test(expected = DataAccessException.class)
	public void actualizarNombreEmpty() {
		Pais pais = paisDao.get(1L);
		pais.setNombre(new String());
		paisDao.update(pais);
	}

	@Test(expected = DataAccessException.class)
	public void actualizarNombreLength() {
		Pais pais = paisDao.get(1L);
		pais.setNombre(StringUtilsTest.insertChars("Alemania", 64));
		paisDao.update(pais);
	}	
	
	@Test
	public void eliminar() {
		Long id = 1L;
		paisDao.delete(PaisFactory.newInstance(id));
		Assert.assertNull(paisDao.get(id));
	}

	@Test(expected = DataAccessException.class)
	public void eliminarNull() {
		Pais pais = null;
		paisDao.delete(pais);
	}

	@Test
	public void eliminarEmpty() {
		Pais pais = PaisFactory.newInstance();
		paisDao.delete(pais);
	}

	@Test(expected = DataAccessException.class)
	public void eliminarIdNoExistente() {
		Long id = 0L;
		Pais pais = PaisFactory.newInstance(id);
		paisDao.delete(pais);
	}
	
	@Test
	public void getList() {
		Collection<Pais> paises = paisDao.getList();
		Assert.assertNotNull(paises);
		Assert.assertFalse(paises.isEmpty());
		Assert.assertEquals(3, paises.size());
	}

	private static Pais crearPais(){
		Pais pais = PaisFactory.newInstance();
		pais.setNombre("México");
		return pais;
	}		
}
