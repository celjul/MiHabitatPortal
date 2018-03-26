package com.bstmexico.mihabitat.notificaciones.model;

import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.comunicacion.blogs.model.PostIncidencia;
import com.bstmexico.mihabitat.comunicacion.blogs.model.TemaIncidencia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Pegasus on 07/08/2015.
 */
@Entity
@DiscriminatorValue("incidencia-actualizada")
public class IncidenciaActualizadaNotification extends Notification{

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NIdRol")
    private CatalogoRol rol;

    @JsonIgnore
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, targetEntity = PostIncidencia.class)
    @JoinColumn(name = "NIdPostIncidencia")
    private PostIncidencia avanceIncidencia;

    public IncidenciaActualizadaNotification() {
        this.setEmailTemplate("incidencia-pendiente.html");
    }

    public PostIncidencia getAvanceIncidencia() {
        return avanceIncidencia;
    }

    public void setAvanceIncidencia(PostIncidencia avanceIncidencia) {
        this.avanceIncidencia = avanceIncidencia;
    }

    public CatalogoRol getRol() {
        return rol;
    }

    public void setRol(CatalogoRol rol) {
        this.rol = rol;
    }

    @Override
    public DateTime getFecha() {
        if(super.getFecha() == null){
            super.setFecha(new DateTime(this.getAvanceIncidencia().getFecha()));
        }
        return super.getFecha();
    }

    @Override
    public String getTitulo() {
        if(this.getAvanceIncidencia() != null && this.getAvanceIncidencia().getEstatusIncidencia()!= null && this.getAvanceIncidencia().getEstatusIncidencia().getId()!= null) {
            if (this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.solicitada"))) {
                if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                    return "Incidencia Reportada: " + this.getAvanceIncidencia().getTema().getNombre();
                } else {
                    return "Incidencia " + this.getAvanceIncidencia().getTema().getNombre() +" en espera de Atención";
                }
            } else {
                return "Incidencia " + this.getAvanceIncidencia().getTema().getNombre() +" actualizada con estatus " + (this.getAvanceIncidencia()!=null?this.getAvanceIncidencia().getEstatusIncidencia().getDescripcion():"");
            }
        }
        return super.getTitulo();
    }

    @Override
    public String getEmailTemplate() {
        if(this.getAvanceIncidencia() != null && this.getAvanceIncidencia().getEstatusIncidencia() != null ) {
            return (
                    (this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.pendiente")) ||
                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.repetida")) ||
                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.cancelado")) ||
                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.concluido"))) ? "incidencia-actualizada.html" :
                            (
                                    this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.solicitada")) ? "incidencia-solicitada.html" :
                                            "incidencia-solicitada.html"
                            )
            );
        }
        return super.getEmailTemplate();
    }

    @Override
    public String getSubtipo() {
        if(this.getAvanceIncidencia() != null && this.getAvanceIncidencia().getEstatusIncidencia().getId() != null ) {
            return (
                    (
                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.pendiente")) ? "incidencia-pendiente" :
                                    (
                                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.repetida")) ? "incidencia-repetida" :
                                                    (
                                                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.cancelado")) ? "incidencia-cancelado" :
                                                                    (
                                                                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.concluido")) ? "incidencia-concluido" :
                                                                            (
                                                                                this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.solicitada")) ? "incidencia-solicitada" :
                                                                                    "incidencia-solicitada"
                                                                            )
                                                                    )
                                                    )
                                    )
                    )
            );
            /*return (
                    (this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.pendiente")) ||
                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.repetida")) ||
                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.cancelado")) ||
                            this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.concluido"))) ? "incidencia-actualizada" :
                            (
                                    this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.solicitada")) ? "incidencia-solicitada" :
                                            "incidencia-solicitada"
                            )
            );*/
        }
        return "incidencia-pendiente";
    }

    @Override
    public String getLink() {
        if(this.getAvanceIncidencia() != null && this.getAvanceIncidencia().getEstatusIncidencia().getId()!= null) {
            if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                return String.format("/administrador/blogs/temas/actualizar/%s", this.getAvanceIncidencia().getTema().getId());
            } else {
                return ("/contacto/blogs/temas/actualizar/" +
                        this.getAvanceIncidencia().getTema().getId());
            }
        }
        return super.getLink();
    }

    @Override
    public String getFormattedHtmlAlert(){
        DateTime hoy = DateTime.now();
        Duration duracion = new Duration(new DateTime(this.getFecha()), hoy);
        StringBuffer notificacionStr = new StringBuffer();
        if(this.getAvanceIncidencia() != null && this.getAvanceIncidencia().getEstatusIncidencia()!= null) {
            if (this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.solicitada"))) {
                if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                    notificacionStr.append("<strong>" + getAvanceIncidencia().getUsuario().getPersona().getNombreCompleto() +
                            "</strong> ha registrado una incidencia " +
                            " <strong>" + getAvanceIncidencia().getTema().getNombre() + "</strong> que está en espera de respuesta." +
                            " <span class=\"pull-right font-xs text-muted\"><i>Hace " +
                            (duracion.getStandardDays() != 0 ? duracion.getStandardDays() + " dias y " : " ") +
                            (duracion.getStandardHours() - (duracion.getStandardDays() * 24)) + " horas..." +
                            "...</i></span>");
                } else {
                    notificacionStr.append("Su incidencia registrada <strong>\"" + getAvanceIncidencia().getTema().getNombre() + "\"</strong> se encuentra en espera de atención por parte de la administración. " +
                            " <span class=\"pull-right font-xs text-muted\"><i>Hace " +
                            (duracion.getStandardDays()!=0?duracion.getStandardDays() + " dias y ":" ") +
                            (duracion.getStandardHours()-(duracion.getStandardDays()*24)) + " horas..." +
                            "...</i></span>");
                }
            } else {
                notificacionStr.append("Nueva actualización de su incidencia reportada <strong>" +
                        this.getAvanceIncidencia().getTema().getNombre() + "</strong> con estatus <strong>" +
                        (
                                this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.pendiente")) ? "En Atención" :
                                        (
                                                this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.repetida")) ? "Repetida" :
                                                        (
                                                                this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.concluido")) ? "Concluida" :
                                                                        (
                                                                                this.getAvanceIncidencia().getEstatusIncidencia().getId() == Long.valueOf(configuration.getProperty("incidencia.estatus.cancelado")) ? "Cancelada" :
                                                                                        "Modificada"
                                                                        )
                                                        )
                                        )
                        )
                        +
                        "</stronf>.&nbsp;" +
                        "<span class='pull-right font-xs text-muted'><i> " +

                        "Hace " +
                        (duracion.getStandardDays() != 0 ? duracion.getStandardDays() + " dias y " : " ") +
                        (duracion.getStandardHours() - (duracion.getStandardDays() * 24)) + " horas..."

                        + "...</i></span>");
            }
        }
        return notificacionStr.toString();
    }

}
