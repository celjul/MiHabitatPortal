package com.bstmexico.mihabitat.comunes.direcciones.dao.impl;

import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.direcciones.dao.MunicipioDao;
import com.bstmexico.mihabitat.comunes.direcciones.model.Municipio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class MunicipioDaoImpl extends GenericDaoImpl<Municipio, Long> implements
		MunicipioDao {
}
