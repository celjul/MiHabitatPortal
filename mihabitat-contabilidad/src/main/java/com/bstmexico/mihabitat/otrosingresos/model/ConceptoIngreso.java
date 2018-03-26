package com.bstmexico.mihabitat.otrosingresos.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
@Entity
@Table(name = "tconceptosingresos")
public class ConceptoIngreso implements Serializable {

    private static final long serialVersionUID = -8296928234722219348L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdConceptoIngreso", nullable = false, unique = true)
    private Long id;

    @Size(max = 128)
    @Column(name = "VConcepto", length = 128)
    private String concepto;

    @NotNull
    @OneToOne(mappedBy = "conceptoIngreso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private MovimientoConceptoOtroIngreso movimientoConceptoOtroIngreso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public MovimientoConceptoOtroIngreso getMovimientoConceptoOtroIngreso() {
        return movimientoConceptoOtroIngreso;
    }

    public void setMovimientoConceptoOtroIngreso(MovimientoConceptoOtroIngreso movimientoConceptoOtroIngreso) {
        this.movimientoConceptoOtroIngreso = movimientoConceptoOtroIngreso;
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
        ConceptoIngreso other = (ConceptoIngreso) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
