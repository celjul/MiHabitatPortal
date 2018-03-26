package com.bstmexico.mihabitat.condominios.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.condominios.factory.MantenimientoCondominioFactory;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MantenimientoCondominioFactoryTests {
	@Test
	public void _001_newInstanceEmpty() {
		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory
				.newInstance();

		Assert.assertNotNull(mantenimientoCondominio);
		Assert.assertNull(mantenimientoCondominio.getDescripcion());
		Assert.assertNull(mantenimientoCondominio.getCondominio());
		Assert.assertNull(mantenimientoCondominio.getId());
		Assert.assertNull(mantenimientoCondominio.getMonto());

	}

	@Test
	public void _002_newInstanceId() {
		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory
				.newInstance(1L);
		Assert.assertNotNull(mantenimientoCondominio.getId());
		Assert.assertEquals(mantenimientoCondominio.getId(), new Long(1));
		Assert.assertNotNull(mantenimientoCondominio);
		Assert.assertNull(mantenimientoCondominio.getDescripcion());
		Assert.assertNull(mantenimientoCondominio.getCondominio());
		Assert.assertNull(mantenimientoCondominio.getMonto());
		Assert.assertNotNull(mantenimientoCondominio.getId());

	}

	@SuppressWarnings("unused")
	@Test(expected = FactoryException.class)
	public void _003_newInstanceNull() {
		MantenimientoCondominio mantenimientoCondominio = MantenimientoCondominioFactory
				.newInstance(null);
	}

}
