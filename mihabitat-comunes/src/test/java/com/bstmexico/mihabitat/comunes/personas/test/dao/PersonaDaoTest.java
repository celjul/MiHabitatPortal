package com.bstmexico.mihabitat.comunes.personas.test.dao;

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

import com.bstmexico.mihabitat.comunes.dummy.PersonaDummy;
import com.bstmexico.mihabitat.comunes.personas.dao.PersonaDao;
import com.bstmexico.mihabitat.comunes.personas.factory.PersonaFactory;
import com.bstmexico.mihabitat.comunes.personas.model.Persona;
import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup(value = {"classpath:/datasets/catalogos.xml", 
		"classpath:/datasets/personas.xml" }, type = DatabaseOperation.CLEAN_INSERT)
//@DatabaseTearDown(value = "/datasets/tear_down.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonaDaoTest {
	@Autowired
	private PersonaDao personaDao;
	
	@Test
	public void isNotNull() {
		Assert.assertNotNull(personaDao);
	}

	@Test
	public void crearPersona() {
		Persona persona = PersonaDummy.getPersonaDummy(1, 1);
		
		personaDao.save(persona);
	}
	
	@Test
	public void crearPersonaMuchosEmails() {
		Persona persona = PersonaDummy.getPersonaDummy(3, 5);
		
		personaDao.save(persona);
	}
}
