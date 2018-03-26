package com.bstmexico.mihabitat.otrosingresos.model;

import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @created 2015
 */
@Entity
@DiscriminatorValue("concepto_ingreso")
public class MovimientoConceptoOtroIngreso extends Movimiento {

    private static final long serialVersionUID = -6396039235624184491L;

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
    @JoinColumn(name = "NIdConceptoIngreso", referencedColumnName = "NIdConceptoIngreso")
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private ConceptoIngreso conceptoIngreso;

    @NotNull
    @JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoMovimiento tipo;

    public MovimientoConceptoOtroIngreso() {
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

    public ConceptoIngreso getConceptoIngreso() {
        return conceptoIngreso;
    }

    public void setConceptoIngreso(ConceptoIngreso conceptoIngreso) {
        this.conceptoIngreso = conceptoIngreso;
    }

    public CatalogoTipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(CatalogoTipoMovimiento tipo) {
        this.tipo = tipo;
    }
}
