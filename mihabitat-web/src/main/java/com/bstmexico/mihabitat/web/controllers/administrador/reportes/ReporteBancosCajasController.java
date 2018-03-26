package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.factory.CuentaFactory;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.MovimientoIngresoNoIdentificado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdi;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCfdiAplicado;
import com.bstmexico.mihabitat.movimientos.model.MovimientoPago;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.otrosingresos.model.MovimientoOtroIngreso;
import com.bstmexico.mihabitat.proveedores.gastos.model.MovimientoGasto;
import com.bstmexico.mihabitat.transferencias.model.MovimientoDeposito;
import com.bstmexico.mihabitat.transferencias.model.MovimientoRetiro;
import com.bstmexico.mihabitat.web.dto.reportes.Cuenta;
import com.bstmexico.mihabitat.web.dto.reportes.Movimiento;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteBancosCajas;
import com.bstmexico.mihabitat.web.util.DateUtils;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * @author Ana Karen Canales Santos
 * @version 1.0
 * @since 2016
 */
@Controller
@RequestMapping(value = "administrador/reportes/bancos-cajas")
public class ReporteBancosCajasController {

    private static final Logger LOG = LoggerFactory
            .getLogger(ReporteBancosCajasController.class);

    @Autowired
    private CondominioService condominioService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private ServletContext context;

    @Autowired
    private ReportUtils reportUtils;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String iniciar(Model model,
                          @RequestParam(required = false) String inicio,
                          @RequestParam(required = false) String fin) {
        model.addAttribute("inicio", mapper.writeValueAsString(inicio));
        model.addAttribute("fin", mapper.writeValueAsString(fin));
        return "administrador/reportes/bancos-cajas";
    }

    @RequestMapping(method = RequestMethod.GET, value = "consulta")
    @ResponseBody
    public ReporteBancosCajas consultar(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
            HttpSession session) {
        return getReporte(
                (Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                        .getValue()), DateUtils.getStartOfDay(inicio),
                DateUtils.getEndOfDay(fin));
    }

    @RequestMapping(method = RequestMethod.GET, value = "imprimir")
    public ResponseEntity<byte[]> imprimir(@RequestParam String formato,
                                           @RequestParam Boolean detalle,
                                           @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
                                           @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
                                           HttpSession session) {

        ReporteBancosCajas reporte = getReporte(
                (Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                        .getValue()), DateUtils.getStartOfDay(inicio),
                DateUtils.getEndOfDay(fin));
        reporte.setCondominio((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));

        byte[] bytes = getBytes(reporte, formato, detalle);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        String filename = "bancos-cajas." + formato;

        reportUtils.setHttpHeaders(headers, formato);
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private byte[] getBytes(ReporteBancosCajas reporte, String formato,
                            Boolean detalle) {
        JRDataSource jrDataSource = null;

        String contexto = context.getRealPath("/");
        String directorio = contexto + "jrxml" + File.separator
                + "bancos-cajas" + File.separator;

        Map map = new HashMap();
        map.put("SUBREPORT_DIR", directorio);
        /*map.put("IMAGEN", contexto + "recursos" + File.separator + "img"
                + File.separator + configurationServiceImpl.getLogo());*/
        map.put("FORMATO", formato);
        map.put("DETALLE", detalle);

        Condominio condominio = condominioService.readConImagen(reporte.getCondominio().getId());

        if (condominio.getLogoCondominio() != null) {
            InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
            try {
                BufferedImage image = ImageIO.read(is);
                map.put("IMAGEN", image);
            } catch (IOException ioe) {
                LOG.error("Error leyendo logo del Condominio, se colocar? el de MH");
                File initialFile = new File(contexto + "recursos" + File.separator + "img"
                        + File.separator + configurationServiceImpl.getLogo());
                try {
                    is = new FileInputStream(initialFile);
                    BufferedImage image = ImageIO.read(is);
                    map.put("IMAGEN", image);
                } catch (IOException ioedos) {
                    LOG.error("No se encontr? el logo de MiHabitat");
                }
            }

        } else {
            LOG.warn("No se encontr? logo del Condominio, se colocar? el de MH");
            File initialFile = new File(contexto + "recursos" + File.separator + "img"
                    + File.separator + configurationServiceImpl.getLogo());
            try {
                InputStream is = new FileInputStream(initialFile);
                BufferedImage image = ImageIO.read(is);
                map.put("IMAGEN", image);
            } catch (IOException ioedos) {
                LOG.error("No se encontr? el logo de MiHabitat");
            }
        }

        Collection collection = new ArrayList();
        collection.add(reporte);
        jrDataSource = new JRBeanCollectionDataSource(collection);

        String sourceFile = formato.equals(reportUtils.PDF) ? directorio
                + "bancos-cajas.jasper" : directorio
                + "bancos-cajas-sin-formato.jasper";
        return reportUtils.export(formato, sourceFile, map, jrDataSource);
    }

    private ReporteBancosCajas getReporte(Condominio condominio, Date inicio,
                                          Date fin) {
        ReporteBancosCajas reporte = new ReporteBancosCajas();
        reporte.setInicio(inicio);
        reporte.setFin(fin);

        com.bstmexico.mihabitat.cuentas.model.Cuenta bancos = cuentaService
                .getCuentas(condominio, configurationServiceImpl.getBancos())
                .iterator().next();

        com.bstmexico.mihabitat.cuentas.model.Cuenta cajas = cuentaService
                .getCuentas(condominio, configurationServiceImpl.getCajas())
                .iterator().next();

        setCuentas(cajas, reporte);
        setCuentas(bancos, reporte);
        return reporte;
    }

    private void setCuentas(
            com.bstmexico.mihabitat.cuentas.model.Cuenta primer,
            ReporteBancosCajas reporte) {
        Cuenta p = getCuenta(primer);
        setMovimientos(p, movimientoService.getMovimientos(primer,
                reporte.getInicio(), reporte.getFin()));
        setInicial(p, reporte.getInicio());
        setRetirosDepositos(p, reporte.getInicio(), reporte.getFin());
        reporte.addCuenta(p);

        if (!CollectionUtils.isEmpty(primer.getCuentasHijas())) {
            for (com.bstmexico.mihabitat.cuentas.model.Cuenta segundo : primer
                    .getCuentasHijas()) {
                Cuenta s = getCuenta(segundo);
                s.setNombre(" - - - " + s.getNombre());
                setMovimientos(
                        s,
                        movimientoService.getMovimientos(segundo,
                                reporte.getInicio(), reporte.getFin()));
                setInicial(s, reporte.getInicio());
                setRetirosDepositos(s, reporte.getInicio(), reporte.getFin());
                reporte.addCuenta(s);

                if (!CollectionUtils.isEmpty(segundo.getCuentasHijas())) {
                    for (com.bstmexico.mihabitat.cuentas.model.Cuenta tercer : segundo
                            .getCuentasHijas()) {
                        Cuenta t = getCuenta(tercer);
                        t.setNombre(" - - - - - - " + t.getNombre());
                        setMovimientos(t, movimientoService.getMovimientos(
                                tercer, reporte.getInicio(), reporte.getFin()));
                        setInicial(t, reporte.getInicio());
                        setRetirosDepositos(t, reporte.getInicio(),
                                reporte.getFin());
                        reporte.addCuenta(t);

                        if (!CollectionUtils.isEmpty(tercer.getCuentasHijas())) {
                            for (com.bstmexico.mihabitat.cuentas.model.Cuenta cuarto : tercer
                                    .getCuentasHijas()) {
                                Cuenta c = getCuenta(cuarto);
                                c.setNombre(" - - - - - - - - - "
                                        + c.getNombre());
                                setMovimientos(c,
                                        movimientoService.getMovimientos(
                                                cuarto, reporte.getInicio(),
                                                reporte.getFin()));
                                setInicial(c, reporte.getInicio());
                                setRetirosDepositos(c, reporte.getInicio(),
                                        reporte.getFin());
                                reporte.addCuenta(c);
                            }
                        }
                    }
                }
            }
        }
    }

    public Cuenta getCuenta(com.bstmexico.mihabitat.cuentas.model.Cuenta c) {
        Cuenta cuenta = new Cuenta();
        cuenta.setId(c.getId());
        cuenta.setNombre(c.getNombre());
        cuenta.setInicial(c.getInicial());
        return cuenta;
    }

    public void setMovimientos(
            Cuenta cuenta,
            Collection<com.bstmexico.mihabitat.movimientos.model.Movimiento> movimientos) {
        if (!CollectionUtils.isEmpty(movimientos)) {
            for (com.bstmexico.mihabitat.movimientos.model.Movimiento m : movimientos) {
                if (m instanceof MovimientoPago) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setFecha(m.getFecha());
                    if (m.getDebe() != null) {
                        movimiento.setDebe(m.getDebe());
                        movimiento.setDescripcion("ABONO CANCELADO");
                    } else if (m.getHaber() != null) {
                        movimiento.setHaber(m.getHaber());
                        movimiento.setDescripcion("ABONO - "
                                + ((MovimientoPago) m).getPago()
                                .getMetodoPago().getDescripcion());
                        movimiento.setNombre(((MovimientoPago) m).getPago()
                                .getContacto().getNombre()
                                + " "
                                + ((MovimientoPago) m).getPago().getContacto()
                                .getApellidoPaterno());
                        movimiento.setReferencia(((MovimientoPago) m).getPago()
                                .getReferencia());
                    }
                    cuenta.addMovimiento(movimiento);
                } else if (m instanceof MovimientoCfdi) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setFecha(m.getFecha());

                    if (m.getDebe() != null) {
                        movimiento.setDebe(m.getDebe());
                        movimiento.setDescripcion("PAGO A CFDI CANCELADO");
                    } else if (m.getHaber() != null) {
                        movimiento.setHaber(m.getHaber());
                        movimiento.setDescripcion("PAGO A CFDI - "
                                + ((MovimientoCfdi) ((MovimientoCfdiAplicado) m).getMovimiento()).getDetalleCfdi().getDescripcion());
                    }
                    cuenta.addMovimiento(movimiento);
                } else if (m instanceof MovimientoGasto) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setFecha(m.getFecha());

                    if (m.getHaber() != null) {
                        movimiento.setHaber(m.getHaber());
                        movimiento.setDescripcion("GASTO CANCELADO");
                    } else if (m.getDebe() != null) {
                        movimiento.setDebe(m.getDebe());
                        movimiento.setDescripcion("GASTO - "
                                + (((MovimientoGasto) m).getGasto().getDetalles().iterator().hasNext() ?
                                ((MovimientoGasto) m).getGasto().getDetalles().iterator().next().getConcepto() :
                                ""));
                        movimiento.setNombre(((MovimientoGasto) m).getGasto().getProveedor().getNombre());
                    }
                    cuenta.addMovimiento(movimiento);
                } else if (m instanceof MovimientoOtroIngreso) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setFecha(m.getFecha());

                    if (m.getDebe() != null) {
                        movimiento.setDebe(m.getDebe());
                        movimiento.setDescripcion("OTROS INGRESOS CANCELADO");
                    } else if (m.getHaber() != null) {
                        movimiento.setHaber(m.getHaber());
                        movimiento.setDescripcion("OTROS INGRESOS - "
                                + (((MovimientoOtroIngreso) m).getOtroIngreso().getConceptos().iterator().hasNext() ?
                                ((MovimientoOtroIngreso) m).getOtroIngreso().getConceptos().iterator().next().getConcepto() :
                                ""));
                    }
                    cuenta.addMovimiento(movimiento);
                } else if (m instanceof MovimientoRetiro) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setFecha(m.getFecha());

                    if (m.getHaber() != null) {
                        movimiento.setHaber(m.getHaber());
                        movimiento.setDescripcion("TRANSFERENCIA CANCELADA");
                    } else if (m.getDebe() != null) {
                        movimiento.setDebe(m.getDebe());
                        movimiento.setDescripcion("TRANSFERENCIA DESDE - " + ((MovimientoRetiro) m).getTransferencia().getDeposito().getCuenta().getNombre());
                        movimiento.setReferencia(((MovimientoRetiro) m).getTransferencia().getComentario());
                    }
                    cuenta.addMovimiento(movimiento);
                } else if (m instanceof MovimientoDeposito) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setFecha(m.getFecha());

                    if (m.getDebe() != null) {
                        movimiento.setDebe(m.getDebe());
                        movimiento.setDescripcion("TRANSFERENCIA CANCELADA");
                    } else if (m.getHaber() != null) {
                        movimiento.setHaber(m.getHaber());
                        movimiento.setDescripcion("TRANSFERENCIA HACIA - " + ((MovimientoDeposito) m).getTransferencia().getRetiro().getCuenta().getNombre());
                        movimiento.setReferencia(((MovimientoDeposito) m).getTransferencia().getComentario());
                    }
                    cuenta.addMovimiento(movimiento);
                } else if (m instanceof MovimientoIngresoNoIdentificado) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setFecha(m.getFecha());

                    if (m.getDebe() != null) {
                        movimiento.setDebe(m.getDebe());
                        movimiento.setDescripcion("INGRESO NO IDENTIFICADO CANCELADO/APLICADO - " +
                                (((MovimientoIngresoNoIdentificado) m).getIngresoNoIdentificado().getComentario()) + " - " +
                                (((MovimientoIngresoNoIdentificado) m).getIngresoNoIdentificado().getAplicadoEn()));
                    } else if (m.getHaber() != null) {
                        movimiento.setHaber(m.getHaber());
                        movimiento.setDescripcion("INGRESO NO IDENTIFICADO - "
                                + (((MovimientoIngresoNoIdentificado) m).getIngresoNoIdentificado().getComentario()));
                    }
                    cuenta.addMovimiento(movimiento);
                }
            }
        }
    }

    private void setInicial(Cuenta cuenta, Date fin) {
        cuenta.setInicial(cuenta.getInicial().add(movimientoService.getHaber(fin,
                CuentaFactory.newInstance(cuenta.getId())))
                .subtract(movimientoService.getDebe(fin,
                        CuentaFactory.newInstance(cuenta.getId()))));
    }

    private void setRetirosDepositos(Cuenta cuenta, Date inicio, Date fin) {
        cuenta.setDebe(movimientoService.getDebe(inicio, fin,
                CuentaFactory.newInstance(cuenta.getId())));
        cuenta.setHaber(movimientoService.getHaber(inicio, fin,
                CuentaFactory.newInstance(cuenta.getId())));
    }
}
