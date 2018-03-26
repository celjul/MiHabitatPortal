package com.bstmexico.mihabitat.proveedores.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.proveedores.model.PagoProveedor;

import java.util.Date;
import java.util.List;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
public interface PagoProveedorService {

    void save(PagoProveedor pago);

    void cancelar(PagoProveedor pago);

    List<PagoProveedor> getList(Condominio condominio);

    PagoProveedor get(Long id);

    void update(PagoProveedor pago);

    List<PagoProveedor> listarPorFecha(Condominio condominio, Date inicio, Date fin);
}
