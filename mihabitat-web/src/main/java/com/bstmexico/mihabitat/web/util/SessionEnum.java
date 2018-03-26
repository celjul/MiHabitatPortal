package com.bstmexico.mihabitat.web.util;

public enum SessionEnum {
	USUARIO("usuario"), CONDOMINIOS("condominios"), CONDOMINIO("condominio"), ROL("rol"),ES_ADMINISTRADOR("esadministrador"),ES_CONTACTO("escontacto");
	
	private String value;
	
	SessionEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
