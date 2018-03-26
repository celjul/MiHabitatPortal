package com.bstmexico.mihabitat.web.controllers.administrador.reportes;

import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.proveedores.service.ProveedorService;
import com.bstmexico.mihabitat.web.dto.reportes.ReporteGastos;
import com.bstmexico.mihabitat.web.service.InformeDeGastosService;
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
@RequestMapping(value = "administrador/reportes/reporteegresos")
public class ReporteGastosController {

    private static final Logger LOG = LoggerFactory.getLogger(ReporteGastosController.class);

    @Autowired
    private CondominioService condominioService;

    @Autowired
    private InformeDeGastosService informeDeGastosService;

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
        return "administrador/reportes/reporte-egresos";
    }

    @RequestMapping(method = RequestMethod.GET, value = "consulta")
    @ResponseBody
    public ReporteGastos consultar(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
            HttpSession session) {
        return getReporte((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()), inicio, fin);
    }

    @RequestMapping(method = RequestMethod.GET, value = "imprimir")
    public ResponseEntity<byte[]> imprimir(@RequestParam String formato,
                                           @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date inicio,
                                           @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
                                           HttpSession session) {

        ReporteGastos reporte = getReporte((Condominio) session.getAttribute
                        (SessionEnum.CONDOMINIO.getValue()), DateUtils.getStartOfDay(inicio),
                DateUtils.getEndOfDay(fin));
        reporte.setCondominio((Condominio) session.getAttribute(SessionEnum.CONDOMINIO.getValue()));
        byte[] bytes = getBytes(reporte, formato);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        String filename = "gastos." + formato;
        reportUtils.setHttpHeaders(headers, formato);
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }
    /**
     *
     * */


    @SuppressWarnings({"rawtypes", "unchecked"})
    private byte[] getBytes(ReporteGastos reporte, String formato) {

        JRDataSource jrDataSource = null;
        String contexto = context.getRealPath("/");
        String directorio = contexto + "jrxml" + File.separator + "reporteegresos" + File.separator;
        Map map = new HashMap();
        map.put("SUBREPORT_DIR", directorio);
        map.put("FORMATO", formato);
        Condominio condominio = condominioService.readConImagen(reporte.getCondominio().getId());
        if (condominio.getLogoCondominio() != null) {
            InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
            try {
                BufferedImage image = ImageIO.read(is);
                map.put("IMAGEN", image);
            } catch (IOException ioe) {
                LOG.error("Error leyendo el logo del Condominio, se colocará el logo de MH");
                File initialFile = new File(contexto + "recursos" + File.separator + "img"
                        + File.separator + configurationServiceImpl.getLogo());
                try {
                    is = new FileInputStream(initialFile);
                    BufferedImage image = ImageIO.read(is);
                    map.put("IMAGEN", image);
                } catch (IOException ioedos) {
                    LOG.error("No se encontró el logo de MiHabitat");
                }
            }
        } else {
            LOG.warn("No se encontró el logo del Condominio, se colocará el de MH");
            File initialFile = new File(contexto + "recursos" + File.separator + "img"
                    + File.separator + configurationServiceImpl.getLogo());
            try {
                InputStream is = new FileInputStream(initialFile);
                BufferedImage image = ImageIO.read(is);
                map.put("IMAGEN", image);
            } catch (IOException ioedos) {
                LOG.error("No se encontró el logo de MiHabitat");
            }
        }
        Collection collection = new ArrayList();
        collection.add(reporte);
        jrDataSource = new JRBeanCollectionDataSource(collection);
            String sourceFile = formato.equals(reportUtils.PDF) ? directorio + "gastos.jasper" : directorio + "gastos-sin-formato.jasper";

        return reportUtils.export(formato, sourceFile, map, jrDataSource);
    }

    private ReporteGastos getReporte(Condominio condominio, Date inicio, Date fin) {

        ReporteGastos reporte = informeDeGastosService.getReporteGastos(condominio, inicio, fin);

        return reporte;
    }
}
