package com.bstmexico.mihabitat.condominios.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.condominios.factory.GrupoCondominioFactory;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GrupoCondominioFactoryTests {
	@Test
	public void _001_newInstanceEmpty() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory.newInstance();

		Assert.assertNotNull(grupoCondominio);
		Assert.assertNull(grupoCondominio.getDescripcion());
		Assert.assertNull(grupoCondominio.getCondominio());
		Assert.assertNull(grupoCondominio.getId());

	}

	@Test
	public void _002_newInstanceId() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory
				.newInstance(1L);
		Assert.assertNotNull(grupoCondominio.getId());
		Assert.assertEquals(grupoCondominio.getId(), new Long(1));
		Assert.assertNotNull(grupoCondominio);
		Assert.assertNull(grupoCondominio.getDescripcion());
		Assert.assertNull(grupoCondominio.getCondominio());
		Assert.assertNotNull(grupoCondominio.getId());

	}

	@SuppressWarnings("unused")
	@Test(expected = FactoryException.class)
	public void _003_newInstanceNull() {
		GrupoCondominio grupoCondominio = GrupoCondominioFactory
				.newInstance(null);
	}

}
