package com.bstmexico.mihabitat.notificaciones.model;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaEvento;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaIncidencia;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pegasus on 05/08/2015.
 */
public class NuevoTemaNotification extends  Notification{
    @JsonIgnore
    private static final SimpleDateFormat FORMATO = new SimpleDateFormat("yyyy-MM-dd");

    @JsonIgnore
    private static final SimpleDateFormat FORMATO_HORA = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    @JsonIgnore
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Tema.class)
    @JoinColumn(name = "NIdTema")
    private Tema tema;

    public NuevoTemaNotification() {

    }

    @Override
    public String getEmailTemplate() {
        return "nuevo-tema.html";
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    @Override
    public String getTitulo() {
        String tituloStr = "";
        if(this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.general.bienvenidos")) ||
                this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.general.noticias")) ||
                this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.general.expertos"))){

            tituloStr = "¡Tenemos noticias!";

        } else if(this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.condominio.avisos")) ||
                this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.condominio.temas")) ||
                this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.condominio.documentos"))) {

            if(this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.condominio.avisos"))){
                tituloStr = "Aviso de la Administración: " + this.getTema().getNombre();
            } else {
                tituloStr = "Nueva Publicación: " + this.getTema().getNombre();
            }

        } else if(this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.condominio.incidencias"))) {
            tituloStr = "Nueva Indidencia Reportada";
        } else if(this.getTema().getBlog().getId() == Long.valueOf(configuration.getProperty("blog.condominio.soporte"))) {
            tituloStr = "Nueva Solicitud de Soporte";
        }

        return tituloStr;
    }

    @Override
    public String getSubtipo() {
        String subt = "nuevo_tema";
        if(this.getTema() != null) {
            if (this.getTema() instanceof TemaEvento) {
                subt = "nuevo_tema";
            } else if (this.getTema() instanceof TemaIncidencia) {
                subt = "nueva_incidencia";
            } else {
                subt = "nuevo_tema";
            }
        }
        return subt;
    }

    @Override
    public String getLink() {
        if(this.getTema() != null) {
            return "/blogs/temas/actualizar/" + this.getTema().getId();
        }
        return super.getLink();
    }

    @Override
    public String getFormattedHtmlAlert(){
        String mensaje = "";
        if(this.getTema() instanceof TemaEvento) {
            mensaje = ("" + this.getTema().getNombre() + "<small>" + FORMATO_HORA.format(((TemaEvento) this.getTema()).getFechaInicio().toDate()) +
                    " - " + FORMATO_HORA.format(((TemaEvento) this.getTema()).getFechaFin().toDate()) + "</small>");
        } else if(this.getTema() instanceof TemaIncidencia) {
            if(((TemaIncidencia) this.getTema()).getTipoIncidencia().getId() == Long.valueOf(configuration.getProperty("blog.general.incidencias"))) {
                mensaje = (this.getTema().getUsuario().getPersona().getNombreCompleto() + " " +
                        "registró una nueva incidencia " + this.getTema().getNombre());
            } else {
                mensaje = ("Se ha registrado un nuevo proyecto "  + this.getTema().getNombre());
            }
        } else {
            mensaje = (this.getTema().getPrimerPost().getComentario().length() < 50 ?
                    this.getTema().getPrimerPost().getComentario() : this.getTema().getPrimerPost().getComentario().substring(0, 50) + "...");
        }
        return mensaje;
    }
}
