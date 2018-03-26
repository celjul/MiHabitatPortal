package com.bstmexico.mihabitat.ingresosnoidentificados.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.IngresoNoIdentificado;
import com.bstmexico.mihabitat.pagos.model.Pago;

import java.util.List;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
public interface IngresoNoIdentificadoService {

    void guardar(IngresoNoIdentificado otroIngreso);
    IngresoNoIdentificado get(Long id);
    void actualizar(IngresoNoIdentificado otroIngreso);
    void cancelar(IngresoNoIdentificado otroIngreso);
    List<IngresoNoIdentificado> listar(Condominio condominio);
    void asignar(IngresoNoIdentificado ingresoNoIdentificado, Pago pago, Boolean aprobado);
}
