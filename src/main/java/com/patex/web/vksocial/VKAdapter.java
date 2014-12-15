/*
 * Copyright 2013-2014 the original author or authors.
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

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.web.client.HttpClientErrorException;

/**
 * VKontakte ApiAdapter implementation.
 *
 * @author vkolodrevskiy
 */
public class VKAdapter implements ApiAdapter<VK> {
    @Override
    public boolean test(VK vk) {
        try {
            vk.usersOperations().getUser();
            return true;
        } catch (HttpClientErrorException e) {
            return false;
        }
    }


    public void setConnectionValues(VK vk, ConnectionValues values) {
//        VKProfile profile = vk.usersOperations().getUser();
//        values.setProviderUserId(profile.getUid());
//        values.setDisplayName(profile.getFirstName() + " " + profile.getLastName());
//        values.setProfileUrl("http://vk.com/id" + profile.getUid());
//        values.setImageUrl(profile.getPhoto());
    }

    @Override
    public UserProfile fetchUserProfile(VK vk) {
        VKProfile profile = vk.usersOperations().getUser();
        return new UserProfileBuilder()
                .setFirstName(profile.getFirstName())
                .setLastName(profile.getLastName())
                .setName(profile.getFirstName() + " " + profile.getLastName())
                .build();
    }

    @Override
    public void updateStatus(VK vkontakte, String message) {
        // vk api does not allow to perform status.set or wall.post methods for websites,
        // so according to method contract we do nothing here
    }
}
