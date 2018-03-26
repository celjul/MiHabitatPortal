package com.bstmexico.mihabitat.web.controllers.administrador;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.cargos.model.CargoDepartamento;
import com.bstmexico.mihabitat.cargos.model.CatalogoCargo;
import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.exceptions.ApplicationException;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.factory.ContactoFactory;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.cuentas.service.CuentaService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.ingresosnoidentificados.model.IngresoNoIdentificado;
import com.bstmexico.mihabitat.ingresosnoidentificados.service.IngresoNoIdentificadoService;
import com.bstmexico.mihabitat.movimientos.model.MovimientoCargoAplicado;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.pagos.factory.PagoFactory;
import com.bstmexico.mihabitat.pagos.model.CatalogoMetodoPago;
import com.bstmexico.mihabitat.pagos.model.Pago;
import com.bstmexico.mihabitat.pagos.model.PagoDepartamento;
import com.bstmexico.mihabitat.pagos.service.PagoService;
import com.bstmexico.mihabitat.pagostdc.feenicia.service.FeeniciaService;
import com.bstmexico.mihabitat.web.controllers.comunes.ComprobanteController;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.dto.FileMetaData;
import com.bstmexico.mihabitat.web.dto.PagoDto;
import com.bstmexico.mihabitat.web.exceptions.GenericController;
import com.bstmexico.mihabitat.web.service.AvisoDeCobroService;
import com.bstmexico.mihabitat.web.service.PaymentService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/pagos")
public class PagoController extends GenericController {

    @Autowired
    @Qualifier("pagoserviceproxy")
    private PagoService pagoService;

    @Autowired
    private IngresoNoIdentificadoService ingresoNoIdentificadoService;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    @Qualifier("cargoserviceproxy")
    private CargoService cargoService;

    @Autowired
    private AvisoDeCobroService avisoDeCobroService;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    @Qualifier("movimientoserviceproxy")
    private MovimientoService movimientoService;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
	private FeeniciaService feeniciaService;

    private static final Logger LOG = LoggerFactory
            .getLogger(PagoController.class);

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "nuevo")
    public String nuevo(Model model, HttpSession session) {
        session.removeAttribute(ComprobanteController.COMPROBANTE);
        Map map = new HashMap();
        map.put("condominio", (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
        model.addAttribute("contactos",
                mapper.writeValueAsString(contactoService.search(map)));
        model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
                .getCuentas((Condominio) session
                                .getAttribute(SessionEnum.CONDOMINIO.getValue()),
                        configurationServiceImpl.getCuentaCajasBancos())));
        model.addAttribute("cuentasIngresos", mapper.writeValueAsString(cuentaService
                .getCuentas((Condominio) session
                                .getAttribute(SessionEnum.CONDOMINIO.getValue()),
                        configurationServiceImpl.getCuentaIngresos())));
        model.addAttribute("metodosPago", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoMetodoPago.class)));
        model.addAttribute("tiposCargo", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoCargo.class)));
        return "administrador/pagos/nuevo";
    }

    @RequestMapping(method = RequestMethod.POST, value = "cargos")
    public
    @ResponseBody
    Collection<CargoDepartamento> getCargos(
            @RequestParam Long contacto,
            /*@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fecha,*/
            HttpSession session) {
        return cargoService.getCargos(ContactoFactory.newInstance(contacto), null);
        /*Collection<CargoDepartamento> cargos = new ArrayList<>();
		for(ContactoDepartamento contactoDepartamento : contactoService.getDepartamentos(contacto)){
			AvisoDeCobro avisoDeCobro = avisoDeCobroService.getAvisoDeCobro((Condominio) session
					.getAttribute(SessionEnum.CONDOMINIO.getValue()), contactoDepartamento.getDepartamento(), contactoDepartamento.getContacto(), fecha);
			cargos.addAll(avisoDeCobro.getCargos());
		}
		return cargos;*/
    }

    @RequestMapping(method = RequestMethod.POST, value = "saldo-favor")
    public
    @ResponseBody
    Map<Long, BigDecimal> getSaldoFavor(@RequestParam Long contacto) {
        Map<Long, BigDecimal> map = new HashMap<>();
        for(ContactoDepartamento cd : contactoService.getDepartamentos(contacto)) {
            map.put(cd.getDepartamento().getId(), movimientoService.getSaldoFavorPorDepartamento(cd.getDepartamento()));
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public Pago guardar(@RequestBody PagoDto pagoDto, HttpSession session) throws Exception {
    	Pago pago = pagoDto.getPago();
		com.bstmexico.mihabitat.pagostdc.feenicia.dto.Pago pagoTarjeta = pagoDto.getPagoTarjeta();
    	
    	Boolean aprobado = null;
    	if (pago.getMetodoPago().getId().equals(configurationServiceImpl.getTarjeta().getId())) {
    		aprobado = Boolean.TRUE;
    		Condominio condominio = (Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue());
    		feeniciaService.pay(pagoTarjeta, condominio);
    	} else {
    		aprobado = pago.getEstatus().iterator().next().getEstatus()
             .getId().equals(configurationServiceImpl.getPagoAprobado()) ? Boolean.TRUE
             : Boolean.FALSE;
    	}
    	
        if (session.getAttribute(ComprobanteController.COMPROBANTE) != null) {
            FileMetaData file = (FileMetaData) session
                    .getAttribute(ComprobanteController.COMPROBANTE);
            pago.setComprobante(file.getBytes());
        }

        pagoService.save(pago, aprobado);
		/*notificationService.enviarNotificacionPagoPendiente(pago);*/

		/*paymentService.sendEmail(pago);*/
        //***pago.setMovimientos(null);
        pago.setPagosDepartamento(null);
        return pago;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "guardarConIngresoNoIdentificado")
    public Pago guardarConIngresoNoIdentificado(@RequestBody Pago pago, HttpSession session) {
        if (session.getAttribute(ComprobanteController.COMPROBANTE) != null) {
            FileMetaData file = (FileMetaData) session
                    .getAttribute(ComprobanteController.COMPROBANTE);
            pago.setComprobante(file.getBytes());
        }
        if (session.getAttribute("ind") != null) {
            IngresoNoIdentificado ingresoNoIdentificado = (IngresoNoIdentificado) session.getAttribute("ind");
            Boolean aprobado = pago.getEstatus().iterator().next().getEstatus()
                    .getId().equals(configurationServiceImpl.getPagoAprobado()) ? Boolean.TRUE
                    : Boolean.FALSE;
            ingresoNoIdentificadoService.asignar(ingresoNoIdentificado, pago, aprobado);
            session.removeAttribute("ind");
            //***pago.setMovimientos(null);
            pago.setPagosDepartamento(null);
            return pago;
			/*pagoService.save(pago, aprobado);*/
        } else {
            throw new ApplicationException("No se cuenta con un Ingreso No Identificado");
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String lista(Model model, HttpSession session) {
		/*Map map = new HashMap();
		map.put("condominio", (Condominio) session.getAttribute("condominio"));
		Collection<Pago> pagos = pagoService.search(map);
		for(Pago pago : pagos) {
			for(MovimientoCargoAplicado movimientoCargoAplicado :  pago.getMovimientos()) {
				movimientoCargoAplicado.setPago(null);
			}
		}
		model.addAttribute("pagos",
				mapper.writeValueAsString(pagos));*/
        return "administrador/pagos/lista";
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping(method = RequestMethod.GET, value = "listaConFechas")
    @ResponseBody
    public Collection<Pago> listaConFechas(Model model, HttpSession session,
                                           @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
                                           @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin) {

        Collection<Pago> pagos = pagoService.getList((Condominio) session.getAttribute("condominio"),
                inicio, fin);
        for (Pago pago : pagos) {
            /***for (MovimientoCargoAplicado movimientoCargoAplicado : pago.getMovimientos()) {
                movimientoCargoAplicado.setPago(null);
            }*/
            for (PagoDepartamento pd : pago.getPagosDepartamento()) {
                pd.setPagoCondomino(null);
            }
        }

        return pagos;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(method = RequestMethod.GET, value = "/actualizar/{idPago}")
    public String editar(@PathVariable Long idPago, Model model,
                         HttpSession session) {
        Map map = new HashMap();
        map.put("condominio", (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
        model.addAttribute("contactos",
                mapper.writeValueAsString(contactoService.search(map)));
        model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
                .getCuentas((Condominio) session
                                .getAttribute(SessionEnum.CONDOMINIO.getValue()),
                        configurationServiceImpl.getCuentaCajasBancos())));
        model.addAttribute("metodosPago", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoMetodoPago.class)));

        Pago pago = pagoService.get(idPago);
		/*Archivo archivo = ArchivoFactory.newInstance(ArchivoAdjuntoPost.class);*/
        FileMetaData fileMeta = new FileMetaData();
        if (pago.getComprobante() != null) {
            try {
                String extension = Magic.getMagicMatch(pago.getComprobante()).getExtension();

                fileMeta.setName("comprobante_" + String.valueOf(idPago) + "."
                        + extension);
                fileMeta.setSize(pago.getComprobante().length / 1024 + "");
                if (extension.equals("pdf")) {
                    fileMeta.setType("application/pdf");
                } else if (extension.equals("doc")) {
                    fileMeta.setType("application/ms-word");
                } else if (extension.equals("docx")) {
                    fileMeta.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                } else if (extension.equals("jpg")) {
                    fileMeta.setType("image/jpeg");
                } else if (extension.equals("gif")) {
                    fileMeta.setType("image/gif");
                } else if (extension.equals("png")) {
                    fileMeta.setType("image/png");
                }
                fileMeta.setBytes(pago.getComprobante());
            } catch (MagicParseException | MagicMatchNotFoundException
                    | MagicException e) {
                LOG.warn("Error al procesar el archivo " + e);
            }
            session.setAttribute(ComprobanteController.COMPROBANTE, fileMeta);
        }
        model.addAttribute("archivo", mapper.writeValueAsString(fileMeta));
        /***for (MovimientoCargoAplicado aplicado : pago.getMovimientos()) {
            aplicado.setPago(null);
            aplicado.getMovimientoCargo().setAplicados(null);
            aplicado.getMovimientoCargo().getCargo().setMovimientos(null);
        }*/
        for (PagoDepartamento pd : pago.getPagosDepartamento()) {
            for (MovimientoCargoAplicado aplicado : pd.getMovimientos()) {
                aplicado.setPago(null);
                aplicado.getMovimientoCargo().setAplicados(null);
                aplicado.getMovimientoCargo().getCargo().setMovimientos(null);
            }
        }
        Collection<CargoDepartamento> cargos = cargoService.getCargos(pago);
		/*for(CargoDepartamento cd : cargos) {
			for (MovimientoCargo mc : cd.getMovimientos()) {
				for(MovimientoCargoAplicado mca : mc.getAplicados()) {
					mca.setPago(PagoFactory.newInstance(pago.getId()));
				}
			}
		}*/
        model.addAttribute("pago", mapper.writeValueAsString(pago));
        model.addAttribute("cargos", mapper.writeValueAsString(cargos));
        return "administrador/pagos/editar";

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/aprobar")
    public Pago aprobar(@RequestBody Pago pago, HttpSession session) {
        if (session.getAttribute(ComprobanteController.COMPROBANTE) != null) {
            FileMetaData file = (FileMetaData) session
                    .getAttribute(ComprobanteController.COMPROBANTE);
            pago.setComprobante(file.getBytes());
        } else {
            pago.setComprobante(null);
        }
        pagoService.aprobar(pago);
        /***pago.setMovimientos(null);*/
        pago.setPagosDepartamento(null);
        return pago;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/rechazar")
    public Pago rechazar(@RequestBody Pago pago, HttpSession session) {
        if (session.getAttribute(ComprobanteController.COMPROBANTE) != null) {
            FileMetaData file = (FileMetaData) session
                    .getAttribute(ComprobanteController.COMPROBANTE);
            pago.setComprobante(file.getBytes());
        }
        pagoService.rechazar(pago);
		/*notificationService.enviarNotificacionPagoPendiente(pago);*/
		/*paymentService.sendEmail(pago);*/
        /***pago.setMovimientos(null);*/
        pago.setPagosDepartamento(null);
        return pago;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/cancelar")
    public Pago cancelar(@RequestBody Pago pago, HttpSession session) {

        pagoService.cancelar(pago);
		/*notificationService.enviarNotificacionPagoPendiente(pago);*/
		/*paymentService.sendEmail(pago);*/
        /***pago.setMovimientos(null);*/
        pago.setPagosDepartamento(null);
        return pago;
    }

    @RequestMapping(value = "/comprobante/{idPago}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getComprobante(@PathVariable Long idPago) {
        byte[] bytes = pagoService.get(idPago).getComprobante();
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        String filename = null;

        try {
            String extension = Magic.getMagicMatch(bytes).getExtension();
            filename = "comprobante_" + String.valueOf(idPago) + "."
                    + extension;
            if (extension.equals("pdf")) {
                headers.setContentType(MediaType
                        .parseMediaType("application/pdf"));
            } else if (extension.equals("doc")) {
                headers.setContentType(MediaType
                        .parseMediaType("application/ms-word"));
            } else if (extension.equals("docx")) {
                headers.setContentType(MediaType
                        .parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
            } else if (extension.equals("jpg")) {
                headers.setContentType(MediaType.parseMediaType("image/jpeg"));
            } else if (extension.equals("gif")) {
                headers.setContentType(MediaType.parseMediaType("image/gif"));
            } else if (extension.equals("png")) {
                headers.setContentType(MediaType.parseMediaType("image/png"));
            }
        } catch (MagicParseException | MagicMatchNotFoundException
                | MagicException e) {
            LOG.warn("Error al procesar el archivo " + e);
        }
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/recibo/{idPago}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getRecibo(@PathVariable Long idPago) {

        byte[] bytes = paymentService
                .getRecibo(PagoFactory.newInstance(idPago));
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        String filename = "recibo_" + idPago + ".pdf";
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "enviar")
    public Boolean enviar(@RequestParam(value = "emails[]") String[] emails,
                          @RequestParam String mensaje, @RequestParam Long idPago, HttpSession session) {

        /*ByteArrayResource resource = new ByteArrayResource(
                paymentService.getRecibo(PagoFactory.newInstance(idPago)));
        paymentService.sendRecibo(new Adjunto("recibo_" + idPago + ".pdf",
                resource), mensaje, ((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue())), emails);*/

        paymentService.sendRecibo(((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue())), mensaje, idPago, emails);
        return Boolean.TRUE;
    }


    @RequestMapping(method = RequestMethod.GET, value = "asignarIngresoNoIdentificados/{idContacto}")
    public String asignarAbono(@PathVariable Long idContacto, Model model, HttpSession session) {

        session.removeAttribute(ComprobanteController.COMPROBANTE);

        Map map = new HashMap();
        map.put("condominio", (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
        model.addAttribute("contactos",
                mapper.writeValueAsString(contactoService.search(map)));
        model.addAttribute("cuentas", mapper.writeValueAsString(cuentaService
                .getCuentas((Condominio) session
                                .getAttribute(SessionEnum.CONDOMINIO.getValue()),
                        configurationServiceImpl.getCuentaCajasBancos())));
        model.addAttribute("cuentasIngresos", mapper.writeValueAsString(cuentaService
                .getCuentas((Condominio) session
                                .getAttribute(SessionEnum.CONDOMINIO.getValue()),
                        configurationServiceImpl.getCuentaIngresos())));
        model.addAttribute("metodosPago", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoMetodoPago.class)));
        model.addAttribute("tiposCargo", mapper
                .writeValueAsString(catalogoService
                        .getList(CatalogoCargo.class)));

        model.addAttribute("ingresoNoIdentificado", mapper
                .writeValueAsString(session.getAttribute("ind")));
        model.addAttribute("contacto", mapper
                .writeValueAsString(contactoService.get(idContacto)));

        return "administrador/pagos/nuevo";
    }
}
