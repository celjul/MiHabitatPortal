package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.comunicacion.directorio.factory.DirectorioRegistroFactory;
import com.bstmexico.mihabitat.comunicacion.directorio.model.CatalogoTipoDirectorio;
import com.bstmexico.mihabitat.comunicacion.directorio.model.DirectorioRegistro;
import com.bstmexico.mihabitat.comunicacion.directorio.service.DirectorioRegistroService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.contactos.model.Contacto;
import com.bstmexico.mihabitat.contactos.service.ContactoService;
import com.bstmexico.mihabitat.departamentos.model.ContactoDepartamento;
import com.bstmexico.mihabitat.departamentos.model.Departamento;
import com.bstmexico.mihabitat.departamentos.service.DepartamentoService;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zoe on 25/01/2016.
 */
@Controller
@RequestMapping(value = "administrador/directorio")
public class DirectorioController {

    private static final Logger LOG = LoggerFactory
            .getLogger(DirectorioController.class);

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private DirectorioRegistroService directorioRegistroService;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String listaBlogs(Model model, HttpSession session) {

        Condominio condominio = (Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                .getValue());

        Collection<DirectorioRegistro> directorios =
                directorioRegistroService.getList(condominio);

        Map map = new HashMap();
        map.put("condominio", condominio);

        Collection<Contacto> contactos = contactoService.search(map);

        if(!CollectionUtils.isEmpty(contactos)) {
            for(Contacto contacto : contactos){
                for(ContactoDepartamento contactoDepartamento : contacto.getDepartamentos()){
                    DirectorioRegistro dir = DirectorioRegistroFactory.newInstance();
                    dir.setTitulo(contactoDepartamento.getDepartamento().getNombre() + " | " + (contacto.getNombre() + " " +
                            contacto.getApellidoPaterno() + " " +
                            contacto.getApellidoMaterno()));

                    if(!CollectionUtils.isEmpty(contacto.getEmails())) {
                        StringBuilder sb = new StringBuilder();
                        for(Email email : contacto.getEmails()){
                            sb.append(email.getDireccion() + ", ");
                        }
                        sb.delete(sb.lastIndexOf(", "), sb.length());
                        dir.setEmail(sb.toString());
                    }

                    if(!CollectionUtils.isEmpty(contacto.getTelefonos())) {
                        StringBuilder sb = new StringBuilder();
                        for(Telefono telefono : contacto.getTelefonos()){
                            sb.append(telefono.getNumero() + ", ");
                        }
                        sb.delete(sb.lastIndexOf(", "), sb.length());
                        dir.setTelefono(sb.toString());
                    }

                    dir.setTipo((CatalogoTipoDirectorio) CatalogoFactory.newInstance(CatalogoTipoDirectorio.class, 0L));
                    dir.getTipo().setDescripcion("Condómino");

                    directorios.add(dir);
                }
            }
        }

        model.addAttribute("tiposDirectorio", mapper.writeValueAsString(catalogoService.getList(CatalogoTipoDirectorio.class)));
        model.addAttribute("registrosDirectorio",
                mapper.writeValueAsString(directorios));

        return "directorio/lista";
    }

    @RequestMapping(method = RequestMethod.POST, value = "guardar")
    public @ResponseBody
    DirectorioRegistro guardar(@RequestBody DirectorioRegistro directorioRegistro,
                 HttpSession session) {

        directorioRegistro.getCondominios().add((Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                        .getValue()));
        directorioRegistro.setTipo((CatalogoTipoDirectorio)
                CatalogoFactory.newInstance(CatalogoTipoDirectorio.class,
                        configurationService.getDirectorioCondominio()));
        directorioRegistroService.save(directorioRegistro);

        return directorioRegistro;
    }

    @RequestMapping(method = RequestMethod.POST, value = "actualizar")
    public @ResponseBody
    DirectorioRegistro actualizar(@RequestBody DirectorioRegistro directorioRegistro,
                               HttpSession session) {

        directorioRegistro.getCondominios().clear();
        directorioRegistro.getCondominios().add((Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                .getValue()));
        directorioRegistro.setTipo((CatalogoTipoDirectorio)
                CatalogoFactory.newInstance(CatalogoTipoDirectorio.class,
                        configurationService.getDirectorioCondominio()));
        directorioRegistroService.update(directorioRegistro);

        return directorioRegistro;
    }

}
