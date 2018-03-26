package com.bstmexico.mihabitat.comunicacion.blogs.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by Pegasus on 18/08/2015.
 */
@Entity
@DiscriminatorValue("post_normal")
public class PostNormal extends Post {

    /**
     *
     */
    private static final long serialVersionUID = 1650156283297293659L;
}
