/*
 * Copyright 2011 the original author or authors.
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
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.support.URIBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Properties;

/**
 */
class AbstractVKOperations {
    private final static String VK_REST_URL = "https://api.vk.com/method/";

    private final boolean isAuthorized;
    private final String accessToken;
    protected final ObjectMapper objectMapper;

    public AbstractVKOperations(boolean isAuthorized, String accessToken, ObjectMapper objectMapper) {
        this.isAuthorized = isAuthorized;
        this.accessToken = accessToken;
        this.objectMapper = objectMapper;
    }

    protected void requireAuthorization() {
        if (!isAuthorized) {
            throw new MissingAuthorizationException("vkontakte");
        }
    }

    protected URI makeOperationURL(String method, Properties params, String apiVersion) {
        URIBuilder uri = URIBuilder.fromUri(VK_REST_URL + method);

        preProcessURI(uri);

        // add api version
        // TODO: I think finally we should migrate to latest api
        uri.queryParam("v", apiVersion);

        for (Map.Entry<Object, Object> objectObjectEntry : params.entrySet()) {
            uri.queryParam(objectObjectEntry.getKey().toString(), objectObjectEntry.getValue().toString());
        }
        return uri.build();
    }

    protected void preProcessURI(URIBuilder uri) {
        // add access_token
        // TODO: for some methods we do not need access token, so think about it.
        uri.queryParam("access_token", accessToken);
    }

    // throw exception if VKontakte response contains error
    // TODO: consider to throw specific exceptions for each error code.
    //       like for error code 113 that would be let's say InvalidUserIdVKException
    protected <T extends VKResponse> void checkForError(T toCheck) {
        if (toCheck.getError() != null) {
            throw new RuntimeException(toCheck.getError().getCode() + ": " + toCheck.getError().getMessage());
        }
    }

}
