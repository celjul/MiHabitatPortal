package com.bstmexico.mihabitat.cuentas.factory;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;

public class CuentaFactory {

	public static final Logger LOG = LoggerFactory
			.getLogger(CuentaFactory.class);

	public static Cuenta newInstance() {
		Cuenta cuenta = new Cuenta();
		cuenta.setInicial(BigDecimal.ZERO);
		cuenta.setActivo(Boolean.TRUE);
		cuenta.setFecha(new Date());
		
		return cuenta;
	}

	public static Cuenta newInstance(Long id) {
		try {
			Assert.notNull(id, "SEREX003");
			Cuenta cuenta = newInstance();
			cuenta.setId(id);
			return cuenta;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	public static Cuenta newInstance(String nombre, String noCuenta) {
		try {
			Assert.notNull(nombre, "SEREX003");
			Assert.notNull(noCuenta, "SEREX003");
			Cuenta cuenta = newInstance();
			cuenta.setNombre(nombre);
			cuenta.setNumero(noCuenta);
			return cuenta;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new FactoryException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}
}
