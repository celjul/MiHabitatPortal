package com.bstmexico.mihabitat.notificaciones.model;

import javax.persistence.*;

/**
 * Created by Pegasus on 16/06/2015.
 */
@Entity
@DiscriminatorValue("recordatorio")
public class RecordatorioNotification extends Notification {

    @Transient
    private static final String emailTemplate = "template";

    public RecordatorioNotification() {

    }

    /*public static String getEmailTemplate() {
        return emailTemplate;
    }*/
}
