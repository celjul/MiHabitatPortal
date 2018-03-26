package com.bstmexico.mihabitat.contactos.dao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface ContactoDao extends GenericDao<Contacto, Long> {

	Contacto get(Condominio condominio, String email);

	Contacto get(Long id, String email);

	Collection<Contacto> buscar(Condominio condominio, String key);
	Collection<ContactoDepartamento> getDepartamentos(Long id);

	Collection<Contacto> searchConConfNot(Set<Map.Entry> map);
}
