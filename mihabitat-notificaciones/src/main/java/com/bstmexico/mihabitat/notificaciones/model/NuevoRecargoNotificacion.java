package com.bstmexico.mihabitat.notificaciones.model;

import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Pegasus on 29/06/2015.
 */
@Entity
@DiscriminatorValue("nuevo-recargo")
public class NuevoRecargoNotificacion extends Notification {

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, targetEntity = MovimientoCargo.class)
    @JoinColumn(name = "NIdRecargo")
    private MovimientoCargo recargo;

    public NuevoRecargoNotificacion() {
        this.setEmailTemplate("recargo-nuevo.html");
    }

    public MovimientoCargo getRecargo() {
        return recargo;
    }

    public void setRecargo(MovimientoCargo recargo) {
        this.recargo = recargo;
    }

    @Override
    public DateTime getFecha() {
        if(super.getFecha() == null){
            super.setFecha(new DateTime(this.getRecargo().getFecha()));
        }
        return super.getFecha();
    }

    @Override
    public String getTitulo() {
        return "Se Ha Generado un Recargo";
    }

    @Override
    public String getEmailTemplate() {
        return "recargo-nuevo.html";
    }

    @Override
    public String getSubtipo() {
        return "nuevo-recargo";
    }

    @Override
    public String getLink() {
        return "/contacto/mis-estados-cuenta/consulta";
    }

    @Override
    public String getFormattedHtmlAlert(){
        DateTime hoy = DateTime.now();
        Duration duracion = new Duration(new DateTime(this.getFecha()), hoy);
        StringBuffer notificacionStr = new StringBuffer();
        notificacionStr.append("Se ha generado un recargo por <strong>$" +
                this.getRecargo().getDebe() +
                "</strong> para el cargo <strong>" + this.getRecargo().getCargo().getConcepto() +
                "</strong> del departamento <strong>" +
                this.getRecargo().getCargo().getDepartamento().getNombre() +
                "</strong>." +
                "<span class='pull-right font-xs text-muted'><i> " +

                "Hace " +
                (duracion.getStandardDays() != 0 ? duracion.getStandardDays() + " dias y " : " ") +
                (duracion.getStandardHours() - (duracion.getStandardDays() * 24)) + " horas..."

                + "...</i></span>");
        return notificacionStr.toString();
    }
}
