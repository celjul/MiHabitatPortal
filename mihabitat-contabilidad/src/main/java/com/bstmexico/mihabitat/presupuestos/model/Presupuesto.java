package com.bstmexico.mihabitat.presupuestos.model;

import com.bstmexico.mihabitat.cuentas.model.AgrupadorSat;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase para hacer lo de presupuestos, se espera hasta que quede lo de egresos.
 */

@Entity
@Table(name = "tpresupuestos" ,
        uniqueConstraints={@UniqueConstraint(columnNames={"NIdCuenta", "NAnio"})})
public class Presupuesto implements Serializable {

    private static final long serialVersionUID = 2467607685221659574L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdPresupuesto", nullable = false, unique = true)
    private Long id;

    @JsonIgnoreProperties(value = { "presupuestos" })
    @JoinColumn(name = "NIdCuenta", referencedColumnName = "NIdCuenta")
    @ManyToOne(fetch = FetchType.EAGER)
    private Cuenta cuenta;

    @Min(value = 1900)
    @Max(value = 2100)
    @NotNull
    @Column(name = "NAnio", nullable = false, unique = false)
    private Integer anio;

    @NotNull
    @Min(value = 0)
    @Column(name = "DEnero", precision = 9, scale = 2, nullable = false)
    private BigDecimal enero;

    @NotNull
    @Min(value = 0)
    @Column(name = "DFebrero", precision = 9, scale = 2, nullable = false)
    private BigDecimal fecbero;

    @NotNull
    @Min(value = 0)
    @Column(name = "DMarzo", precision = 9, scale = 2, nullable = false)
    private BigDecimal marzo;

    @NotNull
    @Min(value = 0)
    @Column(name = "DAbril", precision = 9, scale = 2, nullable = false)
    private BigDecimal abril;

    @NotNull
    @Min(value = 0)
    @Column(name = "DMayo", precision = 9, scale = 2, nullable = false)
    private BigDecimal mayo;

    @NotNull
    @Min(value = 0)
    @Column(name = "DJunio", precision = 9, scale = 2, nullable = false)
    private BigDecimal junio;

    @NotNull
    @Min(value = 0)
    @Column(name = "DJulio", precision = 9, scale = 2, nullable = false)
    private BigDecimal julio;

    @NotNull
    @Min(value = 0)
    @Column(name = "DAgosto", precision = 9, scale = 2, nullable = false)
    private BigDecimal agosto;

    @NotNull
    @Min(value = 0)
    @Column(name = "DSeptiembre", precision = 9, scale = 2, nullable = false)
    private BigDecimal septiembre;

    @NotNull
    @Min(value = 0)
    @Column(name = "DOctubre", precision = 9, scale = 2, nullable = false)
    private BigDecimal octubre;

    @NotNull
    @Min(value = 0)
    @Column(name = "DNoviembre", precision = 9, scale = 2, nullable = false)
    private BigDecimal noviembre;

    @NotNull
    @Min(value = 0)
    @Column(name = "DDiciembre", precision = 9, scale = 2, nullable = false)
    private BigDecimal diciembre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public BigDecimal getEnero() {
        return enero;
    }

    public void setEnero(BigDecimal enero) {
        this.enero = enero;
    }

    public BigDecimal getFecbero() {
        return fecbero;
    }

    public void setFecbero(BigDecimal fecbero) {
        this.fecbero = fecbero;
    }

    public BigDecimal getMarzo() {
        return marzo;
    }

    public void setMarzo(BigDecimal marzo) {
        this.marzo = marzo;
    }

    public BigDecimal getAbril() {
        return abril;
    }

    public void setAbril(BigDecimal abril) {
        this.abril = abril;
    }

    public BigDecimal getMayo() {
        return mayo;
    }

    public void setMayo(BigDecimal mayo) {
        this.mayo = mayo;
    }

    public BigDecimal getJunio() {
        return junio;
    }

    public void setJunio(BigDecimal junio) {
        this.junio = junio;
    }

    public BigDecimal getJulio() {
        return julio;
    }

    public void setJulio(BigDecimal julio) {
        this.julio = julio;
    }

    public BigDecimal getAgosto() {
        return agosto;
    }

    public void setAgosto(BigDecimal agosto) {
        this.agosto = agosto;
    }

    public BigDecimal getSeptiembre() {
        return septiembre;
    }

    public void setSeptiembre(BigDecimal septiembre) {
        this.septiembre = septiembre;
    }

    public BigDecimal getOctubre() {
        return octubre;
    }

    public void setOctubre(BigDecimal octubre) {
        this.octubre = octubre;
    }

    public BigDecimal getNoviembre() {
        return noviembre;
    }

    public void setNoviembre(BigDecimal noviembre) {
        this.noviembre = noviembre;
    }

    public BigDecimal getDiciembre() {
        return diciembre;
    }

    public void setDiciembre(BigDecimal diciembre) {
        this.diciembre = diciembre;
    }
}
