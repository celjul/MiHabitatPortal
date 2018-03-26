package com.bstmexico.mihabitat.cobranza.dao.impl;

import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.cobranza.dao.RecordatorioDao;
import com.bstmexico.mihabitat.cobranza.model.Recordatorio;
import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class RecordatorioDaoImpl extends GenericDaoImpl<Recordatorio, Long>
		implements RecordatorioDao {
}
