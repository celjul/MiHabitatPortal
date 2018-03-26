package com.bstmexico.mihabitat.notificaciones.model;

import com.bstmexico.mihabitat.comunes.usuarios.model.CatalogoRol;
import com.bstmexico.mihabitat.pagos.model.EstatusPago;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Pegasus on 29/06/2015.
 */
@Entity
@DiscriminatorValue("pago-validado")
public class PagoValidadoNotification extends Notification{

    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NIdRol")
    private CatalogoRol rol;

    @JsonIgnore
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, targetEntity = EstatusPago.class)
    @JoinColumn(name = "NIdEstatusPago")
    private EstatusPago estatusPago;

    public PagoValidadoNotification() {
        this.setEmailTemplate("pago-pendiente.html");
    }

    public EstatusPago getEstatusPago() {
        return estatusPago;
    }

    public void setEstatusPago(EstatusPago estatusPago) {
        this.estatusPago = estatusPago;
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
            super.setFecha(new DateTime(this.getEstatusPago().getPago().getFecha()));
        }
        return super.getFecha();
    }

    @Override
    public String getTitulo() {
        if(this.getEstatusPago() != null && this.getEstatusPago().getPago()!= null && this.getEstatusPago().getPago().getId() != null ) {
            if (this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("pendiente"))) {
                if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                    return "Nueva Solicitud de Registro de Pago";
                } else {
                    return "Pago en espera de Aprobación";
                }
            } else {
                return "Pago " + this.getEstatusPago().getEstatus().getDescripcion();
            }
        }
        return super.getTitulo();
    }

    @Override
    public String getEmailTemplate() {
        if(this.getEstatusPago() != null && this.getEstatusPago().getPago()!= null && this.getEstatusPago().getPago().getId() != null ) {
            return (
                (this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("aprobado")) ||
                        this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("rechazado")) ||
                            this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("cancelado")))? "pago-validado.html" :
                        (
                                this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("pendiente")) ? "pago-pendiente.html" :
                                        "pago-pendiente.html"
                        )
            );
        }
        return super.getEmailTemplate();
    }

    @Override
    public String getSubtipo() {
        if(this.getEstatusPago() != null && this.getEstatusPago().getPago()!= null && this.getEstatusPago().getPago().getId() != null ) {
             return (
                    (
                            this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("aprobado")) ? "pago-aprobado" :
                                    (
                                            this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("rechazado")) ? "pago-rechazado" :
                                                    (
                                                            this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("cancelado")) ? "pago-cancelado" :
                                                                    (
                                                                            this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("pendiente")) ? "pago-pendiente" :
                                                                                    "pago-desconocido"
                                                                    )
                                                    )
                                    )
                    )
            );
        }
        return "pago-pendiente";
    }

    @Override
    public String getLink() {
        if(this.getEstatusPago() != null && this.getEstatusPago().getPago()!= null && this.getEstatusPago().getPago().getId() != null ) {
            if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                return "/administrador/pagos/actualizar/" + this.getEstatusPago().getPago().getId();
            } else {
                return "/contacto/mis-pagos/actualizar/" + this.getEstatusPago().getPago().getId();
            }
        }
        return super.getLink();
    }

    @Override
    public String getFormattedHtmlAlert(){
        DateTime hoy = DateTime.now();
        Duration duracion = new Duration(new DateTime(this.getFecha()), hoy);
        StringBuffer notificacionStr = new StringBuffer();
        if(this.getEstatusPago() != null && this.getEstatusPago().getPago()!= null && this.getEstatusPago().getPago().getId() != null ) {
            if (this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("pendiente"))) {
                if(this.rol == null || this.rol.getId().equals(Long.valueOf(configuration.getProperty("roles.administrador")))) {
                    notificacionStr.append("<strong>"+this.getEstatusPago().getPago().getContacto().getNombreCompleto() +
                            "</strong> ha registrado un pago por un monto de " +
                            " <strong>" + this.getEstatusPago().getPago().getMonto() + "</strong> que está en espera de aprobación." +
                            " <span class=\"pull-right font-xs text-muted\"><i>Hace " +
                            (duracion.getStandardDays()!=0?duracion.getStandardDays() + " dias y ":" ") +
                            (duracion.getStandardHours()-(duracion.getStandardDays()*24)) + " horas..." +
                            "...</i></span>");
                } else {
                    notificacionStr.append("Su pago registrado por <strong>$" + this.getEstatusPago().getPago().getMonto() +
                            "</strong> se encuentra en espera de ser aprobado por parte de la administración. " +
                            " <span class=\"pull-right font-xs text-muted\"><i>Hace " +
                            (duracion.getStandardDays()!=0?duracion.getStandardDays() + " dias y ":" ") +
                            (duracion.getStandardHours()-(duracion.getStandardDays()*24)) + " horas..." +
                            "...</i></span>");
                }
            } else {
                notificacionStr.append("La Administración ha <strong>" +
                        (
                                this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("aprobado")) ? "Aprobado" :
                                        (
                                                this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("rechazado")) ? "Rechazado" :
                                                        (
                                                                this.getEstatusPago().getEstatus().getId() == Long.valueOf(configuration.getProperty("cancelado")) ? "Cancelado" :
                                                                        "Modificado"
                                                        )
                                        )
                        )
                        +
                        "</strong> su pago por un monto de <strong>$" +
                        this.getEstatusPago().getPago().getMonto() +
                        "</strong>.&nbsp;" +
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
