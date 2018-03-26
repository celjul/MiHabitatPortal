package com.bstmexico.mihabitat.instalaciones.test.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalTime;
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

import com.bstmexico.mihabitat.comunes.catalogos.dao.impl.CatalogoDaoImpl;
import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.instalaciones.dao.InstalacionDao;
import com.bstmexico.mihabitat.instalaciones.factory.DisponibilidadFactory;
import com.bstmexico.mihabitat.instalaciones.factory.InstalacionFactory;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoUnidadInstalacion;
import com.bstmexico.mihabitat.instalaciones.model.Disponibilidad;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;
import com.bstmexico.mihabitat.instalaciones.test.utils.StringUtilsTest;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;

@ContextConfiguration(locations = { "/spring-context.xml" })
@DatabaseSetup(value = { "/datasets/catalogos.xml","/datasets/paises.xml", "/datasets/estados.xml",
		"/datasets/municipios.xml", "/datasets/colonias.xml",
		"/datasets/personas.xml", "/datasets/usuarios.xml",
		"/datasets/instalacions.xml",  "/datasets/instalaciones.xml"})
@DatabaseTearDown(value = "/datasets/tear_down.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DbUnitTestExecutionListener.class 

	})
public class InstalacionDaoTest {

	
	private static final Logger LOG = LoggerFactory
			.getLogger(InstalacionDaoTest.class); 

	@Autowired
	private InstalacionDao instalacionDao;

	@Test
	public void _001_isNotNullDao() {
		LOG.debug("Comprobando que el dao no sea nulo.");
		Assert.assertNotNull(instalacionDao);
	}

	@Test(expected = DataAccessException.class)
	public void _002_saveNull() {
		Instalacion instalacion = null;
		instalacionDao.save(instalacion);
	}

	@Test
	public void _003_save() {
		Instalacion instalacion = getInstalacion();
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _004_createEmpty() {
		Instalacion instalacion = InstalacionFactory.newInstance();
		instalacionDao.save(instalacion);
	}

	@Test(expected = DataAccessException.class)
	public void _005_saveNombreNull() {
		Instalacion instalacion = getInstalacion();
		instalacion.setNombre(null);
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _006_saveNombreEmpty() {
		Instalacion instalacion = getInstalacion();
		instalacion.setNombre(new String());
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _007_saveNombreMaxLength() {
		Instalacion instalacion = getInstalacion();
		instalacion.setNombre(StringUtilsTest.insertChars("Alberca", 128));
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _008_saveCondominioNull() {
		Instalacion instalacion = getInstalacion();
		instalacion.setCondominio(null);
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}

	@Test(expected = DataAccessException.class)
	public void _009_saveCondominioEmpty() {
		Instalacion instalacion = getInstalacion();
		instalacion.setCondominio(new Condominio());
		instalacionDao.save(instalacion);

	}

	@Test(expected = DataAccessException.class)
	public void _010_saveCondominioNoExsitente() {
		Instalacion instalacion = getInstalacion();
		instalacion.setCondominio(CondominioFactory.newInstance(0L));
		instalacionDao.save(instalacion);

	}
	
	

	@Test(expected = DataAccessException.class)
	public void _013_saveDescripcionMaxLength() {
		Instalacion instalacion = getInstalacion();
		instalacion.setDescripcion(StringUtilsTest.insertChars("Uso de alberca en instalaciones", 512));
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _014_saveMaxReservacionesNull() {
		Instalacion instalacion = getInstalacion();
		instalacion.setMaximoReservaciones(null);
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _015_saveMaxReservacionesEmpty() {
		Instalacion instalacion = getInstalacion();
		instalacion.setMaximoReservaciones(Integer.parseInt(""));
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _016_saveMaxReservacionesLessThanCero() {
		Instalacion instalacion = getInstalacion();
		instalacion.setMaximoReservaciones(-2);
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	


	@Test(expected = DataAccessException.class)
	public void _019_saveReglamentoMaxLength() {
		Instalacion instalacion = getInstalacion();
		instalacion.setReglamento(StringUtilsTest.insertChars("Uso de alberca en instalaciones", 2048));
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _020_saveCostoNull() {
		Instalacion instalacion = getInstalacion();
		instalacion.setCosto(null);;
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _021_saveCostoLessThanCero() {
		Instalacion instalacion = getInstalacion();
		instalacion.setCosto(BigDecimal.valueOf(-2.33));
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _022_saveCostoEqualsToCero() {
		Instalacion instalacion = getInstalacion();
		instalacion.setCosto(BigDecimal.valueOf(0));
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _023_saveActivoNull() {
		Instalacion instalacion = getInstalacion();
		instalacion.setActivo(null);;
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _023_saveUnidadNull() {
		Instalacion instalacion = getInstalacion();
		instalacion.setUnidad(null);;
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	
	@Test(expected = DataAccessException.class)
	public void _024_saveUnidadNoExistente() {
		Instalacion instalacion = getInstalacion();
		instalacion.setUnidad((CatalogoUnidadInstalacion)(new CatalogoDaoImpl()).get(0L));
		instalacionDao.save(instalacion);
		Assert.assertNotNull(instalacion.getId());
	}
	

	@Test
	public void _025_get() {
		Long id = 1L;
		Instalacion instalacion = instalacionDao.get(id);
		Assert.assertNotNull(instalacion);
		Assert.assertNotNull(instalacion.getNombre());
		Assert.assertNotNull(instalacion.getDescripcion());
		Assert.assertNotNull(instalacion.getCondominio());
		Assert.assertNotNull(instalacion.getReglamento());
		Assert.assertNotNull(instalacion.getCosto());
		Assert.assertNotNull(instalacion.getMaximoReservaciones());
		Assert.assertNotNull(instalacion.getActivo());
		Assert.assertNotNull(instalacion.getUnidad());
		Assert.assertNotNull(instalacion.getId());
		Assert.assertFalse(instalacion.getNombre().isEmpty());
		Assert.assertFalse(instalacion.getDescripcion().isEmpty());
		Assert.assertFalse(instalacion.getReglamento().isEmpty());
		Assert.assertEquals(instalacion.getNombre(), "Sal?n de Eventos");
		Assert.assertEquals(instalacion.getCondominio().getId(), new Long("1"));
		Assert.assertEquals(instalacion.getDescripcion(), "Capacidad para 150 personas...");
		Assert.assertEquals(instalacion.getMaximoReservaciones(), new Integer("1"));
		Assert.assertEquals(instalacion.getReglamento(), "Hacer buen uso de ?l...");
		Assert.assertEquals(instalacion.getCosto(), new BigDecimal("1360.00"));
		Assert.assertEquals(instalacion.getActivo(), new Boolean(true));
		Assert.assertEquals(instalacion.getUnidad().getDescripcion(), "D?a");
		Assert.assertEquals(instalacion.getDisponibilidades().size(), 1);
		Assert.assertEquals(((Disponibilidad)(instalacion.getDisponibilidades().toArray()[0])).getId(), new Long(1));
		//Assert.assertEquals(((Disponibilidad)(instalacion.getDisponibilidades().toArray()[0])).getDia().getDescripcion(), "Lunes");
		Assert.assertEquals(((Disponibilidad)(instalacion.getDisponibilidades().toArray()[0])).getHoraInicio(), "09:00:00");
		Assert.assertEquals(((Disponibilidad)(instalacion.getDisponibilidades().toArray()[0])).getHoraFin(), "18:00:00");
	}

	@Test(expected = DataAccessException.class)
	public void _026_getNull() {
		Long id = null;
		Instalacion instalacion = instalacionDao.get(id);
		Assert.assertNull(instalacion);
	}

	@Test
	public void _027_getInexistente() {
		Long id = 0L;
		Instalacion instalacion = instalacionDao.get(id);
		Assert.assertNull(instalacion);

	}

	@Test(expected = DataAccessException.class)
	public void _028_updateNull() {
		Instalacion instalacion = null;
		instalacionDao.update(instalacion);
	}

	@Test(expected = DataAccessException.class)
	public void _029_updateEmpty() {
		Instalacion instalacion = InstalacionFactory.newInstance();
		instalacionDao.update(instalacion);
	}

	@Test(expected = DataAccessException.class)
	public void _030_updateNombreNull() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setNombre(null);
		instalacionDao.update(instalacion);
	}

	@Test(expected = DataAccessException.class)
	public void _031_updateCondominioNull() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setCondominio(null);
		instalacionDao.update(instalacion);
	}

	@Test(expected = DataAccessException.class)
	public void _032_updateMaximoReservacionesNull() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setMaximoReservaciones(null);;
		instalacionDao.update(instalacion);
	}
	
	@Test(expected = DataAccessException.class)
	public void _033_updateCostoNull() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setCosto(null);;
		instalacionDao.update(instalacion);
	}
	
	@Test(expected = DataAccessException.class)
	public void _034_updateUnidadNull() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setUnidad(null);;
		instalacionDao.update(instalacion);
	}

	@Test(expected = DataAccessException.class)
	public void _035_updateNombreEmpty() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setNombre(new String());
		instalacionDao.update(instalacion);
	}
	

	@Test(expected = DataAccessException.class)
	public void _036_updateNombreMaxLength() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setNombre(StringUtilsTest.insertChars("Gym", 129));
		instalacionDao.update(instalacion);
	}
	
	@Test(expected = DataAccessException.class)
	public void _037_updateMaxReservacionesLessThanCero() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setMaximoReservaciones(-1);;
		instalacionDao.update(instalacion);
	}
	
	@Test(expected = DataAccessException.class)
	public void _038_updateCostoLessThanCero() {
		Instalacion instalacion = instalacionDao.get(1L);
		instalacion.setCosto( new BigDecimal(-1.90));;
		instalacionDao.update(instalacion);
	}
	
	
	

	@Test
	public void _039_getList() {
		Collection<Instalacion> instalacions = instalacionDao.getList();
		Assert.assertNotNull(instalacions);
		Assert.assertFalse(instalacions.isEmpty());
		Assert.assertEquals(1, instalacions.size());
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void _040_search(){
		Long id = 1L;
		Map map = new HashMap();
		Condominio condominio = CondominioFactory.newInstance();
		condominio.setId(id);
		map.put("condominio", condominio);
		Collection<Instalacion> instalacions =  instalacionDao.search(map.entrySet());
		Assert.assertEquals(1,instalacions.size());
		for (Instalacion con : instalacions) {
			Assert.assertEquals("Sal?n de Eventos", con.getNombre());
			
		}
		
	}
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Test(expected = DataAccessException.class)
	public void _34_searchEmpty(){
		
		Map map = new HashMap();
		Condominio condominio = new Condominio();
		
		map.put("condominio", condominio);
		Collection<Instalacion> instalacions =  instalacionDao.search(map.entrySet());
		
	}
	

	private static Instalacion getInstalacion() {
		Instalacion instalacion = InstalacionFactory.newInstance();
		instalacion.setNombre("Sal?n de Eventos2");
		instalacion.setCondominio(getCondominio());
		instalacion.setDescripcion("Capacidad para 150 personas...");
		instalacion.setMaximoReservaciones(3);;
		instalacion.setReglamento("Hacer buen uso de ?l2...");
		instalacion.setCosto(new BigDecimal(2000.00));
		instalacion.setActivo(true);
		instalacion.setUnidad((CatalogoUnidadInstalacion)CatalogoFactory.newInstance(CatalogoUnidadInstalacion.class, 29L));
		instalacion.setDisponibilidades(getDisponibilidades());

		return instalacion;
	}

	private static Collection<Disponibilidad> getDisponibilidades() {
		Disponibilidad disponibilidad = DisponibilidadFactory.newInstance();
		//disponibilidad.setDia((CatalogoDiaSemana)CatalogoFactory.newInstance(CatalogoDiaSemana.class, 1L));
		disponibilidad.setHoraInicio(new LocalTime(9,53,0));
		disponibilidad.setHoraInicio(new LocalTime(20,13,0));
		Collection<Disponibilidad> disponibilidades = new ArrayList<Disponibilidad>();
		
		return disponibilidades;
	}
	
	private static Condominio getCondominio() {
		Condominio condominio = CondominioFactory.newInstance();
		
		condominio.setId(1L);;
		return condominio;
	}
	
	
	
}
