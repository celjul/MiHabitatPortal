package com.bstmexico.mihabitat.comunes.personas.dao.impl;

import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.personas.dao.EmailPersonaDao;
import com.bstmexico.mihabitat.comunes.personas.model.EmailPersona;

@Repository("emailPersonaDaoImpl")
public class EmailPersonaDaoImpl extends GenericDaoImpl<EmailPersona, Long>
		implements EmailPersonaDao {

}
