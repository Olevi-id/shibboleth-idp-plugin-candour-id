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
package fi.csc.shibboleth.plugin.candourid.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;
import org.opensaml.security.httpclient.HttpClientSecuritySupport;
import org.slf4j.Logger;

import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponse;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponseHandler;

import net.shibboleth.shared.annotation.constraint.NonnullAfterInit;
import net.shibboleth.shared.annotation.constraint.ThreadSafeAfterInit;
import net.shibboleth.shared.component.ComponentInitializationException;
import net.shibboleth.shared.logic.Constraint;
import net.shibboleth.shared.primitive.LoggerFactory;

/**
 * An abstract class for Candour actions that make synchronous HTTP requests and
 * return types of {@link CandourResponse responses}.
 */
@ThreadSafeAfterInit
public abstract class AbstractCandourHttpAuthenticationAction extends AbstractCandourAuthenticationAction {

    /** Class logger. */
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractCandourHttpAuthenticationAction.class);

    /** Candour API location. */
    @NonnullAfterInit
    private URI candouridURI;

    /** Candour API client public key. */
    @NonnullAfterInit
    private String clientPublicKey;

    /** Candour API client hmac key. */
    @NonnullAfterInit
    private String clientHmacKey;

    /** Http client for contacting the endpoint. */
    @NonnullAfterInit
    private HttpClient httpClient;

    /** HTTP client security parameters. */
    @Nullable
    private HttpClientSecurityParameters httpClientSecurityParameters;

    /**
     * Set the {@link HttpClient} to use.
     * 
     * @param client client to use
     */
    public void setHttpClient(@Nonnull final HttpClient client) {
        checkSetterPreconditions();
        httpClient = Constraint.isNotNull(client, "HttpClient cannot be null");
    }

    /**
     * Set the optional client security parameters.
     * 
     * @param params the new client security parameters
     */
    public void setHttpClientSecurityParameters(@Nullable final HttpClientSecurityParameters params) {
        checkSetterPreconditions();
        httpClientSecurityParameters = params;
    }

    /**
     * Set Candour API location.
     * 
     * @param uri Candour API location
     * @throws URISyntaxException
     */
    public void setCandouridURI(@Nonnull String uri) throws URISyntaxException {
        checkSetterPreconditions();
        assert uri != null;
        candouridURI = new URI(uri);
    }

    /**
     * Get Candour API location.
     * 
     * @return Candour API location
     */
    public URI getCandouridURI() {
        return candouridURI;
    }

    /**
     * Set Candour API client public key.
     * 
     * @param publicKey Candour API client public key
     */
    public void setClientPublicKey(@Nonnull String publicKey) {
        checkSetterPreconditions();
        assert publicKey != null;
        clientPublicKey = publicKey;
    }

    /**
     * Get Candour API client public key.
     * 
     * @return Candour API client public key
     */
    public String getClientPublicKey() {
        return clientPublicKey;
    }

    /**
     * Set Candour API client hmac key.
     * 
     * @param hmacKey Candour API client hmac key
     */
    public void setClientHmacKey(@Nonnull String hmacKey) {
        checkSetterPreconditions();
        assert hmacKey != null;
        clientHmacKey = hmacKey;
    }

    /**
     * Get Candour API client hmac key.
     * 
     * @return Candour API client hmac key
     */
    public String getClientHmacKey() {
        return clientHmacKey;
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();

        if (httpClient == null) {
            throw new ComponentInitializationException("httpClient cannot be null");
        }
        if (candouridURI == null) {
            throw new ComponentInitializationException("CandouridURI cannot be null");
        }
        if (clientPublicKey == null) {
            throw new ComponentInitializationException("ClientPublicKey cannot be null");
        }
        if (clientHmacKey == null) {
            throw new ComponentInitializationException("ClientHmacKey cannot be null");
        }
    }

    /**
     * Performs a call to an Http endpoint using the configured HttpClient,
     * HttpClientResponseHandler, and security parameters.
     * 
     * @param request                the prepared HTTP request
     * @param authenticatableContext an authenticatable context to set the
     *                               authenticated flag. Can be {@literal null} if
     *                               no flag is supplied.
     * 
     * @return the encoded Http response.
     * 
     * @throws IOException if there is an error producing a response
     */
    @Nullable
    protected CandourResponse executeHttpRequest(@Nonnull final ClassicHttpRequest request) throws IOException {

        Constraint.isNotNull(request, "Request can not be null");
        final HttpClientContext clientContext = HttpClientContext.create();
        assert clientContext != null;
        HttpClientSecuritySupport.marshalSecurityParameters(clientContext, httpClientSecurityParameters, true);
        HttpClientSecuritySupport.addDefaultTLSTrustEngineCriteria(clientContext, request);
        CandourResponse candourResponse = httpClient.execute(request, clientContext, new CandourResponseHandler());
        final String scheme = request.getScheme();
        assert scheme != null;
        HttpClientSecuritySupport.checkTLSCredentialEvaluated(clientContext, scheme);
        return candourResponse;

    }

}
