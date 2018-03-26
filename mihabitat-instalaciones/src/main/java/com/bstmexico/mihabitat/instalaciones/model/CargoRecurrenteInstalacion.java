package com.bstmexico.mihabitat.instalaciones.model;


import com.bstmexico.mihabitat.cargos.model.CargoRecurrente;
import javax.persistence.*;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tcargosrecurrentesinstalaciones")
public class CargoRecurrenteInstalacion extends CargoRecurrente {

    private static final long serialVersionUID = -26469923545737205L;

    @JoinColumn(name = "NIdInstalacion", nullable = true,
            referencedColumnName = "NIdInstalacion")
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Instalacion instalacion;

    public Instalacion getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(Instalacion instalacion) {
        this.instalacion = instalacion;
    }
}

