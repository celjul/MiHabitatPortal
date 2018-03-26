package com.bstmexico.mihabitat.comunes.exceptions.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class FactoryException extends ApplicationException {

	private static final long serialVersionUID = -8739700608587070066L;

	public FactoryException() {
		super();
	}

	public FactoryException(Throwable cause) {
		super(cause);
	}

	public FactoryException(String code) {
		super(code);
	}

	public FactoryException(String code, Throwable cause) {
		super(code, cause);
	}
	
	public FactoryException(String code, Throwable cause, Object... args) {
		super(code, cause, args);
	}
}
