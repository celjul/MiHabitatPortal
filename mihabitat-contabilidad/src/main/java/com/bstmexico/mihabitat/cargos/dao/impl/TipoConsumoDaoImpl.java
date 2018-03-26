package com.bstmexico.mihabitat.cargos.dao.impl;

import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.cargos.dao.TipoConsumoDao;
import com.bstmexico.mihabitat.cargos.model.TipoConsumo;
import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class TipoConsumoDaoImpl extends GenericDaoImpl<TipoConsumo, Long>
		implements TipoConsumoDao {
}
