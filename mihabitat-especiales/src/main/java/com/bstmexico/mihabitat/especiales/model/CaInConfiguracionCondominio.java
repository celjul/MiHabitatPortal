package com.bstmexico.mihabitat.especiales.model;

import com.bstmexico.mihabitat.condominios.model.CatalogoTipoCobroDepartamento;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
@Entity
@Table(name = "tciconfcondominio")
public class CaInConfiguracionCondominio {

    @NotNull
    @JoinColumn(name = "NIdTipoCobro", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoCobroDepartamento tipoCobroDepartamento;

    @NotNull
    @Min(0)
    @Column(name = "NMonto", nullable = false, precision = 9, scale = 2)
    private BigDecimal monto;

    @NotNull
    @Column(name = "BRequierePagoTdc", nullable = false)
    private Boolean requierePagoTdc;

    public CatalogoTipoCobroDepartamento getTipoCobroDepartamento() {
        return tipoCobroDepartamento;
    }

    public void setTipoCobroDepartamento(CatalogoTipoCobroDepartamento tipoCobroDepartamento) {
        this.tipoCobroDepartamento = tipoCobroDepartamento;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Boolean getRequierePagoTdc() {
        return requierePagoTdc;
    }

    public void setRequierePagoTdc(Boolean requierePagoTdc) {
        this.requierePagoTdc = requierePagoTdc;
    }
}
