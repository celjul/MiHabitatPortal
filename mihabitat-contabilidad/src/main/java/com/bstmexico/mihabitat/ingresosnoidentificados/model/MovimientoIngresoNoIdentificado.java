package com.bstmexico.mihabitat.ingresosnoidentificados.model;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @created 2016
 */
@Entity
@DiscriminatorValue("ingresonoidentificado")
public class MovimientoIngresoNoIdentificado extends Movimiento {

    private static final long serialVersionUID = 1242923223436456469L;

    @NotNull
    @Column(name = "BAplicado")
    private Boolean aplicado;

    @NotNull
    @Column(name = "BCancelado")
    private Boolean cancelado;

    @NotNull
    @JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cuenta cuenta;

    @JsonIgnore
    @JoinColumn(name="NIdIngresoNI", referencedColumnName = "NIdIngresoNI")
    @OneToOne(fetch = FetchType.LAZY)
    private IngresoNoIdentificado ingresoNoIdentificado;

    @NotNull
    @JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoMovimiento tipo;

    public MovimientoIngresoNoIdentificado() {
        super();
    }

    public Boolean getAplicado() {
        return aplicado;
    }

    public void setAplicado(Boolean aplicado) {
        this.aplicado = aplicado;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public CatalogoTipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(CatalogoTipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public IngresoNoIdentificado getIngresoNoIdentificado() {
        return ingresoNoIdentificado;
    }

    public void setIngresoNoIdentificado(IngresoNoIdentificado ingresoNoIdentificado) {
        this.ingresoNoIdentificado = ingresoNoIdentificado;
    }
}
