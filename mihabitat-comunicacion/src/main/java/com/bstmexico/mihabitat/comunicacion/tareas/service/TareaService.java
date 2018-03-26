package com.bstmexico.mihabitat.comunicacion.tareas.service;

import com.bstmexico.mihabitat.comunicacion.tareas.model.Tarea;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;
import java.util.Map;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
public interface TareaService {

    Collection<Tarea> getList(Condominio condominio);
    void save(Tarea tarea);
    Tarea get(Long id);
    void update(Tarea tarea);
    Collection<Tarea> search(Map map);
    void delete(Tarea tarea);

}
