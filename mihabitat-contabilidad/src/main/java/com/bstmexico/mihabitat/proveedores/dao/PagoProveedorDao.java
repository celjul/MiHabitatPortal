package com.bstmexico.mihabitat.proveedores.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.proveedores.model.PagoProveedor;

import java.util.Collection;
import java.util.Date;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
public interface PagoProveedorDao extends GenericDao<PagoProveedor, Long> {

    void evict(PagoProveedor pago);

    Collection<PagoProveedor> getPorFecha(Condominio condominio, Date inicio, Date fin);
}
