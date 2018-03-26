package com.bstmexico.mihabitat.departamentos.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public class DepartamentoFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(DepartamentoFactory.class);

	public static Departamento newInstance() {
		return new Departamento();
	}

	public static Departamento newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Departamento departamento = newInstance();
			departamento.setId(id);
			return departamento;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
