package com.bstmexico.mihabitat.departamentos.service.impl;

import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.dao.ConfiguracionNotificacionContactoDao;
import com.bstmexico.mihabitat.condominios.factory.ConfiguracionNotificacionFactory;
import com.bstmexico.mihabitat.departamentos.model.CatalogoTipoNotificacion;
import com.bstmexico.mihabitat.departamentos.model.ConfiguracionNotificacionContacto;
import com.bstmexico.mihabitat.departamentos.service.ConfiguracionNotificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

@Service
public class ConfiguracionNotificacionServiceImpl implements ConfiguracionNotificacionService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ConfiguracionNotificacionServiceImpl.class);

	@Autowired
	private CatalogoService catalogoService;

	@Autowired
	private ContactoService contactoService;

	private static Properties configuration;

	static  {
		try {
			configuration = PropertiesLoaderUtils
					.loadAllProperties("configuration.properties");
		} catch (IOException e) {
		}
	}

	@Autowired
	private Validator validator;

	@Autowired
	private ConfiguracionNotificacionContactoDao configuracionNotificacionContactoDao;

	@Override
	public void save(ConfiguracionNotificacionContacto configuracionNotificacionContacto) {

		try {
			Assert.notNull(configuracionNotificacionContacto, "SEREX001");
			Set<ConstraintViolation<ConfiguracionNotificacionContacto>> violations = validator
					.validate(configuracionNotificacionContacto);
			if (violations.isEmpty()) {
				ConfiguracionNotificacionContacto cnf = this.getNotificacionContactoByContacto(configuracionNotificacionContacto.getContactoDepartamento());
				if(cnf == null) {
					configuracionNotificacionContactoDao.save(configuracionNotificacionContacto);
				} else {
					cnf.setContactoDepartamento(configuracionNotificacionContacto.getContactoDepartamento());

					cnf.setTipoNotificacionAbonoCancelado(configuracionNotificacionContacto.getTipoNotificacionAbonoCancelado());
					cnf.setTipoNotificacionAbonoValidado(configuracionNotificacionContacto.getTipoNotificacionAbonoValidado());
					cnf.setTipoNotificacionAprovecharDescuento(configuracionNotificacionContacto.getTipoNotificacionAprovecharDescuento());
					cnf.setTipoNotificacionAvisoCobranza(configuracionNotificacionContacto.getTipoNotificacionAvisoCobranza());
					cnf.setTipoNotificacionEvitarRecargo(configuracionNotificacionContacto.getTipoNotificacionEvitarRecargo());
					cnf.setTipoNotificacionNuevoAviso(configuracionNotificacionContacto.getTipoNotificacionNuevoAviso());
					cnf.setTipoNotificacionNuevoCargo(configuracionNotificacionContacto.getTipoNotificacionNuevoCargo());
					cnf.setTipoNotificacionNuevoComentario(configuracionNotificacionContacto.getTipoNotificacionNuevoComentario());
					cnf.setTipoNotificacionNuevoComentarioTemaComentado(configuracionNotificacionContacto.getTipoNotificacionNuevoComentarioTemaComentado());
					cnf.setTipoNotificacionNuevoComentarioTemaPropio(configuracionNotificacionContacto.getTipoNotificacionNuevoComentarioTemaPropio());
					cnf.setTipoNotificacionNuevoRecargo(configuracionNotificacionContacto.getTipoNotificacionNuevoRecargo());
					cnf.setTipoNotificacionNuevoTema(configuracionNotificacionContacto.getTipoNotificacionNuevoTema());

					configuracionNotificacionContactoDao.update(cnf);
				}
			} else {
				String message = "SEREX002";
				ApplicationException ex1 = new ServiceException(message,
						violations);
				LOG.warn(ex1.getMessage(), violations);
				throw ex1;
			}

		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}

	}

	@Override
	public ConfiguracionNotificacionContacto getNotificacionContactoByContacto(ContactoDepartamento contactoDepartamento) {
		try {
			Assert.notNull(contactoDepartamento, "SEREX001");
			Map map = new HashMap<>();
			map.put("contactoDepartamento", contactoDepartamento);
			Collection<ConfiguracionNotificacionContacto> configuracionNotificacionContactos =
					configuracionNotificacionContactoDao.search(map.entrySet());
			if(!configuracionNotificacionContactos.isEmpty()) {
				return configuracionNotificacionContactos.iterator().next();
			}
			return null;
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public Collection<ConfiguracionNotificacionContacto> getNotificacionContactoByCondominio(Condominio condominio) {
		try {
			Assert.notNull(condominio, "SEREX001");
			return configuracionNotificacionContactoDao.getByCondominio(condominio);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}



	@Override
	public ConfiguracionNotificacionContacto get(Long id) {
		try {
			Assert.notNull(id, "SEREX001");
			return configuracionNotificacionContactoDao.get(id);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	@Override
	public void update(ConfiguracionNotificacionContacto configuracionNotificacionContacto) {
		try {
			Assert.notNull(configuracionNotificacionContacto, "SEREX001");
			Assert.notNull(configuracionNotificacionContacto.getId(), "SEREX001");
			configuracionNotificacionContactoDao.update(configuracionNotificacionContacto);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

	/*@Override
	public void inicializarConfiguracion(ConfiguracionNotificacionContacto cnc) {
		cnc.setTipoNotificacionAbonoCancelado((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))));
		cnc.setTipoNotificacionAbonoValidado((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email"))));
		cnc.setTipoNotificacionAprovecharDescuento((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))));
		cnc.setTipoNotificacionAvisoCobranza((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email"))));
		cnc.setTipoNotificacionEvitarRecargo((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email"))));
		cnc.setTipoNotificacionNuevoAviso((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email"))));
		cnc.setTipoNotificacionNuevoCargo((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))));
		cnc.setTipoNotificacionNuevoComentario((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))));
		cnc.setTipoNotificacionNuevoComentarioTemaComentado((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))));
		cnc.setTipoNotificacionNuevoComentarioTemaPropio((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))));
		cnc.setTipoNotificacionNuevoRecargo((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))));
		cnc.setTipoNotificacionNuevoTema((CatalogoTipoNotificacion)catalogoService.get(Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion"))));
	}*/

	@Override
	public Collection<ConfiguracionNotificacionContacto> obtenerConfiguracionNotificacion(Condominio condominio) {
		Collection<ConfiguracionNotificacionContacto> confs =this.getNotificacionContactoByCondominio(condominio);

		Map map = new HashMap();
		map.put("condominio", condominio);

		Collection<Contacto> contactos = contactoService.search(map);

		for(Contacto contacto : contactos) {
			for(ContactoDepartamento cd : contacto.getDepartamentos()) {
				cd.setContacto(contacto);
				Boolean encontrado = Boolean.FALSE;
				for(ConfiguracionNotificacionContacto conf : confs) {
					if(conf.getContactoDepartamento().equals(cd)) {
						encontrado = Boolean.TRUE;
						break;
					}
				}
				if(!encontrado){
					contacto.setDepartamentos(null);
					ConfiguracionNotificacionContacto cnc = ConfiguracionNotificacionFactory.newInstance(cd);
					confs.add(cnc);
				}
			}
		}
		return confs;
	}
}
