package com.bstmexico.mihabitat.ingresosnoidentificados.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2016
 */
@Entity
@Table(name = "tingresosnoidentificados")
public class IngresoNoIdentificado implements Serializable {

    private static final long serialVersionUID = -8292348089722244348L;

    @NotNull
    @JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Condominio condominio;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "longblob", name = "BComprobante")
    private byte[] comprobante;

    @JoinColumn(name = "NIdCuentaBanco", referencedColumnName = "NIdCuenta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cuenta cuentaBanco;

    @JoinColumn(name = "NIdCuentaIngreso", referencedColumnName = "NIdCuenta")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cuenta cuentaIngreso;

    @NotNull
    @JoinColumn(name = "NIdEstatus", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoEstatusIngreso estatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
    @NotNull
    @Column(columnDefinition = "date", name = "DFecha", nullable = false)
    private Date fecha;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdIngresoNI", nullable = false, unique = true)
    private Long id;

    @NotNull
    @JoinColumn(name = "NIdMetodoPago", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoMetodoPago metodoPago;

    @NotNull
    @Min(value = 0)
    @Column(name = "DMonto", precision = 9, scale = 2, nullable = false)
    private BigDecimal monto;

    @JoinColumn(name = "NIdIngresoNI", nullable = true, referencedColumnName = "NIdIngresoNI")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<MovimientoIngresoNoIdentificado> movimientos;

    @Size(max = 32)
    @Column(name = "VReferencia", length = 32)
    private String referencia;

    @Size(max = 264)
    @Column(name = "VComentario", length = 264)
    private String comentario;

    @Size(max = 128)
    @Column(name = "VAplicadoEn", length = 128)
    private String aplicadoEn;

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public byte[] getComprobante() {
        return comprobante;
    }

    public void setComprobante(byte[] comprobante) {
        this.comprobante = comprobante;
    }

    public Cuenta getCuentaBanco() {
        return cuentaBanco;
    }

    public void setCuentaBanco(Cuenta cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public Cuenta getCuentaIngreso() {
        return cuentaIngreso;
    }

    public void setCuentaIngreso(Cuenta cuentaIngreso) {
        this.cuentaIngreso = cuentaIngreso;
    }

    public CatalogoEstatusIngreso getEstatus() {
        return estatus;
    }

    public void setEstatus(CatalogoEstatusIngreso estatus) {
        this.estatus = estatus;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatalogoMetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(CatalogoMetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Collection<MovimientoIngresoNoIdentificado> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Collection<MovimientoIngresoNoIdentificado> movimientos) {
        this.movimientos = movimientos;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getAplicadoEn() {
        return aplicadoEn;
    }

    public void setAplicadoEn(String aplicadoEn) {
        this.aplicadoEn = aplicadoEn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IngresoNoIdentificado other = (IngresoNoIdentificado) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    /*@JsonIgnore
    public BigDecimal getHaber() {
        BigDecimal haber = BigDecimal.ZERO;
            if(movimientos != null && !CollectionUtils.isEmpty(movimientos) &&
                    movimientos.iterator().hasNext()) {
                haber.add(movimientos.iterator().next().getHaber());
            }
        return haber;
    }*/
}
