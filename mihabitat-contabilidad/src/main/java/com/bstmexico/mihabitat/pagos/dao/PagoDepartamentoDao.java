package com.bstmexico.mihabitat.pagos.dao;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.pagos.model.CatalogoEstatusPago;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;

import java.util.Collection;
import java.util.Date;

/**
 * @author Zo� Jonatan Tapia Hern�ndez
 * @version 1.0
 * @since 2016
 */
public interface PagoDepartamentoDao extends GenericDao<PagoDepartamento, Long> {
}
