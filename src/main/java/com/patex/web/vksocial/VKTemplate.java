/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.patex.web.vksocial;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>This is the central class for interacting with VKontakte.</p>
 * <p>
 * There are some operations, such as searching, that do not require OAuth
 * authentication. In those cases, you may use a {@link com.patex.web.vksocial.VKTemplate} that is
 * created through the default constructor and without any OAuth details.
 * Attempts to perform secured operations through such an instance, however,
 * will result in {@link org.springframework.social.NotAuthorizedException} being thrown.
 * </p>
 *
 * @author vkolodrevskiy
 */
public class VKTemplate extends AbstractOAuth2ApiBinding implements VK {

    private UserOperations usersOperations;
    
    private FriendsOperations _friendsOperations;

    private ObjectMapper objectMapper;

    private final String accessToken;

    protected final String clientSecret;

    // TODO: remove?
    public VKTemplate() {
        initialize();
        this.accessToken = null;
        this.clientSecret = null;
    }

    public VKTemplate(String accessToken, String clientSecret) {
        super(accessToken);
        this.accessToken = accessToken;
        this.clientSecret = clientSecret;
        initialize();
    }

    private void initialize() {
        registerJsonModule();
        getRestTemplate().setErrorHandler(new VKErrorHandler());
        initSubApis();
    }

    private void registerJsonModule() {
        List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;

                List<MediaType> mTypes = new LinkedList<MediaType>(jsonConverter.getSupportedMediaTypes());
                mTypes.add(new MediaType("text", "javascript", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET));
                jsonConverter.setSupportedMediaTypes(mTypes);

                objectMapper = new ObjectMapper();
                objectMapper.registerModule(new VKModule());
                jsonConverter.setObjectMapper(objectMapper);
            }
        }
    }

    private void initSubApis() {
        usersOperations = new UserOperationsImpl(getRestTemplate(), accessToken, objectMapper, isAuthorized());
        _friendsOperations=new FriendsOperationsImpl(getRestTemplate(), accessToken, objectMapper, isAuthorized());
    }



    @Override
    protected void configureRestTemplate(RestTemplate restTemplate) {
        super.configureRestTemplate(restTemplate);
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory(){
            @Override
            public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
                Object lock = new Object();
                synchronized (lock){
                    try {
                        lock.wait(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return super.createRequest(uri, httpMethod);
            }
        });
    }

    @Override
    public UserOperations usersOperations() {
        return usersOperations;
    }

    @Override
    public FriendsOperations friendsOperations() {
        return _friendsOperations;
    }
}
