package com.bstmexico.mihabitat.comunes.direcciones.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bstmexico.mihabitat.comunes.direcciones.factory.EstadoFactory;
import com.bstmexico.mihabitat.comunes.direcciones.model.Estado;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EstadoFactoryTest {

	@Test
	public void _001_newInstanceEmpty() {
		Estado estado = EstadoFactory.newInstance();
		Assert.assertNotNull(estado);
		Assert.assertNull(estado.getId());
		Assert.assertNull(estado.getNombre());
	}

	@Test
	public void _002_newInstanceId() {
		Estado estado = EstadoFactory.newInstance(19L);
		Assert.assertNotNull(estado);
		Assert.assertNotNull(estado.getId());
		Assert.assertEquals(estado.getId(), new Long(19));
		Assert.assertNull(estado.getNombre());
	}

	@SuppressWarnings("unused")
	@Test(expected = FactoryException.class)
	public void _003_newInstanceIdNull() {
		Estado pais = EstadoFactory.newInstance(null);
	}
}
