package com.bstmexico.mihabitat.comunes.direcciones.test.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

import com.bstmexico.mihabitat.comunes.direcciones.dao.EstadoDao;
import com.bstmexico.mihabitat.comunes.direcciones.factory.EstadoFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.PaisFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Estado;
import com.bstmexico.mihabitat.comunes.direcciones.service.EstadoService;



@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/spring-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class EstadoServiceTest {
	
	@Autowired
	private EstadoService estadoService;

	@ReplaceWithMock
	@Autowired
	private EstadoDao estadoDao;

	@Test
	public void isNotNullService() {
		Assert.assertNotNull(estadoService);
	}

	@Test
	public void isNotNullDao() {
		Assert.assertNotNull(estadoDao);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void search() {
		Map map = new HashMap();
		map.put("pais", PaisFactory.newInstance(1L));
		Mockito.when(estadoDao.search(map.entrySet())).thenReturn(getEstados());
		Collection<Estado> estados = estadoService.search(map);
		Assert.assertNotNull(estados);
		Assert.assertFalse(estados.isEmpty());
		Assert.assertEquals(3, estados.size());
		Assert.assertTrue(estados.contains(EstadoFactory.newInstance(1L)));
	}


	private static Collection<Estado> getEstados() {
		Collection<Estado> estados = new ArrayList<Estado>();
		estados.add(EstadoFactory.newInstance(1L));
		estados.add(EstadoFactory.newInstance(2L));
		estados.add(EstadoFactory.newInstance(3L));
		return estados;
	}
}
