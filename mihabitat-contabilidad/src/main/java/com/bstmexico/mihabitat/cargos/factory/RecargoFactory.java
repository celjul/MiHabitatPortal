package com.bstmexico.mihabitat.cargos.factory;

import com.bstmexico.mihabitat.cargos.model.Descuento;
import com.bstmexico.mihabitat.cargos.model.Recargo;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created by Pegasus on 09/06/2015.
 */
public class RecargoFactory {
    private static final Logger LOG = LoggerFactory
            .getLogger(RecargoFactory.class);

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<? extends Recargo> tipo) {
        try {
            Assert.notNull(tipo, "FACEX001");
            return (T) tipo.newInstance();
        } catch (IllegalAccessException ex) {
            ApplicationException ex1 = new FactoryException("FAC002", ex,
                    tipo.getSimpleName());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        } catch (InstantiationException ex) {
            ApplicationException ex1 = new FactoryException("FAC002", ex,
                    tipo.getSimpleName());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new FactoryException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<? extends Recargo> tipo, Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            Recargo recargo = newInstance(tipo);
            recargo.setId(id);
            return (T) recargo;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new FactoryException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
