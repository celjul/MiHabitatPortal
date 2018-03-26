package com.bstmexico.mihabitat.web.dto.inicio;

import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.tareas.model.Tarea;
import com.bstmexico.mihabitat.web.dto.reportes.Cuenta;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentasCobrar;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteCuentasPagar;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteIngresosEgresos;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2015
 */
public class InicioAdministrador {

    private ReporteIngresosEgresos reporteIngresosEgresos;

    private ReporteCuentasCobrar reporteCuentasCobrar;

    private ReporteCuentasPagar reporteCuentasPagar;

    private Collection<Evento> eventos;

    private Collection<Tema> temas;

    private Collection<Tarea> tareas;

    public ReporteIngresosEgresos getReporteIngresosEgresos() {
        return reporteIngresosEgresos;
    }

    public void setReporteIngresosEgresos(ReporteIngresosEgresos reporteIngresosEgresos) {
        this.reporteIngresosEgresos = reporteIngresosEgresos;
    }

    public ReporteCuentasCobrar getReporteCuentasCobrar() {
        return reporteCuentasCobrar;
    }

    public void setReporteCuentasCobrar(ReporteCuentasCobrar reporteCuentasCobrar) {
        this.reporteCuentasCobrar = reporteCuentasCobrar;
    }

    public ReporteCuentasPagar getReporteCuentasPagar() {
        return reporteCuentasPagar;
    }

    public void setReporteCuentasPagar(ReporteCuentasPagar reporteCuentasPagar) {
        this.reporteCuentasPagar = reporteCuentasPagar;
    }

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

    public Collection<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(Collection<Tarea> tareas) {
        this.tareas = tareas;
    }
}

