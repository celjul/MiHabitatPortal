package com.bstmexico.mihabitat.especiales.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
@Entity
@Table(name = "tcicargasiniciales")
public class CargaInicial implements Serializable {

    private static final long serialVersionUID = 1354049397051938335L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdCargaInicial", nullable = false, unique = true)
    private Long id;

    @NotNull
    @JoinColumn(name = "NIdCondominio", referencedColumnName = "NIdCondominio", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Condominio condominio;

    @NotNull
    @JoinColumn(name = "NIdEstatusCargaInicial", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoEstatusCargaInicial estatusCargaInicial;

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

    public CatalogoEstatusCargaInicial getEstatusCargaInicial() {
        return estatusCargaInicial;
    }

    public void setEstatusCargaInicial(CatalogoEstatusCargaInicial estatusCargaInicial) {
        this.estatusCargaInicial = estatusCargaInicial;
    }
}
