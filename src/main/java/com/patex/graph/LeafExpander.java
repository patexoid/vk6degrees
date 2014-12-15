package com.patex.graph;

import java.util.Collection;

/**
 * Created by apotekhin on 12/12/2014.
 */
public interface LeafExpander {

    String[] getRelated(String uid);
    void config(Object obj);
}
