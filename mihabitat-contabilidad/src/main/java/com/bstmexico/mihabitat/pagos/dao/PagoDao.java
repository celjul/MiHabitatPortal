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
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface PagoDao extends GenericDao<Pago, Long> {
	
	void evict(Pago pago);

	Collection<Pago> getPagosByStatus(Condominio condominio,CatalogoEstatusPago estatus);

	EstatusPago getPagoByStatus(Long idEstatusPago);

	Collection<Pago> getPagosByStatus(Contacto contacto,CatalogoEstatusPago estatus);

	Long getLastFolio(Condominio condominio);

	Collection<Pago> getList(Condominio condominio, Date inicio, Date fin);

	Collection<PagoDepartamento> getPagos(Departamento departamento);
}
