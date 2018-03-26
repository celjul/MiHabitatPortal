package com.bstmexico.mihabitat.cuentas.service;

import java.util.Collection;
import java.util.Map;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;

public interface CuentaService {

	Collection<Cuenta> getList(Condominio condominio);

	void save(Cuenta cuenta);

	Cuenta get(Long id);

	void update(Cuenta cuenta);

	Collection<Cuenta> getCuentas(Condominio condominio, Cuenta... cuentas);

	@SuppressWarnings("rawtypes")
	Collection<Cuenta> search(Map map);

	Cuenta comprobarExistenciaCuenta(Cuenta cuenta);

	void updateMultiple(Cuenta cuenta);

	Collection<Cuenta> getAllCuentas(Condominio condominio, Cuenta... cuentas);

	String incrementarCuenta(Long idPadre, Byte nivel);

	/**
	 * Obtiene una cuenta contable de un condominio por nombre.
	 * 
	 * @param condominio
	 * @param nombre
	 * @return
	 */
	Cuenta get(Condominio condominio, String nombre);

	/**
	 * Obtiene una cuenta contable con sus cuentas hijas.
	 * 
	 * @param cuenta
	 * @return
	 */
	Cuenta get(Cuenta cuenta);
}
