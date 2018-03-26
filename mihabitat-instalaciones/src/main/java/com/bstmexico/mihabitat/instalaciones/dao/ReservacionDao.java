package com.bstmexico.mihabitat.instalaciones.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.instalaciones.model.CatalogoEstatusReservacion;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;

import java.util.Collection;

public interface ReservacionDao extends GenericDao<Reservacion, Long> {

    Collection<Reservacion> getListByCondominioAndEstatus(Condominio condominio, CatalogoEstatusReservacion estatus);
    Reservacion getConInstalacion(Long id);

}
