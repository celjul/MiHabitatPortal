package com.bstmexico.mihabitat.instalaciones.dao.impl;

import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.instalaciones.dao.InstalacionDao;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class InstalacionDaoImpl extends GenericDaoImpl<Instalacion, Long> implements
		InstalacionDao {

	@Transactional(readOnly = true)
	@Override
	public Instalacion get(Long id) {
		Instalacion instalacion = super.get(id);
		instalacion.getDisponibilidades().size();

		return instalacion;
	}

	@Transactional(readOnly = true)
	@Override
	public Instalacion getConReservaciones(Long id) {
		Instalacion instalacion = super.get(id);
		instalacion.getDisponibilidades().size();
		instalacion.getReservaciones().size();
		return instalacion;
	}

}
