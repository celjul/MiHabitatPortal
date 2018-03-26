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
public class AvisoDeCobro {

    private Departamento departamento;

    private Contacto contacto;

    private Date fecha;

    private BigDecimal saldoFavor;

    private BigDecimal saldoDeudor;

    private Collection<CargoDepartamento> cargos;

    @JsonIgnore
    private static Properties properties;

    public AvisoDeCobro() {
        try {
            properties = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
        } catch (IOException e) {
        }
    }

    public BigDecimal getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(BigDecimal saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
