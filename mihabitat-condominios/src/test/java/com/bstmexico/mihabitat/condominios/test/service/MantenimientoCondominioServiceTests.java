package com.bstmexico.mihabitat.condominios.test.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.dao.MantenimientoCondominioDao;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.factory.MantenimientoCondominioFactory;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;
import com.bstmexico.mihabitat.condominios.service.MantenimientoCondominioService;
import com.bstmexico.mihabitat.condominios.test.utils.StringUtilsTest;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/spring-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class MantenimientoCondominioServiceTests {
	private static final Logger LOG = LoggerFactory
			.getLogger(MantenimientoCondominioServiceTests.class);
	
	@Autowired
	private MantenimientoCondominioService mantenimientoCondominioService;
	
	@Autowired
	@ReplaceWithMock
	private MantenimientoCondominioDao mantenimientoCondominioDao;
	
	@Test
	public void _001_isNotNullDao() {
		LOG.debug("Comprobando que el dao no sea nulo.");
		Assert.assertNotNull(mantenimientoCondominioDao);
	}
	@Test
	public void _002_isNotNullDao(){
		Assert.assertNotNull(mantenimientoCondominioService);
	}

	@Test(expected = ServiceException.class)
	public void _002_saveNull() {
		MantenimientoCondominio mantenimientoCondominio = null;
		mantenimientoCondominioService.save(mantenimientoCondominio);
		
	}

	@Test
	public void _003_save() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominioService.save(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.atLeastOnce()).save(mantenimientoCondominio);
		
	}

	@Test(expected = ServiceException.class)
	public void _004_createEmpty() {
		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory
				.newInstance();
		mantenimientoCondominioService.save(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).save(mantenimientoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _005_createDescripcionNull() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(null);
		mantenimientoCondominioService.save(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).save(mantenimientoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _006_createDescripcionEmpty() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(new String());
		mantenimientoCondominioService.save(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).save(mantenimientoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _007_createDescripcionMaxLenght() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(StringUtilsTest.insertChars(
				"Descripcion del Grupo", 129));
		mantenimientoCondominioService.save(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).save(mantenimientoCondominio);
	}

	

	@Test(expected = ServiceException.class)
	public void _009_createCondominioNull() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setCondominio(null);
		mantenimientoCondominioService.save(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).save(mantenimientoCondominio);
	}
	@Test(expected = ServiceException.class)
	public void _013_createMontoNull() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setMonto(null);
		mantenimientoCondominioService.save(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).save(mantenimientoCondominio);
	}

	@Test
	public void _014_get() {
		Long id = 1L;
		Mockito.when(mantenimientoCondominioDao.get(id)).thenReturn(getmantenimientoCondominio());
		
		MantenimientoCondominio mantenimientoCondominio = mantenimientoCondominioService
				.get(id);
		Assert.assertNotNull(mantenimientoCondominio);
		Assert.assertFalse(mantenimientoCondominio.getDescripcion().isEmpty());
		
		Assert.assertEquals(mantenimientoCondominio.getDescripcion(),
				"Descripcion del Mantenimiento");
		Assert.assertEquals(mantenimientoCondominio.getMonto(),
				BigDecimal.valueOf(134));


	}
	@Test
	@SuppressWarnings("unused")
	public void _015_readIdNull() {
		Long id = null;
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).get(id);
	}

	@Test
	@SuppressWarnings("unused")
	public void _016_readIdNonExistent() {
		Long id = 0L;
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).get(id);
	}
	
	
	@Test(expected = ServiceException.class)
	public void _017_updateNull() {
		MantenimientoCondominio mantenimientoCondominio = null;
		mantenimientoCondominioService.update(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).update(
				mantenimientoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _018_updateDescripcionNull() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(null);
		mantenimientoCondominioService.update(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).update(
				mantenimientoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _019_updateDescripcionEmpty() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(new String());
		mantenimientoCondominioService.update(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).update(
				mantenimientoCondominio);
	}

	@Test(expected = ServiceException.class)
	public void _020_updateDescripcionMaxLength() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setDescripcion(StringUtilsTest.insertChars(
				"descripcon", 130));
		mantenimientoCondominioService.update(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).update(
				mantenimientoCondominio);
	}



	@Test(expected = ServiceException.class)
	public void _22_updateEmpty() {
		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory.newInstance();
		mantenimientoCondominioService.update(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).update(
				mantenimientoCondominio);
	}
	

	@Test(expected = ServiceException.class)
	public void _27_updateMontoNull() {
		MantenimientoCondominio mantenimientoCondominio = getmantenimientoCondominio();
		mantenimientoCondominio.setMonto(null);
		mantenimientoCondominioService.update(mantenimientoCondominio);
		Mockito.verify(mantenimientoCondominioDao, Mockito.never()).update(
				mantenimientoCondominio);
	}

	public static MantenimientoCondominio getmantenimientoCondominio() {

		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory
				.newInstance();
		mantenimientoCondominio.setDescripcion("Descripcion del Mantenimiento");
		mantenimientoCondominio
				.setCondominio(CondominioFactory.newInstance(1L));
		mantenimientoCondominio.setMonto(BigDecimal.valueOf(134));

		return mantenimientoCondominio;

	}

}
