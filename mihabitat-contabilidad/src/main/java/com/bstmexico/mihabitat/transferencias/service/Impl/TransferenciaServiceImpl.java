package com.bstmexico.mihabitat.transferencias.service.Impl;

import com.bstmexico.mihabitat.cargos.dao.CargoDao;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.otrosingresos.service.impl.OtroIngresoServiceImpl;
import com.bstmexico.mihabitat.transferencias.dao.Impl.TransferenciaDaoImpl;
import com.bstmexico.mihabitat.transferencias.dao.TransferenciaDao;
import com.bstmexico.mihabitat.transferencias.model.MovimientoDeposito;
import com.bstmexico.mihabitat.transferencias.model.MovimientoRetiro;
import com.bstmexico.mihabitat.transferencias.model.Transferencia;
import com.bstmexico.mihabitat.transferencias.service.TransferenciaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Eduardo Prieto Islas
 * @version 1.0
 * @created 2015
 */
@Service
public class TransferenciaServiceImpl implements TransferenciaService{

    private static final Logger LOG = LoggerFactory
            .getLogger(OtroIngresoServiceImpl.class);

    @Autowired
    private TransferenciaDao transferenciaDao;


    @Autowired
    private Validator validator;

    @Override
    public Collection<Transferencia> search(Condominio condominio) {

        try {
            Assert.notNull(condominio, "SEREX001");
            Assert.notNull(condominio.getId(), "SEREX001");
            Map map = new HashMap();
            map.put("condominio",condominio);
            return transferenciaDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }

    }

    @Override
    public void save(Transferencia transferencia) {
        try {
            Assert.notNull(transferencia, "SEREX001");
            Set<ConstraintViolation<Transferencia>> violations = validator
                    .validate(transferencia);
            if (violations.isEmpty()) {
                transferenciaDao.save(tratarTransferencia(transferencia));
            } else {
                String message = "SEREX002";
                ApplicationException ex1 = new ServiceException(message,
                        violations);
                LOG.warn(ex1.getMessage() + violations, violations);
                throw ex1;
            }

        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }

    }

    @Override
    public Transferencia get(Long id) {

        try {
            Assert.notNull(id, "SEREX003");
            return transferenciaDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }


    }

    private Transferencia tratarTransferencia(Transferencia transferencia) {
        MovimientoRetiro retiro = transferencia.getRetiro();
        retiro.setDebe(transferencia.getMonto());
        retiro.setTransferencia(transferencia);
        MovimientoDeposito deposito = transferencia.getDeposito();
        deposito.setHaber(transferencia.getMonto());
        deposito.setTransferencia(transferencia);

        return transferencia;
    }
}
