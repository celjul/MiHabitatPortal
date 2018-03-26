package com.bstmexico.mihabitat.web.dto.inicio;

import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.tareas.model.Tarea;
import com.bstmexico.mihabitat.web.dto.EstadoCuenta;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentasCobrar;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentasPagar;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteIngresosEgresos;

import java.util.Collection;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */
public class InicioContacto {

    private Collection<Evento> eventos;

    private Collection<Tema> temas;

    private Collection<EstadoCuenta> estadosDeCuenta;

    public Collection<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(Collection<Evento> eventos) {
        this.eventos = eventos;
    }

    public Collection<Tema> getTemas() {
        return temas;
    }

    public void setTemas(Collection<Tema> temas) {
        this.temas = temas;
    }

    public Collection<EstadoCuenta> getEstadosDeCuenta() {
        return estadosDeCuenta;
    }

    public void setEstadosDeCuenta(Collection<EstadoCuenta> estadosDeCuenta) {
        this.estadosDeCuenta = estadosDeCuenta;
    }
}

