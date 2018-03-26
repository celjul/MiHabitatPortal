package com.bstmexico.mihabitat.comunicacion.directorio.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.comunicacion.directorio.model.DirectorioRegistro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created by Zoe on 26/01/2016.
 */
public class DirectorioRegistroFactory {
    public static final Logger LOG = LoggerFactory
            .getLogger(DirectorioRegistroFactory.class);

    public static DirectorioRegistro newInstance() {
        return new DirectorioRegistro();
    }

    public static DirectorioRegistro newInstance(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            DirectorioRegistro directorioRegistro = newInstance();
            directorioRegistro.setId(id);
            return directorioRegistro;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new FactoryException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
