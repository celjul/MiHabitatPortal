package com.bstmexico.mihabitat.condominios.test.dao;

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
import com.bstmexico.mihabitat.condominios.dao.GrupoCondominioDao;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.factory.GrupoCondominioFactory;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;
import com.bstmexico.mihabitat.condominios.test.utils.StringUtilsTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@ContextConfiguration(locations = { "/spring-context.xml" })
@DatabaseSetup(value = { "/datasets/paises.xml", "/datasets/estados.xml",
		"/datasets/municipios.xml", "/datasets/colonias.xml",
		"/datasets/usuarios.xml", "/datasets/condominios.xml",
		"/datasets/grupos_condominio.xml" })
@DatabaseTearDown(value = "/datasets/tear_down.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class

})
public class GrupoCondominioDaoTests {
	private static final Logger LOG = LoggerFactory
			.getLogger(GrupoCondominioDaoTests.class);
	@Autowired
	private GrupoCondominioDao grupoCondominioDao;

	@Test
	public void _001_isNotNullDao() {
		LOG.debug("Comprobando que el dao no sea nulo.");
		Assert.assertNotNull(grupoCondominioDao);
	}

	@Test(expected = DataAccessException.class)
	public void _002_saveNull() {
		GrupoCondominio grupoCondominio = null;
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test
	public void _003_save() {
		GrupoCondominio grupoCondominio = grupoCondominio();
		grupoCondominioDao.save(grupoCondominio);
		Assert.assertNotNull(grupoCondominio.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _004_createEmpty() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory.newInstance();
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _005_createDescripcionNull() {
		GrupoCondominio grupoCondominio = grupoCondominio();
		grupoCondominio.setDescripcion(null);
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _006_createDescripcionEmpty() {
		GrupoCondominio grupoCondominio = grupoCondominio();
		grupoCondominio.setDescripcion(new String());
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _007_createDescripcionMaxLenght() {
		GrupoCondominio grupoCondominio = grupoCondominio();
		grupoCondominio.setDescripcion(StringUtilsTest.insertChars(
				"Descripcion del Grupo", 129));
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test
	public void _008_createDescripcion() {
		GrupoCondominio grupoCondominio = grupoCondominio();
		grupoCondominio.setDescripcion("Descripcion Correcta");
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _009_createCondominioNull() {
		GrupoCondominio grupoCondominio = grupoCondominio();
		grupoCondominio.setCondominio(null);
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _010_createCondominioEmpty() {
		GrupoCondominio grupoCondominio = grupoCondominio();
		grupoCondominio.setCondominio(CondominioFactory.newInstance());
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _011_createCondominioNoExistente() {
		GrupoCondominio grupoCondominio = grupoCondominio();
		grupoCondominio.setCondominio(CondominioFactory.newInstance(0L));
		grupoCondominioDao.save(grupoCondominio);
	}

	@Test
	public void _012_get() {
		Long id = 1L;
		GrupoCondominio grupoCondominio = grupoCondominioDao.get(id);
		Assert.assertNotNull(grupoCondominio);
		Assert.assertNotNull(grupoCondominio.getDescripcion());
		Assert.assertNotNull(grupoCondominio.getCondominio());
		Assert.assertNotNull(grupoCondominio.getId());
		Assert.assertFalse(grupoCondominio.getDescripcion().isEmpty());
		// Assert.assertEquals(grupoCondominio.getCondominio(),
		// CondominioFactory.newInstance(1L));
		Assert.assertEquals(grupoCondominio.getDescripcion(),
				"Descripciion del grupo");

	}

	@Test(expected = DataAccessException.class)
	public void _013_readIdNull() {
		Long id = null;
		GrupoCondominio grupoCondominio = grupoCondominioDao.get(id);
		Assert.assertNull(grupoCondominio);
	}

	@Test
	public void _014_readIdNonExistent() {
		Long id = 0L;
		GrupoCondominio grupoCondominio = grupoCondominioDao.get(id);
		Assert.assertNull(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _015_updateNull() {
		GrupoCondominio grupoCondominio = null;
		grupoCondominioDao.update(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _016_updateDescripcionNull() {
		GrupoCondominio grupoCondominio = grupoCondominioDao.get(1L);
		grupoCondominio.setDescripcion(null);
		grupoCondominioDao.update(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _017_updateDescripcionEmpty() {
		GrupoCondominio grupoCondominio = grupoCondominioDao.get(1L);
		grupoCondominio.setDescripcion(new String());
		grupoCondominioDao.update(grupoCondominio);
	}

	@Test(expected = DataAccessException.class)
	public void _018_updateDescripcionMaxLength() {
		GrupoCondominio grupoCondominio = grupoCondominioDao.get(1L);
		grupoCondominio.setDescripcion(StringUtilsTest.insertChars(
				"descripcon", 130));
		grupoCondominioDao.update(grupoCondominio);
	}

	@Test
	public void _019_updateDescripcionCorrecto() {
		GrupoCondominio grupoCondominio = grupoCondominioDao.get(1L);
		grupoCondominio.setDescripcion("nueva Descipcion");
		grupoCondominioDao.update(grupoCondominio);
		GrupoCondominio grupoCondominioBD = grupoCondominioDao.get(1L);
		Assert.assertEquals(grupoCondominio.getDescripcion(), grupoCondominioBD.getDescripcion());
	}

	@Test(expected = DataAccessException.class)
	public void _20_updateEmpty() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory.newInstance();
		grupoCondominioDao.update(grupoCondominio);
	}
	
	

	public static GrupoCondominio grupoCondominio() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory.newInstance();
		grupoCondominio.setDescripcion("Descripcion del Grupo");
		grupoCondominio.setCondominio(CondominioFactory.newInstance(1L));
		return grupoCondominio;

	}

}
