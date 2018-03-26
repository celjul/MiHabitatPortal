package com.bstmexico.mihabitat.comunicacion.tareas.dao.Impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunicacion.tareas.dao.TareaDao;
import com.bstmexico.mihabitat.comunicacion.tareas.model.Tarea;
import org.springframework.stereotype.Repository;

/**
 * @author Zoé Jonatan Tapia Hernández
 * @version 1.0
 * @since 2015
 */
@Repository
public class TareaDaoImpl  extends GenericDaoImpl<Tarea, Long> implements
        TareaDao {
}
