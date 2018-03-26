package com.bstmexico.mihabitat.cargos.model;

import com.bstmexico.mihabitat.condominios.model.MantenimientoCondominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;

import javax.persistence.*;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tcargosrecurrentesmantenimientos")
public class CargoRecurrenteMantenimiento extends CargoRecurrente {

    private static final long serialVersionUID = -26469902345737205L;

    @JoinColumn(name = "NIdMantenimiento", nullable = true,
            referencedColumnName = "NIdMantenimiento")
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private MantenimientoCondominio mantenimiento;

    public MantenimientoCondominio getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(MantenimientoCondominio mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
}
