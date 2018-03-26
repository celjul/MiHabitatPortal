package com.bstmexico.mihabitat.comunes.exceptions.dao;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class DataAccessException extends ApplicationException {

	private static final long serialVersionUID = -8739700608587070066L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

	public DataAccessException(String code) {
		super(code);
	}

	public DataAccessException(String code, Throwable cause) {
		super(code, cause);
	}
	
	public DataAccessException(String code, Throwable cause, Object... args) {
		super(code, cause, args);
	}
}
