package com.bstmexico.mihabitat.comunes.direcciones.test.service;

import java.util.ArrayList;
import java.util.Collection;

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

import com.bstmexico.mihabitat.comunes.direcciones.dao.PaisDao;
import com.bstmexico.mihabitat.comunes.direcciones.factory.PaisFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Pais;
import com.bstmexico.mihabitat.comunes.direcciones.service.PaisService;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/spring-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class PaisServiceTest {

	@Autowired
	private PaisService paisService;

	@ReplaceWithMock
	@Autowired
	private PaisDao paisDao;
	
	@Test
	public void isNotNullService() {
		Assert.assertNotNull(paisService);
	}

	@Test
	public void isNotNullDao() {
		Assert.assertNotNull(paisDao);
	}
	
	@Test
	public void getList() {
		Mockito.when(paisDao.getList()).thenReturn(getPaises());
		Collection<Pais> paises = paisService.getList();
		Assert.assertNotNull(paises);
		Assert.assertFalse(paises.isEmpty());
		Assert.assertEquals(3, paises.size());
	}
	
	private static Collection<Pais> getPaises() {
		Collection<Pais> paises = new ArrayList<Pais>();
		paises.add(PaisFactory.newInstance(1L));
		paises.add(PaisFactory.newInstance(2L));
		paises.add(PaisFactory.newInstance(3L));
		return paises;
	}
}
