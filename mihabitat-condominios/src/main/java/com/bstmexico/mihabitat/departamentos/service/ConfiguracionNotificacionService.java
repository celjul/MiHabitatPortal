package com.bstmexico.mihabitat.departamentos.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.ConfiguracionNotificacionContacto;

import java.util.Collection;

public interface ConfiguracionNotificacionService {

	void save(ConfiguracionNotificacionContacto configuracionNotificacionContacto);



	ConfiguracionNotificacionContacto getNotificacionContactoByContacto(ContactoDepartamento contactoDepartamento);

	Collection<ConfiguracionNotificacionContacto> getNotificacionContactoByCondominio(Condominio condominio);



	ConfiguracionNotificacionContacto get(Long id);

	/*void inicializarConfiguracion(ConfiguracionNotificacionContacto cnc);*/

	void update(ConfiguracionNotificacionContacto notification);



	Collection<ConfiguracionNotificacionContacto> obtenerConfiguracionNotificacion(Condominio condominio);

}
