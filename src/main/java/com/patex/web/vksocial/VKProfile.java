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


import java.util.Date;

/**
 * Model class containing a VKontakte user's profile information.
 *
 * @author vkolodrevskiy
 */
public class VKProfile {

    private final static String PROFILE_URL_EXTESTION = "http://vk.com/";

    private final String uid;
    private final String firstName;
    private final String lastName;

    public VKProfile(String uid, String firstName, String lastName ) {
        this.uid = uid;

        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * The user's VKontskte ID
     *
     * @return The user's VKontakte ID
     */
    public String getUid() {
        return uid;
    }


    /**
     * The user's first name
     *
     * @return The user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The user's last name
     *
     * @return The user's last name
     */
    public String getLastName() {
        return lastName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VKProfile)) return false;

        VKProfile vkProfile = (VKProfile) o;

        if (!uid.equals(vkProfile.uid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
