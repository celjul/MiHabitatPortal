package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.cargos.service.CargoService;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.condominios.service.CondominioService;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.factory.ContactoFactory;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.emailing.service.EmailingService;
import com.bstmexico.mihabitat.movimientos.service.MovimientoService;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.dto.AvisoDeCobro;
import com.bstmexico.mihabitat.web.service.AvisoDeCobroService;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * @author Zoe Jonatan Tapia Hernandez
 * @version 1.0
 * @since 2016
 */
@Service
public class AvisoDeCobroServiceImpl implements AvisoDeCobroService{

    private static final Logger LOG = LoggerFactory
            .getLogger(EstadoCuentaServiceImpl.class);

    @Autowired
    private ContactoService contactoService;

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
    private CondominioService condominioService;

    @Autowired
    private ServletContext context;

    @Autowired
    private ConfigurationServiceImpl configurationServiceImpl;

    @Autowired
    private ReportUtils reportUtils;

    @Override
    public AvisoDeCobro getAvisoDeCobro(Condominio condominio,
                                        Departamento departamento, Contacto contacto, Date fin) {
        //TODO: Al parecer el contacto ya no tiene caso, ya que el saldo ya no está casado con el mismo, remover
        AvisoDeCobro avisoDeCobro = new AvisoDeCobro();
        avisoDeCobro.setCargos(cargoService.getCargosPendientes(departamento, fin));
        departamento = departamentoService.get(departamento.getId());
        if (contacto != null && contacto.getId() != null) {
            avisoDeCobro.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(departamento, null, fin));
        } else {
            contacto = departamento.obtenerPrincipal().getContacto();
            avisoDeCobro.setSaldoFavor(movimientoService.getSaldoFavorPorDepartamento(departamento, null, fin));
        }
        avisoDeCobro.setFecha(fin);
        avisoDeCobro.setDepartamento(departamento);
        avisoDeCobro.setContacto(contacto);
        avisoDeCobro.getDepartamento().setCondominio(condominio);
        return avisoDeCobro;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public byte[] getAvisoDeCobro(AvisoDeCobro avisoDeCobro) {
        JRDataSource jrDataSource = null;

        String contexto = context.getRealPath("/");
        String directorio = contexto + "jrxml" + File.separator
                + "aviso-cobro" + File.separator;

        try {
            Map map = new HashMap();
            map.put("SUBREPORT_DIR", directorio);

            Condominio condominio = condominioService.readConImagen(avisoDeCobro.getDepartamento().
                    getCondominio().getId());

            if(condominio.getLogoCondominio() != null) {
                InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
                try{
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
                    }  catch (IOException ioedos) {
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
                }  catch (IOException ioedos) {
                    LOG.error("No se encontró el logo de MiHabitat");
                }
            }

            condominio.setLogoCondominio(null);
            avisoDeCobro.getDepartamento().setCondominio(condominio);

            Collection collection = new ArrayList();
            collection.add(avisoDeCobro);
            jrDataSource = new JRBeanCollectionDataSource(collection);
            return JasperRunManager.runReportToPdf(
                    directorio + "aviso.jasper", map, jrDataSource);
        } catch (JRException ex) {
            String message = "Error al generar el estado de cuenta.";
            LOG.error(message, ex);
            throw new ServiceException(message, ex);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public byte[] getAvisoDeCobroMultiple(Collection<AvisoDeCobro> avisosDeCobro, String formato) {
        JRDataSource jrDataSource = null;

        String contexto = context.getRealPath("/");
        String directorio = contexto + "jrxml" + File.separator
                + "aviso-cobro" + File.separator;

        byte[] bites = null;

        try {
            Map map = new HashMap();
            map.put("FORMATO", formato);
            map.put("SUBREPORT_DIR", directorio);
            String sourceFile = null;
            ByteArrayOutputStream streamXlsx = null;

            if(!CollectionUtils.isEmpty(avisosDeCobro)) {
                Condominio condominio = condominioService.readConImagen(avisosDeCobro.iterator().next().getDepartamento().
                        getCondominio().getId());
                if(condominio.getLogoCondominio() != null) {
                    InputStream is = new ByteArrayInputStream(condominio.getLogoCondominio().getBytes());
                    try{
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
                        }  catch (IOException ioedos) {
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
                    }  catch (IOException ioedos) {
                        LOG.error("No se encontr? el logo de MiHabitat");
                    }
                }

                condominio.setLogoCondominio(null);
                sourceFile = directorio + "aviso.jasper";

                List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
                for(AvisoDeCobro avisoDeCobro : avisosDeCobro){
                    avisoDeCobro.getDepartamento().setCondominio(condominio);
                    Collection collection = new ArrayList();
                    collection.add(avisoDeCobro);
                    jrDataSource = new JRBeanCollectionDataSource(collection);

                    JasperPrint jasperPrint1 = JasperFillManager.fillReport(sourceFile, map, jrDataSource);
                    jasperPrintList.add(jasperPrint1);
                }

                if (formato.equals("pdf")) {
                    JRPdfExporter exporter = new JRPdfExporter();
                    exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList)); //Set as export input my list with JasperPrint s

                    ByteArrayOutputStream b = new ByteArrayOutputStream();
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(b)); //or any other out streaam
                    exporter.exportReport();
                    bites = b.toByteArray();
                } else if (formato.equals("xlsx")){
                    JRXlsxExporter exporterXlsx = new JRXlsxExporter();
                    exporterXlsx.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));

                    SimpleXlsxReportConfiguration configurationXlsx = new SimpleXlsxReportConfiguration();
                    configurationXlsx.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
                    configurationXlsx.setRemoveEmptySpaceBetweenColumns(Boolean.TRUE);
                    configurationXlsx.setDetectCellType(Boolean.TRUE);
                    exporterXlsx.setConfiguration(configurationXlsx);

                    streamXlsx = new ByteArrayOutputStream();
                    SimpleOutputStreamExporterOutput outputXlsx = new SimpleOutputStreamExporterOutput(streamXlsx);
                    exporterXlsx.setExporterOutput(outputXlsx);

                    exporterXlsx.exportReport();
                    outputXlsx.close();
                }
            }
            return formato.equals("pdf") ? bites: streamXlsx.toByteArray();
        } catch (JRException ex) {
            String message = "Error al generar el estado de cuenta.";
            LOG.error(message, ex);
            throw new ServiceException(message, ex);
        }
    }

    @Override
    public void sendAvisoDeCobro(Condominio condominio, String mensaje, Long idDepartamento, Long idContacto, Date fin,
                                 String... emails) {

        Departamento dpto = departamentoService.get(idDepartamento);

        final Map<String, String> emailsMap = new HashMap<>();
        for(String e : emails){
            emailsMap.put(e, dpto.getNombre());
        }

        if (!CollectionUtils.isEmpty(emailsMap)) {
            final Collection<Adjunto> adjuntos = new ArrayList<>();
            AvisoDeCobro ac = this.getAvisoDeCobro(condominio,
                    idDepartamento, fin, idContacto);
            Adjunto adj = new Adjunto("Aviso_de_Cobro_" + dpto.getNombre() + ".pdf", new ByteArrayResource(
                    this.getAvisoDeCobro(ac)));
            adjuntos.add(adj);

            final Map mapVelocity = new HashMap();
            mapVelocity.put("mensaje", mensaje.replaceAll("(\r\n|\n)", "<br />"));
            mapVelocity.put("host", configurationServiceImpl.getHost());

            final String asunto = "Aviso de Cobro " + dpto.getNombre();
            final String nombreCondominio = condominio.getNombre();

            new Thread(new Runnable() {
                @SuppressWarnings("unchecked")
                @Override
                public void run() {
                    emailingService.sendEmail(emailsMap, asunto,
                            "aviso-cobro.html", mapVelocity, adjuntos, nombreCondominio);
                }
            }).start();
        }
    }

    @Override
    public void sendAvisoDeCobroMultiple(Condominio condominio, String mensaje, Date fin, Long... ids) {

        for (Long idDepartamento : ids) {
            Departamento departamento = departamentoService.get(idDepartamento);
            Contacto contacto = contactoService.get(departamento.obtenerPrincipal().getContacto().getId());

            final Map<String, String> emailsMap = new HashMap<>();
            if (contacto != null && !CollectionUtils.isEmpty(contacto
                    .getEmails())) {
                for (Email email : contacto.getEmails()) {
                    emailsMap.put(email.getDireccion(), contacto.getNombreCompleto());
                }
            }
            if(!CollectionUtils.isEmpty(emailsMap)){
                final Collection<Adjunto> adjuntos = new ArrayList<>();

                AvisoDeCobro avisoDeCobro = this.getAvisoDeCobro(condominio, idDepartamento,
                        fin, contacto.getId());

                Adjunto adj = new Adjunto("Aviso_de_Cobro_" + departamento.getNombre() + ".pdf", new ByteArrayResource(
                        this.getAvisoDeCobro(avisoDeCobro)));
                adjuntos.add(adj);

                final Map mapVelocity = new HashMap();
                mapVelocity.put("mensaje", mensaje.replaceAll("(\r\n|\n)", "<br />"));
                mapVelocity.put("host", configurationServiceImpl.getHost());

                final String asunto = "Aviso de Cobro " + departamento.getNombre();
                final String nombreCondominio = condominio.getNombre();

                new Thread(new Runnable() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void run() {
                        emailingService.sendEmail(emailsMap, asunto,
                                "aviso-cobro.html", mapVelocity, adjuntos, nombreCondominio);
                    }
                }).start();
            }
        }
    }

    public AvisoDeCobro getAvisoDeCobro(Condominio condominio, Long idDepartamento,
                                         Date fin, Long idContacto) {
        Contacto contacto = null;
        if(idContacto != null) {
            contacto = contactoService.get(idContacto);
        } else {
            contacto = ContactoFactory.newInstance();
        }
        return getAvisoDeCobro(condominio,
                DepartamentoFactory.newInstance(idDepartamento),
                contacto, fin);
    }

    /*private Date getFinal(Date inicio) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inicio);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }*/

}
