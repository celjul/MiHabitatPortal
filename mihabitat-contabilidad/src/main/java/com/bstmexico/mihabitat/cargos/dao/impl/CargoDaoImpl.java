package com.bstmexico.mihabitat.cargos.dao.impl;

import com.bstmexico.mihabitat.cargos.dao.CargoDao;
import com.bstmexico.mihabitat.cargos.model.*;
import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.dao.CondominioDao;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.dao.DepartamentoDao;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.factory.PagoDepartamentoFactory;
import com.bstmexico.mihabitat.pagos.factory.PagoFactory;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.IntegerType;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bstmexico.mihabitat.cargos.dao.CargoDao;
import com.bstmexico.mihabitat.cargos.model.Cargo;
import com.bstmexico.mihabitat.cargos.model.CargoAgrupador;
import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.model.CargoRecurrente;
import com.bstmexico.mihabitat.cargos.model.TipoConsumo;
import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.dao.DataAccessException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargo;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.pagos.model.Pago;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Repository
public class CargoDaoImpl extends GenericDaoImpl<Cargo, Long> implements
        CargoDao {

    @Autowired
    private DepartamentoDao departamentoDao;

    @Autowired
    private CondominioDao condominioDao;

    @Transactional(readOnly = true)
    @Override
    public Cargo getConDepartamento(Long id, Class<? extends Cargo> cargo, Boolean desbordar) {
        try {
            Cargo c = (Cargo) this.sessionFactory.getCurrentSession().get(
                    cargo, id);
            if (c instanceof CargoAgrupador) {
                ((CargoAgrupador) c).getCargos();
                for (CargoDepartamento cd : ((CargoAgrupador) c).getCargos()) {
                    cd.setAgrupador(null);
                    ((CargoDepartamento) cd).getMovimientos();
                    if (!CollectionUtils.isEmpty(((CargoDepartamento) cd)
                            .getMovimientos())) {
                        for (MovimientoCargo movimiento : ((CargoDepartamento) cd)
                                .getMovimientos()) {
                            if (!desbordar) {
                                movimiento.setCargo(null);
                            }
                            movimiento.getAplicados();
                            if (!desbordar) {
                                if (!CollectionUtils.isEmpty(movimiento
                                        .getAplicados())) {
                                    for (MovimientoCargoAplicado aplicado : movimiento
                                            .getAplicados()) {
                                        aplicado.setMovimientoCargo(null);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (c instanceof CargoDepartamento) {
                ((CargoDepartamento) c).getMovimientos();
                if (!CollectionUtils.isEmpty(((CargoDepartamento) c)
                        .getMovimientos())) {
                    for (MovimientoCargo movimiento : ((CargoDepartamento) c)
                            .getMovimientos()) {
                        if (!desbordar) {
                            movimiento.setCargo(null);
                        }
                        movimiento.getAplicados();
                        if (!desbordar) {
                            if (!CollectionUtils.isEmpty(movimiento
                                    .getAplicados())) {
                                for (MovimientoCargoAplicado aplicado : movimiento
                                        .getAplicados()) {
                                    aplicado.setMovimientoCargo(null);
                                }
                            }
                        }
                    }
                }
                ((CargoDepartamento) c).setDepartamento(departamentoDao.get(((CargoDepartamento) c).getDepartamento().getId()));
                c.setCondominio(condominioDao.get(c.getCondominio().getId()));
                for (ContactoDepartamento contactoDepartamento : ((CargoDepartamento) c).getDepartamento().getContactos()) {
                    contactoDepartamento.getContacto().getEmails().size();
                }
            }
            return c;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new DataAccessException("DAO001", ex,
                    getType().getSimpleName());
            LOG.error(ex1.getMessage(), ex);
            throw ex1;
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Cargo get(Long id, Class<? extends Cargo> cargo, Boolean desbordar) {
        try {
            Cargo c = (Cargo) this.sessionFactory.getCurrentSession().get(
                    cargo, id);
            Hibernate.initialize(c);
            if (c instanceof HibernateProxy) {
                c = (Cargo) ((HibernateProxy) c).getHibernateLazyInitializer()
                        .getImplementation();
            }
            if (c instanceof CargoAgrupador) {
                ((CargoAgrupador) c).getCargos();
                for (CargoDepartamento cd : ((CargoAgrupador) c).getCargos()) {
                    cd.setAgrupador(null);
                    ((CargoDepartamento) cd).getMovimientos();
                    if (!CollectionUtils.isEmpty(((CargoDepartamento) cd)
                            .getMovimientos())) {
                        for (MovimientoCargo movimiento : ((CargoDepartamento) cd)
                                .getMovimientos()) {
                            if (!desbordar) {
                                movimiento.setCargo(null);
                            }
                            if (!CollectionUtils.isEmpty(movimiento
                                    .getAplicados())) {
                                for (MovimientoCargoAplicado aplicado : movimiento
                                        .getAplicados()) {
                                    if (!desbordar) {
                                        aplicado.setMovimientoCargo(null);
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (c instanceof CargoDepartamento) {
                c.getCondominio().getId();
                ((CargoDepartamento) c).getMovimientos();
                if (!CollectionUtils.isEmpty(((CargoDepartamento) c)
                        .getMovimientos())) {
                    for (MovimientoCargo movimiento : ((CargoDepartamento) c)
                            .getMovimientos()) {
                        if (!desbordar) {
                            movimiento.setCargo(null);
                        }
                        if (!CollectionUtils.isEmpty(movimiento.getAplicados())) {
                            for (MovimientoCargoAplicado aplicado : movimiento
                                    .getAplicados()) {
                                if (aplicado.getPago() != null) {
                                    aplicado.getPago().getId();
                                    aplicado.getPago().getContacto().getId();
                                }
                                if (!desbordar) {
                                    aplicado.setMovimientoCargo(null);
                                }
                            }
                        }
                    }
                }
            }
            return c;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new DataAccessException("DAO001", ex,
                    getType().getSimpleName());
            LOG.error(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Transactional(readOnly = true)
    @Override
    public Collection<Cargo> search(Set<Entry> set, Class<? extends Cargo> cargo, Boolean desbordar) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(cargo);
            for (Map.Entry entry : set) {
                if (entry.getValue() != null) {
                    if (entry.getValue() instanceof String) {
                        criteria.add(Restrictions.like((String) entry.getKey(),
                                "%" + entry.getValue() + "%").ignoreCase());
                    } else {
                        criteria.add(Restrictions.eq((String) entry.getKey(),
                                entry.getValue()));
                    }
                } else {
                    criteria.add(Restrictions.isNull((String) entry.getKey()));
                }
            }
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Collection<Cargo> cargos = criteria.list();
            if (!CollectionUtils.isEmpty(cargos)) {
                Cargo cargoAux = cargos.iterator().next();
                if (cargoAux instanceof CargoDepartamento) {
                    for (Cargo c : cargos) {
                        ((CargoDepartamento) c).getMovimientos();
                        ((CargoDepartamento) c).getDepartamento().getGrupos().size();
                        if (!CollectionUtils.isEmpty(((CargoDepartamento) c)
                                .getMovimientos())) {
                            for (MovimientoCargo movimiento : ((CargoDepartamento) c)
                                    .getMovimientos()) {
                                if (!desbordar) {
                                    movimiento.setCargo(null);
                                }
                                movimiento.getAplicados();
                                if (!CollectionUtils.isEmpty(movimiento
                                        .getAplicados())) {
                                    for (MovimientoCargoAplicado aplicado : movimiento
                                            .getAplicados()) {
                                        if (!desbordar) {
                                            aplicado.setMovimientoCargo(null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return cargos;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Transactional(readOnly = true)
    @Override
    public Collection<CargoDepartamento> getList(Condominio condominio, Date inicio, Date fin) {
        try {

            Boolean desbordar = Boolean.FALSE;

            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(CargoDepartamento.class);

            criteria.add(Restrictions.between("fecha", inicio, fin));
            criteria.add(Restrictions.eq("condominio", condominio));

            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Collection<CargoDepartamento> cargos = criteria.list();

            if (!CollectionUtils.isEmpty(cargos)) {
                Cargo cargoAux = cargos.iterator().next();
                if (cargoAux instanceof CargoDepartamento) {
                    for (Cargo c : cargos) {
                        ((CargoDepartamento) c).getMovimientos();
                        Hibernate.initialize(((CargoDepartamento) c).getDepartamento().getGrupos());
                        if (!CollectionUtils.isEmpty(((CargoDepartamento) c)
                                .getMovimientos())) {
                            for (MovimientoCargo movimiento : ((CargoDepartamento) c)
                                    .getMovimientos()) {
                                if (!desbordar) {
                                    movimiento.setCargo(null);
                                }
                                movimiento.getAplicados();
                                if (!CollectionUtils.isEmpty(movimiento
                                        .getAplicados())) {
                                    for (MovimientoCargoAplicado aplicado : movimiento
                                            .getAplicados()) {
                                        if (!desbordar) {
                                            aplicado.setMovimientoCargo(null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return cargos;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public CargoAgrupador getAnterior(Condominio condominio, TipoConsumo consumo) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(CargoAgrupador.class);
            criteria.add(Restrictions.eq("condominio", condominio));
            criteria.createAlias("consumo", "c");
            criteria.add(Restrictions.eq("c.tipo", consumo));
            criteria.addOrder(Order.desc("fecha"));
            criteria.setMaxResults(1);
            CargoAgrupador cargo = (CargoAgrupador) criteria.uniqueResult();
            if (cargo != null) {
                cargo.getCargos();
                for (CargoDepartamento cd : cargo.getCargos()) {
                    cd.setAgrupador(null);
                }
            }
            return cargo;
        } catch (IllegalArgumentException ex) {
            ApplicationException ex1 = new DataAccessException("DAO001", ex,
                    getType().getSimpleName());
            LOG.error(ex1.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<CargoDepartamento> getCargos(
            Collection<Departamento> departamentos, Date fecha) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(CargoDepartamento.class);
            criteria.add(Restrictions.in("departamento", departamentos));
            if (fecha != null) {
                criteria.add(Restrictions.le("fecha", fecha));
            }
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Collection<CargoDepartamento> cargos = criteria.list();
            if (!CollectionUtils.isEmpty(cargos)) {
                for (CargoDepartamento cargo : cargos) {
                    cargo.getMovimientos();
                    if (!CollectionUtils.isEmpty(cargo.getMovimientos())) {
                        for (MovimientoCargo movimiento : cargo
                                .getMovimientos()) {
                            movimiento.setCargo(null);
                            movimiento.getAplicados();
                            if (!CollectionUtils.isEmpty(movimiento
                                    .getAplicados())) {
                                for (MovimientoCargoAplicado aplicado : movimiento
                                        .getAplicados()) {
                                    Hibernate.initialize(aplicado.getPago());
                                    aplicado.setMovimientoCargo(null);
                                }
                            }
                        }
                    }
                }
            }
            return cargos;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<CargoDepartamento> getCargos(Pago pago) {
        Collection<CargoDepartamento> cds = new ArrayList<>();
        for(PagoDepartamento pd : pago.getPagosDepartamento()){
            cds.addAll(getCargos(pd));
        }
        return cds;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<CargoDepartamento> getCargos(PagoDepartamento pago) {
        try {
            Collection<Long> ids = new ArrayList<Long>();
            for (MovimientoCargoAplicado aplicado : pago.getMovimientos()) {
                ids.add(aplicado.getId());
            }
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(CargoDepartamento.class);
            criteria.createAlias("movimientos", "movs");
            criteria.createAlias("movs.aplicados", "apl");
            criteria.add(Restrictions.in("apl.id", ids));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Collection<CargoDepartamento> cargos = criteria.list();
            if (!CollectionUtils.isEmpty(cargos)) {
                for (CargoDepartamento cargo : cargos) {
                    cargo.getMovimientos();
                    if (!CollectionUtils.isEmpty(cargo.getMovimientos())) {
                        for (MovimientoCargo movimiento : cargo
                                .getMovimientos()) {
                            movimiento.setCargo(null);
                            movimiento.getAplicados();
                            if (!CollectionUtils.isEmpty(movimiento
                                    .getAplicados())) {
                                for (MovimientoCargoAplicado aplicado : movimiento
                                        .getAplicados()) {
                                    if (aplicado.getPago() != null) {
                                        aplicado.setPago(PagoFactory.newInstance(aplicado.getPago().getId()));
                                    }
                                    if (aplicado.getPagoDepartamento() != null) {
                                        aplicado.setPagoDepartamento(PagoDepartamentoFactory.newInstance(aplicado.getPagoDepartamento().getId()));
                                    }
                                    aplicado.setMovimientoCargo(null);
                                }
                            }
                        }
                    }
                }
            }
            return cargos;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<CargoDepartamento> getCargosPorFecha(Date fecha) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(CargoDepartamento.class);
            LocalDate fechaAux = new LocalDate(fecha);

            criteria.add(Restrictions.eq("fecha", fechaAux.toDateTimeAtStartOfDay().toDate()));

            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Collection<CargoDepartamento> cargos = criteria.list();
            if (!CollectionUtils.isEmpty(cargos)) {
                for (CargoDepartamento cargo : cargos) {
                    cargo.getMovimientos().size();
                    if (!CollectionUtils.isEmpty(cargo.getMovimientos())) {
                        for (MovimientoCargo movimiento : cargo
                                .getMovimientos()) {
                            movimiento.setCargo(null);
                        }
                    }
                    cargo.getDepartamento().getId();
                    cargo.getCondominio().getId();
                }
            }
            return cargos;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<CargoDepartamento> getCargosPendientes(Departamento departamento, Date fin) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(CargoDepartamento.class);

            criteria.add(Restrictions.eq("departamento", departamento));
            criteria.add(Restrictions.le("fecha", fin));

            Collection<CargoDepartamento> cargosPendientes = new ArrayList<>();

            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Collection<CargoDepartamento> cargos = criteria.list();
            if (!CollectionUtils.isEmpty(cargos)) {
                for (CargoDepartamento cargo : cargos) {

					/*if(cargo.getSaldoPendiente().compareTo(BigDecimal.ZERO) > 0) {*/
                    cargo.getMovimientos().size();
                    if (!CollectionUtils.isEmpty(cargo.getMovimientos())) {
                        Collection<MovimientoCargo> movimientos = new ArrayList<>();
                        for (MovimientoCargo movimiento : cargo
                                .getMovimientos()) {
                            movimiento.setCargo(null);
                            movimiento.getAplicados();
                            if (!CollectionUtils.isEmpty(movimiento
                                    .getAplicados())) {
                                Collection<MovimientoCargoAplicado> movimientosAplicados = new ArrayList<>();
                                for (MovimientoCargoAplicado aplicado : movimiento
                                        .getAplicados()) {
                                    aplicado.setMovimientoCargo(null);
                                    if (aplicado.getFecha().before(fin) || (aplicado.getFecha().getTime() == fin.getTime())) {
                                        movimientosAplicados.add(aplicado);
                                    }
                                }
                                movimiento.setAplicados(movimientosAplicados);
                            }
                            if (movimiento.getFecha().before(fin) || (movimiento.getFecha().getTime() == fin.getTime())) {
                                movimientos.add(movimiento);
                            }
                        }
                        cargo.setMovimientos(movimientos);
                    }
                    if (cargo.getSaldoPendiente().compareTo(BigDecimal.ZERO) > 0) {
                            /*cargo.getDepartamento().getId();
                            cargo.getCondominio().getId();*/
                        cargosPendientes.add(cargo);
                    }
					/*}*/
                }
            }
            return cargosPendientes;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

    /*
     * M�todo para obtener los cargos recurrentes aplicables al d�a
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<CargoRecurrente> getCargosRecurrentesActivosPorFecha(
            Date fecha) {
        try {
            Criteria criteria = sessionFactory.getCurrentSession()
                    .createCriteria(CargoRecurrente.class);
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(fecha);
            Byte[] meses = new Byte[]{(byte) cal.get(GregorianCalendar.MONTH)};

            criteria.createAlias("meses", "m");
            criteria.add(Restrictions.in("m.elements", meses));

            if (cal.get(GregorianCalendar.DAY_OF_MONTH) == 1) {
                criteria.add(Restrictions
                        .disjunction()
                        .add(Restrictions.eq("primerDiaMes", true))
                        .add(Restrictions.eq("dia", ((Integer) cal
                                .get(GregorianCalendar.DAY_OF_MONTH))
                                .byteValue())));
            } else if (cal.get(Calendar.DAY_OF_MONTH) == cal
                    .getActualMaximum(Calendar.DAY_OF_MONTH)) {
                criteria.add(Restrictions
                        .disjunction()
                        .add(Restrictions.eq("ultimoDiaMes", true))
                        .add(Restrictions.eq("dia", ((Integer) cal
                                .get(GregorianCalendar.DAY_OF_MONTH))
                                .byteValue())));
            } else {
                criteria.add(Restrictions.eq("dia", ((Integer) cal
                        .get(GregorianCalendar.DAY_OF_MONTH)).byteValue()));
            }

            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            Collection<CargoRecurrente> cargos = criteria.list();

            for (CargoRecurrente cr : cargos) {
                cr.getCondominio().getId();
            }

            return cargos;
        } catch (HibernateException ex) {
            ApplicationException ex1 = new DataAccessException("DAO009", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
    }

    /*
     * M�todo para obtener los cargos vigentes con Recargos
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public Collection<CargoDepartamento> getCargosRecargosPorAplicar(Date fecha) {
        try {

            Properties configuration = PropertiesLoaderUtils
                    .loadAllProperties("configuration.properties");
            Long idTipoUnico = Long.parseLong(configuration
                    .getProperty("tipoInteres.unico"));

            LocalDate fechaAux = new LocalDate(fecha);
            Calendar calInicio = GregorianCalendar.getInstance();
            calInicio.setTime(fechaAux.toDateTimeAtStartOfDay().toDate());
            calInicio.add(GregorianCalendar.DAY_OF_MONTH, -1);
            calInicio.set(GregorianCalendar.DAY_OF_MONTH, 1);

            Calendar calAyer = GregorianCalendar.getInstance();
            calAyer.setTime(fechaAux.toDateTimeAtStartOfDay().toDate());
            calAyer.add(GregorianCalendar.DAY_OF_MONTH, -1);

            Calendar calFin = GregorianCalendar.getInstance();
            calFin.setTime(fechaAux.toDateTimeAtStartOfDay().toDate());
            calFin.add(GregorianCalendar.DAY_OF_MONTH, -1);
            calFin.set(GregorianCalendar.DAY_OF_MONTH,
                    calFin.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));


            try {
                String query = "SELECT cd FROM CargoDepartamento cd join cd.recargo r JOIN r.tipoInteres t " +
                        " where r.id is not null AND " +
                        " cd.fecha <= :fecha AND DAY(r.fecha) = :dia AND " +
                        " r.fecha <= :fecha AND " +
                        "( " +
                        " t.id <> :idTipoUnico OR " +
                        " ( t.id = :idTipoUnico AND " +
                        "  r.fecha between :fechaInicio and :fechaFin )" +
                        " )";

                Collection<CargoDepartamento> cargos = (Collection<CargoDepartamento>) sessionFactory.getCurrentSession()
                        .createQuery(query).setParameter("fecha", fechaAux
                                .toDateTimeAtStartOfDay().toDate())
                        .setParameter("dia", calAyer.get(GregorianCalendar.DAY_OF_MONTH))
                        .setParameter("idTipoUnico", idTipoUnico).setParameter("fechaInicio", calInicio.getTime())
                        .setParameter("fechaFin", calFin.getTime())
                        .list();

                for (CargoDepartamento cargoDepartamento : cargos) {
                    cargoDepartamento.getMovimientos().size();
                    for (MovimientoCargo movimiento : cargoDepartamento
                            .getMovimientos()) {
                        movimiento.getAplicados().size();
                    }
                }

                return cargos;
            } catch (IllegalArgumentException ex) {
                ApplicationException ex1 = new DataAccessException("DAO004", ex,
                        getType().getSimpleName());
                LOG.error(ex.getMessage(), ex);
                throw ex1;
            } catch (HibernateException ex) {
                ApplicationException ex1 = new DataAccessException("DAO009", ex);
                LOG.error(ex.getMessage(), ex);
                throw ex1;
            }
        } catch (IOException ioe) {
            ApplicationException ex1 = new DataAccessException("DAO009", ioe);
            LOG.error(ioe.getMessage(), ioe);
            throw ex1;
        }
    }
}
