package com.bstmexico.mihabitat.comunes.archivos.dao;

import java.util.Collection;

import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;
import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;
import com.bstmexico.mihabitat.comunes.dao.GenericDao;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
public interface ArchivoDao extends GenericDao<Archivo, Long> {
	
	Collection<Archivo> getList(Class<? extends Archivo> type);
	
}

