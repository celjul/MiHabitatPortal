package com.bstmexico.mihabitat.comunicacion.tareas.service;

import com.bstmexico.mihabitat.comunicacion.tareas.model.RecordatorioAdministracion;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;
import java.util.Map;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
public interface RecordatorioAdministracionService {

    Collection<RecordatorioAdministracion> getList(Condominio condominio);
    void save(RecordatorioAdministracion recordatorioAdministracion);
    RecordatorioAdministracion get(Long id);
    void update(RecordatorioAdministracion recordatorioAdministracion);
    Collection<RecordatorioAdministracion> search(Map map);
    void delete(RecordatorioAdministracion recordatorioAdministracion);

}
