package com.bstmexico.mihabitat.condominios.model;

import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Ana Karen Canales Santos
 * @version 1.0
 * @since 2016
 */

@Entity
@DiscriminatorValue("tipo_cobro")
public class CatalogoTipoCobroDepartamento extends Catalogo {

    private static final long serialVersionUID = -6114409762463823487L;

    public CatalogoTipoCobroDepartamento() {
        super();
    }
}
