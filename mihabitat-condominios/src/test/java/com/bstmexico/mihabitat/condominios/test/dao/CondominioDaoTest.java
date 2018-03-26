package com.bstmexico.mihabitat.condominios.test.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

import com.bstmexico.mihabitat.comunes.direcciones.factory.ColoniaFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.DireccionFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Colonia;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.usuarios.factory.UsuarioFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.dao.CondominioDao;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.DireccionCondominio;
import com.bstmexico.mihabitat.condominios.test.utils.StringUtilsTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@ContextConfiguration(locations = { "/spring-context.xml" })
@DatabaseSetup(value = { "/datasets/paises.xml", "/datasets/estados.xml",
		"/datasets/municipios.xml", "/datasets/colonias.xml",
		"/datasets/personas.xml", "/datasets/usuarios.xml",
		"/datasets/condominios.xml" })
@DatabaseTearDown(value = "/datasets/tear_down.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
DbUnitTestExecutionListener.class 

})
public class CondominioDaoTest {

	private static final Logger LOG = LoggerFactory
			.getLogger(CondominioDaoTest.class);

	@Autowired
	private CondominioDao condominioDao;

	@Test
	public void _001_isNotNullDao() {
		LOG.debug("Comprobando que el dao no sea nulo.");
		Assert.assertNotNull(condominioDao);
	}

	@Test(expected = DataAccessException.class)
	public void _002_saveNull() {
		Condominio condominio = null;
		condominioDao.save(condominio);
	}

	@Test
	public void _003_save() {
		Condominio condominio = getCondominio();
		condominioDao.save(condominio);
		Assert.assertNotNull(condominio.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _004_createEmpty() {
		Condominio condominio = CondominioFactory.newInstance();
		condominioDao.save(condominio);
	}

	@Test(expected = DataAccessException.class)
	public void _005_saveNombreNull() {
		Condominio condominio = getCondominio();
		condominio.setNombre(null);
		condominioDao.save(condominio);
		Assert.assertNotNull(condominio.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _006_saveNombreEmpty() {
		Condominio condominio = getCondominio();
		condominio.setNombre(new String());
		condominioDao.save(condominio);
		Assert.assertNotNull(condominio.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _007_saveNombreMaxLength() {
		Condominio condominio = getCondominio();
		condominio.setNombre(StringUtilsTest.insertChars("Polanco", 128));
		condominioDao.save(condominio);
		Assert.assertNotNull(condominio.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _008_saveAdministradoresNull() {
		Condominio condominio = getCondominio();
		condominio.setAdministradores(null);
		condominioDao.save(condominio);
		Assert.assertNotNull(condominio.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _009_saveAdministradoresEmpty() {
		Condominio condominio = getCondominio();
		condominio.setAdministradores(new HashSet<Usuario>());
		condominioDao.save(condominio);

	}

	@Test(expected = DataAccessException.class)
	public void _010_saveAdministradoresNoExsitente() {
		Condominio condominio = getCondominio();
		condominio.getAdministradores().add(UsuarioFactory.newInstance(0L));
		condominioDao.save(condominio);

	}

	@Test(expected = DataAccessException.class)
	public void _011_saveDireccionNull() {
		Condominio condominio = getCondominio();
		condominio.setAdministradores(null);
		condominioDao.save(condominio);
		Assert.assertNotNull(condominio.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _012_saveDireccionEmpty() {
		Condominio condominio = getCondominio();
		condominio.setDireccion((DireccionCondominio) DireccionFactory
				.newInstance(DireccionCondominio.class));
		condominioDao.save(condominio);

	}

	@Test(expected = DataAccessException.class)
	public void _013_saveDireccionsNoExsitente() {
		Condominio condominio = getCondominio();
		condominio.setDireccion((DireccionCondominio) DireccionFactory.newInstance(
				DireccionCondominio.class, 0L));
		condominioDao.save(condominio);

	}

	@Test
	public void _014_get() {
		Long id = 1L;
		Condominio condominio = condominioDao.get(id);
		Assert.assertNotNull(condominio);
		Assert.assertNotNull(condominio.getNombre());
		Assert.assertNotNull(condominio.getAdministradores());
		Assert.assertNotNull(condominio.getDireccion());
		Assert.assertNotNull(condominio.getId());
		Assert.assertFalse(condominio.getNombre().isEmpty());
		Assert.assertFalse(condominio.getAdministradores().isEmpty());
		Assert.assertEquals(condominio.getNombre(), "Condominio Polanco2");
		Assert.assertEquals(condominio.getDireccion(),
				DireccionFactory.newInstance(DireccionCondominio.class, 1L));
		Assert.assertEquals(condominio.getAdministradores().size(), 2);
	}

	@Test(expected = DataAccessException.class)
	public void _015_getNull() {
		Long id = null;
		Condominio condominio = condominioDao.get(id);
		Assert.assertNull(condominio);
	}

	@Test
	public void _016_getInexistente() {
		Long id = 0L;
		Condominio condominio = condominioDao.get(id);
		Assert.assertNull(condominio);

	}

	@Test(expected = DataAccessException.class)
	public void _017_updateNull() {
		Condominio condominio = null;
		condominioDao.update(condominio);
	}

	@Test(expected = DataAccessException.class)
	public void _18_updateEmpty() {
		Condominio condominio = CondominioFactory.newInstance();
		condominioDao.update(condominio);
	}

	@Test(expected = DataAccessException.class)
	public void _019_updateNombreNull() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setNombre(null);
		condominioDao.update(condominio);
	}

	@Test(expected = DataAccessException.class)
	public void _020_updateUsuarioNull() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setAdministradores(null);
		condominioDao.update(condominio);
	}

	@Test(expected = DataAccessException.class)
	public void _021_updateDireccionNull() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setDireccion(null);
		condominioDao.update(condominio);
	}

	@Test(expected = DataAccessException.class)
	public void _022_updateNombreEmpty() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setNombre(new String());
		condominioDao.update(condominio);
	}

	@Test(expected = DataAccessException.class)
	public void _023_updateDireccionEmpty() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setDireccion((DireccionCondominio) DireccionFactory
				.newInstance(DireccionCondominio.class));
		condominioDao.update(condominio);
	}


	@Test(expected = DataAccessException.class)
	public void _024_updateUsuarioEmpty() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setAdministradores(new HashSet<Usuario>());
		condominioDao.update(condominio);
	}

	@Test(expected = DataAccessException.class)
	public void _025_updateNombreMaxLength() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setNombre(StringUtilsTest.insertChars("Polanco", 129));
		condominioDao.update(condominio);
	}
	@Test(expected = DataAccessException.class)
	public void _026_updateNombreNull() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setNombre(null);
		condominioDao.update(condominio);
		
	}
	@Test
	public void _027_updateDireccion() {
		Condominio condominio = condominioDao.get(1L);
		condominio.getDireccion().setCalle("12");
		condominioDao.update(condominio);
		Condominio condominioBD = condominioDao.get(1L);
		Assert.assertEquals(condominio.getDireccion(), condominioBD.getDireccion());
	}
	@Test(expected = DataAccessException.class)
	public void _028_updateDireccionNull() {
		Condominio condominio = condominioDao.get(1L);
		condominio.getDireccion().setCalle(null);
		condominioDao.update(condominio);
		
	}
	@Test
	public void _029_updateUsuariosHuerfanos() {
		Condominio condominio =  condominioDao.get(1L);
		condominio.getAdministradores().remove(UsuarioFactory.newInstance(1L));
		condominioDao.update(condominio);
		Condominio condominioBD = condominioDao.get(1L);
		Assert.assertFalse(condominioBD.getAdministradores().contains(UsuarioFactory.newInstance(1L)));
		Assert.assertEquals(1, condominioBD.getAdministradores().size());
	}


	@Test
	public void _030_updateNombre() {
		Condominio condominio = condominioDao.get(1L);
		condominio.setNombre("Polanco 4");
		condominioDao.update(condominio);
		Condominio condominioBD = condominioDao.get(1L);
		Assert.assertTrue(condominio != condominioBD);
		Assert.assertEquals(condominio.getNombre(), condominioBD.getNombre());
	
	}
	@Test
	public void _031_updateColonia() {
		Condominio condominio = condominioDao.get(1L);
	
		condominio.getDireccion().setColonia(ColoniaFactory.newInstance(2L));
		condominioDao.update(condominio);
		Condominio condominioBD = condominioDao.get(1L);
		Assert.assertTrue(condominio != condominioBD);
		Assert.assertEquals(condominio.getDireccion(), condominioBD.getDireccion());
	}
	

	@Test
	public void _032_getList() {
		Collection<Condominio> condominios = condominioDao.getList();
		Assert.assertNotNull(condominios);
		Assert.assertFalse(condominios.isEmpty());
		Assert.assertEquals(2, condominios.size());
	}
	@Test
	public void _33_search(){
		Long id = 1L;
		Usuario usuario = UsuarioFactory.newInstance(id);
		Collection<Condominio> condominios =  condominioDao.search(usuario);
		Assert.assertEquals(1,condominios.size());
		for (Condominio con : condominios) {
			Assert.assertEquals("Condominio Polanco2", con.getNombre());
			
		}
		
	}
	@SuppressWarnings("unused")
	@Test(expected = DataAccessException.class)
	public void _34_searchEmpty(){
		
		Usuario usuario = UsuarioFactory.newInstance();
		Collection<Condominio> condominios =  condominioDao.search(usuario);
		
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
			usuarios.add(UsuarioFactory.newInstance(3L));
		
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

}
