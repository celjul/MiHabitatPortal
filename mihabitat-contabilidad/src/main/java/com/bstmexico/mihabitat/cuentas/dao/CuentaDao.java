package com.bstmexico.mihabitat.cuentas.dao;

import java.util.Collection;

import com.bstmexico.mihabitat.comunes.dao.GenericDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;

public interface CuentaDao extends GenericDao<Cuenta, Long> {

	Cuenta getCuenta(Cuenta cuenta);

	Cuenta getPadre(Cuenta cuenta);

	Collection<Cuenta> getList(Condominio condominio);

	Cuenta comprobarExistenciaCuenta(Cuenta cuenta);

	Collection<Cuenta> getCuentaHijos(Cuenta cuenta);

	String getCuentaIncrementar(Long idPadre, Byte nivel);

	/**
	 * Obtiene una cuenta contable de un condominio por nombre.
	 * 
	 * @param condominio
	 * @param nombre
	 * @return
	 */
	Cuenta get(Condominio condominio, String nombre);
}
