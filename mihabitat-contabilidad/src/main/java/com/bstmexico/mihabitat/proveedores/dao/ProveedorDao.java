package com.bstmexico.mihabitat.proveedores.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.proveedores.model.Proveedor;

public interface ProveedorDao extends GenericDao<Proveedor, Long>{

    Proveedor findByRFC(Proveedor proveedor);

}