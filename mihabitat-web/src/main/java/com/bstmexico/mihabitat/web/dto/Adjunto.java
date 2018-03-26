package com.bstmexico.mihabitat.web.dto;

import org.springframework.core.io.InputStreamSource;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public class Adjunto {

	private String nombre;
	private InputStreamSource source;

	public Adjunto(String nombre, InputStreamSource source) {
		this.nombre = nombre;
		this.source = source;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public InputStreamSource getSource() {
		return source;
	}

	public void setSource(InputStreamSource source) {
		this.source = source;
	}
}