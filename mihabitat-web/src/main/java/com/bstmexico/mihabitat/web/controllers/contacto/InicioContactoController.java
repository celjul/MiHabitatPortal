package com.bstmexico.mihabitat.web.controllers.contacto;

import com.bstmexico.mihabitat.cobranza.model.Nota;
import com.bstmexico.mihabitat.cobranza.service.NotaService;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaEvento;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaService;
import com.bstmexico.mihabitat.comunicacion.tareas.model.RecordatorioAdministracion;
import com.bstmexico.mihabitat.comunicacion.tareas.model.Tarea;
import com.bstmexico.mihabitat.comunicacion.tareas.service.RecordatorioAdministracionService;
import com.bstmexico.mihabitat.comunicacion.tareas.service.TareaService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.web.controllers.administrador.reportes.ReporteCuentasCobrarController;
import com.bstmexico.mihabitat.web.controllers.administrador.reportes.ReporteCuentasPagarController;
import com.bstmexico.mihabitat.web.controllers.administrador.reportes.ReporteIngresosEgresosController;
import com.bstmexico.mihabitat.web.dto.EstadoCuenta;
import com.bstmexico.mihabitat.web.dto.inicio.Evento;
import com.bstmexico.mihabitat.web.dto.inicio.InicioAdministrador;
import com.bstmexico.mihabitat.web.dto.inicio.InicioContacto;
import com.bstmexico.mihabitat.web.service.EstadoCuentaService;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2015
 */

@Controller
@RequestMapping(value = "contacto/inicio")
public class InicioContactoController {

    @Autowired
    private TemaService temaService;

    @Autowired
    private EstadoCuentaService estadoCuentaService;

    @Autowired
    private ContactoService contactoService;

    private static final Logger LOG = LoggerFactory
            .getLogger(InicioContactoController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/carga")
    public @ResponseBody
        InicioContacto consulta(Model model, HttpSession session) {

        Calendar inicioMes = Calendar.getInstance();
        inicioMes.set(GregorianCalendar.DAY_OF_MONTH, 1);

        Calendar ultimoDeMes = Calendar.getInstance();
        ultimoDeMes.set(GregorianCalendar.DAY_OF_MONTH, ultimoDeMes.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

        Condominio condominio = (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue());

        Usuario usuario = (Usuario) session
                .getAttribute(SessionEnum.USUARIO.getValue());

        Map map = new HashMap();
        map.put("condominio", condominio);
        map.put("usuario", usuario);

        InicioContacto inicioContacto = new InicioContacto();
        Collection<EstadoCuenta> estadosDeCuenta = new ArrayList<>();

        Collection<Contacto> contactos = contactoService.search(map);
        Set<ContactoDepartamento> departamentos = new HashSet<>();
        if(!CollectionUtils.isEmpty(contactos)) {
            for (Contacto contacto : contactos) {
                for(ContactoDepartamento contactoDepartamento : contacto.getDepartamentos()) {
                    estadosDeCuenta.add(estadoCuentaService.getEstadoCuenta(condominio,
                            contactoDepartamento.getDepartamento(), inicioMes.getTime(), ultimoDeMes.getTime(), contacto));
                }
            }
        }

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

        inicioContacto.setEventos(eventos);
        inicioContacto.setTemas(temas);
        inicioContacto.setEstadosDeCuenta(estadosDeCuenta);

        return inicioContacto;
    }

}
