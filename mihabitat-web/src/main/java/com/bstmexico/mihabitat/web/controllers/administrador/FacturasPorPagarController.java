package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.archivos.factory.ArchivoFactory;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.proveedores.factory.CfdiFactory;
import com.bstmexico.mihabitat.proveedores.factory.ProveedorFactory;
import com.bstmexico.mihabitat.proveedores.model.*;
import com.bstmexico.mihabitat.proveedores.service.FacturaPorPagarService;
import com.bstmexico.mihabitat.proveedores.service.ProveedorService;
import com.bstmexico.mihabitat.web.exceptions.GenericController;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import mx.gob.sat.cfd.x32.ComprobanteDocument;
import mx.gob.sat.timbreFiscalDigital.TimbreFiscalDigitalDocument;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author JPC
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/facturasporpagar")
public class FacturasPorPagarController extends GenericController {

    protected static final Logger LOG = LoggerFactory
            .getLogger(FacturasPorPagarController.class);

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private FacturaPorPagarService facturaporpagarService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private CatalogoService catalogoService;

    public static final String ARCHIVOS = "archivos";

    public static final String FOLIOS = "folios";


    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "nuevo")
    public String nuevo(Model model, HttpSession session) {
        Map map = new HashMap();

        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        map.put("esFacturable", true);
        model.addAttribute("cuenta", mapper.writeValueAsString(cuentaService.getCuentas(
                (Condominio) session.getAttribute("condominio"), configurationServiceImpl.getCuentaEgresos())));
        model.addAttribute("proveedores", mapper.writeValueAsString(proveedorService.search(map)));
        model.addAttribute("metodosPago", mapper.writeValueAsString(catalogoService
                .getList(CatalogoMetodoPago.class)));

        session.removeAttribute(FOLIOS);
        return "administrador/facturasporpagar/nuevo";
    }


    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public
    @ResponseBody
    Cfdi guardar(@RequestBody Cfdi facturaxp) {
        facturaporpagarService.save(facturaxp);
        return facturaxp;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String lista(Model model, HttpSession session) {
        Map map = new HashMap();

        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("facturasxp", mapper.writeValueAsString(facturaporpagarService.search(map)));
        return "administrador/facturasporpagar/lista";
    }


    @RequestMapping(method = RequestMethod.POST, value = "removerCfdi")
    public
    @ResponseBody
    Boolean eliminarCfdi(Model model, HttpSession session, @RequestParam String uuid) {
        List<String> folios = (List<String>) session.getAttribute(FOLIOS);

        Iterator<String> itr = folios.iterator();
        while (itr.hasNext()) {

            String element = itr.next();
            if (element.equals(uuid)) {
                itr.remove();
            }
        }
        session.setAttribute(FOLIOS, folios);
        return Boolean.TRUE;
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "actualizar/{facturasxp}")
    public String editar(@PathVariable Long facturasxp, Model model,
                         HttpSession session) {
        Map map = new HashMap();

        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("cuenta", mapper.writeValueAsString(cuentaService.getCuentas((Condominio) session.getAttribute("condominio"),
                configurationServiceImpl.getCuentaEgresos())));
        model.addAttribute("proveedores", mapper.writeValueAsString(proveedorService.search(map)));
        model.addAttribute("metodosPago", mapper.writeValueAsString(catalogoService
                .getList(CatalogoMetodoPago.class)));
        Cfdi facturaPorPagar = facturaporpagarService.get(facturasxp);
        //model.addAttribute("facturasxp", mapper.writeValueAsString(facturaporpagarService.get(facturasxp)));
        model.addAttribute("facturasxp", mapper.writeValueAsString(facturaPorPagar));

        return "administrador/facturasporpagar/editar";
    }


    @RequestMapping(method = RequestMethod.POST, value = "registraProveedor")
    public
    @ResponseBody
    Proveedor registraProveedor(@RequestBody Proveedor proveedor) {

        proveedorService.save(proveedor);
        return proveedor;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(method = RequestMethod.GET, value = "cargar")
    public String cargar(Model model, HttpSession session, @RequestParam(required = false, value = "_") String aux) {
        LOG.warn("Entre al metodo cargar.");
        Map map = new HashMap();
        map.put("condominio", (Condominio) session.getAttribute("condominio"));
        //model.addAttribute("facturasxp", mapper.writeValueAsString(facturaporpagarService.search(map)));
        model.addAttribute("giros", mapper.writeValueAsString(catalogoService.getList(CatalogoGiro.class)));
        model.addAttribute("cuenta", mapper.writeValueAsString(cuentaService.getCuentas(
                (Condominio) session.getAttribute("condominio"), configurationServiceImpl.getCuentaEgresos())));

        if (aux == null) {
            session.removeAttribute(FOLIOS);
        }
        return "administrador/facturasporpagar/cargar";
    }


    @RequestMapping(method = RequestMethod.POST, value = "guardarcfdi")
    public
    @ResponseBody
    Collection<Cfdi> guardarcfdi(@RequestBody Collection<Cfdi> cfdi) {

        facturaporpagarService.create(cfdi);
        return cfdi;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/existe")
    public
    @ResponseBody
    Cfdi existe(@RequestParam String uuid, HttpSession session) {
        Cfdi cfdi = CfdiFactory.newInstance();
        cfdi.setCondominio((Condominio) session.getAttribute("condominio"));
        cfdi.setUuid(uuid);
        return facturaporpagarService.existeCfdi(cfdi);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(method = RequestMethod.POST, value = "fileupload")
    public
    @ResponseBody
    Cfdi processUpload(@RequestParam MultipartFile file,
                       HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        InputStream inputStream = null;
        Cfdi cfdi = null;

        ArchivoCfdi archivoCfdi = ArchivoFactory.newInstance(ArchivoCfdi.class);
        archivoCfdi.setNombre(file.getOriginalFilename());
        archivoCfdi.setTamano(file.getSize() / 1024 + " Kb");
        archivoCfdi.setTipo(file.getContentType());

        try {
            archivoCfdi.setBytes(file.getBytes());
        } catch (IOException ex) {
            ApplicationException ex1 = new ServiceException("SEREX005", ex);
            LOG.error(ex.getMessage(), ex);
            throw ex1;
        }
        session.setAttribute(ARCHIVOS, archivoCfdi);

        try {
            inputStream = file.getInputStream();
            ComprobanteDocument documento =
                    ComprobanteDocument.Factory.parse(inputStream);

            Collection validationErrors = new ArrayList();
            XmlOptions validateOptions = new XmlOptions();
            validateOptions.setErrorListener(validationErrors);


            if (!documento.validate(validateOptions)) {
                throw new ServiceException("CFDI04");
            }

            Proveedor prov = ProveedorFactory.newInstance();
            prov.setRfc(documento.getComprobante().getEmisor().getRfc());
            prov.setCondominio((Condominio) session.getAttribute("condominio"));

            if (proveedorService.existeProveedor(prov) != null) {
                LOG.warn(">Razon social: " + proveedorService.existeProveedor(prov).getRazonSocial());
            }

            cfdi = procesar(documento);

            List<String> folios = (List<String>) session.getAttribute(FOLIOS);
            if (folios == null) {
                folios = new ArrayList<String>();
            }

            LOG.warn(folios.toString());
            if (folios.contains(cfdi.getUuid())) {
                String message = "CFDI03";
                LOG.warn(message);
                throw new ServiceException(message);
            }
            folios.add(cfdi.getUuid());
            session.setAttribute(FOLIOS, folios);

            cfdi.setProveedor(proveedorService.existeProveedor(prov));
            cfdi.setArchivo(archivoCfdi);

            Cfdi c = CfdiFactory.newInstance();
            c.setCondominio((Condominio) session.getAttribute("condominio"));
            c.setUuid(cfdi.getUuid());
            LOG.warn("UUID: " + facturaporpagarService.existeCfdi(c).getUuid());

            if (facturaporpagarService.existeCfdi(c).getUuid() != null) {
                String message = "CFDI01";
                LOG.warn(message);
                //response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                throw new ServiceException(message);
            }

        } catch (IOException | XmlException ex) {
            String message = "CFDI02";
            LOG.warn(message);
            throw new ServiceException(message);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                String message = "CFDI05";
                throw new ServiceException(message, ex);
            }
        }
        return cfdi;
    }


    private Cfdi procesar(ComprobanteDocument documento) {
        Cfdi comp = new Cfdi();

        LOG.warn("Version CFDI: " + documento.getComprobante().getVersion());
        if (!documento.getComprobante().getVersion().equals("3.2")) {
            comp.setUuid("err");
        }

        comp.setFechaEmision(documento.getComprobante().getFecha().getTime());
        //comp.setFechaVencimiento(null);
        comp.setRfc(documento.getComprobante().getEmisor().getRfc());
        comp.setEmisor(documento.getComprobante().getEmisor().getNombre());
        comp.setImpuestoRetenido(documento.getComprobante().getImpuestos().getTotalImpuestosRetenidos());
        comp.setImpuestoTrasladado(documento.getComprobante().getImpuestos().getTotalImpuestosTrasladados());

        if (documento.getComprobante().getImpuestos().getTotalImpuestosRetenidos() == null) {
            documento.getComprobante().getImpuestos().setTotalImpuestosRetenidos(BigDecimal.ZERO);

        }
        if (documento.getComprobante().getImpuestos().getTotalImpuestosTrasladados() == null) {
            documento.getComprobante().getImpuestos().setTotalImpuestosTrasladados(BigDecimal.ZERO);
        }
        comp.setTotal(documento.getComprobante().getSubTotal().subtract(
                        documento.getComprobante().getImpuestos().getTotalImpuestosRetenidos()).add(
                        documento.getComprobante().getImpuestos().getTotalImpuestosTrasladados())
        );

        comp.setFormadepago(documento.getComprobante().getFormaDePago() != null ? documento.getComprobante().getFormaDePago() : "");
        comp.setMetododepago(documento.getComprobante().getMetodoDePago() != null ? documento.getComprobante().getMetodoDePago() : "");
        comp.setCondiciondepago(documento.getComprobante().getCondicionesDePago() != null ? documento.getComprobante().getCondicionesDePago() : "");

        if (comp.getImpuestoRetenido() == null) {
            comp.setImpuestoRetenido(BigDecimal.ZERO);
        }

        if (comp.getImpuestoTrasladado() == null) {
            comp.setImpuestoTrasladado(BigDecimal.ZERO);
        }

        try {
            TimbreFiscalDigitalDocument timbre = TimbreFiscalDigitalDocument.Factory.parse(documento.getComprobante().getComplemento().newInputStream());
            comp.setUuid(timbre.getTimbreFiscalDigital().getUUID());

        } catch (IOException | XmlException ex) {

        }
        comp.setTipo(documento.getComprobante().getTipoDeComprobante().toString());

        comp.setConceptos(getConceptos(documento.getComprobante().getConceptos()));
        return comp;
    }


    private Collection<DetalleFactura> getConceptos(ComprobanteDocument.Comprobante.Conceptos conceptos) {
        Collection<DetalleFactura> lista = new ArrayList<DetalleFactura>();

        for (mx.gob.sat.cfd.x32.ComprobanteDocument.Comprobante.Conceptos.Concepto concepto : conceptos.getConceptoList()) {
            DetalleFactura detalleFactura = new DetalleFactura();

            detalleFactura.setCantidad(concepto.getCantidad().doubleValue());
            if (concepto.getNoIdentificacion() != null) {
                detalleFactura.setCodigo(concepto.getNoIdentificacion());
            }
            detalleFactura.setDescripcion(concepto.getDescripcion());
            detalleFactura.setPrecioUnitario(concepto.getValorUnitario());
            detalleFactura.setUnidadDeMedida(concepto.getUnidad());
            detalleFactura.setImporte(concepto.getImporte());

            lista.add(detalleFactura);
        }
        return lista;
    }

}
