package com.bstmexico.mihabitat.condominios.dao.impl;

import org.springframework.stereotype.Repository;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.condominios.dao.MantenimientoCondominioDao;
import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class MantenimientoCondominioDaoImpl extends
		GenericDaoImpl<MantenimientoCondominio, Long> implements
		MantenimientoCondominioDao {
}
