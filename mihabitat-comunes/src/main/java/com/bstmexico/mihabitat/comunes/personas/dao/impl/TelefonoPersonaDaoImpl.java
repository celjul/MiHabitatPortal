package com.bstmexico.mihabitat.comunes.personas.dao.impl;

import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.personas.dao.TelefonoPersonaDao;
import com.bstmexico.mihabitat.comunes.personas.model.TelefonoPersona;

@Repository("telefonoPersonaDaoImpl")
public class TelefonoPersonaDaoImpl extends
		GenericDaoImpl<TelefonoPersona, Long> implements TelefonoPersonaDao {
}
