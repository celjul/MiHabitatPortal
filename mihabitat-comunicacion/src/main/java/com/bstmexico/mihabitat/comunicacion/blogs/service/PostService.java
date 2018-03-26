package com.bstmexico.mihabitat.comunicacion.blogs.service;

import com.bstmexico.mihabitat.comunicacion.blogs.model.Post;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Tema;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Pegasus on 29/07/2015.
 */
public interface PostService {
    Tema save(Post post);
    Post get(Long id);
    void update(Post post);
    Collection<Post> search(Map map);
}
