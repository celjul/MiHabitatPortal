package com.bstmexico.mihabitat.mihabitat_visitantes.dao;

import java.util.Collection;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.mihabitat_visitantes.model.Visitantes;


public interface VisitanteDao extends GenericDao<Visitantes, Long> {
	
	public void save(Visitantes visitante);
	
	public Collection<Visitantes> getList();
	
	public Visitantes get(Long id);
	
	public void update(Visitantes visitante);

}
