package com.bstmexico.mihabitat.web.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteGastos;

import java.util.Date;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
public interface InformeDeGastosService {

    ReporteGastos getReporteGastos(Condominio condominio, Date inicio, Date fin);

    byte[] getReporteGastos(ReporteGastos reporteGastos);

}