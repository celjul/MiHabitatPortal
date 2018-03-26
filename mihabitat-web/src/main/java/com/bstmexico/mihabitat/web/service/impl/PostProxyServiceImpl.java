package com.bstmexico.mihabitat.web.service.impl;

import com.bstmexico.mihabitat.comunicacion.blogs.model.Post;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;
import com.bstmexico.mihabitat.comunicacion.blogs.service.PostService;
import com.bstmexico.mihabitat.comunicacion.blogs.service.TemaService;
import com.bstmexico.mihabitat.comunicacion.blogs.service.impl.PostServiceImpl;
import com.bstmexico.mihabitat.configuration.ConfigurationServiceImpl;
import com.bstmexico.mihabitat.web.service.NotificationHelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Pegasus on 07/08/2015.
 */
@Service("postserviceproxy")
public class PostProxyServiceImpl implements PostService {

    private static final Logger LOG = LoggerFactory
            .getLogger(PostProxyServiceImpl.class);

    @Autowired
    @Qualifier("postservicedefault")
    private PostService postService;

    @Autowired
    private TemaService temaService;

    @Autowired
    private NotificationHelperService notificationHelperService;

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @Override
    public Tema save(Post post) {
        Tema tema = postService.save(post);
        if(tema.getPosts().size() <= 1) {
            try {
                notificationHelperService.enviarNotificacionNuevoTema(post.getTema());
            } catch(Exception e) {
                LOG.error("Ocurrio un error al tratar de enviar la notificación del nuevo tema " + tema.getNombre() + " el error es: " + e.getLocalizedMessage());
            }
        } else {
            //notificationHelperService.enviarNotificacionNuevoPost(post);
        }
        return tema;
    }

    @Override
    public Post get(Long id) {
        return postService.get(id);
    }

    @Override
    public void update(Post post) {
        postService.update(post);
    }

    @Override
    public Collection<Post> search(Map map) {
        return postService.search(map);
    }
}
