package com.patex.web.vksocial;


import java.util.Collection;

/**
 * Created by apotekhin on 12/10/2014.
 */
public interface UserOperations {

    VKProfile getUser();

    Collection<VKProfile> getUsers(String fields, String... userIds);
}
