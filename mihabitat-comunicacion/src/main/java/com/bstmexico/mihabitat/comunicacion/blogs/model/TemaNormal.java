package com.bstmexico.mihabitat.comunicacion.blogs.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Pegasus on 11/08/2015.
 */
@Entity
@DiscriminatorValue("tema_normal")
public class TemaNormal extends Tema {
    /**
     *
     */
    private static final long serialVersionUID = 1626356464297959029L;
}
