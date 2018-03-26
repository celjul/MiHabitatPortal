package com.bstmexico.mihabitat.web.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.dto.AvisoDeCobro;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
public interface AvisoDeCobroService {
    AvisoDeCobro getAvisoDeCobro(Condominio condominio,
                                 Departamento departamento, Contacto contacto, Date fin);

    byte[] getAvisoDeCobro(AvisoDeCobro avisoDeCobro);

    byte[] getAvisoDeCobroMultiple(Collection<AvisoDeCobro> avisosDeCobro, String formato);

    void sendAvisoDeCobro(Condominio condominio, String mensaje, Long idDepartamento, Long idContacto, Date fin, String... emails);

    void sendAvisoDeCobroMultiple(Condominio condominio, String mensaje, Date fin, Long... ids);

    AvisoDeCobro getAvisoDeCobro(Condominio condominio, Long idDepartamento,
                                 Date fin, Long idContacto);
}
