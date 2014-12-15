package com.patex.web;

import com.patex.graph.FriendTree;
import com.patex.graph.LeafExpander;
import com.patex.web.vksocial.VK;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by apotekhin on 12/12/2014.
 */
@Component
@Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
public class VKLeafExpander implements LeafExpander{
    private VK _api;

    public void config(Object api) {
        _api = (VK) api;
    }

    @Override
    @Cacheable("treeCache")
    public String[] getRelated(String uid) {
        return _api.friendsOperations().getFriends(uid).toArray(new String[0]);
    }
}
