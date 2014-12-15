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

import org.springframework.social.ApiBinding;

/**
 * Interface specifying a basic set of operations for interacting with .
  *
 * @author vkolodrevskiy
 */
public interface VK extends ApiBinding {

    /**
     * API for performing operations on VKontakte user profiles.
     */
    public UserOperations usersOperations();


    /**
     * API for performing operations with a user's set of friends.
     */
    public FriendsOperations friendsOperations();


}
