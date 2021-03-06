/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.k.loader.yaml

import org.apache.camel.component.direct.DirectEndpoint
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.component.telegram.TelegramEndpoint
import org.apache.camel.k.loader.yaml.support.TestSupport

class RoutesWithEndpointTest extends TestSupport {
    def 'from'() {
        setup:
            def parameters = [
                'telegram.token': 'myToken+',
                'direct.timeout': '1234s',
            ]
            def context = startContextForSpec {
                propertiesComponent.initialProperties = parameters as Properties
            }
        when:
            def de = context.endpoints.find { it instanceof DirectEndpoint }
            def te = context.endpoints.find { it instanceof TelegramEndpoint }
            def me = context.endpoints.find { it instanceof MockEndpoint }
        then:
            te != null
            me != null

            with (de, DirectEndpoint) {
                timeout == 1_234_000
            }
            with (te, TelegramEndpoint) {
                endpointUri == 'telegram://bots?authorizationToken=RAW(myToken+)'
                configuration.authorizationToken == 'myToken+'
            }
            with (me, MockEndpoint) {
                name == 'telegram'
            }
        cleanup:
            context?.stop()
    }

    def 'route'() {
        setup:
            def parameters = [
                'telegram.token': 'myToken+',
                'direct.timeout': '1234s',
            ]
            def context = startContextForSpec {
                propertiesComponent.initialProperties = parameters as Properties
            }
        when:
            def de = context.endpoints.find { it instanceof DirectEndpoint }
            def te = context.endpoints.find { it instanceof TelegramEndpoint }
            def me = context.endpoints.find { it instanceof MockEndpoint }
        then:
            te != null
            me != null

            with (de, DirectEndpoint) {
                timeout == 1_234_000
            }
            with (te, TelegramEndpoint) {
                endpointUri == 'telegram://bots?authorizationToken=RAW(myToken+)'
                configuration.authorizationToken == 'myToken+'
            }
            with (me, MockEndpoint) {
                name == 'telegram'
            }
        cleanup:
            context?.stop()
    }
}
