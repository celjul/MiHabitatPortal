package com.bstmexico.mihabitat.web.controllers.contacto;

import com.bstmexico.mihabitat.comunes.catalogos.factory.CatalogoFactory;
import com.bstmexico.mihabitat.comunes.catalogos.model.Catalogo;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.direcciones.factory.DireccionFactory;
import com.bstmexico.mihabitat.comunes.personas.model.Email;
import com.bstmexico.mihabitat.comunes.personas.model.Telefono;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunicacion.directorio.factory.DirectorioRegistroFactory;
import com.bstmexico.mihabitat.comunicacion.directorio.model.CatalogoTipoDirectorio;
import com.bstmexico.mihabitat.comunicacion.directorio.model.DirectorioRegistro;
import com.bstmexico.mihabitat.comunicacion.directorio.service.DirectorioRegistroService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.web.controllers.administrador.BlogController;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Zoe on 25/01/2016.
 */
@Controller
@RequestMapping(value = "contacto/directorio")
public class MiDirectorioController {

    private static final Logger LOG = LoggerFactory
            .getLogger(MiDirectorioController.class);

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private DirectorioRegistroService directorioRegistroService;

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

        Collection<Catalogo> catalogosTipoDirectorio = catalogoService.getList(CatalogoTipoDirectorio.class);

        for(Usuario usuario : condominio.getAdministradores()){
            DirectorioRegistro dir = DirectorioRegistroFactory.newInstance();
            dir.setTitulo(usuario.getPersona().getNombre() + " " +
                    usuario.getPersona().getApellidoPaterno() + " " + usuario.getPersona().getApellidoMaterno());

            if(!CollectionUtils.isEmpty(usuario.getPersona().getEmails())) {
                StringBuilder sb = new StringBuilder();
                for(Email email : usuario.getPersona().getEmails()){
                    sb.append(email.getDireccion() + ", ");
                }
                sb.delete(sb.lastIndexOf(", "), sb.length());
                dir.setEmail(sb.toString());
            }

            if(!CollectionUtils.isEmpty(usuario.getPersona().getTelefonos())) {
                StringBuilder sb = new StringBuilder();
                for(Telefono telefono : usuario.getPersona().getTelefonos()){
                    sb.append(telefono.getNumero() + ", ");
                }
                sb.delete(sb.lastIndexOf(", "), sb.length());
                dir.setTelefono(sb.toString());
            }

            dir.setTipo((CatalogoTipoDirectorio) CatalogoFactory.newInstance(CatalogoTipoDirectorio.class, 0L));
            dir.getTipo().setDescripcion("Administrador");

            directorios.add(dir);
        }

        model.addAttribute("tiposDirectorio", mapper.writeValueAsString(catalogoService.getList(CatalogoTipoDirectorio.class)));
        model.addAttribute("registrosDirectorio",
                mapper.writeValueAsString(directorios));

        return "directorio/lista";
    }

}

