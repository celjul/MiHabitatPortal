package com.bstmexico.mihabitat.notificaciones.factory;

import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.factory.FactoryException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.notificaciones.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;


/**
 * Created by Pegasus on 13/06/2015.
 */
public class NotificationFactory {
    public static final String PENDIENTE_BAJA = "PendienteBaja";
    public static final String PENDIENTE_NORMAL = "Pendiente";
    public static final String PENDIENTE_URGENTE = "PendienteUrgente";

    /**
     * Devuelve una objeto para enviar una notificacion..
     * @param tipo Distintivo para poder procesar las notificaciones de
     * diferente manera de acuerdo al tipo especificado.
     * @return
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(NotificationFactory.class);

    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<? extends Notification> tipo) {
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

    /**
     * Devuelve una objeto para enviar una notificacion.
     * @param tipo
     * @param condominio
     * @return
     */
    public static final Notification newInstance(Class<? extends Notification> tipo, Condominio condominio) {
        Notification notification = newInstance(tipo);
        notification.setVisto(false);
        return notification;
    }

}
