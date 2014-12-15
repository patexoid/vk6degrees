package com.patex.web;

import com.patex.web.vksocial.VK;
import com.patex.web.vksocial.VKConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by apotekhin on 12/10/2014.
 */

@Component
public class VKAuthenticator {
    @Value("vk_id")
    private String clientId;

    @Value("vk_secret")
    private String clientSecret;

    @Value("redirect")
    private  String redirect;

    public static final String SCOPE = "friends";

    private VKConnectionFactory _vkConnectionFactory;

    @PostConstruct
    public void init(){
        _vkConnectionFactory = new VKConnectionFactory(clientId, clientSecret);
    }

    public String getAuthorizeUrl(){
        OAuth2Operations operations = _vkConnectionFactory.getOAuthOperations();
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri(redirect);
        parameters.setScope(SCOPE);
        return operations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);
    }

    public Connection<VK> getConnection(String code){
        OAuth2Operations oAuthOperations = _vkConnectionFactory.getOAuthOperations();
        OAuth2Parameters additionalParameters = new OAuth2Parameters();
        AccessGrant accessGrant = oAuthOperations.exchangeForAccess(code, redirect, additionalParameters);
        return _vkConnectionFactory.createConnection(accessGrant);
    }

    public boolean isAuthenticated(){
        return false;
    }
}
