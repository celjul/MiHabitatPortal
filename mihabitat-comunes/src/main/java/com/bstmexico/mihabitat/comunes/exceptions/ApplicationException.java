package com.bstmexico.mihabitat.comunes.exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 2948942330329641604L;

	private static final Properties ex = readProperties();

	private static final String DEFAULT_CODE = "EXC000";

	private static final String DEFAULT_MESSAGE = "Excepción no definida.";

	protected String code;

	public ApplicationException() {
		this(DEFAULT_CODE, ex.getProperty(DEFAULT_CODE, DEFAULT_MESSAGE));
	}

	public ApplicationException(Throwable cause) {
		this(DEFAULT_CODE, ex.getProperty(DEFAULT_CODE, DEFAULT_MESSAGE), cause);
	}

	public ApplicationException(String code) {
		this(code, ex.getProperty(code, DEFAULT_MESSAGE));
	}

	private ApplicationException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ApplicationException(String code, Throwable cause) {
		this(code, ex.getProperty(code, DEFAULT_MESSAGE), cause);
	}

	public ApplicationException(String code, Throwable cause, Object... args) {
		this(code, String.format(ex.getProperty(code, DEFAULT_MESSAGE), args),
				cause);
	}

	private ApplicationException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
	public ApplicationException(String code, Object... args) {
		this(code, String.format(ex.getProperty(code, DEFAULT_MESSAGE), args));
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private static Properties readProperties() {
		InputStream stream = null;
		try {
			stream = ApplicationException.class.getClassLoader()
					.getResourceAsStream("exceptions.properties");
			Properties prop = new Properties();
			prop.load(stream);
			return prop;
		} catch (IOException ex) {
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException ex) {
			}
		}
		return null;
	}
}
