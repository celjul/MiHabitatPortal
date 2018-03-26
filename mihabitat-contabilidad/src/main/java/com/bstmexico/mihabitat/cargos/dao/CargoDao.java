package com.bstmexico.mihabitat.cargos.dao;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.bstmexico.mihabitat.cargos.model.*;
import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface CargoDao extends GenericDao<Cargo, Long> {

	Cargo getConDepartamento(Long id, Class<? extends Cargo> cargo, Boolean desbordar);

	Cargo get(Long id, Class<? extends Cargo> cargo, Boolean desbordar);

	@SuppressWarnings("rawtypes")
	Collection<Cargo> search(Set<Map.Entry> set, Class<? extends Cargo> cargo, Boolean desbordar);

	Collection<CargoDepartamento> getList(Condominio condominio, Date inicio, Date fin);

	CargoAgrupador getAnterior(Condominio condominio, TipoConsumo consumo);

	Collection<CargoDepartamento> getCargos(
			Collection<Departamento> departamentos, Date fecha);

	Collection<CargoDepartamento> getCargos(Pago pago);

	Collection<CargoDepartamento> getCargos(PagoDepartamento pago);

	Collection<CargoDepartamento> getCargosPorFecha(Date fecha);

	Collection<CargoDepartamento> getCargosPendientes(Departamento departamento, Date fin);

	Collection<CargoRecurrente> getCargosRecurrentesActivosPorFecha(Date fecha);

	Collection<CargoDepartamento> getCargosRecargosPorAplicar(Date fecha);
}