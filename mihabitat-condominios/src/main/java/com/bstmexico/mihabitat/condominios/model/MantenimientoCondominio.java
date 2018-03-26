package com.bstmexico.mihabitat.condominios.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tmantenimientoscondominio")
public class MantenimientoCondominio implements Serializable {

    private static final long serialVersionUID = 5371810722974951039L;

    @NotNull
    @JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Condominio condominio;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(length = 128, name = "VDescripcion", nullable = false)
    private String descripcion;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdMantenimiento", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Min(0)
    @Column(name = "NMonto", nullable = false, precision = 9, scale = 2)
    private BigDecimal monto;

    @NotNull
    @JoinColumn(name = "NIdTipoCobro", nullable = false, referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private CatalogoTipoCobroDepartamento tipoCobroDepartamento;

    public MantenimientoCondominio() {
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public CatalogoTipoCobroDepartamento getTipoCobroDepartamento() {
        return tipoCobroDepartamento;
    }

    public void setTipoCobroDepartamento(CatalogoTipoCobroDepartamento tipoCobroDepartamento) {
        this.tipoCobroDepartamento = tipoCobroDepartamento;
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
        MantenimientoCondominio other = (MantenimientoCondominio) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
