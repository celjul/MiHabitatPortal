package com.bstmexico.mihabitat.especiales.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
@Entity
@Table(name = "tcitempdepartamentos")
public class CaInTemplateCargaDepartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdTempDepartamento", nullable = false, unique = true)
    private Long id;

    @NotNull
    @JoinColumn(name = "NIdCargaInicial", referencedColumnName = "NIdCargaInicial", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CargaInicial cargaInicial;

    @Size(max = 256)
    @Column(name = "VTorresEtiquetas", length = 256)
    private String torresEtiquetas;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(length = 128, name = "VDepartamento", nullable = false)
    private String departamento;

    @NotNull
    @Min(value = 0)
    @Column(name = "DMantenimiento", precision = 9, scale = 2, nullable = false)
    private BigDecimal mantenimeinto;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(length = 128, name = "VContacto", nullable = false)
    private String contacto;

    @org.hibernate.validator.constraints.Email
    @Size(max = 64)
    @Column(length = 64, name = "VEmailContacto", nullable = true)
    private String emailContacto;

    @Size(max = 16)
    @Column(length = 16, name = "VTelefonoContacto", nullable = true)
    private String telefonoContacto;

    @NotNull
    @Column(name = "DSaldo", precision = 9, scale = 2, nullable = false)
    private BigDecimal saldo;

    @Size(max = 512)
    @Column(name = "VComentarios", nullable = true)
    private String comentarios;

    @Size(max = 128)
    @Column(length = 128, name = "VLeyendaSaldoInicial", nullable = true)
    private String leyendaSaldoInicial;

    @NotNull
    @Column(name = "BVerificado", nullable = false)
    private Boolean verificado;

    @NotNull
    @Column(name = "BCorrecto", nullable = false)
    private Boolean correcto;

    @Size(max = 128)
    @Column(length = 128, name = "VError", nullable = true)
    private String error;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CargaInicial getCargaInicial() {
        return cargaInicial;
    }

    public void setCargaInicial(CargaInicial cargaInicial) {
        this.cargaInicial = cargaInicial;
    }

    public String getTorresEtiquetas() {
        return torresEtiquetas;
    }

    public void setTorresEtiquetas(String torresEtiquetas) {
        this.torresEtiquetas = torresEtiquetas;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getMantenimeinto() {
        return mantenimeinto;
    }

    public void setMantenimeinto(BigDecimal mantenimeinto) {
        this.mantenimeinto = mantenimeinto;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getLeyendaSaldoInicial() {
        return leyendaSaldoInicial;
    }

    public void setLeyendaSaldoInicial(String leyendaSaldoInicial) {
        this.leyendaSaldoInicial = leyendaSaldoInicial;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public Boolean getCorrecto() {
        return correcto;
    }

    public void setCorrecto(Boolean correcto) {
        this.correcto = correcto;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
