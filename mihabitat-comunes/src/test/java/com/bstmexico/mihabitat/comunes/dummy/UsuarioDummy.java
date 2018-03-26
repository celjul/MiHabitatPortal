package com.bstmexico.mihabitat.comunes.dummy;

import java.util.ArrayList;
import java.util.Collection;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.usuarios.factory.UsuarioFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;

public class UsuarioDummy {

	public static Usuario getUsuarioDummy() {
		Usuario usuario = UsuarioFactory.newInstance();
		usuario.setActivo(true);
		usuario.setEmail("usuario@bstmexico.com");
		usuario.setPassword("00000000001111111111222222222233");
		usuario.setPersona(PersonaDummy.getPersonaIdDummy(2, 2));
		usuario.setRoles(getRolesDummy(1));
		usuario.setUser("usuario");
		
		return usuario;
	}

	private static Collection<CatalogoRol> getRolesDummy(int size) {
		Collection<CatalogoRol> roles = new ArrayList<CatalogoRol>(size);
		
		for (int i = 0; i < size; i++) {
			CatalogoRol rol = CatalogoFactory.newInstance(CatalogoRol.class, 
					(long) i + 1);
			roles.add(rol);
		}
		
		return roles;
	}

}
