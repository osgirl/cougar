/*
 * Copyright 2015, Simon Matić Langford
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.betfair.cougar.test.socket.tester.common;

/**
 *
 */
public class ServerConfigurations {
    public static final String PLAIN = "plain";
    public static final String SUPPORTS_SSL = "supports_ssl";
    public static final String REQUIRES_SSL = "requires_ssl";
    public static final String WANTS_CLIENT_AUTH = "supports_ssl_wants_client_auth";
    public static final String NEEDS_CLIENT_AUTH = "supports_ssl_needs_client_auth";
}