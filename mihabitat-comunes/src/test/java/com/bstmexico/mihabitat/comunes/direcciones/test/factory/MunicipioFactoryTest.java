package com.bstmexico.mihabitat.comunes.direcciones.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bstmexico.mihabitat.comunes.direcciones.factory.MunicipioFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Municipio;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MunicipioFactoryTest {

	@Test
	public void newInstanceEmpty(){
		Municipio municipio= MunicipioFactory.newInstance();
		Assert.assertNotNull(municipio);
		Assert.assertNull(municipio.getId());
		Assert.assertNull(municipio.getNombre());		
		Assert.assertNull(municipio.getEstado());
	}
	
	@Test
	public void newInstanceId(){
		Municipio municipio= MunicipioFactory.newInstance(7L);
		Assert.assertNotNull(municipio);
		Assert.assertNotNull(municipio.getId());
		Assert.assertNull(municipio.getNombre());
		Assert.assertNull(municipio.getEstado());
		Assert.assertEquals(municipio.getId(), new Long(7));
	}
	
	@SuppressWarnings("unused")
	@Test(expected = FactoryException.class)
	public void newInstanceIdNull(){
		Municipio municipio= MunicipioFactory.newInstance(null);
	}
}
