package com.bstmexico.mihabitat.proveedores.gastos.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.proveedores.gastos.model.Gasto;

import java.util.Date;
import java.util.List;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
public interface GastoService {

    void guardar(Gasto gasto);

    Gasto editar(Long id);

    void actualizar(Gasto gasto);

    void cancelar(Gasto gasto);

    List<Gasto> listar(Condominio condominio);

    List<Gasto> listarPorFecha(Condominio condominio, Date inicio, Date fin);
}
