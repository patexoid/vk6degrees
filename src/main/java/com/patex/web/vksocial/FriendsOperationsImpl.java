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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User operations.
 * <br>Here should be implemented methods, that can be found under http://vk.com/dev/methods <code>Users</code> section.
 *
 * @author vkolodrevskiy
 */
class FriendsOperationsImpl extends AbstractVKOperations implements FriendsOperations {
    private final RestTemplate restTemplate;

    public FriendsOperationsImpl(RestTemplate restTemplate, String accessToken, ObjectMapper objectMapper, boolean isAuthorizedForUser) {
        super(isAuthorizedForUser, accessToken, objectMapper);
        this.restTemplate = restTemplate;
    }

    @Override
    public Collection<String> getFriends(String userId) {
        requireAuthorization();
        Properties props = new Properties();




        if(userId!=null){
            props.put("user_id",userId);
        }

        // see documentation under http://vk.com/dev/users.get
        URI uri = makeOperationURL("friends.get", props, "5.2.7");

        String profiles = null;
        try {
            profiles = restTemplate.getForObject(uri, String.class);
        } catch (RuntimeException e) {
            profiles = restTemplate.getForObject(uri, String.class);
            e.printStackTrace();
        }
        profiles=profiles.substring(profiles.indexOf('[')+1, profiles.indexOf(']'));

        return Arrays.asList(profiles.split(","));
    }

}
