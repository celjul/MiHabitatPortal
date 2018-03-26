package com.bstmexico.mihabitat.proveedores.service;

import com.bstmexico.mihabitat.proveedores.model.Proveedor;

import java.util.Collection;
import java.util.Map;

/**
 * @author JPC
 * @version 1.0 
 * @since 2015
 */
public interface ProveedorService {
	
	void save(Proveedor departamento);

	Proveedor get(Long id);

	void update(Proveedor proveedor);

	@SuppressWarnings("rawtypes")
	Collection<Proveedor> search(Map map);

	Proveedor existeProveedor(Proveedor proveedor);

}
