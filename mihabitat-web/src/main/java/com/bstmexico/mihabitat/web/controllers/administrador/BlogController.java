package com.bstmexico.mihabitat.web.controllers.administrador;

import com.bstmexico.mihabitat.comunes.archivos.factory.ArchivoFactory;
import com.bstmexico.mihabitat.comunes.archivos.model.Archivo;
import com.bstmexico.mihabitat.comunes.archivos.service.ArchivoService;
import com.bstmexico.mihabitat.comunes.catalogos.service.CatalogoService;
import com.bstmexico.mihabitat.comunes.exceptions.service.ServiceException;
import com.bstmexico.mihabitat.comunes.usuarios.model.Usuario;
import com.bstmexico.mihabitat.comunicacion.blogs.factory.AdjuntoPostFactory;
import com.bstmexico.mihabitat.comunicacion.blogs.factory.TemaRevisadoFactory;
import com.bstmexico.mihabitat.comunicacion.blogs.model.*;
import com.bstmexico.mihabitat.comunicacion.blogs.service.BlogService;
import com.bstmexico.mihabitat.comunicacion.blogs.service.PostService;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaRevisadoService;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaService;
import com.bstmexico.mihabitat.condominios.model.Condominio;
import com.bstmexico.mihabitat.web.util.HibernateAwareObjectMapper;
import com.bstmexico.mihabitat.web.util.SessionEnum;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Zoé Jonatan Tapia Hernández on 10/08/2015.
 */
@Controller
@RequestMapping(value = "administrador/blogs")
public class BlogController {

    @Autowired
    private HibernateAwareObjectMapper mapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TemaService temaService;

    @Autowired
    private TemaRevisadoService temaRevisadoService;

    @Autowired
    @Qualifier("postserviceproxy")
    private PostService postService;

    @Autowired
    private CatalogoService catalogoService;

    @Autowired
    private ArchivoService archivoService;


    private static final Logger LOG = LoggerFactory
            .getLogger(BlogController.class);

    public static final String ARCHIVOS = "archivosadjuntos";

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.GET, value = "lista")
    public String listaBlogs(Model model, HttpSession session) {
        model.addAttribute("blogs",
                mapper.writeValueAsString(blogService.getList((Condominio) session.getAttribute("condominio"))));
        model.addAttribute("user",
                mapper.writeValueAsString((Usuario) session.getAttribute(SessionEnum.USUARIO
                        .getValue())));
        return "administrador/blogs/lista";
    }

    @RequestMapping(method = RequestMethod.GET, value = "{idBlog}/temas/nuevo")
    public String nuevo(@PathVariable Long idBlog, Model model, HttpSession session) {

        session.removeAttribute(ARCHIVOS);
        model.addAttribute("tipos",
                mapper.writeValueAsString(catalogoService.getList(CatalogoTipoIncidencia.class)));
        model.addAttribute("estatus",
                mapper.writeValueAsString(catalogoService.getList(CatalogoEstatusIncidencia.class)));
        model.addAttribute("condominio", (Condominio) session.getAttribute("condominio"));
        model.addAttribute("blog",mapper
                .writeValueAsString(blogService.get(idBlog)));
        model.addAttribute("user", mapper
                .writeValueAsString((Usuario) session.getAttribute(SessionEnum.USUARIO
                        .getValue())));

        return "administrador/blogs/temas/nuevo";
    }

    @RequestMapping(method = RequestMethod.POST, value = "temas/guardar")
    public @ResponseBody
    Tema guardar(@RequestBody Tema tema,
                       HttpSession session) {

        tema.setUsuario((Usuario) session.getAttribute(SessionEnum.USUARIO
                .getValue()));
        tema.setCondominio((Condominio) session.getAttribute(SessionEnum.CONDOMINIO
                .getValue()));
        tema.setFecha(new DateTime());
        temaService.save(tema);
        return tema;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(method = RequestMethod.GET, value = "{blog}/temas/lista")
    public String lista(@PathVariable Long blog, Model model, HttpSession session) {
        Blog blogObj = blogService.getTemasCondominio(blog, (Condominio) session.getAttribute("condominio"));
        model.addAttribute("blog", mapper.writeValueAsString(blogObj));
        model.addAttribute("user", mapper
                .writeValueAsString((Usuario) session.getAttribute(SessionEnum.USUARIO
                        .getValue())));
        return "administrador/blogs/temas/lista";
    }

    @RequestMapping(method = RequestMethod.GET, value = "temas/actualizar/{tema}")
    public String editar(@PathVariable Long tema, Model model, HttpSession session) {

        session.removeAttribute(ARCHIVOS);
        Tema temaObject = temaService
                .getConArchivos(tema);
        model.addAttribute("tipos",
                mapper.writeValueAsString(catalogoService.getList(CatalogoTipoIncidencia.class)));
        model.addAttribute("estatus",
                mapper.writeValueAsString(catalogoService.getList(CatalogoEstatusIncidencia.class)));
        model.addAttribute("tema", mapper
                .writeValueAsString(temaObject));
        model.addAttribute("condominio",(Condominio) session.getAttribute("condominio"));
        model.addAttribute("user", mapper
                .writeValueAsString((Usuario) session.getAttribute(SessionEnum.USUARIO
                        .getValue())));
        return "administrador/blogs/temas/editar";
    }

    @RequestMapping(method = RequestMethod.POST, value = "temas/leer/{tema}")
    public @ResponseBody Tema leer(@PathVariable Long tema, Model model, HttpSession session) {
        Tema temaObject = temaService
                .getConArchivos(tema);
        TemaRevisado tr = temaObject.getTemaRevisado((Usuario) session.getAttribute(SessionEnum.USUARIO
                .getValue()));
        if(tr == null) {
            tr = TemaRevisadoFactory.newInstance();
            tr.setUsuario((Usuario) session.getAttribute(SessionEnum.USUARIO
                    .getValue()));
            tr.setFecha(new DateTime());
            tr.setTema(temaObject);
            temaRevisadoService.save(tr);
        } else {
            tr.setFecha(new DateTime());
            temaRevisadoService.update(tr);
        }
        return temaObject;
    }

    @RequestMapping(method = RequestMethod.POST, value = "temas/actualizar")
    public @ResponseBody Tema actualizar(
            @RequestBody Tema tema,
            HttpSession session) {

        temaService.update(tema);
        return tema;
    }

    @RequestMapping(method = RequestMethod.POST, value = "post/guardar")
    public @ResponseBody Tema guardarPost(
            @RequestBody Post post,
            HttpSession session) {
        Collection<AdjuntoPost> adjuntos =
                ((Collection<AdjuntoPost>)session.getAttribute(ARCHIVOS));
        post.setAdjuntos(adjuntos);
        post.setUsuario((Usuario) session.getAttribute(SessionEnum.USUARIO
                .getValue()));
        post.setFecha(new DateTime());
        postService.save(post);
        session.removeAttribute(ARCHIVOS);
        return temaService.getConArchivos(post.getTema().getId());
    }

    @RequestMapping(method = RequestMethod.POST, value = "post/actualizar")
    public @ResponseBody Tema actualizarPost(
            @RequestBody Post post,
            HttpSession session) {

        post.setUsuario((Usuario) session.getAttribute(SessionEnum.USUARIO
                .getValue()));
        postService.update(post);
        return temaService.getConArchivos(post.getTema().getId());
    }

    @RequestMapping(method = RequestMethod.POST, value = "subirarchivo")
    public @ResponseBody Collection<AdjuntoPost> processUpload(
            @RequestParam MultipartFile file, HttpServletRequest request,
            HttpSession session) {

        Collection<AdjuntoPost> adjuntos = ((Collection<AdjuntoPost>)session.getAttribute(ARCHIVOS));
        if(adjuntos == null){
            adjuntos = new ArrayList<>();
        }
        AdjuntoPost adjunto;
        ArchivoAdjuntoPost archivo;
        /*((DefaultMultipartHttpServletRequest) request).getMultipartFiles();*/
        /*for(MultipartFile file : ) {*/
        archivo = ArchivoFactory.newInstance(ArchivoAdjuntoPost.class);
        archivo.setNombre(file.getOriginalFilename());
        archivo.setTamano(file.getSize() / 1024 + " Kb");
        archivo.setTipo(file.getContentType());
        try {
            archivo.setBytes(file.getBytes());
        } catch (IOException ex) {
            String message = "Error al intentar subir el archivo al servidor.";
            LOG.warn(message, ex);
            throw new ServiceException(message, ex);
        }
        adjunto = AdjuntoPostFactory.newInstance();
        adjunto.setArchivo(archivo);
        adjuntos.add(adjunto);
        /*}*/

        session.setAttribute(ARCHIVOS, adjuntos);

        return adjuntos;
    }

    @RequestMapping(method = RequestMethod.POST, value = "eliminararchivo")
    public @ResponseBody Collection<AdjuntoPost> processDelete(
            @RequestParam String file, HttpServletRequest request,
            HttpSession session) {

        Collection<AdjuntoPost> adjuntos = ((Collection<AdjuntoPost>)session.getAttribute(ARCHIVOS));
        if(adjuntos == null){
            adjuntos = new ArrayList<>();
        }
        if(adjuntos != null && !adjuntos.isEmpty()) {
            for (AdjuntoPost adjuntoPost : adjuntos) {
                if (file.equals(adjuntoPost.getArchivo().getNombre())) {
                    adjuntos.remove(adjuntoPost);
                    break;
                }
            }
        }

        return adjuntos;
    }

    @RequestMapping(value = "/descargar/{idArchivo}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getComprobante(@PathVariable Long idArchivo) {
        Archivo archivo = archivoService.get(idArchivo);

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        String filename = archivo.getNombre();
        headers.setContentType(MediaType
                .parseMediaType(archivo.getTipo()));
        headers.setContentDispositionFormData(filename, filename);
        return new ResponseEntity<byte[]>(archivo.getBytes(), headers, HttpStatus.OK);
    }
}
