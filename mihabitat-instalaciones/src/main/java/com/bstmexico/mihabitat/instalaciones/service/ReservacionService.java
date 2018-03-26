package com.bstmexico.mihabitat.instalaciones.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoEstatusReservacion;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;

public interface ReservacionService {

	Collection<Reservacion> getList(Instalacion instalacion);
	
	void save(Reservacion reservacion);

	Reservacion get(Long id);
	
	void update(Reservacion reservacion);

	@SuppressWarnings("rawtypes")
	Collection<Reservacion> search(Map map);

	/*public void saveAll(Collection<Reservacion> reservaciones);

	public void deleteAll(Collection<Reservacion> reservaciones);*/

	Collection<Reservacion> getListByCondominioByEstatus(Condominio condominio,
														 CatalogoEstatusReservacion estatus);

	void delete(Reservacion reservacion);

	Reservacion getConInstalacion(Long id);
	
}
