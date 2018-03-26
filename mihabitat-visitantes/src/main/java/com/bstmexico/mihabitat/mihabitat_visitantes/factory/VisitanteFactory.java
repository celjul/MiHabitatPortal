package com.bstmexico.mihabitat.mihabitat_visitantes.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;

import com.bstmexico.mihabitat.mihabitat_visitantes.model.Visitantes;

public class VisitanteFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(VisitanteFactory.class);

	public static Visitantes newInstance() {
		return new Visitantes();
	}

	public static Visitantes newInstance(Long id) {
		try {

			Visitantes visitante = newInstance();
			visitante.setIdArrendador(id);
			return visitante;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
