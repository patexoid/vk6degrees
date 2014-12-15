package com.patex.web.vksocial;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;


/**
 * Created by apotekhin on 12/10/2014.
 */
public class VKConnectionFactory extends OAuth2ConnectionFactory<VK> {

    public VKConnectionFactory(String clientId, String clientSecret) {
        super("vk", new VKServiceProvider(clientId, clientSecret), new VKAdapter());
    }

    @Override
    public boolean supportsStateParameter() {
        // vk.com does not send state parameter in it's OAuth2 callback
        // see https://github.com/vkolodrevskiy/spring-social-vkontakte/issues/14
        return false;
    }
}
