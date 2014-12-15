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
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * User operations.
 * <br>Here should be implemented methods, that can be found under http://vk.com/dev/methods <code>Users</code> section.
 *
 * @author vkolodrevskiy
 */
class UserOperationsImpl extends AbstractVKOperations implements UserOperations {
    private final RestTemplate restTemplate;

    public UserOperationsImpl(RestTemplate restTemplate, String accessToken, ObjectMapper objectMapper, boolean isAuthorizedForUser) {
        super(isAuthorizedForUser, accessToken, objectMapper);
        this.restTemplate = restTemplate;
    }



    @Override
    public Collection<VKProfile> getUsers(String fields, String... userIds) {
        requireAuthorization();
        Properties props = new Properties();


        StringBuilder uids = new StringBuilder();
        if (userIds != null) {
            for (String uid : userIds) {
                if (uids.toString().isEmpty())
                    uids.append(uid.trim());
                else uids.append(",").append(uid.trim());
            }
        }

        props.put("user_ids", userIds == null ? "" : uids.toString());
        props.put("fields", fields);

        // see documentation under http://vk.com/dev/users.get
        URI uri = makeOperationURL("users.get", props, "3.0");

        VKProfiles profiles = restTemplate.getForObject(uri, VKProfiles.class);
        checkForError(profiles);


        return profiles.getProfiles();
    }

    public Collection<VKProfile> usersExtend(String fields, VKProfile... userIds) {
        final List<String> collect = Arrays.asList(userIds).stream().map(VKProfile::getUid).collect(Collectors.toList());
        return getUsers(fields, collect.toArray(new String[collect.size()]));
    }

    @Override
    public VKProfile getUser() {
        final Collection<VKProfile> users = getUsers("uid");
        return users.iterator().next();
    }
}
