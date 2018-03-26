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

import com.bstmexico.mihabitat.comunes.direcciones.dao.MunicipioDao;
import com.bstmexico.mihabitat.comunes.direcciones.factory.EstadoFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.MunicipioFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Municipio;
import com.bstmexico.mihabitat.comunes.direcciones.service.MunicipioService;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/spring-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class MunicipioServiceTest {
	@Autowired
	private MunicipioService municipioService;

	@ReplaceWithMock
	@Autowired
	private MunicipioDao municipioDao;

	@Test
	public void isNotNullService() {
		Assert.assertNotNull(municipioService);
	}

	@Test
	public void isNotNullDao() {
		Assert.assertNotNull(municipioDao);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void search(){
		Map map = new HashMap();
		map.put("estado", EstadoFactory.newInstance(1L));		
		Mockito.when(municipioDao.search(map.entrySet())).thenReturn(getMunicipios());		
		Collection<Municipio> municipios= municipioService.search(map);		
		Assert.assertNotNull(municipios);
		Assert.assertFalse(municipios.isEmpty());
		Assert.assertEquals(4, municipios.size());
		Assert.assertTrue(municipios.contains(MunicipioFactory.newInstance(1L)));
	}
	
	private static Collection<Municipio> getMunicipios() {
		Collection<Municipio> municipios = new ArrayList<Municipio>();
		municipios.add(MunicipioFactory.newInstance(1L));
		municipios.add(MunicipioFactory.newInstance(2L));
		municipios.add(MunicipioFactory.newInstance(3L));
		municipios.add(MunicipioFactory.newInstance(4L));
		return municipios;
	}
}
