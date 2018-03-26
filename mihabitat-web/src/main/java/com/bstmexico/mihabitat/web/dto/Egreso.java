package com.bstmexico.mihabitat.web.dto;

import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoDetallle;
import com.bstmexico.mihabitat.proveedores.model.Proveedor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

/**
 * @author Ana Karen Canales Santos
 * @version 1.0
 * @since 2016
 */
public class Egreso {

    private Date fecha;

    private Proveedor proveedor;

    private String conceptos;

    private BigDecimal montoEgreso;

    private CatalogoMetodoPago metodoPago;

    @JsonIgnore
    private static Properties properties;

    public Egreso() {
        montoEgreso = BigDecimal.ZERO;
        try {
            properties = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
        } catch (IOException e) {
        }
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public String getConceptos() {
        return conceptos;
    }

    public void setConceptos(String conceptos) {
        this.conceptos = conceptos;
    }

    public BigDecimal getMontoEgreso() {
        return montoEgreso;
    }

    public void setMontoEgreso(BigDecimal montoGasto) {
        this.montoEgreso = montoGasto;
    }

    public CatalogoMetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(CatalogoMetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        Egreso.properties = properties;
    }

}
