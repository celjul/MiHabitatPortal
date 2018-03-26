package com.bstmexico.mihabitat.comunes.usuarios.test.dao;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.bstmexico.mihabitat.comunes.dummy.UsuarioDummy;
import com.bstmexico.mihabitat.comunes.usuarios.dao.UsuarioDao;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup(value = { "/datasets/catalogos.xml", "/datasets/personas.xml", "/datasets/usuarios.xml" },
		type = DatabaseOperation.CLEAN_INSERT)
//@DatabaseTearDown(value = "/datasets/tear_down.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsuarioDaoTest {
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Test
	public void isNotNull() {
		Assert.assertNotNull(usuarioDao);
	}
	
	@Test
	public void testCrearUsuario() {
		Usuario usuario = UsuarioDummy.getUsuarioDummy();
		usuario.getPersona().setId(1L);
		
		usuarioDao.save(usuario);
		Assert.assertNotNull(usuario.getId());
	}
	
	@Test
	public void testGetUsuario() {
		Usuario usuario = usuarioDao.getByUserName("user1");
		Assert.assertEquals("usuario@dominio.com", usuario.getEmail());
		
		Assert.assertNotNull(usuario.getPersona());
		Assert.assertEquals("Ortiz", usuario.getPersona().getApellidoMaterno());
		Assert.assertEquals("Cardenas", usuario.getPersona()
				.getApellidoPaterno());
		Assert.assertEquals("Luis Angel", usuario.getPersona().getNombre());
	}
	
	@Test
	public void testGetList() {
//		usuarioDao.getByRol(roles);
	}
}
