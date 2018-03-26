package com.bstmexico.mihabitat.notificaciones.model;

        import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
        import com.fasterxml.jackson.annotation.JsonIgnore;
        import org.joda.time.DateTime;
        import org.joda.time.Duration;
        import org.joda.time.format.DateTimeFormat;
        import org.joda.time.format.DateTimeFormatter;

        import javax.persistence.*;
        import javax.validation.constraints.NotNull;
        import java.math.BigDecimal;

/**
 * Created by Pegasus on 16/06/2015.
 */
@Entity
@DiscriminatorValue("nuevo-cargo")
public class NuevoCargoNotification extends Notification {

    @Transient
    DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MMM/yyyy");

    @JsonIgnore
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, targetEntity = CargoDepartamento.class)
    @JoinColumn(name = "NIdCargo")
    private CargoDepartamento cargo;

    public NuevoCargoNotification() {
        this.setEmailTemplate("cargo-nuevo.html");
    }

    public CargoDepartamento getCargo() {
        return cargo;
    }

    public void setCargo(CargoDepartamento cargo) {
        this.cargo = cargo;
    }

    @Override
    public DateTime getFecha() {
        if(super.getFecha() == null){
            super.setFecha(new DateTime(this.getCargo().getFecha()));
        }
        return super.getFecha();
    }

    @Override
    public String getTitulo() {
        return "Nuevo Cargo: " + this.getCargo()!=null?this.getCargo().getConcepto():"";
    }

    @Override
    public String getEmailTemplate() {
        return "cargo-nuevo.html";
    }

    @Override
    public String getSubtipo() {
        return "nuevo-cargo";
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
            notificacionStr.append("Se ha generado un nuevo cargo por <strong>$" +
                this.getCargo().getTotalMonto() +
                "</strong> para el departamento <strong>" +
                    this.getCargo().getDepartamento().getNombre() +
                "</strong> por concepto de  <strong>" +
                    this.getCargo().getConcepto() +
                "</strong>." +
                (
                        this.getCargo().getSaldoPendiente().doubleValue() == 0?
                                (" <strong class='label label-success'> Cargo Pagado </strong>")
                                :
                                (
                                        this.getCargo().getDescuento() != null &&
                                                this.getCargo().getDescuento().getFecha().getTime() >= hoy.toDateMidnight().getMillis()?
                                                (
                                                        "<br/><br/> <strong class='label label-warning'>IMPORTANTE !</strong> Pague completo este cargo antes del <strong>" +
                                                                fmt.print(this.getCargo().getDescuento().getFecha().getTime()) +
                                                                "</strong> y aproveche el descuento para pagar solo <strong>$"+
                                                                (this.getCargo().getTotalMonto().subtract(this.getCargo().getDescuentosCalculados()).
                                                                        setScale(2, BigDecimal.ROUND_HALF_UP)) +
                                                                "</strong>"
                                                ):
                                                (
                                                        this.getCargo().getRecargo() != null &&
                                                                this.getCargo().getRecargo().getFecha().getTime() >= hoy.toDateMidnight().getMillis()?
                                                                "<br/><br/> <strong class='label label-warning'>IMPORTANTE !</strong> Pague completo este cargo antes del <strong>" +
                                                                        fmt.print(this.getCargo().getRecargo().getFecha().getTime()) +
                                                                        "</strong> y evite recargos por pago tardio por hasta <strong>$" + this.getCargo().getRecargosCalculados().
                                                                        setScale(2,BigDecimal.ROUND_HALF_UP) +
                                                                        "</strong>"
                                                                :
                                                                (

                                                                        " <strong class='label label-warning'> Este cargo tiene un saldo pendiente de " +
                                                                                this.getCargo().getSaldoPendiente()+" </strong>"
                                                                )
                                                )
                                )
                )
                +
                "<span class='pull-right font-xs text-muted'><i> " +
                "Hace " +
                (duracion.getStandardDays() != 0 ? duracion.getStandardDays() + " dias y " : " ") +
                (duracion.getStandardHours() - (duracion.getStandardDays() * 24)) + " horas..."

                + "...</i></span>");
        return notificacionStr.toString();
    }
}
