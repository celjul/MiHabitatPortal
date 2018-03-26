package com.bstmexico.mihabitat.comunes.direcciones.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bstmexico.mihabitat.comunes.direcciones.factory.PaisFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Pais;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PaisFactoryTest {

	@Test
	public void newInstanceEmpty() {
		Pais pais = PaisFactory.newInstance();
		Assert.assertNotNull(pais);
		Assert.assertNull(pais.getId());
		Assert.assertNull(pais.getNombre());
	}

	@Test
	public void newInstanceId() {
		Pais pais = PaisFactory.newInstance(19L);
		Assert.assertNotNull(pais);
		Assert.assertNotNull(pais.getId());
		Assert.assertEquals(pais.getId(), new Long(19));
		Assert.assertNull(pais.getNombre());
	}

	@SuppressWarnings("unused")
	@Test(expected = FactoryException.class)
	public void newInstanceIdNull() {
		Pais pais = PaisFactory.newInstance(null);
	}
}
