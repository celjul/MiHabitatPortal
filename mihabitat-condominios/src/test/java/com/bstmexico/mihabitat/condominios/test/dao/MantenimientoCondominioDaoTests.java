package com.bstmexico.mihabitat.condominios.test.dao;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.dao.MantenimientoCondominioDao;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.factory.MantenimientoCondominioFactory;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;
import com.bstmexico.mihabitat.condominios.test.utils.StringUtilsTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@ContextConfiguration(locations = { "/spring-context.xml" })
@DatabaseSetup(value = { "/datasets/paises.xml", "/datasets/estados.xml",
		"/datasets/municipios.xml", "/datasets/colonias.xml",
		"/datasets/usuarios.xml", "/datasets/condominios.xml",
		"/datasets/grupos_condominio.xml",
		"/datasets/mantenimientos_condominio.xml" })
@DatabaseTearDown(value = "/datasets/tear_down.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class

})
public class MantenimientoCondominioDaoTests {
	private static final Logger LOG = LoggerFactory
			.getLogger(MantenimientoCondominioDaoTests.class);
	@Autowired
	private MantenimientoCondominioDao mantenimientoCondominioDao;

	@Test
	public void _001_isNotNullDao() {
		LOG.debug("Comprobando que el dao no sea nulo.");
		Assert.assertNotNull(mantenimientoCondominioDao);
	}

	@Test(expected = DataAccessException.class)
	public void _002_saveNull() {
		MantenimientoCondominio mantenimientoCondominio = null;
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test
	public void _003_save() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominioDao.save(mantenimientoCondominio);
		Assert.assertNotNull(mantenimientoCondominio.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _004_createEmpty() {
		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory
				.newInstance();
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _005_createDescripcionNull() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(null);
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _006_createDescripcionEmpty() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(new String());
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _007_createDescripcionMaxLenght() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(StringUtilsTest.insertChars(
				"Descripcion del Grupo", 129));
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test
	public void _008_createDescripcion() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion("Descripcion Correcta");
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _009_createCondominioNull() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setCondominio(null);
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _010_createCondominioEmpty() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setCondominio(CondominioFactory.newInstance());
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _011_createCondominioNoExistente() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio
				.setCondominio(CondominioFactory.newInstance(0L));
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test
	public void _012_createMonto() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setMonto(BigDecimal.valueOf(150));
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _013_createMontoNull() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setMonto(null);
		mantenimientoCondominioDao.save(mantenimientoCondominio);
	}

	@Test
	public void _014_get() {
		Long id = 1L;
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioDao
				.get(id);
		Assert.assertNotNull(mantenimientoCondominio);
		Assert.assertNotNull(mantenimientoCondominio.getDescripcion());
		Assert.assertNotNull(mantenimientoCondominio.getCondominio());
		Assert.assertNotNull(mantenimientoCondominio.getMonto());
		Assert.assertNotNull(mantenimientoCondominio.getId());
		Assert.assertFalse(mantenimientoCondominio.getDescripcion().isEmpty());
		Assert.assertEquals(mantenimientoCondominio.getDescripcion(),
				"Descripciion del grupo");



	}
	@Test(expected = DataAccessException.class)
	public void _015_readIdNull() {
		Long id = null;
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioDao.get(id);
		Assert.assertNull(mantenimientoCondominio);
	}

	@Test
	public void _016_readIdNonExistent() {
		Long id = 0L;
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioDao.get(id);
		Assert.assertNull(mantenimientoCondominio);
	}
	
	
	@Test(expected = DataAccessException.class)
	public void _017_updateNull() {
		MantenimientoCondominio mantenimientoCondominio = null;
		mantenimientoCondominioDao.update(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _018_updateDescripcionNull() {
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioDao.get(1L);
		mantenimientoCondominio.setDescripcion(null);
		mantenimientoCondominioDao.update(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _019_updateDescripcionEmpty() {
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioDao.get(1L);
		mantenimientoCondominio.setDescripcion(new String());
		mantenimientoCondominioDao.update(mantenimientoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _020_updateDescripcionMaxLength() {
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioDao.get(1L);
		mantenimientoCondominio.setDescripcion(StringUtilsTest.insertChars(
				"descripcon", 130));
		mantenimientoCondominioDao.update(mantenimientoCondominio);
	}

	@Test
	public void _021_updateDescripcionCorrecto() {
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioDao.get(1L);
		mantenimientoCondominio.setDescripcion("nueva Descipcion");
		mantenimientoCondominioDao.update(mantenimientoCondominio);
		MantenimientoCondominio mantenimientoCondominioBD = mantenimientoCondominioDao.get(1L);
		Assert.assertEquals(mantenimientoCondominio.getDescripcion(), mantenimientoCondominioBD.getDescripcion());
	}

	@Test(expected = DataAccessException.class)
	public void _22_updateEmpty() {
		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory.newInstance();
		mantenimientoCondominioDao.update(mantenimientoCondominio);
	}
	

	
	@Test(expected = DataAccessException.class)
	public void _27_updateMontoNull() {
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioDao.get(1L);
		mantenimientoCondominio.setMonto(null);
		mantenimientoCondominioDao.update(mantenimientoCondominio);
	}

	public static MantenimientoCondominio getmantenimientoCondominio() {

		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory
				.newInstance();
		mantenimientoCondominio.setDescripcion("Descripcion del Mantenimiento");
		mantenimientoCondominio
				.setCondominio(CondominioFactory.newInstance(1L));
		mantenimientoCondominio.setMonto(BigDecimal.valueOf(150));

		return mantenimientoCondominio;

	}

}
