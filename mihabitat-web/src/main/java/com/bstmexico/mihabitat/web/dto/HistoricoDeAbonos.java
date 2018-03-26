package com.bstmexico.mihabitat.web.dto;

import com.bstmexico.mihabitat.pagos.model.Pago;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
public class HistoricoDeAbonos {

    private Date fechaInicio;

    private Date fechaFin;

    private Collection<Pago> abonos;

    @JsonIgnore
    private static Properties properties;

    public HistoricoDeAbonos() {
        try {
            properties = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
        } catch (IOException e) {
        }
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Collection<Pago> getCargos() {
        return abonos;
    }

    public void setCargos(Collection<Pago> cargos) {
        this.abonos = cargos;
    }



}
