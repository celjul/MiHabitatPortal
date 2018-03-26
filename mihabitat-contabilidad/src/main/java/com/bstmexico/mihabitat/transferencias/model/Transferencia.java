package com.bstmexico.mihabitat.transferencias.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.movimientos.model.Movimiento;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Eduardo Prieto Islas
 * @version 1.0
 * @created 2015
 */
@Entity
@Table(name = "ttransferencias")
public class Transferencia{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdTransferencia", nullable = false, unique = true)
    private Long id;

    @NotNull
    @OneToOne(mappedBy = "transferencia", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private MovimientoRetiro retiro;

    @NotNull
    @OneToOne(mappedBy = "transferencia", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private MovimientoDeposito deposito;

    @NotNull
    @Column(name = "DMonto")
    private BigDecimal monto;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
    @NotNull
    @Column(columnDefinition = "date", name = "DFecha", nullable = false)
    private Date fecha;

    @Size(max = 264)
    @Column(name = "VComentario", length = 264)
    private String comentario;

    @NotNull
    @JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Condominio condominio;

    @NotNull
    @JoinColumn(name = "NIdMetodoTransferencia", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoMetodoPago metodoTransferencia;



    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public MovimientoRetiro getRetiro() {
        return retiro;
    }

    public void setRetiro(MovimientoRetiro retiro) {
        this.retiro = retiro;
    }

    public MovimientoDeposito getDeposito() {
        return deposito;
    }

    public void setDeposito(MovimientoDeposito deposito) {
        this.deposito = deposito;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }


    public Date getFecha() {
        return fecha;
    }


    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public CatalogoMetodoPago getMetodoTransferencia() {
        return metodoTransferencia;
    }

    public void setMetodoTransferencia(CatalogoMetodoPago metodoTransferencia) {
        this.metodoTransferencia = metodoTransferencia;
    }


}
