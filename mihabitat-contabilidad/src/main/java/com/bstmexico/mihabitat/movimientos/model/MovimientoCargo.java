package com.bstmexico.mihabitat.movimientos.model;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@DiscriminatorValue("cargo")
public class MovimientoCargo extends Movimiento {

    private static final long serialVersionUID = -8979770983890060722L;

    @NotNull
    @Column(name = "BCancelado")
    private Boolean cancelado;

    // @NotNull
    @JoinColumn(name = "NIdCargo", referencedColumnName = "NIdCargo", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CargoDepartamento cargo;

    @NotNull
    @JoinColumn(name = "NIdTipo", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoMovimiento tipo;

    @JoinColumn(name = "NIdMovimientoCargo", nullable = true, referencedColumnName = "NIdMovimiento")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<MovimientoCargoAplicado> aplicados;

    @Column(name = "BAutogenerado")
    private Boolean autogenerado;

    @JsonIgnore
    @Transient
    private static Properties configuration;

    public MovimientoCargo() {
        super();
        try {
            configuration = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
        } catch (IOException e) {
        }
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public CargoDepartamento getCargo() {
        return cargo;
    }

    public void setCargo(CargoDepartamento cargo) {
        this.cargo = cargo;
    }

    public CatalogoTipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(CatalogoTipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public Collection<MovimientoCargoAplicado> getAplicados() {
        return aplicados;
    }

    public void setAplicados(Collection<MovimientoCargoAplicado> aplicados) {
        this.aplicados = aplicados;
    }

    public Boolean getAutogenerado() {
        return autogenerado;
    }

    public void setAutogenerado(Boolean autogenerado) {
        this.autogenerado = autogenerado;
    }

    /**
     * Propiedades para realizar calculos
     **/

    @Transient
    private Set<MovimientoCargoAplicado> pagos;

    @Transient
    private Set<MovimientoCargoAplicado> pagosCancelados;

    @Transient
    private BigDecimal totalPagado;

    @Transient
    private BigDecimal totalCancelado;

    @Transient
    private BigDecimal total;

    public Set<MovimientoCargoAplicado> getPagos() {
        pagos = new HashSet<MovimientoCargoAplicado>();
        if (!CollectionUtils.isEmpty(getAplicados())) {
            for (MovimientoCargoAplicado aplicado : getAplicados()) {
                /* 31 pago recargo */
                /* 32 pago cargo */
				/* 33 pago descuento */
                if (aplicado
                        .getTipo()
                        .getId()
                        .equals(Long.valueOf(configuration
                                .getProperty("pagorecargo")))
                        || aplicado
                        .getTipo()
                        .getId()
                        .equals(Long.valueOf(configuration
                                .getProperty("pagocargo")))
                        || aplicado
                        .getTipo()
                        .getId()
                        .equals(Long.valueOf(configuration
                                .getProperty("pagodescuento")))) {
                    pagos.add(aplicado);
                }
            }
        }
        return pagos;
    }

    public Set<MovimientoCargoAplicado> getPagosCancelados() {
        pagosCancelados = new HashSet<MovimientoCargoAplicado>();
        if (!CollectionUtils.isEmpty(getAplicados())) {
            for (MovimientoCargoAplicado aplicado : getAplicados()) {
				/* 37 cancelacion pago recargo */
				/* 38 cancelacion pago cargo */
				/* 39 cancelacion pago descuento */
                if (aplicado
                        .getTipo()
                        .getId()
                        .equals(Long.valueOf(configuration
                                .getProperty("cancelacionpagorecargo")))
                        || aplicado
                        .getTipo()
                        .getId()
                        .equals(Long.valueOf(configuration
                                .getProperty("cancelacionpagocargo")))
                        || aplicado
                        .getTipo()
                        .getId()
                        .equals(Long.valueOf(configuration
                                .getProperty("cancelacionpagodescuento")))) {
                    pagosCancelados.add(aplicado);
                }
            }
        }
        return pagosCancelados;
    }

    public BigDecimal getTotalPagado() {
        totalPagado = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(getPagos())) {
            for (MovimientoCargoAplicado pago : getPagos()) {
                if (pago.getAplicado() && pago.getImprimible()) {
                    if (pago.getHaber() != null) {
                        totalPagado = totalPagado.add(pago.getHaber());
                    }
                }
            }
        }
        return totalPagado;
    }

    public BigDecimal getTotalCancelado() {
        totalCancelado = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(getPagosCancelados())) {
            for (MovimientoCargoAplicado pagoCancelado : getPagosCancelados()) {
                if (pagoCancelado.getAplicado()
                        && pagoCancelado.getImprimible()) {
                    if (pagoCancelado.getDebe() != null) {
                        totalCancelado = totalCancelado.add(pagoCancelado
                                .getDebe());
                    }
                }
            }
        }
        return totalCancelado;
    }

    public BigDecimal getTotal() {
        total = getTotalPagado().subtract(getTotalCancelado());
        return total;
    }
}
