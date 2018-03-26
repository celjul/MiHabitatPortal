package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.cobranza.model.Nota;
import com.bstmexico.mihabitat.cobranza.model.Recordatorio;
import com.bstmexico.mihabitat.cobranza.service.NotaService;
import com.bstmexico.mihabitat.cobranza.service.RecordatorioService;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaEvento;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaService;
import com.bstmexico.mihabitat.comunicacion.tareas.model.RecordatorioAdministracion;
import com.bstmexico.mihabitat.comunicacion.tareas.model.Tarea;
import com.bstmexico.mihabitat.comunicacion.tareas.service.RecordatorioAdministracionService;
import com.bstmexico.mihabitat.comunicacion.tareas.service.TareaService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.model.Cuenta;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.movimientos.model.*;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoDetallle;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoGasto;
import com.bstmexico.mihabitat.web.controllers.administrador.reportes.ReporteCuentasCobrarController;
import com.bstmexico.mihabitat.web.controllers.administrador.reportes.ReporteCuentasPagarController;
import com.bstmexico.mihabitat.web.controllers.administrador.reportes.ReporteIngresosEgresosController;
import com.bstmexico.mihabitat.web.dto.inicio.Evento;
import com.bstmexico.mihabitat.web.dto.inicio.InicioAdministrador;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Zo� Jonatan Tapia Hern�ndez
 * @version 1.0
 * @since 2015
 */

@Controller
@RequestMapping(value = "administrador/inicio")
public class InicioAdminController {

    @Autowired
    private ReporteIngresosEgresosController ingresosEgresosController;

    @Autowired
    private ReporteCuentasCobrarController reporteCuentasCobrarController;

    @Autowired
    private ReporteCuentasPagarController reporteCuentasPagarController;

    @Autowired
    private TemaService temaService;

    @Autowired
    private NotaService notaService;

    @Autowired
    private RecordatorioAdministracionService recordatorioAdministracionService;

    @Autowired
    private TareaService tareaService;

    private static final Logger LOG = LoggerFactory
            .getLogger(InicioAdminController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/carga")
    public @ResponseBody
        InicioAdministrador consulta(Model model, HttpSession session) {

        Calendar inicioMes = Calendar.getInstance();
        inicioMes.set(GregorianCalendar.DAY_OF_MONTH, 1);

        Calendar hoy = Calendar.getInstance();

        Condominio condominio = (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue());

        InicioAdministrador inicioAdministrador = new InicioAdministrador();

        inicioAdministrador.setReporteIngresosEgresos(ingresosEgresosController.consultar(
                inicioMes.getTime(), hoy.getTime(), session));

        inicioAdministrador.setReporteCuentasCobrar(reporteCuentasCobrarController.consultar(hoy.getTime(), session));

        inicioAdministrador.setReporteCuentasPagar(reporteCuentasPagarController.consultar(hoy.getTime(), session));


        Collection<Evento> eventos = new ArrayList<>();
        Collection<Tema> temas = temaService.getListConPosts(condominio);
        for(Tema tema : temas) {
            if(tema instanceof TemaEvento) {
                Evento evento = new Evento();
                evento.setTitulo(((TemaEvento)tema).getNombre());
                evento.setInicio(((TemaEvento) tema).getFechaInicio());
                evento.setFin(((TemaEvento) tema).getFechaFin());
                evento.setTipo("evento");
                evento.setDescripcion(((TemaEvento) tema).getPosts().iterator().hasNext()?
                        ((TemaEvento) tema).getPosts().iterator().next().getComentario():"");
                evento.setIdReferencia(tema.getId());
                eventos.add(evento);
            }
        }
        Collection<Nota> notas = notaService.getList(condominio);
        for(Nota nota : notas) {
            if(nota.getRecordatorio() != null) {
                Evento evento = new Evento();
                evento.setTitulo("Cobranza | " + nota.getDepartamento().getNombre());
                evento.setInicio(new DateTime(nota.getRecordatorio().getFecha()));
                evento.setFin(new DateTime(nota.getRecordatorio().getFecha()));
                evento.setDescripcion(nota.getNota());
                evento.setTipo("cobranza");
                eventos.add(evento);
            }
        }
        Collection<RecordatorioAdministracion> recordatoriosAdministracion =
                recordatorioAdministracionService.getList(condominio);
        for(RecordatorioAdministracion recordatorioAdministracion : recordatoriosAdministracion){
            Evento evento = new Evento();
            evento.setTitulo(recordatorioAdministracion.getTitulo());
            evento.setInicio(recordatorioAdministracion.getFecha());
            evento.setDescripcion(recordatorioAdministracion.getDetalle());
            evento.setTipo("recordatorio");
            eventos.add(evento);
        }

        inicioAdministrador.setEventos(eventos);
        inicioAdministrador.setTemas(temas);

        inicioAdministrador.setTareas(tareaService.getList(condominio));

        return inicioAdministrador;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/recordatorio/guardar")
    public @ResponseBody
    Collection<RecordatorioAdministracion> guardarRecordatorio(
            @RequestBody RecordatorioAdministracion recordatorioAdministracion,
            Model model, HttpSession session) {
        recordatorioAdministracionService.save(recordatorioAdministracion);
        return recordatorioAdministracionService.getList((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/recordatorio/eliminar")
    public @ResponseBody
    Collection<RecordatorioAdministracion> eliminarRecordatorio(
            @RequestBody RecordatorioAdministracion recordatorioAdministracion,
            Model model, HttpSession session) {
        recordatorioAdministracionService.delete(recordatorioAdministracion);
        return recordatorioAdministracionService.getList((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tarea/guardar")
    public @ResponseBody
    Collection<Tarea> guardarTarea(
            @RequestBody Tarea tarea,
            Model model, HttpSession session) {
        tarea.setCondominio((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
        tareaService.save(tarea);
        return tareaService.getList((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tarea/actualizar")
    public @ResponseBody
    Collection<Tarea> actualizarTarea(
            @RequestBody Tarea tarea,
            Model model, HttpSession session) {
        tarea.setCondominio((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
        tareaService.update(tarea);
        return tareaService.getList((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tarea/eliminar")
    public @ResponseBody
    Collection<Tarea> eliminarTarea(
            @RequestBody Tarea tarea,
            Model model, HttpSession session) {
        tareaService.delete(tarea);
        return tareaService.getList((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
    }

}
