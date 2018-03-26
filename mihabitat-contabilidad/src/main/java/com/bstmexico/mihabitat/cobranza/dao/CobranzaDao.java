package com.bstmexico.mihabitat.cobranza.dao;

import java.util.Collection;
import java.util.Date;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.condominios.model.Condominio;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface CobranzaDao {

	Collection<CargoDepartamento> getCargos(Condominio condominio, Date hasta);
}
