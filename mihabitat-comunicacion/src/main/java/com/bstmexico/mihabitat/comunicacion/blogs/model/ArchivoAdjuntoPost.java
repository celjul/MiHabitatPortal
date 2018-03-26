package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2015
 */

@Entity
@DiscriminatorValue("adjunto_post")
public class ArchivoAdjuntoPost extends Archivo {
    /**
     *
     */
    private static final long serialVersionUID = 5369962359877955640L;

    public ArchivoAdjuntoPost() {
        super();
    }

}


