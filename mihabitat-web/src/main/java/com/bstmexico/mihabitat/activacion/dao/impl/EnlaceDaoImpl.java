package com.bstmexico.mihabitat.activacion.dao.impl;

import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.activacion.dao.EnlaceDao;
import com.bstmexico.mihabitat.activacion.model.Enlace;
import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;

@Repository
public class EnlaceDaoImpl extends GenericDaoImpl<Enlace, Long> implements
		EnlaceDao {

}
