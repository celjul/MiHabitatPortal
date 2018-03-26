package com.bstmexico.mihabitat.condominios.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.ConfiguracionNotificacionContacto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


/**
 * Created by Pegasus on 13/06/2015.
 */
public class ConfiguracionNotificacionFactory {

    private static final Logger LOG = LoggerFactory
            .getLogger(ConfiguracionNotificacionFactory.class);

    public static ConfiguracionNotificacionContacto newInstance(ContactoDepartamento contactoDepartamento) {
        try {
            Assert.notNull(contactoDepartamento, "FACEX001");
            Assert.notNull(contactoDepartamento.getId().getContacto(), "FACEX001");
            Assert.notNull(contactoDepartamento.getId().getDepartamento(), "FACEX001");
            ConfiguracionNotificacionContacto cnfNotif = new ConfiguracionNotificacionContacto();
            cnfNotif.setContactoDepartamento(contactoDepartamento);
            //inicializarConfiguracion(cnfNotif);
            return cnfNotif;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new FactoryException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    public static ConfiguracionNotificacionContacto newInstance() {
        try {
            ConfiguracionNotificacionContacto cnfNotif = new ConfiguracionNotificacionContacto();
            //inicializarConfiguracion(cnfNotif);
            return cnfNotif;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new FactoryException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }


}
