package com.bstmexico.mihabitat.contactos.test.dummy;

import java.util.ArrayList;
import java.util.Collection;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

public class DepartamentoDummy {

	public static Collection<ContactoDepartamento> getListaDepartamentos(
			Contacto contacto, int departamentos) {
		
		if (departamentos > 0) {
			Collection<ContactoDepartamento> lista = new ArrayList<ContactoDepartamento>();
			
			for (int i = 1; i <= departamentos; i++) {
				ContactoDepartamento contactoDepto = new ContactoDepartamento();
				contactoDepto.setContacto(contacto);
				contactoDepto.setDepartamento(getFullDepartamento(contacto.getCondominio(), i));
				contactoDepto.setHabitante(true);
				contactoDepto.setPrincipal(true);
				contactoDepto.setPropietario(true);
				
				lista.add(contactoDepto);
			}
			
			return lista;
		} 
		return null;
	}

	private static Departamento getFullDepartamento(Condominio condominio, int i) {
		Departamento depto = DepartamentoFactory.newInstance();
		depto.setActivo(true);
		depto.setCondominio(condominio);
//		depto.setContactos(contactos);
//		depto.setGrupos(grupos);
//		depto.setMantenimiento(mantenimiento);
		depto.setNombre("DUMY-" + i);
		depto.setObservaciones("Departamento generado desde la clase de dummies");

		return depto;
	}
}
