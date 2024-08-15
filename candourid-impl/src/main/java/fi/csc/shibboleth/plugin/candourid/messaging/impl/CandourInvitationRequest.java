/*
 * Copyright (c) 2024 CSC- IT Center for Science, www.csc.fi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.csc.shibboleth.plugin.candourid.messaging.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;

/**
 * Class to create Candour invitation request.
 */
public class CandourInvitationRequest extends AbstractCandourRequest<CandourInvitationRequestPayload> {

    /**
     * Constructor.
     * 
     * @param apiUri          API uri
     * @param clientPublicKey Client public key
     * @param clientHmacKey   Client hmac key
     */
    public CandourInvitationRequest(final URI apiUri, final String clientPublicKey, final String clientHmacKey) {
        super(apiUri, clientPublicKey, clientHmacKey, HttpMethod.POST);
    }

}
