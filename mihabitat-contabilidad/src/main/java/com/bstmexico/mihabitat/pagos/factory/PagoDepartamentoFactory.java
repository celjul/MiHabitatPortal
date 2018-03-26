package com.bstmexico.mihabitat.pagos.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0 
 * @since 2016
 */
public class PagoDepartamentoFactory {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(PagoDepartamentoFactory.class);

	public static PagoDepartamento newInstance() {
		return new PagoDepartamento();
	}

	public static PagoDepartamento newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			PagoDepartamento pagoDepartamento = newInstance();
			pagoDepartamento.setId(id);
			return pagoDepartamento;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
