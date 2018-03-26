package com.bstmexico.mihabitat.proveedores.gastos.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.proveedores.gastos.model.Gasto;

import java.util.Collection;
import java.util.Date;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
public interface GastoDao extends GenericDao<Gasto, Long> {

    Collection<Gasto> getPorFecha(Condominio condominio, Date inicio, Date fin);
}
