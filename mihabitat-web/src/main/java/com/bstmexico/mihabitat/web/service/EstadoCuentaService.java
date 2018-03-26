package com.bstmexico.mihabitat.web.service;

import java.util.Collection;
import java.util.Date;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.dto.EstadoCuenta;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface EstadoCuentaService {

	EstadoCuenta getEstadoCuenta(Condominio condominio,
								 Departamento departamento, Date inicio, Date fin, Contacto contacto);

	byte[] getEstadoCuenta(EstadoCuenta estado);

	byte[] getEstadoDeCuentaMultiple(Collection<EstadoCuenta> estadosCuenta, String formato);

	void sendEstadoCuenta(Condominio condominio, String mensaje, Long idDepartamento, Long idContacto, Date inicio, Date fin,
						  String... emails);

	void sendEstadoDeCuentaMultiple(Condominio condominio, String mensaje, Date inicio, Date fin, Long... ids);

	EstadoCuenta getEstado(Condominio condominio, Long idDepartamento,
						   Date inicio, Date fin, Long idContacto);
}