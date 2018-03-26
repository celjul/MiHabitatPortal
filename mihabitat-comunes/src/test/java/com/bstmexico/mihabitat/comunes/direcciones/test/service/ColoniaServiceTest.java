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

import com.bstmexico.mihabitat.comunes.direcciones.dao.ColoniaDao;
import com.bstmexico.mihabitat.comunes.direcciones.factory.ColoniaFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.MunicipioFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Colonia;
import com.bstmexico.mihabitat.comunes.direcciones.service.ColoniaService;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/spring-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class ColoniaServiceTest {

	@Autowired
	private ColoniaService coloniaService;
	
	@ReplaceWithMock
	@Autowired	
	private ColoniaDao coloniaDao;	
	
	@Test
	public void isNotNullService(){
		Assert.assertNotNull(coloniaService);
	}
	
	@Test
	public void isNotNullDao(){
		Assert.assertNotNull(coloniaDao);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void search() {
		Map map = new HashMap();
		map.put("pais", MunicipioFactory.newInstance(1L));
		Mockito.when(coloniaDao.search(map.entrySet())).thenReturn(getColonias());
		Collection<Colonia> colonias = coloniaService.search(map);
		Assert.assertNotNull(colonias);
		Assert.assertFalse(colonias.isEmpty());
		Assert.assertEquals(3, colonias.size());
		Assert.assertTrue(colonias.contains(ColoniaFactory.newInstance(1L)));
	}
	
	private static Collection<Colonia> getColonias() {
		Collection<Colonia> colonias = new ArrayList<Colonia>();
		colonias.add(ColoniaFactory.newInstance(1L));
		colonias.add(ColoniaFactory.newInstance(2L));
		colonias.add(ColoniaFactory.newInstance(3L));
		return colonias;
	}

}
