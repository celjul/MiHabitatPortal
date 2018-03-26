package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Pegasus on 09/08/2015.
 */
@Entity
@DiscriminatorValue("tipo_blog")
public class CatalogoTipoBlog extends Catalogo {

    /**
     *
     */
    private static final long serialVersionUID = -6114409348463832187L;

    public CatalogoTipoBlog() {
        super();
    }
}
