package com.bstmexico.mihabitat.notificaciones.model;

import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.instalaciones.model.Reservacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Pegasus on 29/06/2015.
 */
@Entity
@DiscriminatorValue("reservacion-validada")
public class ReservacionValidadaNotification extends Notification {

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NIdRol")
    private CatalogoRol rol;

    @JsonIgnore
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, targetEntity = Reservacion.class)
    @JoinColumn(name = "NIdReservacion")
    private Reservacion reservacion;

    public ReservacionValidadaNotification() {
        this.setEmailTemplate("reservacion-validada.html");
    }

    public Reservacion getReservacion() {
        return reservacion;
    }

    public void setReservacion(Reservacion reservacion) {
        this.reservacion = reservacion;
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
            super.setFecha(new DateTime(this.getReservacion().getFechaInicio()));
        }
        return super.getFecha();
    }

    @Override
    public String getTitulo() {
        if(this.getReservacion() != null && this.getReservacion().getId()!= null) {
            if (this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.pendiente"))) {
                if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                    return "Nueva Solicitud de Reservación";
                } else {
                    return "Reservación en espera de Aprobación";
                }
            } else {
                return "Reservación " + (this.getReservacion()!=null?this.getReservacion().getEstatusReservacion().getDescripcion():"");
            }
        }
        return super.getTitulo();
    }

    @Override
    public String getEmailTemplate() {
        if(this.getReservacion() != null && this.getReservacion().getId() != null ) {
            return (
                    (this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.aprobada")) ||
                            this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.rechazada")) ||
                            this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.cancelada"))) ? "reservacion-validada.html" :
                            (
                                    this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.pendiente")) ? "reservacion-pendiente.html" :
                                            "reservacion-pendiente.html"
                            )
            );
        }
        return super.getEmailTemplate();
    }

    @Override
    public String getSubtipo() {
        if(this.getReservacion() != null && this.getReservacion().getId() != null ) {
            return (
                    (
                            this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.aprobada")) ? "reservacion-aprobada" :
                                    (
                                            this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.rechazada")) ? "reservacion-rechazada" :
                                                    (
                                                            this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.cancelada")) ? "reservacion-cancelada" :
                                                                    (
                                                                            this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.pendiente")) ? "reservacion-pendiente" :
                                                                                    "pago-desconocido"
                                                                    )
                                                    )
                                    )
                    )
            );
            /*return (
                    (this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.aprobada")) ||
                            this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.rechazada")) ||
                            this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.cancelada"))) ? "reservacion-validada.html" :
                            (
                                    this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.pendiente")) ? "reservacion-pendiente" :
                                            "reservacion-pendiente"
                            )
            );*/
        }
        return "reservacion-validada";
    }

    @Override
    public String getLink() {
        //TODO: enviar al calendario en el lugar en donde está el evento
        if(this.getReservacion() != null && this.getReservacion().getId()!= null) {
            if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                return ("/administrador/instalaciones/reservar/" + this.getReservacion().getInstalacion().getId());
            } else {
                return ("/contacto/mis-reservaciones/reservar/" +
                        this.getReservacion().getInstalacion().getId());
            }
        }
        return super.getLink();
        /*return "/mis-reservaciones/reservar/";*/
    }

    @Override
    public String getFormattedHtmlAlert(){
        DateTime hoy = DateTime.now();
        Duration duracion = new Duration(hoy, new DateTime(this.getFecha()));
        StringBuffer notificacionStr = new StringBuffer();
        if(this.getReservacion() != null && this.getReservacion().getId()!= null) {
            if (this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.pendiente"))) {
                if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                    notificacionStr.append("<strong>"+reservacion.getContacto().getNombreCompleto()+
                            "</strong> ha solicitado una reservación para <strong>" +
                            reservacion.getInstalacion().getNombre()
                            + "</strong> " +
								/*"de <strong>(" + fmt.print(reservacion.getFechaInicio())
								+ " - " + fmt.print(reservacion.getFechaFin())
								+ ")</strong> " +*/
                            "que está en espera de aprobación." +
                            "<span class='pull-right font-xs text-muted'><i> " +
                            (duracion.getMillis()>0?(
                                    "Dentro de " +
                                            (duracion.getStandardDays() != 0 ? duracion.getStandardDays() + " dias y " : " ") +
                                            (duracion.getStandardHours() - (duracion.getStandardDays() * 24)) + " horas..."
                            )
                                    :
                                    "Vencida..."
                            )
                            + "...</i></span>");
                } else {
                    notificacionStr.append("Su reservación registrada para la instalación <strong>" + reservacion.getInstalacion().getNombre() +
                            "</strong> se encuentra en espera de ser aprobada por parte de la administración. " +
                            " <span class=\"pull-right font-xs text-muted\"><i>" +
                                    (duracion.getMillis()>0?(
                                            "Dentro de " +
                                                    (duracion.getStandardDays() != 0 ? duracion.getStandardDays() + " dias y " : " ") +
                                                    (duracion.getStandardHours() - (duracion.getStandardDays() * 24)) + " horas..."
                                    )
                                            :
                                            "Vencida..."
                                    ) +
                            "...</i></span>");
                }
            } else {
                notificacionStr.append("La Administración ha <strong>" +
                        (
                                this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.aprobada")) ? "Aprobado" :
                                        (
                                                this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.rechazada")) ? "Rechazado" :
                                                        (
                                                                this.getReservacion().getEstatusReservacion().getId() == Long.valueOf(configuration.getProperty("reservacion.cancelada")) ? "Cancelado" :
                                                                        "Modificado"
                                                        )
                                        )
                        )
                        +
                        "</strong> su reservación para la instalación <strong>" +
                        this.getReservacion().getInstalacion().getNombre() +
                        "</strong>.&nbsp;" +
                        "<span class='pull-right font-xs text-muted'><i> " +

                        (duracion.getMillis()>0?(
                                "Dentro de " +
                                        (duracion.getStandardDays() != 0 ? duracion.getStandardDays() + " dias y " : " ") +
                                        (duracion.getStandardHours() - (duracion.getStandardDays() * 24)) + " horas..."
                        )
                                :
                                "Vencida..."
                        )

                        + "...</i></span>");
            }
        }
        return notificacionStr.toString();
    }
}
