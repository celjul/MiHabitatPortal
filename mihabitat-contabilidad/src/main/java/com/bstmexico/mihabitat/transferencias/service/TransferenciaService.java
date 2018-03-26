package com.bstmexico.mihabitat.transferencias.service;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.transferencias.model.Transferencia;

import java.util.Collection;

/**
 * Created by BST Mexico 01 on 06/01/2016.
 */
public interface TransferenciaService {

    public Collection <Transferencia> search(Condominio condominio);
    public void save(Transferencia transferencia);
    public Transferencia get(Long id);



}
