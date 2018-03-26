package com.bstmexico.mihabitat.web.dto.reportes;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.web.dto.Egreso;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author Ana Karen Canales Santos
 * @version 1.0
 * @since 2016
 */
public class ReporteGastos {

    private Condominio condominio;

    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
    private Date inicio;

    @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Mexico_City")
    private Date fin;

    private Collection<Egreso> egresos;

    public ReporteGastos() {
        super();
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Collection<Egreso> getEgresos() {
        if(this.egresos == null){
            this.egresos = new ArrayList<Egreso>();
        }
        return egresos;
    }

    public void setEgresos(Collection<Egreso> egresos) {
        this.egresos = egresos;
    }
}
