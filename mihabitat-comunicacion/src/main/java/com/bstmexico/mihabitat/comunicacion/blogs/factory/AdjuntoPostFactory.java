package com.bstmexico.mihabitat.comunicacion.blogs.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.comunicacion.blogs.model.AdjuntoPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created by Pegasus on 04/08/2015.
 */
public class AdjuntoPostFactory {

    public static final Logger LOG = LoggerFactory
            .getLogger(AdjuntoPostFactory.class);

    public static AdjuntoPost newInstance() {
        return new AdjuntoPost();
    }

    public static AdjuntoPost newInstance(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            AdjuntoPost adjunto = newInstance();
            adjunto.setId(id);
            return adjunto;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new FactoryException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
