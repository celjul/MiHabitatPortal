package com.bstmexico.mihabitat.movimientos.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoSaldo;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface MovimientoService {

	void save(Movimiento movimiento);

	Movimiento get(Long id, Class<? extends Movimiento> movimiento);

	MovimientoCargo getConCargo(Long id, Class<? extends Movimiento> movimiento);

	void update(Movimiento movimiento);

	void delete(Movimiento movimiento);

	@SuppressWarnings("rawtypes")
	Collection<Movimiento> search(Map map,
			Class<? extends Movimiento> movimiento);

	BigDecimal getSaldoFavor(Contacto contacto);

	BigDecimal getSaldoFavorPorDepartamento(Departamento departamento);

	BigDecimal getSaldoFavorPorDepartamento(Departamento departamento, Date inicio, Date fin);

	Collection<MovimientoSaldo> getSaldosFavor(Departamento departamento);

	/**
	 * Busqueda de movimientos para estado de cuenta por departamento.
	 * 
	 * @param departamento
	 * @param inicio
	 * @param fin
	 * @return
	 */
	Collection<Movimiento> getMovimientos(Departamento departamento,
			Date inicio, Date fin);

	/**
	 * Obtiene el debe de la cuenta hasta la fecha especificada.
	 * 
	 * @param fin
	 * @param cuenta
	 * @return
	 */
	BigDecimal getDebe(Date fin, Cuenta cuenta);

	/**
	 * Obtiene el debe de la cuenta hasta la fecha especificada, incluyendo la fecha.
	 *
	 * @param fin
	 * @param cuenta
	 * @return
	 */
	BigDecimal getDebeLtEq(Date fin, Cuenta cuenta);

	/**
	 * Obtiene el haber de la cuenta hasta la fecha especificada.
	 * 
	 * @param fin
	 * @param cuenta
	 * @return
	 */
	BigDecimal getHaber(Date fin, Cuenta cuenta);

	/**
	 * Obtiene el haber de la cuenta hasta la fecha especificada, incluyendo la fecha especificada.
	 *
	 * @param fin
	 * @param cuenta
	 * @return
	 */
	BigDecimal getHaberLtEq(Date fin, Cuenta cuenta);

	/**
	 * Obtiene el debe de la cuenta en un rango de fechas.
	 * 
	 * @param inicio
	 * @param fin
	 * @param cuenta
	 * @return
	 */
	BigDecimal getDebe(Date inicio, Date fin, Cuenta cuenta);

	/**
	 * Obtiene el debe de la cuenta en un rango de fechas.
	 * 
	 * @param inicio
	 * @param fin
	 * @param cuenta
	 * @return
	 */
	BigDecimal getHaber(Date inicio, Date fin, Cuenta cuenta);

	/**
	 * Obtiene una colección de movimientos de una cuenta contable en un rango
	 * de fechas.
	 * 
	 * @param cuenta
	 * @param inicio
	 * @param fin
	 * @return
	 */
	Collection<Movimiento> getMovimientos(Cuenta cuenta, Date inicio, Date fin);

	/**
	 * Obtiene una colección de movimientos de una cuenta contable hasta la
	 * fecha especificada.
	 * 
	 * @param cuenta
	 * @param fin
	 * @return
	 */
	Collection<Movimiento> getMovimientos(Cuenta cuenta, Date fin);
}
