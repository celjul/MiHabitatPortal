package com.bstmexico.mihabitat.proveedores.gastos.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.movimientos.factory.MovimientoFactory;
import com.bstmexico.mihabitat.movimientos.model.CatalogoTipoMovimiento;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.proveedores.gastos.dao.GastoDao;
import com.bstmexico.mihabitat.proveedores.gastos.model.Detalle;
import com.bstmexico.mihabitat.proveedores.gastos.model.Gasto;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoDetallle;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoGasto;
import com.bstmexico.mihabitat.proveedores.gastos.service.GastoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.*;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @created 2015
 */
@Service
public class GastoServiceImpl implements GastoService {

    private static final Logger LOG = LoggerFactory
            .getLogger(GastoServiceImpl.class);

    @Autowired
    private GastoDao gastoDao;

    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;

    @Autowired
    private Validator validator;

    private static Properties cfg;

    public GastoServiceImpl() {
        try {
            cfg = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
        } catch (IOException e) {
            LOG.error("Error al cargar el archivo de propiedades");
        }
    }

    @Override
    public void guardar(Gasto gasto) {
        try {
            Assert.notNull(gasto);
            Set<ConstraintViolation<Gasto>> violations = validator
                    .validate(gasto);

            if (violations.isEmpty()) {
                gasto.getMovimientoGasto().setDebe(gasto.getDebe());
                gasto.getMovimientoGasto().setGasto(gasto);
                for (Detalle detalle : gasto.getDetalles()) {
                    detalle.getMovimientoDetallle().setDetalle(detalle);
                }
                gastoDao.save(gasto);
            } else {
                String message = "Objeto no valido";
                ApplicationException ex1 = new ServiceException(message);
                LOG.warn(ex1.getMessage() + violations);
                throw ex1;
            }
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public Gasto editar(Long id) {
        try {
            Assert.notNull(id, "SEREX003");
            return gastoDao.get(id);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public void actualizar(Gasto gasto) {
        try {
            Assert.notNull(gasto);
            Assert.notNull(gasto.getId());
            Set<ConstraintViolation<Gasto>> violations = validator
                    .validate(gasto);

            if (violations.isEmpty()) {
                gasto.getMovimientoGasto().setDebe(gasto.getDebe());
                gasto.getMovimientoGasto().setGasto(gasto);
                for (Detalle detalle : gasto.getDetalles()) {
                    detalle.getMovimientoDetallle().setDetalle(detalle);
                }
                gastoDao.update(gasto);
            } else {
                String message = "Objeto no valido";
                ApplicationException ex1 = new ServiceException(message);
                LOG.warn(ex1.getMessage() + violations);
                throw ex1;
            }
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage());
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

	@Transactional
	@Override
	public void cancelar(Gasto gasto) {
		try {
			Assert.notNull(gasto);
			Assert.notNull(gasto.getId());
			gasto = gastoDao.get(gasto.getId());
			gastoDao.delete(gasto);
		} catch (IllegalArgumentException ex) {
			ApplicationException ex1 = new ServiceException(ex.getMessage());
			LOG.warn(ex1.getMessage(), ex);
			throw ex1;
		}
	}

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public List<Gasto> listar(Condominio condominio) {
        try {
            Assert.notNull(condominio);
            Assert.notNull(condominio.getId());
            Map map = new HashMap();
            map.put("condominio", condominio);
            return (List<Gasto>) gastoDao.search(map.entrySet());
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @Override
    public List<Gasto> listarPorFecha(Condominio condominio, Date inicio, Date fin) {
        try {
            Assert.notNull(condominio);
            Assert.notNull(inicio);
            Assert.notNull(fin);
            Assert.notNull(condominio.getId());
            return (List<Gasto>) gastoDao.getPorFecha(condominio, inicio, fin);
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new ServiceException(ex.getMessage(), ex);
            LOG.warn(ex1.getMessage(), ex);
            throw ex1;
        }
    }
}
