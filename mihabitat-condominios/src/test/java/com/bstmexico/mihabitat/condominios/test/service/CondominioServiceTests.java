package com.bstmexico.mihabitat.condominios.test.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

import com.bstmexico.mihabitat.comunes.direcciones.factory.ColoniaFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.DireccionFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Colonia;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.usuarios.factory.UsuarioFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.dao.CondominioDao;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.DireccionCondominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.condominios.test.utils.StringUtilsTest;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "/spring-context.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
public class CondominioServiceTests {
	@Autowired
	private CondominioService condominioService;
	
	@ReplaceWithMock
	@Autowired
	private CondominioDao condominioDao;
	
	@Test
	public void _001_isNotNullDao(){
		Assert.assertNotNull(condominioDao);
	}
	
	@Test
	public void _002_isNotNullService(){
		Assert.assertNotNull(condominioService);
	}
	
	@Test
	public void _003_save(){
		Condominio condominio=  getCondominio();
		condominioService.save(condominio);
		Mockito.verify(condominioDao, Mockito.atLeastOnce()).save(condominio);
	}
	
	@Test(expected = ServiceException.class)
	public void _004_createNull(){
		Condominio condominio=null;
		condominioService.save(condominio);
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);
	}
	
	@Test(expected = ServiceException.class)
	public void _005_createEmpty(){
		Condominio condominio=CondominioFactory.newInstance();
		condominioService.save(condominio);
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);
	}
	@Test(expected = ServiceException.class)
	public void _006_saveNombreNull() {
		Condominio condominio = getCondominio();
		condominio.setNombre(null);
		condominioService.save(condominio);
		Assert.assertNotNull(condominio.getId());
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);
	}
	@Test(expected = ServiceException.class)
	public void _007_saveNombreEmpty() {
		Condominio condominio = getCondominio();
		condominio.setNombre(new String());
		condominioService.save(condominio);
		Assert.assertNotNull(condominio.getId());
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _008_saveNombreMaxLength() {
		Condominio condominio = getCondominio();
		condominio.setNombre(StringUtilsTest.insertChars("Polanco", 128));
		condominioService.save(condominio);
		Assert.assertNotNull(condominio.getId());
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);
	}
	@Test(expected = ServiceException.class)
	public void _009_saveAdministradoresNull() {
		Condominio condominio = getCondominio();
		condominio.setAdministradores(null);
		condominioService.save(condominio);
		Assert.assertNotNull(condominio.getId());
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _010_saveAdministradoresEmpty() {
		Condominio condominio = getCondominio();
		condominio.setAdministradores(new HashSet<Usuario>());
		condominioService.save(condominio);
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);

	}
	@Test(expected = ServiceException.class)
	public void _012_saveDireccionNull() {
		Condominio condominio = getCondominio();
		condominio.setAdministradores(null);
		condominioService.save(condominio);
		Assert.assertNotNull(condominio.getId());
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _013_saveDireccionEmpty() {
		Condominio condominio = getCondominio();
		condominio.setDireccion((DireccionCondominio) DireccionFactory
				.newInstance(DireccionCondominio.class));
		condominioService.save(condominio);
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);

	}

	@Test(expected = ServiceException.class)
	public void _014_saveDireccionsNoExsitente() {
		Condominio condominio = getCondominio();
		condominio.setDireccion((DireccionCondominio) DireccionFactory.newInstance(
				DireccionCondominio.class, 0L));
		condominioService.save(condominio);
		Mockito.verify(condominioDao, Mockito.never()).save(condominio);

	}
	@Test
	public void _014_get() {
		Long id = 1L;
		Mockito.when(condominioDao.get(id)).thenReturn(getCondominio());
		Condominio condominio = condominioService.get(id);
		Assert.assertNotNull(condominio);
		Assert.assertFalse(condominio.getNombre().isEmpty());
		Assert.assertFalse(condominio.getAdministradores().isEmpty());
		Assert.assertEquals(condominio.getNombre(),"Condominio Polanco");
		Assert.assertEquals(condominio.getAdministradores().size(), 2);
		Assert.assertEquals(condominio.getDireccion(), getDireccion());
	}
	@Test(expected = ServiceException.class)
	public void _015_getNull() {
		Long id = null;
		Condominio condominio = condominioService.get(id);
		Assert.assertNull(condominio);
		Mockito.verify(condominioDao, Mockito.never()).get(id);
	}
	@Test(expected = ServiceException.class)
	public void _016_updateNull() {
		Condominio condominio = null;
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _17_updateEmpty() {
		Condominio condominio = CondominioFactory.newInstance();
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _018_updateNombreNull() {
		Condominio condominio = getCondominio();
		condominio.setNombre(null);
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _019_updateUsuarioNull() {
		Condominio condominio = getCondominio();
		condominio.setAdministradores(null);
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _020_updateDireccionNull() {
		Condominio condominio = getCondominio();
		condominio.setDireccion(null);
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _021_updateNombreEmpty() {
		Condominio condominio = getCondominio();
		condominio.setNombre(new String());
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _022_updateDireccionEmpty() {
		Condominio condominio = getCondominio();
		condominio.setDireccion((DireccionCondominio) DireccionFactory
				.newInstance(DireccionCondominio.class));
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}


	@Test(expected = ServiceException.class)
	public void _023_updateUsuarioEmpty() {
		Condominio condominio = getCondominio();
		condominio.setAdministradores(new HashSet<Usuario>());
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}

	@Test(expected = ServiceException.class)
	public void _024_updateNombreMaxLength() {
		Condominio condominio = getCondominio();
		condominio.setNombre(StringUtilsTest.insertChars("Polanco", 129));
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
	}
	@Test(expected = ServiceException.class)
	public void _025_updateNombreNull() {
		Condominio condominio = getCondominio();
		condominio.setNombre(null);
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
		
	}
	
	@Test(expected = ServiceException.class)
	public void _027_updateDireccionNull() {
		Condominio condominio = getCondominio();
		condominio.getDireccion().setCalle(null);
		condominioService.update(condominio);
		Mockito.verify(condominioDao, Mockito.never()).update(condominio);
		
	}
	@Test
	public void _028_getList() {
		Mockito.when(condominioDao.getList()).thenReturn(getCondominios());
		Collection<Condominio> condominios = condominioService.getList();
		Assert.assertNotNull(condominios);
		Assert.assertFalse(condominios.isEmpty());
		Assert.assertEquals(2, condominios.size());
		
	}

	
	private static Condominio getCondominio() {
		Condominio condominio = CondominioFactory.newInstance();
		condominio.setNombre("Condominio Polanco");
		condominio.setAdministradores(getUsuarios());
		condominio.setDireccion(getDireccion());

		return condominio;
	}

	private static Set<Usuario> getUsuarios() {
		Set<Usuario> usuarios = new HashSet<Usuario>();
		usuarios.add(UsuarioFactory.newInstance(1L));
		usuarios.add(UsuarioFactory.newInstance(2L));

		return usuarios;
	}

	private static DireccionCondominio getDireccion() {
		DireccionCondominio direccion = DireccionFactory
				.newInstance(DireccionCondominio.class);
		Colonia col = ColoniaFactory.newInstance(1L);
		direccion.setColonia(col);
		direccion.setCalle("calle1");
		direccion.setNoExterior("10");
		direccion.setNoInterior("15");
		direccion.setReferencias("cerca de una farmacia");
		return direccion;
	}
	private static Collection<Condominio> getCondominios() {
		Collection<Condominio> condominios = new ArrayList<Condominio>();
		condominios.add(CondominioFactory.newInstance(1L));
		condominios.add(CondominioFactory.newInstance(2L));
		
		return condominios;
	}
	
	

}
