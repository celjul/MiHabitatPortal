package com.bstmexico.mihabitat.departamentos.test.dao;

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

import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.dao.DepartamentoDao;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.test.dummy.DepartamentoDummy;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@ContextConfiguration(locations = { "/spring-context.xml" })
@DatabaseSetup(value = { 
		"/datasets/paises.xml", "/datasets/estados.xml",
		"/datasets/municipios.xml", "/datasets/colonias.xml",
		"/datasets/personas.xml",
		"/datasets/usuarios.xml", "/datasets/condominios.xml",
		"/datasets/grupos_condominio.xml",
		"/datasets/mantenimientos_condominio.xml",
		"/datasets/contactos.xml",
		"/datasets/departamentos.xml" 
	
		 })
@DatabaseTearDown(value = "/datasets/tear_down.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class DepartamentoDaoTests {
	private static final Logger LOG= LoggerFactory
			.getLogger(DepartamentoDaoTests.class);
	@Autowired
	private DepartamentoDao departamentoDao;
	
	@Test
	public void isNotNullDao() {
		LOG.debug("Comprobando que el dao no sea nulo.");
		Assert.assertNotNull(departamentoDao);
	}

	@Test(expected = DataAccessException.class)
	public void saveNull() {
		Departamento departamento = null;
		departamentoDao.save(departamento);
	}

	@Test
	public void saveDepartamento() {
		Departamento departamento = DepartamentoDummy.crearDepartamentoDummy();
		departamento.setGrupos(null);
		departamentoDao.save(departamento);
		
		Assert.assertNotNull(departamento.getId());
	}

	@Test
	public void saveDepartamentoContactoSinId() {
		Departamento departamento = DepartamentoDummy.crearDepartamentoDummy();
		departamento.getContactos().iterator().next().getContacto().setId(null);
		departamento.setGrupos(null);
		departamentoDao.save(departamento);
		
		Assert.assertNotNull(departamento.getId());
	}
	
	@Test
	public void saveDepartamentoSinIdContactoSinIdPersona() {
		Departamento departamento = DepartamentoDummy.crearDepartamentoDummy();
		Contacto contacto = departamento.getContactos().iterator().next().getContacto(); 
		contacto.setId(null);
//		contacto.getPersona().setId(null);
		
		departamento.setGrupos(null);
		departamentoDao.save(departamento);
		
		Assert.assertNotNull(departamento.getId());
	}
	
	@Test
	public void saveDepartamentoContactoConId() {
		Departamento departamento = DepartamentoDummy.crearDepartamentoDummy();
//		departamento.getContactos().iterator().next().getContacto().setId(null);
		departamento.setGrupos(null);
		departamentoDao.save(departamento);
		
		Assert.assertNotNull(departamento.getId());
	}
}
