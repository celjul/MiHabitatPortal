package com.bstmexico.mihabitat.cobranza.dao;

import com.bstmexico.mihabitat.cobranza.model.Nota;
import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;

import java.util.Collection;

/**
 * @author Pablo Cruz Santos
 * @version 1.0 
 * @since 2015
 */
public interface NotaDao extends GenericDao<Nota, Long> {

    Collection<Nota> getList(Condominio condominio);
}
