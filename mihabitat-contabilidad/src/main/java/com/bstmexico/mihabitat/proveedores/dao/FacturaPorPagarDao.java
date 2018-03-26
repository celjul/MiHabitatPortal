package com.bstmexico.mihabitat.proveedores.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.proveedores.model.Cfdi;

import java.util.Collection;
import java.util.Map;

public interface FacturaPorPagarDao extends GenericDao<Cfdi, Long>{

    Cfdi findByCfdi(Cfdi cfdi);

    Collection<Cfdi> findByRfc(Map map);

    //Cfdi get(Long id, Boolean desbordar);

}