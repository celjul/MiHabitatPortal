package com.bstmexico.mihabitat.web.controllers.administrador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.bstmexico.mihabitat.web.dto.AvisoDeCobro;
import com.bstmexico.mihabitat.web.service.AvisoDeCobroService;
import com.bstmexico.mihabitat.web.util.ReportUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.contactos.factory.ContactoFactory;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.factory.DepartamentoFactory;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.web.dto.Adjunto;
import com.bstmexico.mihabitat.web.dto.Item;
import com.bstmexico.mihabitat.web.exceptions.GenericController;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 */
@Controller
@RequestMapping(value = "administrador/aviso-cobro")
public class AvisoDeCobroController extends GenericController {

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private AvisoDeCobroService avisoDeCobroService;

    @Autowired
    private ServletContext context;

    @Autowired
    private ReportUtils reportUtils;

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory
            .getLogger(AvisoDeCobroController.class);

    @RequestMapping(method = RequestMethod.GET, value = "individual")
    public String getIndividual(Model model) {

        model.addAttribute("idDepartamento", 0);
        model.addAttribute("idContacto", 0);
        return "administrador/aviso-cobro/individual";
    }

    @RequestMapping(method = RequestMethod.GET, value = "individual/{idDepartamento}/{idContacto}")
    public String getIndividualConId(Model model, @PathVariable Long idDepartamento, @PathVariable Long idContacto) {
        model.addAttribute("idDepartamento", idDepartamento);
        model.addAttribute("idContacto", idContacto);
        return "administrador/aviso-cobro/individual";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "buscar")
    public Collection<Item> buscar(@RequestParam String key, HttpSession session) {
        Collection<Item> items = new ArrayList<Item>();

        Set<Contacto> contactos = new HashSet<Contacto>();
        contactos.addAll(contactoService.buscar((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()), key));

        Set<Departamento> departamentos = new HashSet<Departamento>();
        Map map = new HashMap();
        map.put("condominio", (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()));
        map.put("nombre", key);
        departamentos.addAll(departamentoService.search(map));

        for (Contacto contacto : contactos) {
            if (!CollectionUtils.isEmpty(contacto.getDepartamentos())) {
                for (ContactoDepartamento cd : contacto.getDepartamentos()) {
                    Item item = new Item();
                    item.setLabel(new StringBuilder()
                            .append(!StringUtils.isEmpty(contacto.getNombre()) ? contacto
                                    .getNombre() : "")
                            .append(!StringUtils.isEmpty(contacto
                                    .getApellidoPaterno()) ? " "
                                    + contacto.getApellidoPaterno() : "")
                            .append(!StringUtils.isEmpty(contacto
                                    .getApellidoMaterno()) ? " "
                                    + contacto.getApellidoMaterno() : "")
                            .append(" - ")
                            .append(cd.getDepartamento().getNombre())
                            .toString());
                    item.setUrl("/administrador/aviso-cobro/"
                            + cd.getDepartamento().getId() + "/departamento");
                    item.setId(String.valueOf(contacto.getId()));
                    items.add(item);
                }
            }
        }

        for (Departamento departamento : departamentos) {
            if (!CollectionUtils.isEmpty(departamento.getContactos())) {
                for (ContactoDepartamento cd : departamento.getContactos()) {
                    Item item = new Item();
                    item.setLabel(new StringBuilder()
                            .append(departamento.getNombre())
                            .append(" - ")
                            .append(!StringUtils.isEmpty(cd.getContacto()
                                    .getNombre()) ? cd.getContacto()
                                    .getNombre() : "")
                            .append(!StringUtils.isEmpty(cd.getContacto()
                                    .getApellidoPaterno()) ? " "
                                    + cd.getContacto().getApellidoPaterno()
                                    : "")
                            .append(!StringUtils.isEmpty(cd.getContacto()
                                    .getApellidoMaterno()) ? " "
                                    + cd.getContacto().getApellidoMaterno()
                                    : "").toString());
                    item.setUrl("/administrador/aviso-cobro/"
                            + departamento.getId() + "/departamento");
                    item.setId(String.valueOf(cd.getContacto().getId()));
                    items.add(item);
                }
            }
        }
        return items;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{idDepartamento}/departamento")
    @ResponseBody
    public AvisoDeCobro getAvisoDeCobro(@PathVariable Long idDepartamento,
                                        @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
                                        @RequestParam(required = false) Long idContacto, HttpSession session) {
        Condominio condominio = (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue());
        AvisoDeCobro ac = avisoDeCobroService.getAvisoDeCobro(condominio, idDepartamento, fin, idContacto);
        return ac;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{idDepartamento}/imprimir")
    public ResponseEntity<byte[]> imprimir(@PathVariable Long idDepartamento,
                                           @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
                                           @RequestParam(required = false) Long idContacto, HttpSession session) {

        Condominio condominio = (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue());
        AvisoDeCobro ac = avisoDeCobroService.getAvisoDeCobro(
                condominio, idDepartamento, fin, idContacto);
        byte[] bytes = avisoDeCobroService.getAvisoDeCobro(ac);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        String filename = "aviso_cobro_" + idDepartamento + ".pdf";
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "{idDepartamento}/enviar")
    public Boolean enviar(@PathVariable Long idDepartamento,
                          @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
                          @RequestParam(required = false) Long idContacto,
                          @RequestParam(value = "emails[]") String[] emails,
                          @RequestParam String mensaje, HttpSession session) {


        avisoDeCobroService.sendAvisoDeCobro((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()), mensaje, idDepartamento, idContacto, fin, emails);

        /*Condominio condominio = (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue());
        AvisoDeCobro ac = avisoDeCobroService.getAvisoDeCobro(condominio,
                idDepartamento, fin, idContacto);
        ByteArrayResource resource = new ByteArrayResource(
                avisoDeCobroService.getAvisoDeCobro(ac));

        Departamento dpto = departamentoService.get(idDepartamento);

        Map<String, String> emailsMap = new HashMap<>();
        for(String e : emails){
            emailsMap.put(e, dpto.getNombre());
        }

        final String asunto = "Aviso de Cobro " + dpto.getNombre();
        avisoDeCobroService.sendAvisoDeCobro(new Adjunto("aviso_cobro_"
                + idDepartamento + ".pdf", resource), mensaje, condominio.getNombre(), asunto, emailsMap);*/
        return Boolean.TRUE;
    }

    @RequestMapping(method = RequestMethod.GET, value = "masivo")
    public String getMasivo(Model model, HttpSession session) {
        return "administrador/aviso-cobro/masivo";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "masivo/lista")
    public Collection<AvisoDeCobro> getAvisosDeCobro(HttpSession session,
                                                     @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin) {

        Condominio condominio = (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue());
        Map map = new HashMap();
        map.put("condominio", condominio);

        Collection<AvisoDeCobro> avisos = new ArrayList<>();
        for (Departamento departamento : departamentoService.search(map)) {
            AvisoDeCobro aviso = avisoDeCobroService.getAvisoDeCobro(condominio, departamento.getId(),
                    fin, null);
            if (!CollectionUtils.isEmpty(aviso.getDepartamento()
                    .getContactos())) {
                for (ContactoDepartamento cd : aviso.getDepartamento()
                        .getContactos()) {
                    if (cd.getPrincipal()) {
                        aviso.setContacto(cd.getContacto());
                        break;
                    }
                }
            }

            avisos.add(aviso);
        }
        return avisos;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "masivo/enviar")
    public Boolean envioMasivo(@RequestParam(value = "ids[]") Long[] ids,
                               @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
                               HttpSession session) {

        avisoDeCobroService.sendAvisoDeCobroMultiple((Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue()), "", fin, ids);

        /*Condominio condominio = (Condominio) session
                .getAttribute(SessionEnum.CONDOMINIO.getValue());
        Map map = new HashMap();
        map.put("condominio", condominio);

        for (Long idDepartamento : ids) {
            Departamento departamento = departamentoService.get(idDepartamento);
            Contacto contacto = contactoService.get(departamento.obtenerPrincipal().getContacto().getId());
            AvisoDeCobro avisoDeCobro = avisoDeCobroService.getAvisoDeCobro(condominio, idDepartamento,
                    fin, contacto.getId());

            Collection<String> emails = new ArrayList<String>();
            if (contacto != null && !CollectionUtils.isEmpty(contacto
                    .getEmails())) {
                for (Email email : contacto.getEmails()) {
                    emails.add(email.getDireccion());
                }
            }
            if(emails != null && !emails.isEmpty()){
                ByteArrayResource resource = new ByteArrayResource(
                        avisoDeCobroService.getAvisoDeCobro(avisoDeCobro));
                final String asunto = "Aviso de Cobro " + departamento.getNombre();
sfsdf
                avisoDeCobroService.sendAvisoDeCobro("mensaje" ,condominio,
                        emails.toArray(new String[emails.size()]));
            }
        }*/
        return Boolean.TRUE;
    }

	@RequestMapping(method = RequestMethod.GET, value = "masivo/imprimir")
	public ResponseEntity<byte[]> impresionMasivo(@RequestParam(value = "ids[]") Long[] ids,
                                                  @RequestParam String formato,
							   @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fin,
							   HttpSession session) {

		Condominio condominio = (Condominio) session
				.getAttribute(SessionEnum.CONDOMINIO.getValue());
		Map map = new HashMap();
		map.put("condominio", condominio);

        Collection<AvisoDeCobro> avisosdeCobro = new ArrayList<>();

		for (Long idDepartamento : ids) {
            Departamento departamento = departamentoService.get(idDepartamento);
            Contacto contacto = contactoService.get(departamento.obtenerPrincipal().getContacto().getId());
            AvisoDeCobro avisoDeCobro = avisoDeCobroService.getAvisoDeCobro(condominio, idDepartamento,
                    fin, contacto.getId());
            avisosdeCobro.add(avisoDeCobro);
        }

        byte[] bytes = avisoDeCobroService.getAvisoDeCobroMultiple(avisosdeCobro, formato);

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        String filename = "avisos_cobro_" + condominio.getNombre() + "_" + (new SimpleDateFormat("yyyyMMdd")).format(fin) + "."+ formato;

        reportUtils.setHttpHeaders(headers, formato);
        headers.setContentDispositionFormData(filename, filename);

        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}


}
