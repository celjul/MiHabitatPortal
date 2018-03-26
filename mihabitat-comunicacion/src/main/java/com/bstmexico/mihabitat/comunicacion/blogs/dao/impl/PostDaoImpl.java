package com.bstmexico.mihabitat.comunicacion.blogs.dao.impl;

import com.bstmexico.mihabitat.comunes.dao.impl.GenericDaoImpl;
import com.bstmexico.mihabitat.comunicacion.blogs.dao.PostDao;
import com.bstmexico.mihabitat.comunicacion.blogs.model.Post;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Pegasus on 29/07/2015.
 */
@Repository
public class PostDaoImpl extends GenericDaoImpl<Post, Long> implements
        PostDao {

        @Transactional(readOnly = true)
        @Override
        public Post get(Long key) {
                Post post = super.get(key);
                post.getTema().getId();
                return super.get(key);
        }
}
