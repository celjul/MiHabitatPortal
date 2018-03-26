package com.bstmexico.mihabitat.comunes.archivos.service;

import java.util.Collection;

import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
public interface ArchivoService {

	Collection<Archivo> getList(Class<? extends Archivo> type);
	Archivo get(Long id);
}
