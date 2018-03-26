package com.bstmexico.mihabitat.condominios.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2015
 */

@Entity
@DiscriminatorValue("imagen_condominio")
public class ArchivoImagenCondominio extends Archivo {


    /**
     *
     */
    private static final long serialVersionUID = 5369963459877955640L;

    public ArchivoImagenCondominio() {
        super();
    }

}

