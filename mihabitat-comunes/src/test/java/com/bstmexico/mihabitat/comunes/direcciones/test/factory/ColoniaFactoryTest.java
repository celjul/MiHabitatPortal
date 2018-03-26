package com.bstmexico.mihabitat.comunes.direcciones.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bstmexico.mihabitat.comunes.direcciones.factory.ColoniaFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Colonia;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ColoniaFactoryTest {

	@Test
	public void _001_newInstanceEmpty(){
		Colonia colonia= ColoniaFactory.newInstance();
		Assert.assertNotNull(colonia);
		Assert.assertNull(colonia.getId());
		Assert.assertNull(colonia.getNombre());
	}
	
	@Test
	public void _002_newInstanceId(){
		Colonia colonia= ColoniaFactory.newInstance(7L);
		Assert.assertNotNull(colonia);
		Assert.assertNotNull(colonia.getId());
		Assert.assertEquals(colonia.getId(), new Long(7));
		Assert.assertNull(colonia.getNombre());
	}
	
	@SuppressWarnings("unused")
	@Test(expected = FactoryException.class)
	public void _003_newInstanceIdNull() {
		Colonia colonia= ColoniaFactory.newInstance(null);
	}
	
}
