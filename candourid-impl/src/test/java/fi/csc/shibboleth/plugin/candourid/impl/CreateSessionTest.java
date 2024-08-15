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

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.mockito.Mockito;
import org.opensaml.profile.context.ProfileRequestContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import fi.csc.shibboleth.plugin.candourid.CandourEventIds;
import fi.csc.shibboleth.plugin.candourid.context.CandourContext;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourInvitationRequestPayload;
import fi.csc.shibboleth.plugin.candourid.messaging.impl.CandourResponse;
import net.shibboleth.idp.authn.context.AuthenticationContext;
import net.shibboleth.idp.profile.context.navigate.WebflowRequestContextProfileRequestContextLookup;
import net.shibboleth.idp.profile.testing.RequestContextBuilder;
import net.shibboleth.shared.component.ComponentInitializationException;
import net.shibboleth.shared.servlet.impl.HttpServletRequestResponseContext;
import net.shibboleth.shared.servlet.impl.ThreadLocalHttpServletRequestSupplier;

public class CreateSessionTest {

    private CreateSession action;

    private RequestContext src;

    private ProfileRequestContext prc;

    private CandourContext ctx;

    @BeforeMethod
    public void setup() throws Exception {
        src = new RequestContextBuilder().buildRequestContext();
        prc = new WebflowRequestContextProfileRequestContextLookup().apply(src);
        ctx = (CandourContext) prc.addSubcontext(new AuthenticationContext()).addSubcontext(new CandourContext());
        action = new CreateSession();
        action.setCandouridURI("https://example.com/api");
        action.setClientPublicKey("publicKey");
        action.setClientHmacKey("hmacKey");
        action.setPayload(new CandourInvitationRequestPayload());
        action.setHttpServletRequestSupplier(new ThreadLocalHttpServletRequestSupplier());
        HttpServletRequestResponseContext.loadCurrent(new MockHttpServletRequest(), new MockHttpServletResponse());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void testExpectedSuccess() throws Exception {

        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        CandourResponse candourResponse = new CandourResponse(200,
                "{\"redirectUrl\":\"http://example.com/auth\", \"verificationSessionId\":\"id\",\"timestamp\":\"stamp\",\"validUntil\":\"until\"}");
        Mockito.when(httpClient.execute((HttpUriRequest) Mockito.any(), (HttpContext) Mockito.any(),
                (HttpClientResponseHandler<CandourResponse>) Mockito.any())).thenReturn(candourResponse);
        action.setHttpClient(httpClient);
        action.initialize();
        Event event = action.execute(src);
        Assert.assertNull(event);
        Assert.assertEquals(ctx.getAuthenticationUri(), "http://example.com/auth");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUnexpectedSuccess() throws Exception {

        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        // new claim in response
        CandourResponse candourResponse = new CandourResponse(200,
                "{\"newclaim\":\"value\",\"redirectUrl\":\"http://example.com/auth\", \"verificationSessionId\":\"id\",\"timestamp\":\"stamp\",\"validUntil\":\"until\"}");
        Mockito.when(httpClient.execute((HttpUriRequest) Mockito.any(), (HttpContext) Mockito.any(),
                (HttpClientResponseHandler<CandourResponse>) Mockito.any())).thenReturn(candourResponse);
        action.setHttpClient(httpClient);
        action.initialize();
        Event event = action.execute(src);
        Assert.assertNull(event);
        Assert.assertEquals(ctx.getAuthenticationUri(), "http://example.com/auth");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFailure() throws Exception {

        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        // http code not indicating success
        CandourResponse candourResponse = new CandourResponse(400, "Something bad happened");
        Mockito.when(httpClient.execute((HttpUriRequest) Mockito.any(), (HttpContext) Mockito.any(),
                (HttpClientResponseHandler<CandourResponse>) Mockito.any())).thenReturn(candourResponse);
        action.setHttpClient(httpClient);
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_API_RESP_FAILURE);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMalformed() throws Exception {

        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        // Success case is expected to have json sting in payload
        CandourResponse candourResponse = new CandourResponse(200, "Something bad happened");
        Mockito.when(httpClient.execute((HttpUriRequest) Mockito.any(), (HttpContext) Mockito.any(),
                (HttpClientResponseHandler<CandourResponse>) Mockito.any())).thenReturn(candourResponse);
        action.setHttpClient(httpClient);
        action.initialize();
        Event event = action.execute(src);
        Assert.assertEquals(event.getId(), CandourEventIds.CANDOUR_API_RESP_MALFORMED);
    }

    @Test(expectedExceptions = ComponentInitializationException.class)
    public void testInitFailNoUri() throws Exception {
        action = new CreateSession();
        action.setClientPublicKey("publicKey");
        action.setClientHmacKey("hmacKey");
        action.setPayload(new CandourInvitationRequestPayload());
        action.setHttpServletRequestSupplier(new ThreadLocalHttpServletRequestSupplier());
        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        action.setHttpClient(httpClient);
        action.initialize();
    }

    @Test(expectedExceptions = ComponentInitializationException.class)
    public void testInitFailNoPublicKey() throws Exception {
        action = new CreateSession();
        action.setCandouridURI("https://example.com/api");
        action.setClientHmacKey("hmacKey");
        action.setPayload(new CandourInvitationRequestPayload());
        action.setHttpServletRequestSupplier(new ThreadLocalHttpServletRequestSupplier());
        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        action.setHttpClient(httpClient);
        action.initialize();
    }

    @Test(expectedExceptions = ComponentInitializationException.class)
    public void testInitFailNoHmac() throws Exception {
        action = new CreateSession();
        action.setCandouridURI("https://example.com/api");
        action.setClientPublicKey("publicKey");
        action.setPayload(new CandourInvitationRequestPayload());
        action.setHttpServletRequestSupplier(new ThreadLocalHttpServletRequestSupplier());
        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        action.setHttpClient(httpClient);
        action.initialize();
    }

    @Test(expectedExceptions = ComponentInitializationException.class)
    public void testInitFailNoPayload() throws Exception {
        action = new CreateSession();
        action.setCandouridURI("https://example.com/api");
        action.setClientPublicKey("publicKey");
        action.setClientHmacKey("hmacKey");
        action.setHttpServletRequestSupplier(new ThreadLocalHttpServletRequestSupplier());
        final HttpClient httpClient = Mockito.mock(HttpClient.class);
        action.setHttpClient(httpClient);
        action.initialize();
    }

}
