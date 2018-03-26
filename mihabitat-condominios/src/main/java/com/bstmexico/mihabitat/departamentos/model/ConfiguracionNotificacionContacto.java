package com.bstmexico.mihabitat.departamentos.model;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.persistence.*;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Zo? Jonatan Tapia Hern?ndez
 * @version 1.0
 * @since 2017
 */
@Entity
@Table(name = "tnotconfcontacto", uniqueConstraints=
    @UniqueConstraint(columnNames={"NIdContacto", "NIdDepartamento"}))
public class ConfiguracionNotificacionContacto {

    @JsonIgnore
    @Transient
    protected static Properties configuration;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIdNotConCont", nullable = false, unique = true)
    private Long id;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name="NIdContacto"),
            @JoinColumn(name="NIdDepartamento")
    })
    private ContactoDepartamento contactoDepartamento;

    @JoinColumn(name = "NIdTipoNotNuevCar", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionNuevoCargo;

    @JoinColumn(name = "NIdTipoNotNuevRec", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionNuevoRecargo;

    @JoinColumn(name = "NIdTipoNotAbonValid", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionAbonoValidado;

    @JoinColumn(name = "NIdTipoNotAbonCanc", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionAbonoCancelado;

    @JoinColumn(name = "NIdTipoNotAprovDesc", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionAprovecharDescuento;

    @JoinColumn(name = "NIdTipoNotEvitRec", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionEvitarRecargo;

    @JoinColumn(name = "NIdTipoNotAviCobranza", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionAvisoCobranza;

    @JoinColumn(name = "NIdTipoNotNuevTema", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionNuevoTema;

    @JoinColumn(name = "NIdTipoNotNuevComentTemPropio", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionNuevoComentarioTemaPropio;

    @JoinColumn(name = "NIdTipoNotNuevComentTemCom", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionNuevoComentarioTemaComentado;

    @JoinColumn(name = "NIdTipoNotNuevComent", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionNuevoComentario;

    @JoinColumn(name = "NIdTipoNotNuevAviso", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionNuevoAviso;

    @JoinColumn(name = "NIdTipoNotReservValid", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionReservacionValidada;

    @JoinColumn(name = "NIdTipoNotIncActu", referencedColumnName = "NIdCatalogo")
    @ManyToOne(fetch = FetchType.EAGER)
    private CatalogoTipoNotificacion tipoNotificacionIncidenciaActualizada;

    public ConfiguracionNotificacionContacto() {
        try {
            if(configuration == null) {
                configuration = PropertiesLoaderUtils
                        .loadAllProperties("configuration.properties");
            }
        } catch (IOException e) {
        }
        this.tipoNotificacionNuevoCargo = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionNuevoRecargo = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionAbonoValidado = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionAbonoCancelado = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionAprovecharDescuento = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionEvitarRecargo = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")));
        this.tipoNotificacionAvisoCobranza = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")));
        this.tipoNotificacionNuevoTema = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionNuevoComentarioTemaPropio = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionNuevoComentarioTemaComentado = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionNuevoComentario = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
        this.tipoNotificacionNuevoAviso = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")));
        this.tipoNotificacionReservacionValidada = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.email")));
        this.tipoNotificacionIncidenciaActualizada = CatalogoFactory.newInstance(CatalogoTipoNotificacion.class,
                Long.valueOf(configuration.getProperty("notificacion.configuracion.tipoEnvio.aplicacion")));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContactoDepartamento getContactoDepartamento() {
        return contactoDepartamento;
    }

    public void setContactoDepartamento(ContactoDepartamento contactoDepartamento) {
        this.contactoDepartamento = contactoDepartamento;
    }

    public CatalogoTipoNotificacion getTipoNotificacionNuevoCargo() {
        return tipoNotificacionNuevoCargo;
    }

    public void setTipoNotificacionNuevoCargo(CatalogoTipoNotificacion tipoNotificacionNuevoCargo) {
        this.tipoNotificacionNuevoCargo = tipoNotificacionNuevoCargo;
    }

    public CatalogoTipoNotificacion getTipoNotificacionNuevoRecargo() {
        return tipoNotificacionNuevoRecargo;
    }

    public void setTipoNotificacionNuevoRecargo(CatalogoTipoNotificacion tipoNotificacionNuevoRecargo) {
        this.tipoNotificacionNuevoRecargo = tipoNotificacionNuevoRecargo;
    }

    public CatalogoTipoNotificacion getTipoNotificacionAbonoValidado() {
        return tipoNotificacionAbonoValidado;
    }

    public void setTipoNotificacionAbonoValidado(CatalogoTipoNotificacion tipoNotificacionAbonoValidado) {
        this.tipoNotificacionAbonoValidado = tipoNotificacionAbonoValidado;
    }

    public CatalogoTipoNotificacion getTipoNotificacionAbonoCancelado() {
        return tipoNotificacionAbonoCancelado;
    }

    public void setTipoNotificacionAbonoCancelado(CatalogoTipoNotificacion tipoNotificacionAbonoCancelado) {
        this.tipoNotificacionAbonoCancelado = tipoNotificacionAbonoCancelado;
    }

    public CatalogoTipoNotificacion getTipoNotificacionAprovecharDescuento() {
        return tipoNotificacionAprovecharDescuento;
    }

    public void setTipoNotificacionAprovecharDescuento(CatalogoTipoNotificacion tipoNotificacionAprovecharDescuento) {
        this.tipoNotificacionAprovecharDescuento = tipoNotificacionAprovecharDescuento;
    }

    public CatalogoTipoNotificacion getTipoNotificacionEvitarRecargo() {
        return tipoNotificacionEvitarRecargo;
    }

    public void setTipoNotificacionEvitarRecargo(CatalogoTipoNotificacion tipoNotificacionEvitarRecargo) {
        this.tipoNotificacionEvitarRecargo = tipoNotificacionEvitarRecargo;
    }

    public CatalogoTipoNotificacion getTipoNotificacionAvisoCobranza() {
        return tipoNotificacionAvisoCobranza;
    }

    public void setTipoNotificacionAvisoCobranza(CatalogoTipoNotificacion tipoNotificacionAvisoCobranza) {
        this.tipoNotificacionAvisoCobranza = tipoNotificacionAvisoCobranza;
    }

    public CatalogoTipoNotificacion getTipoNotificacionNuevoTema() {
        return tipoNotificacionNuevoTema;
    }

    public void setTipoNotificacionNuevoTema(CatalogoTipoNotificacion tipoNotificacionNuevoTema) {
        this.tipoNotificacionNuevoTema = tipoNotificacionNuevoTema;
    }

    public CatalogoTipoNotificacion getTipoNotificacionNuevoComentarioTemaPropio() {
        return tipoNotificacionNuevoComentarioTemaPropio;
    }

    public void setTipoNotificacionNuevoComentarioTemaPropio(CatalogoTipoNotificacion tipoNotificacionNuevoComentarioTemaPropio) {
        this.tipoNotificacionNuevoComentarioTemaPropio = tipoNotificacionNuevoComentarioTemaPropio;
    }

    public CatalogoTipoNotificacion getTipoNotificacionNuevoComentarioTemaComentado() {
        return tipoNotificacionNuevoComentarioTemaComentado;
    }

    public void setTipoNotificacionNuevoComentarioTemaComentado(CatalogoTipoNotificacion tipoNotificacionNuevoComentarioTemaComentado) {
        this.tipoNotificacionNuevoComentarioTemaComentado = tipoNotificacionNuevoComentarioTemaComentado;
    }

    public CatalogoTipoNotificacion getTipoNotificacionNuevoComentario() {
        return tipoNotificacionNuevoComentario;
    }

    public void setTipoNotificacionNuevoComentario(CatalogoTipoNotificacion tipoNotificacionNuevoComentario) {
        this.tipoNotificacionNuevoComentario = tipoNotificacionNuevoComentario;
    }

    public CatalogoTipoNotificacion getTipoNotificacionNuevoAviso() {
        return tipoNotificacionNuevoAviso;
    }

    public void setTipoNotificacionNuevoAviso(CatalogoTipoNotificacion tipoNotificacionNuevoAviso) {
        this.tipoNotificacionNuevoAviso = tipoNotificacionNuevoAviso;
    }

    public CatalogoTipoNotificacion getTipoNotificacionReservacionValidada() {
        return tipoNotificacionReservacionValidada;
    }

    public void setTipoNotificacionReservacionValidada(CatalogoTipoNotificacion tipoNotificacionReservacionValidada) {
        this.tipoNotificacionReservacionValidada = tipoNotificacionReservacionValidada;
    }

    public CatalogoTipoNotificacion getTipoNotificacionIncidenciaActualizada() {
        return tipoNotificacionIncidenciaActualizada;
    }

    public void setTipoNotificacionIncidenciaActualizada(CatalogoTipoNotificacion tipoNotificacionIncidenciaActualizada) {
        this.tipoNotificacionIncidenciaActualizada = tipoNotificacionIncidenciaActualizada;
    }
}
