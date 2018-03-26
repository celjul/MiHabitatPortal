package com.bstmexico.mihabitat.movimientos.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
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
public interface MovimientoDao extends GenericDao<Movimiento, Long> {

	Movimiento get(Long id, Class<? extends Movimiento> movimiento);

	MovimientoCargo getConCargo(Long id, Class<? extends Movimiento> movimiento);

	@SuppressWarnings("rawtypes")
	Collection<Movimiento> search(Set<Map.Entry> set,
			Class<? extends Movimiento> movimiento);

	Collection<MovimientoSaldo> getMovimientos(Contacto contacto);

	Collection<MovimientoSaldo> getMovimientosSaldo(Departamento departamento, Date inicio, Date fin);

	Collection<MovimientoSaldo> getMovimientosSaldo(Departamento departamento);

	Collection<MovimientoSaldo> getMovimientos(Contacto contacto, Date inicio, Date fin);

	/*Collection<MovimientoSaldo> getMovimientosConPago(Contacto contacto);*/

	Collection<Movimiento> getMovimientos(Departamento departamento,
			Date inicio, Date fin);

	BigDecimal getDebe(Date fin, Cuenta cuenta);

	BigDecimal getDebeLtEq(Date fin, Cuenta cuenta);

	BigDecimal getHaber(Date fin, Cuenta cuenta);

	BigDecimal getHaberLtEq(Date fin, Cuenta cuenta);

	BigDecimal getDebe(Date inicio, Date fin, Cuenta cuenta);

	BigDecimal getHaber(Date inicio, Date fin, Cuenta cuenta);

	Collection<Movimiento> getMovimientos(Cuenta cuenta, Date inicio, Date fin);
	
	Collection<Movimiento> getMovimientos(Cuenta cuenta, Date fin);
}
