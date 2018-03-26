package com.bstmexico.mihabitat.otrosingresos.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "totrosingresos")
public class OtroIngreso implements Serializable {

    private static final long serialVersionUID = -8296778234722219348L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdOtroIngreso", nullable = false, unique = true)
    private Long id;

    @NotNull
    @JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Condominio condominio;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "longblob", name = "BComprobante")
    private byte[] comprobante;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
    @NotNull
    @Column(columnDefinition = "date", name = "DFecha", nullable = false)
    private Date fecha;

    @NotNull
    @JoinColumn(name = "NIdMetodoPago", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoMetodoPago metodoPago;

    @NotNull
    @OneToOne(mappedBy = "otroIngreso", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private MovimientoOtroIngreso movimientoOtroIngreso;

    @Size(max = 32)
    @Column(name = "VReferencia", length = 32)
    private String referencia;

    @Size(max = 264)
    @Column(name = "VComentario", length = 264)
    private String comentario;

    @JoinColumn(name = "NIdIngreso", nullable = true, referencedColumnName = "NIdOtroIngreso")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<ConceptoIngreso> conceptos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CatalogoMetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(CatalogoMetodoPago metodoPago) {
        this.metodoPago = metodoPago;
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

    public Collection<ConceptoIngreso> getConceptos() {
        return conceptos;
    }

    public void setConceptos(Collection<ConceptoIngreso> conceptos) {
        this.conceptos = conceptos;
    }

    public MovimientoOtroIngreso getMovimientoOtroIngreso() {
        return movimientoOtroIngreso;
    }

    public void setMovimientoOtroIngreso(MovimientoOtroIngreso movimientoOtroIngreso) {
        this.movimientoOtroIngreso = movimientoOtroIngreso;
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
        OtroIngreso other = (OtroIngreso) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @JsonIgnore
    public BigDecimal getHaber() {
        BigDecimal haber = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(getConceptos())) {
            for (ConceptoIngreso conceptoIngreso : conceptos) {
                if (conceptoIngreso.getMovimientoConceptoOtroIngreso().getHaber() != null) {
                    haber = haber.add(conceptoIngreso.getMovimientoConceptoOtroIngreso().getHaber());
                }
            }
        }
        return haber;
    }
}
