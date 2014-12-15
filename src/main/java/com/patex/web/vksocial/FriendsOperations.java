package com.patex.web.vksocial;

import java.util.Collection;

/**
 * Created by apotekhin on 12/10/2014.
 */
public interface FriendsOperations {

    public Collection<String> getFriends(String userIds);
}
