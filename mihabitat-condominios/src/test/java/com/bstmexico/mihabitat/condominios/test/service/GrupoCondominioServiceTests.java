package com.bstmexico.mihabitat.condominios.test.service;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.dao.GrupoCondominioDao;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.factory.GrupoCondominioFactory;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;
import com.bstmexico.mihabitat.condominios.service.GrupoCondominioService;
import com.bstmexico.mihabitat.condominios.test.utils.StringUtilsTest;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/spring-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class GrupoCondominioServiceTests {
	
	@Autowired
	private GrupoCondominioService grupoCondominioService;
	@ReplaceWithMock
	@Autowired
	private GrupoCondominioDao grupoCondominioDao;

	@Test
	public void _001_isNotNullDao() {
		Assert.assertNotNull(grupoCondominioDao);
	}

	@Test
	public void _002_isNotNullDao() {
		Assert.assertNotNull(grupoCondominioService);
	}

	@Test
	public void _003_create() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominioService.save(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.atLeastOnce()).save(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _004_createNull() {
		GrupoCondominio grupoCondominio = null;
		grupoCondominioService.save(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).save(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _005_createEmpty() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory.newInstance();
		grupoCondominioService.save(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).save(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _005_createDescripcionNull() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.setDescripcion(null);
		grupoCondominioService.save(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.atLeastOnce()).save(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _006_createDescripcionEmpty() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.setDescripcion(new String());
		grupoCondominioService.save(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.atLeastOnce()).save(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _007_createDescripcionMaxLenght() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.setDescripcion(StringUtilsTest.insertChars(
				"Descripcion del Grupo", 129));
		grupoCondominioService.save(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).save(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _009_createCondominioNull() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.setCondominio(null);
		grupoCondominioService.save(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.atLeastOnce()).save(
				grupoCondominio);
	}

	

	@Test
	public void _012_get() {
		Long id = 1L;
		Mockito.when(grupoCondominioDao.get(id)).thenReturn(
				getGrupoCondominio());
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		Assert.assertNotNull(grupoCondominio);
		Assert.assertFalse(grupoCondominio.getDescripcion().isEmpty());
		Assert.assertEquals(grupoCondominio.getDescripcion(),
				"Descripcion del Grupo");

	}

	@Test(expected = ServiceException.class)
	public void _013_readIdNull() {
		Long id = null;
		GrupoCondominio grupoCondominio = grupoCondominioService.get(id);
		Assert.assertNull(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).get(id);
	}

	

	@Test(expected = ServiceException.class)
	public void _015_updateNull() {
		GrupoCondominio grupoCondominio = null;
		grupoCondominioService.update(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).update(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _016_updateDescripcionNull() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.setDescripcion(null);
		grupoCondominioService.update(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).update(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _017_updateDescripcionEmpty() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.setDescripcion(new String());
		grupoCondominioService.update(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).update(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _018_updateDescripcionMaxLength() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.setDescripcion(StringUtilsTest.insertChars(
				"descripcon", 130));
		grupoCondominioService.update(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).update(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _20_updateEmpty() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory.newInstance();
		grupoCondominioService.update(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).update(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _22_updateCondominioNull() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.getCondominio().setNombre(null);
		grupoCondominioService.update(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).update(
				grupoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _23_updateCondominioEmpty() {
		GrupoCondominio grupoCondominio = getGrupoCondominio();
		grupoCondominio.getCondominio().setNombre(new String());
		grupoCondominioService.update(grupoCondominio);
		Mockito.verify(grupoCondominioDao, Mockito.never()).update(
				grupoCondominio);
	}

	public static GrupoCondominio getGrupoCondominio() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory.newInstance();
		grupoCondominio.setDescripcion("Descripcion del Grupo");
		grupoCondominio.setCondominio(CondominioFactory.newInstance(1L));
		return grupoCondominio;

	}

}
