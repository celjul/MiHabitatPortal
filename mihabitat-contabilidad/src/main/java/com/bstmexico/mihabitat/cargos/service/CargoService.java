package com.bstmexico.mihabitat.cargos.service;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.bstmexico.mihabitat.cargos.model.*;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface CargoService {

	void save(Cargo cargo);

	Cargo get(Long id, Class<? extends Cargo> cargo, Boolean desbordar);

	void update(Cargo cargo);

	@SuppressWarnings("rawtypes")
	Collection<Cargo> search(Map map, Class<? extends Cargo> cargo, Boolean desbordar);

	Collection<CargoDepartamento> getList(Condominio condominio, Date inicio, Date fin);

	CargoAgrupador getAnterior(Condominio condominio, TipoConsumo consumo);

	Collection<CargoDepartamento> getCargos(Contacto contacto, Date fecha);

	Collection<CargoDepartamento> getCargos(Collection<Departamento> departamentos);

	Collection<CargoDepartamento> getCargosPendientes(Departamento departamento, Date fin);

	Collection<CargoDepartamento> getCargos(Pago pago);

	Collection<CargoDepartamento> getCargos(PagoDepartamento pago);

	Collection<CargoDepartamento> getCargosPorFecha(Date fecha);

	Collection<CargoRecurrente> getCargosRecurrentesPorFecha(Date fecha);

	Collection<CargoDepartamento> getCargosRecargosPorAplicar(Date fecha);
	
	void generarDescuento(MovimientoCargo descuento);
	
	void generarRecargo(MovimientoCargo recargo);
	
	void cancelarDescuento(MovimientoCargo descuento);
	
	void cancelarRecargo(MovimientoCargo recargo);
	
	void cancelarCargo(CargoDepartamento cargo);

	Cargo getConDepartamento(Long id, Class<? extends Cargo> cargo, Boolean desbordar);
}
