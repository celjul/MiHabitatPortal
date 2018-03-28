package com.bstmexico.mihabitat.departamentos.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface DepartamentoService {

	void save(Departamento departamento);

	Departamento get(Long id);

	void update(Departamento departamento);

	@SuppressWarnings("rawtypes")
	Collection<Departamento> search(Map map);

	Departamento get(Departamento departamento);

	Collection<Condominio> search(Usuario usuario);
	
	List<Departamento> searchByCond(Long id);
	
	List<Departamento> searchByPersona(Long id);
}
