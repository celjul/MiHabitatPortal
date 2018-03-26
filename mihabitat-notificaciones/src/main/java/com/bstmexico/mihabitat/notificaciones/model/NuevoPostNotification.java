package com.bstmexico.mihabitat.notificaciones.model;

import com.bstmexico.mihabitat.comunicacion.blogs.model.Post;
import javax.persistence.Transient;

/**
 * Created by Pegasus on 07/08/2015.
 */
public class NuevoPostNotification extends Notification{

    @Transient
    private static final String emailTemplate = "nuevo-comentario.html";

    @Transient
    private Post post;

    public NuevoPostNotification() {

    }

    /*public static String getEmailTemplate() {
        return emailTemplate;
    }*/

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
