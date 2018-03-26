package com.bstmexico.mihabitat.instalaciones.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.instalaciones.model.Instalacion;

public interface InstalacionDao extends GenericDao<Instalacion, Long> {

    Instalacion getConReservaciones(Long id);

}
