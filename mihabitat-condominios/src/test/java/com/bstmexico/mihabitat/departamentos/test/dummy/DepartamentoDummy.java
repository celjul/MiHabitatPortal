package com.bstmexico.mihabitat.departamentos.test.dummy;

import java.util.ArrayList;
import java.util.Collection;

import com.bstmexico.mihabitat.comunes.personas.factory.PersonaFactory;
import com.bstmexico.mihabitat.comunes.personas.model.PersonaAbstract;
import com.bstmexico.mihabitat.condominios.factory.CondominioFactory;
import com.bstmexico.mihabitat.condominios.factory.GrupoCondominioFactory;
import com.bstmexico.mihabitat.condominios.factory.MantenimientoCondominioFactory;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.model.GrupoCondominio;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;
import com.bstmexico.mihabitat.contactos.factory.ContactoDepartamentoFactory;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamentoId;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

public class DepartamentoDummy {

	public static Departamento crearDepartamentoDummy() {
		Departamento departamento = DepartamentoFactory.newInstance();
		departamento.setActivo(true);
		departamento.setCondominio(crearCondominioDummy());
		departamento.setContactos(crearContactosDummy(departamento));
		departamento.setGrupos(crearGruposDummy());
		departamento.setMantenimiento(crearManteminientoDummy());
		departamento.setNombre("A 001");
		departamento.setObservaciones("Observaciones");
		
		return departamento;
	}

	private static Collection<ContactoDepartamento> crearContactosDummy(Departamento departamento) {
		Collection<ContactoDepartamento> contactos = new ArrayList<ContactoDepartamento>();
		
		contactos.add(crearContactoDummy(departamento));
		return contactos;
	}

	private static ContactoDepartamento crearContactoDummy(Departamento departamento) {
		ContactoDepartamento contacto = ContactoDepartamentoFactory.newInstance();
		ContactoDepartamentoId id = new ContactoDepartamentoId();
		id.setContacto(crearContactoDummy(1L, departamento.getCondominio()));
		id.setDepartamento(departamento);
		contacto.setId(id);
		contacto.setHabitante(false);
		contacto.setPrincipal(false);
		contacto.setPropietario(false);
		
		return contacto;
	}

	private static Contacto crearContactoDummy(Long id, Condominio condominio) {
		Contacto contacto = new Contacto();
//		contacto.setActivo(true);
		contacto.setCondominio(condominio);
//		contacto.setDepartamentos(departamentos);
		contacto.setId(id);
//		contacto.setPersona(crearPersonaDummy(1L));
		
		return contacto;
	}

	private static PersonaAbstract crearPersonaDummy(Long id) {
		PersonaAbstract persona = PersonaFactory.newInstance(id);
		
		persona.setApellidoPaterno("Solís");
		persona.setApellidoPaterno("Ramírez");
		persona.setNombre("Marco Antonio");

		return persona;
	}

	private static MantenimientoCondominio crearManteminientoDummy() {
		MantenimientoCondominio mantenimiento = MantenimientoCondominioFactory.newInstance();
		mantenimiento.setId(1L);
		
		return mantenimiento;
	}

	private static Collection<GrupoCondominio> crearGruposDummy() {
		Collection<GrupoCondominio> grupos = new ArrayList<GrupoCondominio>();
		
		grupos.add(crearGrupoDummy());
		return grupos;
	}

	private static GrupoCondominio crearGrupoDummy() {
		GrupoCondominio grupo = GrupoCondominioFactory.newInstance();
		grupo.setId(1L);
		
		return grupo;
	}

	private static Condominio crearCondominioDummy() {
		Condominio condominio = CondominioFactory.newInstance();
		condominio.setId(1L);
		
		return condominio;
	}
	
}
