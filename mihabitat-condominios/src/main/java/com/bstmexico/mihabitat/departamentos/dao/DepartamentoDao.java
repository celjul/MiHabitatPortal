package com.bstmexico.mihabitat.departamentos.dao;

import java.util.Collection;
import java.util.List;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface DepartamentoDao extends GenericDao<Departamento, Long> {

	Departamento get(Departamento departamento);

	Collection<Condominio> search(Usuario usuario);
	
	List<Departamento> searchByCond(Long id);
	
	List<Departamento> searchByPersona(Long id);

	Departamento searchByid(Long id);
}
