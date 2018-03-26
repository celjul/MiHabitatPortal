package com.bstmexico.mihabitat.pagos.service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.pagos.model.CatalogoEstatusPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface PagoService {

	void save(Pago pago, Boolean aplicado);

	Pago get(Long id);

	@SuppressWarnings("rawtypes")
	Collection<Pago> search(Map map);

	Collection<Pago> getList(Condominio condominio, Date inicio, Date fin);

	void aprobar(Pago pago);

	void rechazar(Pago pago);

	void reenviar(Pago pago);

	void cancelar(Pago pago);

	Collection<Pago> getPagosByStatus(Condominio condominio, CatalogoEstatusPago estatus);

	Collection<Pago> getPagosByStatus(Contacto contacto, CatalogoEstatusPago estatus);

	Collection<PagoDepartamento> getPagos(Departamento departamento);
}
