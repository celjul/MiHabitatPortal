package com.bstmexico.mihabitat.comunes.exceptions.service;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ServiceException extends ApplicationException {

	private static final long serialVersionUID = -8739700608587070066L;

	private static final String DEFAULT_MESSAGE = "Excepción generada en la "
			+ "capa de servicios.";

	public ServiceException() {
		super(DEFAULT_MESSAGE);
	}

	public ServiceException(Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	public ServiceException(String code) {
		super(code);
	}

	public ServiceException(String code, Throwable cause) {
		super(code, cause);
	}
	
	public ServiceException(String code, Object... args) {
		super(code, args);
	}
}
