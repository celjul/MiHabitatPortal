package com.bstmexico.mihabitat.condominios.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CondominioFactoryTests {
	@Test
	public void _001_newInstanceEmpty() {
		Condominio condominio = CondominioFactory.newInstance();

		Assert.assertNotNull(condominio);
		Assert.assertNull(condominio.getAdministradores());
		Assert.assertNull(condominio.getDireccion());
		Assert.assertNull(condominio.getNombre());
		Assert.assertNull(condominio.getId());

	}

	@Test
	public void _002_newInstanceId() {
		Condominio condominio = CondominioFactory.newInstance(2L);
		Assert.assertNotNull(condominio);
		Assert.assertNull(condominio.getAdministradores());
		Assert.assertNull(condominio.getDireccion());
		Assert.assertNull(condominio.getNombre());
		Assert.assertNotNull(condominio.getId());
		Assert.assertEquals(condominio.getId(), new Long(2));

	}

	@SuppressWarnings("unused")
	@Test(expected = FactoryException.class)
	public void _003_newInstanceNull() {
		Condominio condominio = CondominioFactory.newInstance(null);

	}

}
