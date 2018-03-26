package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.proveedores.gastos.model.Detalle;
import com.bstmexico.mihabitat.proveedores.gastos.model.Gasto;
import com.bstmexico.mihabitat.proveedores.gastos.service.GastoService;
import com.bstmexico.mihabitat.proveedores.model.PagoProveedor;
import com.bstmexico.mihabitat.proveedores.service.PagoProveedorService;
import com.bstmexico.mihabitat.web.dto.Egreso;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteGastos;
import com.bstmexico.mihabitat.web.service.InformeDeGastosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.Collection;
import java.util.Date;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Service
public class InformeDeGastosServiceImpl implements InformeDeGastosService {

    private static final Logger LOG = LoggerFactory
            .getLogger(InformeDeGastosServiceImpl.class);

    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;

    @Autowired
    @Qualifier("javamailservice")
    private EmailingService emailingService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    @Qualifier("cargoserviceproxy")
    private CargoService cargoService;

    @Autowired
    private GastoService gastoService;

    @Autowired
    private PagoProveedorService pagoProveedorService;

    @Autowired
    private ServletContext context;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Override
    public ReporteGastos getReporteGastos(Condominio condominio, Date inicio, Date fin) {

        ReporteGastos reporteGastos = new ReporteGastos();
        reporteGastos.setCondominio(condominio);
        reporteGastos.setInicio(inicio);
        reporteGastos.setFin(fin);

        Collection<Gasto> gastos = gastoService.listarPorFecha(condominio, inicio, fin);
        Collection<PagoProveedor> pagosProveedores = pagoProveedorService.listarPorFecha(condominio, inicio, fin);
        for (Gasto gasto : gastos) {
            Egreso egreso = new Egreso();
            egreso.setFecha(gasto.getFecha());
            egreso.setProveedor(gasto.getProveedor());
            for (Detalle detalle : gasto.getDetalles()) {
                egreso.setConceptos((egreso.getConceptos() == null) ? detalle.getConcepto() : (" || " + egreso.getConceptos() + detalle.getConcepto() + " || "));
            }
            egreso.setMetodoPago(gasto.getMetodoPago());
            egreso.setMontoEgreso(gasto.getMovimientoGasto().getDebe());
            reporteGastos.getEgresos().add(egreso);
        }
        for (PagoProveedor pagoProveedor : pagosProveedores) {
            Egreso egreso = new Egreso();
            egreso.setFecha(pagoProveedor.getFechaPago());
            egreso.setProveedor(pagoProveedor.getProveedor());
            //Todo Queda pendiente la descripcion del campo para las facturas
            egreso.setConceptos((pagoProveedor.getComentario() == null) ? "Sin Comentarios" :"Pago de Factura " + pagoProveedor.getComentario());
            egreso.setMetodoPago(pagoProveedor.getMetodoPago());
            egreso.setMontoEgreso(pagoProveedor.getTotal());
            reporteGastos.getEgresos().add(egreso);
        }
        return reporteGastos;
    }

    @Override
    public byte[] getReporteGastos(ReporteGastos reporteGastos) {
        return new byte[0];
    }
}
