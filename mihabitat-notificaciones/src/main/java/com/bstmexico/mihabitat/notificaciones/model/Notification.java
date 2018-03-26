package com.bstmexico.mihabitat.notificaciones.model;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

/**
 * Created by Pegasus on 12/06/2015.
 */
@Entity
@Table(name = "tnotificaciones")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "VTipo")
/*@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "clase")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PagosPendientesNotification.class, name = "pago-pendiente"),
        @JsonSubTypes.Type(value = ReservacionesPendientesNotification.class, name = "reservacion-pendiente")})*/
public class Notification implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3815813452554560986L;

    @JsonIgnore
    @Transient
    protected static Properties configuration;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdNotificacion", nullable = false, unique = true)
    private Long id;

    @NotNull
    @JsonIgnoreProperties(value = "administradores")
    @JoinColumn(name = "NIdCondominio", nullable = false, referencedColumnName = "NIdCondominio")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Condominio condominio;

    /*@NotNull
    @Size(min = 1, max = 128)
    @Column(length = 128, name = "VMensaje", nullable = false)
    private String mensaje;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(length = 64, name = "VTitulo", nullable = false)
    private String titulo;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(length = 64, name = "VSubtipo", nullable = false)
    private String subtipo;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(length = 64, name = "VLink", nullable = false)
    private String link;*/

    @NotNull
    @Column(name = "BVisto", nullable = false)
    private Boolean visto;

    @Column(name = "DFechaFin", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fecha;

    @Transient
    private String emailTemplate;

    public Notification() {
        this.setVisto(false);
        try {
            if(configuration == null) {
                configuration = PropertiesLoaderUtils
                        .loadAllProperties("configuration.properties");
            }
        } catch (IOException e) {
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return "";
    }

    public String getTitulo() {
        return "Nueva Notificación";
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getVisto() {
        return visto;
    }

    public void setVisto(Boolean visto) {
        this.visto = visto;
    }

    public String getSubtipo() {
        return "notificacion-desconocida";
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public String getFormattedHtmlAlert(){
        return "";
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer("{");
        str.append("titulo : ").append(this.getTitulo()).append(", ");
        str.append("link : ").append(this.getLink());
        str.append("}");

        return str.toString();
    }

}
