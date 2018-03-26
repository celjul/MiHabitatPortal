package com.bstmexico.mihabitat.otrosingresos.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.otrosingresos.model.OtroIngreso;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
public interface OtroIngresoService {

    void guardar(OtroIngreso otroIngreso);
    OtroIngreso editar(Long id);
    void actualizar(OtroIngreso otroIngreso);
    void cancelar(OtroIngreso otroIngreso);
    List<OtroIngreso> listar(Condominio condominio);

}
