package com.bstmexico.mihabitat.mihabitat_arrendamiento.dao;

import java.util.Collection;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;

import com.bstmexico.mihabitat.mihabitat_arrendamiento.model.Arrendatario;

public interface ArrendamientoDao extends GenericDao<Arrendatario, Long> {
	
	public void save(Arrendatario arredatario);
	
	public void update(Arrendatario arredatario);
	
	public Collection<Arrendatario> getList();
	
	public Arrendatario get(Long idArrendatario);

}
