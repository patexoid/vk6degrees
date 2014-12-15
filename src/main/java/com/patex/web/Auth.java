package com.patex.web;

import com.patex.graph.FriendTree;
import com.patex.graph.LeafExpander;
import com.patex.web.vksocial.VK;
import com.patex.web.vksocial.VKProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by apotekhin on 12/10/2014.
 */
@Controller
@RequestMapping("/auth")
public class Auth {

    @Autowired
    VKAuthenticator _authenticator;

    @Autowired
    LeafExpander _leafExpander;

    @RequestMapping("/")
    public String authenticate() {


        return "redirect:" +_authenticator.getAuthorizeUrl();
    }

    @RequestMapping("/apply")
    public void apply(@RequestParam String code) {
        VK api = _authenticator.getConnection(code).getApi();
        _leafExpander.config(api);
        final VKProfile user = api.usersOperations().getUser();

        System.out.println(new FriendTree("",_leafExpander).findChains(new FriendTree("",_leafExpander)));
        System.out.println(new FriendTree("",_leafExpander).findChains(new FriendTree("",_leafExpander)));

    }
}
