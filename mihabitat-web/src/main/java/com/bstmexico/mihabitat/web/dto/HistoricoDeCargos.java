package com.bstmexico.mihabitat.web.dto;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
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
public class HistoricoDeCargos {

    private Date fechaInicio;

    private Date fechaFin;

    private BigDecimal saldoDeudor;

    private Collection<CargoDepartamento> cargos;

    @JsonIgnore
    private static Properties properties;

    public HistoricoDeCargos() {
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

    public Collection<CargoDepartamento> getCargos() {
        return cargos;
    }

    public void setCargos(Collection<CargoDepartamento> cargos) {
        this.cargos = cargos;
    }

    public BigDecimal getSaldoDeudor() {
        saldoDeudor = BigDecimal.ZERO;
        if(getCargos() != null && !CollectionUtils.isEmpty(getCargos())) {
            for(CargoDepartamento cargoDepartamento : getCargos()) {
                saldoDeudor = saldoDeudor.add(cargoDepartamento.getSaldoPendiente());
            }
        }
        return saldoDeudor;
    }

    public void setSaldoDeudor(BigDecimal saldoDeudor) {
        this.saldoDeudor = saldoDeudor;
    }
}
