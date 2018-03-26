package com.bstmexico.mihabitat.web.dto.inicio;

import org.joda.time.DateTime;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2015
 */
public class Evento {

    private DateTime inicio;

    private DateTime fin;

    private String titulo;

    private String descripcion;

    private String tipo;

    private Long idReferencia;

    public DateTime getInicio() {
        return inicio;
    }

    public void setInicio(DateTime inicio) {
        this.inicio = inicio;
    }

    public DateTime getFin()
    {
        return fin;
    }

    public void setFin(DateTime fin) {
        this.fin = fin;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Long idReferencia) {
        this.idReferencia = idReferencia;
    }
}
