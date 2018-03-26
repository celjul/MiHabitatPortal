package com.bstmexico.mihabitat.contactos.test.dao;

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
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.dao.ContactoDao;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.test.dummy.ContactoDummy;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@ContextConfiguration(locations = { "/spring-context.xml" })
@DatabaseSetup(value = { "/datasets/catalogos.xml", "/datasets/paises.xml", 
		"/datasets/estados.xml", "/datasets/municipios.xml", 
		"/datasets/colonias.xml", "/datasets/personas.xml", 
		"/datasets/usuarios.xml", "/datasets/condominios.xml", 
		"/datasets/mantenimientos.xml", "/datasets/contactos.xml",
		"/datasets/departamentos.xml", "/datasets/grupos.xml"
		})
//@DatabaseTearDown(value = "/datasets/tear_down_1.xml", type = DatabaseOperation.TRUNCATE_TABLE)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class ContactoDaoTests {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContactoDaoTests.class);
	
	@Autowired
	private ContactoDao contactoDao;
	
	@Test
	public void testNotNull() {
		Assert.assertNotNull(contactoDao);
	}
	
	@Test
	public void guardarContacto() {
		Condominio condominio = CondominioFactory.newInstance(1L);
		Contacto contacto = ContactoDummy.getFullContacto(condominio, 0);
		
		contactoDao.save(contacto);
	}
	
	@Test
	public void testGetContacto() {
		Condominio condominio = CondominioFactory.newInstance(1L);
		
		Contacto contacto = contactoDao.get(condominio, "usuario@dominio.com");
		Assert.assertNotNull(contacto);
	}
}
