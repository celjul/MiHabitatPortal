package com.bstmexico.mihabitat.departamentos.test.factory;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartamentoFactoryTests {
	@Test
	public void _001_newInstanceEmpty() {
		Departamento departamento = DepartamentoFactory.newInstance();

		Assert.assertNotNull(departamento);
		Assert.assertNull(departamento.getNombre());
		Assert.assertNull(departamento.getObservaciones());
		Assert.assertNull(departamento.getActivo());
		Assert.assertNull(departamento.getCondominio());
		Assert.assertNull(departamento.getContactos());
		Assert.assertNull(departamento.getGrupos());
		Assert.assertNull(departamento.getId());
		Assert.assertNull(departamento.getMantenimiento());
		
	}

	@Test
	public void _002_newInstanceId() {
		Departamento departamento = DepartamentoFactory.newInstance(1L);
		Assert.assertNotNull(departamento);
		Assert.assertNull(departamento.getNombre());
		Assert.assertNull(departamento.getObservaciones());
		Assert.assertNull(departamento.getActivo());
		Assert.assertNull(departamento.getCondominio());
		Assert.assertNull(departamento.getContactos());
		Assert.assertNull(departamento.getGrupos());
		Assert.assertNotNull(departamento.getId());
		Assert.assertEquals(departamento.getId(), new Long(1));
		Assert.assertNull(departamento.getMantenimiento());

	}

	@SuppressWarnings("unused")
	@Test(expected = FactoryException.class)
	public void _003_newInstanceNull() {
		Departamento departamento = DepartamentoFactory.newInstance(null);

	}

}
