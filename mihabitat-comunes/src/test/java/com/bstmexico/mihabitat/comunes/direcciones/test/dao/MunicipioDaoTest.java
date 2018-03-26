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

import com.bstmexico.mihabitat.comunes.direcciones.dao.MunicipioDao;
import com.bstmexico.mihabitat.comunes.direcciones.factory.EstadoFactory;
import com.bstmexico.mihabitat.comunes.direcciones.factory.MunicipioFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Municipio;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.comunes.test.utils.StringUtilsTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;


@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(locations = { "/spring-context.xml" })
@DatabaseSetup(value = { "/datasets/paises.xml", "/datasets/estados.xml", "/datasets/municipios.xml" }, type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value = "/datasets/tear_down.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
public class MunicipioDaoTest {
	
	@Autowired
	private MunicipioDao municipioDao;

	@Test
	public void notNullDao() {
		Assert.assertNotNull(municipioDao);
	}

	@Test
	public void crear(){
		Municipio municipio= crearMunicipio();
		municipioDao.save(municipio);
	}		

	@Test(expected= DataAccessException.class)
	public void crearNull(){
		Municipio municipio= null;
		municipioDao.save(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void crearEmpty(){
		Municipio municipio= MunicipioFactory.newInstance();
		municipioDao.save(municipio);
	}
	
	
	@Test(expected = DataAccessException.class)
	public void crearEstadoNull(){
		Municipio municipio= crearMunicipio();
		municipio.setEstado(null);
		municipioDao.save(municipio);
	}
	
	@Test(expected = DataAccessException.class)
	public void crearEstadoEmpty(){
		Municipio municipio= crearMunicipio();
		municipio.setEstado(EstadoFactory.newInstance());
		municipioDao.save(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void crearEstadoNoExistente(){
		Municipio municipio= crearMunicipio();
		municipio.setEstado(EstadoFactory.newInstance(0L));
		municipioDao.save(municipio);
	}			
	
	@Test(expected= DataAccessException.class)
	public void crearNombreNull(){
		Municipio municipio= crearMunicipio();
		municipio.setNombre(null);
		municipioDao.save(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void crearNombreEmpty(){
		Municipio municipio= crearMunicipio();
		municipio.setNombre(new String());
		municipioDao.save(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void crearNombreLength(){
		Municipio municipio= crearMunicipio();
		municipio.setNombre(StringUtilsTest.insertChars("OaxacaPrueba", 256));
		municipioDao.save(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void updateNull(){
		Municipio municipio= null;
		municipioDao.update(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void updateEmpty(){
		Municipio municipio= MunicipioFactory.newInstance();
		municipioDao.update(municipio);
	}	
	
	@Test(expected= DataAccessException.class)
	public void updateEstadoNull(){
		Municipio municipio= municipioDao.get(1L);		
		municipio.setEstado(null);
		municipioDao.update(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void updateEstadoEmpty(){
		Municipio municipio= municipioDao.get(1L);
		municipio.setEstado(EstadoFactory.newInstance());
		municipioDao.update(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void updateEstadoNotExistence(){
		Municipio municipio= municipioDao.get(1L);
		municipio.setEstado(EstadoFactory.newInstance(0L));
		municipioDao.update(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void updateNombreNull(){
		Municipio municipio= municipioDao.get(1L);
		municipio.setNombre(null);
		municipioDao.update(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void updateNombreEmpty(){
		Municipio municipio= municipioDao.get(1L);
		municipio.setNombre(new String());
		municipioDao.update(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void updateNombreLength(){
		Municipio municipio= municipioDao.get(1L);
		municipio.setNombre(StringUtilsTest.insertChars("OaxacaModificado", 256));
		municipioDao.update(municipio);
	}
	
	@Test
	public void _029_delete(){
		Long id= 1L;
		municipioDao.delete(MunicipioFactory.newInstance(id));
		Assert.assertNull(municipioDao.get(id));
	}
	
	@Test(expected= DataAccessException.class)
	public void _030_deleteNull(){
		Municipio municipio= null;
		municipioDao.delete(municipio);
	}
	
	@Test
	public void _031_deleteEmpty(){
		Municipio municipio= MunicipioFactory.newInstance();
		municipioDao.delete(municipio);
	}
	
	@Test(expected= DataAccessException.class)
	public void _032_deleteIdNotExistence(){
		Long id= 0L;
		Municipio municipio= MunicipioFactory.newInstance(id);
		municipioDao.delete(municipio);
	}
	
	
	public static Municipio crearMunicipio(){
		Municipio municpio= MunicipioFactory.newInstance();
		municpio.setEstado(EstadoFactory.newInstance(1L));
		municpio.setNombre("MunicipioNuevo");
		return municpio;
	}	
}
