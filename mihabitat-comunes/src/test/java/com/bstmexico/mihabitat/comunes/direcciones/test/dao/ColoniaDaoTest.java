package com.bstmexico.mihabitat.comunes.direcciones.test.dao;

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

import com.bstmexico.mihabitat.comunes.direcciones.dao.ColoniaDao;
import com.bstmexico.mihabitat.comunes.direcciones.factory.ColoniaFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.MunicipioFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Colonia;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.test.utils.StringUtilsTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup(value = { "/datasets/paises.xml", "/datasets/estados.xml", "/datasets/municipios.xml", "/datasets/colonias.xml" }, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/datasets/tear_down.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ColoniaDaoTest {
	
	@Autowired
	private ColoniaDao coloniaDao;
	
	@Test
	public void notNullDao(){
		Assert.assertNotNull(coloniaDao);
	}
	
	@Test
	public void create(){
		Colonia colonia= crearColonia();
		coloniaDao.save(colonia);
	}

	@Test(expected = DataAccessException.class)
	public void createNull(){
		Colonia colonia =null;
		coloniaDao.save(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void creteEmpty(){
		Colonia colonia= ColoniaFactory.newInstance();
		coloniaDao.save(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void createNombreNull(){
		Colonia colonia=crearColonia();
		colonia.setNombre(null);
		coloniaDao.save(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void createNombreEmpty(){
		Colonia colonia= crearColonia();
		colonia.setNombre(new String());
		coloniaDao.save(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void createNombreLength(){
		Colonia colonia= crearColonia();
		colonia.setNombre(StringUtilsTest.insertChars("holaOaxaca", 256));
		coloniaDao.save(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void createCPNull(){
		Colonia colonia=crearColonia();
		colonia.setCodigoPostal(null);
		coloniaDao.save(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void createCPEmpty(){
		Colonia colonia= crearColonia();
		colonia.setCodigoPostal(new String());
		coloniaDao.save(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void createCPLength(){
		Colonia colonia= crearColonia();
		colonia.setCodigoPostal(StringUtilsTest.insertChars("918273", 5));
		coloniaDao.save(colonia);
	}
	
	@Test(expected= DataAccessException.class)
	public void createMunicipioNull(){
		Colonia colonia= crearColonia();
		colonia.setMunicipio(null);
		coloniaDao.save(colonia);
	}
	
	@Test(expected= DataAccessException.class)
	public void createMunicipioEmpty(){
		Colonia colonia= crearColonia();
		colonia.setMunicipio(MunicipioFactory.newInstance());
		coloniaDao.save(colonia);
	}
	
	@Test(expected= DataAccessException.class)
	public void createMunicipioNotExistence(){
		Colonia colonia= crearColonia();
		colonia.setMunicipio(MunicipioFactory.newInstance(0L));
		coloniaDao.save(colonia);
	}

	
	@Test
	public void update(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setNombre("Actualizacion Oaxaca");
		colonia.setCodigoPostal("65758");		
		colonia.setMunicipio(MunicipioFactory.newInstance(3L));
		coloniaDao.update(colonia);
		Colonia coloniaDB= coloniaDao.get(1L);
		Assert.assertTrue(colonia != coloniaDB);
		Assert.assertEquals(colonia.getNombre(), coloniaDB.getNombre());
		Assert.assertEquals(colonia.getMunicipio(), coloniaDB.getMunicipio());
	}
	
	@Test(expected = DataAccessException.class)
	public void updateNull() {
		Colonia colonia= null;
		coloniaDao.update(colonia);
	}

	@Test(expected = DataAccessException.class)
	public void updateEmpty() {
		Colonia colonia= ColoniaFactory.newInstance();
		coloniaDao.update(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void updateNombreNull(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setNombre(null);
		coloniaDao.update(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void updateNombreEmpty(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setNombre(new String());
		coloniaDao.update(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void updateNombreLength(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setNombre(StringUtilsTest.insertChars("Oaxaca actualiza", 512));
		coloniaDao.update(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void updateCPNull(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setCodigoPostal(null);
		coloniaDao.update(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void updateCPEmpty(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setCodigoPostal(new String());
		coloniaDao.update(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void updateCPLength(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setCodigoPostal(StringUtilsTest.insertChars("23213", 5));
		coloniaDao.update(colonia);
	}
		
	@Test(expected = DataAccessException.class)
	public void updateMunicipioNull(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setMunicipio(null);
		coloniaDao.update(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void updateMunicipioEmpty(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setMunicipio(MunicipioFactory.newInstance());
		coloniaDao.update(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void updateMunicipioNotExistence(){
		Colonia colonia= coloniaDao.get(1L);
		colonia.setMunicipio(MunicipioFactory.newInstance(0L));
		coloniaDao.update(colonia);
	}
	
	@Test
	public void delete(){
		Long id= 1L;
		coloniaDao.delete(ColoniaFactory.newInstance(id));
		Assert.assertNull(coloniaDao.get(id));
	}
	
	@Test(expected = DataAccessException.class)
	public void deleteNull(){
		Colonia colonia= null;
		coloniaDao.delete(colonia);		
	}
	
	@Test
	public void deleteEmpty(){
		Colonia colonia= ColoniaFactory.newInstance();
		coloniaDao.delete(colonia);
	}
	
	@Test(expected = DataAccessException.class)
	public void deleteNotExistence(){
		Long id= 0L;
		coloniaDao.delete(ColoniaFactory.newInstance(id));
		Assert.assertNull(coloniaDao.get(id));
	}
		
	private static Colonia crearColonia(){
		Colonia colonia= ColoniaFactory.newInstance();
		colonia.setCodigoPostal("68274");
		colonia.setNombre("Croc");		
		colonia.setMunicipio(MunicipioFactory.newInstance(1L));
		return colonia;
	}

}
