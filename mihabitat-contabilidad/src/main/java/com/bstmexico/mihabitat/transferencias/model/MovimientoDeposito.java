package com.bstmexico.mihabitat.transferencias.model;


import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Eduardo Prieto Islas
 * @version 1.0
 * @created 2015
 */


@Entity
@DiscriminatorValue("transferencia_deposito")
public class MovimientoDeposito extends Movimiento{

    //banderas
    @NotNull
    @Column(name = "BAplicado")
    private Boolean aplicado;
    //bandera desabilitada
    @NotNull
    @Column(name = "BCancelado")
    private Boolean cancelado;
    // cuenta con la cual se esta trabajando
    @NotNull
    @JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
    @ManyToOne(fetch = FetchType.EAGER)
    private Cuenta cuenta;
    //identificador del movimiento
    @NotNull
    @JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoMovimiento tipo;

    @JsonIgnore
    @JoinColumn(name="NIdTransferencia", referencedColumnName = "NIdTransferencia")
    @OneToOne(fetch = FetchType.LAZY)
    private Transferencia transferencia;

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

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }
}
