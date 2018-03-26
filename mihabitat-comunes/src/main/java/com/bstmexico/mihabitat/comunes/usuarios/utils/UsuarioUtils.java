package com.bstmexico.mihabitat.comunes.usuarios.utils;

import com.bstmexico.mihabitat.comunes.personas.utils.PersonaUtils;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

public final class UsuarioUtils {
	public static final void fix(Usuario usuario) {
		if (usuario.getActivo() == null) {
			usuario.setActivo(true);
		}
		
		if (usuario.getPersona() != null ) {
			PersonaUtils.fix(usuario.getPersona());
		}
	}
}
