package com.bstmexico.mihabitat.comunicacion.blogs.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.comunicacion.blogs.model.AdjuntoPost;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaRevisado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Created by Pegasus on 04/08/2015.
 */
public class TemaRevisadoFactory {

    public static final Logger LOG = LoggerFactory
            .getLogger(TemaRevisadoFactory.class);

    public static TemaRevisado newInstance() {
        return new TemaRevisado();
    }

    public static TemaRevisado newInstance(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            TemaRevisado temaRevisado = newInstance();
            temaRevisado.setId(id);
            return temaRevisado;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new FactoryException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
