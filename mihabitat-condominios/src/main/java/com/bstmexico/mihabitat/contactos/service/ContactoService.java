package com.bstmexico.mihabitat.contactos.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface ContactoService {

	void save(Contacto contacto);

	Contacto get(Long id);

	void update(Contacto contacto);

	@SuppressWarnings("rawtypes")
	Collection<Contacto> search(Map map);

	Contacto get(Condominio condominio, String email);

	Contacto get(Long id, String email);

	Collection<Contacto> buscar(Condominio condominio, String key);

	Collection<ContactoDepartamento> getDepartamentos(Long idContacto);
}
